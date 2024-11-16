package com.example.bookstore.service;

import com.example.bookstore.dto.request.GetBookRequestDto;
import com.example.bookstore.dto.request.SaveBookRequestDto;
import com.example.bookstore.dto.request.SaveOrUpdateBookRequestDto;
import com.example.bookstore.dto.request.UpdateBookRequestDto;
import com.example.bookstore.exception.InvalidBookGenreException;
import com.example.bookstore.exception.InvalidBookQuantityException;
import com.example.bookstore.exception.InvalidPriceRangeException;
import com.example.bookstore.specification.BookSpecification;
import com.example.bookstore.model.entity.Book;
import com.example.bookstore.model.enumeration.Genre;
import com.example.bookstore.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Book saveBook(SaveBookRequestDto saveBookRequestDto) {
        Book bookToSave = new Book();
        updateBookFields(bookToSave, saveBookRequestDto);

        return bookRepository.save(bookToSave);
    }

    @Override
    @Transactional
    public Book updateBook(UUID id, UpdateBookRequestDto updateBookRequestDto) {
        Book bookToUpdate = findById(id);

        updateBookFields(bookToUpdate, updateBookRequestDto);

        return bookToUpdate;
    }

    private void updateBookFields(Book bookToUpdate, SaveOrUpdateBookRequestDto dto) {
        if(dto.getTitle() != null) {
            bookToUpdate.setTitle(dto.getTitle());
        }
        if(dto.getAuthor() != null) {
            bookToUpdate.setAuthor(dto.getAuthor());
        }
        if(dto.getGenre() != null) {
            bookToUpdate.setGenre(validateBookGenre(dto.getGenre()));
        }
        if(dto.getPrice() != null) {
            bookToUpdate.setPrice(dto.getPrice());
        }
        if(dto.getQuantity() != null) {
            validateBookQuantityValue(dto.getQuantity());
            bookToUpdate.setQuantityAvailable(dto.getQuantity());
        }
    }

    @Override
    public Book findById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book with ID: %s not found", id)));
    }

    @Override
    @Transactional
    public void deleteBook(UUID id) {
        bookRepository.delete(findById(id));
    }

    @Override
    public List<Book> getBooksByCriteria(GetBookRequestDto dto) {
        validatePriceRange(dto.getFromPrice(), dto.getToPrice());
        Genre genre = validateBookGenre(dto.getGenre());

        return bookRepository.findAll(
                Specification.where(BookSpecification.hasTitle(dto.getTitle()))
                        .and(BookSpecification.hasAuthor(dto.getAuthor()))
                        .and(BookSpecification.hasGenre(genre))
                        .and(BookSpecification.hasFromPrice(dto.getFromPrice()))
                        .and(BookSpecification.hasToPrice(dto.getToPrice()))
        );
    }

    private void validatePriceRange(BigDecimal fromPrice, BigDecimal toPrice) {
        if(fromPrice != null && toPrice != null && fromPrice.compareTo(toPrice) > 0) {
            throw new InvalidPriceRangeException(
                    String.format("Invalid price range values. 'From' price value must be less than or equal to 'To' price. From: %s, To: %s", fromPrice, toPrice)
            );
        }
    }

    private void validateBookQuantityValue(Integer newQuantity) {
        if(newQuantity < 0) {
            throw new InvalidBookQuantityException(
                    String.format("Book quantity is invalid: %d. Value should be between 0 and 10 000 inclusive", newQuantity)
            );
        }
    }

    private Genre validateBookGenre(String genreValue) {
        Genre genre = null;

        if(genreValue != null) {
            try {
                genre = Genre.valueOf(genreValue.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidBookGenreException(
                        String.format("Invalid book genre value: %s. Valid values are: %s", genre, Arrays.toString(Genre.values()))
                );
            }
        }

        return genre;
    }
}
