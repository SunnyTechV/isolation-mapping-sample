package com.techvg.covid.care.isolation.web.rest;

import com.techvg.covid.care.isolation.repository.IsolationRepository;
import com.techvg.covid.care.isolation.service.IsolationQueryService;
import com.techvg.covid.care.isolation.service.IsolationService;
import com.techvg.covid.care.isolation.service.criteria.IsolationCriteria;
import com.techvg.covid.care.isolation.service.dto.IsolationDTO;
import com.techvg.covid.care.isolation.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.techvg.covid.care.isolation.domain.Isolation}.
 */
@RestController
@RequestMapping("/api")
public class IsolationResource {

    private final Logger log = LoggerFactory.getLogger(IsolationResource.class);

    private static final String ENTITY_NAME = "isolation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IsolationService isolationService;

    private final IsolationRepository isolationRepository;

    private final IsolationQueryService isolationQueryService;

    public IsolationResource(
        IsolationService isolationService,
        IsolationRepository isolationRepository,
        IsolationQueryService isolationQueryService
    ) {
        this.isolationService = isolationService;
        this.isolationRepository = isolationRepository;
        this.isolationQueryService = isolationQueryService;
    }

    /**
     * {@code POST  /isolations} : Create a new isolation.
     *
     * @param isolationDTO the isolationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new isolationDTO, or with status {@code 400 (Bad Request)} if the isolation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/isolations")
    public ResponseEntity<IsolationDTO> createIsolation(@Valid @RequestBody IsolationDTO isolationDTO) throws URISyntaxException {
        log.debug("REST request to save Isolation : {}", isolationDTO);
        if (isolationDTO.getId() != null) {
            throw new BadRequestAlertException("A new isolation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IsolationDTO result = isolationService.save(isolationDTO);
        return ResponseEntity
            .created(new URI("/api/isolations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /isolations/:id} : Updates an existing isolation.
     *
     * @param id the id of the isolationDTO to save.
     * @param isolationDTO the isolationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated isolationDTO,
     * or with status {@code 400 (Bad Request)} if the isolationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the isolationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/isolations/{id}")
    public ResponseEntity<IsolationDTO> updateIsolation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IsolationDTO isolationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Isolation : {}, {}", id, isolationDTO);
        if (isolationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, isolationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!isolationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IsolationDTO result = isolationService.save(isolationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, isolationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /isolations/:id} : Partial updates given fields of an existing isolation, field will ignore if it is null
     *
     * @param id the id of the isolationDTO to save.
     * @param isolationDTO the isolationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated isolationDTO,
     * or with status {@code 400 (Bad Request)} if the isolationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the isolationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the isolationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/isolations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IsolationDTO> partialUpdateIsolation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IsolationDTO isolationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Isolation partially : {}, {}", id, isolationDTO);
        if (isolationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, isolationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!isolationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IsolationDTO> result = isolationService.partialUpdate(isolationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, isolationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /isolations} : get all the isolations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of isolations in body.
     */
    @GetMapping("/isolations")
    public ResponseEntity<List<IsolationDTO>> getAllIsolations(
        IsolationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Isolations by criteria: {}", criteria);
        Page<IsolationDTO> page = isolationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /isolations/count} : count all the isolations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/isolations/count")
    public ResponseEntity<Long> countIsolations(IsolationCriteria criteria) {
        log.debug("REST request to count Isolations by criteria: {}", criteria);
        return ResponseEntity.ok().body(isolationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /isolations/:id} : get the "id" isolation.
     *
     * @param id the id of the isolationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the isolationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/isolations/{id}")
    public ResponseEntity<IsolationDTO> getIsolation(@PathVariable Long id) {
        log.debug("REST request to get Isolation : {}", id);
        Optional<IsolationDTO> isolationDTO = isolationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(isolationDTO);
    }

    /**
     * {@code DELETE  /isolations/:id} : delete the "id" isolation.
     *
     * @param id the id of the isolationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/isolations/{id}")
    public ResponseEntity<Void> deleteIsolation(@PathVariable Long id) {
        log.debug("REST request to delete Isolation : {}", id);
        isolationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
