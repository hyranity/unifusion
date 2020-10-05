/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
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
    , @NamedQuery(name = "Programmeparticipant.findByRole", query = "SELECT p FROM Programmeparticipant p WHERE p.role = :role")})
public class Programmeparticipant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PROGRAMMEPARTICIPANTID")
    private String programmeparticipantid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ROLE")
    private String role;
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

    public Programmeparticipant(String programmeparticipantid, String role) {
        this.programmeparticipantid = programmeparticipantid;
        this.role = role;
    }

    public String getProgrammeparticipantid() {
        return programmeparticipantid;
    }

    public void setProgrammeparticipantid(String programmeparticipantid) {
        this.programmeparticipantid = programmeparticipantid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
