/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otherEntity;

import dz.elit.gpecpf.commun.util.StaticUtil;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "moyen", schema = StaticUtil.ADMINISTRATION_SCHEMA)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Moyen.findAll", query = "SELECT m FROM Moyen m")
    , @NamedQuery(name = "Moyen.findById", query = "SELECT m FROM Moyen m WHERE m.id = :id")
    , @NamedQuery(name = "Moyen.findByCode", query = "SELECT m FROM Moyen m WHERE m.code = :code")
    , @NamedQuery(name = "Moyen.findByDescription", query = "SELECT m FROM Moyen m WHERE m.description = :description")})
public class Moyen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "code")
    private String code;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @JoinTable(name = "postemoyen", joinColumns = {
        @JoinColumn(name = "idmoyen", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "idposte", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Poste> posteCollection;

    public Moyen() {
    }

    public Moyen(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Poste> getPosteCollection() {
        return posteCollection;
    }

    public void setPosteCollection(Collection<Poste> posteCollection) {
        this.posteCollection = posteCollection;
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
        if (!(object instanceof Moyen)) {
            return false;
        }
        Moyen other = (Moyen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otherEntity.Moyen[ id=" + id + " ]";
    }
    
}
