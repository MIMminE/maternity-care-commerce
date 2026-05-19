package com.portfolio.maternity.client.pregnancy;

import com.portfolio.maternity.domain.member.Member;
import com.portfolio.maternity.domain.member.MemberRepository;
import com.portfolio.maternity.domain.pregnancy.PregnancyProfile;
import com.portfolio.maternity.domain.pregnancy.PregnancyProfileRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientPregnancyProfileService {

    private final MemberRepository memberRepository;
    private final PregnancyProfileRepository pregnancyProfileRepository;

    public ClientPregnancyProfileService(
            MemberRepository memberRepository,
            PregnancyProfileRepository pregnancyProfileRepository
    ) {
        this.memberRepository = memberRepository;
        this.pregnancyProfileRepository = pregnancyProfileRepository;
    }

    @Transactional(readOnly = true)
    public Optional<PregnancyProfileResponse> findMine(Long memberId) {
        return pregnancyProfileRepository.findByMemberId(memberId)
                .map(PregnancyProfileResponse::from);
    }

    @Transactional
    public PregnancyProfileResponse upsertMine(Long memberId, PregnancyProfileRequest request) {
        PregnancyProfile profile = pregnancyProfileRepository.findByMemberId(memberId)
                .map(existing -> {
                    existing.update(
                            request.status(),
                            request.expectedBirthDate(),
                            request.childBirthDate(),
                            request.pregnancyWeek()
                    );
                    return existing;
                })
                .orElseGet(() -> {
                    Member member = memberRepository.findById(memberId)
                            .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
                    return pregnancyProfileRepository.save(new PregnancyProfile(
                            member,
                            request.status(),
                            request.expectedBirthDate(),
                            request.childBirthDate(),
                            request.pregnancyWeek()
                    ));
                });

        return PregnancyProfileResponse.from(profile);
    }
}

