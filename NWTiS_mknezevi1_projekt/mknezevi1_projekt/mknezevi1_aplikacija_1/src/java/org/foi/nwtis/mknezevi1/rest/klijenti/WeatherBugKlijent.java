/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.mknezevi1.rest.klijenti;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.foi.nwtis.mknezevi1.web.podaci.WeatherData;

/**
 *
 * @author nwtis_2
 */
public class WeatherBugKlijent {
    String customerKey;
    String secretKey;
    WBRESTHelper helper;
    Client client;

    public WeatherBugKlijent(String customerKey, String secretKey) {
        this.customerKey = customerKey;
        this.secretKey = secretKey;
        helper = new WBRESTHelper(customerKey, secretKey);
        helper.autenticiraj();
        client = ClientBuilder.newClient();
    }
    
    /**
     * metoda na temelju dobivenih parametara geolokacije tocke (latitude i longitude)
     * vraca trenutne meteo podatke za tu tocku
     * @param latitude
     * @param longitude
     * @return 
     */
    public WeatherData getRealTimeWeather(String latitude, String longitude){
        WebTarget webResource = client.target(WBRESTHelper.getWB_BASE_URI())
                .path("data/observations/v1/current");
        webResource = webResource.queryParam("location",latitude+","+longitude);
        webResource = webResource.queryParam("locationtype","latitudelongitude");
        webResource = webResource.queryParam("units","metric");
        webResource = webResource.queryParam("cultureinfo","en-en");
        webResource = webResource.queryParam("verbose","true");
        webResource = webResource.queryParam("access_token",helper.getAutentikacija().getAccess_token().getToken());
            
        try {
            String odgovor = webResource.request(MediaType.APPLICATION_JSON).get(String.class);
            JSONObject jo = new JSONObject(odgovor);
            WeatherData wd = new WeatherData();
            wd.setTemperature(jo.get("temperature").toString().compareTo("null") == 0 ?
                    null : ((Double)jo.getDouble("temperature")).floatValue());
            wd.setPressureSeaLevel(jo.get("pressureSeaLevel").toString().compareTo("null") == 0 ?
                    null : ((Double)jo.getDouble("pressureSeaLevel")).floatValue());
            wd.setHumidity(jo.get("humidity").toString().compareTo("null") == 0 ?
                    null : ((Double)jo.getDouble("humidity")).floatValue());
            wd.setWindSpeed(jo.get("windSpeed").toString().compareTo("null") == 0 ?
                    null : ((Double)jo.getDouble("windSpeed")).floatValue());
            
            //ako je sve ok, vrati wd
            return wd;
        } catch (JSONException ex) {
            Logger.getLogger(WeatherBugKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        //ako nije sve ok, vrati null
        return null;
    }
    
}
