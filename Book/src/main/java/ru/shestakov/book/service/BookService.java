package ru.shestakov.book.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shestakov.book.dto.BookDto;
import ru.shestakov.book.dto.ResponseBookDto;
import ru.shestakov.book.entity.Book;
import ru.shestakov.book.entity.StatusEnum;
import ru.shestakov.book.exceptions.*;
import ru.shestakov.book.feign.LibraryServiceProxy;
import ru.shestakov.book.mapper.BookMapper;
import ru.shestakov.book.repository.BookRepository;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final LibraryServiceProxy libraryServiceProxy;


    @Autowired
    public BookService(BookRepository bookRepository,
                       BookMapper bookMapper, LibraryServiceProxy libraryServiceProxy){
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.libraryServiceProxy = libraryServiceProxy;

    }

    public List<ResponseBookDto> findAll(){
        return bookRepository.findAll().stream().map(bookMapper::convertToResponseBookDto).collect(Collectors.toList());
    }


    public ResponseBookDto save(BookDto bookDto, String token){
        if(bookRepository.existsByTitle(bookDto.getTitle()) && bookRepository.existsByIsbn(bookDto.getIsbn())) {
            throw new BookAlReadyExistsException();
        }
        Book convertedBook = bookMapper.convertBookDtoToBook(bookDto);
        convertedBook.setStatus(StatusEnum.FREE);
        bookRepository.save(convertedBook);
        libraryServiceProxy.save(convertedBook.getId(), token);
        return bookMapper.convertToResponseBookDto(bookRepository.save(convertedBook));
    }


    public ResponseBookDto findOne(int id){
        return  bookMapper.convertToResponseBookDto(bookRepository.findById(id).orElseThrow(BookNotFoundException::new));
    }


    public ResponseBookDto findByIsbn(String isbn){
        return  bookMapper.convertToResponseBookDto(bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new));
    }


    public void delete(Integer id){
        Book book =  bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }


    public ResponseBookDto updateBook(int id, BookDto bookDto) {
        Book book =  bookMapper.convertBookDtoToBook(bookDto);
        Book findBook = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        findBook.setTitle(book.getTitle());
        findBook.setIsbn(book.getIsbn());
        findBook.setAuthor(book.getAuthor());
        findBook.setDescription(book.getDescription());
        findBook.setGenre(book.getGenre());
        return bookMapper.convertToResponseBookDto(bookRepository.save(findBook));
    }

    public ResponseBookDto updateStatusById(Integer id, StatusEnum status ){
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        book.setStatus(status);
        return bookMapper.convertToResponseBookDto(bookRepository.save(book));

    }

}
