package com.rumpup.demo.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.rumpup.demo.entities.enums.Authorities;

@Entity
@Table(name = "tb_roles")
@SQLDelete(sql = "UPDATE tb_roles SET deleted = 1 WHERE id=?")
//@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
//@Filter(name = "deletedProductFilter", condition = "deleted = :isDeleted")
@Where(clause = "deleted=false")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer 	id;
	private Authorities authority;
	
	private boolean deleted = Boolean.FALSE; // Nova propriedade para indicar se o registro está excluído
	
	public Role() {
	}
	
	public Role(Integer id, Authorities authority) {
		this.id = id;
		this.authority = authority;
	}
	
	//getters and setters
	
	//CODE FOR SECURITY (RETURN AUTHORITY IN STRING)
	public String getAuthorityinString() {
		String authorityString = authority.toString();
		return authorityString;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Authorities getAuthority() {
		return authority;
	}
	
	public void setAuthority(Authorities authority) {
		this.authority = authority;
	}
	
	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	//hashcode and equals
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(id, other.id);
	}
}