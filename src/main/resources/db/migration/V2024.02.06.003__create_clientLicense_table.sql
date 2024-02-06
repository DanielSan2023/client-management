CREATE TABLE IF NOT EXISTS Client_License
(
    ClientInfo_id BIGINT   NOT NULL,
    License_id    BIGINT   NOT NULL,
    START_DATE    DATETIME NOT NULL,
    CONSTRAINT pk_client_license PRIMARY KEY (ClientInfo_id, License_id)
);
