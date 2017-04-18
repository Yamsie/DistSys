/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paddy
 */
@Entity
@Table(name = "TROUSERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trousers.findAll", query = "SELECT t FROM Trousers t")
    , @NamedQuery(name = "Trousers.findByItemId", query = "SELECT t FROM Trousers t WHERE t.itemId = :itemId")
    , @NamedQuery(name = "Trousers.findByType", query = "SELECT t FROM Trousers t WHERE t.type = :type")
    , @NamedQuery(name = "Trousers.findBySize", query = "SELECT t FROM Trousers t WHERE t.size = :size")
    , @NamedQuery(name = "Trousers.findByPrice", query = "SELECT t FROM Trousers t WHERE t.price = :price")
    , @NamedQuery(name = "Trousers.findByColour", query = "SELECT t FROM Trousers t WHERE t.colour = :colour")})
public class Trousers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ITEM_ID")
    private Integer itemId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TYPE")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SIZE")
    private double size;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE")
    private double price;
    @Size(max = 20)
    @Column(name = "COLOUR")
    private String colour;

    public Trousers() {
    }

    public Trousers(Integer itemId) {
        this.itemId = itemId;
    }

    public Trousers(Integer itemId, String type, double size, double price) {
        this.itemId = itemId;
        this.type = type;
        this.size = size;
        this.price = price;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trousers)) {
            return false;
        }
        Trousers other = (Trousers) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "all.Trousers[ itemId=" + itemId + " ]";
    }
    
}
