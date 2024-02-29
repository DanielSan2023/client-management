INSERT INTO CLIENT_INFO (id, client_name, company_name, address, email)
VALUES (1, 'Neo', 'Warner Bros', 'RoadShow', 'neo@gmailcom');

INSERT INTO CLIENT_INFO (id, client_name, company_name, address, email)
VALUES (2, 'Trinity', 'Warner Bros', 'RoadShow', 'trinity@gmail.com');

INSERT INTO CLIENT_INFO (id, client_name, company_name, address, email)
VALUES (3, 'Morpheus', 'Warner Bros', 'RoadShow', 'morpheus@gmail.com');

INSERT INTO LICENSE_FOR_SW (id, software_name, license_key)
VALUES (1, 'Matrix', 'MatrixNoeKey');

INSERT INTO LICENSE_FOR_SW (id, software_name, license_key)
VALUES (2, 'Matrix', 'MartixTrinityKey');

INSERT INTO LICENSE_FOR_SW (id, software_name, license_key)
VALUES (3, 'Matrix', 'MatrixMorpheusKey');

INSERT INTO LICENSE_FOR_SW (id, software_name, license_key)
VALUES (4, 'PhoneBox', 'PhoneBoxNoeKey');

INSERT INTO LICENSE_FOR_SW (id, software_name, license_key)
VALUES (5, 'PhoneBox', 'PhoneBoxTrinityKey');

INSERT INTO LICENSE_FOR_SW (id, software_name, license_key)
VALUES (6, 'PhoneBox', 'PhoneBoxMatrixMorpheusKey');

INSERT INTO CLIENT_LICENSE (client_info_id, license_id, START_DATE)
VALUES (1, 1, '2024-02-06 12:00:00');

INSERT INTO CLIENT_LICENSE (client_info_id, license_id, START_DATE)
VALUES (2, 2, '2024-02-06 12:00:00');

INSERT INTO CLIENT_LICENSE (client_info_id, license_id, START_DATE)
VALUES (3, 3, '2024-02-06 12:00:00');

INSERT INTO CLIENT_LICENSE (client_info_id, license_id, START_DATE)
VALUES (1, 4, '2024-02-06 12:10:00');

INSERT INTO CLIENT_LICENSE (client_info_id, license_id, START_DATE)
VALUES (2, 5, '2024-02-06 12:10:00');

INSERT INTO CLIENT_LICENSE (client_info_id, license_id, START_DATE)
VALUES (3, 6, '2024-02-06 12:10:00');
