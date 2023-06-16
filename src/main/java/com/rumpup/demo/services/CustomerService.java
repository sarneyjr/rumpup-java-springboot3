package com.rumpup.demo.services;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rumpup.demo.dto.CustomerDTO;
import com.rumpup.demo.entities.Address;
import com.rumpup.demo.entities.Customer;
import com.rumpup.demo.entities.User;
import com.rumpup.demo.repositories.AddressRepository;
import com.rumpup.demo.repositories.CustomerRepository;
import com.rumpup.demo.repositories.UserRepository;
import com.rumpup.demo.services.exceptions.DatabaseException;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;

	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private AddressRepository addressRepository;

	// Regras de negócio

	public Iterable<Customer> findAll() {
		return repository.findAll();
	}

	public Customer findById(Integer id) {
		Optional<Customer> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(id, "Object Not Found"));
	}

	public Customer insert(Customer obj, Integer userId) {
		try {
			Optional<User> user = userRepository.findById(userId);

			if (user.isPresent()) {
				User existingUser = user.get();
				existingUser.setCustomer(obj);
				obj.setUser(existingUser);

				repository.save(obj);
				userRepository.save(existingUser);

				return obj;
			} else {
				throw new ObjectNotFoundException(userId, "userId Not Found");
			}
		} catch (ObjectNotFoundException e) {

			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("the document number has already been used");
		}
	}
	
	public Address insertAddress(Address address, Integer customerId) {
	    try {
	        Optional<Customer> customerOptional = repository.findById(customerId);

	        if (customerOptional.isPresent()) {
	            Customer customer = customerOptional.get();
	            address.setCustomer(customer); // Define o cliente do endereço
	            customer.getAddress().add(address); // Adiciona o endereço ao conjunto de endereços do cliente

	            repository.save(customer); // Salva o cliente para atualizar a relação
	            addressRepository.save(address); // Salva o endereço

	            return address;
	        } else {
	            throw new ObjectNotFoundException(customerId, "Customer Not Found.");
	        }
	    } catch (ObjectNotFoundException e) {
	        e.printStackTrace();
	        throw new DatabaseException(e.getCause().toString());
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new DatabaseException("Database Exception");
	    }
	}

	

/*	
	public boolean hasAccess(Integer id, String email) {
	    try {
	        User user = findById(id).getUser();
	            return (user != null && user.getEmail().equals(email));
	        
	    } catch (Exception e) {
	    return false;
	    }
	}
*/

	public void updateData(Customer existingCustomer, Customer updatedCustomer) {

		if (updatedCustomer.getCustomerName() != null) {
			existingCustomer.setCustomerName(updatedCustomer.getCustomerName());
		}

		if (updatedCustomer.getDocumentNumber() != null) {
			existingCustomer.setDocumentNumber(updatedCustomer.getDocumentNumber());
		}

		if (updatedCustomer.getCustomerStatus() != null) {
			existingCustomer.setCustomerStatus(updatedCustomer.getCustomerStatus());
		}

		if (updatedCustomer.getCreditScore() != null) {
			existingCustomer.setCreditScore(updatedCustomer.getCreditScore());
		}

		if (updatedCustomer.getPassword() != null) {
			existingCustomer.setPassword(updatedCustomer.getPassword());
		}

		if (updatedCustomer.getCustomerType() != null) {
			existingCustomer.setCustomerType(updatedCustomer.getCustomerType());
		}
	}

	public void delete(Integer id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new com.rumpup.demo.services.exceptions.ObjectNotFoundException(e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getCause().toString());
		}
	}

	public Customer update(Customer updatedCustomer) {
		try {
			Optional<Customer> optionalCustomer = repository.findById(updatedCustomer.getId());

			if (optionalCustomer.isPresent()) {
				Customer existingCustomer = optionalCustomer.get();
				updateData(existingCustomer, updatedCustomer);
				return repository.save(existingCustomer);
			} else {
				throw new ObjectNotFoundException(updatedCustomer, "Object Not Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getCause().toString());
		}
	}

	public Customer fromDto(CustomerDTO objDto) {
		return new Customer(objDto.getId(), objDto.getCustomerName(), objDto.getDocumentNumber(),
				objDto.getCustomerStatus(), objDto.getCreditScore(), objDto.getPassword(), objDto.getCustomerType(),
				objDto.getUser());
	}
}