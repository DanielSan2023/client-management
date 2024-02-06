CREATE TABLE IF NOT EXISTS Client_Info
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    clientName  VARCHAR(255),
    companyName VARCHAR(255),
    address     VARCHAR(255),
    CONSTRAINT pk_clientInfo PRIMARY KEY (id)
);
