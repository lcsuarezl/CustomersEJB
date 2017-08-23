/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.dao;

import co.com.gamestore.dto.CustomerPurchaseDto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leon
 */
public class MySqlPurchaseDao extends MySqlDaoAbstract implements PurchaseDaoI {

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public MySqlPurchaseDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<CustomerPurchaseDto> getAllPurchases() {
        List<CustomerPurchaseDto> purchases = new ArrayList<>();
        try {
            Statement st;
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM customer_purchases");
            CustomerPurchaseDto purchase;

            while (rs.next()) {
                purchase = new CustomerPurchaseDto();
                purchase.setPurchaseId(rs.getInt(1) + "");
                java.util.Date date = formatter.parse(rs.getString(3));
                purchase.setDateOfPurchase(new Date(date.getTime()));
                purchase.setDetails(rs.getString(4));
                purchase.setCustomer(rs.getInt(2) + "");
                purchases.add(purchase);
            }
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(MySqlPurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return purchases;
    }

    @Override
    public List<CustomerPurchaseDto> getAllOrders(String customerId) {
        List<CustomerPurchaseDto> purchases = new ArrayList<>();
        try {
            PreparedStatement pst;
            pst = getConnection().prepareStatement("SELECT * FROM customer_purchases WHERE customer_id=?");
            pst.setString(1, customerId);
            ResultSet rs = pst.executeQuery();
            CustomerPurchaseDto purchase;
            purchase = null;
            while (rs.next()) {
                purchase = new CustomerPurchaseDto();
                purchase.setPurchaseId(rs.getString(1));
                purchase.setCustomer(rs.getString(2));
                java.util.Date date = formatter.parse(rs.getString(3));
                purchase.setDateOfPurchase(new Date(date.getTime()));
                purchase.setDetails(rs.getString(4));
                purchases.add(purchase);
            }
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(MySqlPurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return purchases;
    }

    @Override
    public CustomerPurchaseDto getPurchase(String id) {
        try {
            PreparedStatement pst;
            pst = getConnection().prepareStatement("SELECT * FROM customer_purchases WHERE purchase_id=?");
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            CustomerPurchaseDto purchase;
            purchase = null;
            while (rs.next()) {
                purchase = new CustomerPurchaseDto();
                purchase.setPurchaseId(rs.getString(1));
                purchase.setCustomer(rs.getString(2));
                java.util.Date date = formatter.parse(rs.getString(3));
                purchase.setDateOfPurchase(new Date(date.getTime()));
                purchase.setDetails(rs.getString(4));
            }
            return purchase;
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(MySqlPurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String createPurchase(CustomerPurchaseDto purchase) {
        try {
            PreparedStatement pst
                    = getConnection().prepareStatement("INSERT INTO customer_purchases "
                            + "(purchase_id,customer_id, date_of_purchase,other_purchase_details) "
                            + "VALUES"
                            + "(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, purchase.getPurchaseId());
            pst.setString(2, purchase.getCustomer());
            pst.setDate(3, purchase.getDateOfPurchase());
            pst.setString(4, purchase.getDetails());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            rs.next();
            Integer id = rs.getInt(1);
            if (id > 0) {
                return id + "";
            } else {
                Logger.getLogger(MySqlPurchaseDao.class.getName()).log(Level.INFO, "Error al insertar {0}", purchase.getPurchaseId());
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySqlPurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean updatePurchase(CustomerPurchaseDto purchase) {
        try {
            PreparedStatement pst
                    = getConnection().prepareStatement("UPDATE customer_purchases SET "
                            + "customer_id = ?, date_of_purchase = ?, other_purchase_details = ?"
                            + "WHERE  purchase_id = ?;");
            pst.setString(1, purchase.getCustomer());
            pst.setDate(2, purchase.getDateOfPurchase());
            pst.setString(3, purchase.getDetails());
            pst.setString(4, purchase.getPurchaseId());
            if (pst.executeUpdate() == 1) {
                return true;
            } else {
                Logger.getLogger(MySqlPurchaseDao.class.getName()).log(Level.INFO, "Error al Actualizar:{0}", purchase.getPurchaseId());
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySqlPurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deletePurchase(String id) {
        try {
            PreparedStatement pst
                    = getConnection().prepareStatement("DELETE FROM customer_purchases WHERE purchase_id = ?;");
            pst.setString(1, id);
            if (pst.executeUpdate() == 1) {
                return true;
            } else {
                Logger.getLogger(MySqlPurchaseDao.class.getName()).log(Level.INFO, "Error al Eliminar:{0}", id);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySqlPurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
