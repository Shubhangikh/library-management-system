package com.utd.core.library.dao;

import com.utd.core.library.dto.BookLoanDto;

import java.util.List;

public interface BookLoanDao {
    int getCountOfBorrowedBooks(String cardId);
    int bookCheckOut(String cardId, String isbn);
    List<BookLoanDto> checkInBooksList(String pattern);
    int checkInBook(int loanId);
    boolean alreadyCheckout(String isbn);
}
