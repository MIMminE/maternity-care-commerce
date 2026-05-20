import type { Dashboard, MarketingMember, Member, Product, SupportTicket } from './types';

export const emptyDashboard: Dashboard = {
  totalMembers: 2,
  marketingAgreedMembers: 2,
  totalOrders: 1,
  totalOrderAmount: 64000,
  requestedConsultations: 1,
  receivedInquiries: 1
};

export const demoMembers: Member[] = [
  {
    id: 1,
    email: 'mother@example.com',
    name: '김마미',
    phoneNumber: '010-1000-2000',
    status: 'ACTIVE',
    pregnancyProfile: {
      status: 'PREGNANT',
      expectedBirthDate: '2026-12-01',
      pregnancyWeek: 13
    }
  }
];

export const demoProducts: Product[] = [
  { id: 1, name: '케어 바디로션', category: 'BODY_CARE', price: 32000, stockQuantity: 30, status: 'ON_SALE' },
  { id: 2, name: '케어 샴푸', category: 'HAIR_CARE', price: 28000, stockQuantity: 25, status: 'ON_SALE' },
  { id: 3, name: '케어 어메니티 세트', category: 'GIFT_SET', price: 54000, stockQuantity: 12, status: 'ON_SALE' }
];

export const demoConsultations: SupportTicket[] = [
  {
    id: 1,
    memberId: 1,
    title: '출산 후 제품 사용 상담',
    body: '출산 직후에도 사용할 수 있는지 문의드립니다.',
    status: 'REQUESTED'
  }
];

export const demoInquiries: SupportTicket[] = [
  {
    id: 1,
    memberId: 1,
    title: '향 문의',
    body: '향이 강한 편인지 궁금합니다.',
    status: 'RECEIVED',
    productName: '케어 샴푸'
  }
];

export const demoMarketingMembers: MarketingMember[] = [
  { memberId: 1, email: 'mother@example.com', name: '김마미', phoneNumber: '010-1000-2000' },
  { memberId: 2, email: 'marketing@example.com', name: '이고객', phoneNumber: '010-3000-4000' }
];

