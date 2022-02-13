package com.techvg.covid.care.isolation.service;

import com.techvg.covid.care.isolation.domain.*; // for static metamodels
import com.techvg.covid.care.isolation.domain.Assessment;
import com.techvg.covid.care.isolation.repository.AssessmentRepository;
import com.techvg.covid.care.isolation.service.criteria.AssessmentCriteria;
import com.techvg.covid.care.isolation.service.dto.AssessmentDTO;
import com.techvg.covid.care.isolation.service.mapper.AssessmentMapper;
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
 * Service for executing complex queries for {@link Assessment} entities in the database.
 * The main input is a {@link AssessmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AssessmentDTO} or a {@link Page} of {@link AssessmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssessmentQueryService extends QueryService<Assessment> {

    private final Logger log = LoggerFactory.getLogger(AssessmentQueryService.class);

    private final AssessmentRepository assessmentRepository;

    private final AssessmentMapper assessmentMapper;

    public AssessmentQueryService(AssessmentRepository assessmentRepository, AssessmentMapper assessmentMapper) {
        this.assessmentRepository = assessmentRepository;
        this.assessmentMapper = assessmentMapper;
    }

    /**
     * Return a {@link List} of {@link AssessmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AssessmentDTO> findByCriteria(AssessmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Assessment> specification = createSpecification(criteria);
        return assessmentMapper.toDto(assessmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AssessmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AssessmentDTO> findByCriteria(AssessmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Assessment> specification = createSpecification(criteria);
        return assessmentRepository.findAll(specification, page).map(assessmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AssessmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Assessment> specification = createSpecification(criteria);
        return assessmentRepository.count(specification);
    }

    /**
     * Function to convert {@link AssessmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Assessment> createSpecification(AssessmentCriteria criteria) {
        Specification<Assessment> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Assessment_.id));
            }
            if (criteria.getAssessmentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssessmentDate(), Assessment_.assessmentDate));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Assessment_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Assessment_.lastModifiedBy));
            }
            if (criteria.getIsolationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIsolationId(),
                            root -> root.join(Assessment_.isolation, JoinType.LEFT).get(Isolation_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
