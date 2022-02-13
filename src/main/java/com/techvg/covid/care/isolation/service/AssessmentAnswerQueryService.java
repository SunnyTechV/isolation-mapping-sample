package com.techvg.covid.care.isolation.service;

import com.techvg.covid.care.isolation.domain.*; // for static metamodels
import com.techvg.covid.care.isolation.domain.AssessmentAnswer;
import com.techvg.covid.care.isolation.repository.AssessmentAnswerRepository;
import com.techvg.covid.care.isolation.service.criteria.AssessmentAnswerCriteria;
import com.techvg.covid.care.isolation.service.dto.AssessmentAnswerDTO;
import com.techvg.covid.care.isolation.service.mapper.AssessmentAnswerMapper;
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
 * Service for executing complex queries for {@link AssessmentAnswer} entities in the database.
 * The main input is a {@link AssessmentAnswerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AssessmentAnswerDTO} or a {@link Page} of {@link AssessmentAnswerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssessmentAnswerQueryService extends QueryService<AssessmentAnswer> {

    private final Logger log = LoggerFactory.getLogger(AssessmentAnswerQueryService.class);

    private final AssessmentAnswerRepository assessmentAnswerRepository;

    private final AssessmentAnswerMapper assessmentAnswerMapper;

    public AssessmentAnswerQueryService(
        AssessmentAnswerRepository assessmentAnswerRepository,
        AssessmentAnswerMapper assessmentAnswerMapper
    ) {
        this.assessmentAnswerRepository = assessmentAnswerRepository;
        this.assessmentAnswerMapper = assessmentAnswerMapper;
    }

    /**
     * Return a {@link List} of {@link AssessmentAnswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AssessmentAnswerDTO> findByCriteria(AssessmentAnswerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AssessmentAnswer> specification = createSpecification(criteria);
        return assessmentAnswerMapper.toDto(assessmentAnswerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AssessmentAnswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AssessmentAnswerDTO> findByCriteria(AssessmentAnswerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AssessmentAnswer> specification = createSpecification(criteria);
        return assessmentAnswerRepository.findAll(specification, page).map(assessmentAnswerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AssessmentAnswerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AssessmentAnswer> specification = createSpecification(criteria);
        return assessmentAnswerRepository.count(specification);
    }

    /**
     * Function to convert {@link AssessmentAnswerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AssessmentAnswer> createSpecification(AssessmentAnswerCriteria criteria) {
        Specification<AssessmentAnswer> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AssessmentAnswer_.id));
            }
            if (criteria.getAnswer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnswer(), AssessmentAnswer_.answer));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), AssessmentAnswer_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AssessmentAnswer_.lastModifiedBy));
            }
            if (criteria.getAssessmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAssessmentId(),
                            root -> root.join(AssessmentAnswer_.assessment, JoinType.LEFT).get(Assessment_.id)
                        )
                    );
            }
            if (criteria.getQuestionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQuestionId(),
                            root -> root.join(AssessmentAnswer_.question, JoinType.LEFT).get(Question_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
