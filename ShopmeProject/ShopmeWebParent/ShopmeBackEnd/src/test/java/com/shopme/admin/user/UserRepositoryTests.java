package com.shopme.admin.user;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired 
	private UserRepository repo;
	
	
	//TestEntityManager is provided in Spring Data Jpa for unit testing with repository
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		// Tạo một cái Role object với id là 1 từ database với entityManager 
		// (tức là cái object này chứa dữ liệu 
		// tương ứng với hàng thứ 1 trong bảng role - cũng chính là admin) 
		// Đây là cách làm nhanh, chứ mình nghĩ dùng constructor của class Role cũng được
		Role roleAdmin = entityManager.find(Role.class, 1);
		User tdt = new User("eyesontheprize2k4@gmail.com", "123", "Tran", "Duc Tung");
		tdt.addRole(roleAdmin);
		
		User savedUser = repo.save(tdt);
		assertThat(savedUser.getId(), notNullValue());
	}
	
	@Test
	public void testCreateNewUserWithTwoRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		Role roleEditor = entityManager.find(Role.class, 3);
		User tdt = new User("tdtung22@clc.fitus.edu.vn", "12345", "Nguyen", "Thanh Tung");
		tdt.addRole(roleAdmin);
		tdt.addRole(roleEditor);
		
		User savedUser = repo.save(tdt);
		assertThat(savedUser.getId(), notNullValue());
	}
	
	@Test
	public void testCreateNewUserWithThreeRole() {
		User nva = new User("tducpeter@gmail.com", "12345", "Nguyen", "Van A");
		
		Role roleEditor = new Role(3);
		Role roleAdmin = new Role(1);
		Role roleAssistant = new Role(5);
		
		nva.addRole(roleAdmin);
		nva.addRole(roleEditor);
		nva.addRole(roleAssistant);
		
		User savedUser = repo.save(nva);
		assertThat(savedUser.getId(), notNullValue());
	}
	
	@Test
	public void testListAllUser() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test 
	public void testGetUserById() {
		User firstUser = repo.findById(1).get();
		System.out.println(firstUser);
		assertThat(firstUser.getId(), notNullValue());
	}	
	
	@Test
	public void testUpdateUserDetails() {
		User user = repo.findById(1).get();
		user.setEnabled(true);
		user.setEmail("email@example.com");
		repo.save(user);
		
	}
	@Test 
	public void testUpdateUserRoles() {
		User user = repo.findById(2).get();
		
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		
		user.getRoles().remove(roleEditor);
		user.addRole(roleSalesperson);
		
		repo.save(user);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 3;
		repo.deleteById(userId);
	}
}
