package ru.shestakov.book.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return bookRepository.findAll().stream().map(bookMapper::convertToBookDto).collect(Collectors.toList());
    }


    public ResponseBookDto save(ResponseBookDto responseBookDto, String token){
        Book book = bookMapper.convertToBook(responseBookDto);
        if(bookRepository.existsByTitle(book.getTitle()) && bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BookAlReadyExistsException();
        }
        book.setStatus(StatusEnum.FREE.toString());
        bookRepository.save(book);
        libraryServiceProxy.save(book.getId(), token);
         return bookMapper.convertToBookDto(bookRepository.save(book));
    }


    public ResponseBookDto findOne(int id){
        return  bookMapper.convertToBookDto(bookRepository.findById(id).orElseThrow(BookNotFoundException::new));
    }


    public ResponseBookDto findByIsbn(String isbn){
        return  bookMapper.convertToBookDto(bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new));
    }


    public void delete(Integer id){
        Book book =  bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }


    public ResponseBookDto updateBook(int id, ResponseBookDto responseBookDto) {
        Book book =  bookMapper.convertToBook(responseBookDto);
       Book findBook = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        findBook.setTitle(book.getTitle());
        findBook.setIsbn(book.getIsbn());
        findBook.setAuthor(book.getAuthor());
        findBook.setDescription(book.getDescription());
        findBook.setGenre(book.getGenre());

    return bookMapper.convertToBookDto(bookRepository.save(findBook));
    }

    public ResponseBookDto updateStatusById(Integer id, String status){
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        if(status.equals(StatusEnum.FREE.toString()) || status.equals(StatusEnum.OCCUPIED.toString())) {
            book.setStatus(status);
            return bookMapper.convertToBookDto(bookRepository.save(book));
        }
       return null;
    }

}
