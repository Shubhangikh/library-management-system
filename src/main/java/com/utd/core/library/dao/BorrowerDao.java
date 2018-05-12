package com.utd.core.library.dao;

import com.utd.core.library.model.Borrower;
import org.springframework.stereotype.Component;

@Component
public interface BorrowerDao {
    int insert(Borrower borrower);
    boolean exists(Borrower borrower);
    String getCardId();
}
