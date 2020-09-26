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
@Table(name = "CLASSPARTICIPANT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Classparticipant.findAll", query = "SELECT c FROM Classparticipant c")
    , @NamedQuery(name = "Classparticipant.findByClassparticipantid", query = "SELECT c FROM Classparticipant c WHERE c.classparticipantid = :classparticipantid")
    , @NamedQuery(name = "Classparticipant.findByRole", query = "SELECT c FROM Classparticipant c WHERE c.role = :role")
    , @NamedQuery(name = "Classparticipant.findByStatus", query = "SELECT c FROM Classparticipant c WHERE c.status = :status")
    , @NamedQuery(name = "Classparticipant.findByGrade", query = "SELECT c FROM Classparticipant c WHERE c.grade = :grade")
    , @NamedQuery(name = "Classparticipant.findByExammarks", query = "SELECT c FROM Classparticipant c WHERE c.exammarks = :exammarks")
    , @NamedQuery(name = "Classparticipant.findByCourseworkmarks", query = "SELECT c FROM Classparticipant c WHERE c.courseworkmarks = :courseworkmarks")
    , @NamedQuery(name = "Classparticipant.findByIscreator", query = "SELECT c FROM Classparticipant c WHERE c.iscreator = :iscreator")})
public class Classparticipant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CLASSPARTICIPANTID")
    private String classparticipantid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ROLE")
    private String role;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 2)
    @Column(name = "GRADE")
    private String grade;
    @Column(name = "EXAMMARKS")
    private Integer exammarks;
    @Column(name = "COURSEWORKMARKS")
    private Integer courseworkmarks;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISCREATOR")
    private Boolean iscreator;
    @OneToMany(mappedBy = "classparticipantid")
    private Collection<Attendance> attendanceCollection;
    @OneToMany(mappedBy = "classparticipantid")
    private Collection<Submission> submissionCollection;
    @JoinColumn(name = "CLASSID", referencedColumnName = "CLASSID")
    @ManyToOne
    private Class classid;
    @JoinColumn(name = "PARTICIPANTID", referencedColumnName = "PARTICIPANTID")
    @ManyToOne
    private Participant participantid;

    public Classparticipant() {
    }

    public Classparticipant(String classparticipantid) {
        this.classparticipantid = classparticipantid;
    }

    public Classparticipant(String classparticipantid, String role, String status, Boolean iscreator) {
        this.classparticipantid = classparticipantid;
        this.role = role;
        this.status = status;
        this.iscreator = iscreator;
    }

    public String getClassparticipantid() {
        return classparticipantid;
    }

    public void setClassparticipantid(String classparticipantid) {
        this.classparticipantid = classparticipantid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getExammarks() {
        return exammarks;
    }

    public void setExammarks(Integer exammarks) {
        this.exammarks = exammarks;
    }

    public Integer getCourseworkmarks() {
        return courseworkmarks;
    }

    public void setCourseworkmarks(Integer courseworkmarks) {
        this.courseworkmarks = courseworkmarks;
    }

    public Boolean getIscreator() {
        return iscreator;
    }

    public void setIscreator(Boolean iscreator) {
        this.iscreator = iscreator;
    }

    @XmlTransient
    public Collection<Attendance> getAttendanceCollection() {
        return attendanceCollection;
    }

    public void setAttendanceCollection(Collection<Attendance> attendanceCollection) {
        this.attendanceCollection = attendanceCollection;
    }

    @XmlTransient
    public Collection<Submission> getSubmissionCollection() {
        return submissionCollection;
    }

    public void setSubmissionCollection(Collection<Submission> submissionCollection) {
        this.submissionCollection = submissionCollection;
    }

    public Class getClassid() {
        return classid;
    }

    public void setClassid(Class classid) {
        this.classid = classid;
    }

    public Participant getParticipantid() {
        return participantid;
    }

    public void setParticipantid(Participant participantid) {
        this.participantid = participantid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classparticipantid != null ? classparticipantid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Classparticipant)) {
            return false;
        }
        Classparticipant other = (Classparticipant) object;
        if ((this.classparticipantid == null && other.classparticipantid != null) || (this.classparticipantid != null && !this.classparticipantid.equals(other.classparticipantid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Classparticipant[ classparticipantid=" + classparticipantid + " ]";
    }
    
}
