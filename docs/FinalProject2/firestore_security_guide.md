
# 🔐 Firestore 앱 보안을 위한 적용 가능한 모든 보안 항목

## ✅ 1. Firebase Authentication으로 인증 필수화

Firestore의 보안 규칙은 인증된 사용자 정보(`request.auth.uid`)를 기준으로 동작합니다. 따라서 **로그인하지 않은 사용자는 기본적으로 아무것도 접근할 수 없도록 설정**해야 합니다.

```js
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

## ✅ 2. 사용자 별 데이터 접근 제한

사용자가 **자신의 데이터에만 접근**할 수 있도록 제한

```js
match /users/{userId} {
  allow read, write: if request.auth != null && request.auth.uid == userId;
}
```

## ✅ 3. 하위 컬렉션까지 규칙 일관성 유지

```js
match /users/{userId}/user_books/{bookId} {
  allow read, write: if request.auth.uid == userId;
}

match /users/{userId}/quotes/{quoteId} {
  allow read, write: if request.auth.uid == userId;
}
```

## ✅ 4. 역할 기반 권한 관리 (RBAC)

```js
match /users/{userId} {
  allow read: if request.auth.token.admin == true;
}
```

## ✅ 5. 문서의 일부 필드에 대한 보호

```js
match /users/{userId} {
  allow update: if request.resource.data.role == resource.data.role;
}
```

## ✅ 6. 시간 기반 제한

```js
match /posts/{postId} {
  allow read: if resource.data.publishedAt <= request.time;
}
```

## ✅ 7. 쿼리 기반 필터링 우회 방지

```js
match /users/{userId} {
  allow read: if request.auth.uid == userId || request.auth.token.admin == true;
}
```

## ✅ 8. Cloud Functions를 통해 민감 정보 우회 보호

```ts
exports.approveUser = functions.https.onCall((data, context) => {
  const uid = context.auth?.uid;
  if (!uid || !context.auth.token.admin) {
    throw new functions.https.HttpsError('permission-denied', 'Admin only');
  }
});
```

## ✅ 9. 서버 시간 기반 무결성 검증

```js
match /comments/{commentId} {
  allow create: if request.resource.data.timestamp <= request.time;
}
```

## ✅ 10. 읽기/쓰기 구분 제한

```js
match /reports/{reportId} {
  allow create: if request.auth != null;
  allow read: if false;
}
```

## ✅ 11. 삭제 요청 제한

```js
match /posts/{postId} {
  allow delete: if false;
}
```

또는 작성자만 삭제 가능하게:

```js
match /posts/{postId} {
  allow delete: if request.auth.uid == resource.data.authorId;
}
```

## ✅ 12. Firebase Storage 보안

```js
service firebase.storage {
  match /b/{bucket}/o {
    match /user_uploads/{userId}/{allPaths=**} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
  }
}
```

## ✅ 13. 로그인하지 않은 사용자 접근 완전 차단

```js
match /{document=**} {
  allow read, write: if false;
}
```

## ✅ 14. Simulate 기능으로 규칙 테스트

- Firebase Console → Firestore → Rules 탭 → **Simulate**
- 특정 UID로 읽기/쓰기 가능 여부 테스트

## ✅ 15. 배포 전 `rules: strict` 적용 권장

```js
allow read, write: if false;
```

---

## 🔄 체크리스트 요약

| 항목                                  | 적용 여부 체크 |
|---------------------------------------|----------------|
| Firebase Auth 인증 적용                | ✅              |
| 사용자별 문서 접근 제한               | ✅              |
| 하위 컬렉션 규칙 포함                 | ✅              |
| 관리자/권한 분리                      | ✅              |
| 민감 필드 변경 제한                   | ✅              |
| 시간/상태 기반 접근 제한              | ✅              |
| Cloud Function 통한 민감 처리         | ✅              |
| 클라이언트 필드 조작 방지             | ✅              |
| 삭제/쓰기 제한                        | ✅              |
| Firebase Storage 보안 규칙 설정       | ✅              |
| 로그인 안한 사용자 차단              | ✅              |
| 시뮬레이션 테스트                     | ✅              |
| 배포 전 보안 규칙 점검               | ✅              |
