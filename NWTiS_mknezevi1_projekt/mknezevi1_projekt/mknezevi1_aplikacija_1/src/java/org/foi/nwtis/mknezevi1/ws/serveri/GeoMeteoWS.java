/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.ws.serveri;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.foi.nwtis.mknezevi1.rest.klijenti.GoogleMapsKlijent;
import org.foi.nwtis.mknezevi1.rest.klijenti.WeatherBugKlijent;
import org.foi.nwtis.mknezevi1.web.Baza;
import org.foi.nwtis.mknezevi1.web.podaci.Adresa;
import org.foi.nwtis.mknezevi1.web.podaci.Location;
import org.foi.nwtis.mknezevi1.web.podaci.WeatherData;
import org.foi.nwtis.mknezevi1.web.podaci.WeatherData2;
import org.foi.nwtis.mknezevi1.web.slusaci.SlusacAplikacije;

/**
 *
 * @author nwtis_2
 */
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajSveAdrese")
    public List<Adresa> dajSveAdrese() {
        /*List<Adresa> lista = new ArrayList<>();
         Adresa a1 = new Adresa(1, "Varaždin, Pavlinska 2", new Location("46.307831", "16.338246"));
         lista.add(a1);
         Adresa a2 = new Adresa(2, "Čakovec, Ulica kralja Tomislava 1", new Location("46.309732", "16.436054"));
         lista.add(a2);*/
        Baza bp = new Baza();
        List<Adresa> lista = new ArrayList<>();
        lista = bp.dajListuAdresa();

        return lista;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajVazeceMeteoPodatkeZaAdresu")
    public WeatherData dajVazeceMeteoPodatkeZaAdresu(@WebParam(name = "adresa") final String adresa) {
        //String cKey = "SEL78ZRacrxzaG4vgEWbC8bTNV9qegCj";
        //String sKey = "u7KwZGgYG7m1SlGY";
        
        String cKey = SlusacAplikacije.konfig.dajPostavku("cKey");
        String sKey = SlusacAplikacije.konfig.dajPostavku("sKey");
        
        if (adresa != null && adresa.length() > 0) {
            GoogleMapsKlijent gmk = new GoogleMapsKlijent();
            Location location = gmk.getGeoLocation(adresa);
            WeatherBugKlijent wbk = new WeatherBugKlijent(cKey, sKey);
            WeatherData wd = wbk.getRealTimeWeather(location.getLatitude(), location.getLongitude());
            return wd;
        }
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajSpremljeneMeteoPodatkeZaAdresu")
    public List<WeatherData2> dajSpremljeneMeteoPodatkeZaAdresu(@WebParam(name = "adresa") final String adresa) {
        Baza bp = new Baza();
        List<WeatherData2> lista = new ArrayList<>();
        lista = bp.dajListuMeteoPodataka(adresa);

        return lista;
    }
}
