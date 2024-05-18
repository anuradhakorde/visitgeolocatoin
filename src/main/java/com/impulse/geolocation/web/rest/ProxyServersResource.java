package com.impulse.geolocation.web.rest;

import com.impulse.geolocation.repository.ProxyServersRepository;
import com.impulse.geolocation.service.ProxyServersService;
import com.impulse.geolocation.service.dto.ProxyServersDTO;
import com.impulse.geolocation.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.impulse.geolocation.domain.ProxyServers}.
 */
@RestController
@RequestMapping("/api/proxy-servers")
public class ProxyServersResource {

    private final Logger log = LoggerFactory.getLogger(ProxyServersResource.class);

    private static final String ENTITY_NAME = "proxyServers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProxyServersService proxyServersService;

    private final ProxyServersRepository proxyServersRepository;

    public ProxyServersResource(ProxyServersService proxyServersService, ProxyServersRepository proxyServersRepository) {
        this.proxyServersService = proxyServersService;
        this.proxyServersRepository = proxyServersRepository;
    }

    /**
     * {@code POST  /proxy-servers} : Create a new proxyServers.
     *
     * @param proxyServersDTO the proxyServersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proxyServersDTO, or with status {@code 400 (Bad Request)} if the proxyServers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProxyServersDTO> createProxyServers(@RequestBody ProxyServersDTO proxyServersDTO) throws URISyntaxException {
        log.debug("REST request to save ProxyServers : {}", proxyServersDTO);
        if (proxyServersDTO.getId() != null) {
            throw new BadRequestAlertException("A new proxyServers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        proxyServersDTO = proxyServersService.save(proxyServersDTO);
        return ResponseEntity.created(new URI("/api/proxy-servers/" + proxyServersDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, proxyServersDTO.getId().toString()))
            .body(proxyServersDTO);
    }

    /**
     * {@code PUT  /proxy-servers/:id} : Updates an existing proxyServers.
     *
     * @param id the id of the proxyServersDTO to save.
     * @param proxyServersDTO the proxyServersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proxyServersDTO,
     * or with status {@code 400 (Bad Request)} if the proxyServersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proxyServersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProxyServersDTO> updateProxyServers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProxyServersDTO proxyServersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProxyServers : {}, {}", id, proxyServersDTO);
        if (proxyServersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proxyServersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proxyServersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        proxyServersDTO = proxyServersService.update(proxyServersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proxyServersDTO.getId().toString()))
            .body(proxyServersDTO);
    }

    /**
     * {@code PATCH  /proxy-servers/:id} : Partial updates given fields of an existing proxyServers, field will ignore if it is null
     *
     * @param id the id of the proxyServersDTO to save.
     * @param proxyServersDTO the proxyServersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proxyServersDTO,
     * or with status {@code 400 (Bad Request)} if the proxyServersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the proxyServersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the proxyServersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProxyServersDTO> partialUpdateProxyServers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProxyServersDTO proxyServersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProxyServers partially : {}, {}", id, proxyServersDTO);
        if (proxyServersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proxyServersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proxyServersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProxyServersDTO> result = proxyServersService.partialUpdate(proxyServersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proxyServersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /proxy-servers} : get all the proxyServers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proxyServers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProxyServersDTO>> getAllProxyServers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProxyServers");
        Page<ProxyServersDTO> page = proxyServersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /proxy-servers/:id} : get the "id" proxyServers.
     *
     * @param id the id of the proxyServersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proxyServersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProxyServersDTO> getProxyServers(@PathVariable("id") Long id) {
        log.debug("REST request to get ProxyServers : {}", id);
        Optional<ProxyServersDTO> proxyServersDTO = proxyServersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proxyServersDTO);
    }

    /**
     * {@code DELETE  /proxy-servers/:id} : delete the "id" proxyServers.
     *
     * @param id the id of the proxyServersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProxyServers(@PathVariable("id") Long id) {
        log.debug("REST request to delete ProxyServers : {}", id);
        proxyServersService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
