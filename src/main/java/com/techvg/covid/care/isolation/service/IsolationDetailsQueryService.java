package com.techvg.covid.care.isolation.service;

import com.techvg.covid.care.isolation.domain.*; // for static metamodels
import com.techvg.covid.care.isolation.domain.IsolationDetails;
import com.techvg.covid.care.isolation.repository.IsolationDetailsRepository;
import com.techvg.covid.care.isolation.service.criteria.IsolationDetailsCriteria;
import com.techvg.covid.care.isolation.service.dto.IsolationDetailsDTO;
import com.techvg.covid.care.isolation.service.mapper.IsolationDetailsMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link IsolationDetails} entities in the database.
 * The main input is a {@link IsolationDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IsolationDetailsDTO} or a {@link Page} of {@link IsolationDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IsolationDetailsQueryService extends QueryService<IsolationDetails> {

    private final Logger log = LoggerFactory.getLogger(IsolationDetailsQueryService.class);

    private final IsolationDetailsRepository isolationDetailsRepository;

    private final IsolationDetailsMapper isolationDetailsMapper;

    public IsolationDetailsQueryService(
        IsolationDetailsRepository isolationDetailsRepository,
        IsolationDetailsMapper isolationDetailsMapper
    ) {
        this.isolationDetailsRepository = isolationDetailsRepository;
        this.isolationDetailsMapper = isolationDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link IsolationDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IsolationDetailsDTO> findByCriteria(IsolationDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IsolationDetails> specification = createSpecification(criteria);
        return isolationDetailsMapper.toDto(isolationDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IsolationDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IsolationDetailsDTO> findByCriteria(IsolationDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IsolationDetails> specification = createSpecification(criteria);
        return isolationDetailsRepository.findAll(specification, page).map(isolationDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IsolationDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IsolationDetails> specification = createSpecification(criteria);
        return isolationDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link IsolationDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IsolationDetails> createSpecification(IsolationDetailsCriteria criteria) {
        Specification<IsolationDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IsolationDetails_.id));
            }
            if (criteria.getReferredDrName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferredDrName(), IsolationDetails_.referredDrName));
            }
            if (criteria.getReferredDrMobile() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getReferredDrMobile(), IsolationDetails_.referredDrMobile));
            }
            if (criteria.getPrescriptionUrl() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrescriptionUrl(), IsolationDetails_.prescriptionUrl));
            }
            if (criteria.getReportUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReportUrl(), IsolationDetails_.reportUrl));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), IsolationDetails_.remarks));
            }
            if (criteria.getSelfRegistered() != null) {
                specification = specification.and(buildSpecification(criteria.getSelfRegistered(), IsolationDetails_.selfRegistered));
            }
            if (criteria.getLastAssessment() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastAssessment(), IsolationDetails_.lastAssessment));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), IsolationDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), IsolationDetails_.lastModifiedBy));
            }
        }
        return specification;
    }
}
