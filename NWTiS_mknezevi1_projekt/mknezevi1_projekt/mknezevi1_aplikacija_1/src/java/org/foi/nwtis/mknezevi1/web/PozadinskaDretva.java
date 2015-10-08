/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.foi.nwtis.mknezevi1.konfiguracije.Konfiguracija;
import org.foi.nwtis.mknezevi1.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.mknezevi1.rest.klijenti.GoogleMapsKlijent;
import org.foi.nwtis.mknezevi1.rest.klijenti.WeatherBugKlijent;
import static org.foi.nwtis.mknezevi1.web.Baza.kontekst;
import org.foi.nwtis.mknezevi1.web.podaci.Adresa;
import org.foi.nwtis.mknezevi1.web.podaci.Location;
import org.foi.nwtis.mknezevi1.web.podaci.WeatherData;

/**
 * Ova klasa predstavlja pozadinsku dretvu ciji interval je u konfiguraciji. U
 * tom intervalu za adrese iz baze podataka dohvaca trenutne meteo podatke te ih
 * zapisuje u bazu podataka.
 *
 * @author Matija
 */
public class PozadinskaDretva extends Thread {

    Konfiguracija konfig;
    private ServletContext kontekst;
    private long interval;
    long vrijemePocetak = 0;
    long vrijemeTrajanje = 0;
    public static boolean ziva = true;
    public static boolean prikupljajPodatke = true;
    Baza bp;
    List<Adresa> listaAdresa;
    String cKey = "";
    String sKey = "";
    WeatherBugKlijent wbk;

    public PozadinskaDretva(ServletContext c) {
        super();
        this.kontekst = c;
        konfig = (Konfiguracija) c.getAttribute("konfig");
        cKey = konfig.dajPostavku("cKey");
        sKey = konfig.dajPostavku("sKey");
        interval = Integer.parseInt(konfig.dajPostavku("interval"));

        bp = new Baza();
        listaAdresa = new ArrayList<>();
        wbk = new WeatherBugKlijent(cKey, sKey);
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

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        while (ziva) {
            vrijemePocetak = (new Date()).getTime();

            if (prikupljajPodatke) {
                //System.out.println("vrijeme:" + new Date() + ": upis meteo podataka u bazu");

                listaAdresa = bp.dajListuAdresa();
                for (Integer i = 0; i < listaAdresa.size(); i++) {
                    try {
                        WeatherData wd = wbk.getRealTimeWeather(listaAdresa.get(i).getGeoloc().getLatitude(), listaAdresa.get(i).getGeoloc().getLongitude());
                        
                        bp.upisiMeteoPodatke(listaAdresa.get(i).getAdresa(), wd.getTemperature(), wd.getPressureSeaLevel(), wd.getHumidity(), wd.getWindSpeed()); 
                    
                    } catch (Exception e) {

                    }
                }
            }

            vrijemeTrajanje = (new Date()).getTime() - vrijemePocetak;
            try {
                sleep((this.interval * 1000) - vrijemeTrajanje);
                System.out.println("vrijeme:" + new Date() + ": pokrece se interval upisa meteo podataka u bazu");
                
                /*if (this.interval > vrijemeTrajanje) {
                    //sleep((this.interval - vrijemeTrajanje) * 1000);
                    System.out.println("vrijeme:" + new Date() + ": pokrece se upis meteo podataka u bazu");
                } else { //else je ovdje privremeno i stiti server dok se ne rijesi problem povremenog duzeg trajanja od intervala
                    sleep(this.interval * 1000);
                }*/

            } catch (InterruptedException ex) {
                Logger.getLogger(PozadinskaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
