/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.mknezevi1.web.kontrole;

/**
 *
 * @author Matija
 */
public class Korisnik {
    
    String korisnik;
    String ime;
    String prezime;
    String sesID;
    String email;
    int vrsta;

    public Korisnik(String korisnik, String ime, String prezime, String sesID, String email, int vrsta) {
        this.korisnik = korisnik;
        this.ime = ime;
        this.prezime = prezime;
        this.sesID = sesID;
        this.email = email;
        this.vrsta = vrsta;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
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

    public String getSesID() {
        return sesID;
    }

    public void setSesID(String sesID) {
        this.sesID = sesID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getVrsta() {
        return vrsta;
    }

    public void setVrsta(int vrsta) {
        this.vrsta = vrsta;
    }
    
    
}
