package com.example.baekerrule.init;

import com.example.baekerrule.domain.RuleService;
import com.example.baekerrule.domain.dto.RuleForm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@Profile("dev")
@Configuration
@RequiredArgsConstructor
@Profile("dev")
public class Init {
    private final InitRule initRule;

    @PostConstruct
    public void init() {
        initRule.initData();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitRule {
        private final RuleService ruleService;

        public void initData() {


            for (int i = 0; i < 15; i++) {
                RuleForm ruleForm = new RuleForm("이름" + i, "소개" + i, i + "", i + "", "백준", "GOLD");
                ruleService.create(ruleForm);
            }
        }

    }

}
