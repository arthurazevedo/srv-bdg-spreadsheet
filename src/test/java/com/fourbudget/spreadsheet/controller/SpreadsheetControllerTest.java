package com.fourbudget.spreadsheet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourbudget.spreadsheet.model.Spreadsheet;
import com.fourbudget.spreadsheet.model.SpreadsheetFromUser;
import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.dto.SpreadsheetUserDTO;
import com.fourbudget.spreadsheet.service.SpreadsheetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SpreadsheetController.class)
@ActiveProfiles("test")
public class SpreadsheetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpreadsheetService spreadsheetService;

    @Test
    void contextLoads() {
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

    @Test
    void postSpreadsheet() throws Exception {
        UserProfile userProfileTest = new UserProfile(new Long(1), "test", "test@test");
        Spreadsheet spreadsheetTest = new Spreadsheet(new Long(1), "test");
        SpreadsheetUserDTO suDtoTest = new SpreadsheetUserDTO("test", new Long(1));
        SpreadsheetFromUser suRelation = new SpreadsheetFromUser(userProfileTest, spreadsheetTest);

        given(this.spreadsheetService.registerSpreadsheetLink(any(SpreadsheetUserDTO.class))).willReturn(suRelation);

        MvcResult result = this.mockMvc.perform(post("/spreadsheet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(suDtoTest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(), asJsonString(suRelation));
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
