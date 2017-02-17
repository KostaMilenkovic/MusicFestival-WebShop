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
    , @NamedQuery(name = "FestivalPerformer.findByDateStart", query = "SELECT f FROM FestivalPerformer f WHERE f.dateStart = :dateStart")
    , @NamedQuery(name = "FestivalPerformer.findByTimeStart", query = "SELECT f FROM FestivalPerformer f WHERE f.timeStart = :timeStart")
    , @NamedQuery(name = "FestivalPerformer.findByDateEnd", query = "SELECT f FROM FestivalPerformer f WHERE f.dateEnd = :dateEnd")
    , @NamedQuery(name = "FestivalPerformer.findByTimeEnd", query = "SELECT f FROM FestivalPerformer f WHERE f.timeEnd = :timeEnd")})
public class FestivalPerformer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_start")
    @Temporal(TemporalType.DATE)
    private Date dateStart;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_start")
    @Temporal(TemporalType.TIME)
    private Date timeStart;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_end")
    @Temporal(TemporalType.DATE)
    private Date dateEnd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_end")
    @Temporal(TemporalType.TIME)
    private Date timeEnd;
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

    public FestivalPerformer(Integer id, Date dateStart, Date timeStart, Date dateEnd, Date timeEnd) {
        this.id = id;
        this.dateStart = dateStart;
        this.timeStart = timeStart;
        this.dateEnd = dateEnd;
        this.timeEnd = timeEnd;
    }

    public FestivalPerformer(Festival festival, Performer p, Date dateStart, Date timeStart, Date dateEnd, Date timeEnd) {
        this.dateStart = dateStart;
        this.timeStart = timeStart;
        this.dateEnd = dateEnd;
        this.timeEnd = timeEnd;
        this.festival = festival;
        this.performer = p;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
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
