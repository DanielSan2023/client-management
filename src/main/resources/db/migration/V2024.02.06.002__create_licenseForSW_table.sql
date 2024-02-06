CREATE TABLE IF NOT EXISTS LicenseForSW
(
    id           BIGINT NOT NULL AUTO_INCREMENT,
    softwareName VARCHAR(255),
    licenseKey   VARCHAR(255),
    CONSTRAINT pk_license_for_sw PRIMARY KEY (id)
);
