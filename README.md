# Leisurvation


## 프로젝트 소개
사용자가 레져를 예약, 관리, 후기등을 쉽게할 수 있고 나아가 비즈니스까지 할 수 있는 웹 사이트입니다.
> 개발기간 2023-02-07 ~ 2023-03-07


## 멤버 구성
**이수민**(팀장)
디비설계, Github 세팅 및 관리, 리뷰 페이지에 관한 CRUD, 프론트<br/>
**남기문**
레져, 거래처(컴퍼니) 페이지에 관한 CRUD, 프론트<br/>
**이승준**
예약, 마이페이지에 관한 CRUD, 프론트<br/>
**박찬미**
로그인, 카카오로그인, 회원가입, 권한에관한 CRUD, 메인, 헤더 프론트

## 개발환경 

HTML5, CSS3, JavaScript, jQuery, JSON, Ajax, BootStrap4.0, Java, Spring, Spring Security, Maven, JDBC, Mysql, Dbeaver, Mybatis, Intellij, Apache Tomcat, lombok, Thymeleaf


	


## 시작하기 

 - [ ] db 설정 

> mysql -u root -p 1234<br/>
    create database leidb;<br/>
    create user 'leiuser'@'%' identified by '1234';<br/>
    grant all privileges on leidb.*to'leiuser'@'%';<br/>
    flush privileges;<br/>
    quit<br/>
    mysql -u leiuser -p leidb 1234<br/>
    use leidb;
