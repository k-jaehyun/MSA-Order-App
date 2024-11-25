# MSA-Order-App
Micro Service Architecture 의 간단한 상품 주문 App

- 아래 그림에 Config server 까지 스근하게 더하기

- zipkin: docker에서 9411 포트로 열어줌

- 프로젝트를 간단하게 처리하기 위해 Exception은 단순하게 처리했음. 게이트웨이에서 모두 500 에러로 판단.
  - GlobalException으로 잡아 Response로 응답하면 400 에러를 만들 수 있을 것으로 생각함.

![image](https://github.com/user-attachments/assets/80e61ddb-174c-4d1c-91e1-9c3e3d8db215)

