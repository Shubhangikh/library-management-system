package com.utd.core.library.dao;

import com.utd.core.library.dto.BookLoanDto;
import com.utd.core.library.dto.FineDto;
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
public class FineDaoImpl implements FineDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<BookLoanDto> getRecordsRequiringFineAmount() {
        String sql = "SELECT LOAN_ID, DATEDIFF(CURDATE(), DUE_DATE) FROM BOOK_LOANS WHERE CURDATE() > DUE_DATE AND DATE_IN IS NULL";
        return jdbcTemplate.query(sql, new ResultSetExtractor<List<BookLoanDto>>() {
            @Override
            public List<BookLoanDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<BookLoanDto> bookLoans = new ArrayList<>();
                while(rs.next()) {
                    BookLoanDto bookLoan = new BookLoanDto();
                    bookLoan.setLoanId(rs.getInt(1));
                    bookLoan.setDaysElapsed(rs.getInt(2));

                    bookLoans.add(bookLoan);
                }
                return bookLoans;
            }
        });
    }

    @Override
    public boolean exists(int loanId) {
        String query = "SELECT COUNT(*) FROM FINES WHERE LOAN_ID = ? AND PAID=FALSE";
        int count = jdbcTemplate.queryForObject(
                query, new Object[] { loanId }, Integer.class);
        return count > 0;
    }

    @Override
    public void updateFineAmount(int loanId, int daysElapsed) {
        String sql = "UPDATE FINES SET FINE_AMT = ?*0.25 WHERE LOAN_ID=? AND PAID=FALSE;";
        jdbcTemplate.update(sql, daysElapsed, loanId);
    }

    @Override
    public void insertFineAmount(int loanId, int daysElapsed) {
        String sql = "INSERT INTO FINES(LOAN_ID, FINE_AMT, PAID) VALUES(?, ?*0.25, 0)";
        jdbcTemplate.update(sql, loanId, daysElapsed);
    }

    @Override
    public double getTotalFineAmount(String pattern) {
        String query = "SELECT SUM(FINE_AMT) FROM FINES, BOOK_LOANS, BORROWER WHERE FINES.LOAN_ID=BOOK_LOANS.LOAN_ID AND BORROWER.CARD_ID=BOOK_LOANS.CARD_ID AND (BORROWER.CARD_ID LIKE ? OR BNAME LIKE ?) AND PAID=FALSE";
        return jdbcTemplate.queryForObject(
                query, new Object[] { "%" + pattern + "%", "%" + pattern + "%" }, Double.class);
    }

    @Override
    public List<FineDto> getFineAmountDetails(String pattern) {
        String sql = "SELECT FA.LOAN_ID, BL.CARD_ID, BNAME, ISBN10, FINE_AMT FROM BORROWER AS B, FINES AS FA, BOOK_LOANS AS BL WHERE B.CARD_ID=BL.CARD_ID AND BL.LOAN_ID = FA.LOAN_ID AND FA.PAID=FALSE AND (B.CARD_ID LIKE ? OR BNAME LIKE ?)";
        return jdbcTemplate.query(sql, new Object[]{"%" + pattern + "%", "%" + pattern + "%"}, new ResultSetExtractor<List<FineDto>>() {
            @Override
            public List<FineDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<FineDto> fineAmountList = new ArrayList<>();
                while(rs.next()) {
                    FineDto fineDto = new FineDto();
                    fineDto.setLoanId(rs.getInt(1));
                    fineDto.setCardId(rs.getString(2));
                    fineDto.setbName(rs.getString(3));
                    fineDto.setIsbn(rs.getString(4));
                    fineDto.setFineAmount(rs.getDouble(5));

                    fineAmountList.add(fineDto);
                }
                return fineAmountList;
            }
        });
    }



    @Override
    public int payFine(int loanId) {
        String sql = "UPDATE FINES SET PAID = TRUE WHERE LOAN_ID = ?";
        return jdbcTemplate.update(sql, loanId);
    }

    @Override
    public boolean bookCheckedIn(int loanId) {
        String query = "SELECT COUNT(*) FROM FINES AS FA, BOOK_LOANS AS BL WHERE FA.LOAN_ID = BL.LOAN_ID AND BL.LOAN_ID = ? AND BL.DATE_IN IS NOT NULL";
        int count = jdbcTemplate.queryForObject(
                query, new Object[] { loanId }, Integer.class);
        return count > 0;
    }
}
