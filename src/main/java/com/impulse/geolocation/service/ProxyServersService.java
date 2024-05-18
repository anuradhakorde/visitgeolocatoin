package com.impulse.geolocation.service;

import com.impulse.geolocation.domain.ProxyServers;
import com.impulse.geolocation.repository.ProxyServersRepository;
import com.impulse.geolocation.service.dto.ProxyServersDTO;
import com.impulse.geolocation.service.mapper.ProxyServersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.impulse.geolocation.domain.ProxyServers}.
 */
@Service
@Transactional
public class ProxyServersService {

    private final Logger log = LoggerFactory.getLogger(ProxyServersService.class);

    private final ProxyServersRepository proxyServersRepository;

    private final ProxyServersMapper proxyServersMapper;

    public ProxyServersService(ProxyServersRepository proxyServersRepository, ProxyServersMapper proxyServersMapper) {
        this.proxyServersRepository = proxyServersRepository;
        this.proxyServersMapper = proxyServersMapper;
    }

    /**
     * Save a proxyServers.
     *
     * @param proxyServersDTO the entity to save.
     * @return the persisted entity.
     */
    public ProxyServersDTO save(ProxyServersDTO proxyServersDTO) {
        log.debug("Request to save ProxyServers : {}", proxyServersDTO);
        ProxyServers proxyServers = proxyServersMapper.toEntity(proxyServersDTO);
        proxyServers = proxyServersRepository.save(proxyServers);
        return proxyServersMapper.toDto(proxyServers);
    }

    /**
     * Update a proxyServers.
     *
     * @param proxyServersDTO the entity to save.
     * @return the persisted entity.
     */
    public ProxyServersDTO update(ProxyServersDTO proxyServersDTO) {
        log.debug("Request to update ProxyServers : {}", proxyServersDTO);
        ProxyServers proxyServers = proxyServersMapper.toEntity(proxyServersDTO);
        proxyServers = proxyServersRepository.save(proxyServers);
        return proxyServersMapper.toDto(proxyServers);
    }

    /**
     * Partially update a proxyServers.
     *
     * @param proxyServersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProxyServersDTO> partialUpdate(ProxyServersDTO proxyServersDTO) {
        log.debug("Request to partially update ProxyServers : {}", proxyServersDTO);

        return proxyServersRepository
            .findById(proxyServersDTO.getId())
            .map(existingProxyServers -> {
                proxyServersMapper.partialUpdate(existingProxyServers, proxyServersDTO);

                return existingProxyServers;
            })
            .map(proxyServersRepository::save)
            .map(proxyServersMapper::toDto);
    }

    /**
     * Get all the proxyServers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProxyServersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProxyServers");
        return proxyServersRepository.findAll(pageable).map(proxyServersMapper::toDto);
    }

    /**
     * Get one proxyServers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProxyServersDTO> findOne(Long id) {
        log.debug("Request to get ProxyServers : {}", id);
        return proxyServersRepository.findById(id).map(proxyServersMapper::toDto);
    }

    /**
     * Delete the proxyServers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProxyServers : {}", id);
        proxyServersRepository.deleteById(id);
    }
}
