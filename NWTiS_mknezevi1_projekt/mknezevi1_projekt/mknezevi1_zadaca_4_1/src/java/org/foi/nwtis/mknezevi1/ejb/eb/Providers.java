/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.mknezevi1.ejb.eb;

import java.io.Serializable;
import java.util.List;
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
 * @author nwtis_2
 */
@Entity
@Table(name = "PROVIDERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Providers.findAll", query = "SELECT p FROM Providers p"),
    @NamedQuery(name = "Providers.findByProviderid", query = "SELECT p FROM Providers p WHERE p.providerid = :providerid"),
    @NamedQuery(name = "Providers.findByProvidername", query = "SELECT p FROM Providers p WHERE p.providername = :providername")})
public class Providers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROVIDERID")
    private Integer providerid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PROVIDERNAME")
    private String providername;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "providers")
    private List<Stations> stationsList;

    public Providers() {
    }

    public Providers(Integer providerid) {
        this.providerid = providerid;
    }

    public Providers(Integer providerid, String providername) {
        this.providerid = providerid;
        this.providername = providername;
    }

    public Integer getProviderid() {
        return providerid;
    }

    public void setProviderid(Integer providerid) {
        this.providerid = providerid;
    }

    public String getProvidername() {
        return providername;
    }

    public void setProvidername(String providername) {
        this.providername = providername;
    }

    @XmlTransient
    public List<Stations> getStationsList() {
        return stationsList;
    }

    public void setStationsList(List<Stations> stationsList) {
        this.stationsList = stationsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (providerid != null ? providerid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Providers)) {
            return false;
        }
        Providers other = (Providers) object;
        if ((this.providerid == null && other.providerid != null) || (this.providerid != null && !this.providerid.equals(other.providerid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.mknezevi1.ejb.eb.Providers[ providerid=" + providerid + " ]";
    }
    
}
