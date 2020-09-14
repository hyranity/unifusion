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
@Table(name = "ATTENDANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attendance.findAll", query = "SELECT a FROM Attendance a")
    , @NamedQuery(name = "Attendance.findByAttendanceid", query = "SELECT a FROM Attendance a WHERE a.attendanceid = :attendanceid")
    , @NamedQuery(name = "Attendance.findByDateattended", query = "SELECT a FROM Attendance a WHERE a.dateattended = :dateattended")})
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ATTENDANCEID")
    private String attendanceid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATEATTENDED")
    @Temporal(TemporalType.DATE)
    private Date dateattended;
    @JoinColumn(name = "CLASSPARTICIPANTID", referencedColumnName = "CLASSPARTICIPANTID")
    @ManyToOne
    private Classparticipant classparticipantid;
    @JoinColumn(name = "SESSIONID", referencedColumnName = "SESSIONID")
    @ManyToOne
    private Session sessionid;

    public Attendance() {
    }

    public Attendance(String attendanceid) {
        this.attendanceid = attendanceid;
    }

    public Attendance(String attendanceid, Date dateattended) {
        this.attendanceid = attendanceid;
        this.dateattended = dateattended;
    }

    public String getAttendanceid() {
        return attendanceid;
    }

    public void setAttendanceid(String attendanceid) {
        this.attendanceid = attendanceid;
    }

    public Date getDateattended() {
        return dateattended;
    }

    public void setDateattended(Date dateattended) {
        this.dateattended = dateattended;
    }

    public Classparticipant getClassparticipantid() {
        return classparticipantid;
    }

    public void setClassparticipantid(Classparticipant classparticipantid) {
        this.classparticipantid = classparticipantid;
    }

    public Session getSessionid() {
        return sessionid;
    }

    public void setSessionid(Session sessionid) {
        this.sessionid = sessionid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attendanceid != null ? attendanceid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attendance)) {
            return false;
        }
        Attendance other = (Attendance) object;
        if ((this.attendanceid == null && other.attendanceid != null) || (this.attendanceid != null && !this.attendanceid.equals(other.attendanceid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Attendance[ attendanceid=" + attendanceid + " ]";
    }
    
}
