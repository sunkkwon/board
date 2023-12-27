#게시판 만들기

## 사용된 기술
- Spring Boot
- Spring MVC
- Spring JDBC
- ORACLE
- SQL
- thymeleaf 템플릿 엔진

## 아키텍쳐

브라우져 --> 요청  --> comtroller  --> Service  -->> DAO  --> DB
       <-- 응답  --- 템플릿             <--     <--      <---    
                    <------------ layer간의 데이터 전송은 DTO -->

## 게시판 만드는 순서

1. Controller 와 template
2. Service 비지니스 로직을 처리
3. Service는 비지니스로직을 처리하기위해 CRUD