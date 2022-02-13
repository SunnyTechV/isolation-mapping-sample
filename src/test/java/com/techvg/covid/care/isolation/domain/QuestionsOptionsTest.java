package com.techvg.covid.care.isolation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.isolation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuestionsOptionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionsOptions.class);
        QuestionsOptions questionsOptions1 = new QuestionsOptions();
        questionsOptions1.setId(1L);
        QuestionsOptions questionsOptions2 = new QuestionsOptions();
        questionsOptions2.setId(questionsOptions1.getId());
        assertThat(questionsOptions1).isEqualTo(questionsOptions2);
        questionsOptions2.setId(2L);
        assertThat(questionsOptions1).isNotEqualTo(questionsOptions2);
        questionsOptions1.setId(null);
        assertThat(questionsOptions1).isNotEqualTo(questionsOptions2);
    }
}
