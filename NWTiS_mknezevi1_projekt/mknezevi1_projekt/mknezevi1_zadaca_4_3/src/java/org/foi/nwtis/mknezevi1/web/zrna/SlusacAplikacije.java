/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web.zrna;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.mknezevi1.ejb.mdb.MeteoPostar;
import org.foi.nwtis.mknezevi1.konfiguracije.Konfiguracija;
import org.foi.nwtis.mknezevi1.konfiguracije.KonfiguracijaApstraktna;

/**
 *
 * @author Matija
 */
@WebListener()
public class SlusacAplikacije implements ServletContextListener {

    public static Konfiguracija konfig = null;
    /**
     * metoda contextInitialized se aktivira na inicijalizaciju konteksta, i
     * radi pripremne radnje, preuzimanje konfiguracije i startanje dretve za
     * pozadinsku obradu poruka.
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ISPIS: Kontekst inicijaliziran");

        javax.servlet.ServletContext context = sce.getServletContext();
        String path = context.getRealPath("WEB-INF");
        String datoteka = path + java.io.File.separator + context.getInitParameter("konfiguracija");

        try {
            konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
            context.setAttribute("konfig", konfig);
            
            MeteoPostar.adresaServera = konfig.dajPostavku("adresaServera");
            MeteoPostar.port = Integer.parseInt(konfig.dajPostavku("port"));
            MeteoPostar.korisnikApp4 = konfig.dajPostavku("korisnikApp4");
            MeteoPostar.lozinkaApp4 = konfig.dajPostavku("lozinkaApp4");
            
        } catch (Exception e) {
            System.out.println("Nema konfiguracije!");
            return;
        }
    }

    /**
     * metoda contextDestroyed reagira na unistenje konteksta i radi ciscenje
     * (stopiranje rada) dretvi
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ISPIS: Kontekst unisten");

    }
}
