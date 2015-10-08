/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.mknezevi1.web.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author nwtis_2
 */
@ServerEndpoint("/adresaEndpoint")
public class AdresaEndpoint {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    
    @OnMessage
    public String onMessage(String message) {
        return null;
    }
    
    @OnOpen
    public void onOpen (Session peer) {
        peers.add(peer);
    }

    @OnClose
    public void onClose (Session peer) {
        peers.remove(peer);
    }
    
    public static void obavijestiPromjenu(){
        for(Session s : peers){
            try {
                s.getBasicRemote().sendText("Promjena podataka");
            } catch (IOException ex) {
                Logger.getLogger(AdresaEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
