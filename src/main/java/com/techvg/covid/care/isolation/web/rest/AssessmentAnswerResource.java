package com.techvg.covid.care.isolation.web.rest;

import com.techvg.covid.care.isolation.repository.AssessmentAnswerRepository;
import com.techvg.covid.care.isolation.service.AssessmentAnswerQueryService;
import com.techvg.covid.care.isolation.service.AssessmentAnswerService;
import com.techvg.covid.care.isolation.service.criteria.AssessmentAnswerCriteria;
import com.techvg.covid.care.isolation.service.dto.AssessmentAnswerDTO;
import com.techvg.covid.care.isolation.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.covid.care.isolation.domain.AssessmentAnswer}.
 */
@RestController
@RequestMapping("/api")
public class AssessmentAnswerResource {

    private final Logger log = LoggerFactory.getLogger(AssessmentAnswerResource.class);

    private static final String ENTITY_NAME = "assessmentAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssessmentAnswerService assessmentAnswerService;

    private final AssessmentAnswerRepository assessmentAnswerRepository;

    private final AssessmentAnswerQueryService assessmentAnswerQueryService;

    public AssessmentAnswerResource(
        AssessmentAnswerService assessmentAnswerService,
        AssessmentAnswerRepository assessmentAnswerRepository,
        AssessmentAnswerQueryService assessmentAnswerQueryService
    ) {
        this.assessmentAnswerService = assessmentAnswerService;
        this.assessmentAnswerRepository = assessmentAnswerRepository;
        this.assessmentAnswerQueryService = assessmentAnswerQueryService;
    }

    /**
     * {@code POST  /assessment-answers} : Create a new assessmentAnswer.
     *
     * @param assessmentAnswerDTO the assessmentAnswerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assessmentAnswerDTO, or with status {@code 400 (Bad Request)} if the assessmentAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assessment-answers")
    public ResponseEntity<AssessmentAnswerDTO> createAssessmentAnswer(@RequestBody AssessmentAnswerDTO assessmentAnswerDTO)
        throws URISyntaxException {
        log.debug("REST request to save AssessmentAnswer : {}", assessmentAnswerDTO);
        if (assessmentAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new assessmentAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssessmentAnswerDTO result = assessmentAnswerService.save(assessmentAnswerDTO);
        return ResponseEntity
            .created(new URI("/api/assessment-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /assessment-answers/:id} : Updates an existing assessmentAnswer.
     *
     * @param id the id of the assessmentAnswerDTO to save.
     * @param assessmentAnswerDTO the assessmentAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assessmentAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the assessmentAnswerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assessmentAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assessment-answers/{id}")
    public ResponseEntity<AssessmentAnswerDTO> updateAssessmentAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssessmentAnswerDTO assessmentAnswerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AssessmentAnswer : {}, {}", id, assessmentAnswerDTO);
        if (assessmentAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assessmentAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assessmentAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AssessmentAnswerDTO result = assessmentAnswerService.save(assessmentAnswerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assessmentAnswerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /assessment-answers/:id} : Partial updates given fields of an existing assessmentAnswer, field will ignore if it is null
     *
     * @param id the id of the assessmentAnswerDTO to save.
     * @param assessmentAnswerDTO the assessmentAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assessmentAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the assessmentAnswerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the assessmentAnswerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the assessmentAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/assessment-answers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssessmentAnswerDTO> partialUpdateAssessmentAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssessmentAnswerDTO assessmentAnswerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssessmentAnswer partially : {}, {}", id, assessmentAnswerDTO);
        if (assessmentAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assessmentAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assessmentAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssessmentAnswerDTO> result = assessmentAnswerService.partialUpdate(assessmentAnswerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assessmentAnswerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /assessment-answers} : get all the assessmentAnswers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assessmentAnswers in body.
     */
    @GetMapping("/assessment-answers")
    public ResponseEntity<List<AssessmentAnswerDTO>> getAllAssessmentAnswers(
        AssessmentAnswerCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AssessmentAnswers by criteria: {}", criteria);
        Page<AssessmentAnswerDTO> page = assessmentAnswerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /assessment-answers/count} : count all the assessmentAnswers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/assessment-answers/count")
    public ResponseEntity<Long> countAssessmentAnswers(AssessmentAnswerCriteria criteria) {
        log.debug("REST request to count AssessmentAnswers by criteria: {}", criteria);
        return ResponseEntity.ok().body(assessmentAnswerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /assessment-answers/:id} : get the "id" assessmentAnswer.
     *
     * @param id the id of the assessmentAnswerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assessmentAnswerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assessment-answers/{id}")
    public ResponseEntity<AssessmentAnswerDTO> getAssessmentAnswer(@PathVariable Long id) {
        log.debug("REST request to get AssessmentAnswer : {}", id);
        Optional<AssessmentAnswerDTO> assessmentAnswerDTO = assessmentAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assessmentAnswerDTO);
    }

    /**
     * {@code DELETE  /assessment-answers/:id} : delete the "id" assessmentAnswer.
     *
     * @param id the id of the assessmentAnswerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assessment-answers/{id}")
    public ResponseEntity<Void> deleteAssessmentAnswer(@PathVariable Long id) {
        log.debug("REST request to delete AssessmentAnswer : {}", id);
        assessmentAnswerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
