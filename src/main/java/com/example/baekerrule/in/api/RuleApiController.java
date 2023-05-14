package com.example.baekerrule.in.api;

import com.example.baekerrule.domain.Entity.Rule;
import com.example.baekerrule.domain.RuleService;
import com.example.baekerrule.domain.dto.RuleDto;
import com.example.baekerrule.domain.dto.RuleForm;
import com.example.baekerrule.domain.dto.request.CreateRuleRequest;
import com.example.baekerrule.domain.dto.request.ModifyRuleRequest;
import com.example.baekerrule.domain.dto.response.CreateRuleResponse;
import com.example.baekerrule.domain.dto.response.ModifyRuleResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rule")
public class RuleApiController {

    private final RuleService ruleService;

    /**
     * 생성
     * param
     * name, about, xp,
     * count, provider, difficulty
     */
    @PostMapping("/rules")
    public CreateRuleResponse createRule(@RequestBody @Valid CreateRuleRequest request) {
        RuleForm ruleForm = new RuleForm(request.getName(), request.getAbout(), request.getXp().toString(),request.getCount().toString(), request.getProvider(), request.getDifficulty());
        Long ruleId = ruleService.create(ruleForm);

        return new CreateRuleResponse(ruleId);
    }

    /**
     * 수정
     * param
     * name, about, xp
     * count, provider, difficulty
     */
    @PutMapping("/{id}")
    public ModifyRuleResponse modifyRule(@PathVariable("id") Long id,
                                         @RequestBody @Valid ModifyRuleRequest request) {
        RuleForm ruleForm = new RuleForm(request.getName(), request.getAbout(), request.getXp().toString(),request.getCount().toString(), request.getProvider(), request.getDifficulty());
        ruleService.modify(id, ruleForm);
        Rule rule = ruleService.getRule(id);

        return new ModifyRuleResponse(rule.getName(), rule.getAbout(), rule.getXp(),rule.getCount() ,rule.getProvider() ,rule.getDifficulty());
    }

    /**
     * 조회
     */
    //모든 항목 조회
    @GetMapping("/search")
    public Result searchRule() {
        List<Rule> rules = ruleService.getRuleList();
        List<RuleDto> collect = rules.stream()
                .map(m -> new RuleDto(m.getName(), m.getAbout(), m.getXp(),m.getCount() ,m.getProvider(), m.getDifficulty()))
                .toList();
        return new Result(collect);
    }

    // 1개 조회
//    @GetMapping("/search/{id}")
//    public Result searchIdRule(@PathVariable Long id) {
//        Rule rule = ruleService.getRule(id).getData();
//        return new Result()
//    }

    /**
     * DTO Request
     */
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
