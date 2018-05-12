package com.utd.core.library.dao;

import com.utd.core.library.model.Author;

import java.util.List;

public interface AuthorDao {
    public List<Author> getAuthorsByISBN(String isbn);
}
