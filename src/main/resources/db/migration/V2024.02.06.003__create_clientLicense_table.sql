CREATE TABLE IF NOT EXISTS ClientLicense
(
    client_id  BIGINT   NOT NULL,
    license_id BIGINT   NOT NULL,
    START_DATE DATETIME NOT NULL,
    CONSTRAINT pk_client_license PRIMARY KEY (client_id, license_id)
);
