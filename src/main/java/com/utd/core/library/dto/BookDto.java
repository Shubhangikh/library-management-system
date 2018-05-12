package com.utd.core.library.dto;

import java.util.Date;

public class BookDto {
    private String isbn10;
    private String title;
    private String authors;
    private String isAvailable;
    private Date inDate;
    private Date dueDate;

    public BookDto() {}
    public BookDto(BookDto book) {
        this.isbn10 = book.isbn10;
        this.title = book.title;
        this.authors = book.authors;
        this.isAvailable = book.isAvailable;
        this.inDate = book.inDate;
        this.dueDate = book.dueDate;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
