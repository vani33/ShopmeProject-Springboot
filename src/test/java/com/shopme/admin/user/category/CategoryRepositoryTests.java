package com.shopme.admin.user.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.shopme.admin.category.CategoryRepository;
import com.shopme.common.entity.Category;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Rollback(false)
public class CategoryRepositoryTests {

	@Autowired
	private CategoryRepository repo;
	
	@Test
	public void testCreateRootCategory(){
		Category category = new Category("Electronics");
		Category savedCategory = repo.save(category);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testCreateSubCategory(){
		Category parent = new Category(7);
		Category subCategory = new Category("iphone",parent);
		Category savedCategory = repo.save(subCategory);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
		
		
	}
	@Test
	public void testGetCategory(){
		Category category = repo.findById(1).get();
		System.out.println(category.getName());
		
		Set<Category> children = category.getChildren();
		for (Category subCategory : children){
			System.out.println(subCategory.getName());
		}
		assertThat(children.size()).isGreaterThan(0);
	}
	
	@Test
	public void testPrintHierarchicalCategories(){
		Iterable<Category> categories = repo.findAll();
		
		for(Category category : categories){
			if(category.getParent() == null){
				System.out.println(category.getName());
				
				Set<Category> children = category.getChildren();
				
				for(Category subCategory : children){
					System.out.println("--" + subCategory.getName());
					printChildren(subCategory, 1);
				}
			}
		}
	}
	
	private void printChildren(Category parent, int subLevel){
		
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		for(Category subCategory : children ){
			for(int i=0; i < newSubLevel; i++) {
				System.out.print("--");
			}
			System.out.println(subCategory.getName());
			
			printChildren(subCategory, newSubLevel);
		}
	}
	
	@Test
	public void testListRootCategories(){
		List<Category> rootCategories = repo.findRootCategories();
		rootCategories.forEach(cat->System.out.println(cat.getName()));
	}
	
	
	
	
	
}
