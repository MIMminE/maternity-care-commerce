export type Tab = 'home' | 'profile' | 'shop' | 'cart' | 'support';

export type Product = {
  id: number;
  name: string;
  category: string;
  price: number;
  stockQuantity: number;
  status: string;
};

export type CartItem = {
  cartItemId: number;
  productId: number;
  productName: string;
  unitPrice: number;
  quantity: number;
  lineAmount: number;
};

export type PregnancyProfile = {
  status: string;
  expectedBirthDate?: string;
  childBirthDate?: string;
  pregnancyWeek?: number;
};

export type Order = {
  id: number;
  orderNumber: string;
  status: string;
  totalAmount: number;
};

