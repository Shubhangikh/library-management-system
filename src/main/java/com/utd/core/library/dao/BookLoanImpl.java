package com.utd.core.library.dao;

import com.utd.core.library.dto.BookLoanDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookLoanImpl implements BookLoanDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public int getCountOfBorrowedBooks(String cardId) {
        String query = "SELECT COUNT(*) FROM BOOK_LOANS WHERE CARD_ID=? AND DATE_IN IS NULL;";
        return jdbcTemplate.queryForObject(
                query, new Object[] { cardId }, Integer.class);
    }

    @Override
    public int bookCheckOut(String cardId, String isbn) {
        String insertQuery = "INSERT INTO BOOK_LOANS(ISBN10, CARD_ID, DATE_OUT, DUE_DATE, DATE_IN) VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), NULL)";
        return jdbcTemplate.update(insertQuery, isbn, cardId);
    }

    @Override
    public List<BookLoanDto> checkInBooksList(String pattern) {
        String query = "SELECT LOAN_ID, ISBN10, B.CARD_ID, BNAME, DUE_DATE\n" +
                "FROM BORROWER AS B, BOOK_LOANS AS BL\n" +
                "WHERE (B.CARD_ID LIKE ? OR BNAME LIKE ? OR ISBN10 LIKE ?) AND DATE_IN IS NULL AND B.CARD_ID=BL.CARD_ID;";
        return jdbcTemplate.query(query, new Object[]{"%" + pattern + "%", "%" + pattern + "%", "%" + pattern + "%"}, new ResultSetExtractor<List<BookLoanDto>>() {
            @Override
            public List<BookLoanDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<BookLoanDto> books = new ArrayList<>();
                while(rs.next()) {
                    BookLoanDto book = new BookLoanDto();
                    book.setLoanId(rs.getInt(1));
                    book.setIsbn(rs.getString(2));
                    book.setCardId(rs.getString(3));
                    book.setbName(rs.getString(4));
                    book.setDueDate(rs.getDate(5));

                    books.add(book);
                }
                return books;
            }
        });
    }

    @Override
    public int checkInBook(int loanId) {
        String sql = "UPDATE BOOK_LOANS SET DATE_IN = CURDATE() WHERE LOAN_ID = ?";
        return jdbcTemplate.update(sql, loanId);
    }

    @Override
    public boolean alreadyCheckout(String isbn) {
        String query = "SELECT COUNT(*) FROM BOOK_LOANS WHERE ISBN10 = ? AND DATE_IN IS NULL";
        int count = jdbcTemplate.queryForObject(
                query, new Object[] { isbn }, Integer.class);
        return count > 0;
    }
}
