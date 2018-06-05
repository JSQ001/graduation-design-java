INSERT INTO tb_user VALUES (1,1,'admin','jsq','man' ,'/images/user.png','18797593461',1,1,now(),now(),'1001');
INSERT INTO tb_authority VALUES (1,'管理员');
INSERT INTO tb_authority VALUES (2,'教师');
INSERT INTO tb_authority VALUES (3,'学生');
INSERT INTO tb_user_authorities VALUES (1,1,1);
INSERT INTO tb_user_authorities VALUES (2,1,2);
INSERT INTO tb_user_authorities VALUES (3,1,3);