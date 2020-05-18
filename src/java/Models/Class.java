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
    , @NamedQuery(name = "Class.findByNoofstudents", query = "SELECT c FROM Class c WHERE c.noofstudents = :noofstudents")
    , @NamedQuery(name = "Class.findByClasstype", query = "SELECT c FROM Class c WHERE c.classtype = :classtype")})
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
    @Column(name = "NOOFSTUDENTS")
    private int noofstudents;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "CLASSTYPE")
    private String classtype;
    @OneToMany(mappedBy = "classid")
    private Collection<Participant> participantCollection;
    @OneToMany(mappedBy = "classid")
    private Collection<Gradedcomponent> gradedcomponentCollection;
    @OneToMany(mappedBy = "classid")
    private Collection<Session> sessionCollection;
    @JoinColumn(name = "COURSECODE", referencedColumnName = "COURSECODE")
    @ManyToOne
    private Course coursecode;
    @JoinColumn(name = "SEMESTERCODE", referencedColumnName = "SEMESTERCODE")
    @ManyToOne
    private Semester semestercode;

    public Class() {
    }

    public Class(String classid) {
        this.classid = classid;
    }

    public Class(String classid, int noofstudents, String classtype) {
        this.classid = classid;
        this.noofstudents = noofstudents;
        this.classtype = classtype;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
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

    @XmlTransient
    public Collection<Participant> getParticipantCollection() {
        return participantCollection;
    }

    public void setParticipantCollection(Collection<Participant> participantCollection) {
        this.participantCollection = participantCollection;
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

    public Semester getSemestercode() {
        return semestercode;
    }

    public void setSemestercode(Semester semestercode) {
        this.semestercode = semestercode;
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
