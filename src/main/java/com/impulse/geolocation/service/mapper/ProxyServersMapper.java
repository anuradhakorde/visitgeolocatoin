package com.impulse.geolocation.service.mapper;

import com.impulse.geolocation.domain.ProxyServers;
import com.impulse.geolocation.service.dto.ProxyServersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProxyServers} and its DTO {@link ProxyServersDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProxyServersMapper extends EntityMapper<ProxyServersDTO, ProxyServers> {}
