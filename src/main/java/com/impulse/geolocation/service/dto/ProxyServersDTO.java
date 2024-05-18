package com.impulse.geolocation.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.impulse.geolocation.domain.ProxyServers} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProxyServersDTO implements Serializable {

    private Long id;

    private String ipAddress;

    private Integer port;

    private String country;

    private String protocol;

    private String anonymity;

    private String organization;

    private Integer speed;

    private Integer responseTime;

    private Integer successCount;

    private Integer failCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(String anonymity) {
        this.anonymity = anonymity;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Integer responseTime) {
        this.responseTime = responseTime;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProxyServersDTO)) {
            return false;
        }

        ProxyServersDTO proxyServersDTO = (ProxyServersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, proxyServersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProxyServersDTO{" +
            "id=" + getId() +
            ", ipAddress='" + getIpAddress() + "'" +
            ", port=" + getPort() +
            ", country='" + getCountry() + "'" +
            ", protocol='" + getProtocol() + "'" +
            ", anonymity='" + getAnonymity() + "'" +
            ", organization='" + getOrganization() + "'" +
            ", speed=" + getSpeed() +
            ", responseTime=" + getResponseTime() +
            ", successCount=" + getSuccessCount() +
            ", failCount=" + getFailCount() +
            "}";
    }
}
