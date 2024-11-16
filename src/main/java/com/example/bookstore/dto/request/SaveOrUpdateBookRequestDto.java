package com.example.bookstore.dto.request;

import java.math.BigDecimal;

public interface SaveOrUpdateBookRequestDto {

    String getTitle();

    String getAuthor();

    String getGenre();

    BigDecimal getPrice();

    Integer getQuantity();
}
