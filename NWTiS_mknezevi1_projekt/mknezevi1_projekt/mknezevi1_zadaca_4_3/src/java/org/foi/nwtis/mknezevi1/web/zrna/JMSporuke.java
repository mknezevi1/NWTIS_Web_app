/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web.zrna;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import org.foi.nwtis.mknezevi1.ejb.mdb.MeteoPostar;

/**
 * Zrno sluzi za operacije vezane uz JMS poruke koje pristizu.
 * @author Matija
 */
@ManagedBean(name = "JMSporuke")
@RequestScoped
public class JMSporuke {

    private List<String> listaPoruka = new ArrayList<String>();

    public JMSporuke() {
    }

    void preuzmiPoruke() {
        listaPoruka = MeteoPostar.poruke;
    }

    public void obrisi(String porukaZaBrisanje) {
        MeteoPostar.poruke.remove(porukaZaBrisanje);
        preuzmiPoruke();
    }

    public void obrisiSve() {
        try {
            MeteoPostar.poruke.clear();
            preuzmiPoruke();
        } catch (Exception e) {

        }
    }

    public List<String> getListaPoruka() {
        preuzmiPoruke();
        return listaPoruka;
    }

    public void setListaPoruka(List<String> listaPoruka) {
        this.listaPoruka = listaPoruka;
    }

}
