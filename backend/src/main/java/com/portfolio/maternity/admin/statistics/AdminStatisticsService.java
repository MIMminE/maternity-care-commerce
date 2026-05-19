package com.portfolio.maternity.admin.statistics;

import com.portfolio.maternity.domain.consultation.ConsultationRepository;
import com.portfolio.maternity.domain.consultation.ConsultationStatus;
import com.portfolio.maternity.domain.inquiry.InquiryRepository;
import com.portfolio.maternity.domain.inquiry.InquiryStatus;
import com.portfolio.maternity.domain.member.MemberRepository;
import com.portfolio.maternity.domain.order.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminStatisticsService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ConsultationRepository consultationRepository;
    private final InquiryRepository inquiryRepository;

    public AdminStatisticsService(
            MemberRepository memberRepository,
            OrderRepository orderRepository,
            ConsultationRepository consultationRepository,
            InquiryRepository inquiryRepository
    ) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.consultationRepository = consultationRepository;
        this.inquiryRepository = inquiryRepository;
    }

    @Transactional(readOnly = true)
    public AdminDashboardResponse getDashboard() {
        return new AdminDashboardResponse(
                memberRepository.count(),
                memberRepository.countMarketingAgreedMembers(),
                orderRepository.count(),
                orderRepository.sumTotalAmount(),
                consultationRepository.countByStatus(ConsultationStatus.REQUESTED),
                inquiryRepository.countByStatus(InquiryStatus.RECEIVED)
        );
    }
}

