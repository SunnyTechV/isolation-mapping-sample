package com.techvg.covid.care.isolation.web.rest;

import com.techvg.covid.care.isolation.repository.QuestionsOptionsRepository;
import com.techvg.covid.care.isolation.service.QuestionsOptionsQueryService;
import com.techvg.covid.care.isolation.service.QuestionsOptionsService;
import com.techvg.covid.care.isolation.service.criteria.QuestionsOptionsCriteria;
import com.techvg.covid.care.isolation.service.dto.QuestionsOptionsDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.isolation.domain.QuestionsOptions}.
 */
@RestController
@RequestMapping("/api")
public class QuestionsOptionsResource {

    private final Logger log = LoggerFactory.getLogger(QuestionsOptionsResource.class);

    private static final String ENTITY_NAME = "questionsOptions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionsOptionsService questionsOptionsService;

    private final QuestionsOptionsRepository questionsOptionsRepository;

    private final QuestionsOptionsQueryService questionsOptionsQueryService;

    public QuestionsOptionsResource(
        QuestionsOptionsService questionsOptionsService,
        QuestionsOptionsRepository questionsOptionsRepository,
        QuestionsOptionsQueryService questionsOptionsQueryService
    ) {
        this.questionsOptionsService = questionsOptionsService;
        this.questionsOptionsRepository = questionsOptionsRepository;
        this.questionsOptionsQueryService = questionsOptionsQueryService;
    }

    /**
     * {@code POST  /questions-options} : Create a new questionsOptions.
     *
     * @param questionsOptionsDTO the questionsOptionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionsOptionsDTO, or with status {@code 400 (Bad Request)} if the questionsOptions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/questions-options")
    public ResponseEntity<QuestionsOptionsDTO> createQuestionsOptions(@RequestBody QuestionsOptionsDTO questionsOptionsDTO)
        throws URISyntaxException {
        log.debug("REST request to save QuestionsOptions : {}", questionsOptionsDTO);
        if (questionsOptionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new questionsOptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionsOptionsDTO result = questionsOptionsService.save(questionsOptionsDTO);
        return ResponseEntity
            .created(new URI("/api/questions-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /questions-options/:id} : Updates an existing questionsOptions.
     *
     * @param id the id of the questionsOptionsDTO to save.
     * @param questionsOptionsDTO the questionsOptionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionsOptionsDTO,
     * or with status {@code 400 (Bad Request)} if the questionsOptionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionsOptionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/questions-options/{id}")
    public ResponseEntity<QuestionsOptionsDTO> updateQuestionsOptions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuestionsOptionsDTO questionsOptionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update QuestionsOptions : {}, {}", id, questionsOptionsDTO);
        if (questionsOptionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questionsOptionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionsOptionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QuestionsOptionsDTO result = questionsOptionsService.save(questionsOptionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, questionsOptionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /questions-options/:id} : Partial updates given fields of an existing questionsOptions, field will ignore if it is null
     *
     * @param id the id of the questionsOptionsDTO to save.
     * @param questionsOptionsDTO the questionsOptionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionsOptionsDTO,
     * or with status {@code 400 (Bad Request)} if the questionsOptionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the questionsOptionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the questionsOptionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/questions-options/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QuestionsOptionsDTO> partialUpdateQuestionsOptions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuestionsOptionsDTO questionsOptionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update QuestionsOptions partially : {}, {}", id, questionsOptionsDTO);
        if (questionsOptionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questionsOptionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionsOptionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuestionsOptionsDTO> result = questionsOptionsService.partialUpdate(questionsOptionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, questionsOptionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /questions-options} : get all the questionsOptions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionsOptions in body.
     */
    @GetMapping("/questions-options")
    public ResponseEntity<List<QuestionsOptionsDTO>> getAllQuestionsOptions(
        QuestionsOptionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get QuestionsOptions by criteria: {}", criteria);
        Page<QuestionsOptionsDTO> page = questionsOptionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /questions-options/count} : count all the questionsOptions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/questions-options/count")
    public ResponseEntity<Long> countQuestionsOptions(QuestionsOptionsCriteria criteria) {
        log.debug("REST request to count QuestionsOptions by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionsOptionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /questions-options/:id} : get the "id" questionsOptions.
     *
     * @param id the id of the questionsOptionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionsOptionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/questions-options/{id}")
    public ResponseEntity<QuestionsOptionsDTO> getQuestionsOptions(@PathVariable Long id) {
        log.debug("REST request to get QuestionsOptions : {}", id);
        Optional<QuestionsOptionsDTO> questionsOptionsDTO = questionsOptionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionsOptionsDTO);
    }

    /**
     * {@code DELETE  /questions-options/:id} : delete the "id" questionsOptions.
     *
     * @param id the id of the questionsOptionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/questions-options/{id}")
    public ResponseEntity<Void> deleteQuestionsOptions(@PathVariable Long id) {
        log.debug("REST request to delete QuestionsOptions : {}", id);
        questionsOptionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
