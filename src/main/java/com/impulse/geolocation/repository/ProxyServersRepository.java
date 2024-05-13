package com.impulse.geolocation.repository;

import com.impulse.geolocation.domain.ProxyServers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProxyServers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProxyServersRepository extends JpaRepository<ProxyServers, Long> {}
