package com.impulse.geolocation.web.rest;

import com.impulse.geolocation.domain.ProxyServers;
import com.impulse.geolocation.repository.ProxyServersRepository;
import com.impulse.geolocation.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.impulse.geolocation.domain.ProxyServers}.
 */
@RestController
@RequestMapping("/api/proxy-servers")
@Transactional
public class ProxyServersResource {

    private final Logger log = LoggerFactory.getLogger(ProxyServersResource.class);

    private static final String ENTITY_NAME = "proxyServers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProxyServersRepository proxyServersRepository;

    public ProxyServersResource(ProxyServersRepository proxyServersRepository) {
        this.proxyServersRepository = proxyServersRepository;
    }

    /**
     * {@code POST  /proxy-servers} : Create a new proxyServers.
     *
     * @param proxyServers the proxyServers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proxyServers, or with status {@code 400 (Bad Request)} if the proxyServers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProxyServers> createProxyServers(@RequestBody ProxyServers proxyServers) throws URISyntaxException {
        log.debug("REST request to save ProxyServers : {}", proxyServers);
        if (proxyServers.getId() != null) {
            throw new BadRequestAlertException("A new proxyServers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        proxyServers = proxyServersRepository.save(proxyServers);
        return ResponseEntity.created(new URI("/api/proxy-servers/" + proxyServers.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, proxyServers.getId().toString()))
            .body(proxyServers);
    }

    /**
     * {@code PUT  /proxy-servers/:id} : Updates an existing proxyServers.
     *
     * @param id the id of the proxyServers to save.
     * @param proxyServers the proxyServers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proxyServers,
     * or with status {@code 400 (Bad Request)} if the proxyServers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proxyServers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProxyServers> updateProxyServers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProxyServers proxyServers
    ) throws URISyntaxException {
        log.debug("REST request to update ProxyServers : {}, {}", id, proxyServers);
        if (proxyServers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proxyServers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proxyServersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        proxyServers = proxyServersRepository.save(proxyServers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proxyServers.getId().toString()))
            .body(proxyServers);
    }

    /**
     * {@code PATCH  /proxy-servers/:id} : Partial updates given fields of an existing proxyServers, field will ignore if it is null
     *
     * @param id the id of the proxyServers to save.
     * @param proxyServers the proxyServers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proxyServers,
     * or with status {@code 400 (Bad Request)} if the proxyServers is not valid,
     * or with status {@code 404 (Not Found)} if the proxyServers is not found,
     * or with status {@code 500 (Internal Server Error)} if the proxyServers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProxyServers> partialUpdateProxyServers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProxyServers proxyServers
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProxyServers partially : {}, {}", id, proxyServers);
        if (proxyServers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proxyServers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proxyServersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProxyServers> result = proxyServersRepository
            .findById(proxyServers.getId())
            .map(existingProxyServers -> {
                if (proxyServers.getIpAddress() != null) {
                    existingProxyServers.setIpAddress(proxyServers.getIpAddress());
                }
                if (proxyServers.getPort() != null) {
                    existingProxyServers.setPort(proxyServers.getPort());
                }
                if (proxyServers.getCountry() != null) {
                    existingProxyServers.setCountry(proxyServers.getCountry());
                }
                if (proxyServers.getProtocol() != null) {
                    existingProxyServers.setProtocol(proxyServers.getProtocol());
                }
                if (proxyServers.getAnonymity() != null) {
                    existingProxyServers.setAnonymity(proxyServers.getAnonymity());
                }
                if (proxyServers.getOrganization() != null) {
                    existingProxyServers.setOrganization(proxyServers.getOrganization());
                }
                if (proxyServers.getSpeed() != null) {
                    existingProxyServers.setSpeed(proxyServers.getSpeed());
                }
                if (proxyServers.getResponseTime() != null) {
                    existingProxyServers.setResponseTime(proxyServers.getResponseTime());
                }
                if (proxyServers.getSuccessCount() != null) {
                    existingProxyServers.setSuccessCount(proxyServers.getSuccessCount());
                }
                if (proxyServers.getFailCount() != null) {
                    existingProxyServers.setFailCount(proxyServers.getFailCount());
                }

                return existingProxyServers;
            })
            .map(proxyServersRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proxyServers.getId().toString())
        );
    }

    /**
     * {@code GET  /proxy-servers} : get all the proxyServers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proxyServers in body.
     */
    @GetMapping("")
    public List<ProxyServers> getAllProxyServers() {
        log.debug("REST request to get all ProxyServers");
        return proxyServersRepository.findAll();
    }

    /**
     * {@code GET  /proxy-servers/:id} : get the "id" proxyServers.
     *
     * @param id the id of the proxyServers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proxyServers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProxyServers> getProxyServers(@PathVariable("id") Long id) {
        log.debug("REST request to get ProxyServers : {}", id);
        Optional<ProxyServers> proxyServers = proxyServersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(proxyServers);
    }

    /**
     * {@code DELETE  /proxy-servers/:id} : delete the "id" proxyServers.
     *
     * @param id the id of the proxyServers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProxyServers(@PathVariable("id") Long id) {
        log.debug("REST request to delete ProxyServers : {}", id);
        proxyServersRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
