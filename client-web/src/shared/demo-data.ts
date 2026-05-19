import type { PregnancyProfile, Product } from './types';

export const demoProducts: Product[] = [
  { id: 1, name: '산모애 바디로션', category: 'BODY_CARE', price: 32000, stockQuantity: 30, status: 'ON_SALE' },
  { id: 2, name: '산모애 샴푸', category: 'HAIR_CARE', price: 28000, stockQuantity: 25, status: 'ON_SALE' },
  { id: 3, name: '산모애 어메니티 세트', category: 'GIFT_SET', price: 54000, stockQuantity: 12, status: 'ON_SALE' }
];

export const demoProfile: PregnancyProfile = {
  status: 'PREGNANT',
  expectedBirthDate: '2026-12-01',
  pregnancyWeek: 13
};

