package com.shopme.admin.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Category;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;
	
	public List<Category> listAll(){
		List<Category> rootCategories = repo.findRootCategories();
		return listHierachicalCategories(rootCategories);
	}
	
	private List<Category> listHierachicalCategories(List<Category> rootCategories){
		List<Category> hierachicalCategories = new ArrayList<>();
		
		for(Category rootCategory: rootCategories) {
			hierachicalCategories.add(Category.copyFull(rootCategory));
			
			Set<Category> children = rootCategory.getChildren();
			for(Category subCategory: children){
				String name = "--" + subCategory.getName();
				hierachicalCategories.add(Category.copyFull(subCategory, name));
				
				listSubHierachicalCategories(hierachicalCategories, subCategory, 1);			
			}
		}
		return hierachicalCategories;
		
	}
	
	private void listSubHierachicalCategories(List<Category> hierachicalCategories,Category parent, int subLevel){
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		
		for(Category subCategory: children){
			String name = "--";
			for(int i=0; i < newSubLevel; i++) {
				name += "--";
			}
			name += subCategory.getName();
			hierachicalCategories.add(Category.copyFull(subCategory, name));
			
			listSubHierachicalCategories(hierachicalCategories,subCategory, newSubLevel);
		}
		
	}
	
	public Category save(Category category){
		return repo.save(category);
	}
	
	
	public List<Category> listCategoriesUsedInForm(){
		
		List<Category> categoriesUsedInForm = new ArrayList<Category>();
		
		Iterable<Category> categoriesInDB =  repo.findAll();
		
		for(Category category : categoriesInDB){
			if(category.getParent() == null){
				categoriesUsedInForm.add(Category.copyIdAndName(category));
				
				Set<Category> children = category.getChildren();
				
				for(Category subCategory : children){
					String name = "--" + subCategory.getName();
					categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
					listSubCategoriesUsedInForm(categoriesUsedInForm,subCategory, 1);
				}
			}
		}
		
		return categoriesUsedInForm;
	}
	
	private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel){
		
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		for(Category subCategory : children ){
			String name = "--";
			for(int i=0; i < newSubLevel; i++) {
				name += "--";
			}
			
			name += subCategory.getName();
			categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
			
			listSubCategoriesUsedInForm(categoriesUsedInForm,subCategory, newSubLevel);
		}
	}
}
