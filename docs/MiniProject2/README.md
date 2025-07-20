# 📱 미니 프로젝트 #2 - Kotlin Chat App

이 프로젝트는 **Jetpack Compose + MVVM 아키텍처** 기반의 **MQTT 채팅 앱**을 개발하며 안드로이드 개발의 핵심 기술을 학습하는 코틀린 미니 프로젝트 입니다.

---

## 📌 목표

- MVVM 아키텍처에 기반한 안드로이드 앱 구조 학습
- Feature-based 모듈 구조 학습
- Jetpack Compose로 UI 선언적 구성 실습
- MQTT 기반 실시간 채팅 기능 구현
- Room DB를 활용한 로컬 메시지 저장 및 조회

---

## 🛠 기술 스택

| 영역       | 사용 기술                          |
|------------|------------------------------------|
| 언어       | Kotlin                             |
| UI         | Jetpack Compose                    |
| 구조       | MVVM + Feature-based               |
| 상태관리   | ViewModel + LiveData               |
| 로컬DB     | Room (SQLite 기반)            |
| 실시간 통신       | MQTT (Hive MQTT Broker 사용)                |
| 네트워크       | Retrofit2 + GsonConverter            |
| API 통신   | MockAPI.io (chatrooms, messages 리소스만 사용)              |
| 기타       | SharedPreferences |

---

## 📁 프로젝트 구조

```
📦 project-root
├── features/
│   ├── chatroomlist/     # 채팅 목록
│   ├── chatroom/         # 채팅 상세 (MQTT 통신)
│   └── setting/          # 설정
├── data/
│   ├── local/            # Room 구성 (DB, DAO, Entity)
│   └── mqtt/             # MQTT 클라이언트
├── model/                # 공통 데이터 모델
├── common/               # 공통 유틸/상수
└── MainActivity.kt
```

---

## 🗓 강의 일정

| 일차 | 주제                       | 실습 목표                                |
|------|--------------------------|------------------------------------------|
| 1일차 | 프로젝트 구조 설계 & 채팅방 목록/생성 | Feature 기반 구조 이해, 채팅방 목록 UI 및 생성 + Room 연동     |
| 2일차 | 채팅방 UI , 메시지 목록 및 API 통신     | 채팅방 생성 기능, 메시지 리스트 구성 및 Room 저장 및 API 통신       |
| 3일차 | MQTT 실시간 채팅 구현          | MQTT 송수신 로직, LiveData 반영, Compose UI 갱신   |
| 4일차 | 설정 화면 & UI 마무리       | 채팅방 설정화면, 메시지 타입 분기 처리, 예외/UX 개선 등    |

---

## 💡 학습 포인트

- Jetpack Compose를 활용한 **선언적 UI 개발**
- **ViewModel + LiveData**를 통한 상태 관리
- Room DB를 이용한 **로컬 저장소 구축**
- MockApi 를 이용한 원격 데이터 관리
- MQTT를 활용한 **실시간 채팅 통신 구현**
- 기능 단위로 구성된 **모듈화된 프로젝트 설계 방식**

---

## 📮 문의
강의자: [김갑석]  
이메일: [lecture.scott@email.com]
