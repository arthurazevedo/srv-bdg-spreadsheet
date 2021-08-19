package com.fourbudget.spreadsheet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourbudget.spreadsheet.SpreadsheetApplication;
import com.fourbudget.spreadsheet.domain.Spreadsheet;
import com.fourbudget.spreadsheet.domain.SpreadsheetFromUser;
import com.fourbudget.spreadsheet.domain.UserProfile;
import com.fourbudget.spreadsheet.dto.SpreadsheetUserDTO;
import com.fourbudget.spreadsheet.repository.SpreadsheetFromUserRepository;
import com.fourbudget.spreadsheet.repository.SpreadsheetRepository;
import com.fourbudget.spreadsheet.repository.UserProfileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SpreadsheetApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class SpreadsheetControllerTest {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private SpreadsheetRepository spreadsheetRepository;
    @Autowired
    private SpreadsheetFromUserRepository spreadsheetFromUserRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void ue() {
        Assertions.assertTrue(true);
    }

    @Test
    public void endToEndTest() throws Exception {
        UserProfile userTest = new UserProfile(new Long(1), "Teste", "mm@test");
        Spreadsheet spreadsheetTest = new Spreadsheet("www.teste/");
        this.userProfileRepository.save(userTest);
        this.spreadsheetRepository.save(spreadsheetTest);
        SpreadsheetUserDTO suDTO = new SpreadsheetUserDTO(spreadsheetTest.getSpreadsheetLink(), userTest.getId());

        mockMvc.perform(MockMvcRequestBuilders.post("/spreadsheet")
                .content(asJsonString(suDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        Assertions.assertTrue(this.userProfileRepository.existsById(new Long(1)));
        Assertions.assertTrue(this.spreadsheetRepository.existsById(spreadsheetTest.getId()));
        SpreadsheetFromUser suRelation = this.spreadsheetFromUserRepository.findByUserProfileId(userTest.getId()).get();
        Assertions.assertEquals(suRelation.getUserProfile(), userTest);
        Assertions.assertEquals(suRelation.getSpreadsheet().getSpreadsheetLink(), spreadsheetTest.getSpreadsheetLink());

    }

    @Test
    public void healthTest()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/spreadsheet/health")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
