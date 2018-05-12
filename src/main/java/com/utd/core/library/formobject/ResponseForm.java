package com.utd.core.library.formobject;

import com.utd.core.library.dto.BookDto;
import com.utd.core.library.dto.BookLoanDto;
import com.utd.core.library.dto.FineDto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ResponseForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private String message;
    private int status;
    private Map<String, BookDto> books;
    private List<BookLoanDto> checkInBooks;
    private List<FineDto> fineDetails;
    private double totalFineAmount;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, BookDto> getBooks() {
        return books;
    }

    public void setBooks(Map<String, BookDto> books) {
        this.books = books;
    }

    public List<BookLoanDto> getCheckInBooks() {
        return checkInBooks;
    }

    public void setCheckInBooks(List<BookLoanDto> checkInBooks) {
        this.checkInBooks = checkInBooks;
    }

    public List<FineDto> getFineDetails() {
        return fineDetails;
    }

    public void setFineDetails(List<FineDto> fineDetails) {
        this.fineDetails = fineDetails;
    }

    public double getTotalFineAmount() {
        return totalFineAmount;
    }

    public void setTotalFineAmount(double totalFineAmount) {
        this.totalFineAmount = totalFineAmount;
    }
}
