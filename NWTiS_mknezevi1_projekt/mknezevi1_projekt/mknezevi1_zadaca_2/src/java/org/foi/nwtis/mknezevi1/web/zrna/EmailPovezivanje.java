/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web.zrna;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.foi.nwtis.mknezevi1.web.slusaci.SlusacAplikacije;

/**
 *
 * @author nwtis_2
 */
@ManagedBean
@SessionScoped
public class EmailPovezivanje {

    private String emailPosluzitelj = SlusacAplikacije.konfig.dajPostavku("adresaServera");
    private String korisnickoIme = SlusacAplikacije.konfig.dajPostavku("korisnickoIme");
    private String lozinka = SlusacAplikacije.konfig.dajPostavku("123456");
    
    //private String emailPosluzitelj="127.0.0.1";
    //private String korisnickoIme="servis@nwtis.nastava.foi.hr";
    
    /**
     * Creates a new instance of EmailPovezivanje
     */
    public EmailPovezivanje() {
    }

    public String getEmailPosluzitelj() {
        return emailPosluzitelj;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setEmailPosluzitelj(String emailPosluzitelj) {
        this.emailPosluzitelj = emailPosluzitelj;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String saljiPoruku() {
        return "salji";
    }

    public String citajPoruke() {
        return "citaj";
    }
}
