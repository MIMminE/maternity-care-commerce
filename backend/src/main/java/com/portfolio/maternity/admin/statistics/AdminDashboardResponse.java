package com.portfolio.maternity.admin.statistics;

import java.math.BigDecimal;

public record AdminDashboardResponse(
        long totalMembers,
        long marketingAgreedMembers,
        long totalOrders,
        BigDecimal totalOrderAmount,
        long requestedConsultations,
        long receivedInquiries
) {
}

