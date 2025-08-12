# 특허바다

<div align="center">
  <img src="https://i.postimg.cc/fW5GvgC2/image.png" width="45%" style="margin-right: 2%;">
  <img src="https://i.postimg.cc/Ghdp4N2X/2.png" width="45%">
</div>

## 무형 재산권 중개 플랫폼 
지식 재산권에 대한 거래 중개 플랫폼으로서 특허, 레시피 등 다양한 무형 재산권에 대한 거래 중개 플랫폼입니다. 
사용자는 판매 게시글을 올릴 수 있고, 구매자는 게시글을 보고 채팅 기능을 이용해 궁금한 점을 물어볼 수 있습니다. 

<br>

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

## 담당한 기능 - 채팅
### SSE VS Websocket
websocket을 사용해서 클라이언트와 서버간 통신을 구현했습니다. <br><br>
최초에 SSE와 Websocket 중 고민 했고 실시간 채팅 기능 구현이 목적이라 Websocket을 선택했습니다.<br> 
SSE가 프로젝트에 적합하지 않다고 판단한 근거는 SSE는 단방향 통신이기 때문에 클라이언트에서 지속적으로 서버에 요청을 해야하는데 서버에서의 부하가 클것이라고 판단했습니다.

### Redis pub/sub VS RabbitMQ

Redis Pub/Sub을 사용해서 서버간 통신을 구현했습니다. <br><br>

RabbitMQ는 push알림, 필터링 기능(욕설) 등 부가 기능도 많고, Redis pub/sub 보다 신뢰성 있는 전송을 보장하는 특징이 있습니다.
<br>
그럼에도 불구하고 Redis pub/sub을 사용한 이유는 채팅 특성상 신뢰성 있는 전송보다 빠른 전송을 구현하는게 목적이었습니다. 



