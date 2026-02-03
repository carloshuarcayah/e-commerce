package pe.com.birdcare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.birdcare.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByActiveTrue(Pageable pageable);
    Page<Category> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
