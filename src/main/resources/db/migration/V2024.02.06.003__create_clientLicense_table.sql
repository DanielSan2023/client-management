CREATE TABLE IF NOT EXISTS client_License
(
    client_info_id BIGINT   NOT NULL,
    license_id    BIGINT   NOT NULL,
    START_DATE    DATETIME NOT NULL,
    CONSTRAINT pk_client_license PRIMARY KEY (client_info_id, license_id)
);
