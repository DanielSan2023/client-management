CREATE TABLE IF NOT EXISTS license_For_SW
(
    id           BIGINT NOT NULL AUTO_INCREMENT,
    software_name VARCHAR(255),
    license_key   VARCHAR(255),
    CONSTRAINT pk_license_for_sw PRIMARY KEY (id)
);
