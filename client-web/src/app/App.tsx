import { FormEvent, useMemo, useState } from 'react';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080';

type Tab = 'home' | 'profile' | 'shop' | 'cart' | 'support';

type Product = {
  id: number;
  name: string;
  category: string;
  price: number;
  stockQuantity: number;
  status: string;
};

type CartItem = {
  cartItemId: number;
  productId: number;
  productName: string;
  unitPrice: number;
  quantity: number;
  lineAmount: number;
};

type PregnancyProfile = {
  status: string;
  expectedBirthDate?: string;
  childBirthDate?: string;
  pregnancyWeek?: number;
};

type Order = {
  id: number;
  orderNumber: string;
  status: string;
  totalAmount: number;
};

const demoProducts: Product[] = [
  { id: 1, name: '산모애 바디로션', category: 'BODY_CARE', price: 32000, stockQuantity: 30, status: 'ON_SALE' },
  { id: 2, name: '산모애 샴푸', category: 'HAIR_CARE', price: 28000, stockQuantity: 25, status: 'ON_SALE' },
  { id: 3, name: '산모애 어메니티 세트', category: 'GIFT_SET', price: 54000, stockQuantity: 12, status: 'ON_SALE' }
];

const demoProfile: PregnancyProfile = {
  status: 'PREGNANT',
  expectedBirthDate: '2026-12-01',
  pregnancyWeek: 13
};

export function App() {
  const [tab, setTab] = useState<Tab>('home');
  const [token, setToken] = useState('');
  const [email, setEmail] = useState('mother@example.com');
  const [password, setPassword] = useState('password123!');
  const [profile, setProfile] = useState<PregnancyProfile | null>(demoProfile);
  const [products, setProducts] = useState<Product[]>(demoProducts);
  const [cart, setCart] = useState<CartItem[]>([]);
  const [orders, setOrders] = useState<Order[]>([]);
  const [notice, setNotice] = useState('데모 데이터가 표시 중입니다. 로그인 후 실제 API 흐름을 확인할 수 있습니다.');

  const authHeaders = useMemo(
    () => ({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json'
    }),
    [token]
  );

  async function request<T>(path: string, options: RequestInit = {}): Promise<T> {
    const response = await fetch(`${API_BASE_URL}${path}`, {
      ...options,
      headers: {
        ...(token ? authHeaders : { 'Content-Type': 'application/json' }),
        ...(options.headers ?? {})
      }
    });
    if (!response.ok) {
      throw new Error(`API 요청 실패: ${response.status}`);
    }
    return response.json() as Promise<T>;
  }

  async function signup() {
    try {
      const data = await request<{ accessToken: string }>('/client-api/v1/auth/signup', {
        method: 'POST',
        body: JSON.stringify({
          email,
          password,
          name: '테스트 산모',
          phoneNumber: '010-0000-0000',
          termsAgreed: true,
          privacyAgreed: true,
          sensitiveInformationAgreed: true,
          marketingAgreed: true
        })
      });
      setToken(data.accessToken);
      setNotice('회원가입과 동의 이력 저장이 완료되었습니다.');
    } catch {
      setNotice('회원가입에 실패했습니다. 이미 가입된 이메일이면 로그인해 주세요.');
    }
  }

  async function login(event?: FormEvent) {
    event?.preventDefault();
    try {
      const data = await request<{ accessToken: string }>('/client-api/v1/auth/login', {
        method: 'POST',
        body: JSON.stringify({ email, password })
      });
      setToken(data.accessToken);
      setNotice('로그인되었습니다.');
    } catch {
      setNotice('로그인에 실패했습니다.');
    }
  }

  async function saveProfile(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const form = new FormData(event.currentTarget);
    try {
      const data = await request<PregnancyProfile>('/client-api/v1/pregnancy-profile/me', {
        method: 'PUT',
        body: JSON.stringify({
          status: form.get('status'),
          expectedBirthDate: form.get('expectedBirthDate') || null,
          childBirthDate: form.get('childBirthDate') || null,
          pregnancyWeek: Number(form.get('pregnancyWeek')) || null
        })
      });
      setProfile(data);
      setNotice('산모 프로필이 저장되었습니다.');
    } catch {
      setNotice('산모 프로필 저장에 실패했습니다.');
    }
  }

  async function loadProducts() {
    try {
      const data = await request<Product[]>('/client-api/v1/products');
      setProducts(data);
      setNotice('판매 중인 상품을 불러왔습니다.');
    } catch {
      setNotice('상품 조회에 실패했습니다.');
    }
  }

  async function addToCart(productId: number) {
    try {
      await request<CartItem>('/client-api/v1/cart', {
        method: 'POST',
        body: JSON.stringify({ productId, quantity: 1 })
      });
      await loadCart();
      setNotice('장바구니에 담았습니다.');
    } catch {
      setNotice('장바구니 담기에 실패했습니다.');
    }
  }

  async function loadCart() {
    try {
      const data = await request<CartItem[]>('/client-api/v1/cart');
      setCart(data);
    } catch {
      setNotice('장바구니 조회에 실패했습니다.');
    }
  }

  async function createOrder() {
    try {
      await request<Order>('/client-api/v1/orders', { method: 'POST' });
      await Promise.all([loadCart(), loadOrders()]);
      setNotice('주문이 생성되었습니다.');
    } catch {
      setNotice('주문 생성에 실패했습니다.');
    }
  }

  async function loadOrders() {
    try {
      const data = await request<Order[]>('/client-api/v1/orders');
      setOrders(data);
    } catch {
      setNotice('주문 조회에 실패했습니다.');
    }
  }

  async function createConsultation(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const form = new FormData(event.currentTarget);
    try {
      await request('/client-api/v1/consultations', {
        method: 'POST',
        body: JSON.stringify({
          title: form.get('title'),
          body: form.get('body')
        })
      });
      event.currentTarget.reset();
      setNotice('상담 신청이 접수되었습니다.');
    } catch {
      setNotice('상담 신청에 실패했습니다.');
    }
  }

  const tabs: Array<[Tab, string]> = [
    ['home', '홈'],
    ['profile', '프로필'],
    ['shop', '상품'],
    ['cart', '주문'],
    ['support', '상담']
  ];

  return (
    <main className="client-shell">
      <section className="mobile-frame">
        <header className="app-header">
          <p className="eyebrow">Maternity Care</p>
          <h1>MYMAM Care</h1>
        </header>

        <form className="auth-box" onSubmit={login}>
          <input aria-label="이메일" onChange={(event) => setEmail(event.target.value)} value={email} />
          <input
            aria-label="비밀번호"
            onChange={(event) => setPassword(event.target.value)}
            type="password"
            value={password}
          />
          <div className="button-row">
            <button onClick={signup} type="button">
              가입
            </button>
            <button type="submit">로그인</button>
          </div>
        </form>

        <p className="notice">{notice}</p>

        <nav className="tabbar">
          {tabs.map(([key, label]) => (
            <button className={tab === key ? 'active' : ''} key={key} onClick={() => setTab(key)}>
              {label}
            </button>
          ))}
        </nav>

        {tab === 'home' && (
          <section className="screen">
            <h2>오늘의 케어</h2>
            <div className="summary-card">
              <span>임신 주차</span>
              <strong>{profile?.pregnancyWeek ? `${profile.pregnancyWeek}주` : '프로필 등록 전'}</strong>
            </div>
            <div className="summary-card">
              <span>최근 주문</span>
              <strong>{orders[0]?.status ?? '주문 없음'}</strong>
            </div>
          </section>
        )}

        {tab === 'profile' && (
          <form className="screen form-stack" onSubmit={saveProfile}>
            <h2>산모 프로필</h2>
            <select name="status" defaultValue="PREGNANT">
              <option value="PREGNANT">임신 중</option>
              <option value="POSTPARTUM">출산 후</option>
              <option value="PLANNING">준비 중</option>
            </select>
            <input name="expectedBirthDate" type="date" />
            <input name="childBirthDate" type="date" />
            <input min="1" max="42" name="pregnancyWeek" placeholder="임신 주차" type="number" />
            <button type="submit">저장</button>
          </form>
        )}

        {tab === 'shop' && (
          <section className="screen">
            <div className="section-header">
              <h2>산모애 상품</h2>
              <button onClick={loadProducts}>조회</button>
            </div>
            <div className="product-list">
              {products.length === 0 ? (
                <p>상품을 조회해 주세요.</p>
              ) : (
                products.map((product) => (
                  <article className="product-item" key={product.id}>
                    <div>
                      <strong>{product.name}</strong>
                      <span>{product.category}</span>
                    </div>
                    <div>
                      <b>{Number(product.price).toLocaleString()}원</b>
                      <button onClick={() => addToCart(product.id)}>담기</button>
                    </div>
                  </article>
                ))
              )}
            </div>
          </section>
        )}

        {tab === 'cart' && (
          <section className="screen">
            <div className="section-header">
              <h2>장바구니/주문</h2>
              <button onClick={() => Promise.all([loadCart(), loadOrders()])}>조회</button>
            </div>
            {cart.map((item) => (
              <div className="line-item" key={item.cartItemId}>
                <span>{item.productName}</span>
                <strong>{Number(item.lineAmount).toLocaleString()}원</strong>
              </div>
            ))}
            <button className="wide-button" onClick={createOrder}>
              주문 생성
            </button>
            <h3>주문 내역</h3>
            {orders.map((order) => (
              <div className="line-item" key={order.id}>
                <span>{order.orderNumber}</span>
                <strong>{order.status}</strong>
              </div>
            ))}
          </section>
        )}

        {tab === 'support' && (
          <form className="screen form-stack" onSubmit={createConsultation}>
            <h2>상담 신청</h2>
            <input name="title" placeholder="상담 제목" required />
            <textarea name="body" placeholder="문의 내용을 입력하세요." required rows={5} />
            <button type="submit">접수</button>
          </form>
        )}
      </section>
    </main>
  );
}
