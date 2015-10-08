/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.mknezevi1.web.zrna;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.xml.ws.WebServiceRef;
import org.foi.nwtis.mknezevi1.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.mknezevi1.ws.serveri.Adresa;
import org.foi.nwtis.mknezevi1.ws.serveri.GeoMeteoWS;
import org.foi.nwtis.mknezevi1.ws.serveri.GeoMeteoWS_Service;
import org.foi.nwtis.mknezevi1.ws.serveri.WeatherData;
import org.foi.nwtis.mknezevi1.ws.serveri.WeatherData2;

/**
 * Biljezi odabranu adresu te poziva popratne operacije dohvacanja trenutnih meteo podataka za tu adresu, 
 * odnosno meteo podataka zapisanih u bazu podataka.
 * @author Matija
 */
@ManagedBean(name = "odabir")
@RequestScoped
public class OdabirAdresa {
    //@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8084/mknezevi1_zadaca_3_1/GeoMeteoWS.wsdl")
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8084/mknezevi1_aplikacija_1/GeoMeteoWS.wsdl")
    private GeoMeteoWS_Service service;
    List<Adresa> lista = new ArrayList<>();
    String odabranaAdresa;
    WeatherData podaci;
    List<WeatherData2> podaci2 = new ArrayList<>();
    
    /**
     * Creates a new instance of OdabirAdresa
     */
    public OdabirAdresa() {
        setLista(MeteoWSKlijent.dajSveAdrese());
    }

    public List<Adresa> getLista() {
        return lista;
    }

    public void setLista(List<Adresa> lista) {
        this.lista = lista;
    }

    public String getOdabranaAdresa() {
        return odabranaAdresa;
    }

    public void setOdabranaAdresa(String odabranaAdresa) {
        this.odabranaAdresa = odabranaAdresa;
    }

    public WeatherData getPodaci() {
        return podaci;
    }

    public void setPodaci(WeatherData podaci) {
        this.podaci = podaci;
    }

    public List<WeatherData2> getPodaci2() {
        return podaci2;
    }

    public void setPodaci2(List<WeatherData2> podaci2) {
        this.podaci2 = podaci2;
    }

    public String dajMeteoPodatke(String adresa){
        setOdabranaAdresa(adresa);
        setPodaci(MeteoWSKlijent.dajVazeceMeteoPodatkeZaAdresu(adresa));
        return "OK";
    }
    
    public String dajSpremljeneMeteoPodatke(String adresa){
        setOdabranaAdresa(adresa);
        setPodaci2(MeteoWSKlijent.dajSpremljeneMeteoPodatkeZaAdresu(adresa));
        return "OK";
    }
        
}
