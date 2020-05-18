/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "SUBMISSION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Submission.findAll", query = "SELECT s FROM Submission s")
    , @NamedQuery(name = "Submission.findBySubmissionid", query = "SELECT s FROM Submission s WHERE s.submissionid = :submissionid")
    , @NamedQuery(name = "Submission.findByItemurl", query = "SELECT s FROM Submission s WHERE s.itemurl = :itemurl")
    , @NamedQuery(name = "Submission.findByMarks", query = "SELECT s FROM Submission s WHERE s.marks = :marks")
    , @NamedQuery(name = "Submission.findByDatesubmitted", query = "SELECT s FROM Submission s WHERE s.datesubmitted = :datesubmitted")})
public class Submission implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SUBMISSIONID")
    private String submissionid;
    @Size(max = 100)
    @Column(name = "ITEMURL")
    private String itemurl;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MARKS")
    private BigDecimal marks;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATESUBMITTED")
    @Temporal(TemporalType.DATE)
    private Date datesubmitted;
    @JoinColumn(name = "COMPONENTID", referencedColumnName = "COMPONENTID")
    @ManyToOne
    private Gradedcomponent componentid;
    @JoinColumn(name = "PARTICIPANTID", referencedColumnName = "PARTICIPANTID")
    @ManyToOne
    private Participant participantid;

    public Submission() {
    }

    public Submission(String submissionid) {
        this.submissionid = submissionid;
    }

    public Submission(String submissionid, Date datesubmitted) {
        this.submissionid = submissionid;
        this.datesubmitted = datesubmitted;
    }

    public String getSubmissionid() {
        return submissionid;
    }

    public void setSubmissionid(String submissionid) {
        this.submissionid = submissionid;
    }

    public String getItemurl() {
        return itemurl;
    }

    public void setItemurl(String itemurl) {
        this.itemurl = itemurl;
    }

    public BigDecimal getMarks() {
        return marks;
    }

    public void setMarks(BigDecimal marks) {
        this.marks = marks;
    }

    public Date getDatesubmitted() {
        return datesubmitted;
    }

    public void setDatesubmitted(Date datesubmitted) {
        this.datesubmitted = datesubmitted;
    }

    public Gradedcomponent getComponentid() {
        return componentid;
    }

    public void setComponentid(Gradedcomponent componentid) {
        this.componentid = componentid;
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
        hash += (submissionid != null ? submissionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Submission)) {
            return false;
        }
        Submission other = (Submission) object;
        if ((this.submissionid == null && other.submissionid != null) || (this.submissionid != null && !this.submissionid.equals(other.submissionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Submission[ submissionid=" + submissionid + " ]";
    }
    
}
