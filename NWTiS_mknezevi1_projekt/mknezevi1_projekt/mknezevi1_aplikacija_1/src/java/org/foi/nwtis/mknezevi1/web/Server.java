/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import org.foi.nwtis.mknezevi1.konfiguracije.Konfiguracija;
import org.foi.nwtis.mknezevi1.rest.klijenti.GoogleMapsKlijent;
import org.foi.nwtis.mknezevi1.web.podaci.Location;
import org.foi.nwtis.mknezevi1.web.podaci.WeatherData2;

/**
 * Klasa koja predstavlja socket server za upravljanje pozadinskom dretvom.
 *
 * @author Matija
 */
public class Server extends Thread {

    public boolean serverRadi = true;
    ServerSocket serverSocket;
    Konfiguracija konfig;
    private ServletContext kontekst;
    String odgovor = "";
    int port;
    List<WeatherData2> meteoPodaciZaAdresu;
    String statistika = "";
    private String adresaStatistike;
    private String statickiDio;
    private String adresaServera;
    private String korisnickoIme;
    long start = 0;
    long trajanje = 0;
    int brojPrimljenihNaredbi = 0;
    int brojNeispravnihNaredbi = 0;
    int brojIzvrsenihNaredbi = 1;

    public Server(ServletContext c) {
        super();
        this.kontekst = c;
        konfig = (Konfiguracija) c.getAttribute("konfig");
        port = Integer.parseInt(konfig.dajPostavku("port"));
        adresaStatistike = konfig.dajPostavku("adresaStatistike");
        adresaServera = konfig.dajPostavku("adresaServera");
        korisnickoIme = konfig.dajPostavku("korisnickoIme");
        statickiDio = konfig.dajPostavku("statickiDio");
        //System.out.println("port:" + port);
    }

    @Override
    public void run() {
        System.out.println("Socket radi!");

        try {
            serverSocket = new ServerSocket(port);

            while (serverRadi) {
                Socket veza = serverSocket.accept();
                InputStream is = veza.getInputStream();
                OutputStream os = veza.getOutputStream();
                StringBuilder komanda = new StringBuilder();

                while (true) {
                    int znak = is.read();
                    if (znak == -1) {
                        break;
                    }
                    komanda.append((char) znak);
                }
                System.out.println("komanda: " + komanda);

                //u testZaREST implementirano slanje naredbi, odkomentiraj
                String sintaksa = "^USER ([a-zA-Z0-9_-]+);"
                        + "(( PASSWD) (([!#a-zA-Z0-9_-]+));)?"
                        + "(( PAUSE;))?"
                        + "(( START;))?"
                        + "(( STOP;))?"
                        + "(( ADD \"(.*?)\"+;))?"
                        + "(( TEST \"(.*?)\"+;))?"
                        + "(( GET \"(.*?)\"+;))?"
                        + "(( ADD) ([a-zA-Z0-9_-]+); (NEWPASSWD) (([!#a-zA-Z0-9_-]+));)?"
                        + "$";

                //"(( ADD) ([a-zA-Z0-9_-]+);)?"
                //"(( TEST) ([a-zA-Z0-9_-]+);)?"
                //"(( GET) ([a-zA-Z0-9_-]+);)?"
                Pattern pattern = Pattern.compile(sintaksa);
                Matcher m = pattern.matcher(komanda);
                boolean status = m.matches();
                Baza bp = new Baza();
                GoogleMapsKlijent gmk = new GoogleMapsKlijent();

                brojPrimljenihNaredbi++;
                String vrijeme = (new Date()) + "";
                
                bp.upisiDnevnickiZapis(komanda.toString(),vrijeme);
                System.out.println("Naredba upisana u dnevnik");
                
                if (status) {
                    //onda je sintaksa dobra
                    System.out.println("Odgovara");

                    if (m.group(3) != null) {
                        //onda je admin 
                        start = System.currentTimeMillis();
                        
                        System.out.println("admin:" + m.group(1));
                        System.out.println("lozinka:" + m.group(4));

                        if (bp.provjeraKorisnika(m.group(1), m.group(4)).equals("OK")) {
                            //onda je admin uspjesno prosao provjeru
                            if (m.group(7) != null) {
                                //PAUSE;
                                System.out.println("naredba:" + m.group(7));

                                if (PozadinskaDretva.prikupljajPodatke == true) {
                                    PozadinskaDretva.prikupljajPodatke = false;
                                    odgovor = "OK 10";
                                } else {
                                    odgovor = "ERR 40";
                                }
                            }

                            if (m.group(9) != null) {
                                //START;
                                System.out.println("naredba:" + m.group(9));

                                if (PozadinskaDretva.prikupljajPodatke == false) {
                                    PozadinskaDretva.prikupljajPodatke = true;
                                    odgovor = "OK 10";
                                } else {
                                    odgovor = "ERR 41";
                                }
                            }

                            if (m.group(11) != null) {
                                //STOP;
                                System.out.println("naredba:" + m.group(11));

                                PozadinskaDretva.prikupljajPodatke = false;
                                serverRadi = false;
                            }

                            if (m.group(13) != null) {
                                //ADD "adresa";
                                System.out.println("naredba:" + m.group(13));
                                System.out.println("adresa:" + m.group(14));

                                Location location = gmk.getGeoLocation(m.group(14));
                                if (bp.provjeraAdrese(m.group(14)).equals("OK")) {
                                    odgovor = "ERR 50";
                                } else {
                                    bp.upisiAdresu(m.group(14), location.getLatitude(), location.getLongitude());
                                    odgovor = "OK 10";
                                }

                            }

                            if (m.group(15) != null) {
                                //TEST "adresa";                          
                                System.out.println("naredba:" + m.group(15));
                                System.out.println("adresa:" + m.group(17));

                                if (bp.provjeraAdrese(m.group(17)).equals("OK")) {
                                    odgovor = "OK 10";
                                } else {
                                    odgovor = "ERR 51";
                                }
                            }

                            if (m.group(18) != null) {
                                //GET "adresa";
                                System.out.println("naredba:" + m.group(18));
                                System.out.println("adresa:" + m.group(20));

                                meteoPodaciZaAdresu = bp.dajListuMeteoPodataka(m.group(20));

                                if (meteoPodaciZaAdresu.isEmpty()) {
                                    odgovor = "ERR52";
                                } else {
                                    WeatherData2 zadnje = meteoPodaciZaAdresu.get(meteoPodaciZaAdresu.size() - 1);
                                    //System.out.println("zadnje:" + zadnje.getTemperatura());
                                    Location location = gmk.getGeoLocation(m.group(20));

                                    odgovor = "OK 10 TEMP " + zadnje.getTemperatura()
                                            + " VLAGA " + zadnje.getVlaga()
                                            + " GEOSIR " + location.getLatitude()
                                            + " GEODUZ " + location.getLongitude();
                                }
                            }

                            if (m.group(21) != null) {
                                //ADD korisnik1; NEWPASSWD lozinka1;
                                System.out.println("naredba:" + m.group(21));
                                System.out.println("korisnik1:" + m.group(23));
                                System.out.println("lozinka1:" + m.group(25));

                                if (bp.upisiAdmina(m.group(23), m.group(25)).equals("OK")) {
                                    odgovor = "OK 10";
                                }
                            }

                            try {
                                trajanje = System.currentTimeMillis() - start;
                                
                                statistika = "informacije o naredbi:" 
                                        + "\n trajanje:" + trajanje
                                        + "\n broj primljenih naredbi:" + brojPrimljenihNaredbi
                                        + "\n broj neispravnih naredbi:" + brojNeispravnihNaredbi
                                        + "\n broj izvrsenih naredbi:" + brojIzvrsenihNaredbi;                    
                                // Create the JavaMail session
                                java.util.Properties properties = System.getProperties();
                                properties.put("mail.smtp.host", adresaServera);
                                Session session = Session.getInstance(properties, null);
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
                                message.setContent(statistika, "text/plain");
                                
                                Transport.send(message); 
                                System.out.println("STATISTIKA POSLANA");
                                
                                System.out.println("STATISTIKA:" + statistika);                       
                            } catch (Exception e) {
                                
                            }

                        } else {
                            odgovor = "ERR 30";
                        }
                    } else {
                        //onda je korisnik obicni 
                        if (m.group(18) != null) {
                            //USER korisnik; GET "adresa";
                            System.out.println("korisnik:" + m.group(1));
                            System.out.println("adresa:" + m.group(20));

                            meteoPodaciZaAdresu = bp.dajListuMeteoPodataka(m.group(20));

                            if (meteoPodaciZaAdresu.isEmpty()) {
                                odgovor = "ERR52";
                            } else {
                                WeatherData2 zadnje = meteoPodaciZaAdresu.get(meteoPodaciZaAdresu.size() - 1);
                                //System.out.println("zadnje:" + zadnje.getTemperatura());
                                Location location = gmk.getGeoLocation(m.group(20));

                                odgovor = "OK 10 TEMP " + zadnje.getTemperatura()
                                        + " VLAGA " + zadnje.getVlaga()
                                        + " GEOSIR " + location.getLatitude()
                                        + " GEODUZ " + location.getLongitude();
                            }
                        }
                    }

                    brojIzvrsenihNaredbi++;
                    os.write(odgovor.getBytes());
                    is.close();
                    os.close();
                    veza.close();
                } else {
                    brojNeispravnihNaredbi++;
                    System.out.println("Ne odgovara!");
                    odgovor = "NE ODGOVARA";
                    os.write(odgovor.getBytes());
                    is.close();
                    os.close();
                    veza.close();
                    //return;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.interrupt();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void interrupt() {
        System.out.println("Obavijest: Socket interrupt");

        super.interrupt();
        if (!serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
