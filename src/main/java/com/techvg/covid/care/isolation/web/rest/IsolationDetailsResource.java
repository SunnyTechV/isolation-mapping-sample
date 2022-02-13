package com.techvg.covid.care.isolation.web.rest;

import com.techvg.covid.care.isolation.repository.IsolationDetailsRepository;
import com.techvg.covid.care.isolation.service.IsolationDetailsQueryService;
import com.techvg.covid.care.isolation.service.IsolationDetailsService;
import com.techvg.covid.care.isolation.service.criteria.IsolationDetailsCriteria;
import com.techvg.covid.care.isolation.service.dto.IsolationDetailsDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.isolation.domain.IsolationDetails}.
 */
@RestController
@RequestMapping("/api")
public class IsolationDetailsResource {

    private final Logger log = LoggerFactory.getLogger(IsolationDetailsResource.class);

    private static final String ENTITY_NAME = "isolationDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IsolationDetailsService isolationDetailsService;

    private final IsolationDetailsRepository isolationDetailsRepository;

    private final IsolationDetailsQueryService isolationDetailsQueryService;

    public IsolationDetailsResource(
        IsolationDetailsService isolationDetailsService,
        IsolationDetailsRepository isolationDetailsRepository,
        IsolationDetailsQueryService isolationDetailsQueryService
    ) {
        this.isolationDetailsService = isolationDetailsService;
        this.isolationDetailsRepository = isolationDetailsRepository;
        this.isolationDetailsQueryService = isolationDetailsQueryService;
    }

    /**
     * {@code POST  /isolation-details} : Create a new isolationDetails.
     *
     * @param isolationDetailsDTO the isolationDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new isolationDetailsDTO, or with status {@code 400 (Bad Request)} if the isolationDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/isolation-details")
    public ResponseEntity<IsolationDetailsDTO> createIsolationDetails(@RequestBody IsolationDetailsDTO isolationDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save IsolationDetails : {}", isolationDetailsDTO);
        if (isolationDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new isolationDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IsolationDetailsDTO result = isolationDetailsService.save(isolationDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/isolation-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /isolation-details/:id} : Updates an existing isolationDetails.
     *
     * @param id the id of the isolationDetailsDTO to save.
     * @param isolationDetailsDTO the isolationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated isolationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the isolationDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the isolationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/isolation-details/{id}")
    public ResponseEntity<IsolationDetailsDTO> updateIsolationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IsolationDetailsDTO isolationDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IsolationDetails : {}, {}", id, isolationDetailsDTO);
        if (isolationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, isolationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!isolationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IsolationDetailsDTO result = isolationDetailsService.save(isolationDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, isolationDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /isolation-details/:id} : Partial updates given fields of an existing isolationDetails, field will ignore if it is null
     *
     * @param id the id of the isolationDetailsDTO to save.
     * @param isolationDetailsDTO the isolationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated isolationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the isolationDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the isolationDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the isolationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/isolation-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IsolationDetailsDTO> partialUpdateIsolationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IsolationDetailsDTO isolationDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IsolationDetails partially : {}, {}", id, isolationDetailsDTO);
        if (isolationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, isolationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!isolationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IsolationDetailsDTO> result = isolationDetailsService.partialUpdate(isolationDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, isolationDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /isolation-details} : get all the isolationDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of isolationDetails in body.
     */
    @GetMapping("/isolation-details")
    public ResponseEntity<List<IsolationDetailsDTO>> getAllIsolationDetails(
        IsolationDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get IsolationDetails by criteria: {}", criteria);
        Page<IsolationDetailsDTO> page = isolationDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /isolation-details/count} : count all the isolationDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/isolation-details/count")
    public ResponseEntity<Long> countIsolationDetails(IsolationDetailsCriteria criteria) {
        log.debug("REST request to count IsolationDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(isolationDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /isolation-details/:id} : get the "id" isolationDetails.
     *
     * @param id the id of the isolationDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the isolationDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/isolation-details/{id}")
    public ResponseEntity<IsolationDetailsDTO> getIsolationDetails(@PathVariable Long id) {
        log.debug("REST request to get IsolationDetails : {}", id);
        Optional<IsolationDetailsDTO> isolationDetailsDTO = isolationDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(isolationDetailsDTO);
    }

    /**
     * {@code DELETE  /isolation-details/:id} : delete the "id" isolationDetails.
     *
     * @param id the id of the isolationDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/isolation-details/{id}")
    public ResponseEntity<Void> deleteIsolationDetails(@PathVariable Long id) {
        log.debug("REST request to delete IsolationDetails : {}", id);
        isolationDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
