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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PARTICIPANT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Participant.findAll", query = "SELECT p FROM Participant p")
    , @NamedQuery(name = "Participant.findByParticipantid", query = "SELECT p FROM Participant p WHERE p.participantid = :participantid")
    , @NamedQuery(name = "Participant.findByDateadded", query = "SELECT p FROM Participant p WHERE p.dateadded = :dateadded")
    , @NamedQuery(name = "Participant.findByEducatorrole", query = "SELECT p FROM Participant p WHERE p.educatorrole = :educatorrole")
    , @NamedQuery(name = "Participant.findByStatus", query = "SELECT p FROM Participant p WHERE p.status = :status")})
public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PARTICIPANTID")
    private String participantid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATEADDED")
    @Temporal(TemporalType.DATE)
    private Date dateadded;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "EDUCATORROLE")
    private String educatorrole;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne
    private Users userid;
    @OneToMany(mappedBy = "posterid")
    private Collection<Announcement> announcementCollection;
    @OneToMany(mappedBy = "participantid")
    private Collection<Courseparticipant> courseparticipantCollection;
    @OneToMany(mappedBy = "participantid")
    private Collection<Institutionparticipant> institutionparticipantCollection;
    @OneToMany(mappedBy = "participantid")
    private Collection<Classparticipant> classparticipantCollection;
    @OneToMany(mappedBy = "participantid")
    private Collection<Programmeparticipant> programmeparticipantCollection;

    public Participant() {
    }

    public Participant(String participantid) {
        this.participantid = participantid;
    }

    public Participant(String participantid, Date dateadded, String educatorrole, String status) {
        this.participantid = participantid;
        this.dateadded = dateadded;
        this.educatorrole = educatorrole;
        this.status = status;
    }

    public String getParticipantid() {
        return participantid;
    }

    public void setParticipantid(String participantid) {
        this.participantid = participantid;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }

    public String getEducatorrole() {
        return educatorrole;
    }

    public void setEducatorrole(String educatorrole) {
        this.educatorrole = educatorrole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
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
    public Collection<Institutionparticipant> getInstitutionparticipantCollection() {
        return institutionparticipantCollection;
    }

    public void setInstitutionparticipantCollection(Collection<Institutionparticipant> institutionparticipantCollection) {
        this.institutionparticipantCollection = institutionparticipantCollection;
    }

    @XmlTransient
    public Collection<Classparticipant> getClassparticipantCollection() {
        return classparticipantCollection;
    }

    public void setClassparticipantCollection(Collection<Classparticipant> classparticipantCollection) {
        this.classparticipantCollection = classparticipantCollection;
    }

    @XmlTransient
    public Collection<Programmeparticipant> getProgrammeparticipantCollection() {
        return programmeparticipantCollection;
    }

    public void setProgrammeparticipantCollection(Collection<Programmeparticipant> programmeparticipantCollection) {
        this.programmeparticipantCollection = programmeparticipantCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (participantid != null ? participantid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Participant)) {
            return false;
        }
        Participant other = (Participant) object;
        if ((this.participantid == null && other.participantid != null) || (this.participantid != null && !this.participantid.equals(other.participantid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Participant[ participantid=" + participantid + " ]";
    }
    
}
