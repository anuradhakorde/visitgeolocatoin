package com.impulse.geolocation.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProxyServers.
 */
@Entity
@Table(name = "proxy_servers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProxyServers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "port")
    private Integer port;

    @Column(name = "country")
    private String country;

    @Column(name = "protocol")
    private String protocol;

    @Column(name = "anonymity")
    private String anonymity;

    @Column(name = "organization")
    private String organization;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "response_time")
    private Integer responseTime;

    @Column(name = "success_count")
    private Integer successCount;

    @Column(name = "fail_count")
    private Integer failCount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProxyServers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public ProxyServers ipAddress(String ipAddress) {
        this.setIpAddress(ipAddress);
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPort() {
        return this.port;
    }

    public ProxyServers port(Integer port) {
        this.setPort(port);
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getCountry() {
        return this.country;
    }

    public ProxyServers country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public ProxyServers protocol(String protocol) {
        this.setProtocol(protocol);
        return this;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAnonymity() {
        return this.anonymity;
    }

    public ProxyServers anonymity(String anonymity) {
        this.setAnonymity(anonymity);
        return this;
    }

    public void setAnonymity(String anonymity) {
        this.anonymity = anonymity;
    }

    public String getOrganization() {
        return this.organization;
    }

    public ProxyServers organization(String organization) {
        this.setOrganization(organization);
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Integer getSpeed() {
        return this.speed;
    }

    public ProxyServers speed(Integer speed) {
        this.setSpeed(speed);
        return this;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getResponseTime() {
        return this.responseTime;
    }

    public ProxyServers responseTime(Integer responseTime) {
        this.setResponseTime(responseTime);
        return this;
    }

    public void setResponseTime(Integer responseTime) {
        this.responseTime = responseTime;
    }

    public Integer getSuccessCount() {
        return this.successCount;
    }

    public ProxyServers successCount(Integer successCount) {
        this.setSuccessCount(successCount);
        return this;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailCount() {
        return this.failCount;
    }

    public ProxyServers failCount(Integer failCount) {
        this.setFailCount(failCount);
        return this;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProxyServers)) {
            return false;
        }
        return getId() != null && getId().equals(((ProxyServers) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProxyServers{" +
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
