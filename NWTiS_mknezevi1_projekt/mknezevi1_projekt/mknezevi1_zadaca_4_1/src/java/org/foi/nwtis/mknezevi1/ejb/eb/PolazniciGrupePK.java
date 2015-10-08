/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.mknezevi1.ejb.eb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Matija
 */
@Embeddable
public class PolazniciGrupePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "KOR_IME")
    private String korIme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "GR_IME")
    private String grIme;

    public PolazniciGrupePK() {
    }

    public PolazniciGrupePK(String korIme, String grIme) {
        this.korIme = korIme;
        this.grIme = grIme;
    }

    public String getKorIme() {
        return korIme;
    }

    public void setKorIme(String korIme) {
        this.korIme = korIme;
    }

    public String getGrIme() {
        return grIme;
    }

    public void setGrIme(String grIme) {
        this.grIme = grIme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (korIme != null ? korIme.hashCode() : 0);
        hash += (grIme != null ? grIme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PolazniciGrupePK)) {
            return false;
        }
        PolazniciGrupePK other = (PolazniciGrupePK) object;
        if ((this.korIme == null && other.korIme != null) || (this.korIme != null && !this.korIme.equals(other.korIme))) {
            return false;
        }
        if ((this.grIme == null && other.grIme != null) || (this.grIme != null && !this.grIme.equals(other.grIme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.mknezevi1.ejb.eb.PolazniciGrupePK[ korIme=" + korIme + ", grIme=" + grIme + " ]";
    }
    
}
