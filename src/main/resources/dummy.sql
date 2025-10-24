INSERT INTO post (title, content, member_id, created_at, updated_at)
SELECT
    CONCAT('더미 제목 ', n) AS title,
    CONCAT('이것은 더미 내용입니다. 번호: ', n, ' — 자동 생성 게시물입니다.') AS content,
    1 AS member_id,
    NOW() AS created_at,
    NOW() AS updated_at
FROM (
    SELECT @rownum := @rownum + 1 AS n
    FROM information_schema.columns a,
         information_schema.columns b,
         (SELECT @rownum := 0) r
    LIMIT 1000000
) ;