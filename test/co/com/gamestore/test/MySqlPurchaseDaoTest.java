/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.test;

import co.com.gamestore.dao.CustomerDaoI;
import co.com.gamestore.dao.PurchaseDaoI;
import co.com.gamestore.dto.CustomerDto;
import co.com.gamestore.dto.CustomerPurchaseDto;
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
public class MySqlPurchaseDaoTest {

    private final SecureRandom random = new SecureRandom();

    private final MySqlDAOFactory daoFactory = new MySqlDAOFactory();

    public MySqlPurchaseDaoTest() {
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
    public void getAllPurchases() throws Exception {
        PurchaseDaoI dao = daoFactory.getPurchaseDAO();
        int result = dao.getAllPurchases().size();
        assertTrue(result >= 0);
    }

    @Test
    public void getPruchase() throws Exception {
        PurchaseDaoI dao = daoFactory.getPurchaseDAO();
        List<CustomerPurchaseDto> purchases = dao.getAllPurchases();
        int size = purchases.size();
        CustomerPurchaseDto purchase = dao.getPurchase(purchases.get(random.nextInt(size)).getPurchaseId());
        assertNotNull(purchase);
    }

    @Test
    public void createPurchase() throws Exception {
        PurchaseDaoI dao = daoFactory.getPurchaseDAO();
        int size = dao.getAllPurchases().size();
        CustomerPurchaseDto cp;
        boolean created = true;
        cp = new CustomerPurchaseDto();
        for (int i = 1; i < 10; i++) {
            cp = this.getCustomerPurchase(cp);
            String id = dao.createPurchase(cp);
            if (id == null) {
                created = false;
            }
        }
        assertTrue(created);
    }

    @Test
    public void updatePurchase() throws Exception {
        PurchaseDaoI dao = daoFactory.getPurchaseDAO();
        List<CustomerPurchaseDto> purchases = dao.getAllPurchases();
        int size = purchases.size();
        CustomerPurchaseDto cp = dao.getPurchase(purchases.get(random.nextInt(size)).getPurchaseId());
        CustomerPurchaseDto newCp = this.getCustomerPurchase(null);
        newCp.setPurchaseId(cp.getPurchaseId());
        boolean done = dao.updatePurchase(newCp);
        //La operacion se ejecuto
        assertTrue(done);
        //buscamos nuestro nueva orden
        newCp = dao.getPurchase(cp.getPurchaseId());
        //validamos que el update haya cambiado los datos
        assertEquals(cp.getPurchaseId(), newCp.getPurchaseId());
        assertNotEquals(cp.getDateOfPurchase(), newCp.getDateOfPurchase());
        assertNotEquals(cp.getDetails(), newCp.getDetails());
    }

    public void deletePurchase() throws Exception {
        PurchaseDaoI dao = daoFactory.getPurchaseDAO();
        List<CustomerPurchaseDto> purchases = dao.getAllPurchases();
        int size = purchases.size();
        CustomerPurchaseDto cp = dao.getPurchase(purchases.get(random.nextInt(size)).getPurchaseId());
        boolean done = dao.deletePurchase(cp.getPurchaseId());
        //La operacion se ejecuto
        assertTrue(done);
        CustomerPurchaseDto verify = dao.getPurchase(cp.getPurchaseId());
        assertNull(verify);
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
