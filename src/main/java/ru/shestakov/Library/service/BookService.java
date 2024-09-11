package ru.shestakov.Library.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shestakov.Library.dto.BookDto;
import ru.shestakov.Library.entity.Book;
import ru.shestakov.Library.entity.Library;
import ru.shestakov.Library.entity.StatusEnum;
import ru.shestakov.Library.repo.BookRepository;
import ru.shestakov.Library.exceptions.BookAlReadyExistsException;
import ru.shestakov.Library.exceptions.BookNotFoundException;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final LibraryService libraryService;
    private final ModelMapper  modelMapper;

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper modelMapper,
                       LibraryService libraryService){
        this.bookRepository = bookRepository;
        this.libraryService = libraryService;
        this.modelMapper = modelMapper;
    }

    public List<BookDto> findAll(){

        return bookRepository.findAll().stream().map(this::convertToBookDto).collect(Collectors.toList());
    }


    public void save(BookDto bookDto){
        Book book = convertToBook(bookDto);
        if(bookRepository.existsByTitle(book.getTitle()) && bookRepository.existsByIsbn(book.getIsbn())) throw new BookAlReadyExistsException();
        bookRepository.save(book);
        book.setStatus(StatusEnum.FREE.toString());
        libraryService.save(new Library(bookRepository.findByTitle(book.getTitle()).get().getId()));
    }


    public BookDto findOne(int id){
        return  convertToBookDto(bookRepository.findById(id).orElseThrow(BookNotFoundException::new));
    }


    public BookDto findByIsbn(String isbn){
        return  convertToBookDto(bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new));
    }


    public void delete(Integer id){
        Book book =  bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }


    public void updateBook(int id,BookDto bookDto) {
        Book book = convertToBook(bookDto);
       Book book1 = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        book1.setTitle(book.getTitle());
        book1.setIsbn(book.getIsbn());
        book1.setAuthor(book.getAuthor());
        book1.setDescription(book.getDescription());
        book1.setGenre(book.getGenre());
        if(book.getStatus().equals(StatusEnum.FREE.toString())){
            libraryService.save(new Library(book.getId()));
        }
       bookRepository.save(book1);
    }

    public BookDto convertToBookDto(Book book){
        return modelMapper.map(book, BookDto.class);
    }

    public Book convertToBook(BookDto bookDTO){
        return modelMapper.map(bookDTO,Book.class);
    }




}
