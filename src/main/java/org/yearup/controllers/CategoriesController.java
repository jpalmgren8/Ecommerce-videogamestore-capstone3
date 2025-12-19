package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.data.mysql.MySqlCategoryDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

@RestController
@RequestMapping("categories")
@CrossOrigin
public class CategoriesController
{
    private final CategoryDao categoryDao;
    private final ProductDao productDao;

    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getAll()
    {
        return ResponseEntity.ok(categoryDao.getAllCategories());
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable int id) {

        Category category = null;

        try {
            category = categoryDao.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }

        if (category == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found.");
        }
        return category;
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<Product>> getProductsById(@PathVariable int categoryId)
    {
        List<Product> products = productDao.listByCategoryId(categoryId);
        if (products == null || products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        try
        {
            Category created = categoryDao.create(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        try
        {
            Category selected = categoryDao.getById(id);
            if (selected == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            categoryDao.update(id, category);
            return ResponseEntity.noContent().build();

        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id)
    {
        try
        {
            Category selected = categoryDao.getById(id);

            if(selected == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            categoryDao.delete(id);
            return ResponseEntity.noContent().build();
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
