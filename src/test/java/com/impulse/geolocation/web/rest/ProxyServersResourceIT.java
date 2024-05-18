package com.impulse.geolocation.web.rest;

import static com.impulse.geolocation.domain.ProxyServersAsserts.*;
import static com.impulse.geolocation.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.impulse.geolocation.IntegrationTest;
import com.impulse.geolocation.domain.ProxyServers;
import com.impulse.geolocation.repository.ProxyServersRepository;
import com.impulse.geolocation.service.dto.ProxyServersDTO;
import com.impulse.geolocation.service.mapper.ProxyServersMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProxyServersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProxyServersResourceIT {

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PROTOCOL = "AAAAAAAAAA";
    private static final String UPDATED_PROTOCOL = "BBBBBBBBBB";

    private static final String DEFAULT_ANONYMITY = "AAAAAAAAAA";
    private static final String UPDATED_ANONYMITY = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SPEED = 1;
    private static final Integer UPDATED_SPEED = 2;

    private static final Integer DEFAULT_RESPONSE_TIME = 1;
    private static final Integer UPDATED_RESPONSE_TIME = 2;

    private static final Integer DEFAULT_SUCCESS_COUNT = 1;
    private static final Integer UPDATED_SUCCESS_COUNT = 2;

    private static final Integer DEFAULT_FAIL_COUNT = 1;
    private static final Integer UPDATED_FAIL_COUNT = 2;

    private static final String ENTITY_API_URL = "/api/proxy-servers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProxyServersRepository proxyServersRepository;

    @Autowired
    private ProxyServersMapper proxyServersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProxyServersMockMvc;

    private ProxyServers proxyServers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProxyServers createEntity(EntityManager em) {
        ProxyServers proxyServers = new ProxyServers()
            .ipAddress(DEFAULT_IP_ADDRESS)
            .port(DEFAULT_PORT)
            .country(DEFAULT_COUNTRY)
            .protocol(DEFAULT_PROTOCOL)
            .anonymity(DEFAULT_ANONYMITY)
            .organization(DEFAULT_ORGANIZATION)
            .speed(DEFAULT_SPEED)
            .responseTime(DEFAULT_RESPONSE_TIME)
            .successCount(DEFAULT_SUCCESS_COUNT)
            .failCount(DEFAULT_FAIL_COUNT);
        return proxyServers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProxyServers createUpdatedEntity(EntityManager em) {
        ProxyServers proxyServers = new ProxyServers()
            .ipAddress(UPDATED_IP_ADDRESS)
            .port(UPDATED_PORT)
            .country(UPDATED_COUNTRY)
            .protocol(UPDATED_PROTOCOL)
            .anonymity(UPDATED_ANONYMITY)
            .organization(UPDATED_ORGANIZATION)
            .speed(UPDATED_SPEED)
            .responseTime(UPDATED_RESPONSE_TIME)
            .successCount(UPDATED_SUCCESS_COUNT)
            .failCount(UPDATED_FAIL_COUNT);
        return proxyServers;
    }

    @BeforeEach
    public void initTest() {
        proxyServers = createEntity(em);
    }

    @Test
    @Transactional
    void createProxyServers() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ProxyServers
        ProxyServersDTO proxyServersDTO = proxyServersMapper.toDto(proxyServers);
        var returnedProxyServersDTO = om.readValue(
            restProxyServersMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proxyServersDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProxyServersDTO.class
        );

        // Validate the ProxyServers in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProxyServers = proxyServersMapper.toEntity(returnedProxyServersDTO);
        assertProxyServersUpdatableFieldsEquals(returnedProxyServers, getPersistedProxyServers(returnedProxyServers));
    }

    @Test
    @Transactional
    void createProxyServersWithExistingId() throws Exception {
        // Create the ProxyServers with an existing ID
        proxyServers.setId(1L);
        ProxyServersDTO proxyServersDTO = proxyServersMapper.toDto(proxyServers);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProxyServersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proxyServersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProxyServers in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProxyServers() throws Exception {
        // Initialize the database
        proxyServersRepository.saveAndFlush(proxyServers);

        // Get all the proxyServersList
        restProxyServersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proxyServers.getId().intValue())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].protocol").value(hasItem(DEFAULT_PROTOCOL)))
            .andExpect(jsonPath("$.[*].anonymity").value(hasItem(DEFAULT_ANONYMITY)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED)))
            .andExpect(jsonPath("$.[*].responseTime").value(hasItem(DEFAULT_RESPONSE_TIME)))
            .andExpect(jsonPath("$.[*].successCount").value(hasItem(DEFAULT_SUCCESS_COUNT)))
            .andExpect(jsonPath("$.[*].failCount").value(hasItem(DEFAULT_FAIL_COUNT)));
    }

    @Test
    @Transactional
    void getProxyServers() throws Exception {
        // Initialize the database
        proxyServersRepository.saveAndFlush(proxyServers);

        // Get the proxyServers
        restProxyServersMockMvc
            .perform(get(ENTITY_API_URL_ID, proxyServers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proxyServers.getId().intValue()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.protocol").value(DEFAULT_PROTOCOL))
            .andExpect(jsonPath("$.anonymity").value(DEFAULT_ANONYMITY))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED))
            .andExpect(jsonPath("$.responseTime").value(DEFAULT_RESPONSE_TIME))
            .andExpect(jsonPath("$.successCount").value(DEFAULT_SUCCESS_COUNT))
            .andExpect(jsonPath("$.failCount").value(DEFAULT_FAIL_COUNT));
    }

    @Test
    @Transactional
    void getNonExistingProxyServers() throws Exception {
        // Get the proxyServers
        restProxyServersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProxyServers() throws Exception {
        // Initialize the database
        proxyServersRepository.saveAndFlush(proxyServers);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the proxyServers
        ProxyServers updatedProxyServers = proxyServersRepository.findById(proxyServers.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProxyServers are not directly saved in db
        em.detach(updatedProxyServers);
        updatedProxyServers
            .ipAddress(UPDATED_IP_ADDRESS)
            .port(UPDATED_PORT)
            .country(UPDATED_COUNTRY)
            .protocol(UPDATED_PROTOCOL)
            .anonymity(UPDATED_ANONYMITY)
            .organization(UPDATED_ORGANIZATION)
            .speed(UPDATED_SPEED)
            .responseTime(UPDATED_RESPONSE_TIME)
            .successCount(UPDATED_SUCCESS_COUNT)
            .failCount(UPDATED_FAIL_COUNT);
        ProxyServersDTO proxyServersDTO = proxyServersMapper.toDto(updatedProxyServers);

        restProxyServersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proxyServersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(proxyServersDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProxyServers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProxyServersToMatchAllProperties(updatedProxyServers);
    }

    @Test
    @Transactional
    void putNonExistingProxyServers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proxyServers.setId(longCount.incrementAndGet());

        // Create the ProxyServers
        ProxyServersDTO proxyServersDTO = proxyServersMapper.toDto(proxyServers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProxyServersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proxyServersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(proxyServersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProxyServers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProxyServers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proxyServers.setId(longCount.incrementAndGet());

        // Create the ProxyServers
        ProxyServersDTO proxyServersDTO = proxyServersMapper.toDto(proxyServers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProxyServersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(proxyServersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProxyServers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProxyServers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proxyServers.setId(longCount.incrementAndGet());

        // Create the ProxyServers
        ProxyServersDTO proxyServersDTO = proxyServersMapper.toDto(proxyServers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProxyServersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proxyServersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProxyServers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProxyServersWithPatch() throws Exception {
        // Initialize the database
        proxyServersRepository.saveAndFlush(proxyServers);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the proxyServers using partial update
        ProxyServers partialUpdatedProxyServers = new ProxyServers();
        partialUpdatedProxyServers.setId(proxyServers.getId());

        partialUpdatedProxyServers
            .ipAddress(UPDATED_IP_ADDRESS)
            .port(UPDATED_PORT)
            .speed(UPDATED_SPEED)
            .successCount(UPDATED_SUCCESS_COUNT);

        restProxyServersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProxyServers.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProxyServers))
            )
            .andExpect(status().isOk());

        // Validate the ProxyServers in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProxyServersUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProxyServers, proxyServers),
            getPersistedProxyServers(proxyServers)
        );
    }

    @Test
    @Transactional
    void fullUpdateProxyServersWithPatch() throws Exception {
        // Initialize the database
        proxyServersRepository.saveAndFlush(proxyServers);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the proxyServers using partial update
        ProxyServers partialUpdatedProxyServers = new ProxyServers();
        partialUpdatedProxyServers.setId(proxyServers.getId());

        partialUpdatedProxyServers
            .ipAddress(UPDATED_IP_ADDRESS)
            .port(UPDATED_PORT)
            .country(UPDATED_COUNTRY)
            .protocol(UPDATED_PROTOCOL)
            .anonymity(UPDATED_ANONYMITY)
            .organization(UPDATED_ORGANIZATION)
            .speed(UPDATED_SPEED)
            .responseTime(UPDATED_RESPONSE_TIME)
            .successCount(UPDATED_SUCCESS_COUNT)
            .failCount(UPDATED_FAIL_COUNT);

        restProxyServersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProxyServers.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProxyServers))
            )
            .andExpect(status().isOk());

        // Validate the ProxyServers in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProxyServersUpdatableFieldsEquals(partialUpdatedProxyServers, getPersistedProxyServers(partialUpdatedProxyServers));
    }

    @Test
    @Transactional
    void patchNonExistingProxyServers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proxyServers.setId(longCount.incrementAndGet());

        // Create the ProxyServers
        ProxyServersDTO proxyServersDTO = proxyServersMapper.toDto(proxyServers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProxyServersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, proxyServersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(proxyServersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProxyServers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProxyServers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proxyServers.setId(longCount.incrementAndGet());

        // Create the ProxyServers
        ProxyServersDTO proxyServersDTO = proxyServersMapper.toDto(proxyServers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProxyServersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(proxyServersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProxyServers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProxyServers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proxyServers.setId(longCount.incrementAndGet());

        // Create the ProxyServers
        ProxyServersDTO proxyServersDTO = proxyServersMapper.toDto(proxyServers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProxyServersMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(proxyServersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProxyServers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProxyServers() throws Exception {
        // Initialize the database
        proxyServersRepository.saveAndFlush(proxyServers);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the proxyServers
        restProxyServersMockMvc
            .perform(delete(ENTITY_API_URL_ID, proxyServers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return proxyServersRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected ProxyServers getPersistedProxyServers(ProxyServers proxyServers) {
        return proxyServersRepository.findById(proxyServers.getId()).orElseThrow();
    }

    protected void assertPersistedProxyServersToMatchAllProperties(ProxyServers expectedProxyServers) {
        assertProxyServersAllPropertiesEquals(expectedProxyServers, getPersistedProxyServers(expectedProxyServers));
    }

    protected void assertPersistedProxyServersToMatchUpdatableProperties(ProxyServers expectedProxyServers) {
        assertProxyServersAllUpdatablePropertiesEquals(expectedProxyServers, getPersistedProxyServers(expectedProxyServers));
    }
}
