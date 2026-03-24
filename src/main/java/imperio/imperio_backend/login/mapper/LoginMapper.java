package imperio.imperio_backend.login.mapper;

import imperio.imperio_backend.login.dto.LoginDTO;
import imperio.imperio_backend.login.dto.UserProfileDTO;
import imperio.imperio_backend.login.module.LoginModule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    // Map from DTO to Entity (for responses)
    // converts the database user to a safe profile for the frontend
    UserProfileDTO toProfileDto(LoginModule user);

    // converts the database user to a login response if needed
//    LoginDTO toLoginDTO(LoginModule user);

    // DTO to Entity
    // Converts registration data into a database entity
    // We ignore 'id' and 'createdAt' because the DB/Hibernate handles those

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", defaultValue = "USER")
    LoginModule toEntity(LoginDTO loginDTO);

}
