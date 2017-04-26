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
 * @author will
 */
@Entity
@Table(name = "LOGGING_FACILITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LoggingFacility.findAll", query = "SELECT l FROM LoggingFacility l")
    , @NamedQuery(name = "LoggingFacility.findByLogId", query = "SELECT l FROM LoggingFacility l WHERE l.logId = :logId")
    , @NamedQuery(name = "LoggingFacility.findByUserId", query = "SELECT l FROM LoggingFacility l WHERE l.userId = :userId")
    , @NamedQuery(name = "LoggingFacility.findByLogType", query = "SELECT l FROM LoggingFacility l WHERE l.logType = :logType")
    , @NamedQuery(name = "LoggingFacility.findByOrderId", query = "SELECT l FROM LoggingFacility l WHERE l.orderId = :orderId")
    , @NamedQuery(name = "LoggingFacility.findByItemId", query = "SELECT l FROM LoggingFacility l WHERE l.itemId = :itemId")})
public class LoggingFacility implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOG_ID")
    private Integer logId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USER_ID")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOG_TYPE")
    private Character logType;
    @Column(name = "ORDER_ID")
    private Integer orderId;
    @Column(name = "ITEM_ID")
    private Integer itemId;

    public LoggingFacility() {
    }

    public LoggingFacility(Integer logId) {
        this.logId = logId;
    }

    public LoggingFacility(Integer logId, int userId, Character logType) {
        this.logId = logId;
        this.userId = userId;
        this.logType = logType;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Character getLogType() {
        return logType;
    }

    public void setLogType(Character logType) {
        this.logType = logType;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logId != null ? logId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoggingFacility)) {
            return false;
        }
        LoggingFacility other = (LoggingFacility) object;
        if ((this.logId == null && other.logId != null) || (this.logId != null && !this.logId.equals(other.logId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "all.LoggingFacility[ logId=" + logId + " ]";
    }
    
}
