/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.test;

import co.com.gamestore.connection.SqlConnection;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Prueba unitaria para la conexion a la fuente de datos
 *
 * @author leon
 */
public class SqlConnectionTest {

    public SqlConnectionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Cuando el path que se pasa como parametro es errado debe lanzar una
     * excepcion
     *
     * @throws Exception
     */
    @Test(expected = IOException.class)
    public void connectionNull() throws Exception {
        SqlConnection sqlConn = new SqlConnection("faill");
    }

    /**
     * Probamos la carga de los parametros de la conexion a mysql por medio del
     * archivo de propiedades
     *
     * @throws Exception
     */
    @Test
    public void connectionOk() throws Exception {
        SqlConnection sqlConn = new SqlConnection("");
        assertTrue(sqlConn.getDriver().equals("com.mysql.jdbc.Driver"));
        assertTrue(sqlConn.getUrl().equals("jdbc:mysql://localhost:3306/customer"));
        assertTrue(sqlConn.getUserName().equals("root"));
        assertTrue(sqlConn.getPassword().equals("1111"));
    }

}
