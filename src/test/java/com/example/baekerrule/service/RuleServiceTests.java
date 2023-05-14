package com.example.baekerrule.service;

import com.example.baekerrule.domain.Entity.Rule;
import com.example.baekerrule.domain.RuleService;
import com.example.baekerrule.domain.dto.RuleForm;
import com.example.baekerrule.exception.NotFoundException;
import com.example.baekerrule.out.RuleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

        Rule rule = ruleService.getRule(ruleId);

        assertThat(rule.getId()).isEqualTo(ruleId);
        assertThat(rule.getName()).isEqualTo(ruleForm.getName());
    }

    @Test
    @DisplayName("룰 수정")
    void modifyRuleTests() {
        // 룰 생성
        RuleForm ruleForm = new RuleForm("이름", "소개", "3", "1", "BOJ", "GOLD");
        Long ruleId = ruleService.create(ruleForm);

        Rule rule = ruleService.getRule(ruleId);
        // 수정
        RuleForm updateRule = new RuleForm("수정이름", "수정소개", "5", "2", "BOJ", "SILVER");
        ruleService.modify(rule.getId(), updateRule);
        assertThat(rule.getName()).isEqualTo("수정이름");
        assertThat(rule.getId()).isEqualTo(ruleId);
        assertThat(rule.getProvider()).isEqualTo("BOJ");
    }

    @Test
    @DisplayName("삭제")
    void deleteRuleTests() {
        RuleForm ruleForm = new RuleForm("이름", "소개", "3", "1", "BOJ", "GOLD");
        Long ruleId = ruleService.create(ruleForm);

        Rule rule = ruleService.getRule(ruleId);

        ruleService.delete(rule);

        try {
            Rule searchRule = ruleService.getRule(ruleId);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
