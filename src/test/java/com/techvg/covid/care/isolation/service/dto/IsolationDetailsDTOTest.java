package com.techvg.covid.care.isolation.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.isolation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IsolationDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IsolationDetailsDTO.class);
        IsolationDetailsDTO isolationDetailsDTO1 = new IsolationDetailsDTO();
        isolationDetailsDTO1.setId(1L);
        IsolationDetailsDTO isolationDetailsDTO2 = new IsolationDetailsDTO();
        assertThat(isolationDetailsDTO1).isNotEqualTo(isolationDetailsDTO2);
        isolationDetailsDTO2.setId(isolationDetailsDTO1.getId());
        assertThat(isolationDetailsDTO1).isEqualTo(isolationDetailsDTO2);
        isolationDetailsDTO2.setId(2L);
        assertThat(isolationDetailsDTO1).isNotEqualTo(isolationDetailsDTO2);
        isolationDetailsDTO1.setId(null);
        assertThat(isolationDetailsDTO1).isNotEqualTo(isolationDetailsDTO2);
    }
}
