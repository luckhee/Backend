# 특허바다 - 무형 재산권 중개 플랫폼

<div align="center">
  <img src="https://i.postimg.cc/fW5GvgC2/image.png" width="45%" style="margin-right: 2%;">
  <img src="https://i.postimg.cc/Ghdp4N2X/2.png" width="45%">
</div>

###  
지식 재산권에 대한 거래 중개 플랫폼으로서 특허, 레시피 등 다양한 무형 재산권에 대한 거래 중개 플랫폼입니다. 
사용자는 판매 게시글을 올릴 수 있고, 구매자는 게시글을 보고 채팅 기능을 이용해 궁금한 점을 물어볼 수 있습니다. 

<br>

---
링크 : https://frontend-devteam-10.vercel.app/
---


## 👨‍👩‍👧‍👦 팀원 소개

| 이름 | 역할 |
|:---:|:---:|
| **박태규** | 팀장 및 백엔드 개발 (첨부파일, 백엔드 배포) |
| **김윤수** | 백엔드 개발 (거래, GitHub 관리자) |
| **김선우** | 백엔드 개발 (게시판) |
| **석희성** | 백엔드 개발 (채팅, 프론트 배포) |
| **임홍담** | 백엔드 개발 (회원/인증) |
| 공동 | 프론트 개발 |

---
<br>

## ERD
<img width="725" height="527" alt="image" src="https://i.postimg.cc/1zPKS2fD/ERD.png" />

## WBS
<img width="941" height="831" alt="image" src="https://i.postimg.cc/wxt4wKJB/wbs.png" />

---
## 기술스택
Backend - SpringBoot, Java(21), RestApi, JPA, JWT, WebSocket+Stomp, Redis Pub/Sub
<br>
Frontend - next.js , Tailwind.css
<br>
DB - MySql, Redis, Docker, Google Cloud Storage
<br>
Infra - Vercel , GCP VM, Nginx

<br>

## 시스템 구성도
<div align="center">
  <img src="https://i.postimg.cc/pT1X3Xxr/drawio.png" width="45%">
</div>



<br>

## 아키텍쳐
레이어드 아키텍쳐

<br>

## 🛠️ 주요 기능 구현 목록

### 🔐 인증 및 회원 관리
- **회원가입**: 회원가입
- **로그인/로그아웃**: 로그인 상태 관리 및 `AccessToken` 재발급
- **회원 정보**: 마이페이지 조회, 정보 수정, 회원 탈퇴
- **관리자 기능**: 회원 목록 조회, 상세 조회 및 정보 수정

### 📝 게시판
- **게시글 관리**: 등록, 수정, 삭제, 목록/상세 조회
- **찜 기능**: 게시글 찜 등록/해제 및 찜 목록 조회
- **인기차트**: 인기 게시글 조회 API

### 📎 첨부파일
- **파일 관리**: 게시글 파일 등록, 목록 조회, 삭제
- **관리자 기능**: 모든 파일 조회, 상세 조회, 삭제
- **프로필 이미지**: 사용자 프로필 이미지 업로드, 수정, 삭제

### 💸 거래
- **거래 내역**: 전체/단일 조회, 생성
- **관리자 기능**: 거래 내역 전체/단일 조회

### 💬 채팅
- **채팅방**: 생성, 조회, 나가기
- **메시지**: 전송, 목록 조회
- **실시간 통신**: Websocket 및 Redis `pub/sub`을 활용한 실시간 메시지 전송

### 🎨 프론트엔드
- 각 팀원이 담당한 파트의 프론트엔드 구현

---

<br>

## 담당한 기능 - 채팅
<div align="center">
  <img src="https://i.postimg.cc/kgnG5nNJ/1.png">

  <img src="https://i.postimg.cc/L53Rd1bz/2.png">
</div>



### SSE VS Websocket
websocket을 사용해서 클라이언트와 서버간 통신을 구현했습니다. <br><br>
최초에 SSE와 Websocket 중 고민 했고 실시간 채팅 기능 구현이 목적이라 Websocket을 선택했습니다.<br> 
SSE가 프로젝트에 적합하지 않다고 판단한 근거는 SSE는 단방향 통신이기 때문에 클라이언트에서 지속적으로 서버에 요청을 해야하는데 서버에서의 부하가 클것이라고 판단했습니다.

### Redis pub/sub VS RabbitMQ

Redis Pub/Sub을 사용해서 서버간 통신을 구현했습니다. <br><br>

RabbitMQ는 push알림, 필터링 기능(욕설) 등 부가 기능도 많고, Redis pub/sub 보다 신뢰성 있는 전송을 보장하는 특징이 있습니다.
<br>
그럼에도 불구하고 Redis pub/sub을 사용한 이유는 채팅 특성상 신뢰성 있는 전송보다 빠른 전송을 구현하는게 목적이었습니다. 

---

## 트러블 슈팅
성능 테스트(K6) <br>
테스트 시나리오: 로그인 + 1대1 채팅방 생성 + 실시간 메시지 송수신<br>
결과 <br>
<br>
50명 동시 접속 ✅<br><br>
📊 성능 지표:
- HTTP p(95): 117ms
- WebSocket 연결: 18.6ms
- 인증 성공률: 100%
- 채팅방 생성률: 100%
- HTTP 실패율: 0.17%

<br>


100명 동시 접속 ✅ <br><br>
📊 성능 지표:
- HTTP p(95): 110ms
- WebSocket 연결: 13ms  
- 인증 성공률: 100%
- 채팅방 생성률: 100%
- HTTP 실패율: 0.04%

<br>

200명 동시 접속 ⚠️<br><br>
📊 성능 지표:
- HTTP p(95): 4.33초
- WebSocket 연결: 1.99초
- 인증 성공률: 100%
- 채팅방 생성률: 100%
- HTTP 실패율: 0.02%
- 테스트 완료율: 83% (140개 중단)

## 🎯 결과: 성능 저하 시작, 응답시간 40배 증가<br>

성능급락지점 <br>
100-200명 사이에서 급격한 성능 저하 발생<br>
응답시간이 40배 증가 (110ms → 4.33초)<br>
WebSocket 연결이 150배 증가 (13ms → 1.99초)<br><br>

단계별 개선 목표<br><br>

Phase 1: 200명 안정화 <br>
- HTTP p(95) < 300ms (현재: 4.33초)
- WebSocket 연결 p(95) < 200ms (현재: 1.99초)
- 채팅방 생성률 > 99%
- 테스트 완료율 > 95%

<br>

Phase 2: 100명 초고속화 (HTTP p95 < 50ms)<br>
- HTTP p(95) < 50ms (현재: 110ms)
- WebSocket 연결 p(95) < 20ms (현재: 13ms)
- 실시간 채팅 입장 시간 최소화
- 사용자 체감 성능 극대화

## 🔍 Spring Boot Actuator를 통한 성능 분석
환경 설정

기술 스택: Java/Spring Boot 3.x, MySQL, Redis, STOMP WebSocket<br>
테스트 환경: 로컬 Docker (MySQL/Redis)
<br>
Actuator 설정
yaml#
```
build.gradle
implementation 'org.springframework.boot:spring-boot-starter-actuator'
```
<br>

``` yml
application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,metrics,info,env
  endpoint:
    health:
      show-details: always
```
### 🚨 발견된 주요 성능 이슈
1. GC(Garbage Collection) 급증 문제
측정 결과:

- 테스트 전: 37회 GC, 0.75초
- 테스트 중: 160회 GC, 1.245초
- GC 횟수 4배 증가, 처리 시간 66% 증가

원인 분석:
<br>
200명 동시 접속으로 인한 메모리 할당 급증<br>
WebSocket 연결 및 채팅 메시지 처리 과정에서 대량 객체 생성<br>
현재 JVM 기본 힙 메모리 설정 부족<br>

2. 채팅 API 성능 병목
측정 결과:
- 828회 채팅 API 요청
- 총 처리 시간: 85.5초
- 평균 응답시간: 103ms
- HTTP 상태: 400 에러와 200 성공 혼재
문제점:

평균 응답시간 103ms로 목표 대비 느림
400 Bad Request 에러 빈발
동시성 처리 문제로 인한 데이터 충돌 의심

3. 메모리 사용량 증가
측정 결과:

테스트 전: 241MB<br>
테스트 후: 276MB<br>
35MB 증가 (정상 범위이지만 지속 모니터링 필요)

--- 

### 🎯 해결 방안
1. JVM 메모리 최적화 (우선순위 1)
힙 메모리 증설:<br>
bashjava -jar -Xms1g -Xmx2g -XX:+UseG1GC -XX:MaxGCPauseMillis=100 your-app.jar<br>
G1GC 최적화:<br>
bash-XX:G1HeapRegionSize=16m<br>
-XX:MaxGCPauseMillis=50<br>
2. 채팅 API 성능 개선 (우선순위 2)
즉시 개선 가능한 부분:<br>

400 에러 원인 분석 및 해결<br>
채팅 관련 JPA 쿼리 최적화 (N+1 문제 해결)<br>
@Transactional 범위 최소화<br>

중장기 개선:

Redis 캐싱 전략 도입 (채팅방 정보, 참여자 목록)<br>
메시지 저장 비동기 처리<br>
채팅 관련 인덱스 최적화<br>

3. 데이터베이스 커넥션 풀 튜닝<br>
현재 상태:

총 커넥션: 10개<br>
활성 커넥션: 2개 (여유 있음)<br>

개선 설정:<br>
``` yml
yamlspring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 10
      connection-timeout: 20000
      leak-detection-threshold: 60000
```
📈 모니터링 지표<br>
핵심 모니터링 엔드포인트<br>

/actuator/metrics/jvm.gc.pause - GC 성능<br>
/actuator/metrics/http.server.requests - API 응답시간<br>
/actuator/metrics/jvm.memory.used - 메모리 사용량<br>
/actuator/metrics/hikaricp.connections.active - DB 커넥션<br>

성능 임계치 설정<br>

GC 횟수: 테스트 중 100회 이하 목표<br>
평균 API 응답시간: 50ms 이하 목표<br>
메모리 사용량: 1GB 이하 유지<br>
400 에러율: 1% 이하 목표<br>
