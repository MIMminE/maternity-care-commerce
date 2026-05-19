package com.portfolio.maternity.client.pregnancy;

import com.portfolio.maternity.domain.pregnancy.PregnancyStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record PregnancyProfileRequest(
        @NotNull PregnancyStatus status,
        LocalDate expectedBirthDate,
        LocalDate childBirthDate,
        @Min(1) @Max(42) Integer pregnancyWeek
) {
}

