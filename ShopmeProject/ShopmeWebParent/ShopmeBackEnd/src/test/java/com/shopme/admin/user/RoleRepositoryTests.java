package com.shopme.admin.user;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.isNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
	
	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "mange everything");
		Role savedRole = repo.save(roleAdmin);
		// assertThat bị lỗi deprecated, có thể tìm phương án khác
		assertThat(savedRole.getId(), notNullValue());
	}
	
	@Test
	public void testCreateRestRoles() {
		Role roleSalesPerson = new Role("Salesperson", "manage product price, "
				+ "customers, shipping, orders and sales report");
		
		Role roleEditor = new Role("Editor", "mamange categories, brands"
				+ "products, articles and menus");
		
		Role roleShipper = new Role("Shipper", "view products, view orders"
				+ "and update order status");
		
		Role roleAssistant = new Role("Assisstant", "manage questions and reviews");
		
		repo.saveAll(List.of(roleSalesPerson, roleEditor, roleShipper, roleAssistant));
	}
}
