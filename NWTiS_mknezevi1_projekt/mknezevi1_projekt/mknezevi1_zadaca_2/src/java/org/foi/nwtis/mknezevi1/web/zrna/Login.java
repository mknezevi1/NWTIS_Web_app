/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web.zrna;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.mknezevi1.ejb.eb.Polaznici;
import org.foi.nwtis.mknezevi1.ejb.sb.PolazniciFacade;
import org.foi.nwtis.mknezevi1.web.kontrole.Korisnik;

/**
 * Zrno sluzi za login korisnika. Ukoliko je provjera unesenih podataka prosla uspjesno, objekt klase
 * korisnik se stavlja u sesiju te moze pristupiti zasticenim djelovima aplikacije.
 * @author Matija
 */
@ManagedBean(name = "login")
@RequestScoped
public class Login {

    @EJB
    private PolazniciFacade polazniciFacade;

    private String korisnickoIme;
    private String korisnickaLozinka;

    public Login() {
        this.korisnickoIme = "";
        this.korisnickaLozinka = "";
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getKorisnickaLozinka() {
        return korisnickaLozinka;
    }

    public void setKorisnickaLozinka(String korisnickaLozinka) {
        this.korisnickaLozinka = korisnickaLozinka;
    }

    public String provjeraKorisnika() {
        Polaznici polaznik = null;
        try {
            polaznik = polazniciFacade.find(korisnickoIme);
            if (polaznik.getLozinka().equals(korisnickaLozinka)) {
                HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                Korisnik korisnik = new Korisnik(korisnickoIme, polaznik.getPrezime(), polaznik.getIme(), sesija.getId(), polaznik.getEmailAdresa(), polaznik.getVrsta());
                sesija.setAttribute("korisnik", korisnik); //dodavanje atributa u sesiju (u SlusacuSesije se okida metoda za to)
                System.out.println("Login prosao");
                return "OK";
            } else {
                //System.out.println("Login nije prosao, kriva lozinka");
                return "ERROR";
            }
        } catch (Exception e) {
            //System.out.println("Login nije prosao, ne postoji korisnik");
            return "ERROR";
        }   
    }

    public void odjava() {
        HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(sesija!=null){
            sesija.invalidate();
            System.out.println("Korisnik odjavljen");
        }
    }
}
