/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.ejb.sb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.xml.ws.WebServiceRef;
import org.foi.nwtis.mknezevi1.ws.serveri.Adresa;
import org.foi.nwtis.mknezevi1.ws.serveri.GeoMeteoWS_Service;
import org.foi.nwtis.mknezevi1.ws.serveri.WeatherData;

/**
 *
 * @author nwtis_2
 */
@Stateful
@LocalBean
public class MeteoPrognosticar {

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/localhost_8084/mknezevi1_aplikacija_1/GeoMeteoWS.wsdl")
    private GeoMeteoWS_Service service;
    List<String> adrese;
    List<WeatherData> meteoPodaci;
    private boolean prviPuta = true;

    public List<String> getAdrese() {
        if (prviPuta) {
            List<Adresa> listaAdresa = dajSveAdrese();
            adrese = new ArrayList<>();
            for (Adresa adr : listaAdresa) {
                adrese.add(adr.getAdresa());
            }
            prviPuta = false;
        }
        return adrese;
    }

    public void setAdrese(List<String> adrese) {
        this.adrese = adrese;
    }

    public List<WeatherData> getMeteoPodaci() {
        return meteoPodaci;
    }

    public void setMeteoPodaci(List<WeatherData> meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }

    public List<String> dajAdrese() {
        return getAdrese();
    }

    private java.util.List<org.foi.nwtis.mknezevi1.ws.serveri.Adresa> dajSveAdrese() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.mknezevi1.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveAdrese();
    }

}
