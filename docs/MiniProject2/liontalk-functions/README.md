# LionTalk Firebase Functions

이 프로젝트는 **카카오 / 네이버 소셜 로그인을 Firebase Auth 커스텀 토큰으로 연동하기 위한 Cloud Functions** 코드입니다.

## 폴더 구조

```
functions/
├─ index.js                 // 엔트리 포인트 (admin.initializeApp() 및 exports)
├─ kakao-auth.js            // Kakao 커스텀 토큰 발급 함수
├─ naver-auth.js            // Naver 커스텀 토큰 발급 함수
└─ utils/
   └─ social-mapper.js      // Kakao/Naver 응답을 공통 SocialUser DTO로 변환
```

---

## 개발 환경

- Node.js 18 이상
- Firebase CLI
- Firebase Functions v2
- axios (API 호출)

---

## 초기 세팅

### 1. Firebase CLI 설치

```bash
npm install -g firebase-tools
firebase login
```

### 2. functions 디렉토리로 이동

```bash
cd functions
```

### 3. 필요한 패키지 설치

```bash
npm install
npm install axios
```

---

## 함수 설명

### 1. kakaoCustomAuth

- 호출 경로: `functions.getHttpsCallable("kakaoCustomAuth")`
- 파라미터:
  - `accessToken` (카카오 로그인 SDK에서 받은 토큰)
- 동작:
  1. 카카오 사용자 정보 API 호출 (`https://kapi.kakao.com/v2/user/me`)
  2. SocialUser DTO 생성
  3. Firebase Custom Token 발급 및 반환

### 2. naverCustomAuth

- 호출 경로: `functions.getHttpsCallable("naverCustomAuth")`
- 파라미터:
  - `accessToken` (네이버 로그인 SDK에서 받은 토큰)
- 동작:
  1. 네이버 사용자 정보 API 호출 (`https://openapi.naver.com/v1/nid/me`)
  2. SocialUser DTO 생성
  3. Firebase Custom Token 발급 및 반환

---

## 공통 SocialUser DTO

`utils/social-mapper.js`에서 두 API의 응답을 아래와 같은 공통 형식으로 변환합니다.

```javascript
{
  uid: "kakao:12345678",
  provider: "kakao",
  email: "abc@example.com",
  name: "홍길동",
  profileImage: "https://..."
}
```

---

## 로컬 테스트

Functions 에뮬레이터 실행:

```bash
firebase emulators:start
```

---

## 배포

```bash
firebase deploy --only functions
```

배포 후 다음 함수들이 활성화됩니다:

- `kakaoCustomAuth`
- `naverCustomAuth`

---

## 주의사항

- `axios`는 **functions 디렉토리 안에서 설치**해야 합니다.
- `admin.initializeApp()`은 **index.js에서 한 번만** 호출합니다.
- Functions v2 기준으로 작성되었습니다.
- Node.js 18 런타임을 사용하세요. (`functions/package.json`의 engines.node 확인)
