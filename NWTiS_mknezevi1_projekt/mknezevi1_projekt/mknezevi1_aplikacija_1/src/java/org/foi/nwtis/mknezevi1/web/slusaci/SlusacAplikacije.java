/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web.slusaci;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.mknezevi1.konfiguracije.Konfiguracija;
import org.foi.nwtis.mknezevi1.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.mknezevi1.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.mknezevi1.web.Baza;
import org.foi.nwtis.mknezevi1.web.PozadinskaDretva;
import org.foi.nwtis.mknezevi1.web.Server;
import org.foi.nwtis.mknezevi1.web.TestZaREST;

/**
 * Web application lifecycle listener.
 *
 * @author nwtis_1
 */
public class SlusacAplikacije implements ServletContextListener {

    PozadinskaDretva dretva;
    Server socketServer;
    public static BP_Konfiguracija bpKonfig = null;
    public static Konfiguracija konfig = null;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Kontekst inicijaliziran");

        javax.servlet.ServletContext context = sce.getServletContext();
        String path = context.getRealPath("WEB-INF");
        String datoteka = path + java.io.File.separator + context.getInitParameter("konfiguracija");
        
        BP_Konfiguracija bpKonfig = new BP_Konfiguracija(datoteka);

        try {
            konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
            context.setAttribute("konfig", konfig);
            context.setAttribute("BP_Konfig", bpKonfig);
            
            Baza.setKontekst(context);
            socketServer = new Server(context);
            socketServer.start();
            
            dretva = new PozadinskaDretva(context);
            dretva.start();
        } catch (Exception e) {
            System.out.println("Nema konfiguracije!");
            return;
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ISPIS: Kontekst unisten");

        if (dretva != null && dretva.isAlive()) {
            dretva.interrupt();
        }
        
        if (socketServer != null && socketServer.isAlive()) {
            socketServer.interrupt();
        }
    }
}
