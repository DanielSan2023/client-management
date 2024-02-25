package com.engeto.project3.clientmanagement.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientLicenseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("should retrieve single client by name")
    @Test
    public void testRetrievingOfSingleClient() throws Exception {
        mockMvc.perform(get("/client/Neo")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName", is("Neo")));
    }

    @DisplayName("should retrieve all active clientLicenses")
    @Test
    public void testRetrievingOfActiveLicenses() throws Exception {
        mockMvc.perform(get("/license/active")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(6)));
    }

    @DisplayName("should retrieve all active license grouped by client")
    @Test
    public void testRetrievingOfActiveLicenseGroupedByClient() throws Exception {
        mockMvc.perform(get("/license/active/Neo")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Neo")))
                .andExpect(jsonPath("$[0].softwareName", is("Matrix")))
                .andExpect(jsonPath("$[0].licenseKey", is("MatrixNoeKey")))
                .andExpect(jsonPath("$[0].active", is(true)))
                .andExpect(jsonPath("$[1].name", is("Neo")))
                .andExpect(jsonPath("$[1].softwareName", is("PhoneBox")))
                .andExpect(jsonPath("$[1].licenseKey", is("PhoneBoxNoeKey")))
                .andExpect(jsonPath("$[1].active", is(true)));
    }
}
