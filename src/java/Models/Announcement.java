/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mast3
 */
@Entity
@Table(name = "ANNOUNCEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Announcement.findAll", query = "SELECT a FROM Announcement a")
    , @NamedQuery(name = "Announcement.findByAnnouncementid", query = "SELECT a FROM Announcement a WHERE a.announcementid = :announcementid")
    , @NamedQuery(name = "Announcement.findByTitle", query = "SELECT a FROM Announcement a WHERE a.title = :title")
    , @NamedQuery(name = "Announcement.findByMessage", query = "SELECT a FROM Announcement a WHERE a.message = :message")
    , @NamedQuery(name = "Announcement.findByDateannounced", query = "SELECT a FROM Announcement a WHERE a.dateannounced = :dateannounced")})
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ANNOUNCEMENTID")
    private String announcementid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "MESSAGE")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATEANNOUNCED")
    @Temporal(TemporalType.DATE)
    private Date dateannounced;
    @JoinColumn(name = "CLASSID", referencedColumnName = "CLASSID")
    @ManyToOne
    private Class classid;
    @JoinColumn(name = "COURSECODE", referencedColumnName = "COURSECODE")
    @ManyToOne
    private Course coursecode;
    @JoinColumn(name = "INSTITUTIONCODE", referencedColumnName = "INSTITUTIONCODE")
    @ManyToOne
    private Institution institutioncode;
    @JoinColumn(name = "POSTERID", referencedColumnName = "PARTICIPANTID")
    @ManyToOne
    private Participant posterid;
    @JoinColumn(name = "PROGRAMMECODE", referencedColumnName = "PROGRAMMECODE")
    @ManyToOne
    private Programme programmecode;

    public Announcement() {
    }

    public Announcement(String announcementid) {
        this.announcementid = announcementid;
    }

    public Announcement(String announcementid, String title, String message, Date dateannounced) {
        this.announcementid = announcementid;
        this.title = title;
        this.message = message;
        this.dateannounced = dateannounced;
    }

    public String getAnnouncementid() {
        return announcementid;
    }

    public void setAnnouncementid(String announcementid) {
        this.announcementid = announcementid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateannounced() {
        return dateannounced;
    }

    public void setDateannounced(Date dateannounced) {
        this.dateannounced = dateannounced;
    }

    public Class getClassid() {
        return classid;
    }

    public void setClassid(Class classid) {
        this.classid = classid;
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

    public Participant getPosterid() {
        return posterid;
    }

    public void setPosterid(Participant posterid) {
        this.posterid = posterid;
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
        hash += (announcementid != null ? announcementid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Announcement)) {
            return false;
        }
        Announcement other = (Announcement) object;
        if ((this.announcementid == null && other.announcementid != null) || (this.announcementid != null && !this.announcementid.equals(other.announcementid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Announcement[ announcementid=" + announcementid + " ]";
    }
    
}
