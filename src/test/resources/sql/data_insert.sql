INSERT INTO TBL_MEMBER(MEMBER_ID ,CREATE_DATE ,UPDATE_DATE ,AGE ,BODY_PROFILE_URL ,EMAIL ,EXEC_STATUS ,PARTNER_FIND_STATUS ,HEIGHT ,MBTI ,NICKNAME ,PROFILE_URL ,SEX ,WEIGHT) VALUES (1,CURRENT_TIMESTAMP(), NULL, 28, 'body_profile_url','tlatmsrud@naver.com','W','Y',170.5,  'ISFJ','심승경','profile_url','M',65);
INSERT INTO TBL_MEMBER(MEMBER_ID ,CREATE_DATE ,UPDATE_DATE ,AGE ,BODY_PROFILE_URL ,EMAIL ,EXEC_STATUS ,PARTNER_FIND_STATUS ,HEIGHT ,MBTI ,NICKNAME ,PROFILE_URL ,SEX ,WEIGHT) VALUES (20,CURRENT_TIMESTAMP(), NULL, 20, 'body_profile_url','test1@naver.com','H','Y',155,  'ISTP','테스터1','profile_url','W',62);
INSERT INTO TBL_MEMBER(MEMBER_ID ,CREATE_DATE ,UPDATE_DATE ,AGE ,BODY_PROFILE_URL ,EMAIL ,EXEC_STATUS ,PARTNER_FIND_STATUS ,HEIGHT ,MBTI ,NICKNAME ,PROFILE_URL ,SEX ,WEIGHT) VALUES (21,CURRENT_TIMESTAMP(), NULL, 21, 'body_profile_url','test2@naver.com','H','Y',175,  'ENFP','테스터2','profile_url','M',65);
INSERT INTO TBL_MEMBER(MEMBER_ID ,CREATE_DATE ,UPDATE_DATE ,AGE ,BODY_PROFILE_URL ,EMAIL ,EXEC_STATUS ,PARTNER_FIND_STATUS ,HEIGHT ,MBTI ,NICKNAME ,PROFILE_URL ,SEX ,WEIGHT) VALUES (22,CURRENT_TIMESTAMP(), NULL, 40, 'body_profile_url','test3@naver.com','W','Y',189.5,  'ENFP','테스터3','profile_url','M',120);
INSERT INTO TBL_MEMBER(MEMBER_ID ,CREATE_DATE ,UPDATE_DATE ,AGE ,BODY_PROFILE_URL ,EMAIL ,EXEC_STATUS ,PARTNER_FIND_STATUS ,HEIGHT ,MBTI ,NICKNAME ,PROFILE_URL ,SEX ,WEIGHT) VALUES (23,CURRENT_TIMESTAMP(), NULL, 15, 'body_profile_url','test4@naver.com','W','Y',170,  'ESFJ','테스터4','profile_url','M',75);
INSERT INTO TBL_MEMBER(MEMBER_ID ,CREATE_DATE ,UPDATE_DATE ,AGE ,BODY_PROFILE_URL ,EMAIL ,EXEC_STATUS ,PARTNER_FIND_STATUS ,HEIGHT ,MBTI ,NICKNAME ,PROFILE_URL ,SEX ,WEIGHT) VALUES (24,CURRENT_TIMESTAMP(), NULL, 28, 'body_profile_url','test5@naver.com','I','Y',188,  'ISFJ','테스터5','profile_url','M',77);

INSERT INTO TBL_MEMBER_HEALTH (ID ,BENCH_PRESS ,DEADLIFT ,SQUAT ,DIV ,EXEC_AREA ,FRI ,MON ,SAT ,SUN ,THU ,TUE ,WED ,EXEC_END_TIME ,EXEC_STT_TIME ,ADDRESS ,PLACE_NAME ,X ,Y ,HEALTH_MOTTO ,SPCL_NOTE ,MEMBER_ID ) VALUES (1,96,0,100,'THREE',NULL,true, true, false, false, true, true, true, '20:30:00','19:00:00',NULL,NULL,NULL,NULL,'안다치게 하자', '데드 리프트는 못합니다.', 1);
INSERT INTO TBL_MEMBER_HEALTH (ID ,BENCH_PRESS ,DEADLIFT ,SQUAT ,DIV ,EXEC_AREA ,FRI ,MON ,SAT ,SUN ,THU ,TUE ,WED ,EXEC_END_TIME ,EXEC_STT_TIME ,ADDRESS ,PLACE_NAME ,X ,Y ,HEALTH_MOTTO ,SPCL_NOTE ,MEMBER_ID ) VALUES (2,70,70,50,'TWO',NULL,true, true, false, false, true, true, true, '21:00:00','20:00:00',NULL,NULL,NULL,NULL,'하고죽자', NULL, 20);
INSERT INTO TBL_MEMBER_HEALTH (ID ,BENCH_PRESS ,DEADLIFT ,SQUAT ,DIV ,EXEC_AREA ,FRI ,MON ,SAT ,SUN ,THU ,TUE ,WED ,EXEC_END_TIME ,EXEC_STT_TIME ,ADDRESS ,PLACE_NAME ,X ,Y ,HEALTH_MOTTO ,SPCL_NOTE ,MEMBER_ID ) VALUES (3,84,88,120,NULL,NULL,true, true, false, false, true, true, true, '07:00:00','08:00:00',NULL,NULL,NULL,NULL,'일찍일어나는 새가 강하다', '아침운동만 합니다.', 21);
INSERT INTO TBL_MEMBER_HEALTH (ID ,BENCH_PRESS ,DEADLIFT ,SQUAT ,DIV ,EXEC_AREA ,FRI ,MON ,SAT ,SUN ,THU ,TUE ,WED ,EXEC_END_TIME ,EXEC_STT_TIME ,ADDRESS ,PLACE_NAME ,X ,Y ,HEALTH_MOTTO ,SPCL_NOTE ,MEMBER_ID ) VALUES (4,150,200,230,'THREE',NULL,true, true, true, true, true, true, true, '19:00:00','22:00:00',NULL,NULL,NULL,NULL,'근성장은 멈추지않는다.', '3대 이상만 구합니다.', 22);
INSERT INTO TBL_MEMBER_HEALTH (ID ,BENCH_PRESS ,DEADLIFT ,SQUAT ,DIV ,EXEC_AREA ,FRI ,MON ,SAT ,SUN ,THU ,TUE ,WED ,EXEC_END_TIME ,EXEC_STT_TIME ,ADDRESS ,PLACE_NAME ,X ,Y ,HEALTH_MOTTO ,SPCL_NOTE ,MEMBER_ID ) VALUES (5,90,90,100,'THREE',NULL,true, true, false, false, true, true, true, '19:00:00','20:00:00',NULL,NULL,NULL,NULL,'3대 300도전중', '헬린이분들 같이해요', 23);
INSERT INTO TBL_MEMBER_HEALTH (ID ,BENCH_PRESS ,DEADLIFT ,SQUAT ,DIV ,EXEC_AREA ,FRI ,MON ,SAT ,SUN ,THU ,TUE ,WED ,EXEC_END_TIME ,EXEC_STT_TIME ,ADDRESS ,PLACE_NAME ,X ,Y ,HEALTH_MOTTO ,SPCL_NOTE ,MEMBER_ID ) VALUES (6,90,100,100,'THREE',NULL,true, true, false, false, true, true, true, '19:00:00','20:00:00',NULL,NULL,NULL,NULL,'강한사람', null, 24);

INSERT INTO TBL_PARTNER (ID ,CREATE_DATE ,UPDATE_DATE ,MEMBER_ID ,PARTNER_ID ) VALUES (1, CURRENT_TIMESTAMP(), NULL, 1, 20);
INSERT INTO TBL_PARTNER (ID ,CREATE_DATE ,UPDATE_DATE ,MEMBER_ID ,PARTNER_ID ) VALUES (2, CURRENT_TIMESTAMP(), NULL, 1, 21);

INSERT INTO TBL_PARTNER_REQUEST (ID ,RESPONSE_STATUS ,FROM_MEMBER_ID ,TO_MEMBER_ID ) VALUES(1, 'W', 1, 22);
INSERT INTO TBL_PARTNER_REQUEST (ID ,RESPONSE_STATUS ,FROM_MEMBER_ID ,TO_MEMBER_ID ) VALUES(2, 'Y', 1, 20);
INSERT INTO TBL_PARTNER_REQUEST (ID ,RESPONSE_STATUS ,FROM_MEMBER_ID ,TO_MEMBER_ID ) VALUES(3, 'Y', 1, 21);