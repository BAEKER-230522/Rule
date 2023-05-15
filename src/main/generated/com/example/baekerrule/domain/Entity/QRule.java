package com.example.baekerrule.domain.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRule is a Querydsl query type for Rule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRule extends EntityPathBase<Rule> {

    private static final long serialVersionUID = 1643508524L;

    public static final QRule rule = new QRule("rule");

    public final StringPath about = createString("about");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath difficulty = createString("difficulty");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath provider = createString("provider");

    public final NumberPath<Integer> xp = createNumber("xp", Integer.class);

    public QRule(String variable) {
        super(Rule.class, forVariable(variable));
    }

    public QRule(Path<? extends Rule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRule(PathMetadata metadata) {
        super(Rule.class, metadata);
    }

}

