package com.example.baekerrule.domain;

import com.example.baekerrule.domain.Entity.Rule;
import com.example.baekerrule.domain.dto.RuleForm;
import com.example.baekerrule.exception.NotFoundException;
import com.example.baekerrule.exception.RsData;
import com.example.baekerrule.out.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RuleService {

    private final RuleRepository ruleRepository;

    /**
     * 생성
     */
    @Transactional
    public Long create(RuleForm ruleForm) {
        Rule rule = Rule.builder()
                .name(ruleForm.getName())
                .about(ruleForm.getAbout())
                .xp(Integer.parseInt(ruleForm.getXp()))
                .count(Integer.parseInt(ruleForm.getCount()))
                .provider(ruleForm.getProvider())
                .difficulty(ruleForm.getDifficulty())
                .build();
        ruleRepository.save(rule);
        return rule.getId();
    }

    /**
     * 수정
     * setForm 은 기존 작성되어있는 내용을 넣어주는 메서드
     */

    @Transactional
    public void modify(Long ruleId, RuleForm ruleForm) {
        Rule rule = getRule(ruleId).getData();

        Rule rule1 = rule.toBuilder()
                .name(ruleForm.getName())
                .about(ruleForm.getAbout())
                .provider(ruleForm.getProvider())
                .xp(Integer.parseInt(ruleForm.getXp()))
                .difficulty(ruleForm.getDifficulty())
                .build();
        ruleRepository.save(rule1);
    }

    public void setForm(Long ruleId, RuleForm ruleForm) {
        Rule rule = getRule(ruleId).getData();
        ruleForm.setName(rule.getName());
        ruleForm.setAbout(rule.getAbout());
        ruleForm.setXp(rule.getXp().toString());
        ruleForm.setCount(rule.getCount().toString());
        ruleForm.setProvider(rule.getProvider());
        ruleForm.setDifficulty(rule.getDifficulty());
    }

    /**
     * 조회
     */

    public RsData<Rule> getRule(Long id) {
        Optional<Rule> rule = ruleRepository.findById(id);
        if (rule.isEmpty()) {
            throw new NotFoundException("찾을 수 없습니다");
        }
        return RsData.successOf(rule.get());
    }

    public RsData<Rule> getRule(String name) {
        Optional<Rule> rule = ruleRepository.findByName(name);
        if (rule.isEmpty()) {
            throw new NotFoundException("찾을 수 없습니다");
        }
        return RsData.of("S-1", "조회 성공", rule.get());
    }

    public List<Rule> getRuleList() {
        return ruleRepository.findAll();
    }

    public Page<Rule> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return ruleRepository.findAll(pageable);
    }

    public Page<Rule> getList(String keyword, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return ruleRepository.findByNameContaining(keyword, pageable);
    }

    /**
     * 삭제
     */
    @Transactional
    public void delete(Rule rule) {
        this.ruleRepository.delete(rule);
    }

}
