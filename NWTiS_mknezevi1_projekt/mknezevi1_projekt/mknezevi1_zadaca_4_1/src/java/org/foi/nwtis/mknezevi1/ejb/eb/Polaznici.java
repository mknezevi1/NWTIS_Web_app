/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.mknezevi1.ejb.eb;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Matija
 */
@Entity
@Table(name = "POLAZNICI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Polaznici.findAll", query = "SELECT p FROM Polaznici p"),
    @NamedQuery(name = "Polaznici.findByKorIme", query = "SELECT p FROM Polaznici p WHERE p.korIme = :korIme"),
    @NamedQuery(name = "Polaznici.findByIme", query = "SELECT p FROM Polaznici p WHERE p.ime = :ime"),
    @NamedQuery(name = "Polaznici.findByPrezime", query = "SELECT p FROM Polaznici p WHERE p.prezime = :prezime"),
    @NamedQuery(name = "Polaznici.findByLozinka", query = "SELECT p FROM Polaznici p WHERE p.lozinka = :lozinka"),
    @NamedQuery(name = "Polaznici.findByEmailAdresa", query = "SELECT p FROM Polaznici p WHERE p.emailAdresa = :emailAdresa"),
    @NamedQuery(name = "Polaznici.findByVrsta", query = "SELECT p FROM Polaznici p WHERE p.vrsta = :vrsta")})
public class Polaznici implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "KOR_IME")
    private String korIme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "IME")
    private String ime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "PREZIME")
    private String prezime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LOZINKA")
    private String lozinka;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "EMAIL_ADRESA")
    private String emailAdresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VRSTA")
    private int vrsta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "polaznici")
    private Collection<PolazniciGrupe> polazniciGrupeCollection;

    public Polaznici() {
    }

    public Polaznici(String korIme) {
        this.korIme = korIme;
    }

    public Polaznici(String korIme, String ime, String prezime, String lozinka, String emailAdresa, int vrsta) {
        this.korIme = korIme;
        this.ime = ime;
        this.prezime = prezime;
        this.lozinka = lozinka;
        this.emailAdresa = emailAdresa;
        this.vrsta = vrsta;
    }

    public String getKorIme() {
        return korIme;
    }

    public void setKorIme(String korIme) {
        this.korIme = korIme;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getEmailAdresa() {
        return emailAdresa;
    }

    public void setEmailAdresa(String emailAdresa) {
        this.emailAdresa = emailAdresa;
    }

    public int getVrsta() {
        return vrsta;
    }

    public void setVrsta(int vrsta) {
        this.vrsta = vrsta;
    }

    @XmlTransient
    public Collection<PolazniciGrupe> getPolazniciGrupeCollection() {
        return polazniciGrupeCollection;
    }

    public void setPolazniciGrupeCollection(Collection<PolazniciGrupe> polazniciGrupeCollection) {
        this.polazniciGrupeCollection = polazniciGrupeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (korIme != null ? korIme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Polaznici)) {
            return false;
        }
        Polaznici other = (Polaznici) object;
        if ((this.korIme == null && other.korIme != null) || (this.korIme != null && !this.korIme.equals(other.korIme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.mknezevi1.ejb.eb.Polaznici[ korIme=" + korIme + " ]";
    }
    
}
