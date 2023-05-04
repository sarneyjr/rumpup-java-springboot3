package com.rumpup.demo.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rumpup.demo.entities.Address;
import com.rumpup.demo.entities.Customer;
import com.rumpup.demo.entities.Order;
import com.rumpup.demo.entities.OrderItem;
import com.rumpup.demo.entities.ProductOffering;
import com.rumpup.demo.entities.Role;
import com.rumpup.demo.entities.User;
import com.rumpup.demo.entities.enums.Authorities;
import com.rumpup.demo.entities.enums.CustomerType;
import com.rumpup.demo.entities.enums.POState;
import com.rumpup.demo.repositories.AddressRepository;
import com.rumpup.demo.repositories.CustomerRepository;
import com.rumpup.demo.repositories.OrderItemRepository;
import com.rumpup.demo.repositories.OrderRepository;
import com.rumpup.demo.repositories.ProductOfferingRepository;
import com.rumpup.demo.repositories.RoleRepository;
import com.rumpup.demo.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ProductOfferingRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		ProductOffering p1 = new ProductOffering(null, "The Lord of the Rings", 20.45 , true, POState.Active );
		ProductOffering p2 = new ProductOffering(null, "Smart TV", 2190.0, true, POState.Definition);
		ProductOffering p3 = new ProductOffering(null, "Macbook Pro", 1250.0,false, POState.Technical);
		
		productRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		User u1 = new User(null, "maria.sila@gmail.com", "1234");
		User u2 = new User(null, "joao.sila@gmail.com", "4225");
		
		Customer c1 = new Customer(null, "maria silva", 123, "Ativo", "1234" ,"good", CustomerType.LegalPerson, u1);
		Customer c2 = new Customer(null, "joao pereira", 147, "Ativo", "654" ,"excellent", CustomerType.NaturalPerson, u2);
		
		u1.setCustomer(c1);
		u2.setCustomer(c2);
		
		Role r1 = new Role(null, Authorities.Admin);
		Role r2 = new Role(null, Authorities.Operator);
		
		u1.addRole(r1);
		u2.addRole(r2);
		
		Order o1 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"),c1);
		Order o2 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"),c2); 
		
		Address a1 = new Address(null, "Rua Gonçalves Dias", 12, "centro", 6004584, "Brasil", c1);  
		Address a2 = new Address(null, "Rua Ruberlei Boareto", 152, "centro", 4002892, "Brasil", c1);  
		Address a3 = new Address(null, "Rua Antonio Augusto", 40, "barao", 4006592, "Brasil", c2);
		
		o1.setAddress(a1);
		o2.setAddress(a3);
		
		userRepository.saveAll(Arrays.asList(u1,u2));
		customerRepository.saveAll(Arrays.asList(c1,c2));
		addressRepository.saveAll(Arrays.asList(a1,a2,a3));
		orderRepository.saveAll(Arrays.asList(o1,o2));
		roleRepository.saveAll(Arrays.asList(r1,r2));
		
		OrderItem oi1 = new OrderItem(o1, p3, 0.9, 2);
		OrderItem oi2 = new OrderItem(o1, p2, 0.95, 1);
		OrderItem oi3 = new OrderItem(o2, p1, 0.8, 3);

		
		orderItemRepository.saveAll(Arrays.asList(oi1,oi2,oi3));
	}	
}
