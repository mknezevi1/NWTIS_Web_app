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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
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
@Path("/meteoREST")
public class MeteoRESTResourceContainer {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MeteoRESTResourceContainer
     */
    public MeteoRESTResourceContainer() {
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.mknezevi1.rest.serveri.MeteoRESTResourceContainer
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        /*Adresa a1 = new Adresa(1, "Varaždin, Pavlinska 2", new Location("46.307831", "16.338246"));
        Adresa a2 = new Adresa(2, "Čakovec, Ulica kralja Tomislava 1", new Location("46.309732", "16.436054"));*/
        Baza bp = new Baza();
        List<Adresa> lista = new ArrayList<>();
        lista = bp.dajListuAdresa();
        
        JSONObject rezultat = new JSONObject();
        try {
            JSONArray adrese = new JSONArray();
            
            for(int i=0; i<lista.size(); i++)
            {
            JSONObject jo = new JSONObject();
            jo.put("id", lista.get(i).getIdadresa());
            jo.put("adresa", lista.get(i).getAdresa());
            jo.put("latitude", lista.get(i).getGeoloc().getLatitude());
            jo.put("longitude", lista.get(i).getGeoloc().getLongitude());
            adrese.put(i, jo);
            }
            
            rezultat.put("adrese", adrese);
            
            return rezultat.toString();
        } catch (JSONException ex) {
            Logger.getLogger(MeteoRESTResourceContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * POST method for creating an instance of MeteoRESTResource
     *
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response postJson(String content) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public MeteoRESTResource getMeteoRESTResource(@PathParam("id") String id) {
        return MeteoRESTResource.getInstance(id);
    }
}
