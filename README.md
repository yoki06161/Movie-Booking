# Movie Booking System

> **토이 프로젝트**: Spring Boot + JPA + MySQL + Redis + JWT 인증을 사용한 간단한 영화 예매 시스템
<br/>

## 1. 프로젝트 개요

**Movie Booking System**은 다음과 같은 목적으로 만들어진 학습용 토이 프로젝트입니다.

1. 데이터베이스 트랜잭션, 동시성 제어, 인증/인가(JWT), 캐싱(Redis) 등을 직접 구현해 보며 이론과 실습을 연결

2. Spring Boot, JPA, Spring Security 등을 이용해 REST API를 설계하고 구현

3. Docker & Redis 등 실무에서 자주 쓰이는 기술 스택을 체험
<br/>


## 2. 주요 기능

1. **회원 가입 & 로그인**:  
   - Spring Security + JWT 기반 로그인/회원관리  

   - BCryptPasswordEncoder를 통한 비밀번호 해싱  

2. **영화/상영정보 조회**:  
   - 인기 영화 목록 조회 (Redis 캐싱 적용)
  
   - 상영 스케줄, 좌석 정보 관리  

3. **좌석 예매**:  
   - 동시성 제어(비관적 락)로 동일 좌석 중복 예매 방지  

   - 트랜잭션 처리(JPA / MySQL)  

4. **Docker & docker-compose**:  
   - MySQL, Redis, Spring Boot 컨테이너를 한 번에 구동 가능 
 
   - 환경 변수로 DB 접속 정보 관리  

5. **Swagger(OpenAPI)**:  
   - API 엔드포인트 확인 및 테스트  
<br/>

## 3. 기술 스택

- **Back-end**:  
  - Java 17, Spring Boot 3.x  

  - Spring Data JPA (Hibernate), Spring Security(JWT), Lombok  

  - MySQL (RDB), Redis (캐싱, 세션)  

- **Infrastructure**:  
  - Docker, docker-compose  

- **API 문서화**:  
  - SpringDoc (Swagger UI)
<br/>

## 4. 프로젝트 구조

```
movie-booking
 ┣ src
 ┃ ┣ main
 ┃ ┃ ┣ java/com/example/moviebooking
 ┃ ┃ ┃ ┣ config/            # (Security, Redis, Swagger config)
 ┃ ┃ ┃ ┣ controller/        # REST API 컨트롤러
 ┃ ┃ ┃ ┣ domain/            # 엔티티 (Movie, Schedule, Seat, Reservation, User)
 ┃ ┃ ┃ ┣ dto/               # DTO 클래스
 ┃ ┃ ┃ ┣ repository/        # JPA 레포지토리
 ┃ ┃ ┃ ┣ service/           # 비즈니스 로직
 ┃ ┃ ┃ ┗ util/              # JWT 관련 유틸 필터 등
 ┃ ┃ ┗ resources
 ┃ ┃    ┗ application.yml   # DB, Redis, Swagger 설정 등
 ┃ ┗ test
 ┃    ┗ java/com/example/moviebooking
 ┣ Dockerfile
 ┗ docker-compose.yml
```

## 5. 사용 방법

### 5.1 로컬에서 실행하기

1. **MySQL** 실행 후, `movie_db` 데이터베이스 생성 (또는 `application.yml`에 맞춰 DB 설정 수정)  

2. **Redis** 실행 (혹은 로컬 설치)  

3. 프로젝트 클론
```
git clone https://github.com/yoki06161/movie-booking.git(예시)
```

4. 프로젝트 폴더로 이동 후, Gradle 빌드 & 실행
```
./gradlew bootRun
```

5. 정상 구동 후 http://localhost:8080 에서 서버 동작 확인

### 5.2 Docker 환경에서 실행하기
```
docker-compose up --build
```
- db, redis, app 컨테이너가 순차적으로 올라갑니다.

- 앱이 구동되면 http://localhost:8080/api/v1/... 로 API 호출 가능
<br/>

## 6. API 확인 (Swagger UI)
- ### Swagger URL:
  - http://localhost:8080/swagger-ui/index.html (SpringDoc 사용 시)

- ### 주요 API 예시
  - **회원가입:** POST /api/v1/auth/signup

  - **로그인:** POST /api/v1/auth/login (JWT 토큰 반환)

  - **인기 영화 조회:** GET /api/v1/movies/popular (Authorization: Bearer {토큰})

  - **좌석 예매:** POST /api/v1/reservations/{seatId} (Authorization: Bearer {토큰})
<br/>

## 7. 시연 예시
1. **회원 가입**

   - POST /api/v1/auth/signup

```
{
  "email": "test@example.com",
  "password": "1234",
  "name": "Test User"
}
```
2. **로그인** → 응답으로 JWT 토큰을 수령

   - POST /api/v1/auth/login

```
{
  "email": "test@example.com",
  "password": "1234"
}
```
3. **인기 영화 조회** → 헤더에 Authorization: Bearer {JWT 토큰}

   - GET /api/v1/movies/popular

5. **좌석 예매** → 예매 API 호출 시 동시성 제어(비관적 락) 테스트 가능

   - POST /api/v1/reservations/{seatId}

<br/>

## 8. 주요 학습 포인트
1. **운영체제 & 동시성**
   - 멀티스레드 환경에서 동일 좌석이 동시에 예매되는 문제를 비관적 락으로 방지

   - 트랜잭션 격리 수준, DB 락 개념 실습

2. **네트워크 & HTTP**
   - REST API 구조, JWT 토큰 인증/인가 과정

   - Swagger/OpenAPI 문서화

3. **DB (MySQL)**
   - JPA (Hibernate) 기반 CRUD & 쿼리 확인
   - 
   - 인덱스, 트랜잭션, 락, Join 전략 등

4. **Redis**
   - 인기 영화 목록 캐싱, 데이터 만료 정책

5. **Docker**
   - DB + Redis + Spring Boot를 컨테이너로 구성, docker-compose로 편리한 실행

6. **Spring Security**
   - JWT 구조(헤더.Payload.서명) 이해 및 사용
   - SecurityFilterChain, OncePerRequestFilter 활용
<br/>
