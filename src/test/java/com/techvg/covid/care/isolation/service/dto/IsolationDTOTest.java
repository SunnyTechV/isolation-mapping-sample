package com.techvg.covid.care.isolation.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.isolation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IsolationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IsolationDTO.class);
        IsolationDTO isolationDTO1 = new IsolationDTO();
        isolationDTO1.setId(1L);
        IsolationDTO isolationDTO2 = new IsolationDTO();
        assertThat(isolationDTO1).isNotEqualTo(isolationDTO2);
        isolationDTO2.setId(isolationDTO1.getId());
        assertThat(isolationDTO1).isEqualTo(isolationDTO2);
        isolationDTO2.setId(2L);
        assertThat(isolationDTO1).isNotEqualTo(isolationDTO2);
        isolationDTO1.setId(null);
        assertThat(isolationDTO1).isNotEqualTo(isolationDTO2);
    }
}
