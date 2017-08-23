/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.factory;

import co.com.gamestore.connection.SqlConnection;
import co.com.gamestore.dao.CustomerDaoI;
import co.com.gamestore.dao.OrderDaoI;
import co.com.gamestore.dao.PurchaseDaoI;
import co.com.gamestore.dao.MySqlCustomerDao;
import co.com.gamestore.dao.MySqlOrderDao;
import co.com.gamestore.dao.MySqlPurchaseDao;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementacion del Factory para MySql Gestiona la creacion de los DAOs
 * concretos para MySQL y la conexion y cierre de conexion
 *
 * @author leon
 */
public class MySqlDAOFactory extends DAOFactoryAbstract {

    private SqlConnection sqlConnection;

    private Connection connection;

    /**
     * Retorna un DAO concreto para MySQL de Customer con la conexion iniciada
     * en el Factory
     *
     * @return
     */
    @Override
    public CustomerDaoI getCustomerDAO() {
        return new MySqlCustomerDao(getConnection());
    }

    /**
     * Retorna un DAO concreto para MySQL de Order con la conexion iniciada en
     * el Factory
     *
     * @return
     */
    @Override
    public OrderDaoI getOrderDAO() {
        return new MySqlOrderDao(getConnection());
    }

    /**
     * Retorna un DAO concreto para MySQL de Purchase con la conexion iniciada
     * en el Factory
     *
     * @return
     */
    @Override
    public PurchaseDaoI getPurchaseDAO() {
        return new MySqlPurchaseDao(getConnection());
    }

    /**
     * Inicia la conexion a la BD de nuestro factory, es el primer metodo que
     * debe llamarse antes de generar los DAOs de cada DTO.
     */
    @Override
    public void startConnection() {
        try {
            sqlConnection = new SqlConnection("");
            connection = sqlConnection.getConnection();
        } catch (IOException ex) {
            Logger.getLogger(MySqlDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Termina la conexion, debe llamarse al terminar de usar la conexion.
     */
    @Override
    public void closeConnection() {
        sqlConnection.closeConnection();
    }

    private Connection getConnection() {
        return connection;
    }

}
