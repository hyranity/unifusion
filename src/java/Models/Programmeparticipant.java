/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mast3
 */
@Entity
@Table(name = "PROGRAMMEPARTICIPANT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programmeparticipant.findAll", query = "SELECT p FROM Programmeparticipant p")
    , @NamedQuery(name = "Programmeparticipant.findByProgrammeparticipantid", query = "SELECT p FROM Programmeparticipant p WHERE p.programmeparticipantid = :programmeparticipantid")
    , @NamedQuery(name = "Programmeparticipant.findByCgpa", query = "SELECT p FROM Programmeparticipant p WHERE p.cgpa = :cgpa")
    , @NamedQuery(name = "Programmeparticipant.findByIscreator", query = "SELECT p FROM Programmeparticipant p WHERE p.iscreator = :iscreator")
    , @NamedQuery(name = "Programmeparticipant.findByRole", query = "SELECT p FROM Programmeparticipant p WHERE p.role = :role")
    , @NamedQuery(name = "Programmeparticipant.findByStatus", query = "SELECT p FROM Programmeparticipant p WHERE p.status = :status")
    , @NamedQuery(name = "Programmeparticipant.findByGrade", query = "SELECT p FROM Programmeparticipant p WHERE p.grade = :grade")})
public class Programmeparticipant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PROGRAMMEPARTICIPANTID")
    private String programmeparticipantid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CGPA")
    private BigDecimal cgpa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISCREATOR")
    private Boolean iscreator;
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
    @JoinColumn(name = "PARTICIPANTID", referencedColumnName = "PARTICIPANTID")
    @ManyToOne
    private Participant participantid;
    @JoinColumn(name = "PROGRAMMECODE", referencedColumnName = "PROGRAMMECODE")
    @ManyToOne
    private Programme programmecode;

    public Programmeparticipant() {
    }

    public Programmeparticipant(String programmeparticipantid) {
        this.programmeparticipantid = programmeparticipantid;
    }

    public Programmeparticipant(String programmeparticipantid, Boolean iscreator, String role, String status) {
        this.programmeparticipantid = programmeparticipantid;
        this.iscreator = iscreator;
        this.role = role;
        this.status = status;
    }

    public String getProgrammeparticipantid() {
        return programmeparticipantid;
    }

    public void setProgrammeparticipantid(String programmeparticipantid) {
        this.programmeparticipantid = programmeparticipantid;
    }

    public BigDecimal getCgpa() {
        return cgpa;
    }

    public void setCgpa(BigDecimal cgpa) {
        this.cgpa = cgpa;
    }

    public Boolean getIscreator() {
        return iscreator;
    }

    public void setIscreator(Boolean iscreator) {
        this.iscreator = iscreator;
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

    public Participant getParticipantid() {
        return participantid;
    }

    public void setParticipantid(Participant participantid) {
        this.participantid = participantid;
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
        hash += (programmeparticipantid != null ? programmeparticipantid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programmeparticipant)) {
            return false;
        }
        Programmeparticipant other = (Programmeparticipant) object;
        if ((this.programmeparticipantid == null && other.programmeparticipantid != null) || (this.programmeparticipantid != null && !this.programmeparticipantid.equals(other.programmeparticipantid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Programmeparticipant[ programmeparticipantid=" + programmeparticipantid + " ]";
    }
    
}
