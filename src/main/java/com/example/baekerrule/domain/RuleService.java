package com.example.baekerrule.domain;

import com.example.baekerrule.domain.Entity.Rule;
import com.example.baekerrule.domain.dto.RsData;
import com.example.baekerrule.domain.dto.RuleForm;
import com.example.baekerrule.domain.dto.request.UpdateRequest;
import com.example.baekerrule.domain.repository.RuleRepository;
import com.example.baekerrule.error.exception.NotFoundException;
import com.example.baekerrule.error.exception.NumberInputException;
import lombok.RequiredArgsConstructor;
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
     * POST
     * create
     * @param ruleForm
     */
    @Transactional
    public Long create(RuleForm ruleForm) {
        Rule rule = Rule.createRule(ruleForm.getName(), ruleForm.getAbout(),
                ruleForm.getProvider(), Integer.parseInt(ruleForm.getXp()),
                Integer.parseInt(ruleForm.getCount()), ruleForm.getDifficulty());

        ruleRepository.save(rule);
        return rule.getId();
    }

    /**
     * PUT
     * @param ruleId, name, about, provider
     *                xp, count, difficulty
     */

    @Transactional
    public void modify(Long ruleId, RuleForm ruleForm) {
        Rule rule = getRule(ruleId).getData();

        Rule rule1 = rule.toBuilder()
                .name(ruleForm.getName())
                .about(ruleForm.getAbout())
                .provider(ruleForm.getProvider())
                .xp(Integer.parseInt(ruleForm.getXp()))
                .count(Integer.parseInt(ruleForm.getCount()))
                .difficulty(ruleForm.getDifficulty())
                .build();
        ruleRepository.save(rule1);
    }

    private void setForm(Long ruleId, RuleForm ruleForm) {
        Rule rule = getRule(ruleId).getData();
        ruleForm.setName(rule.getName());
        ruleForm.setAbout(rule.getAbout());
        ruleForm.setXp(rule.getXp().toString());
        ruleForm.setCount(rule.getCount().toString());
        ruleForm.setProvider(rule.getProvider());
        ruleForm.setDifficulty(rule.getDifficulty());
    }

    /**
     * PATCH
     * @param id
     * @param updates
     * @return
     */
    public RuleForm patchRule(Long id, UpdateRequest updates) {
        String name = updates.name();
        String about = updates.about();
        String strXp = updates.xp();
        String strCount = updates.count();
        String provider = updates.provider();
        String difficulty = updates.difficulty();

        RuleForm ruleForm = new RuleForm();
        setForm(id, ruleForm);
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
        return ruleForm;
    }

    /**
     * GET
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
     * DELETE
     * @param rule
     */
    @Transactional
    public void delete(Rule rule) {
        this.ruleRepository.delete(rule);
    }


}
