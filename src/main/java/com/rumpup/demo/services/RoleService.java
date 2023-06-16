package com.rumpup.demo.services;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rumpup.demo.dto.RoleDTO;
import com.rumpup.demo.entities.Role;
import com.rumpup.demo.entities.enums.Authorities;
import com.rumpup.demo.repositories.RoleRepository;
import com.rumpup.demo.services.exceptions.DatabaseException;
import com.rumpup.demo.services.exceptions.UpdateObjectException;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository repository;

	//Regras de negócio
	
	public Iterable<Role> findAll() {
	    return repository.findAll();
	}
	
	public Role findById(Integer id) {
		Optional<Role> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(id, "Usuário não encontrado"));
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

	public void updateData(Role existingRole, Role updatedRole) {
	    // Verificando cada campo do objeto updatedRole e atualize apenas os campos relevantes em existingRole

	    if (updatedRole.getAuthority() != null) {
	        existingRole.setAuthority(updatedRole.getAuthority());
	    }
	}

	public Role update(Role updatedRole) {
	    try {
	        Optional<Role> optionalRole = repository.findById(updatedRole.getId());

	        if (optionalRole.isPresent()) {
	            Role existingRole = optionalRole.get();
	            updateData(existingRole, updatedRole);
	            return repository.save(existingRole);
	        } else {
	            throw new ObjectNotFoundException(updatedRole, "Objeto não encontrado");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new UpdateObjectException("Ocorreu um erro ao atualizar o usuário", e);
	    }
	}
	
	public Role fromDto(RoleDTO objDto) {
		return new Role(objDto.getId(), objDto.getAuthority());
	}

    public Role findByAuthority(Authorities authority) {
        return repository.findByAuthority(authority);
    }
}
