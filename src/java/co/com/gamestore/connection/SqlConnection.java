/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contrato de conexion a las posibles bases de datos
 *
 * @author leon
 */
public class SqlConnection {

    private String propPath = "./src/conf/connection.properties";

    private Properties prop = new Properties();

    private Connection connection;

    private String url;

    private String driver;

    private String userName;

    private String password;

    /**
     * Constructor String, carga la configuracion de un archivo de propiedades
     * en el path del parametro o de la ruta definida en la clase
     *
     * @param path ruta en la que esta el archivo de propiedades a cargar
     * @throws java.io.IOException si no se encuentra el archivo debe lanzarse
     * una excepcion
     */
    public SqlConnection(String path) throws IOException {
        if (path == null || path.isEmpty()) {
            path = this.propPath;
        }
        InputStream input = new FileInputStream(path);
        if (input == null) {
            throw new FileNotFoundException("no es posible encontrar '" + path + "'");
        }
        prop.load(input);
        this.url = prop.getProperty("url");
        this.driver = prop.getProperty("driver");
        this.userName = prop.getProperty("userName");
        this.password = prop.getProperty("password");
    }

    public SqlConnection(String url, String driver, String userName, String password) {
        this.url = url;
        this.driver = driver;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Ejecuta una query nativa para la implementacion concreta de la conexion
     *
     * @param nativeQuery
     * @return
     */
    public Connection getConnection() {
        try {
            if (connection == null) {
                Class.forName(this.driver).newInstance();
                connection = DriverManager.getConnection(url, getUserName(), password);
            }
            return connection;
        } catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void closeConnection() {
        try {
            System.out.println("co.com.gamestore.connection.SqlConnection.closeConnection()");
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
