package com.rumpup.demo.services;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rumpup.demo.dto.ProductOfferingDTO;
import com.rumpup.demo.entities.ProductOffering;
import com.rumpup.demo.repositories.ProductOfferingRepository;
import com.rumpup.demo.services.exceptions.DatabaseException;
import com.rumpup.demo.services.exceptions.InsertObjectException;
import com.rumpup.demo.services.exceptions.UpdateObjectException;

@Service
public class ProductOfferingService {
	
	@Autowired
	private ProductOfferingRepository repository;

	// Regras de negócio

	public Iterable<ProductOffering> findAll() {
		return repository.findAll();
	} 

	public ProductOffering findById(Integer id) {
		Optional<ProductOffering> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(id, "Objeto não encontrado"));
	}

	public ProductOffering insert(ProductOffering obj) {
	    try {
	        return repository.save(obj);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new InsertObjectException("could not create a product with the same name ", e);
	    }
	}

	public void updateData(ProductOffering existingProductOffering, ProductOffering updatedProductOffering) {


		if (updatedProductOffering.getProductName() != null) {
			existingProductOffering.setProductName(updatedProductOffering.getProductName());
		}
		
		if (updatedProductOffering.getUnitPrice() != null) {
			existingProductOffering.setUnitPrice(updatedProductOffering.getUnitPrice());
		}
		

		if (updatedProductOffering.getSellIndicator() != null) {
			existingProductOffering.setSellIndicator(updatedProductOffering.getSellIndicator());
		}
		
		if (updatedProductOffering.getState() != null) {
			existingProductOffering.setState(updatedProductOffering.getState());
		}
	}

	public void delete(Integer id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(id, e.getLocalizedMessage());
			 } catch (DataIntegrityViolationException e) {
			 throw new DatabaseException(e.getMessage());
		}
	}

	public ProductOffering update(ProductOffering updatedProductOffering) {
		try {
			Optional<ProductOffering> optionalProductOffering = repository.findById(updatedProductOffering.getId());

			if (optionalProductOffering.isPresent()) {
				ProductOffering existingProductOffering = optionalProductOffering.get();
				updateData(existingProductOffering, updatedProductOffering);
				return repository.save(existingProductOffering);
			} else {
				throw new ObjectNotFoundException(updatedProductOffering, "Objeto não encontrado");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UpdateObjectException(e.getLocalizedMessage(), e);
		}
	}

	public ProductOffering fromDto(ProductOfferingDTO objDto) {
		return new ProductOffering(objDto.getId(), objDto.getProductName(), objDto.getUnitPrice(), objDto.getSellIndicator(), objDto.getState());
	}
}
