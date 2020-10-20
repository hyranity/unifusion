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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "VENUE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venue.findAll", query = "SELECT v FROM Venue v")
    , @NamedQuery(name = "Venue.findByVenueid", query = "SELECT v FROM Venue v WHERE v.venueid = :venueid")
    , @NamedQuery(name = "Venue.findByTitle", query = "SELECT v FROM Venue v WHERE v.title = :title")
    , @NamedQuery(name = "Venue.findByCapacity", query = "SELECT v FROM Venue v WHERE v.capacity = :capacity")
    , @NamedQuery(name = "Venue.findByLocation", query = "SELECT v FROM Venue v WHERE v.location = :location")})
public class Venue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "VENUEID")
    private String venueid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPACITY")
    private int capacity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "LOCATION")
    private String location;
    @OneToMany(mappedBy = "venueid")
    private Collection<Session> sessionCollection;
    @JoinColumn(name = "COURSECODE", referencedColumnName = "COURSECODE")
    @ManyToOne
    private Course coursecode;
    @JoinColumn(name = "INSTITUTIONCODE", referencedColumnName = "INSTITUTIONCODE")
    @ManyToOne
    private Institution institutioncode;
    @JoinColumn(name = "PROGRAMMECODE", referencedColumnName = "PROGRAMMECODE")
    @ManyToOne
    private Programme programmecode;

    public Venue() {
    }

    public Venue(String venueid) {
        this.venueid = venueid;
    }

    public Venue(String venueid, String title, int capacity, String location) {
        this.venueid = venueid;
        this.title = title;
        this.capacity = capacity;
        this.location = location;
    }

    public String getVenueid() {
        return venueid;
    }

    public void setVenueid(String venueid) {
        this.venueid = venueid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @XmlTransient
    public Collection<Session> getSessionCollection() {
        return sessionCollection;
    }

    public void setSessionCollection(Collection<Session> sessionCollection) {
        this.sessionCollection = sessionCollection;
    }

    public Course getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(Course coursecode) {
        this.coursecode = coursecode;
    }

    public Institution getInstitutioncode() {
        return institutioncode;
    }

    public void setInstitutioncode(Institution institutioncode) {
        this.institutioncode = institutioncode;
    }

    public Programme getProgrammecode() {
        return programmecode;
    }

    public void setProgrammecode(Programme programmecode) {
        this.programmecode = programmecode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (venueid != null ? venueid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venue)) {
            return false;
        }
        Venue other = (Venue) object;
        if ((this.venueid == null && other.venueid != null) || (this.venueid != null && !this.venueid.equals(other.venueid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Venue[ venueid=" + venueid + " ]";
    }
    
}
