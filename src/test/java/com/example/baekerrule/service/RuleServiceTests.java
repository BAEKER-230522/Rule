package com.example.baekerrule.service;

import com.example.baekerrule.domain.Entity.Rule;
import com.example.baekerrule.domain.RuleService;
import com.example.baekerrule.domain.dto.RuleForm;
import com.example.baekerrule.error.exception.NotFoundException;
import com.example.baekerrule.error.exception.ValidException;
import com.example.baekerrule.global.util.JwtUtil;
import com.example.baekerrule.global.util.RedisUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static com.example.baekerrule.error.ErrorResponse.NOT_ADMIN_AUTHORITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@Transactional
public class RuleServiceTests {
    @Autowired
    RuleService ruleService;

    @MockBean
    RedisUtil redisUtil;
    @MockBean
    JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        // redisUtil
        when(redisUtil.getValue(any())).thenReturn("role_admin");
        // jwtUtil
    }

    @Test
    @DisplayName("룰생성")
    void createRuleTests() {
        // Rule 생성
        RuleForm ruleForm = new RuleForm("이름", "소개", "3", "1", "BOJ", "GOLD");
        Long ruleId = ruleService.create(ruleForm, 1L, "role_admin");

        Rule rule = ruleService.getRule(ruleId).getData();

        assertThat(rule.getId()).isEqualTo(ruleId);
        assertThat(rule.getName()).isEqualTo(ruleForm.getName());
    }

    @Test
    @DisplayName("룰 수정")
    void modifyRuleTests() {
        // 룰 생성
        RuleForm ruleForm = new RuleForm("이름", "소개", "3", "1", "BOJ", "GOLD");
        Long ruleId = ruleService.create(ruleForm, 1L, "role_admin");

        Rule rule = ruleService.getRule(ruleId).getData();
        // 수정
        RuleForm updateRule = new RuleForm("수정이름", "수정소개", "5", "2", "BOJ", "SILVER");
        ruleService.modify(rule.getId(), updateRule, 1L, "role_admin");
        Rule findRule = ruleService.getRule(ruleId).getData();

        assertThat(rule.getName()).isEqualTo("수정이름");
        assertThat(rule.getId()).isEqualTo(ruleId);
        assertThat(rule.getProvider()).isEqualTo("BOJ");
        assertThat(rule).hasSameHashCodeAs(findRule);
    }

    @Test
    @DisplayName("삭제")
    void deleteRuleTests() {
        RuleForm ruleForm = new RuleForm("이름", "소개", "3", "1", "BOJ", "GOLD");
        Long ruleId = ruleService.create(ruleForm, 1L, "role_admin");

        Rule rule = ruleService.getRule(ruleId).getData();

        ruleService.delete(rule, 1L, "");

        try {
            Rule searchRule = ruleService.getRule(ruleId).getData();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("룰 생성 관리자 권한이 없음")
    void createValidFailTest() {
        // Rule 생성
        RuleForm ruleForm = new RuleForm("이름", "소개", "3", "1", "BOJ", "GOLD");
        String msg = "";
        try {
            Long ruleId = ruleService.create(ruleForm, 1L, "role_user");
            Rule rule = ruleService.getRule(ruleId).getData();
        } catch (ValidException e) {
            msg = e.getMessage();
        }

        assertThat(msg).isEqualTo(NOT_ADMIN_AUTHORITY.getMessage());
    }
}
