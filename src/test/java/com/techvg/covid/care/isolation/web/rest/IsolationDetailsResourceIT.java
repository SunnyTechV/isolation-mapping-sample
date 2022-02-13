package com.techvg.covid.care.isolation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.isolation.IntegrationTest;
import com.techvg.covid.care.isolation.domain.IsolationDetails;
import com.techvg.covid.care.isolation.repository.IsolationDetailsRepository;
import com.techvg.covid.care.isolation.service.criteria.IsolationDetailsCriteria;
import com.techvg.covid.care.isolation.service.dto.IsolationDetailsDTO;
import com.techvg.covid.care.isolation.service.mapper.IsolationDetailsMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IsolationDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IsolationDetailsResourceIT {

    private static final String DEFAULT_REFERRED_DR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REFERRED_DR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REFERRED_DR_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_REFERRED_DR_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_PRESCRIPTION_URL = "AAAAAAAAAA";
    private static final String UPDATED_PRESCRIPTION_URL = "BBBBBBBBBB";

    private static final String DEFAULT_REPORT_URL = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SELF_REGISTERED = false;
    private static final Boolean UPDATED_SELF_REGISTERED = true;

    private static final Instant DEFAULT_LAST_ASSESSMENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_ASSESSMENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/isolation-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IsolationDetailsRepository isolationDetailsRepository;

    @Autowired
    private IsolationDetailsMapper isolationDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIsolationDetailsMockMvc;

    private IsolationDetails isolationDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IsolationDetails createEntity(EntityManager em) {
        IsolationDetails isolationDetails = new IsolationDetails()
            .referredDrName(DEFAULT_REFERRED_DR_NAME)
            .referredDrMobile(DEFAULT_REFERRED_DR_MOBILE)
            .prescriptionUrl(DEFAULT_PRESCRIPTION_URL)
            .reportUrl(DEFAULT_REPORT_URL)
            .remarks(DEFAULT_REMARKS)
            .selfRegistered(DEFAULT_SELF_REGISTERED)
            .lastAssessment(DEFAULT_LAST_ASSESSMENT)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return isolationDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IsolationDetails createUpdatedEntity(EntityManager em) {
        IsolationDetails isolationDetails = new IsolationDetails()
            .referredDrName(UPDATED_REFERRED_DR_NAME)
            .referredDrMobile(UPDATED_REFERRED_DR_MOBILE)
            .prescriptionUrl(UPDATED_PRESCRIPTION_URL)
            .reportUrl(UPDATED_REPORT_URL)
            .remarks(UPDATED_REMARKS)
            .selfRegistered(UPDATED_SELF_REGISTERED)
            .lastAssessment(UPDATED_LAST_ASSESSMENT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return isolationDetails;
    }

    @BeforeEach
    public void initTest() {
        isolationDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createIsolationDetails() throws Exception {
        int databaseSizeBeforeCreate = isolationDetailsRepository.findAll().size();
        // Create the IsolationDetails
        IsolationDetailsDTO isolationDetailsDTO = isolationDetailsMapper.toDto(isolationDetails);
        restIsolationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isolationDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IsolationDetails in the database
        List<IsolationDetails> isolationDetailsList = isolationDetailsRepository.findAll();
        assertThat(isolationDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        IsolationDetails testIsolationDetails = isolationDetailsList.get(isolationDetailsList.size() - 1);
        assertThat(testIsolationDetails.getReferredDrName()).isEqualTo(DEFAULT_REFERRED_DR_NAME);
        assertThat(testIsolationDetails.getReferredDrMobile()).isEqualTo(DEFAULT_REFERRED_DR_MOBILE);
        assertThat(testIsolationDetails.getPrescriptionUrl()).isEqualTo(DEFAULT_PRESCRIPTION_URL);
        assertThat(testIsolationDetails.getReportUrl()).isEqualTo(DEFAULT_REPORT_URL);
        assertThat(testIsolationDetails.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testIsolationDetails.getSelfRegistered()).isEqualTo(DEFAULT_SELF_REGISTERED);
        assertThat(testIsolationDetails.getLastAssessment()).isEqualTo(DEFAULT_LAST_ASSESSMENT);
        assertThat(testIsolationDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testIsolationDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createIsolationDetailsWithExistingId() throws Exception {
        // Create the IsolationDetails with an existing ID
        isolationDetails.setId(1L);
        IsolationDetailsDTO isolationDetailsDTO = isolationDetailsMapper.toDto(isolationDetails);

        int databaseSizeBeforeCreate = isolationDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIsolationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isolationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsolationDetails in the database
        List<IsolationDetails> isolationDetailsList = isolationDetailsRepository.findAll();
        assertThat(isolationDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIsolationDetails() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList
        restIsolationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isolationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].referredDrName").value(hasItem(DEFAULT_REFERRED_DR_NAME)))
            .andExpect(jsonPath("$.[*].referredDrMobile").value(hasItem(DEFAULT_REFERRED_DR_MOBILE)))
            .andExpect(jsonPath("$.[*].prescriptionUrl").value(hasItem(DEFAULT_PRESCRIPTION_URL)))
            .andExpect(jsonPath("$.[*].reportUrl").value(hasItem(DEFAULT_REPORT_URL)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].selfRegistered").value(hasItem(DEFAULT_SELF_REGISTERED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastAssessment").value(hasItem(DEFAULT_LAST_ASSESSMENT.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getIsolationDetails() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get the isolationDetails
        restIsolationDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, isolationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(isolationDetails.getId().intValue()))
            .andExpect(jsonPath("$.referredDrName").value(DEFAULT_REFERRED_DR_NAME))
            .andExpect(jsonPath("$.referredDrMobile").value(DEFAULT_REFERRED_DR_MOBILE))
            .andExpect(jsonPath("$.prescriptionUrl").value(DEFAULT_PRESCRIPTION_URL))
            .andExpect(jsonPath("$.reportUrl").value(DEFAULT_REPORT_URL))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.selfRegistered").value(DEFAULT_SELF_REGISTERED.booleanValue()))
            .andExpect(jsonPath("$.lastAssessment").value(DEFAULT_LAST_ASSESSMENT.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getIsolationDetailsByIdFiltering() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        Long id = isolationDetails.getId();

        defaultIsolationDetailsShouldBeFound("id.equals=" + id);
        defaultIsolationDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultIsolationDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIsolationDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultIsolationDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIsolationDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReferredDrNameIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where referredDrName equals to DEFAULT_REFERRED_DR_NAME
        defaultIsolationDetailsShouldBeFound("referredDrName.equals=" + DEFAULT_REFERRED_DR_NAME);

        // Get all the isolationDetailsList where referredDrName equals to UPDATED_REFERRED_DR_NAME
        defaultIsolationDetailsShouldNotBeFound("referredDrName.equals=" + UPDATED_REFERRED_DR_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReferredDrNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where referredDrName not equals to DEFAULT_REFERRED_DR_NAME
        defaultIsolationDetailsShouldNotBeFound("referredDrName.notEquals=" + DEFAULT_REFERRED_DR_NAME);

        // Get all the isolationDetailsList where referredDrName not equals to UPDATED_REFERRED_DR_NAME
        defaultIsolationDetailsShouldBeFound("referredDrName.notEquals=" + UPDATED_REFERRED_DR_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReferredDrNameIsInShouldWork() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where referredDrName in DEFAULT_REFERRED_DR_NAME or UPDATED_REFERRED_DR_NAME
        defaultIsolationDetailsShouldBeFound("referredDrName.in=" + DEFAULT_REFERRED_DR_NAME + "," + UPDATED_REFERRED_DR_NAME);

        // Get all the isolationDetailsList where referredDrName equals to UPDATED_REFERRED_DR_NAME
        defaultIsolationDetailsShouldNotBeFound("referredDrName.in=" + UPDATED_REFERRED_DR_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReferredDrNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where referredDrName is not null
        defaultIsolationDetailsShouldBeFound("referredDrName.specified=true");

        // Get all the isolationDetailsList where referredDrName is null
        defaultIsolationDetailsShouldNotBeFound("referredDrName.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReferredDrNameContainsSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where referredDrName contains DEFAULT_REFERRED_DR_NAME
        defaultIsolationDetailsShouldBeFound("referredDrName.contains=" + DEFAULT_REFERRED_DR_NAME);

        // Get all the isolationDetailsList where referredDrName contains UPDATED_REFERRED_DR_NAME
        defaultIsolationDetailsShouldNotBeFound("referredDrName.contains=" + UPDATED_REFERRED_DR_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReferredDrNameNotContainsSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where referredDrName does not contain DEFAULT_REFERRED_DR_NAME
        defaultIsolationDetailsShouldNotBeFound("referredDrName.doesNotContain=" + DEFAULT_REFERRED_DR_NAME);

        // Get all the isolationDetailsList where referredDrName does not contain UPDATED_REFERRED_DR_NAME
        defaultIsolationDetailsShouldBeFound("referredDrName.doesNotContain=" + UPDATED_REFERRED_DR_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReferredDrMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where referredDrMobile equals to DEFAULT_REFERRED_DR_MOBILE
        defaultIsolationDetailsShouldBeFound("referredDrMobile.equals=" + DEFAULT_REFERRED_DR_MOBILE);

        // Get all the isolationDetailsList where referredDrMobile equals to UPDATED_REFERRED_DR_MOBILE
        defaultIsolationDetailsShouldNotBeFound("referredDrMobile.equals=" + UPDATED_REFERRED_DR_MOBILE);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReferredDrMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where referredDrMobile not equals to DEFAULT_REFERRED_DR_MOBILE
        defaultIsolationDetailsShouldNotBeFound("referredDrMobile.notEquals=" + DEFAULT_REFERRED_DR_MOBILE);

        // Get all the isolationDetailsList where referredDrMobile not equals to UPDATED_REFERRED_DR_MOBILE
        defaultIsolationDetailsShouldBeFound("referredDrMobile.notEquals=" + UPDATED_REFERRED_DR_MOBILE);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReferredDrMobileIsInShouldWork() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where referredDrMobile in DEFAULT_REFERRED_DR_MOBILE or UPDATED_REFERRED_DR_MOBILE
        defaultIsolationDetailsShouldBeFound("referredDrMobile.in=" + DEFAULT_REFERRED_DR_MOBILE + "," + UPDATED_REFERRED_DR_MOBILE);

        // Get all the isolationDetailsList where referredDrMobile equals to UPDATED_REFERRED_DR_MOBILE
        defaultIsolationDetailsShouldNotBeFound("referredDrMobile.in=" + UPDATED_REFERRED_DR_MOBILE);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReferredDrMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where referredDrMobile is not null
        defaultIsolationDetailsShouldBeFound("referredDrMobile.specified=true");

        // Get all the isolationDetailsList where referredDrMobile is null
        defaultIsolationDetailsShouldNotBeFound("referredDrMobile.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReferredDrMobileContainsSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where referredDrMobile contains DEFAULT_REFERRED_DR_MOBILE
        defaultIsolationDetailsShouldBeFound("referredDrMobile.contains=" + DEFAULT_REFERRED_DR_MOBILE);

        // Get all the isolationDetailsList where referredDrMobile contains UPDATED_REFERRED_DR_MOBILE
        defaultIsolationDetailsShouldNotBeFound("referredDrMobile.contains=" + UPDATED_REFERRED_DR_MOBILE);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReferredDrMobileNotContainsSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where referredDrMobile does not contain DEFAULT_REFERRED_DR_MOBILE
        defaultIsolationDetailsShouldNotBeFound("referredDrMobile.doesNotContain=" + DEFAULT_REFERRED_DR_MOBILE);

        // Get all the isolationDetailsList where referredDrMobile does not contain UPDATED_REFERRED_DR_MOBILE
        defaultIsolationDetailsShouldBeFound("referredDrMobile.doesNotContain=" + UPDATED_REFERRED_DR_MOBILE);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByPrescriptionUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where prescriptionUrl equals to DEFAULT_PRESCRIPTION_URL
        defaultIsolationDetailsShouldBeFound("prescriptionUrl.equals=" + DEFAULT_PRESCRIPTION_URL);

        // Get all the isolationDetailsList where prescriptionUrl equals to UPDATED_PRESCRIPTION_URL
        defaultIsolationDetailsShouldNotBeFound("prescriptionUrl.equals=" + UPDATED_PRESCRIPTION_URL);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByPrescriptionUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where prescriptionUrl not equals to DEFAULT_PRESCRIPTION_URL
        defaultIsolationDetailsShouldNotBeFound("prescriptionUrl.notEquals=" + DEFAULT_PRESCRIPTION_URL);

        // Get all the isolationDetailsList where prescriptionUrl not equals to UPDATED_PRESCRIPTION_URL
        defaultIsolationDetailsShouldBeFound("prescriptionUrl.notEquals=" + UPDATED_PRESCRIPTION_URL);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByPrescriptionUrlIsInShouldWork() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where prescriptionUrl in DEFAULT_PRESCRIPTION_URL or UPDATED_PRESCRIPTION_URL
        defaultIsolationDetailsShouldBeFound("prescriptionUrl.in=" + DEFAULT_PRESCRIPTION_URL + "," + UPDATED_PRESCRIPTION_URL);

        // Get all the isolationDetailsList where prescriptionUrl equals to UPDATED_PRESCRIPTION_URL
        defaultIsolationDetailsShouldNotBeFound("prescriptionUrl.in=" + UPDATED_PRESCRIPTION_URL);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByPrescriptionUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where prescriptionUrl is not null
        defaultIsolationDetailsShouldBeFound("prescriptionUrl.specified=true");

        // Get all the isolationDetailsList where prescriptionUrl is null
        defaultIsolationDetailsShouldNotBeFound("prescriptionUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByPrescriptionUrlContainsSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where prescriptionUrl contains DEFAULT_PRESCRIPTION_URL
        defaultIsolationDetailsShouldBeFound("prescriptionUrl.contains=" + DEFAULT_PRESCRIPTION_URL);

        // Get all the isolationDetailsList where prescriptionUrl contains UPDATED_PRESCRIPTION_URL
        defaultIsolationDetailsShouldNotBeFound("prescriptionUrl.contains=" + UPDATED_PRESCRIPTION_URL);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByPrescriptionUrlNotContainsSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where prescriptionUrl does not contain DEFAULT_PRESCRIPTION_URL
        defaultIsolationDetailsShouldNotBeFound("prescriptionUrl.doesNotContain=" + DEFAULT_PRESCRIPTION_URL);

        // Get all the isolationDetailsList where prescriptionUrl does not contain UPDATED_PRESCRIPTION_URL
        defaultIsolationDetailsShouldBeFound("prescriptionUrl.doesNotContain=" + UPDATED_PRESCRIPTION_URL);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReportUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where reportUrl equals to DEFAULT_REPORT_URL
        defaultIsolationDetailsShouldBeFound("reportUrl.equals=" + DEFAULT_REPORT_URL);

        // Get all the isolationDetailsList where reportUrl equals to UPDATED_REPORT_URL
        defaultIsolationDetailsShouldNotBeFound("reportUrl.equals=" + UPDATED_REPORT_URL);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReportUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where reportUrl not equals to DEFAULT_REPORT_URL
        defaultIsolationDetailsShouldNotBeFound("reportUrl.notEquals=" + DEFAULT_REPORT_URL);

        // Get all the isolationDetailsList where reportUrl not equals to UPDATED_REPORT_URL
        defaultIsolationDetailsShouldBeFound("reportUrl.notEquals=" + UPDATED_REPORT_URL);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReportUrlIsInShouldWork() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where reportUrl in DEFAULT_REPORT_URL or UPDATED_REPORT_URL
        defaultIsolationDetailsShouldBeFound("reportUrl.in=" + DEFAULT_REPORT_URL + "," + UPDATED_REPORT_URL);

        // Get all the isolationDetailsList where reportUrl equals to UPDATED_REPORT_URL
        defaultIsolationDetailsShouldNotBeFound("reportUrl.in=" + UPDATED_REPORT_URL);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReportUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where reportUrl is not null
        defaultIsolationDetailsShouldBeFound("reportUrl.specified=true");

        // Get all the isolationDetailsList where reportUrl is null
        defaultIsolationDetailsShouldNotBeFound("reportUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReportUrlContainsSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where reportUrl contains DEFAULT_REPORT_URL
        defaultIsolationDetailsShouldBeFound("reportUrl.contains=" + DEFAULT_REPORT_URL);

        // Get all the isolationDetailsList where reportUrl contains UPDATED_REPORT_URL
        defaultIsolationDetailsShouldNotBeFound("reportUrl.contains=" + UPDATED_REPORT_URL);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByReportUrlNotContainsSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where reportUrl does not contain DEFAULT_REPORT_URL
        defaultIsolationDetailsShouldNotBeFound("reportUrl.doesNotContain=" + DEFAULT_REPORT_URL);

        // Get all the isolationDetailsList where reportUrl does not contain UPDATED_REPORT_URL
        defaultIsolationDetailsShouldBeFound("reportUrl.doesNotContain=" + UPDATED_REPORT_URL);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where remarks equals to DEFAULT_REMARKS
        defaultIsolationDetailsShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the isolationDetailsList where remarks equals to UPDATED_REMARKS
        defaultIsolationDetailsShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where remarks not equals to DEFAULT_REMARKS
        defaultIsolationDetailsShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the isolationDetailsList where remarks not equals to UPDATED_REMARKS
        defaultIsolationDetailsShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultIsolationDetailsShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the isolationDetailsList where remarks equals to UPDATED_REMARKS
        defaultIsolationDetailsShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where remarks is not null
        defaultIsolationDetailsShouldBeFound("remarks.specified=true");

        // Get all the isolationDetailsList where remarks is null
        defaultIsolationDetailsShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByRemarksContainsSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where remarks contains DEFAULT_REMARKS
        defaultIsolationDetailsShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the isolationDetailsList where remarks contains UPDATED_REMARKS
        defaultIsolationDetailsShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where remarks does not contain DEFAULT_REMARKS
        defaultIsolationDetailsShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the isolationDetailsList where remarks does not contain UPDATED_REMARKS
        defaultIsolationDetailsShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsBySelfRegisteredIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where selfRegistered equals to DEFAULT_SELF_REGISTERED
        defaultIsolationDetailsShouldBeFound("selfRegistered.equals=" + DEFAULT_SELF_REGISTERED);

        // Get all the isolationDetailsList where selfRegistered equals to UPDATED_SELF_REGISTERED
        defaultIsolationDetailsShouldNotBeFound("selfRegistered.equals=" + UPDATED_SELF_REGISTERED);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsBySelfRegisteredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where selfRegistered not equals to DEFAULT_SELF_REGISTERED
        defaultIsolationDetailsShouldNotBeFound("selfRegistered.notEquals=" + DEFAULT_SELF_REGISTERED);

        // Get all the isolationDetailsList where selfRegistered not equals to UPDATED_SELF_REGISTERED
        defaultIsolationDetailsShouldBeFound("selfRegistered.notEquals=" + UPDATED_SELF_REGISTERED);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsBySelfRegisteredIsInShouldWork() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where selfRegistered in DEFAULT_SELF_REGISTERED or UPDATED_SELF_REGISTERED
        defaultIsolationDetailsShouldBeFound("selfRegistered.in=" + DEFAULT_SELF_REGISTERED + "," + UPDATED_SELF_REGISTERED);

        // Get all the isolationDetailsList where selfRegistered equals to UPDATED_SELF_REGISTERED
        defaultIsolationDetailsShouldNotBeFound("selfRegistered.in=" + UPDATED_SELF_REGISTERED);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsBySelfRegisteredIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where selfRegistered is not null
        defaultIsolationDetailsShouldBeFound("selfRegistered.specified=true");

        // Get all the isolationDetailsList where selfRegistered is null
        defaultIsolationDetailsShouldNotBeFound("selfRegistered.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastAssessmentIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastAssessment equals to DEFAULT_LAST_ASSESSMENT
        defaultIsolationDetailsShouldBeFound("lastAssessment.equals=" + DEFAULT_LAST_ASSESSMENT);

        // Get all the isolationDetailsList where lastAssessment equals to UPDATED_LAST_ASSESSMENT
        defaultIsolationDetailsShouldNotBeFound("lastAssessment.equals=" + UPDATED_LAST_ASSESSMENT);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastAssessmentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastAssessment not equals to DEFAULT_LAST_ASSESSMENT
        defaultIsolationDetailsShouldNotBeFound("lastAssessment.notEquals=" + DEFAULT_LAST_ASSESSMENT);

        // Get all the isolationDetailsList where lastAssessment not equals to UPDATED_LAST_ASSESSMENT
        defaultIsolationDetailsShouldBeFound("lastAssessment.notEquals=" + UPDATED_LAST_ASSESSMENT);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastAssessmentIsInShouldWork() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastAssessment in DEFAULT_LAST_ASSESSMENT or UPDATED_LAST_ASSESSMENT
        defaultIsolationDetailsShouldBeFound("lastAssessment.in=" + DEFAULT_LAST_ASSESSMENT + "," + UPDATED_LAST_ASSESSMENT);

        // Get all the isolationDetailsList where lastAssessment equals to UPDATED_LAST_ASSESSMENT
        defaultIsolationDetailsShouldNotBeFound("lastAssessment.in=" + UPDATED_LAST_ASSESSMENT);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastAssessmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastAssessment is not null
        defaultIsolationDetailsShouldBeFound("lastAssessment.specified=true");

        // Get all the isolationDetailsList where lastAssessment is null
        defaultIsolationDetailsShouldNotBeFound("lastAssessment.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultIsolationDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the isolationDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultIsolationDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultIsolationDetailsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the isolationDetailsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultIsolationDetailsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultIsolationDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the isolationDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultIsolationDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastModified is not null
        defaultIsolationDetailsShouldBeFound("lastModified.specified=true");

        // Get all the isolationDetailsList where lastModified is null
        defaultIsolationDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultIsolationDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the isolationDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultIsolationDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultIsolationDetailsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the isolationDetailsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultIsolationDetailsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultIsolationDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the isolationDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultIsolationDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastModifiedBy is not null
        defaultIsolationDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the isolationDetailsList where lastModifiedBy is null
        defaultIsolationDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultIsolationDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the isolationDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultIsolationDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllIsolationDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        // Get all the isolationDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultIsolationDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the isolationDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultIsolationDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIsolationDetailsShouldBeFound(String filter) throws Exception {
        restIsolationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isolationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].referredDrName").value(hasItem(DEFAULT_REFERRED_DR_NAME)))
            .andExpect(jsonPath("$.[*].referredDrMobile").value(hasItem(DEFAULT_REFERRED_DR_MOBILE)))
            .andExpect(jsonPath("$.[*].prescriptionUrl").value(hasItem(DEFAULT_PRESCRIPTION_URL)))
            .andExpect(jsonPath("$.[*].reportUrl").value(hasItem(DEFAULT_REPORT_URL)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].selfRegistered").value(hasItem(DEFAULT_SELF_REGISTERED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastAssessment").value(hasItem(DEFAULT_LAST_ASSESSMENT.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restIsolationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIsolationDetailsShouldNotBeFound(String filter) throws Exception {
        restIsolationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIsolationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIsolationDetails() throws Exception {
        // Get the isolationDetails
        restIsolationDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIsolationDetails() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        int databaseSizeBeforeUpdate = isolationDetailsRepository.findAll().size();

        // Update the isolationDetails
        IsolationDetails updatedIsolationDetails = isolationDetailsRepository.findById(isolationDetails.getId()).get();
        // Disconnect from session so that the updates on updatedIsolationDetails are not directly saved in db
        em.detach(updatedIsolationDetails);
        updatedIsolationDetails
            .referredDrName(UPDATED_REFERRED_DR_NAME)
            .referredDrMobile(UPDATED_REFERRED_DR_MOBILE)
            .prescriptionUrl(UPDATED_PRESCRIPTION_URL)
            .reportUrl(UPDATED_REPORT_URL)
            .remarks(UPDATED_REMARKS)
            .selfRegistered(UPDATED_SELF_REGISTERED)
            .lastAssessment(UPDATED_LAST_ASSESSMENT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        IsolationDetailsDTO isolationDetailsDTO = isolationDetailsMapper.toDto(updatedIsolationDetails);

        restIsolationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, isolationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(isolationDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the IsolationDetails in the database
        List<IsolationDetails> isolationDetailsList = isolationDetailsRepository.findAll();
        assertThat(isolationDetailsList).hasSize(databaseSizeBeforeUpdate);
        IsolationDetails testIsolationDetails = isolationDetailsList.get(isolationDetailsList.size() - 1);
        assertThat(testIsolationDetails.getReferredDrName()).isEqualTo(UPDATED_REFERRED_DR_NAME);
        assertThat(testIsolationDetails.getReferredDrMobile()).isEqualTo(UPDATED_REFERRED_DR_MOBILE);
        assertThat(testIsolationDetails.getPrescriptionUrl()).isEqualTo(UPDATED_PRESCRIPTION_URL);
        assertThat(testIsolationDetails.getReportUrl()).isEqualTo(UPDATED_REPORT_URL);
        assertThat(testIsolationDetails.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testIsolationDetails.getSelfRegistered()).isEqualTo(UPDATED_SELF_REGISTERED);
        assertThat(testIsolationDetails.getLastAssessment()).isEqualTo(UPDATED_LAST_ASSESSMENT);
        assertThat(testIsolationDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testIsolationDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingIsolationDetails() throws Exception {
        int databaseSizeBeforeUpdate = isolationDetailsRepository.findAll().size();
        isolationDetails.setId(count.incrementAndGet());

        // Create the IsolationDetails
        IsolationDetailsDTO isolationDetailsDTO = isolationDetailsMapper.toDto(isolationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIsolationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, isolationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(isolationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsolationDetails in the database
        List<IsolationDetails> isolationDetailsList = isolationDetailsRepository.findAll();
        assertThat(isolationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIsolationDetails() throws Exception {
        int databaseSizeBeforeUpdate = isolationDetailsRepository.findAll().size();
        isolationDetails.setId(count.incrementAndGet());

        // Create the IsolationDetails
        IsolationDetailsDTO isolationDetailsDTO = isolationDetailsMapper.toDto(isolationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsolationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(isolationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsolationDetails in the database
        List<IsolationDetails> isolationDetailsList = isolationDetailsRepository.findAll();
        assertThat(isolationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIsolationDetails() throws Exception {
        int databaseSizeBeforeUpdate = isolationDetailsRepository.findAll().size();
        isolationDetails.setId(count.incrementAndGet());

        // Create the IsolationDetails
        IsolationDetailsDTO isolationDetailsDTO = isolationDetailsMapper.toDto(isolationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsolationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isolationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IsolationDetails in the database
        List<IsolationDetails> isolationDetailsList = isolationDetailsRepository.findAll();
        assertThat(isolationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIsolationDetailsWithPatch() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        int databaseSizeBeforeUpdate = isolationDetailsRepository.findAll().size();

        // Update the isolationDetails using partial update
        IsolationDetails partialUpdatedIsolationDetails = new IsolationDetails();
        partialUpdatedIsolationDetails.setId(isolationDetails.getId());

        partialUpdatedIsolationDetails.prescriptionUrl(UPDATED_PRESCRIPTION_URL).remarks(UPDATED_REMARKS);

        restIsolationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIsolationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIsolationDetails))
            )
            .andExpect(status().isOk());

        // Validate the IsolationDetails in the database
        List<IsolationDetails> isolationDetailsList = isolationDetailsRepository.findAll();
        assertThat(isolationDetailsList).hasSize(databaseSizeBeforeUpdate);
        IsolationDetails testIsolationDetails = isolationDetailsList.get(isolationDetailsList.size() - 1);
        assertThat(testIsolationDetails.getReferredDrName()).isEqualTo(DEFAULT_REFERRED_DR_NAME);
        assertThat(testIsolationDetails.getReferredDrMobile()).isEqualTo(DEFAULT_REFERRED_DR_MOBILE);
        assertThat(testIsolationDetails.getPrescriptionUrl()).isEqualTo(UPDATED_PRESCRIPTION_URL);
        assertThat(testIsolationDetails.getReportUrl()).isEqualTo(DEFAULT_REPORT_URL);
        assertThat(testIsolationDetails.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testIsolationDetails.getSelfRegistered()).isEqualTo(DEFAULT_SELF_REGISTERED);
        assertThat(testIsolationDetails.getLastAssessment()).isEqualTo(DEFAULT_LAST_ASSESSMENT);
        assertThat(testIsolationDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testIsolationDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateIsolationDetailsWithPatch() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        int databaseSizeBeforeUpdate = isolationDetailsRepository.findAll().size();

        // Update the isolationDetails using partial update
        IsolationDetails partialUpdatedIsolationDetails = new IsolationDetails();
        partialUpdatedIsolationDetails.setId(isolationDetails.getId());

        partialUpdatedIsolationDetails
            .referredDrName(UPDATED_REFERRED_DR_NAME)
            .referredDrMobile(UPDATED_REFERRED_DR_MOBILE)
            .prescriptionUrl(UPDATED_PRESCRIPTION_URL)
            .reportUrl(UPDATED_REPORT_URL)
            .remarks(UPDATED_REMARKS)
            .selfRegistered(UPDATED_SELF_REGISTERED)
            .lastAssessment(UPDATED_LAST_ASSESSMENT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restIsolationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIsolationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIsolationDetails))
            )
            .andExpect(status().isOk());

        // Validate the IsolationDetails in the database
        List<IsolationDetails> isolationDetailsList = isolationDetailsRepository.findAll();
        assertThat(isolationDetailsList).hasSize(databaseSizeBeforeUpdate);
        IsolationDetails testIsolationDetails = isolationDetailsList.get(isolationDetailsList.size() - 1);
        assertThat(testIsolationDetails.getReferredDrName()).isEqualTo(UPDATED_REFERRED_DR_NAME);
        assertThat(testIsolationDetails.getReferredDrMobile()).isEqualTo(UPDATED_REFERRED_DR_MOBILE);
        assertThat(testIsolationDetails.getPrescriptionUrl()).isEqualTo(UPDATED_PRESCRIPTION_URL);
        assertThat(testIsolationDetails.getReportUrl()).isEqualTo(UPDATED_REPORT_URL);
        assertThat(testIsolationDetails.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testIsolationDetails.getSelfRegistered()).isEqualTo(UPDATED_SELF_REGISTERED);
        assertThat(testIsolationDetails.getLastAssessment()).isEqualTo(UPDATED_LAST_ASSESSMENT);
        assertThat(testIsolationDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testIsolationDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingIsolationDetails() throws Exception {
        int databaseSizeBeforeUpdate = isolationDetailsRepository.findAll().size();
        isolationDetails.setId(count.incrementAndGet());

        // Create the IsolationDetails
        IsolationDetailsDTO isolationDetailsDTO = isolationDetailsMapper.toDto(isolationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIsolationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, isolationDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isolationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsolationDetails in the database
        List<IsolationDetails> isolationDetailsList = isolationDetailsRepository.findAll();
        assertThat(isolationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIsolationDetails() throws Exception {
        int databaseSizeBeforeUpdate = isolationDetailsRepository.findAll().size();
        isolationDetails.setId(count.incrementAndGet());

        // Create the IsolationDetails
        IsolationDetailsDTO isolationDetailsDTO = isolationDetailsMapper.toDto(isolationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsolationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isolationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsolationDetails in the database
        List<IsolationDetails> isolationDetailsList = isolationDetailsRepository.findAll();
        assertThat(isolationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIsolationDetails() throws Exception {
        int databaseSizeBeforeUpdate = isolationDetailsRepository.findAll().size();
        isolationDetails.setId(count.incrementAndGet());

        // Create the IsolationDetails
        IsolationDetailsDTO isolationDetailsDTO = isolationDetailsMapper.toDto(isolationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsolationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isolationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IsolationDetails in the database
        List<IsolationDetails> isolationDetailsList = isolationDetailsRepository.findAll();
        assertThat(isolationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIsolationDetails() throws Exception {
        // Initialize the database
        isolationDetailsRepository.saveAndFlush(isolationDetails);

        int databaseSizeBeforeDelete = isolationDetailsRepository.findAll().size();

        // Delete the isolationDetails
        restIsolationDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, isolationDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IsolationDetails> isolationDetailsList = isolationDetailsRepository.findAll();
        assertThat(isolationDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
