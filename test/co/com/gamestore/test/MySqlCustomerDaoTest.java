/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.test;

import co.com.gamestore.dao.CustomerDaoI;
import co.com.gamestore.dao.OrderDaoI;
import co.com.gamestore.dao.PurchaseDaoI;
import co.com.gamestore.dto.CustomerDto;
import co.com.gamestore.dto.CustomerOrderDto;
import co.com.gamestore.dto.CustomerPurchaseDto;
import co.com.gamestore.factory.MySqlDAOFactory;
import java.math.BigInteger;
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
public class MySqlCustomerDaoTest {

    private final String[] nombres = {"Andres", "Camilo", "Jose", "Fernando", "Francisco", "Julian", "Marcela", "Carolina", "German", "Manuel", "Leonardo", "Catalina", "Javier"};
    private final String[] apellidos = {"Cardenas", "Contreras", "Zapata", "Oliveiros", "Gil", "Flores", "Suarez", "Ramirez", "Torres", "Perilla", "Guzman", "Luna", "Riaches"};
    private final String[] direccion = {"calle", "carrera", "diagonal", "transversal"};
    private final String[] codigo = {"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"};
    private final SecureRandom random = new SecureRandom();

    private final MySqlDAOFactory daoFactory = new MySqlDAOFactory();

    public MySqlCustomerDaoTest() {
    }

    /**
     * Cargamos algunos datos
     */
    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Valida la creacion de clientes en la bd utilizando nuestro dao
     *
     * @throws Exception
     */
    @Test
    public void createCustomer() throws Exception {
        CustomerDaoI dao = daoFactory.getCustomerDAO();
        CustomerDto customer;
        boolean created = true;
        customer = new CustomerDto();
        for (int i = 1; i < 10; i++) {
            customer = this.getCustomer(customer);
            String id = dao.createCustomer(customer);
            if (id == null) {
                created = false;
            }
        }
        assertTrue(created);
    }

    @Test
    public void getCustomer() throws Exception {
        CustomerDaoI dao = daoFactory.getCustomerDAO();
        List<CustomerDto> customers = dao.getAllCustomers();
        int size = customers.size();
        CustomerDto customer = dao.getCustomer(customers.get(random.nextInt(size)).getCustomerId());
        assertNotNull(customer);
    }

    /**
     * Valida el listar todo de nuestro dao
     *
     * @throws Exception
     */
    @Test
    public void getAllCustomers() throws Exception {
        CustomerDaoI dao = daoFactory.getCustomerDAO();
        List<CustomerDto> customers = dao.getAllCustomers();
        int result = customers.size();
        assertTrue(result >= 0);
    }

    /**
     * Valida el actualizar todo de nuestro dao
     *
     * @throws Exception
     */
    @Test
    public void updateCustomer() throws Exception {
        CustomerDaoI dao = daoFactory.getCustomerDAO();
        List<CustomerDto> customers = dao.getAllCustomers();
        int size = customers.size();
        CustomerDto customer = dao.getCustomer(customers.get(random.nextInt(size)).getCustomerId());
        CustomerDto newCustomer = this.getCustomer(null);
        boolean done = dao.updateCustomer(customer.getCustomerId(), newCustomer);
        //La operacion se ejecuto
        assertTrue(done);
        //buscamos nuestro nuevo customer
        newCustomer = dao.getCustomer(customer.getCustomerId());
        //validamos que el update haya cambiado los datos
        assertEquals(customer.getCustomerId(), newCustomer.getCustomerId());
        assertNotEquals(customer.getAddress(), newCustomer.getAddress());
        assertNotEquals(customer.getCode(), newCustomer.getCode());
        assertNotEquals(customer.getName(), newCustomer.getName());
        assertNotEquals(customer.getDetails(), newCustomer.getDetails());
    }

    /**
     * Valida la eliminacion en nuestro dao Verifica que la operacion de
     * eliminar se lleve a cabo Busca nuevamente el elemento para ver si retorna
     *
     * @throws Exception
     */
    @Test
    public void deleteCustomer() throws Exception {
        CustomerDaoI dao = daoFactory.getCustomerDAO();
        List<CustomerDto> customers = dao.getAllCustomers();
        int size = customers.size();
        CustomerDto customer = dao.getCustomer(customers.get(random.nextInt(size)).getCustomerId());
        boolean done = dao.deleteCustomer(customer.getCustomerId());
        //La operacion se ejecuto
        assertTrue(done);
        CustomerDto verify = dao.getCustomer(customer.getCustomerId());
        assertNull(verify);
    }

    /**
     * Probar si al traer un customer con un id que tiene ordenes trae tambien
     * las ordenes Debe correr bien luego de ejecutar las pruebas sobre Ordenes
     *
     * @throws Exception
     */
    @Test
    public void getCustomerWithOrders() throws Exception {
        OrderDaoI dao = daoFactory.getOrderDAO();
        List<CustomerOrderDto> orders = dao.getAllOrders();
        int size = orders.size();
        String customerId = orders.get(random.nextInt(size)).getCustomer();
        //Deberia traer un customer que tenga ordenes asignadas
        CustomerDaoI daoCustomer = daoFactory.getCustomerDAO();
        CustomerDto customer = daoCustomer.getCustomer(customerId);
        assertTrue(customer.getOrders().size() > 0);
    }

    /**
     * Probar si al traer un customer con un id que tiene purchases trae tambien
     * las purchases Debe correr bien luego de ejecutar las pruebas sobre
     * Purchases
     *
     * @throws Exception
     */
    @Test
    public void getCustomerWithPurchases() throws Exception {
        PurchaseDaoI dao = daoFactory.getPurchaseDAO();
        List<CustomerPurchaseDto> purchases = dao.getAllPurchases();
        int size = purchases.size();
        String customerId = purchases.get(random.nextInt(size)).getCustomer();
        //Deberia traer un customer que tenga ordenes asignadas
        CustomerDaoI daoCustomer = daoFactory.getCustomerDAO();
        CustomerDto customer = daoCustomer.getCustomer(customerId);
        assertTrue(customer.getPurchases().size() > 0);
    }

    /**
     * Iniciamos la conexion a la BD Cargamos algunos datos para poder correr
     * los test bien
     */
    @Before
    public void setUp() {
        daoFactory.startConnection();
        loadCustomers();
        loadOrders();
        loadPurchases();
    }

    /**
     * cerramos la conexion a la BD
     */
    @After
    public void tearDown() {
        daoFactory.closeConnection();
    }

    public void loadCustomers() {
        CustomerDaoI dao = daoFactory.getCustomerDAO();
        CustomerDto customer;
        customer = new CustomerDto();
        for (int i = 1; i < 10; i++) {
            customer = this.getCustomer(customer);
            dao.createCustomer(customer);
        }
    }

    public void loadOrders() {
        OrderDaoI dao = daoFactory.getOrderDAO();
        CustomerOrderDto co;
        co = new CustomerOrderDto();
        for (int i = 1; i < 10; i++) {
            co = getCustomerOrder(co);
            dao.createOrder(co);
        }
    }

    public void loadPurchases() {
        PurchaseDaoI dao = daoFactory.getPurchaseDAO();
        int size = dao.getAllPurchases().size();
        CustomerPurchaseDto cp;
        cp = new CustomerPurchaseDto();
        for (int i = 1; i < 10; i++) {
            cp = getCustomerPurchase(cp);
            dao.createPurchase(cp);
        }
    }

    public CustomerDto getCustomer(CustomerDto customer) {
        if (customer == null) {
            customer = new CustomerDto();
        }
        customer.setCode(new BigInteger(130, random).toString(32));
        customer.setDetails("Some details for customer " + customer.getCode());
        customer.setName(nombres[random.nextInt(12)] + " " + apellidos[random.nextInt(12)]);
        customer.setAddress(direccion[random.nextInt(4)] + " " + (10 + random.nextInt(100)) + " No " + (10 + random.nextInt(100)) + " - " + (10 + random.nextInt(100)));
        return customer;
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

    public CustomerPurchaseDto getCustomerPurchase(CustomerPurchaseDto co) {
        if (co == null) {
            co = new CustomerPurchaseDto();
        }
        String customerId = co.getCustomer();
        if (co.getCustomer() == null) {
            CustomerDaoI dao = daoFactory.getCustomerDAO();
            List<CustomerDto> customers = dao.getAllCustomers();
            int size = customers.size();
            customerId = customers.get(random.nextInt(size)).getCustomerId();
        }
        co.setCustomer(customerId);
        co.setDateOfPurchase(new Date(100 + random.nextInt(10), random.nextInt(12), random.nextInt(28)));
        co.setDetails("details 4  " + co.getDateOfPurchase().toString());
        return co;
    }

}
