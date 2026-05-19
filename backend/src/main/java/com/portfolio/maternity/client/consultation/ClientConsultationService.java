package com.portfolio.maternity.client.consultation;

import com.portfolio.maternity.domain.consultation.Consultation;
import com.portfolio.maternity.domain.consultation.ConsultationRepository;
import com.portfolio.maternity.domain.member.Member;
import com.portfolio.maternity.domain.member.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientConsultationService {

    private final ConsultationRepository consultationRepository;
    private final MemberRepository memberRepository;

    public ClientConsultationService(
            ConsultationRepository consultationRepository,
            MemberRepository memberRepository
    ) {
        this.consultationRepository = consultationRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public List<ConsultationResponse> findMine(Long memberId) {
        return consultationRepository.findByMemberIdOrderByIdDesc(memberId)
                .stream()
                .map(ConsultationResponse::from)
                .toList();
    }

    @Transactional
    public ConsultationResponse create(Long memberId, ConsultationCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        Consultation consultation = consultationRepository.save(new Consultation(
                member,
                request.title(),
                request.body()
        ));
        return ConsultationResponse.from(consultation);
    }
}

