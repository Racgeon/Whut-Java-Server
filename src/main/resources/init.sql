DROP DATABASE IF EXISTS document;
CREATE DATABASE IF NOT EXISTS document;

USE document;
DROP TABLE IF EXISTS tb_user;

CREATE TABLE IF NOT EXISTS tb_user
(
    `id`       BIGINT(20) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `name`     VARCHAR(255)                                NOT NULL,
    `password` VARCHAR(100)                                NOT NULL,
    `role`     ENUM ('administrator','operator','browser') NOT NULL
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

INSERT INTO tb_user
VALUES (1, 'admin', '1234', 'administrator'),
       (null, 'op', '1234', 'operator'),
       (null, 'br', '1234', 'browser');

DROP TABLE IF EXISTS tb_doc;
CREATE TABLE IF NOT EXISTS tb_doc
(
    `id`          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `creator`     VARCHAR(255) NOT NULL,
    `timestamp`   DATETIME     DEFAULT CURRENT_TIMESTAMP,
    `description` VARCHAR(255) DEFAULT '',
    `filename`    VARCHAR(255)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;