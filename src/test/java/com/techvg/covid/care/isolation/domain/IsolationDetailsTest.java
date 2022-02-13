package com.techvg.covid.care.isolation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.isolation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IsolationDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IsolationDetails.class);
        IsolationDetails isolationDetails1 = new IsolationDetails();
        isolationDetails1.setId(1L);
        IsolationDetails isolationDetails2 = new IsolationDetails();
        isolationDetails2.setId(isolationDetails1.getId());
        assertThat(isolationDetails1).isEqualTo(isolationDetails2);
        isolationDetails2.setId(2L);
        assertThat(isolationDetails1).isNotEqualTo(isolationDetails2);
        isolationDetails1.setId(null);
        assertThat(isolationDetails1).isNotEqualTo(isolationDetails2);
    }
}
