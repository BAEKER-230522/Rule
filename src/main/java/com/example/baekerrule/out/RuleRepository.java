package com.example.baekerrule.out;

import com.example.baekerrule.domain.Entity.Rule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RuleRepository extends JpaRepository<Rule, Long> {
    Page<Rule> findByNameContaining(String keyword,Pageable pageable);

    Optional<Rule> findByName(String name);
}
