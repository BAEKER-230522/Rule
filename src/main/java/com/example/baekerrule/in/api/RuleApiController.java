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
import com.example.baekerrule.exception.RsData;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
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
    @PutMapping("/v1/{id}")
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
    public RsData<ModifyRuleResponse> updateRule(@PathVariable("id") Long id, @RequestBody Map<String, String> updates) {
        Rule rule = ruleService.getRule(id).getData();
        String name = updates.get("name");
        String about = updates.get("about");
        String strXp = updates.get("xp");
        String  strCount = updates.get("count");
        String provider = updates.get("provider");
        String difficulty = updates.get("difficulty");

        RuleForm ruleForm = new RuleForm();
        ruleService.setForm(id, ruleForm);
        if (name != null) {
            ruleForm.setName(name);
        }
        if (about != null) {
            ruleForm.setAbout(about);
        }
        if (strXp != null) {
            try {
                Integer xp = Integer.parseInt(strXp);
                ruleForm.setXp(xp.toString());
            } catch (NumberFormatException e) {
                throw new NumberInputException("xp는 숫자로 입력해주세요");
            }
        }
        if (strCount != null) {
            try {
                Integer count = Integer.parseInt(strCount);
                ruleForm.setCount(count.toString());
            } catch (NumberFormatException e) {
                throw new NumberInputException("Count는 숫자로 입력해주세요");
            }
        }
        if (provider != null) {
            ruleForm.setProvider(provider);
        }
        if (difficulty != null) {
            ruleForm.setDifficulty(difficulty);
        }
        ruleService.modify(id, ruleForm);
        return RsData.of("S-1", "수정 완료", new ModifyRuleResponse(rule));
    }


    /**
     * 전체 리스트 조회
     * return:
     * List<name, about, xp, count, provider, difficulty>
     */
    //모든 항목 조회
    @GetMapping("/v1/search")
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
