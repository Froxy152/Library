package ru.shestakov.Library.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shestakov.Library.dto.UserRequestLoginDto;
import ru.shestakov.Library.dto.UserRequestRegistrationDto;
import ru.shestakov.Library.entity.User;
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
