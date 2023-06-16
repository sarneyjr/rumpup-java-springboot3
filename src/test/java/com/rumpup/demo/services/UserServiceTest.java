package com.rumpup.demo.services;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rumpup.demo.dto.UserDTO;
import com.rumpup.demo.entities.Role;
import com.rumpup.demo.entities.User;
import com.rumpup.demo.entities.enums.Authorities;
import com.rumpup.demo.repositories.RoleRepository;
import com.rumpup.demo.repositories.UserRepository;
import com.rumpup.demo.services.exceptions.DatabaseException;
import com.rumpup.demo.services.exceptions.EmailAlreadyExistsException;
import com.rumpup.demo.services.exceptions.ObjectCreationException;
import com.rumpup.demo.services.exceptions.ObjectNotFoundException;

class UserServiceTest {

    private static final Integer INTEGERID = 1;
    private static final String PASSWORD = "#1234";
    private static final String EMAIL = "maria@gmail.com";
    private static final String OPERATOR = "OPERATOR";

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindByIdThenReturnInstance() {
        User user = new User(INTEGERID, EMAIL, PASSWORD);
        Optional<User> optionalUser = Optional.of(user);
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
                .thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

        Assertions.assertThrows(ObjectNotFoundException.class, () -> service.findById(INTEGERID));
    }

    @Test
    void testUpdateData() {
        User existingUser = new User(INTEGERID, EMAIL, PASSWORD);
        User updatedUser = new User(INTEGERID, EMAIL, PASSWORD);

        service.updateData(existingUser, updatedUser);

        Assertions.assertEquals(PASSWORD, existingUser.getPassword());
    }

    @Test
    void deleteWithSuccess() {
        Optional<User> optionalUser = Optional.of(new User(INTEGERID, EMAIL, PASSWORD));
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
        Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());

        service.delete(INTEGERID);

        Mockito.verify(repository, times(1)).deleteById(Mockito.anyInt());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        User existingUser = new User(INTEGERID, EMAIL, PASSWORD);
        User updatedUser = new User(INTEGERID, EMAIL, PASSWORD);

        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(existingUser));
        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(updatedUser);

        User result = service.update(updatedUser);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updatedUser, result);
    }

    @Test
    void testFindAll() {
        User user = new User(INTEGERID, EMAIL, PASSWORD);
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(user));

        Iterable<User> result = service.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(Collections.singletonList(user), result);
    }

    @Test
    void testDeleteWithExistingId() {
        Optional<User> optionalUser = Optional.of(new User(INTEGERID, EMAIL, PASSWORD));
        Mockito.when(repository.findById(INTEGERID)).thenReturn(optionalUser);
        Mockito.doNothing().when(repository).deleteById(INTEGERID);

        Assertions.assertDoesNotThrow(() -> service.delete(INTEGERID));

        Mockito.verify(repository, Mockito.times(1)).deleteById(INTEGERID);
    }

    @Test
    void testDelete_UserDoesNotExist_ThrowsObjectNotFoundException() {
        Integer userId = 1;

        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(userId);

        Assertions.assertThrows(org.hibernate.ObjectNotFoundException.class, () -> service.delete(userId));
    }

    @Test
    void testDeleteWithIntegrityViolation() {
        Optional<User> optionalUser = Optional.of(new User(INTEGERID, EMAIL, PASSWORD));
        Mockito.when(repository.findById(INTEGERID)).thenReturn(optionalUser);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(INTEGERID);

        Assertions.assertThrows(DatabaseException.class, () -> service.delete(INTEGERID));

        Mockito.verify(repository, Mockito.times(1)).deleteById(INTEGERID);
    }

    @Test
    void testUpdateWithExistingUser() {
        User updatedUser = new User(INTEGERID, EMAIL, PASSWORD);
        Optional<User> optionalUser = Optional.of(updatedUser);

        Mockito.when(repository.findById(INTEGERID)).thenReturn(optionalUser);
        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(updatedUser);

        User result = service.update(updatedUser);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updatedUser, result);
    }

    @Test
    void testUpdateWithNonExistingUser() {
        User updatedUser = new User(INTEGERID, EMAIL, PASSWORD);
        Optional<User> optionalUser = Optional.empty();

        Mockito.when(repository.findById(INTEGERID)).thenReturn(optionalUser);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> service.update(updatedUser));

        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(User.class));
    }

    @Test
    void testUpdateWithDataIntegrityViolation() {
        User updatedUser = new User(INTEGERID, EMAIL, PASSWORD);
        Optional<User> optionalUser = Optional.of(new User(INTEGERID, EMAIL, PASSWORD));

        Mockito.when(repository.findById(INTEGERID)).thenReturn(optionalUser);
        Mockito.when(repository.save(Mockito.any(User.class))).thenThrow(DataIntegrityViolationException.class);

        Assertions.assertThrows(DatabaseException.class, () -> service.update(updatedUser));

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    void testFromDto() {
        User user = new User(INTEGERID, EMAIL, PASSWORD);
        UserDTO dto = new UserDTO(user);

        User result = service.fromDto(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getId(), result.getId());
        Assertions.assertEquals(user.getEmail(), result.getEmail());
        Assertions.assertEquals(user.getPassword(), result.getPassword());
    }

    @Test
    void testLoadUserByUsername_UserFound_ReturnsUserDetails() {
        User user = new User();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setRoles(Collections.singleton(new Role(INTEGERID, Authorities.OPERATOR)));

        Mockito.when(repository.findByEmail(EMAIL)).thenReturn(user);

        UserDetails result = service.loadUserByUsername(EMAIL);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(EMAIL, result.getUsername());
        Assertions.assertEquals(user.getPassword(), result.getPassword());
        Assertions.assertEquals(user.getRolesinString(), result.getAuthorities().toArray()[0].toString());
    }

    @Test
    void testCreateUserWithOperatorRole() {
        UserDTO userDto = new UserDTO();
        userDto.setEmail(EMAIL);
        userDto.setPassword(PASSWORD);

        User existingUser = null; // Simulating that no existing user with the same email exists

        Role operatorRole = new Role();
        operatorRole.setAuthority(Authorities.OPERATOR);

        when(repository.findByEmail(EMAIL)).thenReturn(existingUser);
        when(roleRepository.findByAuthority(Authorities.OPERATOR)).thenReturn(operatorRole);

        // Mock the password encoder
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        // Inject the mocked password encoder into the service
        //service.setPasswordEncoder(passwordEncoder);

        User result = service.createUserWithOperatorRole(userDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(EMAIL, result.getEmail());
        Assertions.assertEquals(PASSWORD, result.getPassword());
        Assertions.assertEquals(Collections.singleton(operatorRole), result.getRoles());
    }


    @Test
    void testCreateUserWithOperatorRole_EmailAlreadyExists_ThrowsEmailAlreadyExistsException() {
        UserDTO userDto = new UserDTO();
        userDto.setEmail(EMAIL);
        userDto.setPassword(PASSWORD);

        User existingUser = new User();
        Role operatorRole = new Role();
        operatorRole.setAuthority(Authorities.OPERATOR);

        when(repository.findByEmail(EMAIL)).thenReturn(existingUser);

        Assertions.assertThrows(EmailAlreadyExistsException.class,
                () -> service.createUserWithOperatorRole(userDto));

    }

    @Test
    void testCreateUserWithOperatorRole_RoleNotFound_ThrowsRoleNotFoundException() {
        UserDTO userDto = new UserDTO();
        userDto.setEmail(EMAIL);
        userDto.setPassword(PASSWORD);

        User existingUser = null;

        when(repository.findByEmail(EMAIL)).thenReturn(existingUser);
        when(roleRepository.findByAuthority(Authorities.OPERATOR)).thenReturn(null);

        Assertions.assertThrows(ObjectCreationException.class,
                () -> service.createUserWithOperatorRole(userDto));

        Mockito.verify(repository, times(1)).findByEmail(EMAIL);
        Mockito.verify(roleRepository, times(1)).findByAuthority(Authorities.OPERATOR);
        //Mockito.verify(passwordEncoder, times(0)).encode(PASSWORD);
        Mockito.verify(repository, times(0)).save(Mockito.any(User.class));
    }

    @Test
    void testCreateUserWithAdminRole() {
        UserDTO userDto = new UserDTO();
        userDto.setEmail(EMAIL);
        userDto.setPassword(PASSWORD);

        User existingUser = new User();
        Role adminRole = new Role();
        adminRole.setAuthority(Authorities.ADMIN);

        Mockito.when(repository.findByEmail(EMAIL)).thenReturn(existingUser);
        Mockito.when(roleRepository.findByAuthority(Authorities.ADMIN)).thenReturn(adminRole);
        //Mockito.when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);

    }

    @Test
    void testCreateUserWithAdminRole_EmailAlreadyExists_ThrowsEmailAlreadyExistsException() {
        UserDTO userDto = new UserDTO();
        userDto.setEmail(EMAIL);
        userDto.setPassword(PASSWORD);

        User existingUser = new User();
        Role adminRole = new Role();
        adminRole.setAuthority(Authorities.ADMIN);

        when(repository.findByEmail(EMAIL)).thenReturn(existingUser);

        Assertions.assertThrows(EmailAlreadyExistsException.class,
                () -> service.createUserWithAdminRole(userDto));

    }

    @Test
    void testCreateUserWithAdminRole_RoleNotFound_ThrowsRoleNotFoundException() {
        UserDTO userDto = new UserDTO();
        userDto.setEmail(EMAIL);
        userDto.setPassword(PASSWORD);

        User existingUser = null;

        when(repository.findByEmail(EMAIL)).thenReturn(existingUser);
        when(roleRepository.findByAuthority(Authorities.ADMIN)).thenReturn(null);

        Assertions.assertThrows(ObjectCreationException.class,
                () -> service.createUserWithAdminRole(userDto));


    }
}

