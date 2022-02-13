package com.techvg.covid.care.isolation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.isolation.IntegrationTest;
import com.techvg.covid.care.isolation.domain.Assessment;
import com.techvg.covid.care.isolation.domain.AssessmentAnswer;
import com.techvg.covid.care.isolation.domain.Question;
import com.techvg.covid.care.isolation.repository.AssessmentAnswerRepository;
import com.techvg.covid.care.isolation.service.criteria.AssessmentAnswerCriteria;
import com.techvg.covid.care.isolation.service.dto.AssessmentAnswerDTO;
import com.techvg.covid.care.isolation.service.mapper.AssessmentAnswerMapper;
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
 * Integration tests for the {@link AssessmentAnswerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssessmentAnswerResourceIT {

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/assessment-answers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssessmentAnswerRepository assessmentAnswerRepository;

    @Autowired
    private AssessmentAnswerMapper assessmentAnswerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssessmentAnswerMockMvc;

    private AssessmentAnswer assessmentAnswer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssessmentAnswer createEntity(EntityManager em) {
        AssessmentAnswer assessmentAnswer = new AssessmentAnswer()
            .answer(DEFAULT_ANSWER)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return assessmentAnswer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssessmentAnswer createUpdatedEntity(EntityManager em) {
        AssessmentAnswer assessmentAnswer = new AssessmentAnswer()
            .answer(UPDATED_ANSWER)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return assessmentAnswer;
    }

    @BeforeEach
    public void initTest() {
        assessmentAnswer = createEntity(em);
    }

    @Test
    @Transactional
    void createAssessmentAnswer() throws Exception {
        int databaseSizeBeforeCreate = assessmentAnswerRepository.findAll().size();
        // Create the AssessmentAnswer
        AssessmentAnswerDTO assessmentAnswerDTO = assessmentAnswerMapper.toDto(assessmentAnswer);
        restAssessmentAnswerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentAnswerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AssessmentAnswer in the database
        List<AssessmentAnswer> assessmentAnswerList = assessmentAnswerRepository.findAll();
        assertThat(assessmentAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        AssessmentAnswer testAssessmentAnswer = assessmentAnswerList.get(assessmentAnswerList.size() - 1);
        assertThat(testAssessmentAnswer.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testAssessmentAnswer.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAssessmentAnswer.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createAssessmentAnswerWithExistingId() throws Exception {
        // Create the AssessmentAnswer with an existing ID
        assessmentAnswer.setId(1L);
        AssessmentAnswerDTO assessmentAnswerDTO = assessmentAnswerMapper.toDto(assessmentAnswer);

        int databaseSizeBeforeCreate = assessmentAnswerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssessmentAnswerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssessmentAnswer in the database
        List<AssessmentAnswer> assessmentAnswerList = assessmentAnswerRepository.findAll();
        assertThat(assessmentAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswers() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList
        restAssessmentAnswerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assessmentAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getAssessmentAnswer() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get the assessmentAnswer
        restAssessmentAnswerMockMvc
            .perform(get(ENTITY_API_URL_ID, assessmentAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assessmentAnswer.getId().intValue()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getAssessmentAnswersByIdFiltering() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        Long id = assessmentAnswer.getId();

        defaultAssessmentAnswerShouldBeFound("id.equals=" + id);
        defaultAssessmentAnswerShouldNotBeFound("id.notEquals=" + id);

        defaultAssessmentAnswerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAssessmentAnswerShouldNotBeFound("id.greaterThan=" + id);

        defaultAssessmentAnswerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAssessmentAnswerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where answer equals to DEFAULT_ANSWER
        defaultAssessmentAnswerShouldBeFound("answer.equals=" + DEFAULT_ANSWER);

        // Get all the assessmentAnswerList where answer equals to UPDATED_ANSWER
        defaultAssessmentAnswerShouldNotBeFound("answer.equals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByAnswerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where answer not equals to DEFAULT_ANSWER
        defaultAssessmentAnswerShouldNotBeFound("answer.notEquals=" + DEFAULT_ANSWER);

        // Get all the assessmentAnswerList where answer not equals to UPDATED_ANSWER
        defaultAssessmentAnswerShouldBeFound("answer.notEquals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByAnswerIsInShouldWork() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where answer in DEFAULT_ANSWER or UPDATED_ANSWER
        defaultAssessmentAnswerShouldBeFound("answer.in=" + DEFAULT_ANSWER + "," + UPDATED_ANSWER);

        // Get all the assessmentAnswerList where answer equals to UPDATED_ANSWER
        defaultAssessmentAnswerShouldNotBeFound("answer.in=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByAnswerIsNullOrNotNull() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where answer is not null
        defaultAssessmentAnswerShouldBeFound("answer.specified=true");

        // Get all the assessmentAnswerList where answer is null
        defaultAssessmentAnswerShouldNotBeFound("answer.specified=false");
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByAnswerContainsSomething() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where answer contains DEFAULT_ANSWER
        defaultAssessmentAnswerShouldBeFound("answer.contains=" + DEFAULT_ANSWER);

        // Get all the assessmentAnswerList where answer contains UPDATED_ANSWER
        defaultAssessmentAnswerShouldNotBeFound("answer.contains=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByAnswerNotContainsSomething() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where answer does not contain DEFAULT_ANSWER
        defaultAssessmentAnswerShouldNotBeFound("answer.doesNotContain=" + DEFAULT_ANSWER);

        // Get all the assessmentAnswerList where answer does not contain UPDATED_ANSWER
        defaultAssessmentAnswerShouldBeFound("answer.doesNotContain=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAssessmentAnswerShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the assessmentAnswerList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAssessmentAnswerShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultAssessmentAnswerShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the assessmentAnswerList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultAssessmentAnswerShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAssessmentAnswerShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the assessmentAnswerList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAssessmentAnswerShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where lastModified is not null
        defaultAssessmentAnswerShouldBeFound("lastModified.specified=true");

        // Get all the assessmentAnswerList where lastModified is null
        defaultAssessmentAnswerShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAssessmentAnswerShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the assessmentAnswerList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAssessmentAnswerShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultAssessmentAnswerShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the assessmentAnswerList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultAssessmentAnswerShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAssessmentAnswerShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the assessmentAnswerList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAssessmentAnswerShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where lastModifiedBy is not null
        defaultAssessmentAnswerShouldBeFound("lastModifiedBy.specified=true");

        // Get all the assessmentAnswerList where lastModifiedBy is null
        defaultAssessmentAnswerShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAssessmentAnswerShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the assessmentAnswerList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAssessmentAnswerShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        // Get all the assessmentAnswerList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAssessmentAnswerShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the assessmentAnswerList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAssessmentAnswerShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByAssessmentIsEqualToSomething() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);
        Assessment assessment;
        if (TestUtil.findAll(em, Assessment.class).isEmpty()) {
            assessment = AssessmentResourceIT.createEntity(em);
            em.persist(assessment);
            em.flush();
        } else {
            assessment = TestUtil.findAll(em, Assessment.class).get(0);
        }
        em.persist(assessment);
        em.flush();
        assessmentAnswer.setAssessment(assessment);
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);
        Long assessmentId = assessment.getId();

        // Get all the assessmentAnswerList where assessment equals to assessmentId
        defaultAssessmentAnswerShouldBeFound("assessmentId.equals=" + assessmentId);

        // Get all the assessmentAnswerList where assessment equals to (assessmentId + 1)
        defaultAssessmentAnswerShouldNotBeFound("assessmentId.equals=" + (assessmentId + 1));
    }

    @Test
    @Transactional
    void getAllAssessmentAnswersByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);
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
        assessmentAnswer.setQuestion(question);
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);
        Long questionId = question.getId();

        // Get all the assessmentAnswerList where question equals to questionId
        defaultAssessmentAnswerShouldBeFound("questionId.equals=" + questionId);

        // Get all the assessmentAnswerList where question equals to (questionId + 1)
        defaultAssessmentAnswerShouldNotBeFound("questionId.equals=" + (questionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAssessmentAnswerShouldBeFound(String filter) throws Exception {
        restAssessmentAnswerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assessmentAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restAssessmentAnswerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAssessmentAnswerShouldNotBeFound(String filter) throws Exception {
        restAssessmentAnswerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAssessmentAnswerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAssessmentAnswer() throws Exception {
        // Get the assessmentAnswer
        restAssessmentAnswerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAssessmentAnswer() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        int databaseSizeBeforeUpdate = assessmentAnswerRepository.findAll().size();

        // Update the assessmentAnswer
        AssessmentAnswer updatedAssessmentAnswer = assessmentAnswerRepository.findById(assessmentAnswer.getId()).get();
        // Disconnect from session so that the updates on updatedAssessmentAnswer are not directly saved in db
        em.detach(updatedAssessmentAnswer);
        updatedAssessmentAnswer.answer(UPDATED_ANSWER).lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        AssessmentAnswerDTO assessmentAnswerDTO = assessmentAnswerMapper.toDto(updatedAssessmentAnswer);

        restAssessmentAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assessmentAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assessmentAnswerDTO))
            )
            .andExpect(status().isOk());

        // Validate the AssessmentAnswer in the database
        List<AssessmentAnswer> assessmentAnswerList = assessmentAnswerRepository.findAll();
        assertThat(assessmentAnswerList).hasSize(databaseSizeBeforeUpdate);
        AssessmentAnswer testAssessmentAnswer = assessmentAnswerList.get(assessmentAnswerList.size() - 1);
        assertThat(testAssessmentAnswer.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testAssessmentAnswer.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAssessmentAnswer.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingAssessmentAnswer() throws Exception {
        int databaseSizeBeforeUpdate = assessmentAnswerRepository.findAll().size();
        assessmentAnswer.setId(count.incrementAndGet());

        // Create the AssessmentAnswer
        AssessmentAnswerDTO assessmentAnswerDTO = assessmentAnswerMapper.toDto(assessmentAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssessmentAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assessmentAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assessmentAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssessmentAnswer in the database
        List<AssessmentAnswer> assessmentAnswerList = assessmentAnswerRepository.findAll();
        assertThat(assessmentAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssessmentAnswer() throws Exception {
        int databaseSizeBeforeUpdate = assessmentAnswerRepository.findAll().size();
        assessmentAnswer.setId(count.incrementAndGet());

        // Create the AssessmentAnswer
        AssessmentAnswerDTO assessmentAnswerDTO = assessmentAnswerMapper.toDto(assessmentAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assessmentAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssessmentAnswer in the database
        List<AssessmentAnswer> assessmentAnswerList = assessmentAnswerRepository.findAll();
        assertThat(assessmentAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssessmentAnswer() throws Exception {
        int databaseSizeBeforeUpdate = assessmentAnswerRepository.findAll().size();
        assessmentAnswer.setId(count.incrementAndGet());

        // Create the AssessmentAnswer
        AssessmentAnswerDTO assessmentAnswerDTO = assessmentAnswerMapper.toDto(assessmentAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentAnswerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentAnswerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssessmentAnswer in the database
        List<AssessmentAnswer> assessmentAnswerList = assessmentAnswerRepository.findAll();
        assertThat(assessmentAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssessmentAnswerWithPatch() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        int databaseSizeBeforeUpdate = assessmentAnswerRepository.findAll().size();

        // Update the assessmentAnswer using partial update
        AssessmentAnswer partialUpdatedAssessmentAnswer = new AssessmentAnswer();
        partialUpdatedAssessmentAnswer.setId(assessmentAnswer.getId());

        restAssessmentAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssessmentAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssessmentAnswer))
            )
            .andExpect(status().isOk());

        // Validate the AssessmentAnswer in the database
        List<AssessmentAnswer> assessmentAnswerList = assessmentAnswerRepository.findAll();
        assertThat(assessmentAnswerList).hasSize(databaseSizeBeforeUpdate);
        AssessmentAnswer testAssessmentAnswer = assessmentAnswerList.get(assessmentAnswerList.size() - 1);
        assertThat(testAssessmentAnswer.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testAssessmentAnswer.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAssessmentAnswer.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateAssessmentAnswerWithPatch() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        int databaseSizeBeforeUpdate = assessmentAnswerRepository.findAll().size();

        // Update the assessmentAnswer using partial update
        AssessmentAnswer partialUpdatedAssessmentAnswer = new AssessmentAnswer();
        partialUpdatedAssessmentAnswer.setId(assessmentAnswer.getId());

        partialUpdatedAssessmentAnswer.answer(UPDATED_ANSWER).lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAssessmentAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssessmentAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssessmentAnswer))
            )
            .andExpect(status().isOk());

        // Validate the AssessmentAnswer in the database
        List<AssessmentAnswer> assessmentAnswerList = assessmentAnswerRepository.findAll();
        assertThat(assessmentAnswerList).hasSize(databaseSizeBeforeUpdate);
        AssessmentAnswer testAssessmentAnswer = assessmentAnswerList.get(assessmentAnswerList.size() - 1);
        assertThat(testAssessmentAnswer.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testAssessmentAnswer.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAssessmentAnswer.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingAssessmentAnswer() throws Exception {
        int databaseSizeBeforeUpdate = assessmentAnswerRepository.findAll().size();
        assessmentAnswer.setId(count.incrementAndGet());

        // Create the AssessmentAnswer
        AssessmentAnswerDTO assessmentAnswerDTO = assessmentAnswerMapper.toDto(assessmentAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssessmentAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assessmentAnswerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assessmentAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssessmentAnswer in the database
        List<AssessmentAnswer> assessmentAnswerList = assessmentAnswerRepository.findAll();
        assertThat(assessmentAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssessmentAnswer() throws Exception {
        int databaseSizeBeforeUpdate = assessmentAnswerRepository.findAll().size();
        assessmentAnswer.setId(count.incrementAndGet());

        // Create the AssessmentAnswer
        AssessmentAnswerDTO assessmentAnswerDTO = assessmentAnswerMapper.toDto(assessmentAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assessmentAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssessmentAnswer in the database
        List<AssessmentAnswer> assessmentAnswerList = assessmentAnswerRepository.findAll();
        assertThat(assessmentAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssessmentAnswer() throws Exception {
        int databaseSizeBeforeUpdate = assessmentAnswerRepository.findAll().size();
        assessmentAnswer.setId(count.incrementAndGet());

        // Create the AssessmentAnswer
        AssessmentAnswerDTO assessmentAnswerDTO = assessmentAnswerMapper.toDto(assessmentAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assessmentAnswerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssessmentAnswer in the database
        List<AssessmentAnswer> assessmentAnswerList = assessmentAnswerRepository.findAll();
        assertThat(assessmentAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssessmentAnswer() throws Exception {
        // Initialize the database
        assessmentAnswerRepository.saveAndFlush(assessmentAnswer);

        int databaseSizeBeforeDelete = assessmentAnswerRepository.findAll().size();

        // Delete the assessmentAnswer
        restAssessmentAnswerMockMvc
            .perform(delete(ENTITY_API_URL_ID, assessmentAnswer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssessmentAnswer> assessmentAnswerList = assessmentAnswerRepository.findAll();
        assertThat(assessmentAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
