package com.example.baekerrule.in.api;

import com.example.baekerrule.domain.Entity.Rule;
import com.example.baekerrule.domain.RuleService;
import com.example.baekerrule.domain.dto.RuleDto;
import com.example.baekerrule.domain.dto.RuleForm;
import com.example.baekerrule.domain.dto.request.CreateRuleRequest;
import com.example.baekerrule.domain.dto.request.ModifyRuleRequest;
import com.example.baekerrule.domain.dto.response.CreateRuleResponse;
import com.example.baekerrule.domain.dto.response.ModifyRuleResponse;
import com.example.baekerrule.exception.NumberInputException;
import com.example.baekerrule.domain.dto.RsData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rule")
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
    @PostMapping("/v1/rules")
    @Operation(summary = "규칙 생성", description = "규칙명(name), 소개(about), 경험치(xp), 문제풀이 수(count), oj사이트(provider), 난이도(difficulty)")
    public RsData<CreateRuleResponse> createRule(@RequestBody @Valid CreateRuleRequest request) {
//        RuleForm ruleForm = new RuleForm(request.getName(), request.getAbout(),
//                request.getXp().toString(),request.getCount().toString(),
//                request.getProvider(), request.getDifficulty());
        RuleForm ruleForm = new RuleForm(request);
        Long ruleId = ruleService.create(ruleForm);

        return RsData.of("S-1", "Rule 생성 완료", new CreateRuleResponse(ruleId));
    }

    /**
     * Put 수정
     * param:
     * name, about, xp
     * count, provider, difficulty
     * return:
     * name, about, xp
     * count, provider, difficulty
     */
    @PutMapping("/v1/{id}")
    @Operation(summary = "규칙전부수정", description = "이름(name), 설명(about), 경험치(xp)," +
            " 문제풀이수(count), oj 사이트(provider), 난이도(difficulty) 를 입력받아 수정")

    public RsData<ModifyRuleResponse> modifyRule(@PathVariable("id") Long id,
                                         @RequestBody @Valid ModifyRuleRequest request) {
        RuleForm ruleForm = new RuleForm(request.getName(), request.getAbout(),
                request.getXp().toString(),request.getCount().toString(), request.getProvider(), request.getDifficulty());

        ruleService.modify(id, ruleForm);
        Rule rule = ruleService.getRule(id).getData();
        return RsData.of("S-1", "수정 완료", new ModifyRuleResponse(rule));
    }

    /**
     * patch 수정
     * @param id
     * @param updates
     * @return patch
     */
    @PatchMapping("/v1/{id}")
    @Operation(summary = "규칙수정", description = "파라미터 id 를 입력받고 수정하고 싶은 Key:value 의 내용을 입력")
    public RsData<ModifyRuleResponse> updateRule(@PathVariable("id") Long id, @RequestBody Map<String, String> updates) {
        RuleForm ruleForm = ruleService.updateRule(id, updates);
        ruleService.modify(id, ruleForm);
        Rule rule = ruleService.getRule(id).getData();
        return RsData.of("S-1", "수정 완료", new ModifyRuleResponse(rule));
    }


    /**
     * 전체 리스트 조회
     * return:
     * List<name, about, xp, count, provider, difficulty>
     */
    //모든 항목 조회
    @GetMapping("/v1/search")
    @Operation(summary = "모든 규칙을 검색합니다.")
    public RsData<List<RuleDto>> searchRule() {
        List<Rule> rules = ruleService.getRuleList();
        List<RuleDto> collect = rules.stream()
                .map(RuleDto::new)
                .toList();
        return new RsData<>("S-1","Rule 목록" ,collect);
    }

    /**
     *
     * @param id
     * @return
     * name, about, xp, count, provider, difficulty
     */
    @GetMapping("/v1/search/{id}")
    @Operation(summary = "규칙 1개를 검색합니다.")
    public RsData<RuleDto> searchIdRule(@PathVariable Long id) {
        Rule rule = ruleService.getRule(id).getData();
        return new RsData<>("S-1",String.format("%d 번 아이디 정보", id), new RuleDto(rule));
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
