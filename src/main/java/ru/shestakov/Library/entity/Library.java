package ru.shestakov.Library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Data
@Table(name = "freebooks")
public class Library {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "taken_at")
    private String taken_at;
    @Column(name = "return_at")
    private String return_at;
    @Column(name = "book")
    private int book;
    public Library(int id){
        this.book = id;
    }
}
