package com.techvg.covid.care.isolation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.isolation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IsolationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Isolation.class);
        Isolation isolation1 = new Isolation();
        isolation1.setId(1L);
        Isolation isolation2 = new Isolation();
        isolation2.setId(isolation1.getId());
        assertThat(isolation1).isEqualTo(isolation2);
        isolation2.setId(2L);
        assertThat(isolation1).isNotEqualTo(isolation2);
        isolation1.setId(null);
        assertThat(isolation1).isNotEqualTo(isolation2);
    }
}
