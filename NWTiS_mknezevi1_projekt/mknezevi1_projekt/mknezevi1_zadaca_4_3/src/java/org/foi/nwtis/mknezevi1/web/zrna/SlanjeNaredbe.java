/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web.zrna;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import static org.foi.nwtis.mknezevi1.ejb.mdb.MeteoPostar.adresaServera;
 import static org.foi.nwtis.mknezevi1.ejb.mdb.MeteoPostar.korisnikApp4;
 import static org.foi.nwtis.mknezevi1.ejb.mdb.MeteoPostar.lozinkaApp4;
 import static org.foi.nwtis.mknezevi1.ejb.mdb.MeteoPostar.port;

/**
 *
 * @author Matija
 */
@ManagedBean(name = "SlanjeNaredbe")
@RequestScoped
public class SlanjeNaredbe {

    private String naredba = "";

    public SlanjeNaredbe() {
    }

    public String getNaredba() {
        return naredba;
    }

    public void setNaredba(String naredba) {
        this.naredba = naredba;
    }

    public String posaljiNaredbu() {
        try {   
            Socket veza = null;
                
            //veza = new Socket(, 8000);
            veza = new Socket(adresaServera, port);
            InputStream is = veza.getInputStream();
            OutputStream os = veza.getOutputStream();

            //os.write("USER admin; PASSWD 123456; ADD \"Varazdin, Pavlinska 2\";".getBytes());
            //os.write("USER pero; TEST \"Zagreb, Maksimirska 2\";".getBytes());
            //String naredba = "USER admin; PASSWD 123456; TEST \"" + addAdresa + "\";";
            //String naredba = "USER " + korisnikApp4 + "; PASSWD " + lozinkaApp4 + "; TEST \"" + addAdresa + "\";";
            os.write(naredba.getBytes());
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
            veza.close();
            
        } catch (Exception e) {

        }

        return "OK";
    }
}
