package com.techvg.covid.care.isolation.repository;

import com.techvg.covid.care.isolation.domain.Isolation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Isolation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IsolationRepository extends JpaRepository<Isolation, Long>, JpaSpecificationExecutor<Isolation> {}
