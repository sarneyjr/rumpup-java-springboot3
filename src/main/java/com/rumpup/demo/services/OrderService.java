package com.rumpup.demo.services;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rumpup.demo.dto.OrderDTO;
import com.rumpup.demo.entities.Address;
import com.rumpup.demo.entities.Customer;
import com.rumpup.demo.entities.Order;
import com.rumpup.demo.entities.OrderItem;
import com.rumpup.demo.entities.ProductOffering;
import com.rumpup.demo.repositories.AddressRepository;
import com.rumpup.demo.repositories.CustomerRepository;
import com.rumpup.demo.repositories.OrderRepository;
import com.rumpup.demo.repositories.ProductOfferingRepository;
import com.rumpup.demo.services.exceptions.DatabaseException;
import com.rumpup.demo.services.exceptions.UpdateObjectException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private ProductOfferingRepository productOfferingRepository;

	// Regras de negócio

	public Iterable<Order> findAll() {
		return repository.findAll();
	}

	public Order findById(Integer id) {
		Optional<Order> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(id, "Objeto não encontrado"));
	}

	public Order insert(Order obj, Integer customerId, Integer addressId, Integer productId, Double discount, Integer quantity) {
	    try {
	        Optional<Customer> customer = customerRepository.findById(customerId);
	        Optional<Address> address = addressRepository.findById(addressId);
	        Optional<ProductOffering> productOffering = productOfferingRepository.findById(productId);

	        if (customer.isPresent() && address.isPresent() && productOffering.isPresent()) {
	            Customer existingCustomer = customer.get();
	            Address existingAddress = address.get();
	            ProductOffering existingProductOffering = productOffering.get();

	            existingCustomer.getOrders().add(obj);  //add the order to the customer's order set
	            obj.setCustomer(existingCustomer);
	            obj.setAddress(existingAddress);  //set the address for the order

	            OrderItem orderItem = new OrderItem(obj, existingProductOffering, discount, quantity);
	            obj.getItems().add(orderItem); //add the order item to the order's item set

	            repository.save(obj);
	            customerRepository.save(existingCustomer);

	            return obj;
	        } else {
	            throw new ObjectNotFoundException(customerId, "Usuário não encontrado");
	        }
	    } catch (ObjectNotFoundException e) {
	        e.printStackTrace();
	        throw e;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new DatabaseException(e.getLocalizedMessage());
	    }
	}

	public void updateData(Order existingOrder, Order updatedOrder) {


		if (updatedOrder.getInstant() != null) {
			existingOrder.setInstant(updatedOrder.getInstant());
		}
		
		if (updatedOrder.getCustomer() != null) {
			existingOrder.setCustomer(updatedOrder.getCustomer());
		}
	}

	public void delete(Integer id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(id, "Objeto não encontrado");
			 } catch (DataIntegrityViolationException e) {
			 throw new DatabaseException(e.getMessage());
		}
	}

	public Order update(Order updatedOrder) {
		try {
			Optional<Order> optionalOrder = repository.findById(updatedOrder.getId());

			if (optionalOrder.isPresent()) {
				Order existingOrder = optionalOrder.get();
				updateData(existingOrder, updatedOrder);
				return repository.save(existingOrder);
			} else {
				throw new ObjectNotFoundException(updatedOrder, "Objeto não encontrado");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UpdateObjectException("Ocorreu um erro ao atualizar o usuário", e);
		}
	}

	public Order fromDto(OrderDTO objDto) {
		return new Order(objDto.getId(), objDto.getInstant(), objDto.getCustomer());
	}
}
