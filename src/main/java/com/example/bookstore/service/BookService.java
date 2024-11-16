package com.example.bookstore.service;

import com.example.bookstore.dto.request.GetBookRequestDto;
import com.example.bookstore.dto.request.SaveBookRequestDto;
import com.example.bookstore.dto.request.UpdateBookRequestDto;
import com.example.bookstore.model.entity.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {

    Book saveBook(SaveBookRequestDto requestDto);

    Book updateBook(UUID id, UpdateBookRequestDto dto);

    Book findById(UUID id);

    void deleteBook(UUID id);

    List<Book> getBooksByCriteria(GetBookRequestDto dto);
}
