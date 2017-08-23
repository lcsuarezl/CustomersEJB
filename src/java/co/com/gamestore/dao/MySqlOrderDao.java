/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.dao;

import co.com.gamestore.dto.CustomerOrderDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leon
 */
public class MySqlOrderDao extends MySqlDaoAbstract implements OrderDaoI {

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public MySqlOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<CustomerOrderDto> getAllOrders() {
        List<CustomerOrderDto> orders = new ArrayList<>();
        try {
            Statement st;
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM customer_order");
            CustomerOrderDto order;

            while (rs.next()) {
                order = new CustomerOrderDto();
                order.setOrderId(rs.getInt(1) + "");
                order.setCustomer(rs.getInt(2) + "");
                java.util.Date date = formatter.parse(rs.getString(3));
                order.setDateOfOrder(new Date(date.getTime()));
                order.setDetails(rs.getString(4));
                orders.add(order);
            }
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(MySqlOrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    @Override
    public List<CustomerOrderDto> getAllOrders(String customerId) {
        List<CustomerOrderDto> orders = new ArrayList<>();
        try {
            PreparedStatement pst;
            pst = getConnection().prepareStatement("SELECT * FROM customer_order WHERE customer_id=?");
            pst.setString(1, customerId);
            ResultSet rs = pst.executeQuery();
            CustomerOrderDto order;
            order = null;
            while (rs.next()) {
                order = new CustomerOrderDto();
                order.setOrderId(rs.getString(1));
                order.setCustomer(rs.getString(2));
                java.util.Date date = formatter.parse(rs.getString(3));
                order.setDateOfOrder(new Date(date.getTime()));
                order.setDetails(rs.getString(4));
                orders.add(order);
            }
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(MySqlOrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    @Override
    public CustomerOrderDto getOrder(String id) {
        try {
            PreparedStatement pst;
            pst = getConnection().prepareStatement("SELECT * FROM customer_order WHERE order_id=?");
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            CustomerOrderDto order;
            order = null;
            while (rs.next()) {
                order = new CustomerOrderDto();
                order.setOrderId(rs.getString(1));
                order.setCustomer(rs.getString(2));
                java.util.Date date = formatter.parse(rs.getString(3));
                order.setDateOfOrder(new Date(date.getTime()));
                order.setDetails(rs.getString(4));
            }
            return order;
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(MySqlOrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String createOrder(CustomerOrderDto co) {
        try {
            PreparedStatement pst
                    = getConnection().prepareStatement("INSERT INTO customer_order "
                            + "(order_id,customer_id, date_of_order,other_order_details) "
                            + "VALUES"
                            + "(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, co.getOrderId());
            pst.setString(2, co.getCustomer());
            pst.setDate(3, co.getDateOfOrder());
            pst.setString(4, co.getDetails());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            rs.next();
            Integer id = rs.getInt(1);
            if (id > 0) {
                return id + "";
            } else {
                Logger.getLogger(MySqlOrderDao.class.getName()).log(Level.INFO, "Error al insertar {0}", co.getOrderId());
                return null;
            }
        } catch (SQLException exe) {
            Logger.getLogger(MySqlOrderDao.class.getName()).log(Level.SEVERE, null, exe);
        }
        return null;
    }

    @Override
    public boolean updateOrder(CustomerOrderDto co) {
        try {
            PreparedStatement pst
                    = getConnection().prepareStatement("UPDATE customer_order SET "
                            + "customer_id = ?, date_of_order = ?, other_order_details = ?"
                            + "WHERE  order_id = ?;");
            pst.setString(1, co.getCustomer());
            pst.setDate(2, co.getDateOfOrder());
            pst.setString(3, co.getDetails());
            pst.setString(4, co.getOrderId());
            if (pst.executeUpdate() == 1) {
                return true;
            } else {
                Logger.getLogger(MySqlOrderDao.class.getName()).log(Level.INFO, "Error al Actualizar:{0}", co.getOrderId());
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySqlOrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteOrder(String id) {
        try {
            PreparedStatement pst
                    = getConnection().prepareStatement("DELETE FROM customer_order WHERE order_id = ?;");
            pst.setString(1, id);
            if (pst.executeUpdate() == 1) {
                return true;
            } else {
                Logger.getLogger(MySqlOrderDao.class.getName()).log(Level.INFO, "Error al Eliminar:{0}", id);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySqlOrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
