package com.impulse.geolocation.domain;

import static com.impulse.geolocation.domain.ProxyServersTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.impulse.geolocation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProxyServersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProxyServers.class);
        ProxyServers proxyServers1 = getProxyServersSample1();
        ProxyServers proxyServers2 = new ProxyServers();
        assertThat(proxyServers1).isNotEqualTo(proxyServers2);

        proxyServers2.setId(proxyServers1.getId());
        assertThat(proxyServers1).isEqualTo(proxyServers2);

        proxyServers2 = getProxyServersSample2();
        assertThat(proxyServers1).isNotEqualTo(proxyServers2);
    }
}
