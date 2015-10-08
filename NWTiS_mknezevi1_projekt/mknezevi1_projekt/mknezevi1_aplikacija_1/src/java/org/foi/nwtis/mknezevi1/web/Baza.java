/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import org.foi.nwtis.mknezevi1.konfiguracije.Konfiguracija;
import org.foi.nwtis.mknezevi1.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.mknezevi1.web.podaci.Adresa;
import org.foi.nwtis.mknezevi1.web.podaci.Location;
import org.foi.nwtis.mknezevi1.web.podaci.WeatherData;
import org.foi.nwtis.mknezevi1.web.podaci.WeatherData2;

/**
 * Klasa u kojoj su implementirane sve operacije s bazom podataka. Iz ostalih
 * klasa se ove metode pozivaju kada postoji potreba za nekom operacijom na
 * bazom podataka.
 *
 * @author Matija
 */
public class Baza {

    Konfiguracija konfig;
    public static ServletContext kontekst;

    public Baza() {
    }

    public static ServletContext getKontekst() {
        return kontekst;
    }

    public static void setKontekst(ServletContext kontekst) {
        Baza.kontekst = kontekst;
    }

    /**
     * metoda upisuje podatke o novoj adresi u bazu podataka. Podaci koji se
     * zapisuju iz parametara su adresa(naziv) te geolokacija te adrese.
     *
     * @param adresa
     * @param lat
     * @param lon
     * @return
     */
    public String upisiAdresu(String adresa, String lat, String lon) {
        BP_Konfiguracija bp = (BP_Konfiguracija) kontekst.getAttribute("BP_Konfig");

        if (bp == null) {
            return "ERROR1";
        }

        String connUrl = bp.getServer_database() + bp.getUser_database();
        String sql = "INSERT INTO ADRESE VALUES ('" + adresa + "," + lat + "," + lon + "')";

        try {
            Class.forName(bp.getDriver_database());
        } catch (ClassNotFoundException ex) {
            return "ERROR2";
        }

        try {
            Connection conn = DriverManager.getConnection(connUrl, bp.getUser_username(), bp.getUser_password());

            String insertTableSQL = "INSERT INTO ADRESE"
                    + "(ADRESA, LATITUDE, LONGITUDE) VALUES"
                    + "(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, adresa);
            preparedStatement.setString(2, lat);
            preparedStatement.setString(3, lon);
            preparedStatement.executeUpdate();

            return "OK";
        } catch (SQLException ex) {
            return "ERROR3";
        }
    }

    /**
     * Metoda upisuje u bazu podataka meteo podatke za neku adresu.
     *
     * @param adresa
     * @param temperatura
     * @param tlak
     * @param vlaga
     * @param vjetar
     * @return
     */
    public String upisiMeteoPodatke(String adresa, Float temperatura, Float tlak, Float vlaga, Float vjetar) {
        BP_Konfiguracija bp = (BP_Konfiguracija) kontekst.getAttribute("BP_Konfig");

        if (bp == null) {
            return "ERROR1";
        }

        String connUrl = bp.getServer_database() + bp.getUser_database();
        String sql = "INSERT INTO MKNEZEVI1_METEO VALUES ('" + adresa + ","
                + temperatura + ","
                + vlaga + ","
                + vjetar + ","
                + tlak + "')";

        try {
            Class.forName(bp.getDriver_database());
        } catch (ClassNotFoundException ex) {
            return "ERROR2";
        }

        String adresaSQL = "SELECT IDADRESA FROM ADRESE WHERE ADRESA = '" + adresa + "'";
        Integer VK = null;

        try {
            Connection conn = DriverManager.getConnection(connUrl, bp.getUser_username(), bp.getUser_password());

            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(adresaSQL);
            while (rs.next()) {
                //System.out.println("RS:" + rs.getInt("IDADRESA"));
                VK = rs.getInt("IDADRESA");
            }

            String insertTableSQL = "INSERT INTO MKNEZEVI1_METEO"
                    + "(IDADRESA, TEMPERATURA, VLAGA, VJETAR, TLAK) VALUES"
                    + "(?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
            preparedStatement.setInt(1, VK);
            preparedStatement.setFloat(2, temperatura);
            preparedStatement.setFloat(3, vlaga);
            preparedStatement.setFloat(4, vjetar);
            try {
                preparedStatement.setFloat(5, tlak);
            } catch (Exception e) {
                //preparedStatement.setFloat(5, 0);
                preparedStatement.setObject(5, null);
            }
            preparedStatement.executeUpdate();

            return "OK";

        } catch (SQLException ex) {
            return "ERROR3";
        }
    }

    /**
     * metoda vraca listu meteo podataka za dobivenu adresu u parametru.
     *
     * @param adresa
     * @return
     */
    public List<WeatherData2> dajListuMeteoPodataka(String adresa) {
        List<WeatherData2> lista = new ArrayList<>();
        Integer idmeteo;
        float temperatura;
        float vlaga;
        float vjetar;
        float tlak;

        BP_Konfiguracija bp = (BP_Konfiguracija) kontekst.getAttribute("BP_Konfig");

        if (bp == null) {
            return lista;
        }

        String adresaSQL = "SELECT IDADRESA FROM ADRESE WHERE ADRESA = '" + adresa + "'";
        Integer VK = null;

        String connUrl = bp.getServer_database() + bp.getUser_database();

        try {
            Class.forName(bp.getDriver_database());
        } catch (ClassNotFoundException ex) {
            return lista;
        }

        try {
            Connection conn = DriverManager.getConnection(connUrl, bp.getUser_username(), bp.getUser_password());

            java.sql.Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery(adresaSQL);
            while (rs2.next()) {
                //System.out.println("RS:" + rs2.getInt("IDADRESA"));
                VK = rs2.getInt("IDADRESA");
            }

            //String sql = "SELECT IDMETEO, TEMPERATURA, VLAGA, VJETAR, TLAK FROM MKNEZEVI1_METEO WHERE IDADRESA = 2";
            String sql = "SELECT IDMETEO, TEMPERATURA, VLAGA, VJETAR, TLAK FROM MKNEZEVI1_METEO WHERE IDADRESA = " + VK + "";

            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                idmeteo = rs.getInt("IDMETEO");
                temperatura = rs.getFloat("TEMPERATURA");
                vlaga = rs.getFloat("VLAGA");
                vjetar = rs.getFloat("VJETAR");
                tlak = rs.getFloat("TLAK");
                lista.add(new WeatherData2(idmeteo, temperatura, vlaga, vjetar, tlak));
            }

            return lista;
        } catch (SQLException ex) {
            return lista;
        }
    }

    /**
     * Metoda vraca listu svih adresa koje su zapisane u bazi podataka.
     *
     * @return
     */
    public List<Adresa> dajListuAdresa() {
        List<Adresa> lista = new ArrayList<>();
        Integer idadresa;
        String adresa;
        String latitude;
        String longitude;

        BP_Konfiguracija bp = (BP_Konfiguracija) kontekst.getAttribute("BP_Konfig");
        //System.out.println("proba:" + bp.getAdmin_username());

        if (bp == null) {
            return lista;
        }

        String connUrl = bp.getServer_database() + bp.getUser_database();
        String sql = "SELECT IDADRESA, ADRESA, LATITUDE, LONGITUDE FROM ADRESE";
        try {
            Class.forName(bp.getDriver_database());
        } catch (ClassNotFoundException ex) {
            return lista;
        }

        try {
            Connection conn = DriverManager.getConnection(connUrl, bp.getUser_username(), bp.getUser_password());
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                idadresa = rs.getInt("IDADRESA");
                adresa = rs.getString("ADRESA");
                latitude = rs.getString("LATITUDE");
                longitude = rs.getString("LONGITUDE");
                lista.add(new Adresa(idadresa, adresa, new Location(latitude, longitude)));
            }

            return lista;
        } catch (SQLException ex) {
            return lista;
        }
    }

    /**
     * Metoda provjerava istinitost unesenih korisnickih(administratorskih) podataka.
     * @param admin
     * @param lozinka
     * @return 
     */
    public String provjeraKorisnika(String admin, String lozinka) {
        BP_Konfiguracija bp = (BP_Konfiguracija) kontekst.getAttribute("BP_Konfig");

        if (bp == null) {
            return "ERROR";
        }
        if (admin == null || admin.length() == 0 || lozinka == null || lozinka.length() == 0) {
            return "NOT_OK";
        }

        String connUrl = bp.getServer_database() + bp.getUser_database();
        String sql = "SELECT ime, prezime, lozinka, email_adresa, vrsta FROM polaznici WHERE kor_ime = '" + admin + "'";
        try {
            Class.forName(bp.getDriver_database());
        } catch (ClassNotFoundException ex) {
            return "ERROR";
        }

        try {
            Connection conn = DriverManager.getConnection(connUrl, bp.getUser_username(), bp.getUser_password());
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.next()) {
                return "NOT_OK";
            }

            String ime = rs.getString("ime");
            String prezime = rs.getString("prezime");
            int vrsta = rs.getInt("vrsta");
            String lozinkaIzBaze = rs.getString("lozinka");

            if (lozinka.equals(lozinkaIzBaze)) {
                return "OK";
            } else {
                return "NOT_OK";
            }

        } catch (SQLException ex) {
            return "ERROR";
        }
    }

    /**
     * Metoda provjerava postoji li adresa iz parametra u bazi podataka.
     * @param adresa
     * @return 
     */
    public String provjeraAdrese(String adresa) {
        BP_Konfiguracija bp = (BP_Konfiguracija) kontekst.getAttribute("BP_Konfig");

        if (bp == null) {
            return "ERROR";
        }
        if (adresa == null) {
            return "NOT_OK";
        }

        String connUrl = bp.getServer_database() + bp.getUser_database();
        String sql = "SELECT idadresa, adresa, latitude, longitude FROM adrese WHERE adresa = '" + adresa + "'";

        try {
            Class.forName(bp.getDriver_database());
        } catch (ClassNotFoundException ex) {
            return "ERROR";
        }

        try {
            Connection conn = DriverManager.getConnection(connUrl, bp.getUser_username(), bp.getUser_password());
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.next()) {
                return "NOT_OK";
            } else {
                return "OK";
            }

        } catch (SQLException ex) {
            return "ERROR";
        }
    }

    /**
     * Metoda upisuje novog korisnika (administratora) u bazu podataka.
     * @param admin
     * @param lozinka
     * @return 
     */
    public String upisiAdmina(String admin, String lozinka) {
        BP_Konfiguracija bp = (BP_Konfiguracija) kontekst.getAttribute("BP_Konfig");

        if (bp == null) {
            return "ERROR1";
        }

        if (admin == null || admin.length() == 0 || lozinka == null || lozinka.length() == 0) {
            return "NOT_OK";
        }

        String connUrl = bp.getServer_database() + bp.getUser_database();
        String sql = "INSERT INTO polaznici VALUES ('" + admin + "," + lozinka + "," + 1 + "')";

        try {
            Class.forName(bp.getDriver_database());
        } catch (ClassNotFoundException ex) {
            return "ERROR2";
        }

        try {
            Connection conn = DriverManager.getConnection(connUrl, bp.getUser_username(), bp.getUser_password());

            String insertTableSQL = "INSERT INTO polaznici"
                    + "(kor_ime, lozinka, vrsta) VALUES"
                    + "(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, admin);
            preparedStatement.setString(2, lozinka);
            preparedStatement.setInt(3, 1);
            preparedStatement.executeUpdate();

            return "OK";
        } catch (SQLException ex) {
            return "ERROR3";
        }
    }

    /**
     * metoda upisuje dobivenu naredbu i vrijeme zahtjeva u tablicu baze podataka
     * @param naredba
     * @param vrijeme
     * @return 
     */
    public String upisiDnevnickiZapis(String naredba, String vrijeme) {
        BP_Konfiguracija bp = (BP_Konfiguracija) kontekst.getAttribute("BP_Konfig");

        if (bp == null) {
            return "ERROR1";
        }

        String connUrl = bp.getServer_database() + bp.getUser_database();
        String sql = "INSERT INTO dnevnik VALUES ('" + naredba + "," + vrijeme + "')";

        try {
            Class.forName(bp.getDriver_database());
        } catch (ClassNotFoundException ex) {
            return "ERROR2";
        }

        try {
            Connection conn = DriverManager.getConnection(connUrl, bp.getUser_username(), bp.getUser_password());

            String insertTableSQL = "INSERT INTO dnevnik"
                    + "(naredba,vrijeme) VALUES"
                    + "(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, naredba);
            preparedStatement.setString(2, vrijeme);
            preparedStatement.executeUpdate();

            return "OK";
        } catch (SQLException ex) {
            return "ERROR3";
        }
    }
}
