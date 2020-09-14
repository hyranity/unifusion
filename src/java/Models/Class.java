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
    , @NamedQuery(name = "Class.findByClassname", query = "SELECT c FROM Class c WHERE c.classname = :classname")
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
    @Size(min = 1, max = 30)
    @Column(name = "CLASSID")
    private String classid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CLASSNAME")
    private String classname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NOOFSTUDENTS")
    private int noofstudents;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "CLASSTYPE")
    private String classtype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ICONURL")
    private String iconurl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "BANNERURL")
    private String bannerurl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "COLOURTHEME")
    private String colourtheme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISPUBLIC")
    private Boolean ispublic;
    @OneToMany(mappedBy = "classid")
    private Collection<Gradedcomponent> gradedcomponentCollection;
    @OneToMany(mappedBy = "classid")
    private Collection<Session> sessionCollection;
    @JoinColumn(name = "COURSECODE", referencedColumnName = "COURSECODE")
    @ManyToOne
    private Course coursecode;
    @JoinColumn(name = "HOSTID", referencedColumnName = "PARTICIPANTID")
    @ManyToOne
    private Participant hostid;

    public Class() {
    }

    public Class(String classid) {
        this.classid = classid;
    }

    public Class(String classid, String classname, int noofstudents, String classtype, String iconurl, String bannerurl, String colourtheme, Boolean ispublic) {
        this.classid = classid;
        this.classname = classname;
        this.noofstudents = noofstudents;
        this.classtype = classtype;
        this.iconurl = iconurl;
        this.bannerurl = bannerurl;
        this.colourtheme = colourtheme;
        this.ispublic = ispublic;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
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
    public Collection<Gradedcomponent> getGradedcomponentCollection() {
        return gradedcomponentCollection;
    }

    public void setGradedcomponentCollection(Collection<Gradedcomponent> gradedcomponentCollection) {
        this.gradedcomponentCollection = gradedcomponentCollection;
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

    public Participant getHostid() {
        return hostid;
    }

    public void setHostid(Participant hostid) {
        this.hostid = hostid;
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
