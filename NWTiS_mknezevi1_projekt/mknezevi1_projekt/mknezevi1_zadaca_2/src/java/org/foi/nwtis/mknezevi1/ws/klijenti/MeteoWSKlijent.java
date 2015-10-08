/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.mknezevi1.ws.klijenti;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.Dispatch;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Service;
import java.io.StringReader;
import org.foi.nwtis.mknezevi1.ws.serveri.WeatherData;

/**
 *U klasi se pozivaju operacije web servisa.
 * @author nwtis_2
 */
public class MeteoWSKlijent {

    public static java.util.List<org.foi.nwtis.mknezevi1.ws.serveri.Adresa> dajSveAdrese() {
        org.foi.nwtis.mknezevi1.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.mknezevi1.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.mknezevi1.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveAdrese();
    }
    
    public static java.util.List<org.foi.nwtis.mknezevi1.ws.serveri.WeatherData2> dajSpremljeneMeteoPodatkeZaAdresu(java.lang.String adresa) {
        org.foi.nwtis.mknezevi1.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.mknezevi1.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.mknezevi1.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSpremljeneMeteoPodatkeZaAdresu(adresa);
    }

    public static WeatherData dajVazeceMeteoPodatkeZaAdresu(java.lang.String adresa) {
        org.foi.nwtis.mknezevi1.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.mknezevi1.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.mknezevi1.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajVazeceMeteoPodatkeZaAdresu(adresa);
    }

}
