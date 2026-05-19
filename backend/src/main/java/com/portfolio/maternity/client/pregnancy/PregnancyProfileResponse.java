package com.portfolio.maternity.client.pregnancy;

import com.portfolio.maternity.domain.pregnancy.PregnancyProfile;
import com.portfolio.maternity.domain.pregnancy.PregnancyStatus;
import java.time.LocalDate;

public record PregnancyProfileResponse(
        Long id,
        PregnancyStatus status,
        LocalDate expectedBirthDate,
        LocalDate childBirthDate,
        Integer pregnancyWeek
) {
    public static PregnancyProfileResponse from(PregnancyProfile profile) {
        return new PregnancyProfileResponse(
                profile.getId(),
                profile.getStatus(),
                profile.getExpectedBirthDate(),
                profile.getChildBirthDate(),
                profile.getPregnancyWeek()
        );
    }
}

