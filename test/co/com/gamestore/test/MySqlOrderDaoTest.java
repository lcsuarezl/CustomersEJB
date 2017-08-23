/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.test;

import co.com.gamestore.dao.CustomerDaoI;
import co.com.gamestore.dao.OrderDaoI;
import co.com.gamestore.dto.CustomerDto;
import co.com.gamestore.dto.CustomerOrderDto;
import co.com.gamestore.factory.MySqlDAOFactory;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author leon
 */
public class MySqlOrderDaoTest {

    private final SecureRandom random = new SecureRandom();

    private MySqlDAOFactory daoFactory = new MySqlDAOFactory();

    public MySqlOrderDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Iniciamos la conexion a la BD
     */
    @Before
    public void setUp() {
        daoFactory.startConnection();

    }

    /**
     * cerramos la conexion a la BD
     */
    @After
    public void tearDown() {
        daoFactory.closeConnection();
    }

    @Test
    public void getAllOrders() throws Exception {
        OrderDaoI dao = daoFactory.getOrderDAO();
        List<CustomerOrderDto> orders = dao.getAllOrders();
        int result = orders.size();
        assertTrue(result >= 0);
    }

    @Test
    public void getOrder() throws Exception {
        OrderDaoI dao = daoFactory.getOrderDAO();
        List<CustomerOrderDto> orders = dao.getAllOrders();
        int size = orders.size();
        CustomerOrderDto customer = dao.getOrder(orders.get(random.nextInt(size)).getOrderId());
        assertNotNull(customer);
    }

    @Test
    public void createOrder() throws Exception {
        OrderDaoI dao = daoFactory.getOrderDAO();
        CustomerOrderDto co;
        boolean created = true;
        co = new CustomerOrderDto();
        for (int i = 1; i < 10; i++) {
            co = this.getCustomerOrder(co);
            String id = dao.createOrder(co);
            if (id == null) {
                created = false;
            }
        }
        assertTrue(created);
    }

    @Test
    public void updateOrder() throws Exception {
        OrderDaoI dao = daoFactory.getOrderDAO();
        List<CustomerOrderDto> orders = dao.getAllOrders();
        int size = orders.size();
        CustomerOrderDto co = dao.getOrder(orders.get(random.nextInt(size)).getOrderId());
        CustomerOrderDto newCo = this.getCustomerOrder(null);
        newCo.setOrderId(co.getOrderId());
        boolean done = dao.updateOrder(newCo);
        //La operacion se ejecuto
        assertTrue(done);
        //buscamos nuestro nueva orden
        newCo = dao.getOrder(co.getOrderId());
        //validamos que el update haya cambiado los datos
        assertEquals(co.getOrderId(), newCo.getOrderId());
        assertNotEquals(co.getDateOfOrder(), newCo.getDateOfOrder());
        assertNotEquals(co.getDetails(), newCo.getDetails());
    }

    public void deleteOrder() throws Exception {
        OrderDaoI dao = daoFactory.getOrderDAO();
        List<CustomerOrderDto> orders = dao.getAllOrders();
        int size = orders.size();
        CustomerOrderDto co = dao.getOrder(orders.get(random.nextInt(size)).getOrderId());
        boolean done = dao.deleteOrder(co.getOrderId());
        //La operacion se ejecuto
        assertTrue(done);
        CustomerOrderDto verify = dao.getOrder(co.getOrderId());
        assertNull(verify);
    }

    public CustomerOrderDto getCustomerOrder(CustomerOrderDto co) {
        if (co == null) {
            co = new CustomerOrderDto();
        }
        String customerId = co.getCustomer();
        if (co.getCustomer() == null) {
            CustomerDaoI dao = daoFactory.getCustomerDAO();
            List<CustomerDto> customers = dao.getAllCustomers();
            int size = customers.size();
            customerId = customers.get(random.nextInt(size)).getCustomerId();
        }
        co.setCustomer(customerId);
        co.setDateOfOrder(new Date(100 + random.nextInt(10), random.nextInt(12), random.nextInt(28)));
        co.setDetails("details 4  " + co.getDateOfOrder().toString());
        return co;
    }
}
