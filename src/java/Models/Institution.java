/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
 * @author mast3
 */
@Entity
@Table(name = "INSTITUTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Institution.findAll", query = "SELECT i FROM Institution i")
    , @NamedQuery(name = "Institution.findByInstitutioncode", query = "SELECT i FROM Institution i WHERE i.institutioncode = :institutioncode")
    , @NamedQuery(name = "Institution.findByAuthcode", query = "SELECT i FROM Institution i WHERE i.authcode = :authcode")
    , @NamedQuery(name = "Institution.findByName", query = "SELECT i FROM Institution i WHERE i.name = :name")
    , @NamedQuery(name = "Institution.findByDescription", query = "SELECT i FROM Institution i WHERE i.description = :description")
    , @NamedQuery(name = "Institution.findByAddress", query = "SELECT i FROM Institution i WHERE i.address = :address")
    , @NamedQuery(name = "Institution.findByIconurl", query = "SELECT i FROM Institution i WHERE i.iconurl = :iconurl")
    , @NamedQuery(name = "Institution.findByBannerurl", query = "SELECT i FROM Institution i WHERE i.bannerurl = :bannerurl")
    , @NamedQuery(name = "Institution.findByColourtheme", query = "SELECT i FROM Institution i WHERE i.colourtheme = :colourtheme")
    , @NamedQuery(name = "Institution.findByIspublic", query = "SELECT i FROM Institution i WHERE i.ispublic = :ispublic")})
public class Institution implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "INSTITUTIONCODE")
    private String institutioncode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "AUTHCODE")
    private String authcode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 50)
    @Column(name = "ICONURL")
    private String iconurl;
    @Size(max = 50)
    @Column(name = "BANNERURL")
    private String bannerurl;
    @Size(max = 20)
    @Column(name = "COLOURTHEME")
    private String colourtheme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISPUBLIC")
    private Boolean ispublic;
    @OneToMany(mappedBy = "institutioncode")
    private Collection<Programme> programmeCollection;
    @OneToMany(mappedBy = "institutioncode")
    private Collection<Announcement> announcementCollection;
    @OneToMany(mappedBy = "institutioncode")
    private Collection<Venue> venueCollection;
    @OneToMany(mappedBy = "institutioncode")
    private Collection<Institutionparticipant> institutionparticipantCollection;

    public Institution() {
    }

    public Institution(String institutioncode) {
        this.institutioncode = institutioncode;
    }

    public Institution(String institutioncode, String authcode, String name, String description, String address, Boolean ispublic) {
        this.institutioncode = institutioncode;
        this.authcode = authcode;
        this.name = name;
        this.description = description;
        this.address = address;
        this.ispublic = ispublic;
    }

    public String getInstitutioncode() {
        return institutioncode;
    }

    public void setInstitutioncode(String institutioncode) {
        this.institutioncode = institutioncode;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getBannerurl() {
        return bannerurl;
    }

    public void setBannerurl(String bannerurl) {
        this.bannerurl = bannerurl;
    }

    public String getColourtheme() {
        return colourtheme;
    }

    public void setColourtheme(String colourtheme) {
        this.colourtheme = colourtheme;
    }

    public Boolean getIspublic() {
        return ispublic;
    }

    public void setIspublic(Boolean ispublic) {
        this.ispublic = ispublic;
    }

    @XmlTransient
    public Collection<Programme> getProgrammeCollection() {
        return programmeCollection;
    }

    public void setProgrammeCollection(Collection<Programme> programmeCollection) {
        this.programmeCollection = programmeCollection;
    }

    @XmlTransient
    public Collection<Announcement> getAnnouncementCollection() {
        return announcementCollection;
    }

    public void setAnnouncementCollection(Collection<Announcement> announcementCollection) {
        this.announcementCollection = announcementCollection;
    }

    @XmlTransient
    public Collection<Venue> getVenueCollection() {
        return venueCollection;
    }

    public void setVenueCollection(Collection<Venue> venueCollection) {
        this.venueCollection = venueCollection;
    }

    @XmlTransient
    public Collection<Institutionparticipant> getInstitutionparticipantCollection() {
        return institutionparticipantCollection;
    }

    public void setInstitutionparticipantCollection(Collection<Institutionparticipant> institutionparticipantCollection) {
        this.institutionparticipantCollection = institutionparticipantCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (institutioncode != null ? institutioncode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Institution)) {
            return false;
        }
        Institution other = (Institution) object;
        if ((this.institutioncode == null && other.institutioncode != null) || (this.institutioncode != null && !this.institutioncode.equals(other.institutioncode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Institution[ institutioncode=" + institutioncode + " ]";
    }
    
}
