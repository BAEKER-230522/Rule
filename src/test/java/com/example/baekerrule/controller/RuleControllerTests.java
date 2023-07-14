package com.example.baekerrule.controller;

import com.example.baekerrule.domain.RuleService;
import com.example.baekerrule.domain.controller.RuleApiController;
import com.example.baekerrule.domain.dto.RuleForm;
import com.example.baekerrule.domain.dto.request.CreateRuleRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RuleApiController.class)
@ExtendWith(SpringExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
public class RuleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleService ruleService;

    @Test
    @DisplayName("룰 생성 테스트")
    void createRuleTest() throws Exception {
        CreateRuleRequest createRuleRequest = new CreateRuleRequest("이름", "소개", 1, 1, "BOJ", "GOLD");

        String request = new ObjectMapper().writeValueAsString(createRuleRequest);
        ResultActions resultActions = mockMvc
                .perform(post("/api/rule/v1/rules")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request));

        resultActions
                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json"))
                .andExpect(jsonPath("$.resultCode").value("S-1"))
                .andExpect(jsonPath("$.msg").value("Rule 생성 완료"))
                .andDo(print());
    }

    @Test
    @DisplayName("전체 목록 검색 테스트")
    void ruleSearchTest() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/api/rule/v1/search"));


        for (int i = 0; i < 5; i++) {
            RuleForm ruleForm = new RuleForm("이름" + i, "소개" + i, "1", "1", "BOJ", "GOLD");
            ruleService.create(ruleForm);
        }

        resultActions
                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json"))
                .andExpect(jsonPath("$.resultCode").value("S-1"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이징 목록 검색 테스트")
    void rulePagingTest() {

    }
}



