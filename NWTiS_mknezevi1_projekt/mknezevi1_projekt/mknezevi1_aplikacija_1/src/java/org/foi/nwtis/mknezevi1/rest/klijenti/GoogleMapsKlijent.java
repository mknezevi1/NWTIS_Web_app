/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.mknezevi1.rest.klijenti;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.foi.nwtis.mknezevi1.web.podaci.Location;
import org.foi.nwtis.mknezevi1.web.podaci.WeatherData;

/**
 *
 * @author nwtis_2
 */
public class GoogleMapsKlijent {
    GMRESTHelper helper;
    Client client;

    public GoogleMapsKlijent() {
        this.client = ClientBuilder.newClient();
    }
 
    /**
     * Metoda na temelju parametra adrese vraca geolokaciju te tocke (lat i lng)
     * @param adresa
     * @return 
     */
    public Location getGeoLocation(String adresa){
        try {
            WebTarget webResource = client.target(GMRESTHelper.getGM_BASE_URI())
                    .path("maps/api/geocode/json");
            webResource = webResource.queryParam("address",URLEncoder.encode(adresa, "UTF-8"));
            webResource = webResource.queryParam("sensor","false");
            
            String odgovor = webResource.request(MediaType.APPLICATION_JSON).get(String.class);
            try {
                JSONObject jo = new JSONObject(odgovor);
                JSONObject obj = jo.getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONObject("location");
                
                Location loc = new Location(obj.getString("lat"),obj.getString("lng"));
                
                return loc;
                
            } catch (JSONException ex) {
                Logger.getLogger(WeatherBugKlijent.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GoogleMapsKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
