package com.techvg.covid.care.isolation.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IsolationDetailsMapperTest {

    private IsolationDetailsMapper isolationDetailsMapper;

    @BeforeEach
    public void setUp() {
        isolationDetailsMapper = new IsolationDetailsMapperImpl();
    }
}
