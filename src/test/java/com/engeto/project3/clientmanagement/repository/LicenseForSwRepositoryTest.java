package com.engeto.project3.clientmanagement.repository;

import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LicenseForSwRepositoryTest {
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
    void GIVEN_empty_db_WHEN_deleteByIdIn_THEN_db_empty() {
        // GIVEN
        assertThat(licenseForSwRepository.findAll()).isEmpty();
        List<Long> idsToDelete = Arrays.asList(1L, 1L);

        // WHEN
        licenseForSwRepository.deleteByIdIn(idsToDelete);

        // THEN
        assertThat(licenseForSwRepository.findAll()).isEmpty();
    }

    @Test
    void GIVEN_two_licenses_WHEN_deleteByIdIn_THEN_db_empty() {
        // GIVEN
        assertThat(licenseForSwRepository.findAll()).isEmpty();
        LicenseForSW license1 = new LicenseForSW(1L, "Windows", "windowsKey");
        LicenseForSW license2 = new LicenseForSW(2L, "Linux", "linuxKey");
        licenseForSwRepository.saveAll(Arrays.asList(license1, license2));

        List<Long> idsToDelete = Arrays.asList(license1.getId(), license2.getId());

        // WHEN
        licenseForSwRepository.deleteByIdIn(idsToDelete);

        // THEN
        assertThat(licenseForSwRepository.findAll()).isEmpty();
    }

    @Test
    void GIVEN_three_licenses_WHEN_deleteByIdIn_THEN_checked_db() {
        // GIVEN
        assertThat(licenseForSwRepository.findAll()).isEmpty();
        LicenseForSW license1 = new LicenseForSW(1L, "Windows", "windowsKey");
        LicenseForSW license2 = new LicenseForSW(2L, "Linux", "linuxKey");
        LicenseForSW license3 = new LicenseForSW(3L, "Android", "androidKey");
        licenseForSwRepository.saveAll(Arrays.asList(license1, license2, license3));

        List<Long> idsToDelete = Arrays.asList(license1.getId(), license2.getId());

        // WHEN
        assertThat(licenseForSwRepository.findAll()).hasSize(3);
        licenseForSwRepository.deleteByIdIn(idsToDelete);

        // THEN
        assertThat(licenseForSwRepository.findAll()).hasSize(1);
        assertThat(licenseForSwRepository.findAll().get(0)).isEqualTo(license3);
    }

    @Test
    void GIVEN_empty_db_WHEN_deleteById_THEN_empty_db() {
        //GIVEN
        assertThat(licenseForSwRepository.findAll()).isEmpty();
        Long id = 1L;
        //WHEN
        licenseForSwRepository.deleteById(id);

        //THEN
        assertThat(licenseForSwRepository.findAll()).isEmpty();
    }

    @Test
    void GIVEN_three_licenses_WHEN_deleteById_THEN_checked_db_if_deleted_correct_id() {
        // GIVEN
        assertThat(licenseForSwRepository.findAll()).isEmpty();
        LicenseForSW license1 = new LicenseForSW(1L, "Windows", "windowsKey");
        LicenseForSW license2 = new LicenseForSW(2L, "Linux", "linuxKey");
        LicenseForSW license3 = new LicenseForSW(3L, "Android", "androidKey");
        licenseForSwRepository.saveAll(Arrays.asList(license1, license2, license3));

        // WHEN
        assertThat(licenseForSwRepository.findAll()).hasSize(3);
        licenseForSwRepository.deleteById(license1.getId());

        // THEN
        assertThat(licenseForSwRepository.findAll()).hasSize(2);
        assertThat(licenseForSwRepository.findAll().get(0)).isEqualTo(license2);
        assertThat(licenseForSwRepository.findAll().get(1)).isEqualTo(license3);
    }

    @Test
    void GIVEN_three_licenses_invalid_Id_WHEN_deleteById_THEN_checked_db() {
        // GIVEN
        assertThat(licenseForSwRepository.findAll()).isEmpty();
        Long invalidId = 100L;

        LicenseForSW license1 = new LicenseForSW(1L, "Windows", "windowsKey");
        LicenseForSW license2 = new LicenseForSW(2L, "Linux", "linuxKey");
        LicenseForSW license3 = new LicenseForSW(3L, "Android", "androidKey");
        licenseForSwRepository.saveAll(Arrays.asList(license1, license2, license3));

        // WHEN
        assertThat(licenseForSwRepository.findAll()).hasSize(3);
        licenseForSwRepository.deleteById(invalidId);

        // THEN
        assertThat(licenseForSwRepository.findAll()).hasSize(3);
        assertThat(licenseForSwRepository.findAll().get(0)).isEqualTo(license1);
        assertThat(licenseForSwRepository.findAll().get(1)).isEqualTo(license2);
        assertThat(licenseForSwRepository.findAll().get(2)).isEqualTo(license3);
    }
}