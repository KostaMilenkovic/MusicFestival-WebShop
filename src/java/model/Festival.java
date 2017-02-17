/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "festival")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Festival.findAll", query = "SELECT f FROM Festival f"),
    @NamedQuery(name = "Festival.findAllDesc", query = "SELECT f FROM Festival f"),
    @NamedQuery(name = "Festival.findById", query = "SELECT f FROM Festival f WHERE f.id = :id"),
    @NamedQuery(name = "Festival.findTopFiveByRating", query = "SELECT f.id, f.name, f.place, f.startDate, f.endDate, AVG(r.rating) as average FROM Festival f, FestivalRating r WHERE f.id = r.festival GROUP BY f.id ORDER BY average DESC"),
    @NamedQuery(name = "Festival.findMostVisited", query = "SELECT f FROM Festival f ORDER BY numVisits DESC"),
    @NamedQuery(name = "Festival.findMostTicketsBought", query = "SELECT f FROM Festival f, Ticket t WHERE f.id = t.festival GROUP BY f.id ORDER BY COUNT(t.id) DESC")
    , @NamedQuery(name = "Festival.findByName", query = "SELECT f FROM Festival f WHERE f.name = :name")
    , @NamedQuery(name = "Festival.findByPlace", query = "SELECT f FROM Festival f WHERE f.place = :place")
    , @NamedQuery(name = "Festival.findByStartDate", query = "SELECT f FROM Festival f WHERE f.startDate = :startDate")
    , @NamedQuery(name = "Festival.findByEndDate", query = "SELECT f FROM Festival f WHERE f.endDate = :endDate")
    , @NamedQuery(name = "Festival.findByCostDay", query = "SELECT f FROM Festival f WHERE f.costDay = :costDay")
    , @NamedQuery(name = "Festival.findByCostAll", query = "SELECT f FROM Festival f WHERE f.costAll = :costAll")
    , @NamedQuery(name = "Festival.findByUserTicketDay", query = "SELECT f FROM Festival f WHERE f.userTicketDay = :userTicketDay")
    , @NamedQuery(name = "Festival.findByNumTicketsDay", query = "SELECT f FROM Festival f WHERE f.numTicketsDay = :numTicketsDay")
    , @NamedQuery(name = "Festival.findByStatus", query = "SELECT f FROM Festival f WHERE f.status = :status")
    , @NamedQuery(name = "Festival.findRecentFive", query = "SELECT f FROM Festival f WHERE f.endDate>current_date() ORDER BY f.startDate ASC")
    , @NamedQuery(name = "Festival.findInitialized", query = "SELECT f FROM Festival f WHERE f.status = 'initialized'")
    , @NamedQuery(name = "Festival.findByNumVisits", query = "SELECT f FROM Festival f WHERE f.numVisits = :numVisits")})
public class Festival implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "place")
    private String place;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cost_day")
    private int costDay;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cost_all")
    private int costAll;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_ticket_day")
    private int userTicketDay;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_tickets_day")
    private int numTicketsDay;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_visits")
    private int numVisits;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
    private Collection<Ticket> ticketCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
    private Collection<FestivalRating> festivalRatingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
    private Collection<FestivalComment> festivalCommentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
    private Collection<SocialNetwork> socialNetworkCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
    private Collection<FestivalPerformer> festivalPerformerCollection;

    public Festival() {
    }

    public Festival(Integer id) {
        this.id = id;
    }

    public Festival(Integer id, String name, String place, Date startDate, Date endDate, int costDay, int costAll, int userTicketDay, int numTicketsDay, String status, int numVisits) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.costDay = costDay;
        this.costAll = costAll;
        this.userTicketDay = userTicketDay;
        this.numTicketsDay = numTicketsDay;
        this.status = status;
        this.numVisits = numVisits;
    }

    public Festival(String name, String location, Date dateStart, Date dateEnd, Integer priceOneDay, Integer priceAllDays, Integer numTicketsPerUser, Integer numTicketsPerDay, String initialized, int i) {
        this.name = name;
        this.place = location;
        this.startDate = dateStart;
        this.endDate = dateEnd;
        this.costDay = priceOneDay;
        this.costAll = priceAllDays;
        this.userTicketDay = numTicketsPerUser;
        this.numTicketsDay = numTicketsPerDay;
        this.status = initialized;
        this.numVisits = i;
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

    public int getCostDay() {
        return costDay;
    }

    public void setCostDay(int costDay) {
        this.costDay = costDay;
    }

    public int getCostAll() {
        return costAll;
    }

    public void setCostAll(int costAll) {
        this.costAll = costAll;
    }

    public int getUserTicketDay() {
        return userTicketDay;
    }

    public void setUserTicketDay(int userTicketDay) {
        this.userTicketDay = userTicketDay;
    }

    public int getNumTicketsDay() {
        return numTicketsDay;
    }

    public void setNumTicketsDay(int numTicketsDay) {
        this.numTicketsDay = numTicketsDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumVisits() {
        return numVisits;
    }

    public void setNumVisits(int numVisits) {
        this.numVisits = numVisits;
    }

    @XmlTransient
    public Collection<Ticket> getTicketCollection() {
        return ticketCollection;
    }

    public void setTicketCollection(Collection<Ticket> ticketCollection) {
        this.ticketCollection = ticketCollection;
    }

    @XmlTransient
    public Collection<FestivalRating> getFestivalRatingCollection() {
        return festivalRatingCollection;
    }

    public void setFestivalRatingCollection(Collection<FestivalRating> festivalRatingCollection) {
        this.festivalRatingCollection = festivalRatingCollection;
    }

    @XmlTransient
    public Collection<FestivalComment> getFestivalCommentCollection() {
        return festivalCommentCollection;
    }

    public void setFestivalCommentCollection(Collection<FestivalComment> festivalCommentCollection) {
        this.festivalCommentCollection = festivalCommentCollection;
    }

    @XmlTransient
    public Collection<SocialNetwork> getSocialNetworkCollection() {
        return socialNetworkCollection;
    }

    public void setSocialNetworkCollection(Collection<SocialNetwork> socialNetworkCollection) {
        this.socialNetworkCollection = socialNetworkCollection;
    }

    @XmlTransient
    public Collection<FestivalPerformer> getFestivalPerformerCollection() {
        return festivalPerformerCollection;
    }

    public void setFestivalPerformerCollection(Collection<FestivalPerformer> festivalPerformerCollection) {
        this.festivalPerformerCollection = festivalPerformerCollection;
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
