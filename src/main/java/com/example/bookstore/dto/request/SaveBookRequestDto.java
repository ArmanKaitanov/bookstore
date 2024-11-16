package com.example.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveBookRequestDto implements SaveOrUpdateBookRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    private String author;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotNull(message = "Available quantity is required")
    private Integer quantity;
}
