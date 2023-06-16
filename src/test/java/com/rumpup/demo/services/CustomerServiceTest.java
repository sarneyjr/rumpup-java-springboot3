package com.rumpup.demo.services;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.rumpup.demo.dto.CustomerDTO;
import com.rumpup.demo.entities.Customer;
import com.rumpup.demo.entities.User;
import com.rumpup.demo.entities.enums.CustomerType;
import com.rumpup.demo.repositories.CustomerRepository;
import com.rumpup.demo.repositories.RoleRepository;
import com.rumpup.demo.repositories.UserRepository;
import com.rumpup.demo.services.exceptions.DatabaseException;
import com.rumpup.demo.services.exceptions.ObjectNotFoundException;

class CustomerServiceTest {
	
	private static final Integer 		INTEGERID 			= 1;
	private static final String  		CUSTOMERNAME 		= "yasuo silva";
	private static final Integer 		DOCUMENTNUMBER 		= 00000000;
	private static final String  		CUSTOMERSTATUS 		= "ATIVO";
	private static final String  		CREDITSCORE 		= "100";
	private static final String  		PASSWORD 			= "xxxxxx";
	private static final CustomerType  	CUSTOMERTYPE 		= CustomerType.LegalPerson;
	private static final User 			UserDto 			= null;
	
	
	@InjectMocks
	private CustomerService service;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private CustomerRepository repository;
	
	@Mock
	private RoleRepository roleRepository;
	
    @InjectMocks
    private CustomerService customerService;
    
	private Customer customer;
	private User user;
	private CustomerDTO customerDto;
	private Optional<Customer> optionalCustomer;
	
	private void startCustomer() {
		customer = new Customer(INTEGERID, CUSTOMERNAME, DOCUMENTNUMBER, CUSTOMERSTATUS, CREDITSCORE, PASSWORD, CUSTOMERTYPE, null);
		customerDto = new CustomerDTO(customer);
		optionalCustomer = Optional.of(new Customer(INTEGERID, CUSTOMERNAME,DOCUMENTNUMBER, CUSTOMERSTATUS, CREDITSCORE, PASSWORD, CUSTOMERTYPE, UserDto));
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startCustomer();
	}
	
	@Test
	void whenFindByIdThenReturnInstance() {
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalCustomer);
		Customer response = service.findById(INTEGERID);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(Customer.class, response.getClass());
		Assertions.assertEquals(INTEGERID, 		response.getId());
		Assertions.assertEquals(CUSTOMERNAME, 	response.getCustomerName());
		Assertions.assertEquals(DOCUMENTNUMBER, response.getDocumentNumber());
		Assertions.assertEquals(CUSTOMERSTATUS, response.getCustomerStatus());
		Assertions.assertEquals(PASSWORD, 		response.getPassword());
		Assertions.assertEquals(CUSTOMERTYPE, 	response.getCustomerType());
	}
	
	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		Mockito.when(repository.findById(Mockito.anyInt()))
		.thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
		
		try {
			service.findById(INTEGERID);
		} catch (Exception ex) {
			Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
			Assertions.assertEquals("Objeto não encontrado", ex.getMessage());
		}   
	}

    @Test
    public void testInsert() {

        Customer customer = new Customer();
        User user = new User();
        user.setId(INTEGERID);
        customer.setUser(user);

        Mockito.when(userRepository.findById(INTEGERID)).thenReturn(Optional.of(user));
        Mockito.when(repository.save(Mockito.any(Customer.class))).thenReturn(customer);

        Customer result = customerService.insert(customer, INTEGERID);

        Assertions.assertEquals(customer, result);
        Assertions.assertEquals(user, customer.getUser());
        Mockito.verify(userRepository).findById(INTEGERID);
        Mockito.verify(repository).save(customer);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void testInsert_EmptyUserId() {
        Customer customer = new Customer();

        when(userRepository.findById(INTEGERID)).thenReturn(Optional.empty());

        try {
            customerService.insert(customer, INTEGERID);
        } catch (ObjectNotFoundException e) {
            Assertions.assertEquals("Objeto não encontrado", e.getMessage());
        }
    }

    @Test
    public void testInsert_DatabaseException() {
        Customer customer = new Customer();
        User user = new User();
        user.setId(1);
        customer.setUser(user);

        when(userRepository.findById(INTEGERID)).thenReturn(Optional.of(user));
        when(repository.save(Mockito.any(Customer.class))).thenThrow(DataIntegrityViolationException.class);

        try {
            customerService.insert(customer, INTEGERID);
        } catch (DatabaseException e) {
            Assertions.assertEquals("Ocorreu um erro ao inserir o Customer", e.getMessage());
        }
    }

	
	@Test
	void testUpdateData() {
		
		User user = new User(null, null, null);
		
	    //usuário existente
	    Customer existingCustomer = new Customer(INTEGERID, CUSTOMERNAME, DOCUMENTNUMBER, CUSTOMERSTATUS, CREDITSCORE, PASSWORD, CUSTOMERTYPE, user);
	    //usuário atualizado
	    Customer updatedCustomer = new Customer(INTEGERID, CUSTOMERNAME, DOCUMENTNUMBER, CUSTOMERSTATUS, CREDITSCORE, PASSWORD, CUSTOMERTYPE, user);

	    service.updateData(existingCustomer, updatedCustomer);
	    // Verifica se a senha foi atualizada corretamente
	    Assertions.assertEquals(PASSWORD, existingCustomer.getPassword());
	}

	@Test
	void deleteWithSucess() {
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalCustomer);
		service.delete(INTEGERID);
		
		Mockito.verify(repository, times(1)).deleteById(Mockito.anyInt());	
	}

	@Test
	void whenUpdateThenReturnSucess() {

	    Customer existingCustomer = new Customer(INTEGERID, CUSTOMERNAME, DOCUMENTNUMBER, CUSTOMERSTATUS, CREDITSCORE, PASSWORD, CUSTOMERTYPE, user);
	    Customer updatedCustomer = new Customer(INTEGERID, CUSTOMERNAME, DOCUMENTNUMBER, CUSTOMERSTATUS, CREDITSCORE, PASSWORD, CUSTOMERTYPE, user);

	    //mock do método findById para retornar o usuário existente
	    Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(existingCustomer));
	    Mockito.when(repository.save(Mockito.any(Customer.class))).thenReturn(updatedCustomer);

	    //chamada ao método update
	    Customer result = service.update(updatedCustomer);

	    //verifica se o usuário retornado é igual ao usuário atualizado
	    Assertions.assertEquals(updatedCustomer, result);
	}
	
	@Test
	void testFindAll() {
		Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(customer));

		Iterable<Customer> result = service.findAll();

		Assertions.assertNotNull(result);
		Assertions.assertEquals(Collections.singletonList(customer), result);
	}

	@Test
	void testDeleteWithExistingId() {
		Mockito.when(repository.findById(INTEGERID)).thenReturn(optionalCustomer);
		Mockito.doNothing().when(repository).deleteById(INTEGERID);

		Assertions.assertDoesNotThrow(() -> service.delete(INTEGERID));

		Mockito.verify(repository, Mockito.times(1)).deleteById(INTEGERID);
	}

    @Test
    void testDelete_CustomerDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        Integer customerId = INTEGERID;

        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(customerId);

        // Act and Assert
        Assertions.assertThrows(ObjectNotFoundException.class, () -> customerService.delete(customerId));
    }

	@Test
	void testDeleteWithIntegrityViolation() {
		Mockito.when(repository.findById(INTEGERID)).thenReturn(optionalCustomer);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(INTEGERID);

		Assertions.assertThrows(DatabaseException.class, () -> service.delete(INTEGERID));

		Mockito.verify(repository, Mockito.times(1)).deleteById(INTEGERID);
	}

	@Test
	void testUpdateWithExistingCustomer() {
		Customer updatedCustomer = new Customer(INTEGERID, CUSTOMERNAME, DOCUMENTNUMBER, CUSTOMERSTATUS, CREDITSCORE, PASSWORD, CUSTOMERTYPE, user);

		Mockito.when(repository.findById(INTEGERID)).thenReturn(optionalCustomer);
		Mockito.when(repository.save(Mockito.any(Customer.class))).thenReturn(updatedCustomer);

		Customer result = service.update(updatedCustomer);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(updatedCustomer, result);
	}

	@Test 
	void testUpdateWithNonExistingCustomer() {
		Customer updatedCustomer = new Customer();

		Mockito.when(repository.findById(INTEGERID)).thenReturn(Optional.empty());

		Assertions.assertThrows(ObjectNotFoundException.class, () -> service.update(updatedCustomer));

		Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Customer.class));
	}

	@Test
	void testUpdateWithDataIntegrityViolation() {
		Customer updatedCustomer = new Customer(INTEGERID, CUSTOMERNAME, DOCUMENTNUMBER, CUSTOMERSTATUS, CREDITSCORE, PASSWORD, CUSTOMERTYPE, user);

		Mockito.when(repository.findById(INTEGERID)).thenReturn(optionalCustomer);
		Mockito.when(repository.save(Mockito.any(Customer.class))).thenThrow(DataIntegrityViolationException.class);

		Assertions.assertThrows(DatabaseException.class, () -> service.update(updatedCustomer));

		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Customer.class));
	}

	@Test
	void testFromDto() {
		CustomerDTO dto = new CustomerDTO(customer);
		Customer result = service.fromDto(dto);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(customer.getId(), result.getId());
		Assertions.assertEquals(customer.getCustomerName(), result.getCustomerName());
		Assertions.assertEquals(customer.getDocumentNumber(), result.getDocumentNumber());
		Assertions.assertEquals(customer.getCustomerStatus(), result.getCustomerStatus());
		Assertions.assertEquals(customer.getCreditScore(), result.getCreditScore());
		Assertions.assertEquals(customer.getPassword(), result.getPassword());
		result.getCustomerType();
		Assertions.assertEquals(customer.getCustomerType(), CustomerType.LegalPerson);
	}
}