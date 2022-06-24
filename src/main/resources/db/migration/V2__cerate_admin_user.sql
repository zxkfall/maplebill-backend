# password: P42F1_6r$2$711
# encode: $2a$10$we1KwoVzwkchAMfvRJ2NdurNk3.KzcnDcrEfrD17uT3itfnEaNVdG
INSERT INTO maple_bill.`user`
(`email`,
 `nickname`,
 `password`,
 `roles`,
 `create_time`,
 `update_time`)
VALUES ('1475795322@qq.com',
        'zxkfall',
        '$2a$10$we1KwoVzwkchAMfvRJ2NdurNk3.KzcnDcrEfrD17uT3itfnEaNVdG',
        'ROLE_ADMIN',
        now(),
        now());
