package by.psu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExcursionRequest(
        @NotBlank
        String guideName,
        @NotBlank
        String excursionType,
        @NotNull
        Boolean lunchIncluded,
        @NotBlank
        String name,
        @NotNull
        @Positive
        BigDecimal price,
        @NotNull
        LocalDate from,
        @NotNull
        LocalDate to
) {
}