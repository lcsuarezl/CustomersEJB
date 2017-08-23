/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.dao;

import co.com.gamestore.dto.CustomerOrderDto;
import co.com.gamestore.dto.CustomerPurchaseDto;
import co.com.gamestore.dto.CustomerDto;

import java.util.List;

/**
 * Intefaz para las diferentes posibles implementaciones del DAO dependiendo del tipo de almacenamiento de los datos
 * @author leon
 */
public interface CustomerDaoI {
    
    /**
     * Retorna todos los clientes existentes en la fuente de datos concreta
     * @return 
     */
    public List<CustomerDto> getAllCustomers();
    
    /**
     * Retorna el customer con el id del parametro
     * @param id del customer a buscar
     * @return customer con el id del parametro null si no se encuentra
     */
    public CustomerDto getCustomer(String id);
    
    /**
     * Retorna un cliente por el Id definido dependiendo de la fuente de datos concreta
     * @param customer cliente a crear
     * @return Id del customer si todo sale bien, null en otro caso
     */
    public String createCustomer(CustomerDto customer);
    
    /**
     * Actualiza el cliente con el Id en la fuente de datos concreta
     * @param id Id del cliente a actualizar
     * @param customer datos del cliente a actualizar
     * @return true si tiene exito false en otro caso
     * 
     */
    public boolean updateCustomer(String id, CustomerDto customer);
    
    /**
     * Elimina el cliente con el id en la fuente de datos concreta
     * @param id id del cliente a eliminar
     * @return true si tiene exito false en otro caso
     */
    public boolean deleteCustomer(String id);
    
    /**
     * Asigna el listado de ordenes al cliente con el id en la fuente de datos concreta
     * @param id Id del cliente
     * @param orders listado de ordenes a asignar
     * @return true si tiene exito false en otro caso
     */
    public boolean setOrders(String id, List<CustomerOrderDto> orders);
        
    /**
     * Asigna el listado de purchases al cliente con el id en la fuente de datos concreta
     * @param id Id del cliente
     * @param purchases listado de purchases a asignar
     * @return true si tiene exito false en otro caso
     */
    public boolean setPurchases(String id, List<CustomerPurchaseDto> purchases);
    
}
