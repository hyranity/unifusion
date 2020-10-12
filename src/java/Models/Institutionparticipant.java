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
@Table(name = "INSTITUTIONPARTICIPANT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Institutionparticipant.findAll", query = "SELECT i FROM Institutionparticipant i")
    , @NamedQuery(name = "Institutionparticipant.findByInstitutionparticipantid", query = "SELECT i FROM Institutionparticipant i WHERE i.institutionparticipantid = :institutionparticipantid")
    , @NamedQuery(name = "Institutionparticipant.findByRole", query = "SELECT i FROM Institutionparticipant i WHERE i.role = :role")
    , @NamedQuery(name = "Institutionparticipant.findByStatus", query = "SELECT i FROM Institutionparticipant i WHERE i.status = :status")
    , @NamedQuery(name = "Institutionparticipant.findByIscreator", query = "SELECT i FROM Institutionparticipant i WHERE i.iscreator = :iscreator")})
public class Institutionparticipant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "INSTITUTIONPARTICIPANTID")
    private String institutionparticipantid;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISCREATOR")
    private Boolean iscreator;
    @JoinColumn(name = "INSTITUTIONCODE", referencedColumnName = "INSTITUTIONCODE")
    @ManyToOne
    private Institution institutioncode;
    @JoinColumn(name = "PARTICIPANTID", referencedColumnName = "PARTICIPANTID")
    @ManyToOne
    private Participant participantid;

    public Institutionparticipant() {
    }

    public Institutionparticipant(String institutionparticipantid) {
        this.institutionparticipantid = institutionparticipantid;
    }

    public Institutionparticipant(String institutionparticipantid, String role, String status, Boolean iscreator) {
        this.institutionparticipantid = institutionparticipantid;
        this.role = role;
        this.status = status;
        this.iscreator = iscreator;
    }

    public String getInstitutionparticipantid() {
        return institutionparticipantid;
    }

    public void setInstitutionparticipantid(String institutionparticipantid) {
        this.institutionparticipantid = institutionparticipantid;
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

    public Boolean getIscreator() {
        return iscreator;
    }

    public void setIscreator(Boolean iscreator) {
        this.iscreator = iscreator;
    }

    public Institution getInstitutioncode() {
        return institutioncode;
    }

    public void setInstitutioncode(Institution institutioncode) {
        this.institutioncode = institutioncode;
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
        hash += (institutionparticipantid != null ? institutionparticipantid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Institutionparticipant)) {
            return false;
        }
        Institutionparticipant other = (Institutionparticipant) object;
        if ((this.institutionparticipantid == null && other.institutionparticipantid != null) || (this.institutionparticipantid != null && !this.institutionparticipantid.equals(other.institutionparticipantid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Institutionparticipant[ institutionparticipantid=" + institutionparticipantid + " ]";
    }
    
}
