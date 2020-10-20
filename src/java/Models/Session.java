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
@Table(name = "SESSION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Session.findAll", query = "SELECT s FROM Session s")
    , @NamedQuery(name = "Session.findBySessionid", query = "SELECT s FROM Session s WHERE s.sessionid = :sessionid")
    , @NamedQuery(name = "Session.findByTempvenuename", query = "SELECT s FROM Session s WHERE s.tempvenuename = :tempvenuename")
    , @NamedQuery(name = "Session.findByStarttime", query = "SELECT s FROM Session s WHERE s.starttime = :starttime")
    , @NamedQuery(name = "Session.findByEndtime", query = "SELECT s FROM Session s WHERE s.endtime = :endtime")
    , @NamedQuery(name = "Session.findByIsreplacement", query = "SELECT s FROM Session s WHERE s.isreplacement = :isreplacement")})
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "SESSIONID")
    private String sessionid;
    @Size(max = 25)
    @Column(name = "TEMPVENUENAME")
    private String tempvenuename;
    @Column(name = "STARTTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date starttime;
    @Column(name = "ENDTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endtime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISREPLACEMENT")
    private Boolean isreplacement;
    @OneToMany(mappedBy = "sessionid")
    private Collection<Attendance> attendanceCollection;
    @JoinColumn(name = "CLASSID", referencedColumnName = "CLASSID")
    @ManyToOne
    private Class classid;
    @JoinColumn(name = "CREATORID", referencedColumnName = "CLASSPARTICIPANTID")
    @ManyToOne
    private Classparticipant creatorid;
    @JoinColumn(name = "VENUEID", referencedColumnName = "VENUEID")
    @ManyToOne
    private Venue venueid;

    public Session() {
    }

    public Session(String sessionid) {
        this.sessionid = sessionid;
    }

    public Session(String sessionid, Boolean isreplacement) {
        this.sessionid = sessionid;
        this.isreplacement = isreplacement;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getTempvenuename() {
        return tempvenuename;
    }

    public void setTempvenuename(String tempvenuename) {
        this.tempvenuename = tempvenuename;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Boolean getIsreplacement() {
        return isreplacement;
    }

    public void setIsreplacement(Boolean isreplacement) {
        this.isreplacement = isreplacement;
    }

    @XmlTransient
    public Collection<Attendance> getAttendanceCollection() {
        return attendanceCollection;
    }

    public void setAttendanceCollection(Collection<Attendance> attendanceCollection) {
        this.attendanceCollection = attendanceCollection;
    }

    public Class getClassid() {
        return classid;
    }

    public void setClassid(Class classid) {
        this.classid = classid;
    }

    public Classparticipant getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Classparticipant creatorid) {
        this.creatorid = creatorid;
    }

    public Venue getVenueid() {
        return venueid;
    }

    public void setVenueid(Venue venueid) {
        this.venueid = venueid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sessionid != null ? sessionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Session)) {
            return false;
        }
        Session other = (Session) object;
        if ((this.sessionid == null && other.sessionid != null) || (this.sessionid != null && !this.sessionid.equals(other.sessionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Session[ sessionid=" + sessionid + " ]";
    }
    
}
