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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author milenkok
 */
@Entity
@Table(name = "ticket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t"),
    @NamedQuery(name = "Ticket.findById", query = "SELECT t FROM Ticket t WHERE t.id = :id"),
    @NamedQuery(name = "Ticket.findByOwnerId", query = "SELECT t FROM Ticket t WHERE t.ownerId = :ownerId"),
    @NamedQuery(name = "Ticket.findByFestivalId", query = "SELECT t FROM Ticket t WHERE t.festivalId = :festivalId"),
    @NamedQuery(name = "Ticket.findByPerformerId", query = "SELECT t FROM Ticket t WHERE t.performerId = :performerId"),
    @NamedQuery(name = "Ticket.findByFestivalName", query = "SELECT t FROM Ticket t WHERE t.festivalName = :festivalName"),
    @NamedQuery(name = "Ticket.findByPerformerName", query = "SELECT t FROM Ticket t WHERE t.performerName = :performerName"),
    @NamedQuery(name = "Ticket.findByStartDate", query = "SELECT t FROM Ticket t WHERE t.startDate = :startDate"),
    @NamedQuery(name = "Ticket.findByEndDate", query = "SELECT t FROM Ticket t WHERE t.endDate = :endDate"),
    @NamedQuery(name = "Ticket.findByStartTime", query = "SELECT t FROM Ticket t WHERE t.startTime = :startTime"),
    @NamedQuery(name = "Ticket.findByEndTime", query = "SELECT t FROM Ticket t WHERE t.endTime = :endTime"),
    @NamedQuery(name = "Ticket.findByStatus", query = "SELECT t FROM Ticket t WHERE t.status = :status")})
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ownerId")
    private int ownerId;
    @Basic(optional = false)
    @Column(name = "festivalId")
    private int festivalId;
    @Basic(optional = false)
    @Column(name = "performerId")
    private int performerId;
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "festivalName")
    private String festivalName;
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "performerName")
    private String performerName;
    @Basic(optional = false)
    @Column(name = "startDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @Column(name = "startTime")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Basic(optional = false)
    @Column(name = "endTime")
    @Temporal(TemporalType.TIME)
    private Date endTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "status")
    private String status;

    public Ticket() {
    }

    public Ticket(Integer id) {
        this.id = id;
    }

    public Ticket(Integer id, int ownerId, int festivalId, int performerId, String festivalName, String performerName, Date startDate, Date endDate, Date startTime, Date endTime, String status) {
        this.id = id;
        this.ownerId = ownerId;
        this.festivalId = festivalId;
        this.performerId = performerId;
        this.festivalName = festivalName;
        this.performerName = performerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(int festivalId) {
        this.festivalId = festivalId;
    }

    public int getPerformerId() {
        return performerId;
    }

    public void setPerformerId(int performerId) {
        this.performerId = performerId;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public String getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Ticket[ id=" + id + " ]";
    }
    
}
