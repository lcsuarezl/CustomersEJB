/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.dao;

import co.com.gamestore.dto.CustomerDto;
import co.com.gamestore.dto.CustomerOrderDto;
import co.com.gamestore.dto.CustomerPurchaseDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementa el DAO para MySQL
 *
 * @author leon
 */
public class MySqlCustomerDao extends MySqlDaoAbstract implements CustomerDaoI {

    public MySqlCustomerDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<CustomerDto> customers = new ArrayList<>();
        try {
            Statement st;
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM customer");
            CustomerDto customer;
            while (rs.next()) {
                customer = new CustomerDto();
                customer.setCustomerId(rs.getString(1));
                customer.setCode(rs.getString(2));
                customer.setName(rs.getString(3));
                customer.setAddress(rs.getString(4));
                customer.setDetails(rs.getString(5));
                customer.setOrders(null);
                customer.setPurchases(null);
                customers.add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySqlCustomerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customers;
    }

    @Override
    public CustomerDto getCustomer(String id) {
        OrderDaoI orderDao = new MySqlOrderDao(getConnection());
        PurchaseDaoI purchaseDao = new MySqlPurchaseDao(getConnection());
        try {
            PreparedStatement pst;
            pst = getConnection().prepareStatement("SELECT * FROM customer WHERE customer_id=?");
            pst.setInt(1, Integer.valueOf(id));
            ResultSet rs = pst.executeQuery();
            CustomerDto customer;
            customer = null;
            while (rs.next()) {
                customer = new CustomerDto();
                customer.setCustomerId(rs.getString(1));
                customer.setCode(rs.getString(2));
                customer.setName(rs.getString(3));
                customer.setAddress(rs.getString(4));
                customer.setDetails(rs.getString(5));
                customer.setOrders(orderDao.getAllOrders(id));
                customer.setPurchases(purchaseDao.getAllOrders(id));
            }
            return customer;
        } catch (SQLException ex) {
            Logger.getLogger(MySqlCustomerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String createCustomer(CustomerDto customer) {
        try {
            PreparedStatement pst
                    = getConnection().prepareStatement("INSERT INTO customer "
                            + "(customer_code,customer_name,customer_address,customer_otherdetails) "
                            + "VALUES"
                            + "(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            //pst.setString(1, customer.getCustomerId());
            pst.setString(1, customer.getCode());
            pst.setString(2, customer.getName());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getDetails());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            rs.next();
            Integer id = rs.getInt(1);
            if (id > 0) {
                return id + "";
            } else {
                Logger.getLogger(MySqlCustomerDao.class.getName()).log(Level.INFO, "Error al insertar {0}", customer.getCustomerId());
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySqlCustomerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean updateCustomer(String id, CustomerDto customer) {
        try {
            PreparedStatement pst
                    = getConnection().prepareStatement("UPDATE customer SET "
                            + "customer_code = ?, customer_name = ?, customer_address = ?, customer_otherdetails = ?"
                            + "WHERE  customer_id = ?");
            pst.setString(1, customer.getCode());
            pst.setString(2, customer.getName());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getDetails());
            pst.setInt(5, Integer.valueOf(id));
            if (pst.executeUpdate() == 1) {
                return true;
            } else {
                Logger.getLogger(MySqlCustomerDao.class.getName()).log(Level.INFO, "Error al Actualizar:{0}", id);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySqlCustomerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        try {
            PreparedStatement pst
                    = getConnection().prepareStatement("DELETE FROM customer WHERE customer_id = ?");
            pst.setInt(1, Integer.valueOf(id));
            if (pst.executeUpdate() == 1) {
                return true;
            } else {
                Logger.getLogger(MySqlCustomerDao.class.getName()).log(Level.INFO, "Error al Eliminar:{0}", id);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySqlCustomerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Recibe un listado de ordenes y las asigna al customer con id
     *
     * @param id del customer a asignar las ordenes
     * @param orders listado de ordenes a asignar al customer con id id
     * @return true si se completa la operacion false eoc
     */
    @Override
    public boolean setOrders(String id, List<CustomerOrderDto> orders) {
        OrderDaoI orderDao = new MySqlOrderDao(getConnection());
        for (CustomerOrderDto order : orders) {
            order.setCustomer(id);
            if (orderDao.createOrder(order) == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Recibe un listado de purchases y las asigna al customer con id
     *
     * @param id del customer a asignar las purchases
     * @param purchases listado de purchases a asignar al customer con id id
     * @return true si se completa la operacion false eoc
     */
    @Override
    public boolean setPurchases(String id, List<CustomerPurchaseDto> purchases) {
        PurchaseDaoI purchaseDao = new MySqlPurchaseDao(getConnection());
        for (CustomerPurchaseDto purchase : purchases) {
            purchase.setCustomer(id);
            if (purchaseDao.createPurchase(purchase) == null) {
                return false;
            }
        }
        return true;
    }

}
