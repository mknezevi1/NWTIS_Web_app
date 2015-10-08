/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.mknezevi1.ejb.sb;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.foi.nwtis.mknezevi1.rest.klijenti.GoogleMapsKlijent;
import org.foi.nwtis.mknezevi1.rest.klijenti.WeatherBugKlijent;
import org.foi.nwtis.mknezevi1.web.podaci.Location;
import org.foi.nwtis.mknezevi1.web.podaci.WeatherData;

/**
 *
 * @author nwtis_2
 */
@Stateless
@LocalBean
public class MeteoAdresniKlijent {

    private String cKey = "";
    private String sKey = "";

    public void postaviKorisnickePodatke(final String cKey, final String sKey) {
            this.cKey = cKey;
            this.sKey = sKey;
    }

    public WeatherData dajVazeceMeteoPodatke(final String adresa) {
        GoogleMapsKlijent gmk = new GoogleMapsKlijent();
        Location loc = gmk.getGeoLocation(adresa);
        WeatherBugKlijent wbk = new WeatherBugKlijent(cKey, sKey);
        WeatherData wd = wbk.getRealTimeWeather(loc.getLatitude(), loc.getLongitude());
        return wd;
    }

     public WeatherData dajVazeceMeteoPodatke(final String latitude, final String longitude) {
        WeatherBugKlijent wbk = new WeatherBugKlijent(cKey, sKey);
        WeatherData wd = wbk.getRealTimeWeather(latitude, longitude);
        return wd;
    }
    
    public Location dajLokaciju(final String adresa) {
        GoogleMapsKlijent gmk = new GoogleMapsKlijent();
        Location loc = gmk.getGeoLocation(adresa);
        return loc;
    }

   

    
}
