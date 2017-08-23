/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.factory;

import co.com.gamestore.dao.CustomerDaoI;
import co.com.gamestore.dao.OrderDaoI;
import co.com.gamestore.dao.PurchaseDaoI;

/**
 * Clase abstracta que nos permite definir el alcance de la conexion a la base
 * de datos, en este caso esta delimitado por los metodos de inicio y fin de
 * conecixion. Todos lod DAOs que se creen entre estos metodos estaran dentro
 * del alcance
 *
 * @author leon
 */
public abstract class DAOFactoryAbstract {

    //Tipos de DAO soportados por la Factory
    public static final int MYSQL = 1;

    /**
     * Devuelve un CustomerDao dependiendo del tipo de Dao especificado en la
     * implementacion concreta
     *
     * @return customerDao dependiendo de la BD implementada en el dao concreto
     */
    public abstract CustomerDaoI getCustomerDAO();

    /**
     * Devuelve un OrderDao dependiendo del tipo de Dao especificado en la
     * implementacion concreta
     *
     * @return OrderDao dependiendo de la BD implementada en el dao concreto
     */
    public abstract OrderDaoI getOrderDAO();

    /**
     * Devuelve un PurchaseDao dependiendo del tipo de Dao especificado en la
     * implementacion concreta
     *
     * @return PurchaseDao dependiendo de la BD implementada en el dao concreto
     */
    public abstract PurchaseDaoI getPurchaseDAO();

    /**
     * Inicia una conexion a la bd en la implementacion concreta
     */
    public abstract void startConnection();

    /**
     * Termina una conexion a la bd en la implementacion concreta
     */
    public abstract void closeConnection();
}
