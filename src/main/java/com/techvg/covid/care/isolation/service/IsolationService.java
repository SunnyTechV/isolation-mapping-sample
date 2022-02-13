package com.techvg.covid.care.isolation.service;

import com.techvg.covid.care.isolation.domain.Isolation;
import com.techvg.covid.care.isolation.repository.IsolationRepository;
import com.techvg.covid.care.isolation.service.dto.IsolationDTO;
import com.techvg.covid.care.isolation.service.mapper.IsolationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Isolation}.
 */
@Service
@Transactional
public class IsolationService {

    private final Logger log = LoggerFactory.getLogger(IsolationService.class);

    private final IsolationRepository isolationRepository;

    private final IsolationMapper isolationMapper;

    public IsolationService(IsolationRepository isolationRepository, IsolationMapper isolationMapper) {
        this.isolationRepository = isolationRepository;
        this.isolationMapper = isolationMapper;
    }

    /**
     * Save a isolation.
     *
     * @param isolationDTO the entity to save.
     * @return the persisted entity.
     */
    public IsolationDTO save(IsolationDTO isolationDTO) {
        log.debug("Request to save Isolation : {}", isolationDTO);
        Isolation isolation = isolationMapper.toEntity(isolationDTO);
        isolation = isolationRepository.save(isolation);
        return isolationMapper.toDto(isolation);
    }

    /**
     * Partially update a isolation.
     *
     * @param isolationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<IsolationDTO> partialUpdate(IsolationDTO isolationDTO) {
        log.debug("Request to partially update Isolation : {}", isolationDTO);

        return isolationRepository
            .findById(isolationDTO.getId())
            .map(existingIsolation -> {
                isolationMapper.partialUpdate(existingIsolation, isolationDTO);

                return existingIsolation;
            })
            .map(isolationRepository::save)
            .map(isolationMapper::toDto);
    }

    /**
     * Get all the isolations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IsolationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Isolations");
        return isolationRepository.findAll(pageable).map(isolationMapper::toDto);
    }

    /**
     * Get one isolation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IsolationDTO> findOne(Long id) {
        log.debug("Request to get Isolation : {}", id);
        return isolationRepository.findById(id).map(isolationMapper::toDto);
    }

    /**
     * Delete the isolation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Isolation : {}", id);
        isolationRepository.deleteById(id);
    }
}
