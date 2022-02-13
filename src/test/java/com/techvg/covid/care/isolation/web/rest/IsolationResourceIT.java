package com.techvg.covid.care.isolation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.isolation.IntegrationTest;
import com.techvg.covid.care.isolation.domain.Isolation;
import com.techvg.covid.care.isolation.domain.IsolationDetails;
import com.techvg.covid.care.isolation.domain.enumeration.HealthCondition;
import com.techvg.covid.care.isolation.domain.enumeration.IsolationStatus;
import com.techvg.covid.care.isolation.repository.IsolationRepository;
import com.techvg.covid.care.isolation.service.criteria.IsolationCriteria;
import com.techvg.covid.care.isolation.service.dto.IsolationDTO;
import com.techvg.covid.care.isolation.service.mapper.IsolationMapper;
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
 * Integration tests for the {@link IsolationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IsolationResourceIT {

    private static final String DEFAULT_ICMR_ID = "AAAAAAAAAA";
    private static final String UPDATED_ICMR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RTPCR_ID = "AAAAAAAAAA";
    private static final String UPDATED_RTPCR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RAT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RAT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARY_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_CONTACT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR_CARD_NO = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_CARD_NO = "BBBBBBBBBB";

    private static final IsolationStatus DEFAULT_STATUS = IsolationStatus.HOMEISOLATION;
    private static final IsolationStatus UPDATED_STATUS = IsolationStatus.HOSPITALISED;

    private static final String DEFAULT_AGE = "AAAAAAAAAA";
    private static final String UPDATED_AGE = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final Long DEFAULT_STATE_ID = 1L;
    private static final Long UPDATED_STATE_ID = 2L;
    private static final Long SMALLER_STATE_ID = 1L - 1L;

    private static final Long DEFAULT_DISTRICT_ID = 1L;
    private static final Long UPDATED_DISTRICT_ID = 2L;
    private static final Long SMALLER_DISTRICT_ID = 1L - 1L;

    private static final Long DEFAULT_TALUKA_ID = 1L;
    private static final Long UPDATED_TALUKA_ID = 2L;
    private static final Long SMALLER_TALUKA_ID = 1L - 1L;

    private static final Long DEFAULT_CITY_ID = 1L;
    private static final Long UPDATED_CITY_ID = 2L;
    private static final Long SMALLER_CITY_ID = 1L - 1L;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_COLLECTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COLLECTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_HOSPITALIZED = false;
    private static final Boolean UPDATED_HOSPITALIZED = true;

    private static final Long DEFAULT_HOSPITAL_ID = 1L;
    private static final Long UPDATED_HOSPITAL_ID = 2L;
    private static final Long SMALLER_HOSPITAL_ID = 1L - 1L;

    private static final String DEFAULT_ADDRESS_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_LONGITUDE = "BBBBBBBBBB";

    private static final Instant DEFAULT_HOSPITALIZATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOSPITALIZATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final HealthCondition DEFAULT_HEALTH_CONDITION = HealthCondition.STABLE;
    private static final HealthCondition UPDATED_HEALTH_CONDITION = HealthCondition.CRITICAL;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SYMPTOMATIC = false;
    private static final Boolean UPDATED_SYMPTOMATIC = true;

    private static final String DEFAULT_CCMS_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_CCMS_LOGIN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SELF_REGISTERED = false;
    private static final Boolean UPDATED_SELF_REGISTERED = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_ISOLATION_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ISOLATION_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ISOLATION_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ISOLATION_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_TVG_ISOLATION_USER_ID = 1L;
    private static final Long UPDATED_TVG_ISOLATION_USER_ID = 2L;
    private static final Long SMALLER_TVG_ISOLATION_USER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/isolations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IsolationRepository isolationRepository;

    @Autowired
    private IsolationMapper isolationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIsolationMockMvc;

    private Isolation isolation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Isolation createEntity(EntityManager em) {
        Isolation isolation = new Isolation()
            .icmrId(DEFAULT_ICMR_ID)
            .rtpcrId(DEFAULT_RTPCR_ID)
            .ratId(DEFAULT_RAT_ID)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .email(DEFAULT_EMAIL)
            .imageUrl(DEFAULT_IMAGE_URL)
            .activated(DEFAULT_ACTIVATED)
            .mobileNo(DEFAULT_MOBILE_NO)
            .passwordHash(DEFAULT_PASSWORD_HASH)
            .secondaryContactNo(DEFAULT_SECONDARY_CONTACT_NO)
            .aadharCardNo(DEFAULT_AADHAR_CARD_NO)
            .status(DEFAULT_STATUS)
            .age(DEFAULT_AGE)
            .gender(DEFAULT_GENDER)
            .stateId(DEFAULT_STATE_ID)
            .districtId(DEFAULT_DISTRICT_ID)
            .talukaId(DEFAULT_TALUKA_ID)
            .cityId(DEFAULT_CITY_ID)
            .address(DEFAULT_ADDRESS)
            .pincode(DEFAULT_PINCODE)
            .collectionDate(DEFAULT_COLLECTION_DATE)
            .hospitalized(DEFAULT_HOSPITALIZED)
            .hospitalId(DEFAULT_HOSPITAL_ID)
            .addressLatitude(DEFAULT_ADDRESS_LATITUDE)
            .addressLongitude(DEFAULT_ADDRESS_LONGITUDE)
            .currentLatitude(DEFAULT_CURRENT_LATITUDE)
            .currentLongitude(DEFAULT_CURRENT_LONGITUDE)
            .hospitalizationDate(DEFAULT_HOSPITALIZATION_DATE)
            .healthCondition(DEFAULT_HEALTH_CONDITION)
            .remarks(DEFAULT_REMARKS)
            .symptomatic(DEFAULT_SYMPTOMATIC)
            .ccmsLogin(DEFAULT_CCMS_LOGIN)
            .selfRegistered(DEFAULT_SELF_REGISTERED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .isolationStartDate(DEFAULT_ISOLATION_START_DATE)
            .isolationEndDate(DEFAULT_ISOLATION_END_DATE)
            .tvgIsolationUserId(DEFAULT_TVG_ISOLATION_USER_ID);
        return isolation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Isolation createUpdatedEntity(EntityManager em) {
        Isolation isolation = new Isolation()
            .icmrId(UPDATED_ICMR_ID)
            .rtpcrId(UPDATED_RTPCR_ID)
            .ratId(UPDATED_RAT_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .activated(UPDATED_ACTIVATED)
            .mobileNo(UPDATED_MOBILE_NO)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .secondaryContactNo(UPDATED_SECONDARY_CONTACT_NO)
            .aadharCardNo(UPDATED_AADHAR_CARD_NO)
            .status(UPDATED_STATUS)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .stateId(UPDATED_STATE_ID)
            .districtId(UPDATED_DISTRICT_ID)
            .talukaId(UPDATED_TALUKA_ID)
            .cityId(UPDATED_CITY_ID)
            .address(UPDATED_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .collectionDate(UPDATED_COLLECTION_DATE)
            .hospitalized(UPDATED_HOSPITALIZED)
            .hospitalId(UPDATED_HOSPITAL_ID)
            .addressLatitude(UPDATED_ADDRESS_LATITUDE)
            .addressLongitude(UPDATED_ADDRESS_LONGITUDE)
            .currentLatitude(UPDATED_CURRENT_LATITUDE)
            .currentLongitude(UPDATED_CURRENT_LONGITUDE)
            .hospitalizationDate(UPDATED_HOSPITALIZATION_DATE)
            .healthCondition(UPDATED_HEALTH_CONDITION)
            .remarks(UPDATED_REMARKS)
            .symptomatic(UPDATED_SYMPTOMATIC)
            .ccmsLogin(UPDATED_CCMS_LOGIN)
            .selfRegistered(UPDATED_SELF_REGISTERED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isolationStartDate(UPDATED_ISOLATION_START_DATE)
            .isolationEndDate(UPDATED_ISOLATION_END_DATE)
            .tvgIsolationUserId(UPDATED_TVG_ISOLATION_USER_ID);
        return isolation;
    }

    @BeforeEach
    public void initTest() {
        isolation = createEntity(em);
    }

    @Test
    @Transactional
    void createIsolation() throws Exception {
        int databaseSizeBeforeCreate = isolationRepository.findAll().size();
        // Create the Isolation
        IsolationDTO isolationDTO = isolationMapper.toDto(isolation);
        restIsolationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isolationDTO)))
            .andExpect(status().isCreated());

        // Validate the Isolation in the database
        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeCreate + 1);
        Isolation testIsolation = isolationList.get(isolationList.size() - 1);
        assertThat(testIsolation.getIcmrId()).isEqualTo(DEFAULT_ICMR_ID);
        assertThat(testIsolation.getRtpcrId()).isEqualTo(DEFAULT_RTPCR_ID);
        assertThat(testIsolation.getRatId()).isEqualTo(DEFAULT_RAT_ID);
        assertThat(testIsolation.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testIsolation.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testIsolation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testIsolation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testIsolation.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testIsolation.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testIsolation.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testIsolation.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testIsolation.getPasswordHash()).isEqualTo(DEFAULT_PASSWORD_HASH);
        assertThat(testIsolation.getSecondaryContactNo()).isEqualTo(DEFAULT_SECONDARY_CONTACT_NO);
        assertThat(testIsolation.getAadharCardNo()).isEqualTo(DEFAULT_AADHAR_CARD_NO);
        assertThat(testIsolation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testIsolation.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testIsolation.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIsolation.getStateId()).isEqualTo(DEFAULT_STATE_ID);
        assertThat(testIsolation.getDistrictId()).isEqualTo(DEFAULT_DISTRICT_ID);
        assertThat(testIsolation.getTalukaId()).isEqualTo(DEFAULT_TALUKA_ID);
        assertThat(testIsolation.getCityId()).isEqualTo(DEFAULT_CITY_ID);
        assertThat(testIsolation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testIsolation.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testIsolation.getCollectionDate()).isEqualTo(DEFAULT_COLLECTION_DATE);
        assertThat(testIsolation.getHospitalized()).isEqualTo(DEFAULT_HOSPITALIZED);
        assertThat(testIsolation.getHospitalId()).isEqualTo(DEFAULT_HOSPITAL_ID);
        assertThat(testIsolation.getAddressLatitude()).isEqualTo(DEFAULT_ADDRESS_LATITUDE);
        assertThat(testIsolation.getAddressLongitude()).isEqualTo(DEFAULT_ADDRESS_LONGITUDE);
        assertThat(testIsolation.getCurrentLatitude()).isEqualTo(DEFAULT_CURRENT_LATITUDE);
        assertThat(testIsolation.getCurrentLongitude()).isEqualTo(DEFAULT_CURRENT_LONGITUDE);
        assertThat(testIsolation.getHospitalizationDate()).isEqualTo(DEFAULT_HOSPITALIZATION_DATE);
        assertThat(testIsolation.getHealthCondition()).isEqualTo(DEFAULT_HEALTH_CONDITION);
        assertThat(testIsolation.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testIsolation.getSymptomatic()).isEqualTo(DEFAULT_SYMPTOMATIC);
        assertThat(testIsolation.getCcmsLogin()).isEqualTo(DEFAULT_CCMS_LOGIN);
        assertThat(testIsolation.getSelfRegistered()).isEqualTo(DEFAULT_SELF_REGISTERED);
        assertThat(testIsolation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testIsolation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testIsolation.getIsolationStartDate()).isEqualTo(DEFAULT_ISOLATION_START_DATE);
        assertThat(testIsolation.getIsolationEndDate()).isEqualTo(DEFAULT_ISOLATION_END_DATE);
        assertThat(testIsolation.getTvgIsolationUserId()).isEqualTo(DEFAULT_TVG_ISOLATION_USER_ID);
    }

    @Test
    @Transactional
    void createIsolationWithExistingId() throws Exception {
        // Create the Isolation with an existing ID
        isolation.setId(1L);
        IsolationDTO isolationDTO = isolationMapper.toDto(isolation);

        int databaseSizeBeforeCreate = isolationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIsolationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isolationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Isolation in the database
        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMobileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = isolationRepository.findAll().size();
        // set the field null
        isolation.setMobileNo(null);

        // Create the Isolation, which fails.
        IsolationDTO isolationDTO = isolationMapper.toDto(isolation);

        restIsolationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isolationDTO)))
            .andExpect(status().isBadRequest());

        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = isolationRepository.findAll().size();
        // set the field null
        isolation.setPasswordHash(null);

        // Create the Isolation, which fails.
        IsolationDTO isolationDTO = isolationMapper.toDto(isolation);

        restIsolationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isolationDTO)))
            .andExpect(status().isBadRequest());

        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAadharCardNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = isolationRepository.findAll().size();
        // set the field null
        isolation.setAadharCardNo(null);

        // Create the Isolation, which fails.
        IsolationDTO isolationDTO = isolationMapper.toDto(isolation);

        restIsolationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isolationDTO)))
            .andExpect(status().isBadRequest());

        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIsolations() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList
        restIsolationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isolation.getId().intValue())))
            .andExpect(jsonPath("$.[*].icmrId").value(hasItem(DEFAULT_ICMR_ID)))
            .andExpect(jsonPath("$.[*].rtpcrId").value(hasItem(DEFAULT_RTPCR_ID)))
            .andExpect(jsonPath("$.[*].ratId").value(hasItem(DEFAULT_RAT_ID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH)))
            .andExpect(jsonPath("$.[*].secondaryContactNo").value(hasItem(DEFAULT_SECONDARY_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].aadharCardNo").value(hasItem(DEFAULT_AADHAR_CARD_NO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].stateId").value(hasItem(DEFAULT_STATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID.intValue())))
            .andExpect(jsonPath("$.[*].talukaId").value(hasItem(DEFAULT_TALUKA_ID.intValue())))
            .andExpect(jsonPath("$.[*].cityId").value(hasItem(DEFAULT_CITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].collectionDate").value(hasItem(DEFAULT_COLLECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].hospitalized").value(hasItem(DEFAULT_HOSPITALIZED.booleanValue())))
            .andExpect(jsonPath("$.[*].hospitalId").value(hasItem(DEFAULT_HOSPITAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].addressLatitude").value(hasItem(DEFAULT_ADDRESS_LATITUDE)))
            .andExpect(jsonPath("$.[*].addressLongitude").value(hasItem(DEFAULT_ADDRESS_LONGITUDE)))
            .andExpect(jsonPath("$.[*].currentLatitude").value(hasItem(DEFAULT_CURRENT_LATITUDE)))
            .andExpect(jsonPath("$.[*].currentLongitude").value(hasItem(DEFAULT_CURRENT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].hospitalizationDate").value(hasItem(DEFAULT_HOSPITALIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].healthCondition").value(hasItem(DEFAULT_HEALTH_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].symptomatic").value(hasItem(DEFAULT_SYMPTOMATIC.booleanValue())))
            .andExpect(jsonPath("$.[*].ccmsLogin").value(hasItem(DEFAULT_CCMS_LOGIN)))
            .andExpect(jsonPath("$.[*].selfRegistered").value(hasItem(DEFAULT_SELF_REGISTERED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isolationStartDate").value(hasItem(DEFAULT_ISOLATION_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].isolationEndDate").value(hasItem(DEFAULT_ISOLATION_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].tvgIsolationUserId").value(hasItem(DEFAULT_TVG_ISOLATION_USER_ID.intValue())));
    }

    @Test
    @Transactional
    void getIsolation() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get the isolation
        restIsolationMockMvc
            .perform(get(ENTITY_API_URL_ID, isolation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(isolation.getId().intValue()))
            .andExpect(jsonPath("$.icmrId").value(DEFAULT_ICMR_ID))
            .andExpect(jsonPath("$.rtpcrId").value(DEFAULT_RTPCR_ID))
            .andExpect(jsonPath("$.ratId").value(DEFAULT_RAT_ID))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.passwordHash").value(DEFAULT_PASSWORD_HASH))
            .andExpect(jsonPath("$.secondaryContactNo").value(DEFAULT_SECONDARY_CONTACT_NO))
            .andExpect(jsonPath("$.aadharCardNo").value(DEFAULT_AADHAR_CARD_NO))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.stateId").value(DEFAULT_STATE_ID.intValue()))
            .andExpect(jsonPath("$.districtId").value(DEFAULT_DISTRICT_ID.intValue()))
            .andExpect(jsonPath("$.talukaId").value(DEFAULT_TALUKA_ID.intValue()))
            .andExpect(jsonPath("$.cityId").value(DEFAULT_CITY_ID.intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.collectionDate").value(DEFAULT_COLLECTION_DATE.toString()))
            .andExpect(jsonPath("$.hospitalized").value(DEFAULT_HOSPITALIZED.booleanValue()))
            .andExpect(jsonPath("$.hospitalId").value(DEFAULT_HOSPITAL_ID.intValue()))
            .andExpect(jsonPath("$.addressLatitude").value(DEFAULT_ADDRESS_LATITUDE))
            .andExpect(jsonPath("$.addressLongitude").value(DEFAULT_ADDRESS_LONGITUDE))
            .andExpect(jsonPath("$.currentLatitude").value(DEFAULT_CURRENT_LATITUDE))
            .andExpect(jsonPath("$.currentLongitude").value(DEFAULT_CURRENT_LONGITUDE))
            .andExpect(jsonPath("$.hospitalizationDate").value(DEFAULT_HOSPITALIZATION_DATE.toString()))
            .andExpect(jsonPath("$.healthCondition").value(DEFAULT_HEALTH_CONDITION.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.symptomatic").value(DEFAULT_SYMPTOMATIC.booleanValue()))
            .andExpect(jsonPath("$.ccmsLogin").value(DEFAULT_CCMS_LOGIN))
            .andExpect(jsonPath("$.selfRegistered").value(DEFAULT_SELF_REGISTERED.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.isolationStartDate").value(DEFAULT_ISOLATION_START_DATE.toString()))
            .andExpect(jsonPath("$.isolationEndDate").value(DEFAULT_ISOLATION_END_DATE.toString()))
            .andExpect(jsonPath("$.tvgIsolationUserId").value(DEFAULT_TVG_ISOLATION_USER_ID.intValue()));
    }

    @Test
    @Transactional
    void getIsolationsByIdFiltering() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        Long id = isolation.getId();

        defaultIsolationShouldBeFound("id.equals=" + id);
        defaultIsolationShouldNotBeFound("id.notEquals=" + id);

        defaultIsolationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIsolationShouldNotBeFound("id.greaterThan=" + id);

        defaultIsolationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIsolationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIsolationsByIcmrIdIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where icmrId equals to DEFAULT_ICMR_ID
        defaultIsolationShouldBeFound("icmrId.equals=" + DEFAULT_ICMR_ID);

        // Get all the isolationList where icmrId equals to UPDATED_ICMR_ID
        defaultIsolationShouldNotBeFound("icmrId.equals=" + UPDATED_ICMR_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByIcmrIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where icmrId not equals to DEFAULT_ICMR_ID
        defaultIsolationShouldNotBeFound("icmrId.notEquals=" + DEFAULT_ICMR_ID);

        // Get all the isolationList where icmrId not equals to UPDATED_ICMR_ID
        defaultIsolationShouldBeFound("icmrId.notEquals=" + UPDATED_ICMR_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByIcmrIdIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where icmrId in DEFAULT_ICMR_ID or UPDATED_ICMR_ID
        defaultIsolationShouldBeFound("icmrId.in=" + DEFAULT_ICMR_ID + "," + UPDATED_ICMR_ID);

        // Get all the isolationList where icmrId equals to UPDATED_ICMR_ID
        defaultIsolationShouldNotBeFound("icmrId.in=" + UPDATED_ICMR_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByIcmrIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where icmrId is not null
        defaultIsolationShouldBeFound("icmrId.specified=true");

        // Get all the isolationList where icmrId is null
        defaultIsolationShouldNotBeFound("icmrId.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByIcmrIdContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where icmrId contains DEFAULT_ICMR_ID
        defaultIsolationShouldBeFound("icmrId.contains=" + DEFAULT_ICMR_ID);

        // Get all the isolationList where icmrId contains UPDATED_ICMR_ID
        defaultIsolationShouldNotBeFound("icmrId.contains=" + UPDATED_ICMR_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByIcmrIdNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where icmrId does not contain DEFAULT_ICMR_ID
        defaultIsolationShouldNotBeFound("icmrId.doesNotContain=" + DEFAULT_ICMR_ID);

        // Get all the isolationList where icmrId does not contain UPDATED_ICMR_ID
        defaultIsolationShouldBeFound("icmrId.doesNotContain=" + UPDATED_ICMR_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByRtpcrIdIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where rtpcrId equals to DEFAULT_RTPCR_ID
        defaultIsolationShouldBeFound("rtpcrId.equals=" + DEFAULT_RTPCR_ID);

        // Get all the isolationList where rtpcrId equals to UPDATED_RTPCR_ID
        defaultIsolationShouldNotBeFound("rtpcrId.equals=" + UPDATED_RTPCR_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByRtpcrIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where rtpcrId not equals to DEFAULT_RTPCR_ID
        defaultIsolationShouldNotBeFound("rtpcrId.notEquals=" + DEFAULT_RTPCR_ID);

        // Get all the isolationList where rtpcrId not equals to UPDATED_RTPCR_ID
        defaultIsolationShouldBeFound("rtpcrId.notEquals=" + UPDATED_RTPCR_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByRtpcrIdIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where rtpcrId in DEFAULT_RTPCR_ID or UPDATED_RTPCR_ID
        defaultIsolationShouldBeFound("rtpcrId.in=" + DEFAULT_RTPCR_ID + "," + UPDATED_RTPCR_ID);

        // Get all the isolationList where rtpcrId equals to UPDATED_RTPCR_ID
        defaultIsolationShouldNotBeFound("rtpcrId.in=" + UPDATED_RTPCR_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByRtpcrIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where rtpcrId is not null
        defaultIsolationShouldBeFound("rtpcrId.specified=true");

        // Get all the isolationList where rtpcrId is null
        defaultIsolationShouldNotBeFound("rtpcrId.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByRtpcrIdContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where rtpcrId contains DEFAULT_RTPCR_ID
        defaultIsolationShouldBeFound("rtpcrId.contains=" + DEFAULT_RTPCR_ID);

        // Get all the isolationList where rtpcrId contains UPDATED_RTPCR_ID
        defaultIsolationShouldNotBeFound("rtpcrId.contains=" + UPDATED_RTPCR_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByRtpcrIdNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where rtpcrId does not contain DEFAULT_RTPCR_ID
        defaultIsolationShouldNotBeFound("rtpcrId.doesNotContain=" + DEFAULT_RTPCR_ID);

        // Get all the isolationList where rtpcrId does not contain UPDATED_RTPCR_ID
        defaultIsolationShouldBeFound("rtpcrId.doesNotContain=" + UPDATED_RTPCR_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByRatIdIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where ratId equals to DEFAULT_RAT_ID
        defaultIsolationShouldBeFound("ratId.equals=" + DEFAULT_RAT_ID);

        // Get all the isolationList where ratId equals to UPDATED_RAT_ID
        defaultIsolationShouldNotBeFound("ratId.equals=" + UPDATED_RAT_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByRatIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where ratId not equals to DEFAULT_RAT_ID
        defaultIsolationShouldNotBeFound("ratId.notEquals=" + DEFAULT_RAT_ID);

        // Get all the isolationList where ratId not equals to UPDATED_RAT_ID
        defaultIsolationShouldBeFound("ratId.notEquals=" + UPDATED_RAT_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByRatIdIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where ratId in DEFAULT_RAT_ID or UPDATED_RAT_ID
        defaultIsolationShouldBeFound("ratId.in=" + DEFAULT_RAT_ID + "," + UPDATED_RAT_ID);

        // Get all the isolationList where ratId equals to UPDATED_RAT_ID
        defaultIsolationShouldNotBeFound("ratId.in=" + UPDATED_RAT_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByRatIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where ratId is not null
        defaultIsolationShouldBeFound("ratId.specified=true");

        // Get all the isolationList where ratId is null
        defaultIsolationShouldNotBeFound("ratId.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByRatIdContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where ratId contains DEFAULT_RAT_ID
        defaultIsolationShouldBeFound("ratId.contains=" + DEFAULT_RAT_ID);

        // Get all the isolationList where ratId contains UPDATED_RAT_ID
        defaultIsolationShouldNotBeFound("ratId.contains=" + UPDATED_RAT_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByRatIdNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where ratId does not contain DEFAULT_RAT_ID
        defaultIsolationShouldNotBeFound("ratId.doesNotContain=" + DEFAULT_RAT_ID);

        // Get all the isolationList where ratId does not contain UPDATED_RAT_ID
        defaultIsolationShouldBeFound("ratId.doesNotContain=" + UPDATED_RAT_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where firstName equals to DEFAULT_FIRST_NAME
        defaultIsolationShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the isolationList where firstName equals to UPDATED_FIRST_NAME
        defaultIsolationShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationsByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where firstName not equals to DEFAULT_FIRST_NAME
        defaultIsolationShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the isolationList where firstName not equals to UPDATED_FIRST_NAME
        defaultIsolationShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationsByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultIsolationShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the isolationList where firstName equals to UPDATED_FIRST_NAME
        defaultIsolationShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationsByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where firstName is not null
        defaultIsolationShouldBeFound("firstName.specified=true");

        // Get all the isolationList where firstName is null
        defaultIsolationShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where firstName contains DEFAULT_FIRST_NAME
        defaultIsolationShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the isolationList where firstName contains UPDATED_FIRST_NAME
        defaultIsolationShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationsByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where firstName does not contain DEFAULT_FIRST_NAME
        defaultIsolationShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the isolationList where firstName does not contain UPDATED_FIRST_NAME
        defaultIsolationShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastName equals to DEFAULT_LAST_NAME
        defaultIsolationShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the isolationList where lastName equals to UPDATED_LAST_NAME
        defaultIsolationShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationsByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastName not equals to DEFAULT_LAST_NAME
        defaultIsolationShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the isolationList where lastName not equals to UPDATED_LAST_NAME
        defaultIsolationShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultIsolationShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the isolationList where lastName equals to UPDATED_LAST_NAME
        defaultIsolationShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastName is not null
        defaultIsolationShouldBeFound("lastName.specified=true");

        // Get all the isolationList where lastName is null
        defaultIsolationShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByLastNameContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastName contains DEFAULT_LAST_NAME
        defaultIsolationShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the isolationList where lastName contains UPDATED_LAST_NAME
        defaultIsolationShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationsByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastName does not contain DEFAULT_LAST_NAME
        defaultIsolationShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the isolationList where lastName does not contain UPDATED_LAST_NAME
        defaultIsolationShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllIsolationsByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where latitude equals to DEFAULT_LATITUDE
        defaultIsolationShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the isolationList where latitude equals to UPDATED_LATITUDE
        defaultIsolationShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where latitude not equals to DEFAULT_LATITUDE
        defaultIsolationShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the isolationList where latitude not equals to UPDATED_LATITUDE
        defaultIsolationShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultIsolationShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the isolationList where latitude equals to UPDATED_LATITUDE
        defaultIsolationShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where latitude is not null
        defaultIsolationShouldBeFound("latitude.specified=true");

        // Get all the isolationList where latitude is null
        defaultIsolationShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByLatitudeContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where latitude contains DEFAULT_LATITUDE
        defaultIsolationShouldBeFound("latitude.contains=" + DEFAULT_LATITUDE);

        // Get all the isolationList where latitude contains UPDATED_LATITUDE
        defaultIsolationShouldNotBeFound("latitude.contains=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByLatitudeNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where latitude does not contain DEFAULT_LATITUDE
        defaultIsolationShouldNotBeFound("latitude.doesNotContain=" + DEFAULT_LATITUDE);

        // Get all the isolationList where latitude does not contain UPDATED_LATITUDE
        defaultIsolationShouldBeFound("latitude.doesNotContain=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where longitude equals to DEFAULT_LONGITUDE
        defaultIsolationShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the isolationList where longitude equals to UPDATED_LONGITUDE
        defaultIsolationShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where longitude not equals to DEFAULT_LONGITUDE
        defaultIsolationShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the isolationList where longitude not equals to UPDATED_LONGITUDE
        defaultIsolationShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultIsolationShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the isolationList where longitude equals to UPDATED_LONGITUDE
        defaultIsolationShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where longitude is not null
        defaultIsolationShouldBeFound("longitude.specified=true");

        // Get all the isolationList where longitude is null
        defaultIsolationShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByLongitudeContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where longitude contains DEFAULT_LONGITUDE
        defaultIsolationShouldBeFound("longitude.contains=" + DEFAULT_LONGITUDE);

        // Get all the isolationList where longitude contains UPDATED_LONGITUDE
        defaultIsolationShouldNotBeFound("longitude.contains=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByLongitudeNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where longitude does not contain DEFAULT_LONGITUDE
        defaultIsolationShouldNotBeFound("longitude.doesNotContain=" + DEFAULT_LONGITUDE);

        // Get all the isolationList where longitude does not contain UPDATED_LONGITUDE
        defaultIsolationShouldBeFound("longitude.doesNotContain=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where email equals to DEFAULT_EMAIL
        defaultIsolationShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the isolationList where email equals to UPDATED_EMAIL
        defaultIsolationShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllIsolationsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where email not equals to DEFAULT_EMAIL
        defaultIsolationShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the isolationList where email not equals to UPDATED_EMAIL
        defaultIsolationShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllIsolationsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultIsolationShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the isolationList where email equals to UPDATED_EMAIL
        defaultIsolationShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllIsolationsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where email is not null
        defaultIsolationShouldBeFound("email.specified=true");

        // Get all the isolationList where email is null
        defaultIsolationShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByEmailContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where email contains DEFAULT_EMAIL
        defaultIsolationShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the isolationList where email contains UPDATED_EMAIL
        defaultIsolationShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllIsolationsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where email does not contain DEFAULT_EMAIL
        defaultIsolationShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the isolationList where email does not contain UPDATED_EMAIL
        defaultIsolationShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllIsolationsByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultIsolationShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the isolationList where imageUrl equals to UPDATED_IMAGE_URL
        defaultIsolationShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllIsolationsByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultIsolationShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the isolationList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultIsolationShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllIsolationsByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultIsolationShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the isolationList where imageUrl equals to UPDATED_IMAGE_URL
        defaultIsolationShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllIsolationsByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where imageUrl is not null
        defaultIsolationShouldBeFound("imageUrl.specified=true");

        // Get all the isolationList where imageUrl is null
        defaultIsolationShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where imageUrl contains DEFAULT_IMAGE_URL
        defaultIsolationShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the isolationList where imageUrl contains UPDATED_IMAGE_URL
        defaultIsolationShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllIsolationsByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultIsolationShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the isolationList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultIsolationShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllIsolationsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where activated equals to DEFAULT_ACTIVATED
        defaultIsolationShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the isolationList where activated equals to UPDATED_ACTIVATED
        defaultIsolationShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllIsolationsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where activated not equals to DEFAULT_ACTIVATED
        defaultIsolationShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the isolationList where activated not equals to UPDATED_ACTIVATED
        defaultIsolationShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllIsolationsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultIsolationShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the isolationList where activated equals to UPDATED_ACTIVATED
        defaultIsolationShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllIsolationsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where activated is not null
        defaultIsolationShouldBeFound("activated.specified=true");

        // Get all the isolationList where activated is null
        defaultIsolationShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultIsolationShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the isolationList where mobileNo equals to UPDATED_MOBILE_NO
        defaultIsolationShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsByMobileNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where mobileNo not equals to DEFAULT_MOBILE_NO
        defaultIsolationShouldNotBeFound("mobileNo.notEquals=" + DEFAULT_MOBILE_NO);

        // Get all the isolationList where mobileNo not equals to UPDATED_MOBILE_NO
        defaultIsolationShouldBeFound("mobileNo.notEquals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultIsolationShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the isolationList where mobileNo equals to UPDATED_MOBILE_NO
        defaultIsolationShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where mobileNo is not null
        defaultIsolationShouldBeFound("mobileNo.specified=true");

        // Get all the isolationList where mobileNo is null
        defaultIsolationShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where mobileNo contains DEFAULT_MOBILE_NO
        defaultIsolationShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the isolationList where mobileNo contains UPDATED_MOBILE_NO
        defaultIsolationShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultIsolationShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the isolationList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultIsolationShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsByPasswordHashIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where passwordHash equals to DEFAULT_PASSWORD_HASH
        defaultIsolationShouldBeFound("passwordHash.equals=" + DEFAULT_PASSWORD_HASH);

        // Get all the isolationList where passwordHash equals to UPDATED_PASSWORD_HASH
        defaultIsolationShouldNotBeFound("passwordHash.equals=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    void getAllIsolationsByPasswordHashIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where passwordHash not equals to DEFAULT_PASSWORD_HASH
        defaultIsolationShouldNotBeFound("passwordHash.notEquals=" + DEFAULT_PASSWORD_HASH);

        // Get all the isolationList where passwordHash not equals to UPDATED_PASSWORD_HASH
        defaultIsolationShouldBeFound("passwordHash.notEquals=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    void getAllIsolationsByPasswordHashIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where passwordHash in DEFAULT_PASSWORD_HASH or UPDATED_PASSWORD_HASH
        defaultIsolationShouldBeFound("passwordHash.in=" + DEFAULT_PASSWORD_HASH + "," + UPDATED_PASSWORD_HASH);

        // Get all the isolationList where passwordHash equals to UPDATED_PASSWORD_HASH
        defaultIsolationShouldNotBeFound("passwordHash.in=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    void getAllIsolationsByPasswordHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where passwordHash is not null
        defaultIsolationShouldBeFound("passwordHash.specified=true");

        // Get all the isolationList where passwordHash is null
        defaultIsolationShouldNotBeFound("passwordHash.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByPasswordHashContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where passwordHash contains DEFAULT_PASSWORD_HASH
        defaultIsolationShouldBeFound("passwordHash.contains=" + DEFAULT_PASSWORD_HASH);

        // Get all the isolationList where passwordHash contains UPDATED_PASSWORD_HASH
        defaultIsolationShouldNotBeFound("passwordHash.contains=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    void getAllIsolationsByPasswordHashNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where passwordHash does not contain DEFAULT_PASSWORD_HASH
        defaultIsolationShouldNotBeFound("passwordHash.doesNotContain=" + DEFAULT_PASSWORD_HASH);

        // Get all the isolationList where passwordHash does not contain UPDATED_PASSWORD_HASH
        defaultIsolationShouldBeFound("passwordHash.doesNotContain=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    void getAllIsolationsBySecondaryContactNoIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where secondaryContactNo equals to DEFAULT_SECONDARY_CONTACT_NO
        defaultIsolationShouldBeFound("secondaryContactNo.equals=" + DEFAULT_SECONDARY_CONTACT_NO);

        // Get all the isolationList where secondaryContactNo equals to UPDATED_SECONDARY_CONTACT_NO
        defaultIsolationShouldNotBeFound("secondaryContactNo.equals=" + UPDATED_SECONDARY_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsBySecondaryContactNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where secondaryContactNo not equals to DEFAULT_SECONDARY_CONTACT_NO
        defaultIsolationShouldNotBeFound("secondaryContactNo.notEquals=" + DEFAULT_SECONDARY_CONTACT_NO);

        // Get all the isolationList where secondaryContactNo not equals to UPDATED_SECONDARY_CONTACT_NO
        defaultIsolationShouldBeFound("secondaryContactNo.notEquals=" + UPDATED_SECONDARY_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsBySecondaryContactNoIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where secondaryContactNo in DEFAULT_SECONDARY_CONTACT_NO or UPDATED_SECONDARY_CONTACT_NO
        defaultIsolationShouldBeFound("secondaryContactNo.in=" + DEFAULT_SECONDARY_CONTACT_NO + "," + UPDATED_SECONDARY_CONTACT_NO);

        // Get all the isolationList where secondaryContactNo equals to UPDATED_SECONDARY_CONTACT_NO
        defaultIsolationShouldNotBeFound("secondaryContactNo.in=" + UPDATED_SECONDARY_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsBySecondaryContactNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where secondaryContactNo is not null
        defaultIsolationShouldBeFound("secondaryContactNo.specified=true");

        // Get all the isolationList where secondaryContactNo is null
        defaultIsolationShouldNotBeFound("secondaryContactNo.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsBySecondaryContactNoContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where secondaryContactNo contains DEFAULT_SECONDARY_CONTACT_NO
        defaultIsolationShouldBeFound("secondaryContactNo.contains=" + DEFAULT_SECONDARY_CONTACT_NO);

        // Get all the isolationList where secondaryContactNo contains UPDATED_SECONDARY_CONTACT_NO
        defaultIsolationShouldNotBeFound("secondaryContactNo.contains=" + UPDATED_SECONDARY_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsBySecondaryContactNoNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where secondaryContactNo does not contain DEFAULT_SECONDARY_CONTACT_NO
        defaultIsolationShouldNotBeFound("secondaryContactNo.doesNotContain=" + DEFAULT_SECONDARY_CONTACT_NO);

        // Get all the isolationList where secondaryContactNo does not contain UPDATED_SECONDARY_CONTACT_NO
        defaultIsolationShouldBeFound("secondaryContactNo.doesNotContain=" + UPDATED_SECONDARY_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsByAadharCardNoIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where aadharCardNo equals to DEFAULT_AADHAR_CARD_NO
        defaultIsolationShouldBeFound("aadharCardNo.equals=" + DEFAULT_AADHAR_CARD_NO);

        // Get all the isolationList where aadharCardNo equals to UPDATED_AADHAR_CARD_NO
        defaultIsolationShouldNotBeFound("aadharCardNo.equals=" + UPDATED_AADHAR_CARD_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsByAadharCardNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where aadharCardNo not equals to DEFAULT_AADHAR_CARD_NO
        defaultIsolationShouldNotBeFound("aadharCardNo.notEquals=" + DEFAULT_AADHAR_CARD_NO);

        // Get all the isolationList where aadharCardNo not equals to UPDATED_AADHAR_CARD_NO
        defaultIsolationShouldBeFound("aadharCardNo.notEquals=" + UPDATED_AADHAR_CARD_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsByAadharCardNoIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where aadharCardNo in DEFAULT_AADHAR_CARD_NO or UPDATED_AADHAR_CARD_NO
        defaultIsolationShouldBeFound("aadharCardNo.in=" + DEFAULT_AADHAR_CARD_NO + "," + UPDATED_AADHAR_CARD_NO);

        // Get all the isolationList where aadharCardNo equals to UPDATED_AADHAR_CARD_NO
        defaultIsolationShouldNotBeFound("aadharCardNo.in=" + UPDATED_AADHAR_CARD_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsByAadharCardNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where aadharCardNo is not null
        defaultIsolationShouldBeFound("aadharCardNo.specified=true");

        // Get all the isolationList where aadharCardNo is null
        defaultIsolationShouldNotBeFound("aadharCardNo.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByAadharCardNoContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where aadharCardNo contains DEFAULT_AADHAR_CARD_NO
        defaultIsolationShouldBeFound("aadharCardNo.contains=" + DEFAULT_AADHAR_CARD_NO);

        // Get all the isolationList where aadharCardNo contains UPDATED_AADHAR_CARD_NO
        defaultIsolationShouldNotBeFound("aadharCardNo.contains=" + UPDATED_AADHAR_CARD_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsByAadharCardNoNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where aadharCardNo does not contain DEFAULT_AADHAR_CARD_NO
        defaultIsolationShouldNotBeFound("aadharCardNo.doesNotContain=" + DEFAULT_AADHAR_CARD_NO);

        // Get all the isolationList where aadharCardNo does not contain UPDATED_AADHAR_CARD_NO
        defaultIsolationShouldBeFound("aadharCardNo.doesNotContain=" + UPDATED_AADHAR_CARD_NO);
    }

    @Test
    @Transactional
    void getAllIsolationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where status equals to DEFAULT_STATUS
        defaultIsolationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the isolationList where status equals to UPDATED_STATUS
        defaultIsolationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllIsolationsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where status not equals to DEFAULT_STATUS
        defaultIsolationShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the isolationList where status not equals to UPDATED_STATUS
        defaultIsolationShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllIsolationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultIsolationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the isolationList where status equals to UPDATED_STATUS
        defaultIsolationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllIsolationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where status is not null
        defaultIsolationShouldBeFound("status.specified=true");

        // Get all the isolationList where status is null
        defaultIsolationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where age equals to DEFAULT_AGE
        defaultIsolationShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the isolationList where age equals to UPDATED_AGE
        defaultIsolationShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllIsolationsByAgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where age not equals to DEFAULT_AGE
        defaultIsolationShouldNotBeFound("age.notEquals=" + DEFAULT_AGE);

        // Get all the isolationList where age not equals to UPDATED_AGE
        defaultIsolationShouldBeFound("age.notEquals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllIsolationsByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where age in DEFAULT_AGE or UPDATED_AGE
        defaultIsolationShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the isolationList where age equals to UPDATED_AGE
        defaultIsolationShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllIsolationsByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where age is not null
        defaultIsolationShouldBeFound("age.specified=true");

        // Get all the isolationList where age is null
        defaultIsolationShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByAgeContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where age contains DEFAULT_AGE
        defaultIsolationShouldBeFound("age.contains=" + DEFAULT_AGE);

        // Get all the isolationList where age contains UPDATED_AGE
        defaultIsolationShouldNotBeFound("age.contains=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllIsolationsByAgeNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where age does not contain DEFAULT_AGE
        defaultIsolationShouldNotBeFound("age.doesNotContain=" + DEFAULT_AGE);

        // Get all the isolationList where age does not contain UPDATED_AGE
        defaultIsolationShouldBeFound("age.doesNotContain=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllIsolationsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where gender equals to DEFAULT_GENDER
        defaultIsolationShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the isolationList where gender equals to UPDATED_GENDER
        defaultIsolationShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIsolationsByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where gender not equals to DEFAULT_GENDER
        defaultIsolationShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the isolationList where gender not equals to UPDATED_GENDER
        defaultIsolationShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIsolationsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultIsolationShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the isolationList where gender equals to UPDATED_GENDER
        defaultIsolationShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIsolationsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where gender is not null
        defaultIsolationShouldBeFound("gender.specified=true");

        // Get all the isolationList where gender is null
        defaultIsolationShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByGenderContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where gender contains DEFAULT_GENDER
        defaultIsolationShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the isolationList where gender contains UPDATED_GENDER
        defaultIsolationShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIsolationsByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where gender does not contain DEFAULT_GENDER
        defaultIsolationShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the isolationList where gender does not contain UPDATED_GENDER
        defaultIsolationShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIsolationsByStateIdIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where stateId equals to DEFAULT_STATE_ID
        defaultIsolationShouldBeFound("stateId.equals=" + DEFAULT_STATE_ID);

        // Get all the isolationList where stateId equals to UPDATED_STATE_ID
        defaultIsolationShouldNotBeFound("stateId.equals=" + UPDATED_STATE_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByStateIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where stateId not equals to DEFAULT_STATE_ID
        defaultIsolationShouldNotBeFound("stateId.notEquals=" + DEFAULT_STATE_ID);

        // Get all the isolationList where stateId not equals to UPDATED_STATE_ID
        defaultIsolationShouldBeFound("stateId.notEquals=" + UPDATED_STATE_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByStateIdIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where stateId in DEFAULT_STATE_ID or UPDATED_STATE_ID
        defaultIsolationShouldBeFound("stateId.in=" + DEFAULT_STATE_ID + "," + UPDATED_STATE_ID);

        // Get all the isolationList where stateId equals to UPDATED_STATE_ID
        defaultIsolationShouldNotBeFound("stateId.in=" + UPDATED_STATE_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByStateIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where stateId is not null
        defaultIsolationShouldBeFound("stateId.specified=true");

        // Get all the isolationList where stateId is null
        defaultIsolationShouldNotBeFound("stateId.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByStateIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where stateId is greater than or equal to DEFAULT_STATE_ID
        defaultIsolationShouldBeFound("stateId.greaterThanOrEqual=" + DEFAULT_STATE_ID);

        // Get all the isolationList where stateId is greater than or equal to UPDATED_STATE_ID
        defaultIsolationShouldNotBeFound("stateId.greaterThanOrEqual=" + UPDATED_STATE_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByStateIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where stateId is less than or equal to DEFAULT_STATE_ID
        defaultIsolationShouldBeFound("stateId.lessThanOrEqual=" + DEFAULT_STATE_ID);

        // Get all the isolationList where stateId is less than or equal to SMALLER_STATE_ID
        defaultIsolationShouldNotBeFound("stateId.lessThanOrEqual=" + SMALLER_STATE_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByStateIdIsLessThanSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where stateId is less than DEFAULT_STATE_ID
        defaultIsolationShouldNotBeFound("stateId.lessThan=" + DEFAULT_STATE_ID);

        // Get all the isolationList where stateId is less than UPDATED_STATE_ID
        defaultIsolationShouldBeFound("stateId.lessThan=" + UPDATED_STATE_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByStateIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where stateId is greater than DEFAULT_STATE_ID
        defaultIsolationShouldNotBeFound("stateId.greaterThan=" + DEFAULT_STATE_ID);

        // Get all the isolationList where stateId is greater than SMALLER_STATE_ID
        defaultIsolationShouldBeFound("stateId.greaterThan=" + SMALLER_STATE_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByDistrictIdIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where districtId equals to DEFAULT_DISTRICT_ID
        defaultIsolationShouldBeFound("districtId.equals=" + DEFAULT_DISTRICT_ID);

        // Get all the isolationList where districtId equals to UPDATED_DISTRICT_ID
        defaultIsolationShouldNotBeFound("districtId.equals=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByDistrictIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where districtId not equals to DEFAULT_DISTRICT_ID
        defaultIsolationShouldNotBeFound("districtId.notEquals=" + DEFAULT_DISTRICT_ID);

        // Get all the isolationList where districtId not equals to UPDATED_DISTRICT_ID
        defaultIsolationShouldBeFound("districtId.notEquals=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByDistrictIdIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where districtId in DEFAULT_DISTRICT_ID or UPDATED_DISTRICT_ID
        defaultIsolationShouldBeFound("districtId.in=" + DEFAULT_DISTRICT_ID + "," + UPDATED_DISTRICT_ID);

        // Get all the isolationList where districtId equals to UPDATED_DISTRICT_ID
        defaultIsolationShouldNotBeFound("districtId.in=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByDistrictIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where districtId is not null
        defaultIsolationShouldBeFound("districtId.specified=true");

        // Get all the isolationList where districtId is null
        defaultIsolationShouldNotBeFound("districtId.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByDistrictIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where districtId is greater than or equal to DEFAULT_DISTRICT_ID
        defaultIsolationShouldBeFound("districtId.greaterThanOrEqual=" + DEFAULT_DISTRICT_ID);

        // Get all the isolationList where districtId is greater than or equal to UPDATED_DISTRICT_ID
        defaultIsolationShouldNotBeFound("districtId.greaterThanOrEqual=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByDistrictIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where districtId is less than or equal to DEFAULT_DISTRICT_ID
        defaultIsolationShouldBeFound("districtId.lessThanOrEqual=" + DEFAULT_DISTRICT_ID);

        // Get all the isolationList where districtId is less than or equal to SMALLER_DISTRICT_ID
        defaultIsolationShouldNotBeFound("districtId.lessThanOrEqual=" + SMALLER_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByDistrictIdIsLessThanSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where districtId is less than DEFAULT_DISTRICT_ID
        defaultIsolationShouldNotBeFound("districtId.lessThan=" + DEFAULT_DISTRICT_ID);

        // Get all the isolationList where districtId is less than UPDATED_DISTRICT_ID
        defaultIsolationShouldBeFound("districtId.lessThan=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByDistrictIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where districtId is greater than DEFAULT_DISTRICT_ID
        defaultIsolationShouldNotBeFound("districtId.greaterThan=" + DEFAULT_DISTRICT_ID);

        // Get all the isolationList where districtId is greater than SMALLER_DISTRICT_ID
        defaultIsolationShouldBeFound("districtId.greaterThan=" + SMALLER_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByTalukaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where talukaId equals to DEFAULT_TALUKA_ID
        defaultIsolationShouldBeFound("talukaId.equals=" + DEFAULT_TALUKA_ID);

        // Get all the isolationList where talukaId equals to UPDATED_TALUKA_ID
        defaultIsolationShouldNotBeFound("talukaId.equals=" + UPDATED_TALUKA_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByTalukaIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where talukaId not equals to DEFAULT_TALUKA_ID
        defaultIsolationShouldNotBeFound("talukaId.notEquals=" + DEFAULT_TALUKA_ID);

        // Get all the isolationList where talukaId not equals to UPDATED_TALUKA_ID
        defaultIsolationShouldBeFound("talukaId.notEquals=" + UPDATED_TALUKA_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByTalukaIdIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where talukaId in DEFAULT_TALUKA_ID or UPDATED_TALUKA_ID
        defaultIsolationShouldBeFound("talukaId.in=" + DEFAULT_TALUKA_ID + "," + UPDATED_TALUKA_ID);

        // Get all the isolationList where talukaId equals to UPDATED_TALUKA_ID
        defaultIsolationShouldNotBeFound("talukaId.in=" + UPDATED_TALUKA_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByTalukaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where talukaId is not null
        defaultIsolationShouldBeFound("talukaId.specified=true");

        // Get all the isolationList where talukaId is null
        defaultIsolationShouldNotBeFound("talukaId.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByTalukaIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where talukaId is greater than or equal to DEFAULT_TALUKA_ID
        defaultIsolationShouldBeFound("talukaId.greaterThanOrEqual=" + DEFAULT_TALUKA_ID);

        // Get all the isolationList where talukaId is greater than or equal to UPDATED_TALUKA_ID
        defaultIsolationShouldNotBeFound("talukaId.greaterThanOrEqual=" + UPDATED_TALUKA_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByTalukaIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where talukaId is less than or equal to DEFAULT_TALUKA_ID
        defaultIsolationShouldBeFound("talukaId.lessThanOrEqual=" + DEFAULT_TALUKA_ID);

        // Get all the isolationList where talukaId is less than or equal to SMALLER_TALUKA_ID
        defaultIsolationShouldNotBeFound("talukaId.lessThanOrEqual=" + SMALLER_TALUKA_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByTalukaIdIsLessThanSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where talukaId is less than DEFAULT_TALUKA_ID
        defaultIsolationShouldNotBeFound("talukaId.lessThan=" + DEFAULT_TALUKA_ID);

        // Get all the isolationList where talukaId is less than UPDATED_TALUKA_ID
        defaultIsolationShouldBeFound("talukaId.lessThan=" + UPDATED_TALUKA_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByTalukaIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where talukaId is greater than DEFAULT_TALUKA_ID
        defaultIsolationShouldNotBeFound("talukaId.greaterThan=" + DEFAULT_TALUKA_ID);

        // Get all the isolationList where talukaId is greater than SMALLER_TALUKA_ID
        defaultIsolationShouldBeFound("talukaId.greaterThan=" + SMALLER_TALUKA_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByCityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where cityId equals to DEFAULT_CITY_ID
        defaultIsolationShouldBeFound("cityId.equals=" + DEFAULT_CITY_ID);

        // Get all the isolationList where cityId equals to UPDATED_CITY_ID
        defaultIsolationShouldNotBeFound("cityId.equals=" + UPDATED_CITY_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByCityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where cityId not equals to DEFAULT_CITY_ID
        defaultIsolationShouldNotBeFound("cityId.notEquals=" + DEFAULT_CITY_ID);

        // Get all the isolationList where cityId not equals to UPDATED_CITY_ID
        defaultIsolationShouldBeFound("cityId.notEquals=" + UPDATED_CITY_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByCityIdIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where cityId in DEFAULT_CITY_ID or UPDATED_CITY_ID
        defaultIsolationShouldBeFound("cityId.in=" + DEFAULT_CITY_ID + "," + UPDATED_CITY_ID);

        // Get all the isolationList where cityId equals to UPDATED_CITY_ID
        defaultIsolationShouldNotBeFound("cityId.in=" + UPDATED_CITY_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByCityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where cityId is not null
        defaultIsolationShouldBeFound("cityId.specified=true");

        // Get all the isolationList where cityId is null
        defaultIsolationShouldNotBeFound("cityId.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByCityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where cityId is greater than or equal to DEFAULT_CITY_ID
        defaultIsolationShouldBeFound("cityId.greaterThanOrEqual=" + DEFAULT_CITY_ID);

        // Get all the isolationList where cityId is greater than or equal to UPDATED_CITY_ID
        defaultIsolationShouldNotBeFound("cityId.greaterThanOrEqual=" + UPDATED_CITY_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByCityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where cityId is less than or equal to DEFAULT_CITY_ID
        defaultIsolationShouldBeFound("cityId.lessThanOrEqual=" + DEFAULT_CITY_ID);

        // Get all the isolationList where cityId is less than or equal to SMALLER_CITY_ID
        defaultIsolationShouldNotBeFound("cityId.lessThanOrEqual=" + SMALLER_CITY_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByCityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where cityId is less than DEFAULT_CITY_ID
        defaultIsolationShouldNotBeFound("cityId.lessThan=" + DEFAULT_CITY_ID);

        // Get all the isolationList where cityId is less than UPDATED_CITY_ID
        defaultIsolationShouldBeFound("cityId.lessThan=" + UPDATED_CITY_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByCityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where cityId is greater than DEFAULT_CITY_ID
        defaultIsolationShouldNotBeFound("cityId.greaterThan=" + DEFAULT_CITY_ID);

        // Get all the isolationList where cityId is greater than SMALLER_CITY_ID
        defaultIsolationShouldBeFound("cityId.greaterThan=" + SMALLER_CITY_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where address equals to DEFAULT_ADDRESS
        defaultIsolationShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the isolationList where address equals to UPDATED_ADDRESS
        defaultIsolationShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where address not equals to DEFAULT_ADDRESS
        defaultIsolationShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the isolationList where address not equals to UPDATED_ADDRESS
        defaultIsolationShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultIsolationShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the isolationList where address equals to UPDATED_ADDRESS
        defaultIsolationShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where address is not null
        defaultIsolationShouldBeFound("address.specified=true");

        // Get all the isolationList where address is null
        defaultIsolationShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where address contains DEFAULT_ADDRESS
        defaultIsolationShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the isolationList where address contains UPDATED_ADDRESS
        defaultIsolationShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where address does not contain DEFAULT_ADDRESS
        defaultIsolationShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the isolationList where address does not contain UPDATED_ADDRESS
        defaultIsolationShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIsolationsByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where pincode equals to DEFAULT_PINCODE
        defaultIsolationShouldBeFound("pincode.equals=" + DEFAULT_PINCODE);

        // Get all the isolationList where pincode equals to UPDATED_PINCODE
        defaultIsolationShouldNotBeFound("pincode.equals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllIsolationsByPincodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where pincode not equals to DEFAULT_PINCODE
        defaultIsolationShouldNotBeFound("pincode.notEquals=" + DEFAULT_PINCODE);

        // Get all the isolationList where pincode not equals to UPDATED_PINCODE
        defaultIsolationShouldBeFound("pincode.notEquals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllIsolationsByPincodeIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where pincode in DEFAULT_PINCODE or UPDATED_PINCODE
        defaultIsolationShouldBeFound("pincode.in=" + DEFAULT_PINCODE + "," + UPDATED_PINCODE);

        // Get all the isolationList where pincode equals to UPDATED_PINCODE
        defaultIsolationShouldNotBeFound("pincode.in=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllIsolationsByPincodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where pincode is not null
        defaultIsolationShouldBeFound("pincode.specified=true");

        // Get all the isolationList where pincode is null
        defaultIsolationShouldNotBeFound("pincode.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByPincodeContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where pincode contains DEFAULT_PINCODE
        defaultIsolationShouldBeFound("pincode.contains=" + DEFAULT_PINCODE);

        // Get all the isolationList where pincode contains UPDATED_PINCODE
        defaultIsolationShouldNotBeFound("pincode.contains=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllIsolationsByPincodeNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where pincode does not contain DEFAULT_PINCODE
        defaultIsolationShouldNotBeFound("pincode.doesNotContain=" + DEFAULT_PINCODE);

        // Get all the isolationList where pincode does not contain UPDATED_PINCODE
        defaultIsolationShouldBeFound("pincode.doesNotContain=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCollectionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where collectionDate equals to DEFAULT_COLLECTION_DATE
        defaultIsolationShouldBeFound("collectionDate.equals=" + DEFAULT_COLLECTION_DATE);

        // Get all the isolationList where collectionDate equals to UPDATED_COLLECTION_DATE
        defaultIsolationShouldNotBeFound("collectionDate.equals=" + UPDATED_COLLECTION_DATE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCollectionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where collectionDate not equals to DEFAULT_COLLECTION_DATE
        defaultIsolationShouldNotBeFound("collectionDate.notEquals=" + DEFAULT_COLLECTION_DATE);

        // Get all the isolationList where collectionDate not equals to UPDATED_COLLECTION_DATE
        defaultIsolationShouldBeFound("collectionDate.notEquals=" + UPDATED_COLLECTION_DATE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCollectionDateIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where collectionDate in DEFAULT_COLLECTION_DATE or UPDATED_COLLECTION_DATE
        defaultIsolationShouldBeFound("collectionDate.in=" + DEFAULT_COLLECTION_DATE + "," + UPDATED_COLLECTION_DATE);

        // Get all the isolationList where collectionDate equals to UPDATED_COLLECTION_DATE
        defaultIsolationShouldNotBeFound("collectionDate.in=" + UPDATED_COLLECTION_DATE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCollectionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where collectionDate is not null
        defaultIsolationShouldBeFound("collectionDate.specified=true");

        // Get all the isolationList where collectionDate is null
        defaultIsolationShouldNotBeFound("collectionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalizedIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalized equals to DEFAULT_HOSPITALIZED
        defaultIsolationShouldBeFound("hospitalized.equals=" + DEFAULT_HOSPITALIZED);

        // Get all the isolationList where hospitalized equals to UPDATED_HOSPITALIZED
        defaultIsolationShouldNotBeFound("hospitalized.equals=" + UPDATED_HOSPITALIZED);
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalizedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalized not equals to DEFAULT_HOSPITALIZED
        defaultIsolationShouldNotBeFound("hospitalized.notEquals=" + DEFAULT_HOSPITALIZED);

        // Get all the isolationList where hospitalized not equals to UPDATED_HOSPITALIZED
        defaultIsolationShouldBeFound("hospitalized.notEquals=" + UPDATED_HOSPITALIZED);
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalizedIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalized in DEFAULT_HOSPITALIZED or UPDATED_HOSPITALIZED
        defaultIsolationShouldBeFound("hospitalized.in=" + DEFAULT_HOSPITALIZED + "," + UPDATED_HOSPITALIZED);

        // Get all the isolationList where hospitalized equals to UPDATED_HOSPITALIZED
        defaultIsolationShouldNotBeFound("hospitalized.in=" + UPDATED_HOSPITALIZED);
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalizedIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalized is not null
        defaultIsolationShouldBeFound("hospitalized.specified=true");

        // Get all the isolationList where hospitalized is null
        defaultIsolationShouldNotBeFound("hospitalized.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalId equals to DEFAULT_HOSPITAL_ID
        defaultIsolationShouldBeFound("hospitalId.equals=" + DEFAULT_HOSPITAL_ID);

        // Get all the isolationList where hospitalId equals to UPDATED_HOSPITAL_ID
        defaultIsolationShouldNotBeFound("hospitalId.equals=" + UPDATED_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalId not equals to DEFAULT_HOSPITAL_ID
        defaultIsolationShouldNotBeFound("hospitalId.notEquals=" + DEFAULT_HOSPITAL_ID);

        // Get all the isolationList where hospitalId not equals to UPDATED_HOSPITAL_ID
        defaultIsolationShouldBeFound("hospitalId.notEquals=" + UPDATED_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalIdIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalId in DEFAULT_HOSPITAL_ID or UPDATED_HOSPITAL_ID
        defaultIsolationShouldBeFound("hospitalId.in=" + DEFAULT_HOSPITAL_ID + "," + UPDATED_HOSPITAL_ID);

        // Get all the isolationList where hospitalId equals to UPDATED_HOSPITAL_ID
        defaultIsolationShouldNotBeFound("hospitalId.in=" + UPDATED_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalId is not null
        defaultIsolationShouldBeFound("hospitalId.specified=true");

        // Get all the isolationList where hospitalId is null
        defaultIsolationShouldNotBeFound("hospitalId.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalId is greater than or equal to DEFAULT_HOSPITAL_ID
        defaultIsolationShouldBeFound("hospitalId.greaterThanOrEqual=" + DEFAULT_HOSPITAL_ID);

        // Get all the isolationList where hospitalId is greater than or equal to UPDATED_HOSPITAL_ID
        defaultIsolationShouldNotBeFound("hospitalId.greaterThanOrEqual=" + UPDATED_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalId is less than or equal to DEFAULT_HOSPITAL_ID
        defaultIsolationShouldBeFound("hospitalId.lessThanOrEqual=" + DEFAULT_HOSPITAL_ID);

        // Get all the isolationList where hospitalId is less than or equal to SMALLER_HOSPITAL_ID
        defaultIsolationShouldNotBeFound("hospitalId.lessThanOrEqual=" + SMALLER_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalIdIsLessThanSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalId is less than DEFAULT_HOSPITAL_ID
        defaultIsolationShouldNotBeFound("hospitalId.lessThan=" + DEFAULT_HOSPITAL_ID);

        // Get all the isolationList where hospitalId is less than UPDATED_HOSPITAL_ID
        defaultIsolationShouldBeFound("hospitalId.lessThan=" + UPDATED_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalId is greater than DEFAULT_HOSPITAL_ID
        defaultIsolationShouldNotBeFound("hospitalId.greaterThan=" + DEFAULT_HOSPITAL_ID);

        // Get all the isolationList where hospitalId is greater than SMALLER_HOSPITAL_ID
        defaultIsolationShouldBeFound("hospitalId.greaterThan=" + SMALLER_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where addressLatitude equals to DEFAULT_ADDRESS_LATITUDE
        defaultIsolationShouldBeFound("addressLatitude.equals=" + DEFAULT_ADDRESS_LATITUDE);

        // Get all the isolationList where addressLatitude equals to UPDATED_ADDRESS_LATITUDE
        defaultIsolationShouldNotBeFound("addressLatitude.equals=" + UPDATED_ADDRESS_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where addressLatitude not equals to DEFAULT_ADDRESS_LATITUDE
        defaultIsolationShouldNotBeFound("addressLatitude.notEquals=" + DEFAULT_ADDRESS_LATITUDE);

        // Get all the isolationList where addressLatitude not equals to UPDATED_ADDRESS_LATITUDE
        defaultIsolationShouldBeFound("addressLatitude.notEquals=" + UPDATED_ADDRESS_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where addressLatitude in DEFAULT_ADDRESS_LATITUDE or UPDATED_ADDRESS_LATITUDE
        defaultIsolationShouldBeFound("addressLatitude.in=" + DEFAULT_ADDRESS_LATITUDE + "," + UPDATED_ADDRESS_LATITUDE);

        // Get all the isolationList where addressLatitude equals to UPDATED_ADDRESS_LATITUDE
        defaultIsolationShouldNotBeFound("addressLatitude.in=" + UPDATED_ADDRESS_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where addressLatitude is not null
        defaultIsolationShouldBeFound("addressLatitude.specified=true");

        // Get all the isolationList where addressLatitude is null
        defaultIsolationShouldNotBeFound("addressLatitude.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressLatitudeContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where addressLatitude contains DEFAULT_ADDRESS_LATITUDE
        defaultIsolationShouldBeFound("addressLatitude.contains=" + DEFAULT_ADDRESS_LATITUDE);

        // Get all the isolationList where addressLatitude contains UPDATED_ADDRESS_LATITUDE
        defaultIsolationShouldNotBeFound("addressLatitude.contains=" + UPDATED_ADDRESS_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressLatitudeNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where addressLatitude does not contain DEFAULT_ADDRESS_LATITUDE
        defaultIsolationShouldNotBeFound("addressLatitude.doesNotContain=" + DEFAULT_ADDRESS_LATITUDE);

        // Get all the isolationList where addressLatitude does not contain UPDATED_ADDRESS_LATITUDE
        defaultIsolationShouldBeFound("addressLatitude.doesNotContain=" + UPDATED_ADDRESS_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where addressLongitude equals to DEFAULT_ADDRESS_LONGITUDE
        defaultIsolationShouldBeFound("addressLongitude.equals=" + DEFAULT_ADDRESS_LONGITUDE);

        // Get all the isolationList where addressLongitude equals to UPDATED_ADDRESS_LONGITUDE
        defaultIsolationShouldNotBeFound("addressLongitude.equals=" + UPDATED_ADDRESS_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where addressLongitude not equals to DEFAULT_ADDRESS_LONGITUDE
        defaultIsolationShouldNotBeFound("addressLongitude.notEquals=" + DEFAULT_ADDRESS_LONGITUDE);

        // Get all the isolationList where addressLongitude not equals to UPDATED_ADDRESS_LONGITUDE
        defaultIsolationShouldBeFound("addressLongitude.notEquals=" + UPDATED_ADDRESS_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where addressLongitude in DEFAULT_ADDRESS_LONGITUDE or UPDATED_ADDRESS_LONGITUDE
        defaultIsolationShouldBeFound("addressLongitude.in=" + DEFAULT_ADDRESS_LONGITUDE + "," + UPDATED_ADDRESS_LONGITUDE);

        // Get all the isolationList where addressLongitude equals to UPDATED_ADDRESS_LONGITUDE
        defaultIsolationShouldNotBeFound("addressLongitude.in=" + UPDATED_ADDRESS_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where addressLongitude is not null
        defaultIsolationShouldBeFound("addressLongitude.specified=true");

        // Get all the isolationList where addressLongitude is null
        defaultIsolationShouldNotBeFound("addressLongitude.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressLongitudeContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where addressLongitude contains DEFAULT_ADDRESS_LONGITUDE
        defaultIsolationShouldBeFound("addressLongitude.contains=" + DEFAULT_ADDRESS_LONGITUDE);

        // Get all the isolationList where addressLongitude contains UPDATED_ADDRESS_LONGITUDE
        defaultIsolationShouldNotBeFound("addressLongitude.contains=" + UPDATED_ADDRESS_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByAddressLongitudeNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where addressLongitude does not contain DEFAULT_ADDRESS_LONGITUDE
        defaultIsolationShouldNotBeFound("addressLongitude.doesNotContain=" + DEFAULT_ADDRESS_LONGITUDE);

        // Get all the isolationList where addressLongitude does not contain UPDATED_ADDRESS_LONGITUDE
        defaultIsolationShouldBeFound("addressLongitude.doesNotContain=" + UPDATED_ADDRESS_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCurrentLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where currentLatitude equals to DEFAULT_CURRENT_LATITUDE
        defaultIsolationShouldBeFound("currentLatitude.equals=" + DEFAULT_CURRENT_LATITUDE);

        // Get all the isolationList where currentLatitude equals to UPDATED_CURRENT_LATITUDE
        defaultIsolationShouldNotBeFound("currentLatitude.equals=" + UPDATED_CURRENT_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCurrentLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where currentLatitude not equals to DEFAULT_CURRENT_LATITUDE
        defaultIsolationShouldNotBeFound("currentLatitude.notEquals=" + DEFAULT_CURRENT_LATITUDE);

        // Get all the isolationList where currentLatitude not equals to UPDATED_CURRENT_LATITUDE
        defaultIsolationShouldBeFound("currentLatitude.notEquals=" + UPDATED_CURRENT_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCurrentLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where currentLatitude in DEFAULT_CURRENT_LATITUDE or UPDATED_CURRENT_LATITUDE
        defaultIsolationShouldBeFound("currentLatitude.in=" + DEFAULT_CURRENT_LATITUDE + "," + UPDATED_CURRENT_LATITUDE);

        // Get all the isolationList where currentLatitude equals to UPDATED_CURRENT_LATITUDE
        defaultIsolationShouldNotBeFound("currentLatitude.in=" + UPDATED_CURRENT_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCurrentLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where currentLatitude is not null
        defaultIsolationShouldBeFound("currentLatitude.specified=true");

        // Get all the isolationList where currentLatitude is null
        defaultIsolationShouldNotBeFound("currentLatitude.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByCurrentLatitudeContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where currentLatitude contains DEFAULT_CURRENT_LATITUDE
        defaultIsolationShouldBeFound("currentLatitude.contains=" + DEFAULT_CURRENT_LATITUDE);

        // Get all the isolationList where currentLatitude contains UPDATED_CURRENT_LATITUDE
        defaultIsolationShouldNotBeFound("currentLatitude.contains=" + UPDATED_CURRENT_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCurrentLatitudeNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where currentLatitude does not contain DEFAULT_CURRENT_LATITUDE
        defaultIsolationShouldNotBeFound("currentLatitude.doesNotContain=" + DEFAULT_CURRENT_LATITUDE);

        // Get all the isolationList where currentLatitude does not contain UPDATED_CURRENT_LATITUDE
        defaultIsolationShouldBeFound("currentLatitude.doesNotContain=" + UPDATED_CURRENT_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCurrentLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where currentLongitude equals to DEFAULT_CURRENT_LONGITUDE
        defaultIsolationShouldBeFound("currentLongitude.equals=" + DEFAULT_CURRENT_LONGITUDE);

        // Get all the isolationList where currentLongitude equals to UPDATED_CURRENT_LONGITUDE
        defaultIsolationShouldNotBeFound("currentLongitude.equals=" + UPDATED_CURRENT_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCurrentLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where currentLongitude not equals to DEFAULT_CURRENT_LONGITUDE
        defaultIsolationShouldNotBeFound("currentLongitude.notEquals=" + DEFAULT_CURRENT_LONGITUDE);

        // Get all the isolationList where currentLongitude not equals to UPDATED_CURRENT_LONGITUDE
        defaultIsolationShouldBeFound("currentLongitude.notEquals=" + UPDATED_CURRENT_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCurrentLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where currentLongitude in DEFAULT_CURRENT_LONGITUDE or UPDATED_CURRENT_LONGITUDE
        defaultIsolationShouldBeFound("currentLongitude.in=" + DEFAULT_CURRENT_LONGITUDE + "," + UPDATED_CURRENT_LONGITUDE);

        // Get all the isolationList where currentLongitude equals to UPDATED_CURRENT_LONGITUDE
        defaultIsolationShouldNotBeFound("currentLongitude.in=" + UPDATED_CURRENT_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCurrentLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where currentLongitude is not null
        defaultIsolationShouldBeFound("currentLongitude.specified=true");

        // Get all the isolationList where currentLongitude is null
        defaultIsolationShouldNotBeFound("currentLongitude.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByCurrentLongitudeContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where currentLongitude contains DEFAULT_CURRENT_LONGITUDE
        defaultIsolationShouldBeFound("currentLongitude.contains=" + DEFAULT_CURRENT_LONGITUDE);

        // Get all the isolationList where currentLongitude contains UPDATED_CURRENT_LONGITUDE
        defaultIsolationShouldNotBeFound("currentLongitude.contains=" + UPDATED_CURRENT_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByCurrentLongitudeNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where currentLongitude does not contain DEFAULT_CURRENT_LONGITUDE
        defaultIsolationShouldNotBeFound("currentLongitude.doesNotContain=" + DEFAULT_CURRENT_LONGITUDE);

        // Get all the isolationList where currentLongitude does not contain UPDATED_CURRENT_LONGITUDE
        defaultIsolationShouldBeFound("currentLongitude.doesNotContain=" + UPDATED_CURRENT_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalizationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalizationDate equals to DEFAULT_HOSPITALIZATION_DATE
        defaultIsolationShouldBeFound("hospitalizationDate.equals=" + DEFAULT_HOSPITALIZATION_DATE);

        // Get all the isolationList where hospitalizationDate equals to UPDATED_HOSPITALIZATION_DATE
        defaultIsolationShouldNotBeFound("hospitalizationDate.equals=" + UPDATED_HOSPITALIZATION_DATE);
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalizationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalizationDate not equals to DEFAULT_HOSPITALIZATION_DATE
        defaultIsolationShouldNotBeFound("hospitalizationDate.notEquals=" + DEFAULT_HOSPITALIZATION_DATE);

        // Get all the isolationList where hospitalizationDate not equals to UPDATED_HOSPITALIZATION_DATE
        defaultIsolationShouldBeFound("hospitalizationDate.notEquals=" + UPDATED_HOSPITALIZATION_DATE);
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalizationDateIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalizationDate in DEFAULT_HOSPITALIZATION_DATE or UPDATED_HOSPITALIZATION_DATE
        defaultIsolationShouldBeFound("hospitalizationDate.in=" + DEFAULT_HOSPITALIZATION_DATE + "," + UPDATED_HOSPITALIZATION_DATE);

        // Get all the isolationList where hospitalizationDate equals to UPDATED_HOSPITALIZATION_DATE
        defaultIsolationShouldNotBeFound("hospitalizationDate.in=" + UPDATED_HOSPITALIZATION_DATE);
    }

    @Test
    @Transactional
    void getAllIsolationsByHospitalizationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where hospitalizationDate is not null
        defaultIsolationShouldBeFound("hospitalizationDate.specified=true");

        // Get all the isolationList where hospitalizationDate is null
        defaultIsolationShouldNotBeFound("hospitalizationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByHealthConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where healthCondition equals to DEFAULT_HEALTH_CONDITION
        defaultIsolationShouldBeFound("healthCondition.equals=" + DEFAULT_HEALTH_CONDITION);

        // Get all the isolationList where healthCondition equals to UPDATED_HEALTH_CONDITION
        defaultIsolationShouldNotBeFound("healthCondition.equals=" + UPDATED_HEALTH_CONDITION);
    }

    @Test
    @Transactional
    void getAllIsolationsByHealthConditionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where healthCondition not equals to DEFAULT_HEALTH_CONDITION
        defaultIsolationShouldNotBeFound("healthCondition.notEquals=" + DEFAULT_HEALTH_CONDITION);

        // Get all the isolationList where healthCondition not equals to UPDATED_HEALTH_CONDITION
        defaultIsolationShouldBeFound("healthCondition.notEquals=" + UPDATED_HEALTH_CONDITION);
    }

    @Test
    @Transactional
    void getAllIsolationsByHealthConditionIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where healthCondition in DEFAULT_HEALTH_CONDITION or UPDATED_HEALTH_CONDITION
        defaultIsolationShouldBeFound("healthCondition.in=" + DEFAULT_HEALTH_CONDITION + "," + UPDATED_HEALTH_CONDITION);

        // Get all the isolationList where healthCondition equals to UPDATED_HEALTH_CONDITION
        defaultIsolationShouldNotBeFound("healthCondition.in=" + UPDATED_HEALTH_CONDITION);
    }

    @Test
    @Transactional
    void getAllIsolationsByHealthConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where healthCondition is not null
        defaultIsolationShouldBeFound("healthCondition.specified=true");

        // Get all the isolationList where healthCondition is null
        defaultIsolationShouldNotBeFound("healthCondition.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where remarks equals to DEFAULT_REMARKS
        defaultIsolationShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the isolationList where remarks equals to UPDATED_REMARKS
        defaultIsolationShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllIsolationsByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where remarks not equals to DEFAULT_REMARKS
        defaultIsolationShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the isolationList where remarks not equals to UPDATED_REMARKS
        defaultIsolationShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllIsolationsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultIsolationShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the isolationList where remarks equals to UPDATED_REMARKS
        defaultIsolationShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllIsolationsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where remarks is not null
        defaultIsolationShouldBeFound("remarks.specified=true");

        // Get all the isolationList where remarks is null
        defaultIsolationShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByRemarksContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where remarks contains DEFAULT_REMARKS
        defaultIsolationShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the isolationList where remarks contains UPDATED_REMARKS
        defaultIsolationShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllIsolationsByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where remarks does not contain DEFAULT_REMARKS
        defaultIsolationShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the isolationList where remarks does not contain UPDATED_REMARKS
        defaultIsolationShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllIsolationsBySymptomaticIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where symptomatic equals to DEFAULT_SYMPTOMATIC
        defaultIsolationShouldBeFound("symptomatic.equals=" + DEFAULT_SYMPTOMATIC);

        // Get all the isolationList where symptomatic equals to UPDATED_SYMPTOMATIC
        defaultIsolationShouldNotBeFound("symptomatic.equals=" + UPDATED_SYMPTOMATIC);
    }

    @Test
    @Transactional
    void getAllIsolationsBySymptomaticIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where symptomatic not equals to DEFAULT_SYMPTOMATIC
        defaultIsolationShouldNotBeFound("symptomatic.notEquals=" + DEFAULT_SYMPTOMATIC);

        // Get all the isolationList where symptomatic not equals to UPDATED_SYMPTOMATIC
        defaultIsolationShouldBeFound("symptomatic.notEquals=" + UPDATED_SYMPTOMATIC);
    }

    @Test
    @Transactional
    void getAllIsolationsBySymptomaticIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where symptomatic in DEFAULT_SYMPTOMATIC or UPDATED_SYMPTOMATIC
        defaultIsolationShouldBeFound("symptomatic.in=" + DEFAULT_SYMPTOMATIC + "," + UPDATED_SYMPTOMATIC);

        // Get all the isolationList where symptomatic equals to UPDATED_SYMPTOMATIC
        defaultIsolationShouldNotBeFound("symptomatic.in=" + UPDATED_SYMPTOMATIC);
    }

    @Test
    @Transactional
    void getAllIsolationsBySymptomaticIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where symptomatic is not null
        defaultIsolationShouldBeFound("symptomatic.specified=true");

        // Get all the isolationList where symptomatic is null
        defaultIsolationShouldNotBeFound("symptomatic.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByCcmsLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where ccmsLogin equals to DEFAULT_CCMS_LOGIN
        defaultIsolationShouldBeFound("ccmsLogin.equals=" + DEFAULT_CCMS_LOGIN);

        // Get all the isolationList where ccmsLogin equals to UPDATED_CCMS_LOGIN
        defaultIsolationShouldNotBeFound("ccmsLogin.equals=" + UPDATED_CCMS_LOGIN);
    }

    @Test
    @Transactional
    void getAllIsolationsByCcmsLoginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where ccmsLogin not equals to DEFAULT_CCMS_LOGIN
        defaultIsolationShouldNotBeFound("ccmsLogin.notEquals=" + DEFAULT_CCMS_LOGIN);

        // Get all the isolationList where ccmsLogin not equals to UPDATED_CCMS_LOGIN
        defaultIsolationShouldBeFound("ccmsLogin.notEquals=" + UPDATED_CCMS_LOGIN);
    }

    @Test
    @Transactional
    void getAllIsolationsByCcmsLoginIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where ccmsLogin in DEFAULT_CCMS_LOGIN or UPDATED_CCMS_LOGIN
        defaultIsolationShouldBeFound("ccmsLogin.in=" + DEFAULT_CCMS_LOGIN + "," + UPDATED_CCMS_LOGIN);

        // Get all the isolationList where ccmsLogin equals to UPDATED_CCMS_LOGIN
        defaultIsolationShouldNotBeFound("ccmsLogin.in=" + UPDATED_CCMS_LOGIN);
    }

    @Test
    @Transactional
    void getAllIsolationsByCcmsLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where ccmsLogin is not null
        defaultIsolationShouldBeFound("ccmsLogin.specified=true");

        // Get all the isolationList where ccmsLogin is null
        defaultIsolationShouldNotBeFound("ccmsLogin.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByCcmsLoginContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where ccmsLogin contains DEFAULT_CCMS_LOGIN
        defaultIsolationShouldBeFound("ccmsLogin.contains=" + DEFAULT_CCMS_LOGIN);

        // Get all the isolationList where ccmsLogin contains UPDATED_CCMS_LOGIN
        defaultIsolationShouldNotBeFound("ccmsLogin.contains=" + UPDATED_CCMS_LOGIN);
    }

    @Test
    @Transactional
    void getAllIsolationsByCcmsLoginNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where ccmsLogin does not contain DEFAULT_CCMS_LOGIN
        defaultIsolationShouldNotBeFound("ccmsLogin.doesNotContain=" + DEFAULT_CCMS_LOGIN);

        // Get all the isolationList where ccmsLogin does not contain UPDATED_CCMS_LOGIN
        defaultIsolationShouldBeFound("ccmsLogin.doesNotContain=" + UPDATED_CCMS_LOGIN);
    }

    @Test
    @Transactional
    void getAllIsolationsBySelfRegisteredIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where selfRegistered equals to DEFAULT_SELF_REGISTERED
        defaultIsolationShouldBeFound("selfRegistered.equals=" + DEFAULT_SELF_REGISTERED);

        // Get all the isolationList where selfRegistered equals to UPDATED_SELF_REGISTERED
        defaultIsolationShouldNotBeFound("selfRegistered.equals=" + UPDATED_SELF_REGISTERED);
    }

    @Test
    @Transactional
    void getAllIsolationsBySelfRegisteredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where selfRegistered not equals to DEFAULT_SELF_REGISTERED
        defaultIsolationShouldNotBeFound("selfRegistered.notEquals=" + DEFAULT_SELF_REGISTERED);

        // Get all the isolationList where selfRegistered not equals to UPDATED_SELF_REGISTERED
        defaultIsolationShouldBeFound("selfRegistered.notEquals=" + UPDATED_SELF_REGISTERED);
    }

    @Test
    @Transactional
    void getAllIsolationsBySelfRegisteredIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where selfRegistered in DEFAULT_SELF_REGISTERED or UPDATED_SELF_REGISTERED
        defaultIsolationShouldBeFound("selfRegistered.in=" + DEFAULT_SELF_REGISTERED + "," + UPDATED_SELF_REGISTERED);

        // Get all the isolationList where selfRegistered equals to UPDATED_SELF_REGISTERED
        defaultIsolationShouldNotBeFound("selfRegistered.in=" + UPDATED_SELF_REGISTERED);
    }

    @Test
    @Transactional
    void getAllIsolationsBySelfRegisteredIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where selfRegistered is not null
        defaultIsolationShouldBeFound("selfRegistered.specified=true");

        // Get all the isolationList where selfRegistered is null
        defaultIsolationShouldNotBeFound("selfRegistered.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultIsolationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the isolationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultIsolationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllIsolationsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultIsolationShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the isolationList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultIsolationShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllIsolationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultIsolationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the isolationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultIsolationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllIsolationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastModified is not null
        defaultIsolationShouldBeFound("lastModified.specified=true");

        // Get all the isolationList where lastModified is null
        defaultIsolationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultIsolationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the isolationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultIsolationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllIsolationsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultIsolationShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the isolationList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultIsolationShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllIsolationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultIsolationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the isolationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultIsolationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllIsolationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastModifiedBy is not null
        defaultIsolationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the isolationList where lastModifiedBy is null
        defaultIsolationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultIsolationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the isolationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultIsolationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllIsolationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultIsolationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the isolationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultIsolationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllIsolationsByIsolationStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where isolationStartDate equals to DEFAULT_ISOLATION_START_DATE
        defaultIsolationShouldBeFound("isolationStartDate.equals=" + DEFAULT_ISOLATION_START_DATE);

        // Get all the isolationList where isolationStartDate equals to UPDATED_ISOLATION_START_DATE
        defaultIsolationShouldNotBeFound("isolationStartDate.equals=" + UPDATED_ISOLATION_START_DATE);
    }

    @Test
    @Transactional
    void getAllIsolationsByIsolationStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where isolationStartDate not equals to DEFAULT_ISOLATION_START_DATE
        defaultIsolationShouldNotBeFound("isolationStartDate.notEquals=" + DEFAULT_ISOLATION_START_DATE);

        // Get all the isolationList where isolationStartDate not equals to UPDATED_ISOLATION_START_DATE
        defaultIsolationShouldBeFound("isolationStartDate.notEquals=" + UPDATED_ISOLATION_START_DATE);
    }

    @Test
    @Transactional
    void getAllIsolationsByIsolationStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where isolationStartDate in DEFAULT_ISOLATION_START_DATE or UPDATED_ISOLATION_START_DATE
        defaultIsolationShouldBeFound("isolationStartDate.in=" + DEFAULT_ISOLATION_START_DATE + "," + UPDATED_ISOLATION_START_DATE);

        // Get all the isolationList where isolationStartDate equals to UPDATED_ISOLATION_START_DATE
        defaultIsolationShouldNotBeFound("isolationStartDate.in=" + UPDATED_ISOLATION_START_DATE);
    }

    @Test
    @Transactional
    void getAllIsolationsByIsolationStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where isolationStartDate is not null
        defaultIsolationShouldBeFound("isolationStartDate.specified=true");

        // Get all the isolationList where isolationStartDate is null
        defaultIsolationShouldNotBeFound("isolationStartDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByIsolationEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where isolationEndDate equals to DEFAULT_ISOLATION_END_DATE
        defaultIsolationShouldBeFound("isolationEndDate.equals=" + DEFAULT_ISOLATION_END_DATE);

        // Get all the isolationList where isolationEndDate equals to UPDATED_ISOLATION_END_DATE
        defaultIsolationShouldNotBeFound("isolationEndDate.equals=" + UPDATED_ISOLATION_END_DATE);
    }

    @Test
    @Transactional
    void getAllIsolationsByIsolationEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where isolationEndDate not equals to DEFAULT_ISOLATION_END_DATE
        defaultIsolationShouldNotBeFound("isolationEndDate.notEquals=" + DEFAULT_ISOLATION_END_DATE);

        // Get all the isolationList where isolationEndDate not equals to UPDATED_ISOLATION_END_DATE
        defaultIsolationShouldBeFound("isolationEndDate.notEquals=" + UPDATED_ISOLATION_END_DATE);
    }

    @Test
    @Transactional
    void getAllIsolationsByIsolationEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where isolationEndDate in DEFAULT_ISOLATION_END_DATE or UPDATED_ISOLATION_END_DATE
        defaultIsolationShouldBeFound("isolationEndDate.in=" + DEFAULT_ISOLATION_END_DATE + "," + UPDATED_ISOLATION_END_DATE);

        // Get all the isolationList where isolationEndDate equals to UPDATED_ISOLATION_END_DATE
        defaultIsolationShouldNotBeFound("isolationEndDate.in=" + UPDATED_ISOLATION_END_DATE);
    }

    @Test
    @Transactional
    void getAllIsolationsByIsolationEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where isolationEndDate is not null
        defaultIsolationShouldBeFound("isolationEndDate.specified=true");

        // Get all the isolationList where isolationEndDate is null
        defaultIsolationShouldNotBeFound("isolationEndDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByTvgIsolationUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where tvgIsolationUserId equals to DEFAULT_TVG_ISOLATION_USER_ID
        defaultIsolationShouldBeFound("tvgIsolationUserId.equals=" + DEFAULT_TVG_ISOLATION_USER_ID);

        // Get all the isolationList where tvgIsolationUserId equals to UPDATED_TVG_ISOLATION_USER_ID
        defaultIsolationShouldNotBeFound("tvgIsolationUserId.equals=" + UPDATED_TVG_ISOLATION_USER_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByTvgIsolationUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where tvgIsolationUserId not equals to DEFAULT_TVG_ISOLATION_USER_ID
        defaultIsolationShouldNotBeFound("tvgIsolationUserId.notEquals=" + DEFAULT_TVG_ISOLATION_USER_ID);

        // Get all the isolationList where tvgIsolationUserId not equals to UPDATED_TVG_ISOLATION_USER_ID
        defaultIsolationShouldBeFound("tvgIsolationUserId.notEquals=" + UPDATED_TVG_ISOLATION_USER_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByTvgIsolationUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where tvgIsolationUserId in DEFAULT_TVG_ISOLATION_USER_ID or UPDATED_TVG_ISOLATION_USER_ID
        defaultIsolationShouldBeFound("tvgIsolationUserId.in=" + DEFAULT_TVG_ISOLATION_USER_ID + "," + UPDATED_TVG_ISOLATION_USER_ID);

        // Get all the isolationList where tvgIsolationUserId equals to UPDATED_TVG_ISOLATION_USER_ID
        defaultIsolationShouldNotBeFound("tvgIsolationUserId.in=" + UPDATED_TVG_ISOLATION_USER_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByTvgIsolationUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where tvgIsolationUserId is not null
        defaultIsolationShouldBeFound("tvgIsolationUserId.specified=true");

        // Get all the isolationList where tvgIsolationUserId is null
        defaultIsolationShouldNotBeFound("tvgIsolationUserId.specified=false");
    }

    @Test
    @Transactional
    void getAllIsolationsByTvgIsolationUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where tvgIsolationUserId is greater than or equal to DEFAULT_TVG_ISOLATION_USER_ID
        defaultIsolationShouldBeFound("tvgIsolationUserId.greaterThanOrEqual=" + DEFAULT_TVG_ISOLATION_USER_ID);

        // Get all the isolationList where tvgIsolationUserId is greater than or equal to UPDATED_TVG_ISOLATION_USER_ID
        defaultIsolationShouldNotBeFound("tvgIsolationUserId.greaterThanOrEqual=" + UPDATED_TVG_ISOLATION_USER_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByTvgIsolationUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where tvgIsolationUserId is less than or equal to DEFAULT_TVG_ISOLATION_USER_ID
        defaultIsolationShouldBeFound("tvgIsolationUserId.lessThanOrEqual=" + DEFAULT_TVG_ISOLATION_USER_ID);

        // Get all the isolationList where tvgIsolationUserId is less than or equal to SMALLER_TVG_ISOLATION_USER_ID
        defaultIsolationShouldNotBeFound("tvgIsolationUserId.lessThanOrEqual=" + SMALLER_TVG_ISOLATION_USER_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByTvgIsolationUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where tvgIsolationUserId is less than DEFAULT_TVG_ISOLATION_USER_ID
        defaultIsolationShouldNotBeFound("tvgIsolationUserId.lessThan=" + DEFAULT_TVG_ISOLATION_USER_ID);

        // Get all the isolationList where tvgIsolationUserId is less than UPDATED_TVG_ISOLATION_USER_ID
        defaultIsolationShouldBeFound("tvgIsolationUserId.lessThan=" + UPDATED_TVG_ISOLATION_USER_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByTvgIsolationUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        // Get all the isolationList where tvgIsolationUserId is greater than DEFAULT_TVG_ISOLATION_USER_ID
        defaultIsolationShouldNotBeFound("tvgIsolationUserId.greaterThan=" + DEFAULT_TVG_ISOLATION_USER_ID);

        // Get all the isolationList where tvgIsolationUserId is greater than SMALLER_TVG_ISOLATION_USER_ID
        defaultIsolationShouldBeFound("tvgIsolationUserId.greaterThan=" + SMALLER_TVG_ISOLATION_USER_ID);
    }

    @Test
    @Transactional
    void getAllIsolationsByIsolationDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);
        IsolationDetails isolationDetails;
        if (TestUtil.findAll(em, IsolationDetails.class).isEmpty()) {
            isolationDetails = IsolationDetailsResourceIT.createEntity(em);
            em.persist(isolationDetails);
            em.flush();
        } else {
            isolationDetails = TestUtil.findAll(em, IsolationDetails.class).get(0);
        }
        em.persist(isolationDetails);
        em.flush();
        isolation.setIsolationDetails(isolationDetails);
        isolationRepository.saveAndFlush(isolation);
        Long isolationDetailsId = isolationDetails.getId();

        // Get all the isolationList where isolationDetails equals to isolationDetailsId
        defaultIsolationShouldBeFound("isolationDetailsId.equals=" + isolationDetailsId);

        // Get all the isolationList where isolationDetails equals to (isolationDetailsId + 1)
        defaultIsolationShouldNotBeFound("isolationDetailsId.equals=" + (isolationDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIsolationShouldBeFound(String filter) throws Exception {
        restIsolationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isolation.getId().intValue())))
            .andExpect(jsonPath("$.[*].icmrId").value(hasItem(DEFAULT_ICMR_ID)))
            .andExpect(jsonPath("$.[*].rtpcrId").value(hasItem(DEFAULT_RTPCR_ID)))
            .andExpect(jsonPath("$.[*].ratId").value(hasItem(DEFAULT_RAT_ID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH)))
            .andExpect(jsonPath("$.[*].secondaryContactNo").value(hasItem(DEFAULT_SECONDARY_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].aadharCardNo").value(hasItem(DEFAULT_AADHAR_CARD_NO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].stateId").value(hasItem(DEFAULT_STATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID.intValue())))
            .andExpect(jsonPath("$.[*].talukaId").value(hasItem(DEFAULT_TALUKA_ID.intValue())))
            .andExpect(jsonPath("$.[*].cityId").value(hasItem(DEFAULT_CITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].collectionDate").value(hasItem(DEFAULT_COLLECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].hospitalized").value(hasItem(DEFAULT_HOSPITALIZED.booleanValue())))
            .andExpect(jsonPath("$.[*].hospitalId").value(hasItem(DEFAULT_HOSPITAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].addressLatitude").value(hasItem(DEFAULT_ADDRESS_LATITUDE)))
            .andExpect(jsonPath("$.[*].addressLongitude").value(hasItem(DEFAULT_ADDRESS_LONGITUDE)))
            .andExpect(jsonPath("$.[*].currentLatitude").value(hasItem(DEFAULT_CURRENT_LATITUDE)))
            .andExpect(jsonPath("$.[*].currentLongitude").value(hasItem(DEFAULT_CURRENT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].hospitalizationDate").value(hasItem(DEFAULT_HOSPITALIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].healthCondition").value(hasItem(DEFAULT_HEALTH_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].symptomatic").value(hasItem(DEFAULT_SYMPTOMATIC.booleanValue())))
            .andExpect(jsonPath("$.[*].ccmsLogin").value(hasItem(DEFAULT_CCMS_LOGIN)))
            .andExpect(jsonPath("$.[*].selfRegistered").value(hasItem(DEFAULT_SELF_REGISTERED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isolationStartDate").value(hasItem(DEFAULT_ISOLATION_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].isolationEndDate").value(hasItem(DEFAULT_ISOLATION_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].tvgIsolationUserId").value(hasItem(DEFAULT_TVG_ISOLATION_USER_ID.intValue())));

        // Check, that the count call also returns 1
        restIsolationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIsolationShouldNotBeFound(String filter) throws Exception {
        restIsolationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIsolationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIsolation() throws Exception {
        // Get the isolation
        restIsolationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIsolation() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        int databaseSizeBeforeUpdate = isolationRepository.findAll().size();

        // Update the isolation
        Isolation updatedIsolation = isolationRepository.findById(isolation.getId()).get();
        // Disconnect from session so that the updates on updatedIsolation are not directly saved in db
        em.detach(updatedIsolation);
        updatedIsolation
            .icmrId(UPDATED_ICMR_ID)
            .rtpcrId(UPDATED_RTPCR_ID)
            .ratId(UPDATED_RAT_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .activated(UPDATED_ACTIVATED)
            .mobileNo(UPDATED_MOBILE_NO)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .secondaryContactNo(UPDATED_SECONDARY_CONTACT_NO)
            .aadharCardNo(UPDATED_AADHAR_CARD_NO)
            .status(UPDATED_STATUS)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .stateId(UPDATED_STATE_ID)
            .districtId(UPDATED_DISTRICT_ID)
            .talukaId(UPDATED_TALUKA_ID)
            .cityId(UPDATED_CITY_ID)
            .address(UPDATED_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .collectionDate(UPDATED_COLLECTION_DATE)
            .hospitalized(UPDATED_HOSPITALIZED)
            .hospitalId(UPDATED_HOSPITAL_ID)
            .addressLatitude(UPDATED_ADDRESS_LATITUDE)
            .addressLongitude(UPDATED_ADDRESS_LONGITUDE)
            .currentLatitude(UPDATED_CURRENT_LATITUDE)
            .currentLongitude(UPDATED_CURRENT_LONGITUDE)
            .hospitalizationDate(UPDATED_HOSPITALIZATION_DATE)
            .healthCondition(UPDATED_HEALTH_CONDITION)
            .remarks(UPDATED_REMARKS)
            .symptomatic(UPDATED_SYMPTOMATIC)
            .ccmsLogin(UPDATED_CCMS_LOGIN)
            .selfRegistered(UPDATED_SELF_REGISTERED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isolationStartDate(UPDATED_ISOLATION_START_DATE)
            .isolationEndDate(UPDATED_ISOLATION_END_DATE)
            .tvgIsolationUserId(UPDATED_TVG_ISOLATION_USER_ID);
        IsolationDTO isolationDTO = isolationMapper.toDto(updatedIsolation);

        restIsolationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, isolationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(isolationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Isolation in the database
        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeUpdate);
        Isolation testIsolation = isolationList.get(isolationList.size() - 1);
        assertThat(testIsolation.getIcmrId()).isEqualTo(UPDATED_ICMR_ID);
        assertThat(testIsolation.getRtpcrId()).isEqualTo(UPDATED_RTPCR_ID);
        assertThat(testIsolation.getRatId()).isEqualTo(UPDATED_RAT_ID);
        assertThat(testIsolation.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIsolation.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIsolation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testIsolation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testIsolation.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testIsolation.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testIsolation.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testIsolation.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testIsolation.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testIsolation.getSecondaryContactNo()).isEqualTo(UPDATED_SECONDARY_CONTACT_NO);
        assertThat(testIsolation.getAadharCardNo()).isEqualTo(UPDATED_AADHAR_CARD_NO);
        assertThat(testIsolation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testIsolation.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testIsolation.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIsolation.getStateId()).isEqualTo(UPDATED_STATE_ID);
        assertThat(testIsolation.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
        assertThat(testIsolation.getTalukaId()).isEqualTo(UPDATED_TALUKA_ID);
        assertThat(testIsolation.getCityId()).isEqualTo(UPDATED_CITY_ID);
        assertThat(testIsolation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testIsolation.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testIsolation.getCollectionDate()).isEqualTo(UPDATED_COLLECTION_DATE);
        assertThat(testIsolation.getHospitalized()).isEqualTo(UPDATED_HOSPITALIZED);
        assertThat(testIsolation.getHospitalId()).isEqualTo(UPDATED_HOSPITAL_ID);
        assertThat(testIsolation.getAddressLatitude()).isEqualTo(UPDATED_ADDRESS_LATITUDE);
        assertThat(testIsolation.getAddressLongitude()).isEqualTo(UPDATED_ADDRESS_LONGITUDE);
        assertThat(testIsolation.getCurrentLatitude()).isEqualTo(UPDATED_CURRENT_LATITUDE);
        assertThat(testIsolation.getCurrentLongitude()).isEqualTo(UPDATED_CURRENT_LONGITUDE);
        assertThat(testIsolation.getHospitalizationDate()).isEqualTo(UPDATED_HOSPITALIZATION_DATE);
        assertThat(testIsolation.getHealthCondition()).isEqualTo(UPDATED_HEALTH_CONDITION);
        assertThat(testIsolation.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testIsolation.getSymptomatic()).isEqualTo(UPDATED_SYMPTOMATIC);
        assertThat(testIsolation.getCcmsLogin()).isEqualTo(UPDATED_CCMS_LOGIN);
        assertThat(testIsolation.getSelfRegistered()).isEqualTo(UPDATED_SELF_REGISTERED);
        assertThat(testIsolation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testIsolation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testIsolation.getIsolationStartDate()).isEqualTo(UPDATED_ISOLATION_START_DATE);
        assertThat(testIsolation.getIsolationEndDate()).isEqualTo(UPDATED_ISOLATION_END_DATE);
        assertThat(testIsolation.getTvgIsolationUserId()).isEqualTo(UPDATED_TVG_ISOLATION_USER_ID);
    }

    @Test
    @Transactional
    void putNonExistingIsolation() throws Exception {
        int databaseSizeBeforeUpdate = isolationRepository.findAll().size();
        isolation.setId(count.incrementAndGet());

        // Create the Isolation
        IsolationDTO isolationDTO = isolationMapper.toDto(isolation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIsolationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, isolationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(isolationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Isolation in the database
        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIsolation() throws Exception {
        int databaseSizeBeforeUpdate = isolationRepository.findAll().size();
        isolation.setId(count.incrementAndGet());

        // Create the Isolation
        IsolationDTO isolationDTO = isolationMapper.toDto(isolation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsolationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(isolationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Isolation in the database
        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIsolation() throws Exception {
        int databaseSizeBeforeUpdate = isolationRepository.findAll().size();
        isolation.setId(count.incrementAndGet());

        // Create the Isolation
        IsolationDTO isolationDTO = isolationMapper.toDto(isolation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsolationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isolationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Isolation in the database
        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIsolationWithPatch() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        int databaseSizeBeforeUpdate = isolationRepository.findAll().size();

        // Update the isolation using partial update
        Isolation partialUpdatedIsolation = new Isolation();
        partialUpdatedIsolation.setId(isolation.getId());

        partialUpdatedIsolation
            .lastName(UPDATED_LAST_NAME)
            .longitude(UPDATED_LONGITUDE)
            .activated(UPDATED_ACTIVATED)
            .status(UPDATED_STATUS)
            .gender(UPDATED_GENDER)
            .stateId(UPDATED_STATE_ID)
            .districtId(UPDATED_DISTRICT_ID)
            .talukaId(UPDATED_TALUKA_ID)
            .address(UPDATED_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .hospitalized(UPDATED_HOSPITALIZED)
            .addressLongitude(UPDATED_ADDRESS_LONGITUDE)
            .currentLatitude(UPDATED_CURRENT_LATITUDE)
            .currentLongitude(UPDATED_CURRENT_LONGITUDE)
            .symptomatic(UPDATED_SYMPTOMATIC)
            .selfRegistered(UPDATED_SELF_REGISTERED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isolationStartDate(UPDATED_ISOLATION_START_DATE)
            .isolationEndDate(UPDATED_ISOLATION_END_DATE);

        restIsolationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIsolation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIsolation))
            )
            .andExpect(status().isOk());

        // Validate the Isolation in the database
        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeUpdate);
        Isolation testIsolation = isolationList.get(isolationList.size() - 1);
        assertThat(testIsolation.getIcmrId()).isEqualTo(DEFAULT_ICMR_ID);
        assertThat(testIsolation.getRtpcrId()).isEqualTo(DEFAULT_RTPCR_ID);
        assertThat(testIsolation.getRatId()).isEqualTo(DEFAULT_RAT_ID);
        assertThat(testIsolation.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testIsolation.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIsolation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testIsolation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testIsolation.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testIsolation.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testIsolation.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testIsolation.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testIsolation.getPasswordHash()).isEqualTo(DEFAULT_PASSWORD_HASH);
        assertThat(testIsolation.getSecondaryContactNo()).isEqualTo(DEFAULT_SECONDARY_CONTACT_NO);
        assertThat(testIsolation.getAadharCardNo()).isEqualTo(DEFAULT_AADHAR_CARD_NO);
        assertThat(testIsolation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testIsolation.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testIsolation.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIsolation.getStateId()).isEqualTo(UPDATED_STATE_ID);
        assertThat(testIsolation.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
        assertThat(testIsolation.getTalukaId()).isEqualTo(UPDATED_TALUKA_ID);
        assertThat(testIsolation.getCityId()).isEqualTo(DEFAULT_CITY_ID);
        assertThat(testIsolation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testIsolation.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testIsolation.getCollectionDate()).isEqualTo(DEFAULT_COLLECTION_DATE);
        assertThat(testIsolation.getHospitalized()).isEqualTo(UPDATED_HOSPITALIZED);
        assertThat(testIsolation.getHospitalId()).isEqualTo(DEFAULT_HOSPITAL_ID);
        assertThat(testIsolation.getAddressLatitude()).isEqualTo(DEFAULT_ADDRESS_LATITUDE);
        assertThat(testIsolation.getAddressLongitude()).isEqualTo(UPDATED_ADDRESS_LONGITUDE);
        assertThat(testIsolation.getCurrentLatitude()).isEqualTo(UPDATED_CURRENT_LATITUDE);
        assertThat(testIsolation.getCurrentLongitude()).isEqualTo(UPDATED_CURRENT_LONGITUDE);
        assertThat(testIsolation.getHospitalizationDate()).isEqualTo(DEFAULT_HOSPITALIZATION_DATE);
        assertThat(testIsolation.getHealthCondition()).isEqualTo(DEFAULT_HEALTH_CONDITION);
        assertThat(testIsolation.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testIsolation.getSymptomatic()).isEqualTo(UPDATED_SYMPTOMATIC);
        assertThat(testIsolation.getCcmsLogin()).isEqualTo(DEFAULT_CCMS_LOGIN);
        assertThat(testIsolation.getSelfRegistered()).isEqualTo(UPDATED_SELF_REGISTERED);
        assertThat(testIsolation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testIsolation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testIsolation.getIsolationStartDate()).isEqualTo(UPDATED_ISOLATION_START_DATE);
        assertThat(testIsolation.getIsolationEndDate()).isEqualTo(UPDATED_ISOLATION_END_DATE);
        assertThat(testIsolation.getTvgIsolationUserId()).isEqualTo(DEFAULT_TVG_ISOLATION_USER_ID);
    }

    @Test
    @Transactional
    void fullUpdateIsolationWithPatch() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        int databaseSizeBeforeUpdate = isolationRepository.findAll().size();

        // Update the isolation using partial update
        Isolation partialUpdatedIsolation = new Isolation();
        partialUpdatedIsolation.setId(isolation.getId());

        partialUpdatedIsolation
            .icmrId(UPDATED_ICMR_ID)
            .rtpcrId(UPDATED_RTPCR_ID)
            .ratId(UPDATED_RAT_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .activated(UPDATED_ACTIVATED)
            .mobileNo(UPDATED_MOBILE_NO)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .secondaryContactNo(UPDATED_SECONDARY_CONTACT_NO)
            .aadharCardNo(UPDATED_AADHAR_CARD_NO)
            .status(UPDATED_STATUS)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .stateId(UPDATED_STATE_ID)
            .districtId(UPDATED_DISTRICT_ID)
            .talukaId(UPDATED_TALUKA_ID)
            .cityId(UPDATED_CITY_ID)
            .address(UPDATED_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .collectionDate(UPDATED_COLLECTION_DATE)
            .hospitalized(UPDATED_HOSPITALIZED)
            .hospitalId(UPDATED_HOSPITAL_ID)
            .addressLatitude(UPDATED_ADDRESS_LATITUDE)
            .addressLongitude(UPDATED_ADDRESS_LONGITUDE)
            .currentLatitude(UPDATED_CURRENT_LATITUDE)
            .currentLongitude(UPDATED_CURRENT_LONGITUDE)
            .hospitalizationDate(UPDATED_HOSPITALIZATION_DATE)
            .healthCondition(UPDATED_HEALTH_CONDITION)
            .remarks(UPDATED_REMARKS)
            .symptomatic(UPDATED_SYMPTOMATIC)
            .ccmsLogin(UPDATED_CCMS_LOGIN)
            .selfRegistered(UPDATED_SELF_REGISTERED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isolationStartDate(UPDATED_ISOLATION_START_DATE)
            .isolationEndDate(UPDATED_ISOLATION_END_DATE)
            .tvgIsolationUserId(UPDATED_TVG_ISOLATION_USER_ID);

        restIsolationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIsolation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIsolation))
            )
            .andExpect(status().isOk());

        // Validate the Isolation in the database
        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeUpdate);
        Isolation testIsolation = isolationList.get(isolationList.size() - 1);
        assertThat(testIsolation.getIcmrId()).isEqualTo(UPDATED_ICMR_ID);
        assertThat(testIsolation.getRtpcrId()).isEqualTo(UPDATED_RTPCR_ID);
        assertThat(testIsolation.getRatId()).isEqualTo(UPDATED_RAT_ID);
        assertThat(testIsolation.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIsolation.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIsolation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testIsolation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testIsolation.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testIsolation.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testIsolation.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testIsolation.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testIsolation.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testIsolation.getSecondaryContactNo()).isEqualTo(UPDATED_SECONDARY_CONTACT_NO);
        assertThat(testIsolation.getAadharCardNo()).isEqualTo(UPDATED_AADHAR_CARD_NO);
        assertThat(testIsolation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testIsolation.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testIsolation.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIsolation.getStateId()).isEqualTo(UPDATED_STATE_ID);
        assertThat(testIsolation.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
        assertThat(testIsolation.getTalukaId()).isEqualTo(UPDATED_TALUKA_ID);
        assertThat(testIsolation.getCityId()).isEqualTo(UPDATED_CITY_ID);
        assertThat(testIsolation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testIsolation.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testIsolation.getCollectionDate()).isEqualTo(UPDATED_COLLECTION_DATE);
        assertThat(testIsolation.getHospitalized()).isEqualTo(UPDATED_HOSPITALIZED);
        assertThat(testIsolation.getHospitalId()).isEqualTo(UPDATED_HOSPITAL_ID);
        assertThat(testIsolation.getAddressLatitude()).isEqualTo(UPDATED_ADDRESS_LATITUDE);
        assertThat(testIsolation.getAddressLongitude()).isEqualTo(UPDATED_ADDRESS_LONGITUDE);
        assertThat(testIsolation.getCurrentLatitude()).isEqualTo(UPDATED_CURRENT_LATITUDE);
        assertThat(testIsolation.getCurrentLongitude()).isEqualTo(UPDATED_CURRENT_LONGITUDE);
        assertThat(testIsolation.getHospitalizationDate()).isEqualTo(UPDATED_HOSPITALIZATION_DATE);
        assertThat(testIsolation.getHealthCondition()).isEqualTo(UPDATED_HEALTH_CONDITION);
        assertThat(testIsolation.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testIsolation.getSymptomatic()).isEqualTo(UPDATED_SYMPTOMATIC);
        assertThat(testIsolation.getCcmsLogin()).isEqualTo(UPDATED_CCMS_LOGIN);
        assertThat(testIsolation.getSelfRegistered()).isEqualTo(UPDATED_SELF_REGISTERED);
        assertThat(testIsolation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testIsolation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testIsolation.getIsolationStartDate()).isEqualTo(UPDATED_ISOLATION_START_DATE);
        assertThat(testIsolation.getIsolationEndDate()).isEqualTo(UPDATED_ISOLATION_END_DATE);
        assertThat(testIsolation.getTvgIsolationUserId()).isEqualTo(UPDATED_TVG_ISOLATION_USER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIsolation() throws Exception {
        int databaseSizeBeforeUpdate = isolationRepository.findAll().size();
        isolation.setId(count.incrementAndGet());

        // Create the Isolation
        IsolationDTO isolationDTO = isolationMapper.toDto(isolation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIsolationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, isolationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isolationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Isolation in the database
        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIsolation() throws Exception {
        int databaseSizeBeforeUpdate = isolationRepository.findAll().size();
        isolation.setId(count.incrementAndGet());

        // Create the Isolation
        IsolationDTO isolationDTO = isolationMapper.toDto(isolation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsolationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isolationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Isolation in the database
        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIsolation() throws Exception {
        int databaseSizeBeforeUpdate = isolationRepository.findAll().size();
        isolation.setId(count.incrementAndGet());

        // Create the Isolation
        IsolationDTO isolationDTO = isolationMapper.toDto(isolation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsolationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(isolationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Isolation in the database
        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIsolation() throws Exception {
        // Initialize the database
        isolationRepository.saveAndFlush(isolation);

        int databaseSizeBeforeDelete = isolationRepository.findAll().size();

        // Delete the isolation
        restIsolationMockMvc
            .perform(delete(ENTITY_API_URL_ID, isolation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Isolation> isolationList = isolationRepository.findAll();
        assertThat(isolationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
