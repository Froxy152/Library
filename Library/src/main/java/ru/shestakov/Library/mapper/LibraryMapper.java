package ru.shestakov.Library.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shestakov.Library.dto.LibraryDto;
import ru.shestakov.Library.entity.Library;
@Component
public class LibraryMapper {
    public final ModelMapper modelMapper;

    @Autowired
    public LibraryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public LibraryDto convertToLibraryDto(Library library) {
        return modelMapper.map(library, LibraryDto.class);
    }
}
