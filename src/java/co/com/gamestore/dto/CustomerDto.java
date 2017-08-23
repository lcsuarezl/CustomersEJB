/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author leon
 */
public class CustomerDto implements Serializable{
    
    private String address;
    
    private String code;
    
    private String details;
    
    private String customerId;
    
    private String name;
    
    private List<CustomerOrderDto> orders;
    
    private List<CustomerPurchaseDto> purchases;
    
    CustomerDto(String customerId, String name, String code, String details, String address){
        this.customerId = customerId;
        this.name = name;
        this.code = code;
        this.details = details;
        this.address = address;
    }

    public CustomerDto() {
        
    }
    

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
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
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the orders
     */
    public List<CustomerOrderDto> getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(List<CustomerOrderDto> orders) {
        this.orders = orders;
    }

    /**
     * @return the purchases
     */
    public List<CustomerPurchaseDto> getPurchases() {
        return purchases;
    }

    /**
     * @param purchases the purchases to set
     */
    public void setPurchases(List<CustomerPurchaseDto> purchases) {
        this.purchases = purchases;
    }
     
}
