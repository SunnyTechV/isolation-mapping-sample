package com.techvg.covid.care.isolation.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.isolation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssessmentAnswerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssessmentAnswerDTO.class);
        AssessmentAnswerDTO assessmentAnswerDTO1 = new AssessmentAnswerDTO();
        assessmentAnswerDTO1.setId(1L);
        AssessmentAnswerDTO assessmentAnswerDTO2 = new AssessmentAnswerDTO();
        assertThat(assessmentAnswerDTO1).isNotEqualTo(assessmentAnswerDTO2);
        assessmentAnswerDTO2.setId(assessmentAnswerDTO1.getId());
        assertThat(assessmentAnswerDTO1).isEqualTo(assessmentAnswerDTO2);
        assessmentAnswerDTO2.setId(2L);
        assertThat(assessmentAnswerDTO1).isNotEqualTo(assessmentAnswerDTO2);
        assessmentAnswerDTO1.setId(null);
        assertThat(assessmentAnswerDTO1).isNotEqualTo(assessmentAnswerDTO2);
    }
}
