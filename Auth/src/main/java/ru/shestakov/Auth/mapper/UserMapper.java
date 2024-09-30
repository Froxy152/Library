package ru.shestakov.Auth.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shestakov.Auth.dto.UserRequestLoginDto;
import ru.shestakov.Auth.dto.UserRequestRegistrationDto;
import ru.shestakov.Auth.entity.User;
@Component
public class UserMapper {
    private final ModelMapper modelMapper;
    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User UserDTOToUser(UserRequestLoginDto userRequestLoginDTO){
        return modelMapper.map(userRequestLoginDTO, User.class);
    }
    public User UserRegistrationDtoToUser(UserRequestRegistrationDto userRequestRegistrationDto){
        return modelMapper.map(userRequestRegistrationDto, User.class);
    }
}
