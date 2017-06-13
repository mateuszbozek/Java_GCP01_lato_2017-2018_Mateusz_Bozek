package com.lenda.marc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;


@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    @Size(max = 10)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private int isbn;

    public Book(){
    }

    public Book(Long id, String title, String author, int isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
}
