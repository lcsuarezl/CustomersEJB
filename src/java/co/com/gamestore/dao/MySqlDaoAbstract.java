/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.dao;

import java.sql.Connection;

/**
 *
 * @author leon
 */
public abstract class MySqlDaoAbstract {

    private final Connection connection;

    public MySqlDaoAbstract(Connection connection) {
        this.connection = connection;
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

}
