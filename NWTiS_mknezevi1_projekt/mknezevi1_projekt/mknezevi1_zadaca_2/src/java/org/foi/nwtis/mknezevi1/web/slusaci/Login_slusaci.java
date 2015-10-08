/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web.slusaci;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.mknezevi1.konfiguracije.bp.BP_Konfiguracija;

/**
 * Provjera korisnika i pridruzene validacije za elemente prijave.
 * @author nwtis_1
 */
@ManagedBean
@RequestScoped
public class Login_slusaci {

    private String korisnickoIme;
    private String korisnickaLozinka;
    private ServletContext kontekst;

    /**
     * Creates a new instance of Login
     */
    public Login_slusaci() {
        this.korisnickoIme = "";
        this.korisnickaLozinka = "";
    }

    public String getKorisnickaLozinka() {
        return korisnickaLozinka;
    }

    public void setKorisnickaLozinka(String korisnickaLozinka) {
        this.korisnickaLozinka = korisnickaLozinka;
        System.out.println("Korisnicka lozinlka: " + korisnickaLozinka);
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
        System.out.println("Korisnicko ime: " + korisnickoIme);
    }

    /**
     * Metoda provjeraKorisnika provjerava postoji li korisnik iz zadane
     * sintakse ispravne email poruke u bazi podataka (koristi se defaultna baza
     * s lab. vjezbi g2 i postojeca tablica polaznici). Iz konteksta
     * (proslijedenog parametrom) se uzima atribut bpKonfig koji se kasnije
     * koristi za dohvacanje potrebnih podataka konfiguracije. Ako je korisnik
     * pronaden, metoda vraca OK, a inace vrata NOT_OK. U slucaju greske vraca
     * ERROR.
     *
     * @param c
     * @return
     */
    public String provjeraKorisnika(ServletContext c) {
        this.kontekst = c;
        BP_Konfiguracija bp = (BP_Konfiguracija) kontekst.getAttribute("bpKonfig");

        if (bp == null) {
            return "ERROR";
        }
        if (korisnickoIme == null || korisnickoIme.length() == 0 || korisnickaLozinka == null || korisnickaLozinka.length() == 0) {
            return "NOT_OK";
        }

        String connUrl = bp.getServer_database() + bp.getUser_database();
        String sql = "SELECT ime, prezime, lozinka, email_adresa, vrsta FROM polaznici WHERE kor_ime = '" + korisnickoIme + "'";
        try {
            Class.forName(bp.getDriver_database());
        } catch (ClassNotFoundException ex) {
            return "ERROR";
        }

        try {
            Connection conn = DriverManager.getConnection(connUrl, bp.getUser_username(), bp.getUser_password());
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.next()) {
                return "NOT_OK";
            }

            String ime = rs.getString("ime");
            String prezime = rs.getString("prezime");
            int vrsta = rs.getInt("vrsta");
            String lozinkaIzBaze = rs.getString("lozinka");

            if (korisnickaLozinka.equals(lozinkaIzBaze)) {
                return "OK";
            } else {
                return "NOT_OK";
            }

        } catch (SQLException ex) {
            return "ERROR";
        }
    }

    /**
     * Metoda validirajKorisnickoIme validira unos podataka za korisnicko ime za
     * postavke posluzitelja. Pravilo je da korisnicko ime mora imati 3 ili vise
     * znakova.
     *
     * @param facesContext
     * @param arg1
     * @param value
     * @throws ValidatorException
     */
    public void validirajKorisnickoIme(FacesContext facesContext, UIComponent arg1, Object value) throws ValidatorException {
        String kIme = ((String) value).trim();
        if (kIme.length() < 3) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Neispravan broj znakova!");
            message.setDetail(message.getSummary());
            facesContext.addMessage("korisnickoIme", message);
            throw new ValidatorException(message);
        }
    }

    /**
     * metoda validirajKorisnickuLozinku validira unos podataka za lozinku za
     * postavke posluzitelja. Pravilo je da lozinka mora imati 3 ili vise
     * znakova.
     *
     * @param facesContext
     * @param arg1
     * @param value
     * @throws ValidatorException
     */
    public void validirajKorisnickuLozinku(FacesContext facesContext, UIComponent arg1, Object value) throws ValidatorException {
        String kLoz = ((String) value).trim();
        if (kLoz.length() < 3) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Neispravan broj znakova!");
            message.setDetail(message.getSummary());
            facesContext.addMessage("lozinka", message);
            throw new ValidatorException(message);
        }
    }

}
