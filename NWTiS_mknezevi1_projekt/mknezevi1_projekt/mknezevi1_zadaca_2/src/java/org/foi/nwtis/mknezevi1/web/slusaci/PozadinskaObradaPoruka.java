/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web.slusaci;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import org.foi.nwtis.mknezevi1.konfiguracije.Konfiguracija;

/**
 * Ova klasa sluzi za obradu pristiglih email poruka u pozadini.
 *
 * @author Matija
 */
public class PozadinskaObradaPoruka extends Thread {

    Konfiguracija konfig;
    private ServletContext kontekst;
    private long interval;

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss.zzz");
    long vrijemePocetak = 0;
    long vrijemeZavrsetak;
    long vrijemeTrajanje = 0;
    int brojPorukaUkupno = 0;
    int brojPorukaProcitano = 0;
    int brojPorukaProlaz = 0;
    int brojIspravnihPoruka = 0;
    int brojOstalihPoruka = 0;
    int brojPreuzetihDatoteka = 0;

    public boolean ziva = true;
    private String adresaStatistike;
    private String statickiDio;
    private String adresaServera;
    private String korisnickoIme;
    private String lozinka;
    private String kljucna;
    private String direktorijIspravnih;
    private String direktorijOstalih;

    /**
     * konstruktor PozadinskaObradaPoruka prima kontekst u parametru poziva
     * nadkonstruktor dretve te izvlaci potrebne podatke iz konfiguracije
     *
     * @param c
     */
    public PozadinskaObradaPoruka(ServletContext c) {
        //System.out.println("ISPIS: Pozadinska");

        super();
        this.kontekst = c;
        konfig = (Konfiguracija) c.getAttribute("konfig");
        this.interval = Integer.parseInt(konfig.dajPostavku("interval"));
        this.adresaStatistike = konfig.dajPostavku("adresaStatistike");
        this.adresaServera = konfig.dajPostavku("adresaServera");
        this.korisnickoIme = konfig.dajPostavku("korisnickoIme");
        this.lozinka = konfig.dajPostavku("lozinka");
        this.kljucna = konfig.dajPostavku("kljucna");
        this.direktorijOstalih = konfig.dajPostavku("ostalePoruke");
        this.direktorijIspravnih = konfig.dajPostavku("ispravnePoruke");
        this.statickiDio = konfig.dajPostavku("statickiDio");
    }

    /**
     * metoda provjeraIspravnosti prima kao parametar sadrza poruke, provjerava
     * sintaksu sadrzaja kroz regex te ako je sadrzaj ispravan, izvlaci podatke
     * o korisniku i njegovoj lozinki. Tada te podatke salje na provjeru
     * ispravnosti s podacima u bazi podataka. Ako je sintaksa dobra, poruka je
     * ispravna i metoda vraca true. Ukoliko je sintaksa neispravna, poruka je
     * neispravna i metoda vraca false
     *
     * @param sadrzaj
     * @return
     */
    private boolean provjeraIspravnosti(String sadrzaj) {
        String sintaksa = "^USER ([a-zA-Z0-9_-]+);"
                + "(( PASSWORD) (([!#a-zA-Z0-9_-]+));)"
                + "$";

        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(sadrzaj);
        boolean status = m.matches();
        if (status) {
            if (m.group(1) != null) {
                System.out.println("USER:" + m.group(1));
            }
            if (m.group(4) != null) {
                System.out.println("PASSWORD:" + m.group(4));
            }

            Login_slusaci login = new Login_slusaci();
            login.setKorisnickoIme(m.group(1));
            login.setKorisnickaLozinka(m.group(4));
            if (login.provjeraKorisnika(kontekst).equals("OK")) {
                System.out.println("Postoji korisnik");
            } else {
                System.out.println("Nepostojeci korisnik");
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * metoda premjestiPoruku premjesta dobivenu poruku iz njenog trenutnog
     * foldera u odredeni folder. Ako odredisni folder ne postoji, kreira se.
     *
     * @param odrediste
     * @param store
     * @param poruka
     * @param izvoriste
     * @throws MessagingException
     */
    private void premjestiPoruku(String odrediste, Store store, Message poruka, Folder izvoriste) throws MessagingException {
        Folder odredisniFolder = store.getFolder(odrediste);

        if (!odredisniFolder.exists()) {
            odredisniFolder.create(Folder.HOLDS_MESSAGES);
        }

        odredisniFolder = store.getFolder(odrediste);
        
        //fiktivno stvaranje polja (1 element) jer copyMessages zahtijeva polje kao argument
        Message[] poruke = new Message[1];
        poruke[0] = poruka;

        izvoriste.copyMessages(poruke, odredisniFolder);
        izvoriste.setFlags(poruke, new Flags(Flags.Flag.DELETED), true);
        izvoriste.close(true);
    }

    /**
     * metoda sluzi za stopiranje rada dretve s ciljem sprecavanja mogucnosti da
     * zaostale dretve ometaju rad aplikacije.
     */
    @Override
    public void interrupt() {
        ziva = false;
        super.interrupt();
    }

    /**
     * metoda run predstavlja rad pozadinske dretve. Nakon pripremnih radnji,
     * dohvacaju se poruke iz INBOX foldera, kroz njih se iterira (povecavajuci
     * brojeve poruka) te im se jednoj po jednoj provjerava tip, sadrzaj
     * predmeta (kljucna) te njihov ociscen sadrzaj. Nakon provjeravanja, poruke
     * se prebacaju u za njih predviden specificirani folder. Na kraju se
     * formira i salje statistika na za to predvidenu adresu u konfiguraciji.
     */
    @Override
    public void run() {
        Session session = null;
        Store store = null;
        Folder folder = null;
        Message poruka = null;
        Message[] messages = null;
        Object messagecontentObject = null;
        String sender = null;
        String subject = null;
        Multipart multipart = null;
        Part part = null;
        String contentType = null;
        brojPorukaUkupno = 0;

        while (ziva) {
            brojPorukaProcitano = 0;
            brojPorukaProlaz = 0;
            brojIspravnihPoruka = 0;
            brojOstalihPoruka = 0;
            brojPreuzetihDatoteka = 0;

            vrijemePocetak = (new Date()).getTime();

            try {
                // Start the session
                java.util.Properties properties = System.getProperties();
                session = Session.getInstance(properties, null);
                // Connect to the store
                store = session.getStore("imap");
                store.connect(this.adresaServera, this.korisnickoIme, this.lozinka);
                folder = store.getDefaultFolder();
                folder = folder.getFolder("INBOX");
                //Reading the Email Index in Read / Write Mode
                folder.open(Folder.READ_WRITE);
                // Retrieve the messages
                messages = folder.getMessages();

                //provjeraIspravnosti("USER admin; PASSWORD 123456;"); ZA POTREBE TESTIRANJA
                // Loop over all of the messages       
                for (int i = 0; i < messages.length; ++i) {
                    poruka = messages[i];
                    brojPorukaProlaz++;
                    brojPorukaUkupno++;

                    //dobivanje cistog sadrzaja iz poruke
                    String p2 = poruka.getContent().toString();
                    p2 = p2.substring(0, p2.length() - 2);

                    if (poruka.getSubject().contains(kljucna) && poruka.isMimeType(poruka.getContentType())) {
                        //poruka ima trazeni sadrzaj i slijedi njena obrada
                        brojIspravnihPoruka++;
                        premjestiPoruku(direktorijIspravnih, store, poruka, folder);

                        /*if (provjeraIspravnosti(p2)) {
                         //poruka ispravna, dakle ima sadrzaj "USER matija; PASSWORD sifra;" 
                         brojIspravnihPoruka++;
                         premjestiPoruku(direktorijIspravnih, store, poruka, folder);
                         } else {
                         //poruka neispravna 
                         brojOstalihPoruka++;
                         premjestiPoruku(direktorijOstalih, store, poruka, folder);
                         }*/
                    } else {
                        //poruka nema trazeni sadrzaj
                        brojOstalihPoruka++;
                        premjestiPoruku(direktorijOstalih, store, poruka, folder);
                    }

                }

                // Close the folder
                folder.close(true);
                // Close the message store
                store.close();
            } catch (Exception ee) {

            }

            try {
                String statistika = "";
                vrijemeZavrsetak = (new Date()).getTime();
                vrijemeTrajanje = vrijemeZavrsetak - vrijemePocetak;

                statistika += "Obrada zapocela u: " + sdf.format(vrijemePocetak) + "\n"
                        + "Obrada zavrsila u: " + sdf.format(vrijemeZavrsetak) + "\n"
                        + "Trajanje obrade: " + vrijemeTrajanje + "\n"
                        + "Sveukupan broj poruka: " + brojPorukaUkupno + "\n"
                        + "Ukupan broj poruka: " + brojPorukaProlaz + "\n"
                        + "Broj ispravnih poruka: " + brojIspravnihPoruka + "\n"
                        + "Broj ostalih poruka: " + brojOstalihPoruka + "\n";

                // Create the JavaMail session
                java.util.Properties properties = System.getProperties();
                properties.put("mail.smtp.host", adresaServera);
                session = Session.getInstance(properties, null);
                // Construct the message
                MimeMessage message = new MimeMessage(session);
                // Set the from address
                Address fromAddress = new InternetAddress(korisnickoIme);
                message.setFrom(fromAddress);
                // Parse and set the recipient addresses
                Address[] toAddresses = InternetAddress.parse(adresaStatistike);
                message.setRecipients(Message.RecipientType.TO, toAddresses);
                // Set the subject and text
                message.setSubject(statickiDio);
                message.setText(statistika);
                message.setContent(statistika, "text/html");
                //Transport.send(message); 
                //System.out.println("STATISTIKA:" + statistika);
                //System.out.println("STATISTIKA POSLANA");         

                sleep((this.interval * 1000) - vrijemeTrajanje);
                System.out.println("vrijeme:" + new Date() + ": pokrece se obrada poruka");
            } catch (Exception ee) {

            }

        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }
}
