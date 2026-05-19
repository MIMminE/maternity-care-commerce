insert into admin_users (email, name, password_hash, role, active, created_at, updated_at)
values
    ('admin@example.com', '운영 관리자', '{noop}password123!', 'ADMIN', true, current_timestamp, current_timestamp)
on conflict (email) do nothing;

insert into members (email, name, phone_number, status, password_hash, created_at, updated_at)
values
    ('mother@example.com', '김마미', '010-1000-2000', 'ACTIVE', '{noop}password123!', current_timestamp, current_timestamp),
    ('marketing@example.com', '이산모', '010-3000-4000', 'ACTIVE', '{noop}password123!', current_timestamp, current_timestamp)
on conflict (email) do nothing;

insert into pregnancy_profiles (member_id, status, expected_birth_date, child_birth_date, pregnancy_week, created_at, updated_at)
select id, 'PREGNANT', date '2026-12-01', null, 13, current_timestamp, current_timestamp
from members
where email = 'mother@example.com'
on conflict (member_id) do nothing;

insert into consent_histories (member_id, consent_type, agreed, agreed_at, created_at, updated_at)
select id, consent_type, agreed, current_timestamp, current_timestamp, current_timestamp
from members
cross join (
    values
        ('TERMS', true),
        ('PRIVACY', true),
        ('SENSITIVE_INFORMATION', true),
        ('MARKETING', true)
) as consent(consent_type, agreed)
where email in ('mother@example.com', 'marketing@example.com');

insert into products (name, category, price, stock_quantity, status, created_at, updated_at)
values
    ('산모애 바디로션', 'BODY_CARE', 32000.00, 30, 'ON_SALE', current_timestamp, current_timestamp),
    ('산모애 샴푸', 'HAIR_CARE', 28000.00, 25, 'ON_SALE', current_timestamp, current_timestamp),
    ('산모애 어메니티 세트', 'GIFT_SET', 54000.00, 12, 'ON_SALE', current_timestamp, current_timestamp)
on conflict do nothing;

insert into consultations (member_id, title, body, status, internal_memo, created_at, updated_at)
select id, '출산 후 제품 사용 상담', '출산 직후에도 사용할 수 있는지 문의드립니다.', 'REQUESTED', null, current_timestamp, current_timestamp
from members
where email = 'mother@example.com';

insert into inquiries (member_id, product_id, title, body, status, created_at, updated_at)
select m.id, p.id, '향 문의', '향이 강한 편인지 궁금합니다.', 'RECEIVED', current_timestamp, current_timestamp
from members m
join products p on p.name = '산모애 샴푸'
where m.email = 'mother@example.com';

