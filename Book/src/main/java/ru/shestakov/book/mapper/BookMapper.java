package ru.shestakov.book.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.shestakov.book.dto.BookDto;
import ru.shestakov.book.dto.ResponseBookDto;
import ru.shestakov.book.entity.Book;
@Component
public class BookMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public BookMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    public ResponseBookDto convertToResponseBookDto(Book book){
        return modelMapper.map(book, ResponseBookDto.class);
    }

    public Book convertBookDtoToBook(BookDto bookDto){
        return modelMapper.map(bookDto, Book.class);
    }

}
