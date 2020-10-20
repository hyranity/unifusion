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
@Table(name = "COURSE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c")
    , @NamedQuery(name = "Course.findByCoursecode", query = "SELECT c FROM Course c WHERE c.coursecode = :coursecode")
    , @NamedQuery(name = "Course.findByTitle", query = "SELECT c FROM Course c WHERE c.title = :title")
    , @NamedQuery(name = "Course.findByDescription", query = "SELECT c FROM Course c WHERE c.description = :description")
    , @NamedQuery(name = "Course.findByIconurl", query = "SELECT c FROM Course c WHERE c.iconurl = :iconurl")
    , @NamedQuery(name = "Course.findByBannerurl", query = "SELECT c FROM Course c WHERE c.bannerurl = :bannerurl")
    , @NamedQuery(name = "Course.findByColourtheme", query = "SELECT c FROM Course c WHERE c.colourtheme = :colourtheme")
    , @NamedQuery(name = "Course.findByIspublic", query = "SELECT c FROM Course c WHERE c.ispublic = :ispublic")})
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "COURSECODE")
    private String coursecode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
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
    @OneToMany(mappedBy = "coursecode")
    private Collection<Class> classCollection;
    @JoinColumn(name = "PROGRAMMECODE", referencedColumnName = "PROGRAMMECODE")
    @ManyToOne
    private Programme programmecode;
    @OneToMany(mappedBy = "coursecode")
    private Collection<Announcement> announcementCollection;
    @OneToMany(mappedBy = "coursecode")
    private Collection<Courseparticipant> courseparticipantCollection;
    @OneToMany(mappedBy = "coursecode")
    private Collection<Venue> venueCollection;

    public Course() {
    }

    public Course(String coursecode) {
        this.coursecode = coursecode;
    }

    public Course(String coursecode, String title, String description, Boolean ispublic) {
        this.coursecode = coursecode;
        this.title = title;
        this.description = description;
        this.ispublic = ispublic;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public Collection<Class> getClassCollection() {
        return classCollection;
    }

    public void setClassCollection(Collection<Class> classCollection) {
        this.classCollection = classCollection;
    }

    public Programme getProgrammecode() {
        return programmecode;
    }

    public void setProgrammecode(Programme programmecode) {
        this.programmecode = programmecode;
    }

    @XmlTransient
    public Collection<Announcement> getAnnouncementCollection() {
        return announcementCollection;
    }

    public void setAnnouncementCollection(Collection<Announcement> announcementCollection) {
        this.announcementCollection = announcementCollection;
    }

    @XmlTransient
    public Collection<Courseparticipant> getCourseparticipantCollection() {
        return courseparticipantCollection;
    }

    public void setCourseparticipantCollection(Collection<Courseparticipant> courseparticipantCollection) {
        this.courseparticipantCollection = courseparticipantCollection;
    }

    @XmlTransient
    public Collection<Venue> getVenueCollection() {
        return venueCollection;
    }

    public void setVenueCollection(Collection<Venue> venueCollection) {
        this.venueCollection = venueCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coursecode != null ? coursecode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.coursecode == null && other.coursecode != null) || (this.coursecode != null && !this.coursecode.equals(other.coursecode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Course[ coursecode=" + coursecode + " ]";
    }
    
}
