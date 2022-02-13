package com.techvg.covid.care.isolation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.isolation.IntegrationTest;
import com.techvg.covid.care.isolation.domain.AssessmentAnswer;
import com.techvg.covid.care.isolation.domain.Question;
import com.techvg.covid.care.isolation.domain.enumeration.QuestionType;
import com.techvg.covid.care.isolation.repository.QuestionRepository;
import com.techvg.covid.care.isolation.service.criteria.QuestionCriteria;
import com.techvg.covid.care.isolation.service.dto.QuestionDTO;
import com.techvg.covid.care.isolation.service.mapper.QuestionMapper;
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
 * Integration tests for the {@link QuestionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuestionResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_DESC = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_DESC = "BBBBBBBBBB";

    private static final QuestionType DEFAULT_QUESTION_TYPE = QuestionType.FREETEXT;
    private static final QuestionType UPDATED_QUESTION_TYPE = QuestionType.MULTISELECT;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionMockMvc;

    private Question question;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Question createEntity(EntityManager em) {
        Question question = new Question()
            .question(DEFAULT_QUESTION)
            .questionDesc(DEFAULT_QUESTION_DESC)
            .questionType(DEFAULT_QUESTION_TYPE)
            .active(DEFAULT_ACTIVE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return question;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Question createUpdatedEntity(EntityManager em) {
        Question question = new Question()
            .question(UPDATED_QUESTION)
            .questionDesc(UPDATED_QUESTION_DESC)
            .questionType(UPDATED_QUESTION_TYPE)
            .active(UPDATED_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return question;
    }

    @BeforeEach
    public void initTest() {
        question = createEntity(em);
    }

    @Test
    @Transactional
    void createQuestion() throws Exception {
        int databaseSizeBeforeCreate = questionRepository.findAll().size();
        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);
        restQuestionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isCreated());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate + 1);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQuestion.getQuestionDesc()).isEqualTo(DEFAULT_QUESTION_DESC);
        assertThat(testQuestion.getQuestionType()).isEqualTo(DEFAULT_QUESTION_TYPE);
        assertThat(testQuestion.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testQuestion.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testQuestion.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createQuestionWithExistingId() throws Exception {
        // Create the Question with an existing ID
        question.setId(1L);
        QuestionDTO questionDTO = questionMapper.toDto(question);

        int databaseSizeBeforeCreate = questionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuestions() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList
        restQuestionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(question.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].questionDesc").value(hasItem(DEFAULT_QUESTION_DESC)))
            .andExpect(jsonPath("$.[*].questionType").value(hasItem(DEFAULT_QUESTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get the question
        restQuestionMockMvc
            .perform(get(ENTITY_API_URL_ID, question.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(question.getId().intValue()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.questionDesc").value(DEFAULT_QUESTION_DESC))
            .andExpect(jsonPath("$.questionType").value(DEFAULT_QUESTION_TYPE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getQuestionsByIdFiltering() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        Long id = question.getId();

        defaultQuestionShouldBeFound("id.equals=" + id);
        defaultQuestionShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where question equals to DEFAULT_QUESTION
        defaultQuestionShouldBeFound("question.equals=" + DEFAULT_QUESTION);

        // Get all the questionList where question equals to UPDATED_QUESTION
        defaultQuestionShouldNotBeFound("question.equals=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where question not equals to DEFAULT_QUESTION
        defaultQuestionShouldNotBeFound("question.notEquals=" + DEFAULT_QUESTION);

        // Get all the questionList where question not equals to UPDATED_QUESTION
        defaultQuestionShouldBeFound("question.notEquals=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionIsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where question in DEFAULT_QUESTION or UPDATED_QUESTION
        defaultQuestionShouldBeFound("question.in=" + DEFAULT_QUESTION + "," + UPDATED_QUESTION);

        // Get all the questionList where question equals to UPDATED_QUESTION
        defaultQuestionShouldNotBeFound("question.in=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where question is not null
        defaultQuestionShouldBeFound("question.specified=true");

        // Get all the questionList where question is null
        defaultQuestionShouldNotBeFound("question.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where question contains DEFAULT_QUESTION
        defaultQuestionShouldBeFound("question.contains=" + DEFAULT_QUESTION);

        // Get all the questionList where question contains UPDATED_QUESTION
        defaultQuestionShouldNotBeFound("question.contains=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionNotContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where question does not contain DEFAULT_QUESTION
        defaultQuestionShouldNotBeFound("question.doesNotContain=" + DEFAULT_QUESTION);

        // Get all the questionList where question does not contain UPDATED_QUESTION
        defaultQuestionShouldBeFound("question.doesNotContain=" + UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionDescIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where questionDesc equals to DEFAULT_QUESTION_DESC
        defaultQuestionShouldBeFound("questionDesc.equals=" + DEFAULT_QUESTION_DESC);

        // Get all the questionList where questionDesc equals to UPDATED_QUESTION_DESC
        defaultQuestionShouldNotBeFound("questionDesc.equals=" + UPDATED_QUESTION_DESC);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionDescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where questionDesc not equals to DEFAULT_QUESTION_DESC
        defaultQuestionShouldNotBeFound("questionDesc.notEquals=" + DEFAULT_QUESTION_DESC);

        // Get all the questionList where questionDesc not equals to UPDATED_QUESTION_DESC
        defaultQuestionShouldBeFound("questionDesc.notEquals=" + UPDATED_QUESTION_DESC);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionDescIsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where questionDesc in DEFAULT_QUESTION_DESC or UPDATED_QUESTION_DESC
        defaultQuestionShouldBeFound("questionDesc.in=" + DEFAULT_QUESTION_DESC + "," + UPDATED_QUESTION_DESC);

        // Get all the questionList where questionDesc equals to UPDATED_QUESTION_DESC
        defaultQuestionShouldNotBeFound("questionDesc.in=" + UPDATED_QUESTION_DESC);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where questionDesc is not null
        defaultQuestionShouldBeFound("questionDesc.specified=true");

        // Get all the questionList where questionDesc is null
        defaultQuestionShouldNotBeFound("questionDesc.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionDescContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where questionDesc contains DEFAULT_QUESTION_DESC
        defaultQuestionShouldBeFound("questionDesc.contains=" + DEFAULT_QUESTION_DESC);

        // Get all the questionList where questionDesc contains UPDATED_QUESTION_DESC
        defaultQuestionShouldNotBeFound("questionDesc.contains=" + UPDATED_QUESTION_DESC);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionDescNotContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where questionDesc does not contain DEFAULT_QUESTION_DESC
        defaultQuestionShouldNotBeFound("questionDesc.doesNotContain=" + DEFAULT_QUESTION_DESC);

        // Get all the questionList where questionDesc does not contain UPDATED_QUESTION_DESC
        defaultQuestionShouldBeFound("questionDesc.doesNotContain=" + UPDATED_QUESTION_DESC);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where questionType equals to DEFAULT_QUESTION_TYPE
        defaultQuestionShouldBeFound("questionType.equals=" + DEFAULT_QUESTION_TYPE);

        // Get all the questionList where questionType equals to UPDATED_QUESTION_TYPE
        defaultQuestionShouldNotBeFound("questionType.equals=" + UPDATED_QUESTION_TYPE);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where questionType not equals to DEFAULT_QUESTION_TYPE
        defaultQuestionShouldNotBeFound("questionType.notEquals=" + DEFAULT_QUESTION_TYPE);

        // Get all the questionList where questionType not equals to UPDATED_QUESTION_TYPE
        defaultQuestionShouldBeFound("questionType.notEquals=" + UPDATED_QUESTION_TYPE);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where questionType in DEFAULT_QUESTION_TYPE or UPDATED_QUESTION_TYPE
        defaultQuestionShouldBeFound("questionType.in=" + DEFAULT_QUESTION_TYPE + "," + UPDATED_QUESTION_TYPE);

        // Get all the questionList where questionType equals to UPDATED_QUESTION_TYPE
        defaultQuestionShouldNotBeFound("questionType.in=" + UPDATED_QUESTION_TYPE);
    }

    @Test
    @Transactional
    void getAllQuestionsByQuestionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where questionType is not null
        defaultQuestionShouldBeFound("questionType.specified=true");

        // Get all the questionList where questionType is null
        defaultQuestionShouldNotBeFound("questionType.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where active equals to DEFAULT_ACTIVE
        defaultQuestionShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the questionList where active equals to UPDATED_ACTIVE
        defaultQuestionShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllQuestionsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where active not equals to DEFAULT_ACTIVE
        defaultQuestionShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the questionList where active not equals to UPDATED_ACTIVE
        defaultQuestionShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllQuestionsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultQuestionShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the questionList where active equals to UPDATED_ACTIVE
        defaultQuestionShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllQuestionsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where active is not null
        defaultQuestionShouldBeFound("active.specified=true");

        // Get all the questionList where active is null
        defaultQuestionShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultQuestionShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the questionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultQuestionShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllQuestionsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultQuestionShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the questionList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultQuestionShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllQuestionsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultQuestionShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the questionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultQuestionShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllQuestionsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastModified is not null
        defaultQuestionShouldBeFound("lastModified.specified=true");

        // Get all the questionList where lastModified is null
        defaultQuestionShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultQuestionShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the questionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultQuestionShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuestionsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultQuestionShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the questionList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultQuestionShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuestionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultQuestionShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the questionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultQuestionShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuestionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastModifiedBy is not null
        defaultQuestionShouldBeFound("lastModifiedBy.specified=true");

        // Get all the questionList where lastModifiedBy is null
        defaultQuestionShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultQuestionShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the questionList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultQuestionShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuestionsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultQuestionShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the questionList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultQuestionShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuestionsByAssessmentAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);
        AssessmentAnswer assessmentAnswer;
        if (TestUtil.findAll(em, AssessmentAnswer.class).isEmpty()) {
            assessmentAnswer = AssessmentAnswerResourceIT.createEntity(em);
            em.persist(assessmentAnswer);
            em.flush();
        } else {
            assessmentAnswer = TestUtil.findAll(em, AssessmentAnswer.class).get(0);
        }
        em.persist(assessmentAnswer);
        em.flush();
        question.addAssessmentAnswer(assessmentAnswer);
        questionRepository.saveAndFlush(question);
        Long assessmentAnswerId = assessmentAnswer.getId();

        // Get all the questionList where assessmentAnswer equals to assessmentAnswerId
        defaultQuestionShouldBeFound("assessmentAnswerId.equals=" + assessmentAnswerId);

        // Get all the questionList where assessmentAnswer equals to (assessmentAnswerId + 1)
        defaultQuestionShouldNotBeFound("assessmentAnswerId.equals=" + (assessmentAnswerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionShouldBeFound(String filter) throws Exception {
        restQuestionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(question.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].questionDesc").value(hasItem(DEFAULT_QUESTION_DESC)))
            .andExpect(jsonPath("$.[*].questionType").value(hasItem(DEFAULT_QUESTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restQuestionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionShouldNotBeFound(String filter) throws Exception {
        restQuestionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingQuestion() throws Exception {
        // Get the question
        restQuestionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Update the question
        Question updatedQuestion = questionRepository.findById(question.getId()).get();
        // Disconnect from session so that the updates on updatedQuestion are not directly saved in db
        em.detach(updatedQuestion);
        updatedQuestion
            .question(UPDATED_QUESTION)
            .questionDesc(UPDATED_QUESTION_DESC)
            .questionType(UPDATED_QUESTION_TYPE)
            .active(UPDATED_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        QuestionDTO questionDTO = questionMapper.toDto(updatedQuestion);

        restQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestion.getQuestionDesc()).isEqualTo(UPDATED_QUESTION_DESC);
        assertThat(testQuestion.getQuestionType()).isEqualTo(UPDATED_QUESTION_TYPE);
        assertThat(testQuestion.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testQuestion.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testQuestion.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();
        question.setId(count.incrementAndGet());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();
        question.setId(count.incrementAndGet());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();
        question.setId(count.incrementAndGet());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuestionWithPatch() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Update the question using partial update
        Question partialUpdatedQuestion = new Question();
        partialUpdatedQuestion.setId(question.getId());

        partialUpdatedQuestion
            .question(UPDATED_QUESTION)
            .questionDesc(UPDATED_QUESTION_DESC)
            .questionType(UPDATED_QUESTION_TYPE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestion))
            )
            .andExpect(status().isOk());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestion.getQuestionDesc()).isEqualTo(UPDATED_QUESTION_DESC);
        assertThat(testQuestion.getQuestionType()).isEqualTo(UPDATED_QUESTION_TYPE);
        assertThat(testQuestion.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testQuestion.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testQuestion.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateQuestionWithPatch() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Update the question using partial update
        Question partialUpdatedQuestion = new Question();
        partialUpdatedQuestion.setId(question.getId());

        partialUpdatedQuestion
            .question(UPDATED_QUESTION)
            .questionDesc(UPDATED_QUESTION_DESC)
            .questionType(UPDATED_QUESTION_TYPE)
            .active(UPDATED_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestion))
            )
            .andExpect(status().isOk());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestion.getQuestionDesc()).isEqualTo(UPDATED_QUESTION_DESC);
        assertThat(testQuestion.getQuestionType()).isEqualTo(UPDATED_QUESTION_TYPE);
        assertThat(testQuestion.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testQuestion.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testQuestion.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();
        question.setId(count.incrementAndGet());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, questionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();
        question.setId(count.incrementAndGet());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();
        question.setId(count.incrementAndGet());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(questionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        int databaseSizeBeforeDelete = questionRepository.findAll().size();

        // Delete the question
        restQuestionMockMvc
            .perform(delete(ENTITY_API_URL_ID, question.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
