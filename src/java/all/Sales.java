/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author paddy
 */
@Entity
@Table(name = "SALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sales.findAll", query = "SELECT s FROM Sales s")
    , @NamedQuery(name = "Sales.findByDate", query = "SELECT s FROM Sales s WHERE s.date = :date")
    , @NamedQuery(name = "Sales.findBySaleId", query = "SELECT s FROM Sales s WHERE s.saleId = :saleId")
    , @NamedQuery(name = "Sales.findByItemId", query = "SELECT s FROM Sales s WHERE s.itemId = :itemId")
    , @NamedQuery(name = "Sales.findByCustomerId", query = "SELECT s FROM Sales s WHERE s.customerId = :customerId")})
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SALE_ID")
    private Integer saleId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ITEM_ID")
    private int itemId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CUSTOMER_ID")
    private int customerId;

    public Sales() {
    }

    public Sales(Integer saleId) {
        this.saleId = saleId;
    }

    public Sales(Integer saleId, Date date, int itemId, int customerId) {
        this.saleId = saleId;
        this.date = date;
        this.itemId = itemId;
        this.customerId = customerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (saleId != null ? saleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sales)) {
            return false;
        }
        Sales other = (Sales) object;
        if ((this.saleId == null && other.saleId != null) || (this.saleId != null && !this.saleId.equals(other.saleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "all.Sales[ saleId=" + saleId + " ]";
    }
    
}
