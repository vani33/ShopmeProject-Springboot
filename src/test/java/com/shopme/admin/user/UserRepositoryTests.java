package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.hibernate.usertype.UserCollectionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

/*@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)*/
//@Rollback(false)

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;

	@Autowired
	private RoleRepository roleRepo;

	/*
	 * @Autowired private TestEntityManager entityManager;
	 */

	@Test
	public void testCreateUserWithOneRole() {

		// Role roleAdmin = entityManager.find(Role.class, 1);
		Role roleAdmin = roleRepo.findById(1);
		User userVani = new User("nani74k@gmail.com", "vani@123", "kothapalli", "vani");
		userVani.addRole(roleAdmin);

		User savedUser = repo.save(userVani);
		assertThat(savedUser.getId()).isGreaterThan(0);

	}

	@Test
	public void testCreateUserWithTwoRole() {

		User userKavya = new User("Kavya22@gmail.com", "kavya@22", "Kothapalli", "kavya");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);

		userKavya.addRole(roleEditor);
		userKavya.addRole(roleAssistant);

		User savedUser = repo.save(userKavya);
		assertThat(savedUser.getId()).isGreaterThan(0);

	}

	@Test
	public void testListAllUsers() {

		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));

	}

	@Test
	public void testGetUserById() {

		User userName = repo.findById(1).get();
		System.out.println(userName);
		assertThat(userName).isNotNull();

	}

	@Test
	public void testUpdateUserDetails() {
		User userName = repo.findById(1).get();
		userName.setEnabled(true);
		userName.setEmail("nani2218k@gmail.com");

		repo.save(userName);
	}

	@Test
	public void testUpdateUserRoles() {
		User userKavya = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		userKavya.getRoles().remove(roleEditor);
		userKavya.addRole(roleSalesperson);

		repo.save(userKavya);
	}

	@Test
	public void testDeleteUser() {

		repo.deleteById(2);
	}
	
	@Test
	public void testGetUserByEmail(){
		String email = "Kavya22@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById(){
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser(){
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUser(){
		Integer id = 3;
		repo.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testListFirstPage(){
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user-> System.out.println(user));
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
	
	
	@Test
	public void testSearchUsers(){
	
		String keyword = "Bruce";
		
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user-> System.out.println(user));
		
		assertThat(listUsers.size()).isGreaterThan(0);
		
	}
}
