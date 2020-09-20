CREATE DATA internet_shop
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(225) NOT NULL,
  `price` double NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`) )
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO products(name, price)
VALUES ('cherry', '33,5'),
        ('Gold_apple','20,57'),
        ('Melon', '6,29');

DROP DATA web_shop;
