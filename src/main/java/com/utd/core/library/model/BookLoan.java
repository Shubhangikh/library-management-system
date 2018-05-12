package com.utd.core.library.model;

import java.io.Serializable;
import java.util.Date;

public class BookLoan implements Serializable{
    private static final long serialVersionUID = 5L;

    private int loanId;
    private String isbn;
    private String cardId;
    private Date dateOut;
    private Date dueDate;
    private Date dateIn;

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    @Override
    public String toString() {
        return "BookLoan{" +
                "loanId=" + loanId +
                ", isbn='" + isbn + '\'' +
                ", cardId=" + cardId +
                ", dateOut=" + dateOut +
                ", dueDate=" + dueDate +
                ", dateIn=" + dateIn +
                '}';
    }

}
