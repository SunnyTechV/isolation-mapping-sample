package com.techvg.covid.care.isolation.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestionsOptionsMapperTest {

    private QuestionsOptionsMapper questionsOptionsMapper;

    @BeforeEach
    public void setUp() {
        questionsOptionsMapper = new QuestionsOptionsMapperImpl();
    }
}
