package com.techvg.covid.care.isolation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.isolation.IntegrationTest;
import com.techvg.covid.care.isolation.domain.Question;
import com.techvg.covid.care.isolation.domain.QuestionsOptions;
import com.techvg.covid.care.isolation.domain.enumeration.HealthCondition;
import com.techvg.covid.care.isolation.repository.QuestionsOptionsRepository;
import com.techvg.covid.care.isolation.service.criteria.QuestionsOptionsCriteria;
import com.techvg.covid.care.isolation.service.dto.QuestionsOptionsDTO;
import com.techvg.covid.care.isolation.service.mapper.QuestionsOptionsMapper;
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
 * Integration tests for the {@link QuestionsOptionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuestionsOptionsResourceIT {

    private static final String DEFAULT_ANS_OPTION = "AAAAAAAAAA";
    private static final String UPDATED_ANS_OPTION = "BBBBBBBBBB";

    private static final HealthCondition DEFAULT_HEALTH_CONDITION = HealthCondition.STABLE;
    private static final HealthCondition UPDATED_HEALTH_CONDITION = HealthCondition.CRITICAL;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/questions-options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuestionsOptionsRepository questionsOptionsRepository;

    @Autowired
    private QuestionsOptionsMapper questionsOptionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionsOptionsMockMvc;

    private QuestionsOptions questionsOptions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionsOptions createEntity(EntityManager em) {
        QuestionsOptions questionsOptions = new QuestionsOptions()
            .ansOption(DEFAULT_ANS_OPTION)
            .healthCondition(DEFAULT_HEALTH_CONDITION)
            .active(DEFAULT_ACTIVE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return questionsOptions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionsOptions createUpdatedEntity(EntityManager em) {
        QuestionsOptions questionsOptions = new QuestionsOptions()
            .ansOption(UPDATED_ANS_OPTION)
            .healthCondition(UPDATED_HEALTH_CONDITION)
            .active(UPDATED_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return questionsOptions;
    }

    @BeforeEach
    public void initTest() {
        questionsOptions = createEntity(em);
    }

    @Test
    @Transactional
    void createQuestionsOptions() throws Exception {
        int databaseSizeBeforeCreate = questionsOptionsRepository.findAll().size();
        // Create the QuestionsOptions
        QuestionsOptionsDTO questionsOptionsDTO = questionsOptionsMapper.toDto(questionsOptions);
        restQuestionsOptionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionsOptionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the QuestionsOptions in the database
        List<QuestionsOptions> questionsOptionsList = questionsOptionsRepository.findAll();
        assertThat(questionsOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionsOptions testQuestionsOptions = questionsOptionsList.get(questionsOptionsList.size() - 1);
        assertThat(testQuestionsOptions.getAnsOption()).isEqualTo(DEFAULT_ANS_OPTION);
        assertThat(testQuestionsOptions.getHealthCondition()).isEqualTo(DEFAULT_HEALTH_CONDITION);
        assertThat(testQuestionsOptions.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testQuestionsOptions.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testQuestionsOptions.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createQuestionsOptionsWithExistingId() throws Exception {
        // Create the QuestionsOptions with an existing ID
        questionsOptions.setId(1L);
        QuestionsOptionsDTO questionsOptionsDTO = questionsOptionsMapper.toDto(questionsOptions);

        int databaseSizeBeforeCreate = questionsOptionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionsOptionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionsOptionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsOptions in the database
        List<QuestionsOptions> questionsOptionsList = questionsOptionsRepository.findAll();
        assertThat(questionsOptionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuestionsOptions() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList
        restQuestionsOptionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionsOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].ansOption").value(hasItem(DEFAULT_ANS_OPTION)))
            .andExpect(jsonPath("$.[*].healthCondition").value(hasItem(DEFAULT_HEALTH_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getQuestionsOptions() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get the questionsOptions
        restQuestionsOptionsMockMvc
            .perform(get(ENTITY_API_URL_ID, questionsOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionsOptions.getId().intValue()))
            .andExpect(jsonPath("$.ansOption").value(DEFAULT_ANS_OPTION))
            .andExpect(jsonPath("$.healthCondition").value(DEFAULT_HEALTH_CONDITION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getQuestionsOptionsByIdFiltering() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        Long id = questionsOptions.getId();

        defaultQuestionsOptionsShouldBeFound("id.equals=" + id);
        defaultQuestionsOptionsShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionsOptionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionsOptionsShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionsOptionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionsOptionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByAnsOptionIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where ansOption equals to DEFAULT_ANS_OPTION
        defaultQuestionsOptionsShouldBeFound("ansOption.equals=" + DEFAULT_ANS_OPTION);

        // Get all the questionsOptionsList where ansOption equals to UPDATED_ANS_OPTION
        defaultQuestionsOptionsShouldNotBeFound("ansOption.equals=" + UPDATED_ANS_OPTION);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByAnsOptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where ansOption not equals to DEFAULT_ANS_OPTION
        defaultQuestionsOptionsShouldNotBeFound("ansOption.notEquals=" + DEFAULT_ANS_OPTION);

        // Get all the questionsOptionsList where ansOption not equals to UPDATED_ANS_OPTION
        defaultQuestionsOptionsShouldBeFound("ansOption.notEquals=" + UPDATED_ANS_OPTION);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByAnsOptionIsInShouldWork() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where ansOption in DEFAULT_ANS_OPTION or UPDATED_ANS_OPTION
        defaultQuestionsOptionsShouldBeFound("ansOption.in=" + DEFAULT_ANS_OPTION + "," + UPDATED_ANS_OPTION);

        // Get all the questionsOptionsList where ansOption equals to UPDATED_ANS_OPTION
        defaultQuestionsOptionsShouldNotBeFound("ansOption.in=" + UPDATED_ANS_OPTION);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByAnsOptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where ansOption is not null
        defaultQuestionsOptionsShouldBeFound("ansOption.specified=true");

        // Get all the questionsOptionsList where ansOption is null
        defaultQuestionsOptionsShouldNotBeFound("ansOption.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByAnsOptionContainsSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where ansOption contains DEFAULT_ANS_OPTION
        defaultQuestionsOptionsShouldBeFound("ansOption.contains=" + DEFAULT_ANS_OPTION);

        // Get all the questionsOptionsList where ansOption contains UPDATED_ANS_OPTION
        defaultQuestionsOptionsShouldNotBeFound("ansOption.contains=" + UPDATED_ANS_OPTION);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByAnsOptionNotContainsSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where ansOption does not contain DEFAULT_ANS_OPTION
        defaultQuestionsOptionsShouldNotBeFound("ansOption.doesNotContain=" + DEFAULT_ANS_OPTION);

        // Get all the questionsOptionsList where ansOption does not contain UPDATED_ANS_OPTION
        defaultQuestionsOptionsShouldBeFound("ansOption.doesNotContain=" + UPDATED_ANS_OPTION);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByHealthConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where healthCondition equals to DEFAULT_HEALTH_CONDITION
        defaultQuestionsOptionsShouldBeFound("healthCondition.equals=" + DEFAULT_HEALTH_CONDITION);

        // Get all the questionsOptionsList where healthCondition equals to UPDATED_HEALTH_CONDITION
        defaultQuestionsOptionsShouldNotBeFound("healthCondition.equals=" + UPDATED_HEALTH_CONDITION);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByHealthConditionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where healthCondition not equals to DEFAULT_HEALTH_CONDITION
        defaultQuestionsOptionsShouldNotBeFound("healthCondition.notEquals=" + DEFAULT_HEALTH_CONDITION);

        // Get all the questionsOptionsList where healthCondition not equals to UPDATED_HEALTH_CONDITION
        defaultQuestionsOptionsShouldBeFound("healthCondition.notEquals=" + UPDATED_HEALTH_CONDITION);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByHealthConditionIsInShouldWork() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where healthCondition in DEFAULT_HEALTH_CONDITION or UPDATED_HEALTH_CONDITION
        defaultQuestionsOptionsShouldBeFound("healthCondition.in=" + DEFAULT_HEALTH_CONDITION + "," + UPDATED_HEALTH_CONDITION);

        // Get all the questionsOptionsList where healthCondition equals to UPDATED_HEALTH_CONDITION
        defaultQuestionsOptionsShouldNotBeFound("healthCondition.in=" + UPDATED_HEALTH_CONDITION);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByHealthConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where healthCondition is not null
        defaultQuestionsOptionsShouldBeFound("healthCondition.specified=true");

        // Get all the questionsOptionsList where healthCondition is null
        defaultQuestionsOptionsShouldNotBeFound("healthCondition.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where active equals to DEFAULT_ACTIVE
        defaultQuestionsOptionsShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the questionsOptionsList where active equals to UPDATED_ACTIVE
        defaultQuestionsOptionsShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where active not equals to DEFAULT_ACTIVE
        defaultQuestionsOptionsShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the questionsOptionsList where active not equals to UPDATED_ACTIVE
        defaultQuestionsOptionsShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultQuestionsOptionsShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the questionsOptionsList where active equals to UPDATED_ACTIVE
        defaultQuestionsOptionsShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where active is not null
        defaultQuestionsOptionsShouldBeFound("active.specified=true");

        // Get all the questionsOptionsList where active is null
        defaultQuestionsOptionsShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultQuestionsOptionsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the questionsOptionsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultQuestionsOptionsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultQuestionsOptionsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the questionsOptionsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultQuestionsOptionsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultQuestionsOptionsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the questionsOptionsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultQuestionsOptionsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where lastModified is not null
        defaultQuestionsOptionsShouldBeFound("lastModified.specified=true");

        // Get all the questionsOptionsList where lastModified is null
        defaultQuestionsOptionsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultQuestionsOptionsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the questionsOptionsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultQuestionsOptionsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultQuestionsOptionsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the questionsOptionsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultQuestionsOptionsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultQuestionsOptionsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the questionsOptionsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultQuestionsOptionsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where lastModifiedBy is not null
        defaultQuestionsOptionsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the questionsOptionsList where lastModifiedBy is null
        defaultQuestionsOptionsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultQuestionsOptionsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the questionsOptionsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultQuestionsOptionsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        // Get all the questionsOptionsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultQuestionsOptionsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the questionsOptionsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultQuestionsOptionsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuestionsOptionsByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        em.persist(question);
        em.flush();
        questionsOptions.setQuestion(question);
        questionsOptionsRepository.saveAndFlush(questionsOptions);
        Long questionId = question.getId();

        // Get all the questionsOptionsList where question equals to questionId
        defaultQuestionsOptionsShouldBeFound("questionId.equals=" + questionId);

        // Get all the questionsOptionsList where question equals to (questionId + 1)
        defaultQuestionsOptionsShouldNotBeFound("questionId.equals=" + (questionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionsOptionsShouldBeFound(String filter) throws Exception {
        restQuestionsOptionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionsOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].ansOption").value(hasItem(DEFAULT_ANS_OPTION)))
            .andExpect(jsonPath("$.[*].healthCondition").value(hasItem(DEFAULT_HEALTH_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restQuestionsOptionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionsOptionsShouldNotBeFound(String filter) throws Exception {
        restQuestionsOptionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionsOptionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingQuestionsOptions() throws Exception {
        // Get the questionsOptions
        restQuestionsOptionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuestionsOptions() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        int databaseSizeBeforeUpdate = questionsOptionsRepository.findAll().size();

        // Update the questionsOptions
        QuestionsOptions updatedQuestionsOptions = questionsOptionsRepository.findById(questionsOptions.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionsOptions are not directly saved in db
        em.detach(updatedQuestionsOptions);
        updatedQuestionsOptions
            .ansOption(UPDATED_ANS_OPTION)
            .healthCondition(UPDATED_HEALTH_CONDITION)
            .active(UPDATED_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        QuestionsOptionsDTO questionsOptionsDTO = questionsOptionsMapper.toDto(updatedQuestionsOptions);

        restQuestionsOptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionsOptionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsOptionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the QuestionsOptions in the database
        List<QuestionsOptions> questionsOptionsList = questionsOptionsRepository.findAll();
        assertThat(questionsOptionsList).hasSize(databaseSizeBeforeUpdate);
        QuestionsOptions testQuestionsOptions = questionsOptionsList.get(questionsOptionsList.size() - 1);
        assertThat(testQuestionsOptions.getAnsOption()).isEqualTo(UPDATED_ANS_OPTION);
        assertThat(testQuestionsOptions.getHealthCondition()).isEqualTo(UPDATED_HEALTH_CONDITION);
        assertThat(testQuestionsOptions.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testQuestionsOptions.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testQuestionsOptions.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingQuestionsOptions() throws Exception {
        int databaseSizeBeforeUpdate = questionsOptionsRepository.findAll().size();
        questionsOptions.setId(count.incrementAndGet());

        // Create the QuestionsOptions
        QuestionsOptionsDTO questionsOptionsDTO = questionsOptionsMapper.toDto(questionsOptions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsOptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionsOptionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsOptionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsOptions in the database
        List<QuestionsOptions> questionsOptionsList = questionsOptionsRepository.findAll();
        assertThat(questionsOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuestionsOptions() throws Exception {
        int databaseSizeBeforeUpdate = questionsOptionsRepository.findAll().size();
        questionsOptions.setId(count.incrementAndGet());

        // Create the QuestionsOptions
        QuestionsOptionsDTO questionsOptionsDTO = questionsOptionsMapper.toDto(questionsOptions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsOptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsOptionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsOptions in the database
        List<QuestionsOptions> questionsOptionsList = questionsOptionsRepository.findAll();
        assertThat(questionsOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuestionsOptions() throws Exception {
        int databaseSizeBeforeUpdate = questionsOptionsRepository.findAll().size();
        questionsOptions.setId(count.incrementAndGet());

        // Create the QuestionsOptions
        QuestionsOptionsDTO questionsOptionsDTO = questionsOptionsMapper.toDto(questionsOptions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsOptionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionsOptionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuestionsOptions in the database
        List<QuestionsOptions> questionsOptionsList = questionsOptionsRepository.findAll();
        assertThat(questionsOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuestionsOptionsWithPatch() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        int databaseSizeBeforeUpdate = questionsOptionsRepository.findAll().size();

        // Update the questionsOptions using partial update
        QuestionsOptions partialUpdatedQuestionsOptions = new QuestionsOptions();
        partialUpdatedQuestionsOptions.setId(questionsOptions.getId());

        partialUpdatedQuestionsOptions
            .ansOption(UPDATED_ANS_OPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restQuestionsOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestionsOptions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestionsOptions))
            )
            .andExpect(status().isOk());

        // Validate the QuestionsOptions in the database
        List<QuestionsOptions> questionsOptionsList = questionsOptionsRepository.findAll();
        assertThat(questionsOptionsList).hasSize(databaseSizeBeforeUpdate);
        QuestionsOptions testQuestionsOptions = questionsOptionsList.get(questionsOptionsList.size() - 1);
        assertThat(testQuestionsOptions.getAnsOption()).isEqualTo(UPDATED_ANS_OPTION);
        assertThat(testQuestionsOptions.getHealthCondition()).isEqualTo(DEFAULT_HEALTH_CONDITION);
        assertThat(testQuestionsOptions.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testQuestionsOptions.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testQuestionsOptions.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateQuestionsOptionsWithPatch() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        int databaseSizeBeforeUpdate = questionsOptionsRepository.findAll().size();

        // Update the questionsOptions using partial update
        QuestionsOptions partialUpdatedQuestionsOptions = new QuestionsOptions();
        partialUpdatedQuestionsOptions.setId(questionsOptions.getId());

        partialUpdatedQuestionsOptions
            .ansOption(UPDATED_ANS_OPTION)
            .healthCondition(UPDATED_HEALTH_CONDITION)
            .active(UPDATED_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restQuestionsOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestionsOptions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestionsOptions))
            )
            .andExpect(status().isOk());

        // Validate the QuestionsOptions in the database
        List<QuestionsOptions> questionsOptionsList = questionsOptionsRepository.findAll();
        assertThat(questionsOptionsList).hasSize(databaseSizeBeforeUpdate);
        QuestionsOptions testQuestionsOptions = questionsOptionsList.get(questionsOptionsList.size() - 1);
        assertThat(testQuestionsOptions.getAnsOption()).isEqualTo(UPDATED_ANS_OPTION);
        assertThat(testQuestionsOptions.getHealthCondition()).isEqualTo(UPDATED_HEALTH_CONDITION);
        assertThat(testQuestionsOptions.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testQuestionsOptions.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testQuestionsOptions.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingQuestionsOptions() throws Exception {
        int databaseSizeBeforeUpdate = questionsOptionsRepository.findAll().size();
        questionsOptions.setId(count.incrementAndGet());

        // Create the QuestionsOptions
        QuestionsOptionsDTO questionsOptionsDTO = questionsOptionsMapper.toDto(questionsOptions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, questionsOptionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsOptionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsOptions in the database
        List<QuestionsOptions> questionsOptionsList = questionsOptionsRepository.findAll();
        assertThat(questionsOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuestionsOptions() throws Exception {
        int databaseSizeBeforeUpdate = questionsOptionsRepository.findAll().size();
        questionsOptions.setId(count.incrementAndGet());

        // Create the QuestionsOptions
        QuestionsOptionsDTO questionsOptionsDTO = questionsOptionsMapper.toDto(questionsOptions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsOptionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionsOptions in the database
        List<QuestionsOptions> questionsOptionsList = questionsOptionsRepository.findAll();
        assertThat(questionsOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuestionsOptions() throws Exception {
        int databaseSizeBeforeUpdate = questionsOptionsRepository.findAll().size();
        questionsOptions.setId(count.incrementAndGet());

        // Create the QuestionsOptions
        QuestionsOptionsDTO questionsOptionsDTO = questionsOptionsMapper.toDto(questionsOptions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsOptionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuestionsOptions in the database
        List<QuestionsOptions> questionsOptionsList = questionsOptionsRepository.findAll();
        assertThat(questionsOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuestionsOptions() throws Exception {
        // Initialize the database
        questionsOptionsRepository.saveAndFlush(questionsOptions);

        int databaseSizeBeforeDelete = questionsOptionsRepository.findAll().size();

        // Delete the questionsOptions
        restQuestionsOptionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, questionsOptions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionsOptions> questionsOptionsList = questionsOptionsRepository.findAll();
        assertThat(questionsOptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
