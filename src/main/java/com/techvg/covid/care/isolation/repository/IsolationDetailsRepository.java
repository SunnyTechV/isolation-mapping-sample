package com.techvg.covid.care.isolation.repository;

import com.techvg.covid.care.isolation.domain.IsolationDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IsolationDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IsolationDetailsRepository extends JpaRepository<IsolationDetails, Long>, JpaSpecificationExecutor<IsolationDetails> {}
