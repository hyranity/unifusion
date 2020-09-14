/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mast3
 */
@Entity
@Table(name = "SEMESTER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Semester.findAll", query = "SELECT s FROM Semester s")
    , @NamedQuery(name = "Semester.findBySemestercode", query = "SELECT s FROM Semester s WHERE s.semestercode = :semestercode")
    , @NamedQuery(name = "Semester.findBySemesterno", query = "SELECT s FROM Semester s WHERE s.semesterno = :semesterno")
    , @NamedQuery(name = "Semester.findByYear", query = "SELECT s FROM Semester s WHERE s.year = :year")
    , @NamedQuery(name = "Semester.findByDatestart", query = "SELECT s FROM Semester s WHERE s.datestart = :datestart")
    , @NamedQuery(name = "Semester.findByDateend", query = "SELECT s FROM Semester s WHERE s.dateend = :dateend")})
public class Semester implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SEMESTERCODE")
    private String semestercode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEMESTERNO")
    private int semesterno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "year")
    private int year;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATESTART")
    @Temporal(TemporalType.DATE)
    private Date datestart;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATEEND")
    @Temporal(TemporalType.DATE)
    private Date dateend;
    @OneToMany(mappedBy = "semestercode")
    private Collection<Course> courseCollection;

    public Semester() {
    }

    public Semester(String semestercode) {
        this.semestercode = semestercode;
    }

    public Semester(String semestercode, int semesterno, int year, Date datestart, Date dateend) {
        this.semestercode = semestercode;
        this.semesterno = semesterno;
        this.year = year;
        this.datestart = datestart;
        this.dateend = dateend;
    }

    public String getSemestercode() {
        return semestercode;
    }

    public void setSemestercode(String semestercode) {
        this.semestercode = semestercode;
    }

    public int getSemesterno() {
        return semesterno;
    }

    public void setSemesterno(int semesterno) {
        this.semesterno = semesterno;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getDatestart() {
        return datestart;
    }

    public void setDatestart(Date datestart) {
        this.datestart = datestart;
    }

    public Date getDateend() {
        return dateend;
    }

    public void setDateend(Date dateend) {
        this.dateend = dateend;
    }

    @XmlTransient
    public Collection<Course> getCourseCollection() {
        return courseCollection;
    }

    public void setCourseCollection(Collection<Course> courseCollection) {
        this.courseCollection = courseCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semestercode != null ? semestercode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semester)) {
            return false;
        }
        Semester other = (Semester) object;
        if ((this.semestercode == null && other.semestercode != null) || (this.semestercode != null && !this.semestercode.equals(other.semestercode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Semester[ semestercode=" + semestercode + " ]";
    }
    
}
