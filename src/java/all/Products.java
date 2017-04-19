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


@Entity
@Table(name = "PRODUCTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p")
    , @NamedQuery(name = "Products.findByItemid", query = "SELECT p FROM Products p WHERE p.itemId = :itemId")
    , @NamedQuery(name = "Products.findByName", query = "SELECT p FROM Products p WHERE p.name = :name")
    , @NamedQuery(name = "Products.findBySize", query = "SELECT p FROM Products p WHERE p.size = :size")
    , @NamedQuery(name = "Products.findByPrice", query = "SELECT p FROM Products p WHERE p.price = :price")
    , @NamedQuery(name = "Products.findByColour", query = "SELECT p FROM Products p WHERE p.colour = :colour")
    , @NamedQuery(name = "Products.findByQuantity", query = "SELECT p FROM Products p WHERE p.quantity = :quantity")})

public class Products implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ITEMID")
    private Integer itemId;
    @Size(max = 100)
    @Column(name = "NAME")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SIZE")
    private Double size;
    @Column(name = "PRICE")
    private Double price;
    @Size(max = 50)
    @Column(name = "COLOUR")
    private String colour;
    @Column(name = "QUANTITY")
    private Integer quantity;

    public Products() {
    }

    public Products(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemid) {
        this.itemId = itemid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "all.Products[ itemid=" + itemId + " ]";
    }
    
}
