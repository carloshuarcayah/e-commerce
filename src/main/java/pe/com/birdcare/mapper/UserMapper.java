package pe.com.birdcare.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import pe.com.birdcare.dto.UserRequestDTO;
import pe.com.birdcare.dto.UserResponseDTO;
import pe.com.birdcare.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    void updateEntityFromDto(UserRequestDTO dto, @MappingTarget User user);

    @Mapping(target = "password", ignore = true)
    User fromRequestToEntity(UserRequestDTO req);

    UserResponseDTO toResponseDTO(User user);
}
