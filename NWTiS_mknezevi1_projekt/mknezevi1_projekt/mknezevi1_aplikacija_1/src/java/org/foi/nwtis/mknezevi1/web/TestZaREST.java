/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.mknezevi1.rest.klijenti.GoogleMapsKlijent;
import org.foi.nwtis.mknezevi1.rest.klijenti.WeatherBugKlijent;
import org.foi.nwtis.mknezevi1.web.podaci.Adresa;
import org.foi.nwtis.mknezevi1.web.podaci.Location;
import org.foi.nwtis.mknezevi1.web.podaci.WeatherData;
import org.foi.nwtis.mknezevi1.web.podaci.WeatherData2;
import org.foi.nwtis.mknezevi1.web.slusaci.SlusacAplikacije;

/**
 * Klasa koja je napravljena za testiranje REST servisa. Ovisno o gumbu koji je
 * pritisnut u index, obavlja odredenu operaciju: prikaz geo podataka, prikaz
 * meteo podataka ili spremanje adrese u bazu podataka.
 *
 * @author nwtis_2
 */
public class TestZaREST extends HttpServlet {

    String adresa;
    String lat;
    String lon;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestZaREST</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestZaREST at " + request.getContextPath() + "</h1>");

            //String cKey = "SEL78ZRacrxzaG4vgEWbC8bTNV9qegCj";
            //String sKey = "u7KwZGgYG7m1SlGY";
            String cKey = SlusacAplikacije.konfig.dajPostavku("cKey");
            String sKey = SlusacAplikacije.konfig.dajPostavku("sKey");

            lat = "46.390372";
            lon = "16.436053";
            WeatherBugKlijent wbk = new WeatherBugKlijent(cKey, sKey);
            Baza bp = new Baza();

            if (request.getParameter("gumb1") != null) {
                out.println("DOHVACANJE METEO PODATAKA");
                out.println("</br>");

                adresa = request.getParameter("adresa");
                out.println("Adresa: " + adresa);
                out.println("</br>");
                if (adresa != null && adresa.length() > 0) {
                    GoogleMapsKlijent gmk = new GoogleMapsKlijent();
                    Location location = gmk.getGeoLocation(adresa);
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                }
                WeatherData wd = wbk.getRealTimeWeather(lat, lon);
                out.println("Temp: " + wd.getTemperature());
                out.println("Tlak: " + wd.getPressureSeaLevel());
                out.println("Vlaga: " + wd.getHumidity());
                out.println("Vjetar: " + wd.getWindSpeed());

                out.println("</br>");
                out.print("<a href=\"http://localhost:8084/mknezevi1_aplikacija_1\">index.jsp</a>");
                //SAMO TESTIRANJE
                /*Baza bp = new Baza();
                 out.println("Operacija s BP: " + bp.upisiMeteoPodatke(adresa, wd.getTemperature(), wd.getPressureSeaLevel(), wd.getHumidity(), wd.getWindSpeed()));
                 Baza bp = new Baza();
                 out.println("Velicina pristigle liste: " + bp.dajListuMeteoPodataka(adresa).size());*/
                //SAMO TESTIRANJE

                //SAMO TESTIRANJE
                /*Socket veza = null;
                 veza = new Socket("127.0.0.1", 8000);
                 InputStream is = veza.getInputStream();
                 OutputStream os = veza.getOutputStream();
                 //os.write("USER admin; PASSWD 123456; ADD \"Varazdin, Pavlinska 2\";".getBytes());
                 //os.write("USER admin; PASSWD 123456; PAUSE;".getBytes());
                 //os.write("USER admin; PASSWD 123456; ADD mata; NEWPASSWD loza2;".getBytes());
                 //os.write("USER pero; GET \"Zagreb, Maksimirska 2\";".getBytes());
                 os.flush();
                 veza.shutdownOutput();

                 StringBuilder odgovor = new StringBuilder();
                 while (true) {
                 int znak = is.read();
                 if (znak == -1) {
                 break;
                 }
                 odgovor.append((char) znak);
                 }
                 System.out.println("odgovor: " + odgovor);
                 is.close();
                 os.close();
                 veza.close();*/
                //SAMO TESTIRANJE
            } else if (request.getParameter("gumb2") != null) {
                out.println("DOHVACANJE GEO PODATAKA");
                out.println("</br>");

                adresa = request.getParameter("adresa");
                out.println("Adresa: " + adresa);
                out.println("</br>");
                GoogleMapsKlijent gmk = new GoogleMapsKlijent();
                Location location = gmk.getGeoLocation(adresa);
                lat = location.getLatitude();
                lon = location.getLongitude();
                out.println("lat: " + lat);
                out.println("lon: " + lon);

                out.println("</br>");
                out.print("<a href=\"http://localhost:8084/mknezevi1_aplikacija_1\">index.jsp</a>");
            } else if (request.getParameter("gumb3") != null) {
                out.println("SPREMANJE U BAZU PODATAKA");
                out.println("</br>");

                adresa = request.getParameter("adresa");
                out.println("Adresa: " + adresa);
                out.println("</br>");

                GoogleMapsKlijent gmk = new GoogleMapsKlijent();
                Location location = gmk.getGeoLocation(adresa);
                lat = location.getLatitude();
                lon = location.getLongitude();

                out.println("Operacija s BP: " + bp.upisiAdresu(adresa, lat, lon));

                out.println("</br>");
                out.print("<a href=\"http://localhost:8084/mknezevi1_aplikacija_1\">index.jsp</a>");
            } else if (request.getParameter("gumb4") != null) {
                out.println("POPIS ADRESA");
                out.println("</br>");

                List<Adresa> listaAdresa;
                listaAdresa = new ArrayList<>();
                listaAdresa = bp.dajListuAdresa();
                out.println("</br>");
                out.println("</br>");
                out.println("ADRESE:");
                out.println("<table border=1 style=\"width:300px\">");
                for (int i = 0; i < listaAdresa.size(); i++) {
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(listaAdresa.get(i).getAdresa());
                    out.println("</td>");

                    out.println("<td>");
                    out.println("gumb");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");

                List<WeatherData2> listaMeteoPodataka;
                listaMeteoPodataka = new ArrayList<>();
                listaMeteoPodataka = bp.dajListuMeteoPodataka("Karlovac, Miroslava Krleze 1c");
                out.println("</br>");
                out.println("</br>");
                out.println("METEOPODACI:");
                out.println("<table border=1 style=\"width:300px\">");
                for (int i = 0; i < listaMeteoPodataka.size(); i++) {
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(listaMeteoPodataka.get(i).getIdmeteo());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(listaMeteoPodataka.get(i).getTemperatura());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(listaMeteoPodataka.get(i).getTlak());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(listaMeteoPodataka.get(i).getVjetar());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(listaMeteoPodataka.get(i).getVlaga());
                    out.println("</td>");

                    out.println("</tr>");
                }
                out.println("</table>");

                out.println("</br>");
                out.print("<a href=\"http://localhost:8084/mknezevi1_aplikacija_1\">index.jsp</a>");
            } else if (request.getParameter("gumb5") != null) {
                out.println("NAREDBA SOCKET SERVERU");
                out.println("</br>");

                adresa = request.getParameter("adresa");
                out.println("Naredba: " + adresa);
                out.println("</br>");

                String adresaServera = SlusacAplikacije.konfig.dajPostavku("adresaServera");
                Integer port = Integer.parseInt(SlusacAplikacije.konfig.dajPostavku("port"));

                Socket veza = null;
                veza = new Socket(adresaServera, port);
                InputStream is = veza.getInputStream();
                OutputStream os = veza.getOutputStream();
                os.write(adresa.getBytes());
                //os.write("USER admin; PASSWD 123456; ADD \"Varazdin, Pavlinska 2\";".getBytes());
                //os.write("USER admin; PASSWD 123456; PAUSE;".getBytes());
                //os.write("USER admin; PASSWD 123456; ADD mata; NEWPASSWD loza2;".getBytes());
                //os.write("USER pero; GET \"Zagreb, Maksimirska 2\";".getBytes());
                os.flush();
                veza.shutdownOutput();

                StringBuilder odgovor = new StringBuilder();
                while (true) {
                    int znak = is.read();
                    if (znak == -1) {
                        break;
                    }
                    odgovor.append((char) znak);
                }
                System.out.println("odgovor: " + odgovor);
                out.println("odgovor: " + odgovor);
                is.close();
                os.close();
                veza.close();

                out.println("</br>");
                out.print("<a href=\"http://localhost:8084/mknezevi1_aplikacija_1\">index.jsp</a>");
            }

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
