/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author milenkok
 */
@Entity
@Table(name = "performer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Performer.findAll", query = "SELECT p FROM Performer p"),
    @NamedQuery(name = "Performer.findById", query = "SELECT p FROM Performer p WHERE p.id = :id"),
    @NamedQuery(name = "Performer.findByIdFestival", query = "SELECT p FROM Performer p WHERE p.idFestival = :idFestival"),
    @NamedQuery(name = "Performer.findByStartDate", query = "SELECT p FROM Performer p WHERE p.startDate = :startDate"),
    @NamedQuery(name = "Performer.findByEndDate", query = "SELECT p FROM Performer p WHERE p.endDate = :endDate"),
    @NamedQuery(name = "Performer.findByStartTime", query = "SELECT p FROM Performer p WHERE p.startTime = :startTime"),
    @NamedQuery(name = "Performer.findByEndTime", query = "SELECT p FROM Performer p WHERE p.endTime = :endTime")})
public class Performer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idFestival")
    private int idFestival;
    @Basic(optional = false)
    @NotNull
    @Column(name = "startDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "startTime")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "endTime")
    @Temporal(TemporalType.TIME)
    private Date endTime;

    public Performer() {
    }

    public Performer(Integer id) {
        this.id = id;
    }

    public Performer(Integer id, int idFestival, Date startDate, Date endDate, Date startTime, Date endTime) {
        this.id = id;
        this.idFestival = idFestival;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdFestival() {
        return idFestival;
    }

    public void setIdFestival(int idFestival) {
        this.idFestival = idFestival;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Performer)) {
            return false;
        }
        Performer other = (Performer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Performer[ id=" + id + " ]";
    }
    
}
