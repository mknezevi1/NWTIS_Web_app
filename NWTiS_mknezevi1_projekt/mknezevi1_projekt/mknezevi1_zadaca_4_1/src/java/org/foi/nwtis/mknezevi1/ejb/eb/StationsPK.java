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
 * @author nwtis_2
 */
@Embeddable
public class StationsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROVIDERID")
    private int providerid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "STATIONID")
    private String stationid;

    public StationsPK() {
    }

    public StationsPK(int providerid, String stationid) {
        this.providerid = providerid;
        this.stationid = stationid;
    }

    public int getProviderid() {
        return providerid;
    }

    public void setProviderid(int providerid) {
        this.providerid = providerid;
    }

    public String getStationid() {
        return stationid;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) providerid;
        hash += (stationid != null ? stationid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StationsPK)) {
            return false;
        }
        StationsPK other = (StationsPK) object;
        if (this.providerid != other.providerid) {
            return false;
        }
        if ((this.stationid == null && other.stationid != null) || (this.stationid != null && !this.stationid.equals(other.stationid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.mknezevi1.ejb.eb.StationsPK[ providerid=" + providerid + ", stationid=" + stationid + " ]";
    }
    
}
