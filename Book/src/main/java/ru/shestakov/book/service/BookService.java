package ru.shestakov.book.service;



import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shestakov.book.dto.BookDto;
import ru.shestakov.book.dto.ResponseBookDto;
import ru.shestakov.book.entity.Book;
import ru.shestakov.book.entity.Status;
import ru.shestakov.book.exceptions.*;
import ru.shestakov.book.feign.LibraryServiceClient;
import ru.shestakov.book.mapper.BookMapper;
import ru.shestakov.book.repository.BookRepository;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final LibraryServiceClient libraryServiceClient;


    @Autowired
    public BookService(BookRepository bookRepository,
                       BookMapper bookMapper, LibraryServiceClient libraryServiceClient){
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.libraryServiceClient = libraryServiceClient;

    }

    public List<ResponseBookDto> findAll(){
        return bookRepository.findAll().stream().map(bookMapper::convertToResponseBookDto).collect(Collectors.toList());
    }


    public ResponseBookDto save(BookDto bookDto, String token){
        if (bookRepository.existsByTitle(bookDto.getTitle()) && bookRepository.existsByIsbn(bookDto.getIsbn())) {
            throw new BookAlReadyExistsException();
        }
        try {
            libraryServiceClient.healthCheck(token);
        }catch (FeignException e){
            throw new ServiceUnvailabeException();
        }
        Book convertedBook = bookMapper.convertBookDtoToBook(bookDto);
        convertedBook.setStatus(Status.FREE);
        bookRepository.save(convertedBook);
        libraryServiceClient.save(convertedBook.getId(), token);
        return bookMapper.convertToResponseBookDto(bookRepository.save(convertedBook));
    }


    public ResponseBookDto findOne(int id){
        return  bookMapper.convertToResponseBookDto(bookRepository.findById(id).orElseThrow(BookNotFoundException::new));
    }


    public ResponseBookDto findByIsbn(String isbn){
        return  bookMapper.convertToResponseBookDto(bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new));
    }


    public void delete(Integer id,String token){
        try{
            libraryServiceClient.healthCheck(token);
        }catch (FeignException e){
            throw new ServiceUnvailabeException();
        }
        Book book =  bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        if(book.getStatus().equals(Status.FREE)){
            libraryServiceClient.deleteByStatus(id, token);
        }
        bookRepository.delete(book);
    }


    public ResponseBookDto updateBook(int id, BookDto bookDto) {
        Book book =  bookMapper.convertBookDtoToBook(bookDto);
        if(bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BookAlReadyExistsException();
        }
        Book findBook = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        findBook.setTitle(book.getTitle());
        findBook.setIsbn(book.getIsbn());
        findBook.setAuthor(book.getAuthor());
        findBook.setDescription(book.getDescription());
        findBook.setGenre(book.getGenre());
        return bookMapper.convertToResponseBookDto(bookRepository.save(findBook));
    }

    public ResponseBookDto updateStatusById(Integer id, Status status , String token){
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        if(status.equals(Status.OCCUPIED)){
            libraryServiceClient.deleteByStatus(id,token);
        }else{
            libraryServiceClient.save(id,token);
        }
        book.setStatus(status);
        return bookMapper.convertToResponseBookDto(bookRepository.save(book));

    }

}
