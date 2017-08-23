/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.dto;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author leon
 */
public class CustomerPurchaseDto implements Serializable {

    private String purchaseId;

    private Date dateOfPurchase;

    private String details;

    private String customer;

    public CustomerPurchaseDto() {
    }

    public CustomerPurchaseDto(String purchaseId, Date dateOfPurchase, String details, String customer) {
        this.purchaseId = purchaseId;
        this.dateOfPurchase = dateOfPurchase;
        this.details = details;
        this.customer = customer;
    }

    /**
     * @return the purchaseId
     */
    public String getPurchaseId() {
        return purchaseId;
    }

    /**
     * @param purchaseId the purchaseId to set
     */
    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    /**
     * @return the dateOfPurchase
     */
    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    /**
     * @param dateOfPurchase the dateOfPurchase to set
     */
    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    /**
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * @return the customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
