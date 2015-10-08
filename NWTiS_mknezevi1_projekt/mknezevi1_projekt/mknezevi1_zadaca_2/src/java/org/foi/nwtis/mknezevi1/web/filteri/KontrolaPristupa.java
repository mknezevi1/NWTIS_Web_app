/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mknezevi1.web.filteri;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Filter koji vrsi kontrolu pristupa i propusta/ne propusta odredenog korisnika u zasticeni dio /korisnik/*.  
 * Propusta se samo valjano ulogirani korisnik.
 * @author Matija
 */
@WebFilter(filterName = "KontrolaPristupa", urlPatterns = {"/korisnik/*"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class KontrolaPristupa implements Filter {

    private FilterConfig config = null;

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean propusti = true;
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
        }

        HttpSession sesija = ((HttpServletRequest) request).getSession(false);
        if (sesija == null) {
            propusti = false;
        } else {
            if (sesija.getAttribute("korisnik") == null) {
                propusti = false;
            }
        }
        if (propusti==false) {
            RequestDispatcher rd = config.getServletContext().getRequestDispatcher("/faces/login.xhtml");
            rd.forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        this.config = null;
    }

}
