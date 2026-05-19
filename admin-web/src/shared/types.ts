export type View = 'dashboard' | 'members' | 'products' | 'support' | 'marketing';

export type Dashboard = {
  totalMembers: number;
  marketingAgreedMembers: number;
  totalOrders: number;
  totalOrderAmount: number;
  requestedConsultations: number;
  receivedInquiries: number;
};

export type Member = {
  id: number;
  email: string;
  name: string;
  phoneNumber: string | null;
  status: string;
  pregnancyProfile?: {
    status: string;
    expectedBirthDate?: string;
    childBirthDate?: string;
    pregnancyWeek?: number;
  } | null;
};

export type Product = {
  id: number;
  name: string;
  category: string;
  price: number;
  stockQuantity: number;
  status: string;
};

export type SupportTicket = {
  id: number;
  memberId: number;
  title: string;
  body: string;
  status: string;
  productName?: string | null;
};

export type MarketingMember = {
  memberId: number;
  email: string;
  name: string;
  phoneNumber: string | null;
};

