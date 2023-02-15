-- 기존테이블 삭제
DELETE FROM user_authorities;
ALTER TABLE user_authorities AUTO_INCREMENT = 1;
DELETE FROM authority;
ALTER TABLE authority AUTO_INCREMENT = 1;
DELETE FROM leisure_file;
ALTER TABLE leisure_file AUTO_INCREMENT = 1;
DELETE FROM review_comment;
ALTER TABLE review_comment AUTO_INCREMENT = 1;
DELETE FROM review_file;
ALTER TABLE review_file AUTO_INCREMENT = 1;
DELETE FROM review;
ALTER TABLE review AUTO_INCREMENT = 1;
DELETE FROM reservation;
ALTER TABLE reservation AUTO_INCREMENT = 1;
DELETE FROM leisure;
ALTER TABLE leisure AUTO_INCREMENT = 1;
DELETE FROM company;
ALTER TABLE company AUTO_INCREMENT = 1;
DELETE FROM qna_comment;
ALTER TABLE qna_comment AUTO_INCREMENT = 1;
DELETE FROM qna;
ALTER TABLE qna AUTO_INCREMENT = 1;
DELETE FROM user;
ALTER TABLE user AUTO_INCREMENT = 1;

-- 샘플 authority
INSERT INTO authority (name) VALUES
	('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_COMPANY')
;
-- 샘플 사용자
INSERT INTO user (u_id, password) VALUES
('USER1', '1234'),
('USER2', '1234'),
('COMPANY1', '1234'),
('ADMIN1', '1234')
;
-- 샘플 사용자-권한
INSERT INTO user_authorities VALUES
(1,1),
(3,1),
(3,3),
(4,1),
(4,2),
(4,3)
;
-- 샘플 qna
INSERT INTO qna (user_id, subject, content) VALUES
(1, '제목입니다1', '내용입니다1'),
(1, '제목입니다2', '내용입니다2'),
(3, '제목입니다3', '내용입니다3'),
(3, '제목입니다4', '내용입니다4'),
(4, '제목입니다5', '내용입니다5'),
(4, '제목입니다6', '내용입니다6')
;
-- 샘플 qna_comment
INSERT INTO qna_comment (user_id, qna_id, content) VALUES
(1, 1, '1. user1이 1번글에 댓글 작성.'),
(1, 1, '2. user1이 1번글에 댓글 작성.'),
(1, 2, '3. user1이 2번글에 댓글 작성.'),
(1, 2, '4. user1이 2번글에 댓글 작성.'),
(1, 3, '5. user1이 3번글에 댓글 작성.'),
(1, 3, '6. user1이 3번글에 댓글 작성.'),
(1, 4, '7. user1이 4번글에 댓글 작성.'),
(1, 4, '8. user1이 4번글에 댓글 작성.'),
(3, 1, '9. company1이 1번글에 댓글 작성.'),
(3, 1, '10. company1이 1번글에 댓글 작성.'),
(3, 2, '11. company1이 2번글에 댓글 작성.'),
(3, 2, '12. company1이 2번글에 댓글 작성.'),
(3, 3, '13. company1이 3번글에 댓글 작성.'),
(3, 3, '14. company1이 3번글에 댓글 작성.'),
(3, 4, '15. company1이 4번글에 댓글 작성.'),
(3, 4, '16. company1이 4번글에 댓글 작성.'),
(4, 1, '17. admin1이 1번글에 댓글 작성.'),
(4, 1, '18. admin1이 1번글에 댓글 작성.'),
(4, 2, '19. admin1이 2번글에 댓글 작성.'),
(4, 2, '20. admin1이 2번글에 댓글 작성.'),
(4, 3, '21. admin1이 3번글에 댓글 작성.'),
(4, 3, '22. admin1이 3번글에 댓글 작성.'),
(4, 4, '23. admin1이 4번글에 댓글 작성.'),
(4, 4, '24. admin1이 4번글에 댓글 작성.')
;
-- 샘플 company
INSERT INTO company (user_id, name, address, companyname) VALUES
(3, '홍길동', '서울특별시', '이마트'),
(4, '도라에몽', '수원시', '롯데')
;
-- 샘플 leisure
INSERT INTO leisure (company_id, name, price, content, address) VALUES
(1, '스키캠프', 3999, '스키탈수있음', '춘천시'),
(1, '페러글라이딩', 3999, '날수있음', '원주시'),
(2, '수영놀이', 2999, '수영장 꽁자야', '대구광역시'),
(2, '잠수부체험', 3999, '물속구경가능', '목포시')
;
-- 샘플 leisure_file
INSERT INTO leisure_file (leisure_id, SOURCE, file) VALUES
(1, 'face01.png', 'face01.png'),
(1, 'face02.png', 'face02.png'),
(2, 'face03.png', 'face03.png'),
(2, 'face04.png', 'face04.png'),
(3, 'face05.png', 'face05.png'),
(3, 'face06.png', 'face06.png'),
(4, 'face07.png', 'face07.png'),
(4, 'face08.png', 'face08.png')
;
-- 샘플 reservation
INSERT INTO reservation (user_id, leisure_id, name, phone, date) VALUES
(1, 1, '김가', 01011111111, '20230214'),
(3, 1, '김마', 01011111115, '20230214'),
(4, 1, '김자', 01011111119, '20230214')
;
-- 샘플 review
INSERT INTO review (reservation_id, user_id, leisure_id, subject, content, star) VALUES
(1, 1, 1, '제목', '내용', 3),
(1, 1, 2, '제목', '내용', 3),
(1, 1, 3, '제목', '내용', 3),
(1, 1, 4, '제목', '내용', 3),
(1, 3, 1, '제목', '내용', 3),
(1, 3, 2, '제목', '내용', 3),
(1, 3, 3, '제목', '내용', 3),
(1, 3, 4, '제목', '내용', 3),
(1, 4, 1, '제목', '내용', 3),
(1, 4, 2, '제목', '내용', 3),
(1, 4, 3, '제목', '내용', 3),
(1, 4, 4, '제목', '내용', 3),
(2, 1, 1, '제목', '내용', 3),
(2, 1, 2, '제목', '내용', 3),
(2, 1, 3, '제목', '내용', 3),
(2, 1, 4, '제목', '내용', 3),
(2, 3, 1, '제목', '내용', 3),
(2, 3, 2, '제목', '내용', 3),
(2, 3, 3, '제목', '내용', 3),
(2, 3, 4, '제목', '내용', 3),
(2, 4, 1, '제목', '내용', 3),
(2, 4, 2, '제목', '내용', 3),
(2, 4, 3, '제목', '내용', 3),
(2, 4, 4, '제목', '내용', 3),
(3, 1, 1, '제목', '내용', 3),
(3, 1, 2, '제목', '내용', 3),
(3, 1, 3, '제목', '내용', 3),
(3, 1, 4, '제목', '내용', 3),
(3, 3, 1, '제목', '내용', 3),
(3, 3, 2, '제목', '내용', 3),
(3, 3, 3, '제목', '내용', 3),
(3, 3, 4, '제목', '내용', 3),
(3, 4, 1, '제목', '내용', 3),
(3, 4, 2, '제목', '내용', 3),
(3, 4, 3, '제목', '내용', 3),
(3, 4, 4, '제목', '내용', 3)
;
-- 샘플 review_comment
INSERT INTO review_comment (user_id, review_id, content) VALUES
(1, 1, '1. user1이 1번글에 댓글 작성.'),
(1, 2, '2. user1이 1번글에 댓글 작성.'),
(1, 3, '3. user1이 2번글에 댓글 작성.'),
(1, 4, '4. user1이 2번글에 댓글 작성.'),
(1, 5, '5. user1이 3번글에 댓글 작성.'),
(1, 6, '6. user1이 3번글에 댓글 작성.'),
(1, 7, '7. user1이 4번글에 댓글 작성.'),
(1, 8, '8. user1이 4번글에 댓글 작성.'),
(3, 9, '9. company1이 1번글에 댓글 작성.'),
(3, 10, '10. company1이 1번글에 댓글 작성.'),
(3, 11, '11. company1이 2번글에 댓글 작성.'),
(3, 12, '12. company1이 2번글에 댓글 작성.'),
(3, 13, '13. company1이 3번글에 댓글 작성.'),
(3, 14, '14. company1이 3번글에 댓글 작성.'),
(3, 15, '15. company1이 4번글에 댓글 작성.'),
(3, 16, '16. company1이 4번글에 댓글 작성.'),
(4, 17, '17. admin1이 1번글에 댓글 작성.'),
(4, 18, '18. admin1이 1번글에 댓글 작성.'),
(4, 19, '19. admin1이 2번글에 댓글 작성.'),
(4, 20, '20. admin1이 2번글에 댓글 작성.'),
(4, 21, '21. admin1이 3번글에 댓글 작성.'),
(4, 22, '22. admin1이 3번글에 댓글 작성.'),
(4, 23, '23. admin1이 4번글에 댓글 작성.'),
(4, 24, '24. admin1이 4번글에 댓글 작성.'),
(3, 25, '12. company1이 2번글에 댓글 작성.'),
(3, 26, '13. company1이 3번글에 댓글 작성.'),
(3, 27, '14. company1이 3번글에 댓글 작성.'),
(3, 28, '15. company1이 4번글에 댓글 작성.'),
(3, 29, '16. company1이 4번글에 댓글 작성.'),
(4, 30, '17. admin1이 1번글에 댓글 작성.'),
(4, 31, '18. admin1이 1번글에 댓글 작성.'),
(4, 32, '19. admin1이 2번글에 댓글 작성.'),
(4, 33, '20. admin1이 2번글에 댓글 작성.'),
(4, 34, '21. admin1이 3번글에 댓글 작성.'),
(4, 35, '22. admin1이 3번글에 댓글 작성.'),
(4, 36, '23. admin1이 4번글에 댓글 작성.')
;
-- 샘플 review_file
INSERT INTO review_file (review_id, SOURCE, file) VALUES
(1, 'face01.png', 'face01.png'),
(2, 'face02.png', 'face02.png'),
(3, 'face03.png', 'face03.png'),
(4, 'face04.png', 'face04.png'),
(5, 'face05.png', 'face05.png'),
(6, 'face06.png', 'face06.png'),
(7, 'face07.png', 'face07.png'),
(8, 'face08.png', 'face08.png'),
(9, 'face01.png', 'face01.png'),
(10, 'face02.png', 'face02.png'),
(11, 'face03.png', 'face03.png'),
(12, 'face04.png', 'face04.png'),
(13, 'face05.png', 'face05.png'),
(14, 'face06.png', 'face06.png'),
(15, 'face07.png', 'face07.png'),
(16, 'face08.png', 'face08.png'),
(17, 'face01.png', 'face01.png'),
(18, 'face02.png', 'face02.png'),
(19, 'face03.png', 'face03.png'),
(20, 'face04.png', 'face04.png'),
(21, 'face05.png', 'face05.png'),
(22, 'face06.png', 'face06.png'),
(23, 'face07.png', 'face07.png'),
(24, 'face08.png', 'face08.png'),
(25, 'face01.png', 'face01.png'),
(26, 'face02.png', 'face02.png'),
(27, 'face03.png', 'face03.png'),
(28, 'face04.png', 'face04.png'),
(29, 'face05.png', 'face05.png'),
(30, 'face06.png', 'face06.png'),
(31, 'face07.png', 'face07.png'),
(32, 'face08.png', 'face08.png'),
(33, 'face01.png', 'face01.png'),
(34, 'face02.png', 'face02.png'),
(35, 'face03.png', 'face03.png'),
(36, 'face04.png', 'face04.png')
;