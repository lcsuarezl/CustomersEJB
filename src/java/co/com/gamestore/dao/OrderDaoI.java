/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gamestore.dao;

import co.com.gamestore.dto.CustomerOrderDto;
import java.util.List;

/**
 * Intefaz para las diferentes posibles implementaciones del DAO dependiendo del
 * tipo de almacenamiento de los datos
 *
 * @author leon
 */
public interface OrderDaoI {

    public List<CustomerOrderDto> getAllOrders();
    
    public List<CustomerOrderDto> getAllOrders(String customerId);

    public CustomerOrderDto getOrder(String id);

    public String createOrder(CustomerOrderDto co);

    public boolean updateOrder(CustomerOrderDto co);

    public boolean deleteOrder(String id);

}
