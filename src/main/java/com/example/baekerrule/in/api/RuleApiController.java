package com.example.baekerrule.in.api;

import com.example.baekerrule.domain.Entity.Rule;
import com.example.baekerrule.domain.RuleService;
import com.example.baekerrule.domain.dto.RuleDto;
import com.example.baekerrule.domain.dto.RuleForm;
import com.example.baekerrule.domain.dto.request.CreateRuleRequest;
import com.example.baekerrule.domain.dto.request.ModifyRuleRequest;
import com.example.baekerrule.domain.dto.response.CreateRuleResponse;
import com.example.baekerrule.domain.dto.response.ModifyRuleResponse;
import com.example.baekerrule.exception.RsData;
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
     * param:
     * name, about, xp,
     * count, provider, difficulty
     * return:
     * Long id
     */
    @PostMapping("/rules")
    public RsData<CreateRuleResponse> createRule(@RequestBody @Valid CreateRuleRequest request) {
        RuleForm ruleForm = new RuleForm(request.getName(), request.getAbout(), request.getXp().toString(),request.getCount().toString(), request.getProvider(), request.getDifficulty());
        Long ruleId = ruleService.create(ruleForm);

        return RsData.of("S-1", "Rule 생성 완료", new CreateRuleResponse(ruleId));
    }

    /**
     * 수정
     * param:
     * name, about, xp
     * count, provider, difficulty
     * return:
     * name, about, xp
     * count, provider, difficulty
     */
    @PutMapping("/{id}")
    public RsData<ModifyRuleResponse> modifyRule(@PathVariable("id") Long id,
                                         @RequestBody @Valid ModifyRuleRequest request) {
        RuleForm ruleForm = new RuleForm(request.getName(), request.getAbout(),
                request.getXp().toString(),request.getCount().toString(), request.getProvider(), request.getDifficulty());

        ruleService.modify(id, ruleForm);
        Rule rule = ruleService.getRule(id).getData();
        return RsData.of("S-1", "수정 완료", new ModifyRuleResponse(rule.getName(), rule.getAbout(),
                rule.getXp(), rule.getCount(), rule.getProvider(), rule.getDifficulty()));

//        return new ModifyRuleResponse(rule.getName(), rule.getAbout(), rule.getXp(),rule.getCount() ,rule.getProvider() ,rule.getDifficulty());
    }

    /**
     * 전체 리스트 조회
     * return:
     * List<name, about, xp, count, provider, difficulty>
     */
    //모든 항목 조회
    @GetMapping("/search")
    public RsData<List<RuleDto>> searchRule() {
        List<Rule> rules = ruleService.getRuleList();
        List<RuleDto> collect = rules.stream()
                .map(m -> new RuleDto(m.getName(), m.getAbout(), m.getXp(),
                        m.getCount() ,m.getProvider(), m.getDifficulty()))
                .toList();
        return new RsData<>("S-1","Rule 목록" ,collect);
    }

    /**
     *
     * @param id
     * @return
     * name, about, xp, count, provider, difficulty
     */
    @GetMapping("/search/{id}")
    public RsData<RuleDto> searchIdRule(@PathVariable Long id) {
        Rule rule = ruleService.getRule(id).getData();
        return new RsData<>("S-1",String.format("%d 번 아이디 정보", id), new RuleDto(rule.getName(), rule.getAbout(), rule.getXp(), rule.getCount(), rule.getProvider(), rule.getDifficulty()));
    }

    /**
     * DTO Request
     */
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
