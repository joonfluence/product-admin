-- schema.sql
CREATE TABLE IF NOT EXISTS brands
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS products
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id    BIGINT         NOT NULL,
    category_id BIGINT         NOT NULL,
    price       DECIMAL(10, 2) NOT NULL
);

-- 인덱스 생성
CREATE INDEX idx_product_price ON products (price);
CREATE INDEX idx_product_category ON products (category_id);
CREATE INDEX idx_product_brand_category ON products (brand_id, category_id);