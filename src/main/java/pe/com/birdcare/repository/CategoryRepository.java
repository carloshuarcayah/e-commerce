package pe.com.birdcare.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.birdcare.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByActiveTrue(Pageable pageable);
    Page<Category> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(@NotBlank String name, Long id);
}
