/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author milenkok
 */
@Entity
@Table(name = "socialnetwork")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Socialnetwork.findAll", query = "SELECT s FROM Socialnetwork s"),
    @NamedQuery(name = "Socialnetwork.findById", query = "SELECT s FROM Socialnetwork s WHERE s.id = :id"),
    @NamedQuery(name = "Socialnetwork.findByIdFestival", query = "SELECT s FROM Socialnetwork s WHERE s.idFestival = :idFestival"),
    @NamedQuery(name = "Socialnetwork.findByName", query = "SELECT s FROM Socialnetwork s WHERE s.name = :name"),
    @NamedQuery(name = "Socialnetwork.findByLink", query = "SELECT s FROM Socialnetwork s WHERE s.link = :link")})
public class Socialnetwork implements Serializable {

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
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "link")
    private String link;

    public Socialnetwork() {
    }

    public Socialnetwork(Integer id) {
        this.id = id;
    }

    public Socialnetwork(Integer id, int idFestival, String name, String link) {
        this.id = id;
        this.idFestival = idFestival;
        this.name = name;
        this.link = link;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
        if (!(object instanceof Socialnetwork)) {
            return false;
        }
        Socialnetwork other = (Socialnetwork) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Socialnetwork[ id=" + id + " ]";
    }
    
}
