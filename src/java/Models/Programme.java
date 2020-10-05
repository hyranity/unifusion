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
@Table(name = "PROGRAMME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programme.findAll", query = "SELECT p FROM Programme p")
    , @NamedQuery(name = "Programme.findByProgrammecode", query = "SELECT p FROM Programme p WHERE p.programmecode = :programmecode")
    , @NamedQuery(name = "Programme.findByTitle", query = "SELECT p FROM Programme p WHERE p.title = :title")
    , @NamedQuery(name = "Programme.findByDescription", query = "SELECT p FROM Programme p WHERE p.description = :description")
    , @NamedQuery(name = "Programme.findByGradingstyle", query = "SELECT p FROM Programme p WHERE p.gradingstyle = :gradingstyle")})
public class Programme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PROGRAMMECODE")
    private String programmecode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 20)
    @Column(name = "GRADINGSTYLE")
    private String gradingstyle;
    @JoinColumn(name = "INSTITUTIONCODE", referencedColumnName = "INSTITUTIONCODE")
    @ManyToOne
    private Institution institutioncode;
    @JoinColumn(name = "CREATORID", referencedColumnName = "PARTICIPANTID")
    @ManyToOne
    private Participant creatorid;
    @OneToMany(mappedBy = "programmecode")
    private Collection<Course> courseCollection;
    @OneToMany(mappedBy = "programmecode")
    private Collection<Announcement> announcementCollection;
    @OneToMany(mappedBy = "programmecode")
    private Collection<Programmeparticipant> programmeparticipantCollection;

    public Programme() {
    }

    public Programme(String programmecode) {
        this.programmecode = programmecode;
    }

    public Programme(String programmecode, String title, String description) {
        this.programmecode = programmecode;
        this.title = title;
        this.description = description;
    }

    public String getProgrammecode() {
        return programmecode;
    }

    public void setProgrammecode(String programmecode) {
        this.programmecode = programmecode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGradingstyle() {
        return gradingstyle;
    }

    public void setGradingstyle(String gradingstyle) {
        this.gradingstyle = gradingstyle;
    }

    public Institution getInstitutioncode() {
        return institutioncode;
    }

    public void setInstitutioncode(Institution institutioncode) {
        this.institutioncode = institutioncode;
    }

    public Participant getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Participant creatorid) {
        this.creatorid = creatorid;
    }

    @XmlTransient
    public Collection<Course> getCourseCollection() {
        return courseCollection;
    }

    public void setCourseCollection(Collection<Course> courseCollection) {
        this.courseCollection = courseCollection;
    }

    @XmlTransient
    public Collection<Announcement> getAnnouncementCollection() {
        return announcementCollection;
    }

    public void setAnnouncementCollection(Collection<Announcement> announcementCollection) {
        this.announcementCollection = announcementCollection;
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
        hash += (programmecode != null ? programmecode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programme)) {
            return false;
        }
        Programme other = (Programme) object;
        if ((this.programmecode == null && other.programmecode != null) || (this.programmecode != null && !this.programmecode.equals(other.programmecode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Programme[ programmecode=" + programmecode + " ]";
    }
    
}
