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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paddy
 */
@Entity
@Table(name = "TRANSACTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t")
    , @NamedQuery(name = "Transactions.findByTransId", query = "SELECT t FROM Transactions t WHERE t.transId = :transId")
    , @NamedQuery(name = "Transactions.findBySaleId", query = "SELECT t FROM Transactions t WHERE t.saleId = :saleId")
    , @NamedQuery(name = "Transactions.findByReturnId", query = "SELECT t FROM Transactions t WHERE t.returnId = :returnId")
    , @NamedQuery(name = "Transactions.findByItemId", query = "SELECT t FROM Transactions t WHERE t.itemId = :itemId")
    , @NamedQuery(name = "Transactions.findByPrice", query = "SELECT t FROM Transactions t WHERE t.price = :price")})
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANS_ID")
    private Integer transId;
    @Column(name = "SALE_ID")
    private Integer saleId;
    @Column(name = "RETURN_ID")
    private Integer returnId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ITEM_ID")
    private int itemId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE")
    private int price;

    public Transactions() {
    }

    public Transactions(Integer transId) {
        this.transId = transId;
    }

    public Transactions(Integer transId, int itemId, int price) {
        this.transId = transId;
        this.itemId = itemId;
        this.price = price;
    }

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Integer getReturnId() {
        return returnId;
    }

    public void setReturnId(Integer returnId) {
        this.returnId = returnId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transId != null ? transId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.transId == null && other.transId != null) || (this.transId != null && !this.transId.equals(other.transId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "all.Transactions[ transId=" + transId + " ]";
    }
    
}
