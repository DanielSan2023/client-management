CREATE TABLE IF NOT EXISTS client_Info
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    client_name  VARCHAR(255),
    company_name VARCHAR(255),
    address     VARCHAR(255),
    CONSTRAINT pk_client_info PRIMARY KEY (id)
);
