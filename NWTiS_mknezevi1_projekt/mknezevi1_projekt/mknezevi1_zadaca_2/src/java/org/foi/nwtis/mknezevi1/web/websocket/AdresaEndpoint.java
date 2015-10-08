/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web.websocket;

import java.util.ArrayList;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author nwtis_2
 */
@ServerEndpoint("/adreseEndpoint")
public class AdresaEndpoint {

    static ArrayList<Session> sessions = new ArrayList<>();

    @OnMessage
    public String onMessage(String message) {
        return null;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("onOpen: " + session.getId());
        sessions.add(session);
        System.out.println("onOpen: Notification list size: " + sessions.size());
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose: " + session.getId());
        sessions.remove(session);
    }
    
    public static void obavijestiPromjenu(){
    
    }
    
}
