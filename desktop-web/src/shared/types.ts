export type Product = {
  id: number;
  name: string;
  category: string;
  price: number;
  stockQuantity: number;
  status: string;
};

export type CareProfile = {
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
  createdAt: string;
};

export type Consultation = {
  id: number;
  title: string;
  status: string;
  requestedAt: string;
};
