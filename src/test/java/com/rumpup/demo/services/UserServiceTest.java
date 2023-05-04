package com.rumpup.demo.services;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.rumpup.demo.dto.UserDTO;
import com.rumpup.demo.entities.User;
import com.rumpup.demo.repositories.UserRepository;
import com.rumpup.demo.services.exceptions.ObjectNotFoundException;

class UserServiceTest {
	
	private static final Integer INTEGERID 	= 1;
	private static final String  PASSWORD 	= "#1234";
	private static final String  EMAIL 		= "maria@gmail.com";

	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repository;
	
	private User user;
	private UserDTO userDto;
	private Optional<User> optionalUser;
	

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	void whenFindByIdThenReturnInstance() {
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
		User response = service.findById(INTEGERID);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(User.class, response.getClass());
		Assertions.assertEquals(INTEGERID, response.getId());
		Assertions.assertEquals(PASSWORD, response.getPassword());
		Assertions.assertEquals(EMAIL, response.getEmail());
	}
	
	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		Mockito.when(repository.findById(Mockito.anyInt()))
		.thenThrow(new com.rumpup.demo.services.exceptions.ObjectNotFoundException("Objeto não encontrado"));
		
		try {
			service.findById(INTEGERID);
		} catch (Exception ex) {
			Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
			Assertions.assertEquals("Objeto não encontrado", ex.getMessage());
		}
	}

	@Test
	void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateData() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testFromDto() {
		fail("Not yet implemented");
	}
	
	private void startUser() {
		user = new User(INTEGERID, EMAIL, PASSWORD);
		userDto = new UserDTO(user);
		optionalUser = Optional.of(new User(INTEGERID, EMAIL, PASSWORD));
	}
	
}
