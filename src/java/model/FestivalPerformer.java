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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "festival_performer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FestivalPerformer.findAll", query = "SELECT f FROM FestivalPerformer f")
    , @NamedQuery(name = "FestivalPerformer.findById", query = "SELECT f FROM FestivalPerformer f WHERE f.id = :id")
    , @NamedQuery(name = "FestivalPerformer.findByDate", query = "SELECT f FROM FestivalPerformer f WHERE f.date = :date")
    , @NamedQuery(name = "FestivalPerformer.findByTime", query = "SELECT f FROM FestivalPerformer f WHERE f.time = :time")})
public class FestivalPerformer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time")
    @Temporal(TemporalType.TIME)
    private Date time;
    @JoinColumn(name = "performer", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Performer performer;
    @JoinColumn(name = "festival", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Festival festival;

    public FestivalPerformer() {
    }

    public FestivalPerformer(Integer id) {
        this.id = id;
    }

    public FestivalPerformer(Integer id, Date date, Date time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Performer getPerformer() {
        return performer;
    }

    public void setPerformer(Performer performer) {
        this.performer = performer;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
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
        if (!(object instanceof FestivalPerformer)) {
            return false;
        }
        FestivalPerformer other = (FestivalPerformer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.FestivalPerformer[ id=" + id + " ]";
    }
    
}
