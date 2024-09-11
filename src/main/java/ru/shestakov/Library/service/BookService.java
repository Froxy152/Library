package ru.shestakov.Library.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shestakov.Library.entity.Book;
import ru.shestakov.Library.entity.Library;
import ru.shestakov.Library.repo.BookRepo;
import ru.shestakov.Library.util.BookAlReadyExistsException;
import ru.shestakov.Library.util.BookNotFoundedException;


import java.util.List;



@Service
public class BookService {

    private final BookRepo bookRepo;
    private final LibraryService libraryService;


    @Autowired
    public BookService(BookRepo bookRepo, ModelMapper modelMapper,
                       LibraryService libraryService){
        this.bookRepo = bookRepo;

        this.libraryService = libraryService;
    }

    public List<Book> findAll(){
        return bookRepo.findAll();
    }


    public void save(Book book){
        if(bookRepo.existsByTitle(book.getTitle()) && bookRepo.existsByIsbn(book.getIsbn())) throw new BookAlReadyExistsException();
        bookRepo.save(book);
        book.setStatus("Free");
        libraryService.save(new Library(bookRepo.findByTitle(book.getTitle()).get().getId()));

    }


    public Book findOne(int id){
        return  bookRepo.findById(id).orElseThrow(BookNotFoundedException::new);
    }


    public Book findByIsbn(String isbn){
        return   bookRepo.findByIsbn(isbn).orElseThrow(BookNotFoundedException::new);
    }


    public void delete(Integer id){
        Book book =  bookRepo.findById(id).orElseThrow(BookNotFoundedException::new);
        bookRepo.delete(book);
    }


    public void updateBook(int id,Book book) {
       Book book1 = bookRepo.findById(id).orElseThrow(BookNotFoundedException::new);
        book1.setTitle(book.getTitle());
        book1.setIsbn(book.getIsbn());
        book1.setAuthor(book.getAuthor());
        book1.setDescription(book.getDescription());
        book1.setGenre(book.getGenre());

       bookRepo.save(book1);
    }





}
