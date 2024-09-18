package ru.shestakov.Library.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shestakov.Library.dto.ResponseBookDto;
import ru.shestakov.Library.entity.Book;
import ru.shestakov.Library.entity.Library;
import ru.shestakov.Library.entity.StatusEnum;
import ru.shestakov.Library.mapper.BookMapper;
import ru.shestakov.Library.repo.BookRepository;
import ru.shestakov.Library.exceptions.BookAlReadyExistsException;
import ru.shestakov.Library.exceptions.BookNotFoundException;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final LibraryService libraryService;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository,
                       LibraryService libraryService, BookMapper bookMapper){
        this.bookRepository = bookRepository;
        this.libraryService = libraryService;
        this.bookMapper = bookMapper;
    }

    public List<ResponseBookDto> findAll(){
        return bookRepository.findAll().stream().map(bookMapper::convertToBookDto).collect(Collectors.toList());
    }


    public ResponseBookDto save(ResponseBookDto responseBookDto){
        Book book = bookMapper.convertToBook(responseBookDto);
        if(bookRepository.existsByTitle(book.getTitle()) && bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BookAlReadyExistsException();
        }
        book.setStatus(StatusEnum.FREE.toString());
        bookRepository.save(book);
        libraryService.save(new Library(bookRepository.findByTitle(book.getTitle()).get().getId()));
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
       Book book1 = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        book1.setTitle(book.getTitle());
        book1.setIsbn(book.getIsbn());
        book1.setAuthor(book.getAuthor());
        book1.setDescription(book.getDescription());
        book1.setGenre(book.getGenre());
        if(book.getStatus().equals(StatusEnum.FREE.toString())){
            libraryService.save(new Library(book.getId()));
        }
    return bookMapper.convertToBookDto(bookRepository.save(book1));
    }






}
