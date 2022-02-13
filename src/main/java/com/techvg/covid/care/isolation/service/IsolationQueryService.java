package com.techvg.covid.care.isolation.service;

import com.techvg.covid.care.isolation.domain.*; // for static metamodels
import com.techvg.covid.care.isolation.domain.Isolation;
import com.techvg.covid.care.isolation.repository.IsolationRepository;
import com.techvg.covid.care.isolation.service.criteria.IsolationCriteria;
import com.techvg.covid.care.isolation.service.dto.IsolationDTO;
import com.techvg.covid.care.isolation.service.mapper.IsolationMapper;
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
 * Service for executing complex queries for {@link Isolation} entities in the database.
 * The main input is a {@link IsolationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IsolationDTO} or a {@link Page} of {@link IsolationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IsolationQueryService extends QueryService<Isolation> {

    private final Logger log = LoggerFactory.getLogger(IsolationQueryService.class);

    private final IsolationRepository isolationRepository;

    private final IsolationMapper isolationMapper;

    public IsolationQueryService(IsolationRepository isolationRepository, IsolationMapper isolationMapper) {
        this.isolationRepository = isolationRepository;
        this.isolationMapper = isolationMapper;
    }

    /**
     * Return a {@link List} of {@link IsolationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IsolationDTO> findByCriteria(IsolationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Isolation> specification = createSpecification(criteria);
        return isolationMapper.toDto(isolationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IsolationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IsolationDTO> findByCriteria(IsolationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Isolation> specification = createSpecification(criteria);
        return isolationRepository.findAll(specification, page).map(isolationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IsolationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Isolation> specification = createSpecification(criteria);
        return isolationRepository.count(specification);
    }

    /**
     * Function to convert {@link IsolationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Isolation> createSpecification(IsolationCriteria criteria) {
        Specification<Isolation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Isolation_.id));
            }
            if (criteria.getIcmrId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcmrId(), Isolation_.icmrId));
            }
            if (criteria.getRtpcrId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRtpcrId(), Isolation_.rtpcrId));
            }
            if (criteria.getRatId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRatId(), Isolation_.ratId));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Isolation_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Isolation_.lastName));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLatitude(), Isolation_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLongitude(), Isolation_.longitude));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Isolation_.email));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), Isolation_.imageUrl));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), Isolation_.activated));
            }
            if (criteria.getMobileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNo(), Isolation_.mobileNo));
            }
            if (criteria.getPasswordHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPasswordHash(), Isolation_.passwordHash));
            }
            if (criteria.getSecondaryContactNo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSecondaryContactNo(), Isolation_.secondaryContactNo));
            }
            if (criteria.getAadharCardNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadharCardNo(), Isolation_.aadharCardNo));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Isolation_.status));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAge(), Isolation_.age));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), Isolation_.gender));
            }
            if (criteria.getStateId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStateId(), Isolation_.stateId));
            }
            if (criteria.getDistrictId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDistrictId(), Isolation_.districtId));
            }
            if (criteria.getTalukaId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTalukaId(), Isolation_.talukaId));
            }
            if (criteria.getCityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCityId(), Isolation_.cityId));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Isolation_.address));
            }
            if (criteria.getPincode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPincode(), Isolation_.pincode));
            }
            if (criteria.getCollectionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCollectionDate(), Isolation_.collectionDate));
            }
            if (criteria.getHospitalized() != null) {
                specification = specification.and(buildSpecification(criteria.getHospitalized(), Isolation_.hospitalized));
            }
            if (criteria.getHospitalId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHospitalId(), Isolation_.hospitalId));
            }
            if (criteria.getAddressLatitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressLatitude(), Isolation_.addressLatitude));
            }
            if (criteria.getAddressLongitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressLongitude(), Isolation_.addressLongitude));
            }
            if (criteria.getCurrentLatitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentLatitude(), Isolation_.currentLatitude));
            }
            if (criteria.getCurrentLongitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentLongitude(), Isolation_.currentLongitude));
            }
            if (criteria.getHospitalizationDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getHospitalizationDate(), Isolation_.hospitalizationDate));
            }
            if (criteria.getHealthCondition() != null) {
                specification = specification.and(buildSpecification(criteria.getHealthCondition(), Isolation_.healthCondition));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), Isolation_.remarks));
            }
            if (criteria.getSymptomatic() != null) {
                specification = specification.and(buildSpecification(criteria.getSymptomatic(), Isolation_.symptomatic));
            }
            if (criteria.getCcmsLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCcmsLogin(), Isolation_.ccmsLogin));
            }
            if (criteria.getSelfRegistered() != null) {
                specification = specification.and(buildSpecification(criteria.getSelfRegistered(), Isolation_.selfRegistered));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Isolation_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Isolation_.lastModifiedBy));
            }
            if (criteria.getIsolationStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsolationStartDate(), Isolation_.isolationStartDate));
            }
            if (criteria.getIsolationEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsolationEndDate(), Isolation_.isolationEndDate));
            }
            if (criteria.getTvgIsolationUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTvgIsolationUserId(), Isolation_.tvgIsolationUserId));
            }
            if (criteria.getIsolationDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIsolationDetailsId(),
                            root -> root.join(Isolation_.isolationDetails, JoinType.LEFT).get(IsolationDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
