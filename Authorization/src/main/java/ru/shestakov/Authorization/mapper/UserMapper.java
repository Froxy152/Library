package ru.shestakov.Authorization.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shestakov.Authorization.dto.UserRequestLoginDto;
import ru.shestakov.Authorization.dto.UserRequestRegistrationDto;
import ru.shestakov.Authorization.dto.UserResponseRegistrationDto;
import ru.shestakov.Authorization.entity.User;
@Component
public class UserMapper {
    private final ModelMapper modelMapper;
    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User userDTOToUser(UserRequestLoginDto userRequestLoginDTO){
        return modelMapper.map(userRequestLoginDTO, User.class);
    }
    public User userRegistrationDtoToUser(UserRequestRegistrationDto userRequestRegistrationDto){
        return modelMapper.map(userRequestRegistrationDto, User.class);
    }
    public UserResponseRegistrationDto userToUserRegistration(User user){
        return modelMapper.map(user, UserResponseRegistrationDto.class);
    }
}
