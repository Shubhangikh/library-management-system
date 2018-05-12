package com.utd.core.library.service;

import com.utd.core.library.dao.BookDao;
import com.utd.core.library.dao.BookLoanDao;
import com.utd.core.library.dao.BorrowerDao;
import com.utd.core.library.dao.FineDao;
import com.utd.core.library.dto.BookDto;
import com.utd.core.library.dto.BookLoanDto;
import com.utd.core.library.dto.FineDto;
import com.utd.core.library.formobject.RequestForm;
import com.utd.core.library.formobject.ResponseForm;
import com.utd.core.library.model.Borrower;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LibraryService {
    @Autowired
    private BorrowerDao borrowerDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private BookLoanDao bookLoanDao;
    @Autowired
    private FineDao fineDao;
    private ModelMapper modelMapper = new ModelMapper();

    public ResponseForm register(RequestForm requestForm) {
        Borrower borrower = modelMapper.map(requestForm, Borrower.class);
        ResponseForm responseForm = new ResponseForm();
        if(borrowerDao.exists(borrower)) {
            responseForm.setMessage("Account already exists.");
            responseForm.setStatus(0);
            return responseForm;
        }
        String cardId = borrowerDao.getCardId();
        int nextCardId = Integer.parseInt(cardId) + 1;
        borrower.setCardId("00" + String.valueOf(nextCardId));
        int insertCount = borrowerDao.insert(borrower);
        if(insertCount > 0) {
            responseForm.setMessage("Account created successfully!");
            responseForm.setStatus(1);
        }
        return responseForm;
    }

    public ResponseForm searchBooks(RequestForm requestForm) {
        ResponseForm responseForm = new ResponseForm();
        List<BookDto> booksDto = bookDao.listAllBooks(requestForm.getSearchString());
        if(booksDto.size() == 0) {
            responseForm.setMessage("No record found.");
            responseForm.setStatus(0);
            return responseForm;
        }
        Map<String, BookDto> books = new HashMap<>();
        for(BookDto bookDto:booksDto) {
            BookDto book = null;
            if(books.get(bookDto.getIsbn10()) != null) {
                book = books.get(bookDto.getIsbn10());
                book.setAuthors(book.getAuthors()!=null?book.getAuthors() + "," + bookDto.getAuthors():bookDto.getAuthors());


            } else {
                book = new BookDto(bookDto);
                book.setAuthors(bookDto.getAuthors());

            }
            if(bookDto.getDueDate() != null) {
                if(book.getDueDate().compareTo(bookDto.getDueDate()) == 0 || bookDto.getDueDate().compareTo(book.getDueDate()) > 0) {
                    book.setDueDate(bookDto.getDueDate());
                    book.setIsAvailable(bookDto.getInDate() == null ? "No" : "Yes");
                }
            } else {
                book.setIsAvailable("Yes");
            }
            books.put(book.getIsbn10(), book);

        }
        responseForm.setBooks(books);
        responseForm.setStatus(1);
        return responseForm;
    }

    public ResponseForm bookCheckOut(RequestForm requestForm) {
        ResponseForm responseForm = new ResponseForm();
        if(bookLoanDao.alreadyCheckout(requestForm.getIsbn())) {
            responseForm.setMessage("Book has been already checked-out.");
            responseForm.setStatus(0);
            return responseForm;
        }
        if(bookLoanDao.getCountOfBorrowedBooks(requestForm.getCardId()) == 3) {
            responseForm.setMessage("Maximum check-out limit for user reached. Unable to check-out.");
            responseForm.setStatus(0);
            return responseForm;
        }

        int insertCount = bookLoanDao.bookCheckOut(requestForm.getCardId(), requestForm.getIsbn());
        if(insertCount > 0) {
            responseForm.setMessage("Book check-out successful!");
            responseForm.setStatus(1);
        }
        return responseForm;
    }

    public ResponseForm checkInBooksList(RequestForm requestForm) {
        ResponseForm responseForm = new ResponseForm();
        if(bookLoanDao.checkInBooksList(requestForm.getSearchString()).size() == 0) {
            responseForm.setMessage("No record found.");
            responseForm.setStatus(0);
            return responseForm;
        }
        responseForm.setCheckInBooks(bookLoanDao.checkInBooksList(requestForm.getSearchString()));
        responseForm.setStatus(1);
        return responseForm;
    }

    public ResponseForm checkInBook(RequestForm requestForm) {
        ResponseForm responseForm = new ResponseForm();
        if(bookLoanDao.checkInBook(requestForm.getLoanId()) > 0) {
            responseForm.setMessage("Book checked-in successfully!");
            responseForm.setStatus(1);
        } else {
            responseForm.setMessage("Book check-in could not happen at this moment, try again later.");
            responseForm.setStatus(0);
        }
        return responseForm;
    }

    public ResponseForm updateFineAmounts() {
        ResponseForm responseForm = new ResponseForm();
        List<BookLoanDto> bookLoans = fineDao.getRecordsRequiringFineAmount();
        for(BookLoanDto bookLoan : bookLoans) {
            if(fineDao.exists(bookLoan.getLoanId())) {
                fineDao.updateFineAmount(bookLoan.getLoanId(), bookLoan.getDaysElapsed());
            } else {
                fineDao.insertFineAmount(bookLoan.getLoanId(), bookLoan.getDaysElapsed());
            }
        }
        responseForm.setMessage("Fine amounts updated successfully!");
        responseForm.setStatus(1);
        return responseForm;
    }

    public ResponseForm getBorrowerFineAmountsList(RequestForm requestForm) {
        ResponseForm responseForm = new ResponseForm();
        List<FineDto> fineAmountDetails = fineDao.getFineAmountDetails(requestForm.getSearchString());
        if(fineAmountDetails.size() == 0) {
            responseForm.setMessage("No record found.");
            responseForm.setStatus(0);
            return responseForm;
        }
        double fineAmount = fineDao.getTotalFineAmount(requestForm.getSearchString());
        responseForm.setTotalFineAmount(fineAmount);
        responseForm.setFineDetails(fineAmountDetails);
        responseForm.setStatus(1);
        return responseForm;
    }

    public ResponseForm payFine(RequestForm requestForm) {
        ResponseForm responseForm = new ResponseForm();
        if(!fineDao.bookCheckedIn(requestForm.getLoanId())) {
            responseForm.setMessage("Payment is possible only after the book has been checked-in.");
            responseForm.setStatus(0);
            return responseForm;
        }
        if(fineDao.payFine(requestForm.getLoanId()) > 0) {
            responseForm.setMessage("Payment successful!");
            responseForm.setStatus(1);
        } else {
            responseForm.setMessage("Payment could not happen at this moment, try again later.");
            responseForm.setStatus(0);
        }
        return responseForm;
    }
}
