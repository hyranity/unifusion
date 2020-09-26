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
@Table(name = "CLASS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Class.findAll", query = "SELECT c FROM Class c")
    , @NamedQuery(name = "Class.findByClassid", query = "SELECT c FROM Class c WHERE c.classid = :classid")
    , @NamedQuery(name = "Class.findByClasstitle", query = "SELECT c FROM Class c WHERE c.classtitle = :classtitle")
    , @NamedQuery(name = "Class.findByDescription", query = "SELECT c FROM Class c WHERE c.description = :description")
    , @NamedQuery(name = "Class.findByNoofstudents", query = "SELECT c FROM Class c WHERE c.noofstudents = :noofstudents")
    , @NamedQuery(name = "Class.findByClasstype", query = "SELECT c FROM Class c WHERE c.classtype = :classtype")
    , @NamedQuery(name = "Class.findByIconurl", query = "SELECT c FROM Class c WHERE c.iconurl = :iconurl")
    , @NamedQuery(name = "Class.findByBannerurl", query = "SELECT c FROM Class c WHERE c.bannerurl = :bannerurl")
    , @NamedQuery(name = "Class.findByColourtheme", query = "SELECT c FROM Class c WHERE c.colourtheme = :colourtheme")
    , @NamedQuery(name = "Class.findByIspublic", query = "SELECT c FROM Class c WHERE c.ispublic = :ispublic")})
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CLASSID")
    private String classid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CLASSTITLE")
    private String classtitle;
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NOOFSTUDENTS")
    private int noofstudents;
    @Size(max = 25)
    @Column(name = "CLASSTYPE")
    private String classtype;
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
    @OneToMany(mappedBy = "classid")
    private Collection<Session> sessionCollection;
    @JoinColumn(name = "COURSECODE", referencedColumnName = "COURSECODE")
    @ManyToOne
    private Course coursecode;
    @OneToMany(mappedBy = "classid")
    private Collection<Announcement> announcementCollection;
    @OneToMany(mappedBy = "classid")
    private Collection<Gradedcomponent> gradedcomponentCollection;
    @OneToMany(mappedBy = "classid")
    private Collection<Classparticipant> classparticipantCollection;

    public Class() {
    }

    public Class(String classid) {
        this.classid = classid;
    }

    public Class(String classid, String classtitle, int noofstudents, Boolean ispublic) {
        this.classid = classid;
        this.classtitle = classtitle;
        this.noofstudents = noofstudents;
        this.ispublic = ispublic;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClasstitle() {
        return classtitle;
    }

    public void setClasstitle(String classtitle) {
        this.classtitle = classtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNoofstudents() {
        return noofstudents;
    }

    public void setNoofstudents(int noofstudents) {
        this.noofstudents = noofstudents;
    }

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
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

    @XmlTransient
    public Collection<Announcement> getAnnouncementCollection() {
        return announcementCollection;
    }

    public void setAnnouncementCollection(Collection<Announcement> announcementCollection) {
        this.announcementCollection = announcementCollection;
    }

    @XmlTransient
    public Collection<Gradedcomponent> getGradedcomponentCollection() {
        return gradedcomponentCollection;
    }

    public void setGradedcomponentCollection(Collection<Gradedcomponent> gradedcomponentCollection) {
        this.gradedcomponentCollection = gradedcomponentCollection;
    }

    @XmlTransient
    public Collection<Classparticipant> getClassparticipantCollection() {
        return classparticipantCollection;
    }

    public void setClassparticipantCollection(Collection<Classparticipant> classparticipantCollection) {
        this.classparticipantCollection = classparticipantCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classid != null ? classid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Class)) {
            return false;
        }
        Class other = (Class) object;
        if ((this.classid == null && other.classid != null) || (this.classid != null && !this.classid.equals(other.classid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Class[ classid=" + classid + " ]";
    }
    
}
