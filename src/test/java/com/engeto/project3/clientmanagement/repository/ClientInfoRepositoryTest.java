package com.engeto.project3.clientmanagement.repository;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientInfoRepositoryTest {

    @Autowired
    ClientInfoRepository clientInfoRepository;

    @Autowired
    ClientLicenseRepository clientLicenseRepository;

    @Autowired
    LicenseForSwRepository licenseForSwRepository;

    @BeforeEach
    void deleteDb() {
        clientLicenseRepository.deleteAll();
        clientInfoRepository.deleteAll();
        licenseForSwRepository.deleteAll();
    }

    @Test
    void GIVEN_incorrect_name_WHEN_findByClientName_THEN_return_null() {
        //GIVEN
        assertThat(clientInfoRepository.findAll()).isEmpty();
        ClientInfo client = new ClientInfo("Britney", "IBM", "LA", "jacksson@gmail.com");
        String incName = "Trevor";
        clientInfoRepository.save(client);

        //WHEN
        ClientInfo returnedClient = clientInfoRepository.findByClientName(incName);

        //THEN
        assertThat(clientInfoRepository.findAll()).hasSize(1);
        assertThat(returnedClient).isNull();
    }

    @Test
    void GIVEN_client_name_WHEN_findByClientName_THEN_return_client_object() {
        //GIVEN
        assertThat(clientInfoRepository.findAll()).isEmpty();
        ClientInfo client = new ClientInfo("Jackson", "IBM", "LA", "jacksson@gmail.com");
        String correctName = "Jackson";
        clientInfoRepository.save(client);

        //WHEN
        ClientInfo returnedClient = clientInfoRepository.findByClientName(correctName);

        //THEN
        assertThat(clientInfoRepository.findAll()).hasSize(1);
        assertThat(returnedClient.getClientName()).isEqualTo(correctName);
    }

    @Test
    void GIVEN_empty_DB_WHEN_existsByClientNameIgnoreCase_THEN_false() {
        //GIVEN
        assertThat(clientInfoRepository.findAll()).isEmpty();

        //WHEN
        boolean exist = clientInfoRepository.existsByClientNameIgnoreCase("someName");

        //THEN
        assertThat(exist).isFalse();
    }

    @Test
    void GIVEN_one_client_DB_WHEN_existsByClientNameIgnoreCase_call_with_non_exist_name_THEN_false() {
        //GIVEN
        assertThat(clientInfoRepository.findAll()).isEmpty();
        ClientInfo client = new ClientInfo("Jackson", "IBM", "LA", "jacksson@gmail.com");
        String clientName = "Tina";
        clientInfoRepository.save(client);

        //WHEN
        boolean exist = clientInfoRepository.existsByClientNameIgnoreCase(clientName);

        //THEN
        assertThat(exist).isFalse();
    }

    @Test
    void GIVEN_one_client_in_DB_WHEN_existsByClientNameIgnoreCase_call_with_exist_name_THEN_true() {
        //GIVEN
        assertThat(clientInfoRepository.findAll()).isEmpty();
        ClientInfo client = new ClientInfo("Jackson", "IBM", "LA", "jacksson@gmail.com");
        String clientName = "Jackson";
        clientInfoRepository.save(client);

        //WHEN
        boolean exist = clientInfoRepository.existsByClientNameIgnoreCase(clientName);

        //THEN
        assertThat(exist).isTrue();
    }

    @Test
    void GIVEN_one_client_in_DB_WHEN_existsByClientNameIgnoreCase_call_with_upper_name_THEN_true() {
        //GIVEN
        assertThat(clientInfoRepository.findAll()).isEmpty();
        ClientInfo client = new ClientInfo("Jackson", "IBM", "LA", "jacksson@gmail.com");
        String clientName = "JACKSON";
        clientInfoRepository.save(client);

        //WHEN
        boolean exist = clientInfoRepository.existsByClientNameIgnoreCase(clientName);

        //THEN
        assertThat(exist).isTrue();
    }

    @Test
    void GIVEN_one_client_in_DB_WHEN_existsByClientNameIgnoreCase_call_with_lower_name_THEN_true() {
        //GIVEN
        assertThat(clientInfoRepository.findAll()).isEmpty();
        ClientInfo client = new ClientInfo("Jackson", "IBM", "LA", "jacksson@gmail.com");
        String clientName = "jackson";
        clientInfoRepository.save(client);

        //WHEN
        boolean exist = clientInfoRepository.existsByClientNameIgnoreCase(clientName);

        //THEN
        assertThat(exist).isTrue();
    }

    @Test
    void GIVEN_empty_db_WHEN_deleteByClientName_THEN_verify_check_db() {
        //GIVEN
        assertThat(clientInfoRepository.findAll()).isEmpty();

        //WHEN
        clientInfoRepository.deleteByClientName("someClientName");

        //THEN
        assertThat(clientInfoRepository.findAll()).isEmpty();
    }

    @Test
    void GIVEN_client_to_db_WHEN_deleteByClientName_THEN_verify_db_is_empty() {
        //GIVEN
        assertThat(clientInfoRepository.findAll()).isEmpty();
        ClientInfo client = new ClientInfo("Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        //WHEN
        assertThat(clientInfoRepository.findAll()).hasSize(1);
        clientInfoRepository.deleteByClientName(client.getClientName());

        //THEN
        assertThat(clientInfoRepository.findAll()).isEmpty();
    }

    @Test
    void GIVEN_two_clients_to_db_WHEN_deleteByClientName_THEN_verify_db_has_size_1() {
        //GIVEN
        assertThat(clientInfoRepository.findAll()).isEmpty();
        ClientInfo client1 = new ClientInfo("Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client1);
        ClientInfo client2 = new ClientInfo("Robbie", "Apple", "NY", "robbie@gmail.com");
        clientInfoRepository.save(client2);

        //WHEN
        assertThat(clientInfoRepository.findAll()).hasSize(2);
        clientInfoRepository.deleteByClientNameIgnoreCase(client1.getClientName());

        //THEN
        assertThat(clientInfoRepository.findAll()).containsOnly(client2);
    }
}
