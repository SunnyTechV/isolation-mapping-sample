package com.techvg.covid.care.isolation.service;

import com.techvg.covid.care.isolation.domain.*; // for static metamodels
import com.techvg.covid.care.isolation.domain.QuestionsOptions;
import com.techvg.covid.care.isolation.repository.QuestionsOptionsRepository;
import com.techvg.covid.care.isolation.service.criteria.QuestionsOptionsCriteria;
import com.techvg.covid.care.isolation.service.dto.QuestionsOptionsDTO;
import com.techvg.covid.care.isolation.service.mapper.QuestionsOptionsMapper;
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
 * Service for executing complex queries for {@link QuestionsOptions} entities in the database.
 * The main input is a {@link QuestionsOptionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionsOptionsDTO} or a {@link Page} of {@link QuestionsOptionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionsOptionsQueryService extends QueryService<QuestionsOptions> {

    private final Logger log = LoggerFactory.getLogger(QuestionsOptionsQueryService.class);

    private final QuestionsOptionsRepository questionsOptionsRepository;

    private final QuestionsOptionsMapper questionsOptionsMapper;

    public QuestionsOptionsQueryService(
        QuestionsOptionsRepository questionsOptionsRepository,
        QuestionsOptionsMapper questionsOptionsMapper
    ) {
        this.questionsOptionsRepository = questionsOptionsRepository;
        this.questionsOptionsMapper = questionsOptionsMapper;
    }

    /**
     * Return a {@link List} of {@link QuestionsOptionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionsOptionsDTO> findByCriteria(QuestionsOptionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionsOptions> specification = createSpecification(criteria);
        return questionsOptionsMapper.toDto(questionsOptionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuestionsOptionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionsOptionsDTO> findByCriteria(QuestionsOptionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionsOptions> specification = createSpecification(criteria);
        return questionsOptionsRepository.findAll(specification, page).map(questionsOptionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionsOptionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionsOptions> specification = createSpecification(criteria);
        return questionsOptionsRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionsOptionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QuestionsOptions> createSpecification(QuestionsOptionsCriteria criteria) {
        Specification<QuestionsOptions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QuestionsOptions_.id));
            }
            if (criteria.getAnsOption() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnsOption(), QuestionsOptions_.ansOption));
            }
            if (criteria.getHealthCondition() != null) {
                specification = specification.and(buildSpecification(criteria.getHealthCondition(), QuestionsOptions_.healthCondition));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), QuestionsOptions_.active));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), QuestionsOptions_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), QuestionsOptions_.lastModifiedBy));
            }
            if (criteria.getQuestionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQuestionId(),
                            root -> root.join(QuestionsOptions_.question, JoinType.LEFT).get(Question_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
