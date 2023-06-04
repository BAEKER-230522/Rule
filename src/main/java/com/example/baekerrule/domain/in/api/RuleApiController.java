package com.example.baekerrule.domain.in.api;

import com.example.baekerrule.domain.Entity.Rule;
import com.example.baekerrule.domain.RuleService;
import com.example.baekerrule.domain.dto.RuleDto;
import com.example.baekerrule.domain.dto.RuleForm;
import com.example.baekerrule.domain.dto.request.CreateRuleRequest;
import com.example.baekerrule.domain.dto.request.Keyword;
import com.example.baekerrule.domain.dto.request.ModifyRuleRequest;
import com.example.baekerrule.domain.dto.response.CreateRuleResponse;
import com.example.baekerrule.domain.dto.response.ModifyRuleResponse;
import com.example.baekerrule.exception.NotFoundException;
import com.example.baekerrule.exception.NumberInputException;
import com.example.baekerrule.domain.dto.RsData;
import com.example.baekerrule.exception.controller.ExceptionController;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rule")
public class RuleApiController {


    private final RuleService ruleService;

    /**
     * 삭제, 수정 과 같은 요청은 admin 확인 필요함
     */





    /**
     * 생성
     * param:
     * name, about, xp,
     * count, provider, difficulty
     * return:
     * Long id
     */
    @PostMapping("/v1/rules")
    @Operation(summary = "규칙 생성", description = "규칙명(name), 소개(about), 경험치(xp), 문제풀이 수(count), oj사이트(provider), 난이도(difficulty)", tags = "생성")
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
    @PutMapping("/v1/{ruleid}")
    @Operation(summary = "규칙전부수정", description = "이름(name), 설명(about), 경험치(xp)," +
            " 문제풀이수(count), oj 사이트(provider), 난이도(difficulty) 를 입력받아 수정", tags = "수정")
    public RsData<ModifyRuleResponse> modifyRule(@Parameter(description = "수정하고싶은 RuleId", in = ParameterIn.PATH) @PathVariable("ruleid") Long ruleid,
                                         @RequestBody @Valid ModifyRuleRequest request) {
        RuleForm ruleForm = new RuleForm(request.getName(), request.getAbout(),
                request.getXp().toString(),request.getCount().toString(), request.getProvider(), request.getDifficulty());

        ruleService.modify(ruleid, ruleForm);
        Rule rule = ruleService.getRule(ruleid).getData();
        return RsData.of("S-1", "수정 완료", new ModifyRuleResponse(rule));
    }

    /**
     * patch 수정
     * @param ruleid
     * @param updates
     * @return patch
     */
    @PatchMapping("/v1/{ruleid}")
    @Operation(summary = "규칙수정", description = "파라미터 id 를 입력받고 수정하고 싶은 Key:value 의 내용을 입력", tags = "수정")
    public RsData<ModifyRuleResponse> updateRule(@Parameter(description = "RuleId", in = ParameterIn.PATH) @PathVariable("ruleid") Long ruleid
                                                , @RequestBody Map<String, String> updates) {

        RuleForm ruleForm = ruleService.updateRule(ruleid, updates);
        ruleService.modify(ruleid, ruleForm);
        Rule rule = ruleService.getRule(ruleid).getData();
        return RsData.of("S-1", "수정 완료", new ModifyRuleResponse(rule));
    }


    /**
     * 전체 리스트 조회
     * return:
     * List<name, about, xp, count, provider, difficulty>
     */
    @GetMapping("/v1/search")
    @Operation(summary = "모든 규칙을 검색합니다.", tags = "조회")
    public RsData<List<RuleDto>> searchRule() {
        List<Rule> rules = ruleService.getRuleList();
        List<RuleDto> collect = rules.stream()
                .map(RuleDto::new)
                .toList();
        return new RsData<>("S-1","Rule 목록" ,collect);
    }

    // 수정 필요함 
    @GetMapping("/v1/list")
    @Operation(summary = "페이징 되어있는 리스트입니다.", description = "페이징은 10개씩 되어있습니다.")
    @Parameters({
            @Parameter(name = "page", description = "int 타입의 페이지 default=0", example = "0"),
            @Parameter(name = "keyword", description = "검색할때 사용하는 키워드 제목에 포함되어있는 내용", example = "이름")
    })
    public RsData<Page<Rule>> list(@RequestParam(value = "page", defaultValue = "0") int page
            , @RequestParam(required = false, value = "keyword", defaultValue = "") String keyword) {

        Page<Rule> paging;
        if (keyword.equals("")) {
            paging = ruleService.getList(page);
        } else {
            paging = ruleService.getList(keyword, page);
        }
        return RsData.successOf(paging);
    }

    /**
     *
     * @param ruleid
     * @return
     * name, about, xp, count, provider, difficulty
     */
    @GetMapping("/v1/search/{ruleid}")
    @Operation(summary = "규칙 1개 상세조회", tags = "조회")
    public RsData<RuleDto> searchIdRule(@Parameter(description = "RuleId 입력", example = "1")@PathVariable Long ruleid) {
        Rule rule = ruleService.getRule(ruleid).getData();
        return new RsData<>("S-1",String.format("%d 번 아이디 정보", ruleid), new RuleDto(rule));
    }

    /**
     * 삭제 요청
     * @param ruleid
     * @return
     */
    @DeleteMapping("/v1/rules/{ruleid}")
    public RsData deleteRule(@Parameter(description = "삭제하고싶은 RuleId 입력")@PathVariable("ruleid") Long ruleid) {
        try {
            Rule rule = ruleService.getRule(ruleid).getData();
            ruleService.delete(rule);
        } catch (NotFoundException e) {
            return RsData.of("F-1", String.format("%d 번 아이디가 없습니다.", ruleid),e.getMessage());
        }
        return RsData.of("S-1", String.format("%d 번 아이디가 삭제되었습니다", ruleid));
    }

}
