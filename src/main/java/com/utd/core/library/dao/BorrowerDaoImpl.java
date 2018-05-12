package com.utd.core.library.dao;

import com.utd.core.library.model.Borrower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BorrowerDaoImpl implements BorrowerDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public int insert(Borrower borrower) {
        String insertQuery = "INSERT INTO BORROWER(CARD_ID, SSN, BNAME, ADDRESS, PHONE) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(insertQuery, borrower.getCardId(), borrower.getSsn(), borrower.getbName(),
                                borrower.getAddress(), borrower.getPhone());
    }

    @Override
    public boolean exists(Borrower borrower) {
        String query = "SELECT COUNT(*) FROM BORROWER WHERE SSN = ?";
        int count = jdbcTemplate.queryForObject(
                query, new Object[] { borrower.getSsn() }, Integer.class);
        return count > 0;
    }

    @Override
    public String getCardId() {
        String query = "SELECT CARD_ID FROM BORROWER ORDER BY CARD_ID DESC LIMIT 1";
        return jdbcTemplate.queryForObject(query, String.class);
    }
}
