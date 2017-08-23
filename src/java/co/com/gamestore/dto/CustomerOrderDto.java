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
public class CustomerOrderDto implements Serializable {

    private String orderId;

    private Date dateOfOrder;

    private String details;

    private String customer;

    public CustomerOrderDto(String orderId, Date dateOfOrder, String details) {
        this.orderId = orderId;
        this.dateOfOrder = dateOfOrder;
        this.details = details;
    }

    public CustomerOrderDto() {

    }

    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the dateOfOrder
     */
    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    /**
     * @param dateOfOrder the dateOfOrder to set
     */
    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
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
