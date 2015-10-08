/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.rest.serveri;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.foi.nwtis.mknezevi1.web.Baza;
import org.foi.nwtis.mknezevi1.web.podaci.Adresa;
import org.foi.nwtis.mknezevi1.web.podaci.Location;

/**
 * REST Web Service
 *
 * @author nwtis_2
 */
public class MeteoRESTResource {

    private String id;

    /**
     * Creates a new instance of MeteoRESTResource
     */
    private MeteoRESTResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the MeteoRESTResource
     */
    public static MeteoRESTResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of MeteoRESTResource class.
        return new MeteoRESTResource(id);
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.mknezevi1.rest.serveri.MeteoRESTResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        /*List<Adresa> lista = new ArrayList<>();
        Adresa a1 = new Adresa(1, "Varaždin, Pavlinska 2", new Location("46.307831", "16.338246"));
        lista.add(a1);
        Adresa a2 = new Adresa(2, "Čakovec, Ulica kralja Tomislava 1", new Location("46.309732", "16.436054"));
        lista.add(a2);*/
        Baza bp = new Baza();
        List<Adresa> lista = new ArrayList<>();
        lista = bp.dajListuAdresa();

        int rbr = Integer.parseInt(id);

        if (rbr < lista.size()) {
            try {
                Adresa adr = lista.get(rbr);
                JSONObject jo1 = new JSONObject();
                jo1.put("id", adr.getIdadresa());
                jo1.put("address", adr.getAdresa());
                jo1.put("latitude", adr.getGeoloc().getLatitude());
                jo1.put("longitude", adr.getGeoloc().getLongitude());

                return jo1.toString();
            } catch (JSONException ex) {
                Logger.getLogger(MeteoRESTResourceContainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "null";
    }

    /**
     * PUT method for updating or creating an instance of MeteoRESTResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource MeteoRESTResource
     */
    @DELETE
    public void delete() {
    }
}
