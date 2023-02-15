
SELECT * FROM user_authorities;
SELECT * FROM authority;
SELECT * FROM leisure_file ORDER BY id DESC;
SELECT * FROM review_comment ORDER BY id DESC;
SELECT * FROM review_file ORDER BY id DESC;
SELECT * FROM review ORDER BY id DESC;
SELECT * FROM reservation ORDER BY id DESC;
SELECT * FROM leisure ORDER BY id DESC;
SELECT * FROM company ORDER BY id DESC;
SELECT * FROM qna_comment ORDER BY id DESC;
SELECT * FROM qna ORDER BY id DESC;
SELECT * FROM user ORDER BY id DESC;

-- 특정 id 의 사용자 조회
SELECT
	id
	, u_id
	, password
	, regdate
FROM user
WHERE id = 1
;

-- 특정 name 의 authority 조회
SELECT
	id 
	, name
FROM authority
WHERE name = 'ROLE_ADMIN'


# -------------------------------------------------------
# 리뷰

# 특정글 의 (리뷰 + 사용자) 정보
SELECT c.id, c.content, c.regdate, u.id, u.u_id, u.password, u.regdate
FROM review_comment c, USER u
WHERE c.user_id = u.id AND c.review_id = 4
ORDER BY c.id DESC
;