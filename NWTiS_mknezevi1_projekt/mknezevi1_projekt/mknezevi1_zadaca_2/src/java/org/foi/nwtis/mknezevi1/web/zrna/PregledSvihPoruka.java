/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web.zrna;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.RequestDispatcher;
import org.foi.nwtis.mknezevi1.web.kontrole.Poruka;

/**
 * Zrno sluzi za pregled poruka.
 *
 * @author nwtis_2
 */
@ManagedBean
@RequestScoped
public class PregledSvihPoruka {

    private String emailPosluzitelj = "127.0.0.1";
    private String korisnickoIme = "servis@nwtis.nastava.foi.hr";
    private String lozinka = "123456";
    private List<Poruka> poruke;
    private List<Poruka> poruka;
    private String trenutniId;
    private Map<String, String> mape;
    private String odabranaMapa = "INBOX";
    private String odabranaPoruka;
    private Session session;
    private Store store;
    private Folder folder;

    /**
     * Creates a new instance of PregledSvihPoruka Konstruktor PregledSvihPoruka
     * dohvaca podatke za email posluzitelj, korisnicko ime i lozinku. te se
     * povezuje na server
     */
    public PregledSvihPoruka() {
        EmailPovezivanje ep = (EmailPovezivanje) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("emailPovezivanje");
        emailPosluzitelj = ep.getEmailPosluzitelj();
        korisnickoIme = ep.getKorisnickoIme();
        lozinka = ep.getLozinka();

        try {
            poveziNaServer();
        } catch (MessagingException ex) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().dispatch("../../index.xhtml");
            } catch (IOException ex1) {
                //Logger.getLogger(PregledSvihPoruka.class.getName()).log(Level.SEVERE, null, ex1);
            }
            //Logger.getLogger(PregledSvihPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getEmailPosluzitelj() {
        return emailPosluzitelj;
    }

    public void setEmailPosluzitelj(String emailPosluzitelj) {
        this.emailPosluzitelj = emailPosluzitelj;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public List<Poruka> getPoruka() {
        this.poruka = new ArrayList<Poruka>();
        for (Poruka p : poruke) {
            if (p.getId().equals(this.trenutniId)) {
                this.poruka.add(p);
            }
        }
        return poruka;
    }

    public void setPoruka(List<Poruka> poruka) {
        this.poruka = poruka;
    }

    public List<Poruka> getPoruke() throws IOException {
        try {
            preuzmiPoruke();
        } catch (MessagingException ex) {
            Logger.getLogger(PregledSvihPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
        return poruke;
    }

    public void setPoruke(List<Poruka> poruke) {
        this.poruke = poruke;
    }

    public Map<String, String> getMape() {
        return mape;
    }

    public void setMape(Map<String, String> mape) {
        this.mape = mape;
    }

    public String getOdabranaMapa() {
        return odabranaMapa;
    }

    public void setOdabranaMapa(String odabranaMapa) {
        this.odabranaMapa = odabranaMapa;
    }

    public String getOdabranaPoruka() {
        return odabranaPoruka;
    }

    public void setOdabranaPoruka(String odabranaPoruka) {
        this.odabranaPoruka = odabranaPoruka;
    }

    public String odaberiMapu() {
        return "promjenaMape";
    }

    public String citajPoruke() {
        return "citajPoruke";
    }

    /**
     * metoda otvoriPoruku na trenutniId postavlja id poruke
     *
     * @param id
     * @return
     */
    public String otvoriPoruku(String id) {
        this.trenutniId = id;
        return "otvoriPoruku";
    }

    /**
     * metoda preuzmiPoruke preuzima poruke iz odabrane mape i puni listu poruka
     *
     * @throws MessagingException
     * @throws IOException
     */
    private void preuzmiPoruke() throws MessagingException, IOException {
        Message[] messages;

        // Open the INBOX folder
        folder = store.getFolder(this.odabranaMapa);
        folder.open(Folder.READ_ONLY);

        messages = folder.getMessages();

        this.poruke = new ArrayList<Poruka>();

        for (int i = 0; i < messages.length; ++i) {
            Message m = messages[i];
            Poruka p = new Poruka(m.getHeader("Message-ID")[0],
                    m.getSentDate(), m.getFrom()[0].toString(), m.getSubject(),
                    m.getContentType(), m.getSize(), 0, m.getFlags(), null, true, true, m.getContent().toString());
            //TODO potraziti broj privitaka, sad je hardkodirano da ih je 0

            this.poruke.add(p);
        }
    }

    /**
     * metoda poveziNaServer povezuje na server, pritom koristi podatke izvucene
     * u konstruktoru te puni Mape s postojecim mapama korisnika
     *
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    private void poveziNaServer() throws NoSuchProviderException, MessagingException {
        // Start the session
        java.util.Properties properties = System.getProperties();
        session = Session.getInstance(properties, null);

        // Connect to the store
        store = session.getStore("imap");
        store.connect(this.emailPosluzitelj, this.korisnickoIme, this.lozinka);

        folder = store.getDefaultFolder();
        Folder[] folderi = folder.list();

        this.mape = new HashMap<String, String>();
        for (Folder f : folderi) {
            this.mape.put(f.getName(), f.getName());
        }
    }

}
