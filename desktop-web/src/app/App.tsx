import { useMemo, useState } from 'react';
import { demoConsultations, demoOrders, demoProducts, demoProfile } from '../shared/demo-data';
import type { Product } from '../shared/types';

const categoryLabels: Record<string, string> = {
  BODY_CARE: '바디 케어',
  HAIR_CARE: '헤어 케어',
  GIFT_SET: '선물 세트'
};

const profileStatusLabels: Record<string, string> = {
  PREGNANT: '임신 중',
  POSTPARTUM: '출산 후',
  PLANNING: '준비 중'
};

export function App() {
  const [selectedCategory, setSelectedCategory] = useState('ALL');
  const [cart, setCart] = useState<Product[]>([]);
  const [notice, setNotice] = useState('데스크톱 웹에서는 상품 탐색, 주문 현황, 상담 상태를 한 화면에서 확인합니다.');

  const filteredProducts = useMemo(() => {
    if (selectedCategory === 'ALL') {
      return demoProducts;
    }
    return demoProducts.filter((product) => product.category === selectedCategory);
  }, [selectedCategory]);

  const cartTotal = cart.reduce((sum, product) => sum + product.price, 0);
  const categories = ['ALL', ...Array.from(new Set(demoProducts.map((product) => product.category)))];

  function addToCart(product: Product) {
    setCart((current) => [...current, product]);
    setNotice(`${product.name}을 장바구니에 담았습니다.`);
  }

  function createOrder() {
    if (cart.length === 0) {
      setNotice('상품을 먼저 담으면 주문 생성 흐름을 확인할 수 있습니다.');
      return;
    }
    setCart([]);
    setNotice('데모 주문이 생성되었습니다. 실제 서비스에서는 결제/배송 단계로 이어집니다.');
  }

  return (
    <main className="desktop-shell">
      <nav className="top-nav">
        <div className="brand">
          <strong>Care Commerce</strong>
          <span>Customer Web</span>
        </div>
        <div className="nav-links">
          <a href="#products">상품</a>
          <a href="#orders">주문</a>
          <a href="#support">상담</a>
        </div>
        <button className="nav-action">내 정보</button>
      </nav>

      <section className="overview">
        <div className="overview-copy">
          <span>오늘의 케어</span>
          <h1>상품, 주문, 상담을 한 화면에서 관리합니다.</h1>
        </div>

        <div className="overview-card profile-summary">
          <span>케어 프로필</span>
          <strong>{profileStatusLabels[demoProfile.status]}</strong>
          <p>{demoProfile.pregnancyWeek}주 · 예정일 {demoProfile.expectedBirthDate}</p>
        </div>

        <div className="overview-card">
          <span>진행 주문</span>
          <strong>{demoOrders[0].status}</strong>
          <p>{demoOrders[0].orderNumber}</p>
        </div>

        <div className="overview-card">
          <span>상담</span>
          <strong>{demoConsultations[0].status}</strong>
          <p>{demoConsultations[0].title}</p>
        </div>
      </section>

      <p className="notice">{notice}</p>

      <section className="workspace">
        <aside className="side-column">
          <section className="side-card">
            <div className="section-heading compact">
              <div>
                <span>Cart</span>
                <h2>장바구니</h2>
              </div>
              <strong>{cart.length}개</strong>
            </div>
            {cart.length === 0 ? (
              <p className="empty-text">상품을 담으면 주문 예정 금액이 표시됩니다.</p>
            ) : (
              <div className="cart-list">
                {cart.map((product, index) => (
                  <div className="line-item" key={`${product.id}-${index}`}>
                    <span>{product.name}</span>
                    <strong>{Number(product.price).toLocaleString()}원</strong>
                  </div>
                ))}
              </div>
            )}
            <div className="total-row">
              <span>합계</span>
              <strong>{Number(cartTotal).toLocaleString()}원</strong>
            </div>
            <button className="wide-button" onClick={createOrder}>
              주문 생성
            </button>
          </section>

          <section className="side-card" id="support">
            <div className="section-heading compact">
              <div>
                <span>Support</span>
                <h2>상담 상태</h2>
              </div>
            </div>
            {demoConsultations.map((consultation) => (
              <div className="line-item" key={consultation.id}>
                <span>
                  {consultation.title}
                  <small>{consultation.requestedAt}</small>
                </span>
                <strong>{consultation.status}</strong>
              </div>
            ))}
          </section>
        </aside>

        <section className="main-column">
          <section className="products-section" id="products">
            <div className="section-heading">
              <div>
                <span>Shop</span>
                <h2>케어 상품</h2>
              </div>
              <div className="category-tabs">
                {categories.map((category) => (
                  <button
                    className={selectedCategory === category ? 'active' : ''}
                    key={category}
                    onClick={() => setSelectedCategory(category)}
                  >
                    {category === 'ALL' ? '전체' : categoryLabels[category] ?? category}
                  </button>
                ))}
              </div>
            </div>

            <div className="product-grid">
              {filteredProducts.map((product) => (
                <article className="product-card" key={product.id}>
                  <div className="product-visual">{categoryLabels[product.category]?.slice(0, 2) ?? '상품'}</div>
                  <div className="product-body">
                    <span>{categoryLabels[product.category] ?? product.category}</span>
                    <h3>{product.name}</h3>
                    <p>재고 {product.stockQuantity}개 · {product.status === 'ON_SALE' ? '판매 중' : product.status}</p>
                  </div>
                  <div className="product-footer">
                    <strong>{Number(product.price).toLocaleString()}원</strong>
                    <button onClick={() => addToCart(product)}>담기</button>
                  </div>
                </article>
              ))}
            </div>
          </section>

          <section className="orders-section" id="orders">
            <div className="section-heading">
              <div>
                <span>Orders</span>
                <h2>최근 주문 내역</h2>
              </div>
            </div>
            <div className="orders-table">
              <div className="table-row table-head">
                <span>주문번호</span>
                <span>주문일</span>
                <span>상태</span>
                <span>금액</span>
              </div>
              {demoOrders.map((order) => (
                <div className="table-row" key={order.id}>
                  <span>{order.orderNumber}</span>
                  <span>{order.createdAt}</span>
                  <strong>{order.status}</strong>
                  <strong>{Number(order.totalAmount).toLocaleString()}원</strong>
                </div>
              ))}
            </div>
          </section>
        </section>
      </section>
    </main>
  );
}
