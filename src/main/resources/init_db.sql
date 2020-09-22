CREATE DATA internet_shop
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(225) NOT NULL,
  `price` double NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`), )

INSERT INTO products(name, price)
VALUES ('cherry', '33,5'),
        ('Gold_apple','20,57'),
        ('Melon', '6,29');

