/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.dao;

import co.com.gamestore.dto.CustomerPurchaseDto;
import java.util.List;

/**
 * Intefaz para las diferentes posibles implementaciones del DAO dependiendo del
 * tipo de almacenamiento de los datos
 *
 * @author leon
 */
public interface PurchaseDaoI {

    /**
     * Retorna el listado de todas las purchases de la fuente de datos concreta
     *
     * @return
     */
    public List<CustomerPurchaseDto> getAllPurchases();

    /**
     * Retorna todas las purchases de un customer especifico
     *
     * @param customerId el id del customer
     * @return listado de purchases del customer con id customerId
     */
     public List<CustomerPurchaseDto> getAllOrders(String customerId);

    /**
     * Retorna la purchase con el id
     *
     * @param id id de la purchase
     * @return orden con el id id null en otro caso
     */
    public CustomerPurchaseDto getPurchase(String id);

    /**
     * Crea un purchase
     *
     * @param purchase orden a crear
     * @return El id de la purchase recien creada
     */
    public String createPurchase(CustomerPurchaseDto purchase);

    /**
     * Actualiza una purchase
     *
     * @param purchase
     * @return true si fue exitoso, false eoc
     */
    public boolean updatePurchase(CustomerPurchaseDto purchase);

    /**
     * Eliminar una purchase
     *
     * @param id de la purchase a eliminar
     * @return true si se elimino correctamente false eoc
     */
    public boolean deletePurchase(String id);

}
