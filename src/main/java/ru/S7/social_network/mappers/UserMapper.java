package ru.S7.social_network.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.S7.social_network.dto.UserDtoRequest;
import ru.S7.social_network.dto.UserDtoResponse;
import ru.S7.social_network.entities.User;


@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "login", target = "login"),
            @Mapping(source = "role.id", target = "role"),
    })
    UserDtoResponse userToUserDto(User entity);

    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "login", target = "login"),
            @Mapping(source = "role", target = "role.id"),
    })
    User userDtoToUser(UserDtoRequest dto);
}
