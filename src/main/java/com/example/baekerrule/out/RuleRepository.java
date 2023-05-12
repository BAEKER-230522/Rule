package com.example.baekerrule.out;

import com.example.baekerrule.domain.Entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleRepository extends JpaRepository<Rule, Long> {
    List<Rule> findByNameContaining(String keyword);
}
