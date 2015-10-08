/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.mknezevi1.ejb.eb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nwtis_2
 */
@Entity
@Table(name = "STATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stations.findAll", query = "SELECT s FROM Stations s"),
    @NamedQuery(name = "Stations.findByProviderid", query = "SELECT s FROM Stations s WHERE s.stationsPK.providerid = :providerid"),
    @NamedQuery(name = "Stations.findByStationid", query = "SELECT s FROM Stations s WHERE s.stationsPK.stationid = :stationid"),
    @NamedQuery(name = "Stations.findByStationname", query = "SELECT s FROM Stations s WHERE s.stationname = :stationname"),
    @NamedQuery(name = "Stations.findByLatitude", query = "SELECT s FROM Stations s WHERE s.latitude = :latitude"),
    @NamedQuery(name = "Stations.findByLongitude", query = "SELECT s FROM Stations s WHERE s.longitude = :longitude"),
    @NamedQuery(name = "Stations.findByElevationabovesealevel", query = "SELECT s FROM Stations s WHERE s.elevationabovesealevel = :elevationabovesealevel"),
    @NamedQuery(name = "Stations.findByDisplayflag", query = "SELECT s FROM Stations s WHERE s.displayflag = :displayflag")})
public class Stations implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StationsPK stationsPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "STATIONNAME")
    private String stationname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "LATITUDE")
    private String latitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "LONGITUDE")
    private String longitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ELEVATIONABOVESEALEVEL")
    private String elevationabovesealevel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "DISPLAYFLAG")
    private String displayflag;
    @JoinColumn(name = "PROVIDERID", referencedColumnName = "PROVIDERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Providers providers;

    public Stations() {
    }

    public Stations(StationsPK stationsPK) {
        this.stationsPK = stationsPK;
    }

    public Stations(StationsPK stationsPK, String stationname, String latitude, String longitude, String elevationabovesealevel, String displayflag) {
        this.stationsPK = stationsPK;
        this.stationname = stationname;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevationabovesealevel = elevationabovesealevel;
        this.displayflag = displayflag;
    }

    public Stations(int providerid, String stationid) {
        this.stationsPK = new StationsPK(providerid, stationid);
    }

    public StationsPK getStationsPK() {
        return stationsPK;
    }

    public void setStationsPK(StationsPK stationsPK) {
        this.stationsPK = stationsPK;
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getElevationabovesealevel() {
        return elevationabovesealevel;
    }

    public void setElevationabovesealevel(String elevationabovesealevel) {
        this.elevationabovesealevel = elevationabovesealevel;
    }

    public String getDisplayflag() {
        return displayflag;
    }

    public void setDisplayflag(String displayflag) {
        this.displayflag = displayflag;
    }

    public Providers getProviders() {
        return providers;
    }

    public void setProviders(Providers providers) {
        this.providers = providers;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stationsPK != null ? stationsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stations)) {
            return false;
        }
        Stations other = (Stations) object;
        if ((this.stationsPK == null && other.stationsPK != null) || (this.stationsPK != null && !this.stationsPK.equals(other.stationsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.mknezevi1.ejb.eb.Stations[ stationsPK=" + stationsPK + " ]";
    }
    
}
