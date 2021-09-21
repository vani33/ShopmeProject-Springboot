package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shopme.common.entity.Role;

/*@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)*/

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTests {

	@Autowired
	private RoleRepository repo;

	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "Manage Everything");
		Role savedRole = repo.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);

	}

	@Test
	public void testCreateRestRoles() {
		Role roleSalesPerson = new Role("SalesPerson",
				"Manage Product price, " + "customers, shipping, orders and sales report");
		
		Role roleEditor = new Role("Editor", "Manage categories, brands, " + "products, articles and menus ");
		
		Role roleShipper = new Role("Shipper", "vies products, view orders " + "and update order status ");
		
		Role roleAssistant = new Role("Assistant", "Manage questions and reviews");
		
		List<Role> rolesList = new ArrayList<Role>();
		rolesList.add(roleSalesPerson);
		rolesList.add(roleEditor);
		rolesList.add(roleShipper);
		rolesList.add(roleAssistant);
		
		
		List<Role> savedRoles = repo.saveAll(rolesList);
		//assertThat(savedRoles.get(0).getId()).isGreaterThan(0);

	}

}
