package com.utd.core.library.dao;

import com.utd.core.library.dto.BookDto;
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
public class BookDaoImpl implements BookDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<BookDto> listAllBooks(String pattern) {
        String sql = "SELECT O.ISBN10, O.TITLE, O.NAME, BL.DUE_DATE, BL.DATE_IN\n" +
                "FROM\n" +
                "(SELECT B.ISBN10, B.TITLE, A.NAME\n" +
                "FROM BOOK AS B, BOOK_AUTHORS AS BA, AUTHORS AS A\n" +
                "WHERE B.ISBN10=BA.ISBN10 \n" +
                "AND BA.AUTHOR_ID=A.AUTHOR_ID\n" +
                "AND (B.ISBN10 LIKE ? OR A.NAME LIKE ? OR B.TITLE LIKE ?)) AS O\n" +
                "LEFT JOIN BOOK_LOANS AS BL\n" +
                "ON O.ISBN10=BL.ISBN10";
        return jdbcTemplate.query(sql, new Object[]{"%" + pattern + "%", "%" + pattern + "%", "%" + pattern + "%"}, new ResultSetExtractor<List<BookDto>>() {

            @Override
            public List<BookDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<BookDto> bookList = new ArrayList<>();
                while(rs.next()) {
                    BookDto book = new BookDto();
                    book.setIsbn10(rs.getString(1));
                    book.setTitle(rs.getString(2));
                    book.setAuthors(rs.getString(3));
                    book.setDueDate(rs.getDate(4));
                    book.setInDate(rs.getDate(5));

                    bookList.add(book);
                }
                return bookList;
            }
        });
    }
}
