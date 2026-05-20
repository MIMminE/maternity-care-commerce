import type { CareProfile, Consultation, Order, Product } from './types';

export const demoProfile: CareProfile = {
  status: 'PREGNANT',
  expectedBirthDate: '2026-12-01',
  pregnancyWeek: 13
};

export const demoProducts: Product[] = [
  { id: 1, name: '케어 바디로션', category: 'BODY_CARE', price: 32000, stockQuantity: 30, status: 'ON_SALE' },
  { id: 2, name: '케어 샴푸', category: 'HAIR_CARE', price: 28000, stockQuantity: 25, status: 'ON_SALE' },
  { id: 3, name: '케어 어메니티 세트', category: 'GIFT_SET', price: 54000, stockQuantity: 12, status: 'ON_SALE' },
  { id: 4, name: '케어 홈 릴렉스 키트', category: 'GIFT_SET', price: 68000, stockQuantity: 8, status: 'ON_SALE' }
];

export const demoOrders: Order[] = [
  { id: 1, orderNumber: 'ORD-20260519-001', status: '배송 준비', totalAmount: 86000, createdAt: '2026-05-19' },
  { id: 2, orderNumber: 'ORD-20260512-004', status: '배송 완료', totalAmount: 32000, createdAt: '2026-05-12' }
];

export const demoConsultations: Consultation[] = [
  { id: 1, title: '배송 일정 변경 문의', status: '답변 대기', requestedAt: '2026-05-19' },
  { id: 2, title: '상품 구성 문의', status: '답변 완료', requestedAt: '2026-05-16' }
];
