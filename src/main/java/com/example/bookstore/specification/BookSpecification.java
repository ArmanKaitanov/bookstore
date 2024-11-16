package com.example.bookstore.specification;

import com.example.bookstore.model.entity.Book;
import com.example.bookstore.model.enumeration.Genre;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) -> title == null? null : cb.equal(root.get("title"), title);
    }

    public static Specification<Book> hasAuthor(String author) {
        return (root, query, cb) -> author == null? null : cb.equal(root.get("author"), author);
    }

    public static Specification<Book> hasGenre(Genre genre) {
        return (root, query, cb) -> genre == null? null : cb.equal(root.get("genre"), genre);
    }

    public static Specification<Book> hasFromPrice(BigDecimal fromPrice) {
        return (root, query, cb) -> fromPrice == null? null : cb.greaterThanOrEqualTo(root.get("price"), fromPrice);
    }

    public static Specification<Book> hasToPrice(BigDecimal toPrice) {
        return ((root, query, cb) -> toPrice == null? null : cb.lessThanOrEqualTo(root.get("price"), toPrice));
    }
}
