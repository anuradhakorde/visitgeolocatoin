package com.impulse.geolocation.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.impulse.geolocation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProxyServersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProxyServersDTO.class);
        ProxyServersDTO proxyServersDTO1 = new ProxyServersDTO();
        proxyServersDTO1.setId(1L);
        ProxyServersDTO proxyServersDTO2 = new ProxyServersDTO();
        assertThat(proxyServersDTO1).isNotEqualTo(proxyServersDTO2);
        proxyServersDTO2.setId(proxyServersDTO1.getId());
        assertThat(proxyServersDTO1).isEqualTo(proxyServersDTO2);
        proxyServersDTO2.setId(2L);
        assertThat(proxyServersDTO1).isNotEqualTo(proxyServersDTO2);
        proxyServersDTO1.setId(null);
        assertThat(proxyServersDTO1).isNotEqualTo(proxyServersDTO2);
    }
}
