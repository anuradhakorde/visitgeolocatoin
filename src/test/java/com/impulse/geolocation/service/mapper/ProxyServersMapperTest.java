package com.impulse.geolocation.service.mapper;

import static com.impulse.geolocation.domain.ProxyServersAsserts.*;
import static com.impulse.geolocation.domain.ProxyServersTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProxyServersMapperTest {

    private ProxyServersMapper proxyServersMapper;

    @BeforeEach
    void setUp() {
        proxyServersMapper = new ProxyServersMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProxyServersSample1();
        var actual = proxyServersMapper.toEntity(proxyServersMapper.toDto(expected));
        assertProxyServersAllPropertiesEquals(expected, actual);
    }
}
