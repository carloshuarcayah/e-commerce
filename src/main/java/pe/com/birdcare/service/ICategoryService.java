package pe.com.birdcare.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.birdcare.entity.Category;

public interface ICategoryService {
    Page<Category> findAll(Pageable pageable);
    Page<Category> findActives(Pageable pageable);
    Category findById(Long id);
    Page<Category> findByName(String name, Pageable pageable);
    Category add (Category req);
    Category update (Category req);
    void delete(Long id);
    Category enable(Long id);
}
