### 🛍️ 상품 등록 API

- **URL**: `POST /api/admin/products`
- **설명**: 새로운 상품을 등록합니다.
- **인증**: 관리자 토큰 필요 (`Authorization: Bearer <token>`)

---

#### ✅ 요청 헤더

| 이름              | 타입    | 필수 | 설명                    |
|-------------------|---------|------|-------------------------|
| Authorization     | string  | ✅   | Bearer access token     |
| X-Company-Code    | string  | ✅   | 쇼핑몰 회사 고유 코드   |
| Content-Type      | string  | ✅   | `application/json`      |

---

#### 📥 요청 바디 (JSON)

```json
{
  "name": "스트라이프 반팔티",
  "description": "여름용 시원한 반팔 티셔츠입니다.",
  "price": 29000,
  "stock": 100,
  "category": "CAT123456",
  "images": {
    "thumbnail":"https://cdn.example.com/product1.jpg",
    "detail":"https://cdn.example.com/product2.jpg"
  },
  "options":{
    "item":[
      {
        "name": "색상",
        "values": ["화이트", "블랙"]      
      },
      {
        "name": "사이즈",
        "values": ["M", "L", "XL"]
      }
    ],
    "variants": [
    {
      "optionValues": ["화이트", "M"],
      "stock": 10,
      "sku": "WH-M-001"
    },
    {
      "optionValues": ["화이트", "L"],
      "stock": 15,
      "sku": "WH-L-001"
    },    
    {
      "optionValues": ["블랙", "M"],
      "stock": 20,
      "sku": "BK-M-001"
    },
    {
      "optionValues": ["블랙", "L"],
      "stock": 25,
      "sku": "BK-L-001"
    }]
  },   
  "discount": {
    "type": "percent",
    "value": 10,
    "from": "2025-06-01",
    "to": "2025-06-30"
  }
}
```

---

#### 📤 응답 바디 (성공)

```json
{
  "code": "0000",
  "message": "상품이 성공적으로 등록되었습니다.",
  "result": {
    "id": "684e986028d3ac71c6381ee6",
    "name": "스트라이프 반팔티"
  }
}
```

---

#### ⚠️ 응답 바디 (에러 예시)

```json
{
  "code": "9999",
  "message": "필수 항목 누락: name"
}
```

---

#### 📝 비고
- `images` 배열,객체 모두 사용 가능합니다.
- `discount` 필드는 생략 가능하며, 없을 경우 할인 없이 등록됩니다.
- 옵션(`options`)이 없는 단일 상품도 등록 가능하며 해당 필드는 생략 가능합니다.

---


## 🧩 상품 데이터 모델 (Product)

| 필드명             | 타입        | 설명                                 |
|------------------|------------|--------------------------------------|
| id               | string     | 상품 고유 ID                         |
| name             | string     | 상품명                               |
| description      | string     | 상품 상세 설명                       |
| price            | number     | 정가 (단위: 원)                      |
| stockType        | string     | `fixed` 또는 `variable`              |
| stock            | number     | 단일 옵션 상품의 재고 (선택)         |
| images           | Mixed      | 상품 이미지 URL 배열                  |
| options          | Mixed      | 옵션 항목 배열 (선택)                |
| variants         | Variant[]  | 조합형 옵션일 경우의 상세 목록       |
| discount         | Discount   | 할인 정보 (선택)                     |
| status           | enum       | 판매 상태 (`on_sale`, `sold_out`, `hidden`) |
| favoriteCount    | number     | 찜 수                                     |
| viewCount        | number     | 조회 수                                   |
| orderCount       | number     | 주문 횟수                                 |
| cartCount        | number     | 장바구니 담긴 횟수                         |

> `attributes`, `options`, `images`, `discount`는 `Schema.Types.Mixed`로 정의되어 있어 자유로운 구조를 허용합니다.

---

### 🧩 Option

| 필드명  | 타입      | 설명                       |
|---------|-----------|----------------------------|
| name    | string    | 옵션 이름 (예: 색상, 사이즈) |
| values  | string[]  | 가능한 옵션 값              |

---

### 🧩 Variant

| 필드명       | 타입      | 설명                                |
|--------------|-----------|-------------------------------------|
| optionValues | string[]  | 조합된 옵션 값들 (예: ["블랙", "M"]) |
| stock        | number    | 해당 조합의 재고 수량               |
| sku          | string    | 해당 조합의 고유 SKU                |

---

### 🧩 Discount

| 필드명 | 타입     | 설명                         |
|--------|----------|------------------------------|
| type   | string   | 할인 타입 (`percent` or `fixed`) |
| value  | number   | 할인값 (10이면 10% 또는 1000원) |
| from   | string   | 할인 시작일 (`YYYY-MM-DD`)    |
| to     | string   | 할인 종료일 (`YYYY-MM-DD`)    |