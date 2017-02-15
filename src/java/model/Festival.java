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
@Table(name = "festival")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Festival.findAll", query = "SELECT f FROM Festival f WHERE endDate>CURRENT_DATE"),
    @NamedQuery(name = "Festival.findAllDesc", query = "SELECT f FROM Festival f ORDER BY f.rating DESC"),
    @NamedQuery(name = "Festival.findRecent", query = "SELECT f FROM Festival f WHERE startDate>CURRENT_DATE ORDER BY f. startDate DESC"),
    @NamedQuery(name = "Festival.findById", query = "SELECT f FROM Festival f WHERE f.id = :id"),
    @NamedQuery(name = "Festival.findByName", query = "SELECT f FROM Festival f WHERE f.name = :name"),
    @NamedQuery(name = "Festival.findByPlace", query = "SELECT f FROM Festival f WHERE f.place = :place"),
    @NamedQuery(name = "Festival.findByStartDate", query = "SELECT f FROM Festival f WHERE f.startDate = :startDate"),
    @NamedQuery(name = "Festival.findByEndDate", query = "SELECT f FROM Festival f WHERE f.endDate = :endDate"),
    @NamedQuery(name = "Festival.findByTicketsDay", query = "SELECT f FROM Festival f WHERE f.ticketsDay = :ticketsDay"),
    @NamedQuery(name = "Festival.findByTicketsTotal", query = "SELECT f FROM Festival f WHERE f.ticketsTotal = :ticketsTotal"),
    @NamedQuery(name = "Festival.findByRating", query = "SELECT f FROM Festival f WHERE f.rating = :rating")})
public class Festival implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "place")
    private String place;
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
    @Column(name = "ticketsDay")
    private int ticketsDay;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ticketsTotal")
    private int ticketsTotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private int rating;

    public Festival() {
    }

    public Festival(Integer id) {
        this.id = id;
    }

    public Festival(Integer id, String name, String place, Date startDate, Date endDate, int ticketsDay, int ticketsTotal, int rating) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ticketsDay = ticketsDay;
        this.ticketsTotal = ticketsTotal;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public int getTicketsDay() {
        return ticketsDay;
    }

    public void setTicketsDay(int ticketsDay) {
        this.ticketsDay = ticketsDay;
    }

    public int getTicketsTotal() {
        return ticketsTotal;
    }

    public void setTicketsTotal(int ticketsTotal) {
        this.ticketsTotal = ticketsTotal;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
        if (!(object instanceof Festival)) {
            return false;
        }
        Festival other = (Festival) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Festival[ id=" + id + " ]";
    }
    
}
