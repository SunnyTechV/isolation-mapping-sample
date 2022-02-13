package com.techvg.covid.care.isolation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.isolation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssessmentAnswerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssessmentAnswer.class);
        AssessmentAnswer assessmentAnswer1 = new AssessmentAnswer();
        assessmentAnswer1.setId(1L);
        AssessmentAnswer assessmentAnswer2 = new AssessmentAnswer();
        assessmentAnswer2.setId(assessmentAnswer1.getId());
        assertThat(assessmentAnswer1).isEqualTo(assessmentAnswer2);
        assessmentAnswer2.setId(2L);
        assertThat(assessmentAnswer1).isNotEqualTo(assessmentAnswer2);
        assessmentAnswer1.setId(null);
        assertThat(assessmentAnswer1).isNotEqualTo(assessmentAnswer2);
    }
}
