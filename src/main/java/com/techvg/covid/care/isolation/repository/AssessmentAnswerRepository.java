package com.techvg.covid.care.isolation.repository;

import com.techvg.covid.care.isolation.domain.AssessmentAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AssessmentAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssessmentAnswerRepository extends JpaRepository<AssessmentAnswer, Long>, JpaSpecificationExecutor<AssessmentAnswer> {}
