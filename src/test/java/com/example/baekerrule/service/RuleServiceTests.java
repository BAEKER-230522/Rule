package com.example.baekerrule.service;

import com.example.baekerrule.domain.Entity.Rule;
import com.example.baekerrule.domain.RuleService;
import com.example.baekerrule.domain.dto.RuleForm;
import com.example.baekerrule.error.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class RuleServiceTests {
    @Autowired
    private RuleService ruleService;

    @Test
    @DisplayName("룰생성")
    void createRuleTests() {
        // Rule 생성
        RuleForm ruleForm = new RuleForm("이름", "소개", "3", "1", "BOJ", "GOLD");
        Long ruleId = ruleService.create(ruleForm);

        Rule rule = ruleService.getRule(ruleId).getData();

        assertThat(rule.getId()).isEqualTo(ruleId);
        assertThat(rule.getName()).isEqualTo(ruleForm.getName());
    }

    @Test
    @DisplayName("룰 수정")
    void modifyRuleTests() {
        // 룰 생성
        RuleForm ruleForm = new RuleForm("이름", "소개", "3", "1", "BOJ", "GOLD");
        Long ruleId = ruleService.create(ruleForm);

        Rule rule = ruleService.getRule(ruleId).getData();
        // 수정
        RuleForm updateRule = new RuleForm("수정이름", "수정소개", "5", "2", "BOJ", "SILVER");
        ruleService.modify(rule.getId(), updateRule);
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
        Long ruleId = ruleService.create(ruleForm);

        Rule rule = ruleService.getRule(ruleId).getData();

        ruleService.delete(rule);

        try {
            Rule searchRule = ruleService.getRule(ruleId).getData();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
