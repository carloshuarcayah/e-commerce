package pe.com.birdcare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pe.com.birdcare.dto.CategoryRequestDTO;
import pe.com.birdcare.dto.CategoryResponseDTO;
import pe.com.birdcare.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper{

    //CREATE REQ->CATEGORY
    Category toEntity(CategoryRequestDTO req);

    //UPDATE
    void updateCategory(CategoryRequestDTO req, @MappingTarget Category target);

    //RESPONSE
    CategoryResponseDTO toResponse(Category category);
}