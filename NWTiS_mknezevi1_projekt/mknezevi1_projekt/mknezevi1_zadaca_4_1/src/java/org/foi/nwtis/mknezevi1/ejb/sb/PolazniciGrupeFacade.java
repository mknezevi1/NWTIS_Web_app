/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.mknezevi1.ejb.sb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.foi.nwtis.mknezevi1.ejb.eb.PolazniciGrupe;

/**
 *
 * @author Matija
 */
@Stateless
public class PolazniciGrupeFacade extends AbstractFacade<PolazniciGrupe> {
    @PersistenceContext(unitName = "zadaca_4_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PolazniciGrupeFacade() {
        super(PolazniciGrupe.class);
    }
    
}
