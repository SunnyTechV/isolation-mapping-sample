package com.techvg.covid.care.isolation.service;

import com.techvg.covid.care.isolation.domain.AssessmentAnswer;
import com.techvg.covid.care.isolation.repository.AssessmentAnswerRepository;
import com.techvg.covid.care.isolation.service.dto.AssessmentAnswerDTO;
import com.techvg.covid.care.isolation.service.mapper.AssessmentAnswerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AssessmentAnswer}.
 */
@Service
@Transactional
public class AssessmentAnswerService {

    private final Logger log = LoggerFactory.getLogger(AssessmentAnswerService.class);

    private final AssessmentAnswerRepository assessmentAnswerRepository;

    private final AssessmentAnswerMapper assessmentAnswerMapper;

    public AssessmentAnswerService(AssessmentAnswerRepository assessmentAnswerRepository, AssessmentAnswerMapper assessmentAnswerMapper) {
        this.assessmentAnswerRepository = assessmentAnswerRepository;
        this.assessmentAnswerMapper = assessmentAnswerMapper;
    }

    /**
     * Save a assessmentAnswer.
     *
     * @param assessmentAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    public AssessmentAnswerDTO save(AssessmentAnswerDTO assessmentAnswerDTO) {
        log.debug("Request to save AssessmentAnswer : {}", assessmentAnswerDTO);
        AssessmentAnswer assessmentAnswer = assessmentAnswerMapper.toEntity(assessmentAnswerDTO);
        assessmentAnswer = assessmentAnswerRepository.save(assessmentAnswer);
        return assessmentAnswerMapper.toDto(assessmentAnswer);
    }

    /**
     * Partially update a assessmentAnswer.
     *
     * @param assessmentAnswerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssessmentAnswerDTO> partialUpdate(AssessmentAnswerDTO assessmentAnswerDTO) {
        log.debug("Request to partially update AssessmentAnswer : {}", assessmentAnswerDTO);

        return assessmentAnswerRepository
            .findById(assessmentAnswerDTO.getId())
            .map(existingAssessmentAnswer -> {
                assessmentAnswerMapper.partialUpdate(existingAssessmentAnswer, assessmentAnswerDTO);

                return existingAssessmentAnswer;
            })
            .map(assessmentAnswerRepository::save)
            .map(assessmentAnswerMapper::toDto);
    }

    /**
     * Get all the assessmentAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AssessmentAnswerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AssessmentAnswers");
        return assessmentAnswerRepository.findAll(pageable).map(assessmentAnswerMapper::toDto);
    }

    /**
     * Get one assessmentAnswer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssessmentAnswerDTO> findOne(Long id) {
        log.debug("Request to get AssessmentAnswer : {}", id);
        return assessmentAnswerRepository.findById(id).map(assessmentAnswerMapper::toDto);
    }

    /**
     * Delete the assessmentAnswer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AssessmentAnswer : {}", id);
        assessmentAnswerRepository.deleteById(id);
    }
}
