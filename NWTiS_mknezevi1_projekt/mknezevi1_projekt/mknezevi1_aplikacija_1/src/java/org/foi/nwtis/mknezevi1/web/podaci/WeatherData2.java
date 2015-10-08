/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.mknezevi1.web.podaci;

/**
 *Klasa koja je umanjena verzija klase WeatherData, odnosno sadrzi u ovoj zadaci trazenih
 * 5 varijabli meteo podataka
 * @author Matija
 */
public class WeatherData2 {
    Integer idmeteo;
    Float temperatura;
    Float vlaga;
    Float vjetar;
    Float tlak;

    public WeatherData2(Integer idmeteo, Float temperatura, Float vlaga, Float vjetar, Float tlak) {
        this.idmeteo = idmeteo;
        this.temperatura = temperatura;
        this.vlaga = vlaga;
        this.vjetar = vjetar;
        this.tlak = tlak;
    }
    
    public Integer getIdmeteo() {
        return idmeteo;
    }

    public void setIdmeteo(Integer idmeteo) {
        this.idmeteo = idmeteo;
    }

    public Float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public Float getVlaga() {
        return vlaga;
    }

    public void setVlaga(Float vlaga) {
        this.vlaga = vlaga;
    }

    public Float getVjetar() {
        return vjetar;
    }

    public void setVjetar(Float vjetar) {
        this.vjetar = vjetar;
    }

    public Float getTlak() {
        return tlak;
    }

    public void setTlak(Float tlak) {
        this.tlak = tlak;
    }
    
    
}
