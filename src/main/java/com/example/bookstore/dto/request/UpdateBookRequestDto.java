package com.example.bookstore.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookRequestDto implements SaveOrUpdateBookRequestDto {

    private String title;

    private String author;

    private String genre;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price should be greater than 0.0")
    private BigDecimal price;

    @Min(value = 0, message = "Quantity must be 0 or greater")
    private Integer quantity;
}