package ru.shestakov.book.entity;

import jakarta.persistence.*;

import lombok.Getter;

import lombok.Setter;


@Entity

@Getter
@Setter
@Table(name = "book")
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
    private String status;
    public Book(){}
}
