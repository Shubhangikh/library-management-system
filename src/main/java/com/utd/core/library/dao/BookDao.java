package com.utd.core.library.dao;

import com.utd.core.library.dto.BookDto;

import java.util.List;

public interface BookDao {
    List<BookDto> listAllBooks(String pattern);
}
