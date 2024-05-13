package com.impulse.geolocation.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProxyServersTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ProxyServers getProxyServersSample1() {
        return new ProxyServers()
            .id(1L)
            .ipAddress("ipAddress1")
            .port(1)
            .country("country1")
            .protocol("protocol1")
            .anonymity("anonymity1")
            .organization("organization1")
            .speed(1)
            .responseTime(1)
            .successCount(1)
            .failCount(1);
    }

    public static ProxyServers getProxyServersSample2() {
        return new ProxyServers()
            .id(2L)
            .ipAddress("ipAddress2")
            .port(2)
            .country("country2")
            .protocol("protocol2")
            .anonymity("anonymity2")
            .organization("organization2")
            .speed(2)
            .responseTime(2)
            .successCount(2)
            .failCount(2);
    }

    public static ProxyServers getProxyServersRandomSampleGenerator() {
        return new ProxyServers()
            .id(longCount.incrementAndGet())
            .ipAddress(UUID.randomUUID().toString())
            .port(intCount.incrementAndGet())
            .country(UUID.randomUUID().toString())
            .protocol(UUID.randomUUID().toString())
            .anonymity(UUID.randomUUID().toString())
            .organization(UUID.randomUUID().toString())
            .speed(intCount.incrementAndGet())
            .responseTime(intCount.incrementAndGet())
            .successCount(intCount.incrementAndGet())
            .failCount(intCount.incrementAndGet());
    }
}
