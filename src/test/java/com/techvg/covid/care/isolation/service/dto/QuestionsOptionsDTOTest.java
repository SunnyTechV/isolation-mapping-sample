package com.techvg.covid.care.isolation.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.isolation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuestionsOptionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionsOptionsDTO.class);
        QuestionsOptionsDTO questionsOptionsDTO1 = new QuestionsOptionsDTO();
        questionsOptionsDTO1.setId(1L);
        QuestionsOptionsDTO questionsOptionsDTO2 = new QuestionsOptionsDTO();
        assertThat(questionsOptionsDTO1).isNotEqualTo(questionsOptionsDTO2);
        questionsOptionsDTO2.setId(questionsOptionsDTO1.getId());
        assertThat(questionsOptionsDTO1).isEqualTo(questionsOptionsDTO2);
        questionsOptionsDTO2.setId(2L);
        assertThat(questionsOptionsDTO1).isNotEqualTo(questionsOptionsDTO2);
        questionsOptionsDTO1.setId(null);
        assertThat(questionsOptionsDTO1).isNotEqualTo(questionsOptionsDTO2);
    }
}
