/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web.zrna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.foi.nwtis.mknezevi1.ejb.sb.MeteoOsvjezivac;
import org.foi.nwtis.mknezevi1.ejb.sb.MeteoPrognosticar;
import org.foi.nwtis.mknezevi1.web.podaci.WeatherData;

/**
 * Zrno sluzi za operacije oko adresa (npr. dodavanje nove adrese u sustav).
 * @author nwtis_2
 */
@ManagedBean
@SessionScoped
public class MeteoPrognoza {

    @EJB
    private MeteoOsvjezivac meteoOsvjezivac;
    @EJB
    private MeteoPrognosticar meteoPrognosticar;

    private String novaAdresa;
    String odabranaAdresa;
    private Map<String, String> listaAdresa;
    List<String> adrese;
    List<WeatherData> meteoPodaci;
    boolean adresaPostoji = false;

    /**
     * Creates a new instance of MeteoPrognoza
     */
    public MeteoPrognoza() {
    }

    public String getOdabranaAdresa() {
        return odabranaAdresa;
    }

    public void setOdabranaAdresa(String odabranaAdresa) {
        this.odabranaAdresa = odabranaAdresa;
    }

    public List<String> getAdrese() {
        adrese = meteoPrognosticar.dajAdrese();
        return adrese;
    }

    public void setAdrese(List<String> adrese) {
        this.adrese = adrese;
    }

    public String getNovaAdresa() {
        return novaAdresa;
    }

    public void setNovaAdresa(String novaAdresa) {
        this.novaAdresa = novaAdresa;
    }

    public Map<String, String> getListaAdresa() {
        listaAdresa = new HashMap<>();
        int i = 0;
        getAdrese();
        if (adrese != null) {
            for (String adr : adrese) {
                listaAdresa.put(adr, Integer.toString(i));
                i++;
            }
        }
        return listaAdresa;
    }

    public void setListaAdresa(Map<String, String> listaAdresa) {
        this.listaAdresa = listaAdresa;
    }

    public List<WeatherData> getMeteoPodaci() {
        List<org.foi.nwtis.mknezevi1.ws.serveri.WeatherData> mp = meteoPrognosticar.getMeteoPodaci();
        meteoPodaci = new ArrayList<>();
        for (org.foi.nwtis.mknezevi1.ws.serveri.WeatherData wd : mp) {
            WeatherData wd1 = new WeatherData();
            wd1.setTemperature(wd.getTemperature());
            wd1.setHumidity(wd.getHumidity());
            wd1.setWindSpeed(wd.getWindSpeed());
            //wd1.setPressureSeaLevel(wd.getPressureSeaLevel());
            meteoPodaci.add(wd1);
        }
        return meteoPodaci;
    }

    public void setMeteoPodaci(List<WeatherData> meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }

    public String dodajAdresu() {
        adresaPostoji = false;
        adrese = meteoPrognosticar.dajAdrese();
        if (adrese == null) {
            adrese = new ArrayList<>();
        } else {
            for (String adr : adrese) {
                if (adr.equals(novaAdresa)) {
                    System.out.println("POSTOJI VEC OVA ADRESA");
                    adresaPostoji = true;
                    break;
                }
            }
        }
        if (adresaPostoji == false) {
            adrese.add(novaAdresa);
            meteoPrognosticar.setAdrese(adrese);
            String poruka = novaAdresa;
            meteoOsvjezivac.sendJMSMessageToNWTiS_vjezba_12(poruka);
            System.out.println("MK: PORUKA ODASLANA");
        }
        return "DODANO";
    }

    public String obrisiAdresu() {
        for (Iterator i = listaAdresa.keySet().iterator(); i.hasNext();) {
            String k = (String) i.next();
            String v = listaAdresa.get(k);
            if (odabranaAdresa.compareTo(v) == 0) {
                adrese.remove(k);
                listaAdresa.remove(k);
                meteoPrognosticar.setAdrese(adrese);
                break;
            }
        }
        return "OBRISANO";
    }

    public String preuzmiMeteoPodatke() {
        return "PREUZETO";
    }

}
