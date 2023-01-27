package com.company.model;

import java.time.LocalDate;

public class Book {
    private Integer id;
    private String name;
    private String author;
    private String publisher;
    private LocalDate publicationDate;
    private int count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id= " + id +
                ", name= " + name + '\'' +
                ", author= " + author + '\'' +
                ", publisher= " + publisher + '\'' +
                ", publicationDate= " + publicationDate + '\'' +
                ", available= " + count + '\'' +
                '}';
    }
}
