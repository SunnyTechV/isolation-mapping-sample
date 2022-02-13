package com.techvg.covid.care.isolation.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssessmentAnswerMapperTest {

    private AssessmentAnswerMapper assessmentAnswerMapper;

    @BeforeEach
    public void setUp() {
        assessmentAnswerMapper = new AssessmentAnswerMapperImpl();
    }
}
