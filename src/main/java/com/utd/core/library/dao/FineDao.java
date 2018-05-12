package com.utd.core.library.dao;

import com.utd.core.library.dto.BookLoanDto;
import com.utd.core.library.dto.FineDto;

import java.util.List;

public interface FineDao {
    List<BookLoanDto> getRecordsRequiringFineAmount();
    boolean exists(int loanId);
    void updateFineAmount(int loanId, int daysElapsed);
    void insertFineAmount(int loanId, int daysElapsed);
    double getTotalFineAmount(String searchString);
    List<FineDto> getFineAmountDetails(String searchString);
    int payFine(int loanId);
    boolean bookCheckedIn(int loanId);
}
