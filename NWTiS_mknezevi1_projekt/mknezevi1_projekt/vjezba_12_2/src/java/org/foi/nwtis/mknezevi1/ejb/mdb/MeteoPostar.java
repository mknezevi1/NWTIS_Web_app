/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.ejb.mdb;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author nwtis_2
 */
/*@MessageDriven(activationConfig = {
 @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
 @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/NWTiS_vjezba_12")
 })*/
@MessageDriven(mappedName = "jms/NWTiS_vjezba_12",
        activationConfig = {
            @ActivationConfigProperty(
                    propertyName = "acknowledgeMode",
                    propertyValue = "Auto-acknowledge"),
            @ActivationConfigProperty(
                    propertyName = "destinationType",
                    propertyValue = "javax.jms.Queue")})
public class MeteoPostar implements MessageListener {

    public static List<String> poruke = null;
    public static String adresaServera = "";
    public static Integer port;
    public static String korisnikApp4 = "";
    public static String lozinkaApp4 = "";
    
    public MeteoPostar() {
        if (poruke == null) {
            poruke = new ArrayList<String>();
        }
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("MK: DOSLA PORUKA");
        String addAdresa = "";
        
            try {
                addAdresa = message.getBody(String.class);
                poruke.add(addAdresa);
                System.out.println("SADRZAJ poruke:" + message.getBody(String.class));
            } catch (JMSException ex) {
                System.out.println("PADE");
                ex.printStackTrace();
            } 
            
            try {
            Socket veza = null;
                
            //veza = new Socket(, 8000);
            veza = new Socket(adresaServera, port);
            InputStream is = veza.getInputStream();
            OutputStream os = veza.getOutputStream();

            //os.write("USER admin; PASSWD 123456; ADD \"Varazdin, Pavlinska 2\";".getBytes());
            //os.write("USER pero; TEST \"Zagreb, Maksimirska 2\";".getBytes());
            //String naredba = "USER admin; PASSWD 123456; TEST \"" + addAdresa + "\";";
            String naredba = "USER " + korisnikApp4 + "; PASSWD " + lozinkaApp4 + "; TEST \"" + addAdresa + "\";";
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
            //System.out.println("odgovor: " + odgovor);
            is.close();
            os.close();
            veza.close();

            if (odgovor.toString().equals("ERR 51")) {
                System.out.println("JOŠ JE NEMA U ADRESAMA!");

                //veza = new Socket("127.0.0.1", 8000);
                veza = new Socket(adresaServera, port);
                is = veza.getInputStream();
                os = veza.getOutputStream();
                
                //naredba = "USER admin; PASSWD 123456; ADD \"" + addAdresa + "\";";
                naredba = "USER " + korisnikApp4 + "; PASSWD " + lozinkaApp4 + "; ADD \"" + addAdresa + "\";";
                os.write(naredba.getBytes());
                os.flush();
                veza.shutdownOutput();
                
                odgovor.setLength(0);
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
            }

        } catch (IOException ex) {
           
        }
    }

}
