package com.utd.core.library.dao;

import com.utd.core.library.model.Author;
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
public class AuthorDaoImpl implements AuthorDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Author> getAuthorsByISBN(String isbn) {
        String sql = "SELECT * FROM AUTHORS WHERE AUTHOR_ID IN (SELECT AUTHOR_ID FROM BOOK_AUTHORS WHERE ISBN10 = ?)";
        List<Author> authors = jdbcTemplate.query(sql, new Object[]{isbn}, new ResultSetExtractor<List<Author>>() {

            @Override
            public List<Author> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Author> authorList = new ArrayList<>();
                while(rs.next()) {
                    Author author = new Author();
                    author.setAuthorId(rs.getInt(0));
                    author.setName(rs.getString(1));

                    authorList.add(author);
                }
                return authorList;
            }
        });
        return authors;
    }
}
