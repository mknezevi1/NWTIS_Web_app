/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web.zrna;

import java.util.Date;
import java.util.Locale;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import org.foi.nwtis.mknezevi1.ejb.eb.Polaznici;
import org.foi.nwtis.mknezevi1.ejb.sb.PolazniciFacade;

/**
 * Zrno sluzi za registraciju (upis korisnika) u bazu podataka.
 * @author Matija
 */
@ManagedBean(name = "registracija")
@RequestScoped
public class Registracija {

    @EJB
    private PolazniciFacade polazniciFacade;
    private String korisnickoIme = "";
    private String lozinka = "";
    private String ime = "";
    private String prezime = "";
    private String email = "";

    public String registrirajKorisnika() {
        try {
            Polaznici polaznik = new Polaznici(korisnickoIme, ime, prezime, lozinka, email, 0);
            polazniciFacade.create(polaznik);
            return "OK";
        } catch (Exception e) {
            System.out.println("Krivi podaci");
            return "ERROR";
        }
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

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
