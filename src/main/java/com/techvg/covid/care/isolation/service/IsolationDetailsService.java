package com.techvg.covid.care.isolation.service;

import com.techvg.covid.care.isolation.domain.IsolationDetails;
import com.techvg.covid.care.isolation.repository.IsolationDetailsRepository;
import com.techvg.covid.care.isolation.service.dto.IsolationDetailsDTO;
import com.techvg.covid.care.isolation.service.mapper.IsolationDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IsolationDetails}.
 */
@Service
@Transactional
public class IsolationDetailsService {

    private final Logger log = LoggerFactory.getLogger(IsolationDetailsService.class);

    private final IsolationDetailsRepository isolationDetailsRepository;

    private final IsolationDetailsMapper isolationDetailsMapper;

    public IsolationDetailsService(IsolationDetailsRepository isolationDetailsRepository, IsolationDetailsMapper isolationDetailsMapper) {
        this.isolationDetailsRepository = isolationDetailsRepository;
        this.isolationDetailsMapper = isolationDetailsMapper;
    }

    /**
     * Save a isolationDetails.
     *
     * @param isolationDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public IsolationDetailsDTO save(IsolationDetailsDTO isolationDetailsDTO) {
        log.debug("Request to save IsolationDetails : {}", isolationDetailsDTO);
        IsolationDetails isolationDetails = isolationDetailsMapper.toEntity(isolationDetailsDTO);
        isolationDetails = isolationDetailsRepository.save(isolationDetails);
        return isolationDetailsMapper.toDto(isolationDetails);
    }

    /**
     * Partially update a isolationDetails.
     *
     * @param isolationDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<IsolationDetailsDTO> partialUpdate(IsolationDetailsDTO isolationDetailsDTO) {
        log.debug("Request to partially update IsolationDetails : {}", isolationDetailsDTO);

        return isolationDetailsRepository
            .findById(isolationDetailsDTO.getId())
            .map(existingIsolationDetails -> {
                isolationDetailsMapper.partialUpdate(existingIsolationDetails, isolationDetailsDTO);

                return existingIsolationDetails;
            })
            .map(isolationDetailsRepository::save)
            .map(isolationDetailsMapper::toDto);
    }

    /**
     * Get all the isolationDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IsolationDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IsolationDetails");
        return isolationDetailsRepository.findAll(pageable).map(isolationDetailsMapper::toDto);
    }

    /**
     * Get one isolationDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IsolationDetailsDTO> findOne(Long id) {
        log.debug("Request to get IsolationDetails : {}", id);
        return isolationDetailsRepository.findById(id).map(isolationDetailsMapper::toDto);
    }

    /**
     * Delete the isolationDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IsolationDetails : {}", id);
        isolationDetailsRepository.deleteById(id);
    }
}
