package com.example.bookstore.controller;

import com.example.bookstore.dto.request.GetBookRequestDto;
import com.example.bookstore.dto.request.SaveBookRequestDto;
import com.example.bookstore.dto.request.UpdateBookRequestDto;
import com.example.bookstore.model.entity.Book;
import com.example.bookstore.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Validated
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> saveBook(@Valid @RequestBody SaveBookRequestDto dto) {
        Book createdBook = bookService.saveBook(dto);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateBookQuantityValue(@PathVariable UUID id, @Valid @RequestBody UpdateBookRequestDto dto) {
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "genre", required = false) String genre,
            @DecimalMin(value = "0.0", message = "Price should be 0 or greater")
            @RequestParam(value = "fromPrice", required = false) BigDecimal fromPrice,
            @DecimalMin(value = "0.0", message = "Price should be 0 or greater")
            @RequestParam(value = "toPrice", required = false) BigDecimal toPrice
    ) {
        GetBookRequestDto dto = GetBookRequestDto.builder()
                .title(title)
                .author(author)
                .genre(genre)
                .fromPrice(fromPrice)
                .toPrice(toPrice)
                .build();

        return ResponseEntity.ok(bookService.getBooksByCriteria(dto));
    }
}
