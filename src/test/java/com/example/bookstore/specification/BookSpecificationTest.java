package com.example.bookstore.specification;

import com.example.bookstore.model.entity.Book;
import com.example.bookstore.model.enumeration.Genre;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookSpecificationTest {

    @Mock
    private Root<Book> bookRoot;

    @Mock
    private CriteriaQuery<?> criteriaQuery;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @InjectMocks
    private BookSpecification bookSpecification;

    @BeforeEach
    void setUp() {
        // Задание начальных условий, если необходимо
    }

    @Test
    void hasTitle_shouldReturnPredicate_whenTitleIsNotNull() {
        // given
        String title = "Test Title";
        Predicate predicate = mock(Predicate.class);
        when(criteriaBuilder.equal(any(), anyString())).thenReturn(predicate);

        // when
        Specification<Book> spec = BookSpecification.hasTitle(title);
        Predicate result = spec.toPredicate(bookRoot, criteriaQuery, criteriaBuilder);

        // then
        assertNotNull(result);
        verify(criteriaBuilder, times(1)).equal(bookRoot.get("title"), title);
    }


    @Test
    void hasTitle_shouldReturnNull_whenTitleIsNull() {
        // given
        String title = null;

        // when
        Specification<Book> spec = BookSpecification.hasTitle(title);
        Predicate result = spec.toPredicate(bookRoot, criteriaQuery, criteriaBuilder);

        // then
        assertNull(result);
    }

    @Test
    void hasAuthor_shouldReturnPredicate_whenAuthorIsNotNull() {
        // given
        String author = "Test Author";
        Predicate predicate = mock(Predicate.class);
        when(criteriaBuilder.equal(any(), anyString())).thenReturn(predicate);

        // when
        Specification<Book> spec = BookSpecification.hasAuthor(author);
        Predicate result = spec.toPredicate(bookRoot, criteriaQuery, criteriaBuilder);

        // then
        assertNotNull(result);
        verify(criteriaBuilder, times(1)).equal(bookRoot.get("author"), author);
    }

    @Test
    void hasAuthor_shouldReturnNull_whenAuthorIsNull() {
        // given
        String author = null;

        // when
        Specification<Book> spec = BookSpecification.hasAuthor(author);
        Predicate result = spec.toPredicate(bookRoot, criteriaQuery, criteriaBuilder);

        // then
        assertNull(result);
    }

    @Test
    void hasGenre_shouldReturnPredicate_whenGenreIsNotNull() {
        // given
        Genre genre = Genre.ROMANCE;
        Predicate predicate = mock(Predicate.class);
        when(criteriaBuilder.equal(any(), any(Genre.class))).thenReturn(predicate);

        // when
        Specification<Book> spec = BookSpecification.hasGenre(genre);
        Predicate result = spec.toPredicate(bookRoot, criteriaQuery, criteriaBuilder);

        // then
        assertNotNull(result);
        verify(criteriaBuilder, times(1)).equal(bookRoot.get("genre"), genre);
    }

    @Test
    void hasGenre_shouldReturnNull_whenGenreIsNull() {
        // given
        Genre genre = null;

        // when
        Specification<Book> spec = BookSpecification.hasGenre(genre);
        Predicate result = spec.toPredicate(bookRoot, criteriaQuery, criteriaBuilder);

        // then
        assertNull(result);
    }

    @Test
    void hasFromPrice_shouldReturnPredicate_whenFromPriceIsNotNull() {
        // given
        BigDecimal fromPrice = new BigDecimal("10.0");
        Predicate predicate = mock(Predicate.class);
        when(criteriaBuilder.greaterThanOrEqualTo(any(), any(BigDecimal.class))).thenReturn(predicate);

        // when
        Specification<Book> spec = BookSpecification.hasFromPrice(fromPrice);
        Predicate result = spec.toPredicate(bookRoot, criteriaQuery, criteriaBuilder);

        // then
        assertNotNull(result);
        verify(criteriaBuilder, times(1)).greaterThanOrEqualTo(bookRoot.get("price"), fromPrice);
    }

    @Test
    void hasFromPrice_shouldReturnNull_whenFromPriceIsNull() {
        // given
        BigDecimal fromPrice = null;

        // when
        Specification<Book> spec = BookSpecification.hasFromPrice(fromPrice);
        Predicate result = spec.toPredicate(bookRoot, criteriaQuery, criteriaBuilder);

        // then
        assertNull(result);
    }

    @Test
    void hasToPrice_shouldReturnPredicate_whenToPriceIsNotNull() {
        // given
        BigDecimal toPrice = new BigDecimal("20.0");
        Predicate predicate = mock(Predicate.class);
        when(criteriaBuilder.lessThanOrEqualTo(any(), any(BigDecimal.class))).thenReturn(predicate);

        // when
        Specification<Book> spec = BookSpecification.hasToPrice(toPrice);
        Predicate result = spec.toPredicate(bookRoot, criteriaQuery, criteriaBuilder);

        // then
        assertNotNull(result);
        verify(criteriaBuilder, times(1)).lessThanOrEqualTo(bookRoot.get("price"), toPrice);
    }

    @Test
    void hasToPrice_shouldReturnNull_whenToPriceIsNull() {
        // given
        BigDecimal toPrice = null;

        // when
        Specification<Book> spec = BookSpecification.hasToPrice(toPrice);
        Predicate result = spec.toPredicate(bookRoot, criteriaQuery, criteriaBuilder);

        // then
        assertNull(result);
    }
}
