# SwitchSwitch Auth Server

JWT 기반 인증 서버

`SpringBoot 3.3.1`, `JPA`, `jjwt`

## 프로젝트 설정

- application-secret.properties 파일 생성
   - application-secret.properties.txt 내용 확인

## JWT

- Access Token 
  - 만료시간: 5분

- Refresh Token
  - 만료시간: 1년
  - 저장 위치: DB
  - 만료 시 재로그인 처리 