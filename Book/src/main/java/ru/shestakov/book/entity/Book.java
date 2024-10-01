package ru.shestakov.book.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;

import lombok.Setter;


@Entity

@Data
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    @Column(name = "title")
    private String title;


    @Column(name = "genre")
    private String genre;


    @Column(name = "author")
    private String author;


    @Column(name = "description")
    private String description;

    @Column(name = "isbn")
    private String isbn;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "status")
    private StatusEnum status;
    public Book(){}
}
