package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.Spreadsheet;
import com.fourbudget.spreadsheet.model.SpreadsheetFromUser;
import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.dto.SpreadsheetUserDTO;
import com.fourbudget.spreadsheet.service.SpreadsheetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.fourbudget.spreadsheet.util.AsJsonToStringUtil.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpreadsheetController.class)
@WebAppConfiguration
public class SpreadsheetControllerTest {

    @Autowired
    private SpreadsheetController spreadsheetController;

    @MockBean
    private SpreadsheetService spreadsheetService;

    private UserProfile userProfileTest;
    private Spreadsheet spreadsheetTest;
    private SpreadsheetUserDTO suDtoTest;
    private SpreadsheetFromUser suRelation;

    @BeforeEach
    public void init() {
        this.spreadsheetController = new SpreadsheetController(this.spreadsheetService);
        this.userProfileTest = new UserProfile("1", "test", "test@test");
        this.spreadsheetTest = new Spreadsheet(new Long(1), "test");
        this.suDtoTest = new SpreadsheetUserDTO("test", "1");
        this.suRelation = new SpreadsheetFromUser(userProfileTest, spreadsheetTest);
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void postSpreadsheet() throws Exception {

        given(this.spreadsheetService.registerSpreadsheetLink(suDtoTest)).willReturn(suRelation);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.spreadsheetController).build();
        MvcResult result = mockMvc.perform(post("/spreadsheet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(suDtoTest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(), asJsonString(suRelation));
    }

    @Test
    public void getSpreadsheetByUserId() throws Exception {

        given(this.spreadsheetService.findByUserId(this.userProfileTest.getId())).willReturn(this.spreadsheetTest);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.spreadsheetController).build();
        MvcResult result = mockMvc.perform(get("/spreadsheet/user/{userId}", this.userProfileTest.getId())
                .content(asJsonString(suDtoTest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(asJsonString(this.spreadsheetTest), result.getResponse().getContentAsString());
    }

}
