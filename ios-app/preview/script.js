const products = [
  { id: 1, name: "케어 바디로션", category: "BODY_CARE", categoryLabel: "바디", price: 32000, stock: 30 },
  { id: 2, name: "케어 샴푸", category: "HAIR_CARE", categoryLabel: "헤어", price: 28000, stock: 25 },
  { id: 3, name: "케어 어메니티 세트", category: "GIFT_SET", categoryLabel: "선물", price: 54000, stock: 12 },
  { id: 4, name: "케어 홈 릴렉스 키트", category: "GIFT_SET", categoryLabel: "선물", price: 68000, stock: 8 }
];

const orders = [
  { orderNumber: "ORD-20260519-001", status: "배송 준비", total: 86000, date: "2026-05-19" }
];

const consultations = [
  { title: "배송 일정 변경 문의", status: "답변 대기", date: "2026-05-19" },
  { title: "상품 구성 문의", status: "답변 완료", date: "2026-05-16" }
];

let cart = [];
let filter = "ALL";

if (new URLSearchParams(window.location.search).get("capture") === "phone") {
  document.body.classList.add("capture-phone");
}

const won = (value) => `${value.toLocaleString()}원`;

function setNotice(message) {
  document.querySelector("#notice").textContent = message;
}

function renderProducts() {
  const list = document.querySelector("#product-list");
  const filtered = filter === "ALL" ? products : products.filter((product) => product.category === filter);
  list.innerHTML = filtered
    .map(
      (product) => `
        <article class="product-card">
          <div>
            <strong>${product.name}</strong>
            <small>${product.categoryLabel} · 재고 ${product.stock}개 · 판매 중</small>
            <strong>${won(product.price)}</strong>
          </div>
          <button data-add="${product.id}">담기</button>
        </article>
      `
    )
    .join("");
}

function renderCart() {
  document.querySelectorAll("[data-cart-count]").forEach((node) => {
    node.textContent = cart.reduce((sum, item) => sum + item.quantity, 0);
  });

  const total = cart.reduce((sum, item) => sum + item.product.price * item.quantity, 0);
  document.querySelector("[data-cart-total]").textContent = won(total);
  document.querySelector("#cart-subtitle").textContent = `${cart.length}개 상품`;

  const list = document.querySelector("#cart-list");
  if (cart.length === 0) {
    list.innerHTML = `<p class="empty">상품을 담으면 이곳에서 수량과 금액을 확인할 수 있습니다.</p>`;
  } else {
    list.innerHTML = cart
      .map(
        (item) => `
          <div class="cart-line">
            <span>${item.product.name}<small>${item.quantity}개</small></span>
            <strong>${won(item.product.price * item.quantity)}</strong>
          </div>
        `
      )
      .join("");
  }
}

function renderOrders() {
  document.querySelector("#order-list").innerHTML = orders
    .map(
      (order) => `
        <div class="status-line">
          <span>${order.orderNumber}<small>${order.date}</small></span>
          <strong>${order.status}</strong>
        </div>
      `
    )
    .join("");
}

function renderConsultations() {
  document.querySelector("#support-list").innerHTML = consultations
    .map(
      (consultation) => `
        <div class="status-line">
          <span>${consultation.title}<small>${consultation.date}</small></span>
          <strong>${consultation.status}</strong>
        </div>
      `
    )
    .join("");
}

function addToCart(id) {
  const product = products.find((item) => item.id === Number(id));
  if (!product) return;

  const existing = cart.find((item) => item.product.id === product.id);
  if (existing) {
    existing.quantity += 1;
  } else {
    cart.push({ product, quantity: 1 });
  }
  setNotice(`${product.name}을 장바구니에 담았습니다.`);
  renderCart();
}

document.addEventListener("click", (event) => {
  const addButton = event.target.closest("[data-add]");
  if (addButton) {
    addToCart(addButton.dataset.add);
    return;
  }

  const tabButton = event.target.closest("[data-tab]");
  if (tabButton) {
    document.querySelectorAll("[data-tab]").forEach((button) => button.classList.remove("active"));
    tabButton.classList.add("active");
    document.querySelectorAll("[data-screen]").forEach((screen) => screen.classList.add("hidden"));
    document.querySelector(`[data-screen="${tabButton.dataset.tab}"]`).classList.remove("hidden");
    return;
  }

  const filterButton = event.target.closest("[data-filter]");
  if (filterButton) {
    filter = filterButton.dataset.filter;
    document.querySelectorAll("[data-filter]").forEach((button) => button.classList.remove("active"));
    filterButton.classList.add("active");
    renderProducts();
    return;
  }

  if (event.target.closest("#create-order")) {
    if (cart.length === 0) {
      setNotice("상품을 먼저 담으면 주문 생성 흐름을 확인할 수 있습니다.");
      return;
    }
    orders.unshift({
      orderNumber: `IOS-20260519-${orders.length + 1}`,
      status: "주문 생성",
      total: cart.reduce((sum, item) => sum + item.product.price * item.quantity, 0),
      date: "2026-05-19"
    });
    cart = [];
    setNotice("iOS 데모 주문이 생성되었습니다.");
    renderCart();
    renderOrders();
    return;
  }

  if (event.target.closest("[data-save-profile]")) {
    setNotice("iOS 데모 프로필을 저장했습니다.");
    return;
  }

  if (event.target.closest("#create-support")) {
    const title = document.querySelector("#support-title").value || "상담 문의";
    consultations.unshift({ title, status: "접수", date: "2026-05-19" });
    document.querySelector("#support-title").value = "";
    document.querySelector("#support-body").value = "";
    setNotice("iOS 데모 상담이 접수되었습니다.");
    renderConsultations();
  }
});

renderProducts();
renderCart();
renderOrders();
renderConsultations();
