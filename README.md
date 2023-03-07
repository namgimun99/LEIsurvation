# Leisurvation

## 프로젝트 소개
유저가 레져를 예약, 예약 관리, 후기 작성을 쉽게 할 수 있고 사업자 등록을 해서 레져를 등록, 비즈니스 관리까지 가능하게 하는 웹사이트 입니다.
------------------------------------------
## 멤버구성
-이수민(팀장) : 디비설계, Github 세팅 및 관리, 리뷰 페이지에 관한 CRUD, 프론트<br/>
-남기문 : 레져, 거래처(컴퍼니) 페이지에 관한 CRUD, 프론트<br/>
-이승준 : 예약, 마이페이지에 관한 CRUD, 프론트<br/>
-박찬미 : 로그인, 카카오로그인, 회원가입, 권한에관한 CRUD, 메인, 헤더 프론트
-----------------------------
## 개발환경
HTML5, CSS3, JavaScript, jQuery, JSON, Ajax, BootStrap4.0, Java, Spring, Spring Security, Maven, JDBC, Mysql, Dbeaver, Mybatis, Intellij, Apache Tomcat, lombok, Thymeleaf
-------------------
## db 설정
  name : leidb<br/>
  id : leiuser<br/>
  pw : 1234
  
  ###세팅 방법
  ```
    mysql -u root -p 1234
    create database leidb;
    create user 'leiuser'@'%' identified by '1234';
    grant all privileges on leidb.*to'leiuser'@'%';
    flush privileges;
    quit
    mysql -u leiuser -p leidb 1234
    use leidb;
 ```
