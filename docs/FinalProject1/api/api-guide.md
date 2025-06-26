# 📘 API 이용 가이드

본 문서는 Android 부트캠프를 위한 통합 API 서버
관리자 및 사용자 API 사용 방법을 안내합니다.

---

## 🔐 관리자 API

### 1. 🛍️ 상품 관리
- **목록 조회**: `GET /api/admin/products`
- **상품 등록**: `POST /api/admin/products`
- **상품 수정**: `PUT /api/admin/products/:id`
- **상품 삭제**: `DELETE /api/admin/products/:id`

### 2. 📢 공지사항 관리
- **공지 목록 조회**: `GET /api/admin/notices`
- **공지 등록**: `POST /api/admin/notices`
- **공지 수정**: `PUT /api/admin/notices/:id`
- **공지 삭제**: `DELETE /api/admin/notices/:id`

### 3. 📦 주문 관리
- **주문 목록 조회**: `GET /api/admin/orders`
- **주문 상태 변경**: `PATCH /api/admin/orders/:id/status`
- **주문 상세 조회**: `GET /api/admin/orders/:id`

### 4. 🎉 이벤트 관리
- **이벤트 목록 조회**: `GET /api/admin/events`
- **이벤트 등록**: `POST /api/admin/events`
- **이벤트 수정**: `PUT /api/admin/events/:id`
- **이벤트 삭제**: `DELETE /api/admin/events/:id`

### 1. 🛍️ 상품 관리
- **목록 조회**: `GET /api/admin/products`
- **상품 등록**: `POST /api/admin/products`
- **상품 수정**: `PUT /api/admin/products/:id`
- **상품 삭제**: `DELETE /api/admin/products/:id`

---

## 👤 사용자 API

### 1. 🛍️ 상품 조회
- **상품 목록 조회**: `GET /api/products`
- **상품 상세 조회**: `GET /api/products/:id`

### 2. 📢 공지사항 조회
- **공지 목록 조회**: `GET /api/notices`
- **공지 상세 조회**: `GET /api/notices/:id`

### 3. 📦 주문
- **주문 생성**: `POST /api/orders`
- **주문 목록 조회**: `GET /api/orders`
- **주문 상세 조회**: `GET /api/orders/:id`
- **주문 취소**: `PATCH /api/orders/:id/cancel`

### 4. 🎉 이벤트
- **이벤트 목록 조회**: `GET /api/events`
- **이벤트 상세 조회**: `GET /api/events/:id`

---

## 📎 공통 요청 헤더
- `Authorization`: `Bearer <AccessToken>`
- `X-Company-Code`: `<회사 코드>`

---

## 📌 참고사항
- 모든 요청은 `application/json` 형식을 사용합니다.
- 날짜 형식은 ISO 8601 (`YYYY-MM-DDTHH:mm:ssZ`) 형식을 따릅니다.
- 관리자 API는 인증된 관리자 권한이 필요합니다.

