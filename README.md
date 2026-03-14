# 🚀 Guestbook - MyBatis Version

> **Spring Boot MVC 구조 학습을 위해 JPA 기반 예제를 MyBatis 구조로
> 마이그레이션하고\
> Spring Security 인증 기능(DB Login, OAuth2, JWT)을 확장 구현한
> 프로젝트**

------------------------------------------------------------------------

## 📌 프로젝트 개요

본 프로젝트는 **Spring Boot 기반 MVC 패턴 학습을 목적으로 제작된 웹
애플리케이션**입니다.

기존에 JPA + QueryDSL로 구현된 프로젝트를, 실무 환경과 동일한 기술
스택에 맞추어 MyBatis 기반 구조로 마이그레이션하였습니다.

또한 프로젝트를 확장하여 **Spring Security 기반 인증 시스템을 추가
구현**했습니다.

### 마이그레이션 배경

- 현업에서는 JPA 및 모던 ORM 프레임워크를 사용하지 않음
- 실무 기술 스택과 동일한 환경에서 MVC 패턴을 더 명확히 이해하고자 함
- 러닝 커브를 낮추고, SQL 중심 구조에 대한 이해도를 높이기 위해
  MyBatis 선택

### 주요 학습 목표

이 프로젝트는 단순 CRUD 구현이 목적이 아니라,

- MVC 계층 분리
- 요청-응답 흐름 이해
- 서비스 레이어 책임 분리
- MyBatis 기반 데이터 접근 방식 학습
- JPA와 SQL 중심 설계 방식 비교
- Spring Security 인증 구조 이해

에 초점을 둡니다.

------------------------------------------------------------------------

## 🧠 프로젝트 특징

이 프로젝트는 단순 CRUD 예제가 아니라 다음 과정을 포함합니다.

### 1️⃣ JPA → MyBatis 마이그레이션

기존 예제의 **JPA / QueryDSL 기반 데이터 접근 구조를 MyBatis 기반으로 재구성**했습니다.

| 기존 구조       | 변경 구조    |
  | --------------- | ------------ |
| JPA Entity      | Domain / VO  |
| Repository      | Mapper       |
| JPQL / QueryDSL | XML 기반 SQL |
| ORM 중심        | SQL 중심     |

이를 통해 **ORM 기반 개발과 SQL 기반 개발의 차이를 비교 학습**할 수 있습니다.

------------------------------------------------------------------------

### 2️⃣ MVC 계층 구조 명확화

프로젝트는 전형적인 **Spring MVC 계층 구조**를 따릅니다.

    Client Request
          ↓
    Controller
          ↓
    Service
          ↓
    Mapper (MyBatis)
          ↓
    Database

각 계층의 책임을 분리하여 **유지보수성과 구조 이해도를 높이는 것을 목표**로 합니다.

------------------------------------------------------------------------

### 3️⃣ 인증 시스템 확장

기본 기능 외에도 **Spring Security 기반 인증 시스템을 확장 구현**했습니다.

구현된 인증 방식

| 인증 방식              | 설명                               |
  | ---------------------- | ---------------------------------- |
| DB Login               | 사용자 정보를 DB에서 조회하여 인증 |
| Google OAuth2 Login    | Google 계정을 이용한 소셜 로그인   |
| JWT API Authentication | 토큰 기반 API 접근                 |

이를 통해 **세션 기반 인증과 토큰 기반 인증의 차이를 학습**할 수 있습니다.

------------------------------------------------------------------------

## 🔐 Authentication Flow

### 1️⃣ DB 기반 로그인

Spring Security `UserDetailsService`를 활용하여 **DB 사용자 정보를 기반으로 인증**하도록 구현했습니다.

    Login Request
         ↓
    Spring Security Filter
         ↓
    UserDetailsService
         ↓
    DB User 조회
         ↓
    Authentication 성공

------------------------------------------------------------------------

### 2️⃣ Google OAuth2 Login

Spring Security OAuth2 Client 기능을 이용하여 **Google 계정 기반 소셜 로그인**을 구현했습니다.

OAuth2 인증 후에는 Google 사용자 정보를 애플리케이션 사용자 정보와 매핑하여 인증을 처리합니다.

    Client
      ↓
    Google OAuth2 인증
      ↓
    OAuth2UserService
      ↓
    Google 사용자 정보 조회
      ↓
    DB 사용자 조회 / 매핑
      ↓
    Authentication 성공

OAuth2 로그인 후에는 **애플리케이션 사용자 정보와 매핑하여 인증을 유지**합니다.

------------------------------------------------------------------------

### 3️⃣ JWT API Authentication

로그인 성공 시 **JWT 토큰을 발급**하고 API 요청 시 토큰을 통해 인증합니다.

    Authorization: Bearer {JWT}

JWT 인증 흐름

    Client Request
          ↓
    JWT Filter
          ↓
    Token Validation
          ↓
    Token에서 UserId 추출
          ↓
    DB 사용자 조회
          ↓
    SecurityContext 저장
          ↓
    Controller 접근 허용

토큰에 포함된 사용자 정보가 DB에 존재하는 사용자와 일치하는지 검증한 후 인증을 완료합니다.

------------------------------------------------------------------------

## 🧰 기술 스택

### Backend

- Java 17
- Spring Boot
- Spring Security
- MyBatis
- JWT

### Frontend

- Thymeleaf

### Database

- PostgreSQL

### Build Tool

- Gradle

------------------------------------------------------------------------

## 📂 프로젝트 구조

    controller
     └─ 요청 처리 및 View 반환

    service
     └─ 비즈니스 로직 처리
     └─ 트랜잭션 경계 설정

    mapper
     └─ MyBatis Mapper 인터페이스
     └─ XML 기반 SQL 정의

    domain
     └─ Entity / VO

    dto
     └─ 요청 / 응답 객체

    config
    └─ SecurityConfig
    └─ Web 설정
    
    security
    ├─ filter      (JWT 인증 / API 로그인 필터)
    ├─ service     (UserDetailsService, OAuth2UserService)
    ├─ handler     (로그인 성공 / 실패 처리)
    ├─ dto         (Security 인증 사용자 객체)
    └─ util        (JWT 생성 및 검증)

------------------------------------------------------------------------

## 📝 주요 기능

### 게시판 기능

#### 게시글

- 게시글 목록 조회 (검색 포함)
- 게시글 등록
- 게시글 조회
- 게시글 수정
- 게시글 삭제

#### 댓글

- 댓글 등록
- 댓글 조회
- 댓글 수정
- 댓글 삭제

------------------------------------------------------------------------

### 영화 게시판 기능

#### 영화

- 영화 목록 조회 (검색 포함)
- 영화 등록
    - 스틸컷 이미지 업로드
    - 썸네일 이미지 업로드
- 영화 상세 조회

#### 리뷰

- 리뷰 등록
- 리뷰 조회
- 리뷰 수정
- 리뷰 삭제

------------------------------------------------------------------------

## ✏️ 학습 포인트

### 1. JPA → MyBatis 구조 차이 이해

| JPA             | MyBatis          |
  | --------------- | ---------------- |
| 엔티티 중심     | SQL 중심         |
| JPQL/QueryDSL   | XML 기반 SQL     |
| 영속성 컨텍스트 | 명시적 쿼리 실행 |

- ORM 추상화 제거 후 SQL 직접 제어
- 동적 쿼리 구성 (`<if>`, `<where>`, `<trim>`) 활용
- 페이징 및 검색 조건 처리 방식 차이 이해

------------------------------------------------------------------------

### 2. MVC 흐름 체득

1. Controller에서 요청 수신
2. Service에서 비즈니스 로직 수행
3. Mapper에서 DB 접근
4. 결과를 View에 전달

요청 → Controller → Service → Mapper → DB → View

------------------------------------------------------------------------

### 3. 파일 업로드 처리

- `MultipartFile` 기반 이미지 업로드
- 영화 등록 시
    - 썸네일 이미지
    - 스틸컷 이미지 다중 업로드
- 파일 경로를 DB에 저장 후 화면 렌더링

------------------------------------------------------------------------

## ▶️실행 방법

``` bash
git clone https://github.com/jwo29/guestbook-mybatis.git
cd guestbook-mybatis
./gradlew bootRun
```

브라우저 접속:

    http://localhost:8080

------------------------------------------------------------------------

## 📈 개선 예정 사항

- 페이징 고도화
- 예외 처리 공통화 (Global Exception Handler)
- 공통 응답 구조 설계
- Refresh Token 구현
- JWT Claim 확장
- Role 기반 권한 관리
- 테스트 코드 추가
- 트랜잭션 경계 명확화

------------------------------------------------------------------------

## 🎯 프로젝트 의의

이 프로젝트는 단순 CRUD 구현이 아닌 다음을 목표로 합니다.

- Spring MVC 아키텍처 이해
- MyBatis 기반 SQL 중심 데이터 접근
- JPA와 MyBatis 구조 비교
- Spring Security 인증 구조 학습
- OAuth2 및 JWT 인증 방식 이해

즉, **MVC 구조와 인증 시스템을 함께 학습하기 위한 마이그레이션 프로젝트입니다.**
