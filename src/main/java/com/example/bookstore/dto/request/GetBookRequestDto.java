package com.example.bookstore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetBookRequestDto {

    private String title;

    private String author;

    private String genre;

    private BigDecimal fromPrice;

    private BigDecimal toPrice;
}
