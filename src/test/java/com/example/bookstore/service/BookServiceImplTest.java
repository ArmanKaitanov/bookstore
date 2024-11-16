package com.example.bookstore.service;

import com.example.bookstore.dto.request.GetBookRequestDto;
import com.example.bookstore.dto.request.SaveBookRequestDto;
import com.example.bookstore.dto.request.UpdateBookRequestDto;
import com.example.bookstore.exception.InvalidBookGenreException;
import com.example.bookstore.exception.InvalidBookQuantityException;
import com.example.bookstore.exception.InvalidPriceRangeException;
import com.example.bookstore.model.entity.Book;
import com.example.bookstore.model.enumeration.Genre;
import com.example.bookstore.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private SaveBookRequestDto saveBookRequestDto;
    private UpdateBookRequestDto updateBookRequestDto;

    @BeforeEach
    void setUp() {
        String updatedAuthor = "updatedAuthor";
        String updatedTitle = "updatedTitle";
        String updatedGenre = "Classic";
        BigDecimal updatedPrice = BigDecimal.valueOf(80.55);
        Integer updatedQuantity = 25;

        saveBookRequestDto = new SaveBookRequestDto("Test Title", "Test Author", "Romance", new BigDecimal("10.0"), 5);
        updateBookRequestDto = new UpdateBookRequestDto(updatedTitle, updatedAuthor, updatedGenre, updatedPrice, updatedQuantity);

        book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle(saveBookRequestDto.getTitle());
        book.setAuthor(saveBookRequestDto.getAuthor());
        book.setGenre(Genre.valueOf(saveBookRequestDto.getGenre().toUpperCase()));
        book.setPrice(saveBookRequestDto.getPrice());
        book.setQuantityAvailable(saveBookRequestDto.getQuantity());
    }

    @Test
    void saveBook_shouldSaveBookSuccessfully() {
        // given
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // when
        Book savedBook = bookService.saveBook(saveBookRequestDto);

        // then
        assertNotNull(savedBook);
        assertEquals(saveBookRequestDto.getTitle(), savedBook.getTitle());
        assertEquals(saveBookRequestDto.getAuthor(), savedBook.getAuthor());
        assertEquals(Genre.valueOf(saveBookRequestDto.getGenre().toUpperCase()), savedBook.getGenre());
        assertEquals(saveBookRequestDto.getPrice(), savedBook.getPrice());
        assertEquals(saveBookRequestDto.getQuantity(), savedBook.getQuantityAvailable());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBook_shouldUpdateBookSuccessfully_whenExistingBookIdProvided() {
        // given
        UUID bookId = book.getId();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // when
        Book updatedBook = bookService.updateBook(bookId, updateBookRequestDto);

        // then
        assertNotNull(updatedBook);
        assertEquals(updateBookRequestDto.getTitle(), updatedBook.getTitle());
        assertEquals(updateBookRequestDto.getAuthor(), updatedBook.getAuthor());
        assertEquals(Genre.valueOf(updateBookRequestDto.getGenre().toUpperCase()), updatedBook.getGenre());
        assertEquals(updateBookRequestDto.getPrice(), updatedBook.getPrice());
        assertEquals(updateBookRequestDto.getQuantity(), updatedBook.getQuantityAvailable());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void updateBook_shouldUpdateBookSuccessfully_whenQuantityNotProvidedInRequest() {
        // given
        UUID bookId = book.getId();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        updateBookRequestDto.setQuantity(null);

        // when
        Book updatedBook = bookService.updateBook(bookId, updateBookRequestDto);

        // then
        assertNotNull(updatedBook);
        assertEquals(updateBookRequestDto.getTitle(), updatedBook.getTitle());
        assertEquals(updateBookRequestDto.getAuthor(), updatedBook.getAuthor());
        assertEquals(Genre.valueOf(updateBookRequestDto.getGenre().toUpperCase()), updatedBook.getGenre());
        assertEquals(updateBookRequestDto.getPrice(), updatedBook.getPrice());
        assertEquals(book.getQuantityAvailable(), updatedBook.getQuantityAvailable());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void updateBook_shouldThrowEntityNotFoundException_whenNonExistingBookIdProvided() {
        // given
        UUID nonExistingId = UUID.randomUUID();
        when(bookRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // when // then
        assertThrows(EntityNotFoundException.class, () -> bookService.updateBook(nonExistingId, updateBookRequestDto));
        verify(bookRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void updateBook_shouldThrowInvalidBookQuantityException_whenNegativeQuantityValueProvided() {
        // given
        UUID bookId = book.getId();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        UpdateBookRequestDto dto = UpdateBookRequestDto.builder().quantity(-15).build();

        // when // then
        assertThrows(InvalidBookQuantityException.class, () -> bookService.updateBook(bookId, dto));
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void findById_shouldReturnBookSuccessfully_whenExistingBookIdProvided() {
        // given
        UUID existingId = book.getId();
        when(bookRepository.findById(existingId)).thenReturn(Optional.of(book));

        // when
        Book foundBook = bookService.findById(existingId);

        // then
        assertNotNull(foundBook);
        assertEquals(existingId, foundBook.getId());
        verify(bookRepository, times(1)).findById(existingId);
    }

    @Test
    void findById_shouldThrowEntityNotFoundException_whenNonExistingBookIdProvided() {
        // given
        UUID nonExistingBookId = UUID.randomUUID();
        when(bookRepository.findById(nonExistingBookId)).thenReturn(Optional.empty());

        // when // then
        assertThrows(EntityNotFoundException.class, () -> bookService.findById(nonExistingBookId));
    }

    @Test
    void deleteBook_shouldDeleteBookSuccessfully_whenExistingBookIdProvided() {
        // given
        UUID bookId = book.getId();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // when
        bookService.deleteBook(bookId);

        // then
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void deleteBook_shouldThrowEntityNotFoundException_whenNonExistingBookIdProvided() {
        // given
        UUID nonExistingBookId = UUID.randomUUID();
        when(bookRepository.findById(nonExistingBookId)).thenReturn(Optional.empty());

        // when // then
        assertThrows(EntityNotFoundException.class, () -> bookService.deleteBook(nonExistingBookId));
        verify(bookRepository, never()).delete(any(Book.class));
    }

    @Test
    void getBooksByCriteria_shouldReturnBooksSuccessfully_whenValidCriteriaProvided() {
        // given
        GetBookRequestDto requestDto = new GetBookRequestDto(
                book.getTitle(),
                book.getAuthor(),
                book.getGenre().name(),
                new BigDecimal("5.0"),
                new BigDecimal("500.0")
        );

        when(bookRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.getBooksByCriteria(requestDto);

        // then
        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals(book, books.get(0));
        verify(bookRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void getBooksByCriteria_shouldReturnBooksSuccessfully_whenToPriceAndGenreCriteriaAreNotProvided() {
        // given
        GetBookRequestDto requestDto = GetBookRequestDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .fromPrice(new BigDecimal("10.5"))
                .build();

        when(bookRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.getBooksByCriteria(requestDto);

        // then
        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals(book, books.get(0));
        verify(bookRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void getBooksByCriteria_shouldThrowInvalidPriceRangeException_whenFromPriceGreaterThanToPrice() {
        // given
        GetBookRequestDto requestDto = GetBookRequestDto.builder()
                .fromPrice(new BigDecimal("100"))
                .toPrice(new BigDecimal("20"))
                .build();

        // when // then
        assertThrows(InvalidPriceRangeException.class, () -> bookService.getBooksByCriteria(requestDto));
    }

    @Test
    void getBooksByCriteria_shouldThrowInvalidBookGenreException_whenInvalidGenreProvided() {
        // given
        GetBookRequestDto requestDto = GetBookRequestDto.builder()
                .genre("nonExistingGenre")
                .build();

        // when // then
        assertThrows(InvalidBookGenreException.class, () -> bookService.getBooksByCriteria(requestDto));
    }


//    @Test
//    void validateBookQuantityValue_shouldThrowInvalidBookQuantityException_whenQuantityLessThanZero() {
//        // given
//        int invalidQuantity = -1;
//
//        // when // then
//        assertThrows(InvalidBookQuantityException.class, () -> bookService.validateBookQuantityValue(invalidQuantity));
//    }
//
//    @Test
//    void validateBookGenre_shouldThrowInvalidBookGenreException_whenInvalidGenreProvided() {
//        // given
//        String invalidGenre = "INVALID_GENRE";
//
//        // when // then
//        assertThrows(InvalidBookGenreException.class, () -> bookService.validateBookGenre(invalidGenre));
//    }
}
