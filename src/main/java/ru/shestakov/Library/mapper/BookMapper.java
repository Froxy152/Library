package ru.shestakov.Library.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import ru.shestakov.Library.dto.ResponseBookDto;
import ru.shestakov.Library.entity.Book;
@Component
public class BookMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public BookMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    public ResponseBookDto convertToBookDto(Book book){
        return modelMapper.map(book, ResponseBookDto.class);
    }

    public Book convertToBook(ResponseBookDto responseBookDTO){
        return modelMapper.map(responseBookDTO,Book.class);
    }
}
