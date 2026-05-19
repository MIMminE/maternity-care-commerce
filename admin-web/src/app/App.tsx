import { FormEvent, useState } from 'react';
import type { ReactNode } from 'react';
import { apiRequest } from '../shared/api';
import {
  demoConsultations,
  demoInquiries,
  demoMarketingMembers,
  demoMembers,
  demoProducts,
  emptyDashboard
} from '../shared/demo-data';
import type { Dashboard, MarketingMember, Member, Product, SupportTicket, View } from '../shared/types';

export function App() {
  const [view, setView] = useState<View>('dashboard');
  const [token, setToken] = useState('');
  const [email, setEmail] = useState('admin@example.com');
  const [password, setPassword] = useState('password123!');
  const [dashboard, setDashboard] = useState<Dashboard>(emptyDashboard);
  const [members, setMembers] = useState<Member[]>(demoMembers);
  const [selectedMember, setSelectedMember] = useState<Member | null>(demoMembers[0]);
  const [products, setProducts] = useState<Product[]>(demoProducts);
  const [consultations, setConsultations] = useState<SupportTicket[]>(demoConsultations);
  const [inquiries, setInquiries] = useState<SupportTicket[]>(demoInquiries);
  const [marketingMembers, setMarketingMembers] = useState<MarketingMember[]>(demoMarketingMembers);
  const [notice, setNotice] = useState('데모 데이터가 표시 중입니다. 백엔드 로그인 후 실제 데이터로 갱신할 수 있습니다.');

  async function login(event: FormEvent) {
    event.preventDefault();
    try {
      const data = await apiRequest<{ accessToken: string }>('/admin-api/v1/auth/login', token, {
        method: 'POST',
        body: JSON.stringify({ email, password })
      });
      setToken(data.accessToken);
      setNotice('관리자 인증이 완료되었습니다.');
    } catch (error) {
      setNotice('관리자 로그인에 실패했습니다. 테스트용 관리자 계정이 필요합니다.');
    }
  }

  async function loadDashboard() {
    try {
      const data = await apiRequest<Dashboard>('/admin-api/v1/statistics/dashboard', token);
      setDashboard(data);
      setNotice('운영 대시보드 통계를 불러왔습니다.');
    } catch {
      setNotice('대시보드 조회에 실패했습니다.');
    }
  }

  async function loadMembers() {
    try {
      const data = await apiRequest<Member[]>('/admin-api/v1/members', token);
      setMembers(data);
      setNotice('회원 목록을 불러왔습니다.');
    } catch {
      setNotice('회원 목록 조회에 실패했습니다.');
    }
  }

  async function loadMemberDetail(memberId: number) {
    try {
      const data = await apiRequest<Member>(`/admin-api/v1/members/${memberId}`, token, {
        headers: { 'X-Audit-Reason': '관리자 화면 회원 상세 확인' }
      });
      setSelectedMember(data);
      setNotice('회원 상세를 조회했고 감사 로그가 저장됩니다.');
    } catch {
      setNotice('회원 상세 조회에 실패했습니다.');
    }
  }

  async function loadProducts() {
    try {
      const data = await apiRequest<Product[]>('/admin-api/v1/products', token);
      setProducts(data);
      setNotice('상품 목록을 불러왔습니다.');
    } catch {
      setNotice('상품 목록 조회에 실패했습니다.');
    }
  }

  async function createProduct(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const form = new FormData(event.currentTarget);
    try {
      await apiRequest<Product>('/admin-api/v1/products', token, {
        method: 'POST',
        body: JSON.stringify({
          name: form.get('name'),
          category: form.get('category'),
          price: Number(form.get('price')),
          stockQuantity: Number(form.get('stockQuantity')),
          status: form.get('status')
        })
      });
      event.currentTarget.reset();
      await loadProducts();
      setNotice('상품을 등록했습니다.');
    } catch {
      setNotice('상품 등록에 실패했습니다.');
    }
  }

  async function loadSupport() {
    try {
      const [consultationData, inquiryData] = await Promise.all([
        apiRequest<SupportTicket[]>('/admin-api/v1/consultations', token),
        apiRequest<SupportTicket[]>('/admin-api/v1/inquiries', token)
      ]);
      setConsultations(consultationData);
      setInquiries(inquiryData);
      setNotice('상담과 문의 목록을 불러왔습니다.');
    } catch {
      setNotice('상담/문의 조회에 실패했습니다.');
    }
  }

  async function loadMarketingMembers() {
    try {
      const data = await apiRequest<MarketingMember[]>('/admin-api/v1/marketing/members', token, {
        headers: { 'X-Audit-Reason': '관리자 화면 마케팅 대상 확인' }
      });
      setMarketingMembers(data);
      setNotice('마케팅 동의 고객을 조회했고 감사 로그가 저장됩니다.');
    } catch {
      setNotice('마케팅 동의 고객 조회에 실패했습니다.');
    }
  }

  const navItems: Array<[View, string]> = [
    ['dashboard', '대시보드'],
    ['members', '회원'],
    ['products', '상품'],
    ['support', 'CS'],
    ['marketing', '마케팅']
  ];

  return (
    <main className="admin-shell">
      <aside className="sidebar">
        <strong>Maternity Admin</strong>
        <nav>
          {navItems.map(([key, label]) => (
            <button className={view === key ? 'active' : ''} key={key} onClick={() => setView(key)}>
              {label}
            </button>
          ))}
        </nav>
      </aside>

      <section className="workspace">
        <header className="topbar">
          <div>
            <p className="eyebrow">Operations</p>
            <h1>관리자 백오피스</h1>
          </div>
          <form className="login-form" onSubmit={login}>
            <input aria-label="관리자 이메일" onChange={(event) => setEmail(event.target.value)} value={email} />
            <input
              aria-label="관리자 비밀번호"
              onChange={(event) => setPassword(event.target.value)}
              type="password"
              value={password}
            />
            <button type="submit">로그인</button>
          </form>
        </header>

        <p className="notice">{notice}</p>

        {view === 'dashboard' && (
          <section className="panel">
            <div className="section-header">
              <h2>운영 대시보드</h2>
              <button onClick={loadDashboard}>새로고침</button>
            </div>
            <div className="metric-grid">
              <Metric label="총 회원" value={dashboard.totalMembers} />
              <Metric label="마케팅 동의" value={dashboard.marketingAgreedMembers} />
              <Metric label="총 주문" value={dashboard.totalOrders} />
              <Metric label="누적 주문금액" value={`${Number(dashboard.totalOrderAmount).toLocaleString()}원`} />
              <Metric label="대기 상담" value={dashboard.requestedConsultations} />
              <Metric label="대기 문의" value={dashboard.receivedInquiries} />
            </div>
          </section>
        )}

        {view === 'members' && (
          <section className="two-column">
            <div className="panel">
              <div className="section-header">
                <h2>회원 목록</h2>
                <button onClick={loadMembers}>조회</button>
              </div>
              <DataTable
                headers={['이메일', '이름', '상태']}
                rows={members.map((member) => [
                  member.email,
                  member.name,
                  member.status,
                  <button key={member.id} onClick={() => loadMemberDetail(member.id)}>
                    상세
                  </button>
                ])}
              />
            </div>
            <div className="panel detail-panel">
              <h2>회원 상세</h2>
              {selectedMember ? (
                <dl>
                  <dt>이메일</dt>
                  <dd>{selectedMember.email}</dd>
                  <dt>연락처</dt>
                  <dd>{selectedMember.phoneNumber ?? '-'}</dd>
                  <dt>산모 상태</dt>
                  <dd>{selectedMember.pregnancyProfile?.status ?? '-'}</dd>
                  <dt>임신 주차</dt>
                  <dd>{selectedMember.pregnancyProfile?.pregnancyWeek ?? '-'}</dd>
                </dl>
              ) : (
                <p>회원을 선택하면 산모 프로필과 함께 표시됩니다.</p>
              )}
            </div>
          </section>
        )}

        {view === 'products' && (
          <section className="two-column">
            <form className="panel form-grid" onSubmit={createProduct}>
              <h2>상품 등록</h2>
              <input name="name" placeholder="상품명" required />
              <input name="category" placeholder="카테고리" required />
              <input min="0" name="price" placeholder="가격" required type="number" />
              <input min="0" name="stockQuantity" placeholder="재고" required type="number" />
              <select name="status" defaultValue="ON_SALE">
                <option value="ON_SALE">판매중</option>
                <option value="DRAFT">초안</option>
                <option value="SOLD_OUT">품절</option>
                <option value="HIDDEN">숨김</option>
              </select>
              <button type="submit">등록</button>
            </form>
            <div className="panel">
              <div className="section-header">
                <h2>상품 목록</h2>
                <button onClick={loadProducts}>조회</button>
              </div>
              <DataTable
                headers={['상품', '가격', '재고', '상태']}
                rows={products.map((product) => [
                  product.name,
                  `${Number(product.price).toLocaleString()}원`,
                  product.stockQuantity,
                  product.status
                ])}
              />
            </div>
          </section>
        )}

        {view === 'support' && (
          <section className="two-column">
            <SupportPanel title="상담" tickets={consultations} onRefresh={loadSupport} />
            <SupportPanel title="상품 문의" tickets={inquiries} onRefresh={loadSupport} />
          </section>
        )}

        {view === 'marketing' && (
          <section className="panel">
            <div className="section-header">
              <h2>마케팅 동의 고객</h2>
              <button onClick={loadMarketingMembers}>조회</button>
            </div>
            <DataTable
              headers={['이메일', '이름', '연락처']}
              rows={marketingMembers.map((member) => [member.email, member.name, member.phoneNumber ?? '-'])}
            />
          </section>
        )}
      </section>
    </main>
  );
}

function Metric({ label, value }: { label: string; value: number | string }) {
  return (
    <div className="metric">
      <span>{label}</span>
      <strong>{value}</strong>
    </div>
  );
}

function DataTable({ headers, rows }: { headers: string[]; rows: Array<Array<ReactNode>> }) {
  return (
    <table>
      <thead>
        <tr>
          {headers.map((header) => (
            <th key={header}>{header}</th>
          ))}
          {rows.some((row) => row.length > headers.length) && <th>작업</th>}
        </tr>
      </thead>
      <tbody>
        {rows.length === 0 ? (
          <tr>
            <td colSpan={headers.length + 1}>표시할 데이터가 없습니다.</td>
          </tr>
        ) : (
          rows.map((row, index) => (
            <tr key={index}>
              {row.map((cell, cellIndex) => (
                <td key={cellIndex}>{cell}</td>
              ))}
            </tr>
          ))
        )}
      </tbody>
    </table>
  );
}

function SupportPanel({
  title,
  tickets,
  onRefresh
}: {
  title: string;
  tickets: SupportTicket[];
  onRefresh: () => void;
}) {
  return (
    <div className="panel">
      <div className="section-header">
        <h2>{title}</h2>
        <button onClick={onRefresh}>조회</button>
      </div>
      <DataTable
        headers={['제목', '상태', '상품']}
        rows={tickets.map((ticket) => [ticket.title, ticket.status, ticket.productName ?? '-'])}
      />
    </div>
  );
}
