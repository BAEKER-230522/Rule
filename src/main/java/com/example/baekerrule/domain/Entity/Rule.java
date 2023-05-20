package com.example.baekerrule.domain.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Rule {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifyDate;

    private String name;

    private String about;

    private String provider;

    private Integer xp;

    private Integer count;
    private String difficulty;

    public static Rule createRule(String name, String about, String provider, Integer xp, Integer count, String difficulty) {
        return builder()
                .name(name)
                .about(about)
                .provider(provider)
                .xp(xp)
                .count(count)
                .difficulty(difficulty).build();
    }
}

