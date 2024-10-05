package ru.shestakov.Library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
@Table(name = "library")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "taken_at")
    private long taken_at;
    @Column(name = "return_at")
    private long return_at;
    @Column(name = "book_id")
    private int bookId;
    public Library(int id){
        this.bookId = id;
    }
}
