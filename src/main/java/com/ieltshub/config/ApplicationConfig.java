package com.ieltshub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author tuannd
 * @date 27/06/2016
 * @since 1.0
 */
@Component
public class ApplicationConfig {
    @Value("${app.static.server.host}")
    private String staticServerHost;

    @Value("${app.paging.pageSize.default}")
    private Integer defaultPageSize;

    @Value("${app.paging.pageSize.maxPageSize}")
    private Integer maxPageSize;

    @Value("${app.datetime.format.default}")
    private String defaultDateTimeFormat;

    @Value("${app.cache.timeout.seconds.default}")
    private Integer defaultCachedTimeout;

    @Value("${app.trusted.client}")
    private String trustedClients;
    
    @Value("${app.static.image.path}")
    private String staticImagePath;
    
    @Value("${spring.redis.host}")
    private String serverHost;

    @Value("${app.email.username}")
    private String appEmail;

    @Value("${app.email.password}")
    private String appEmailPassword;

    @Value("${app.static.resource.physical.path}")
    private String staticResourcePhysicalPath;

    @Value("${app.static.internal.resource.physical.path}")
    private String internalStaticResourcePhysicalPath;

    public String getStaticServerHost() {
        return staticServerHost;
    }

    public void setStaticServerHost(String staticServerHost) {
        this.staticServerHost = staticServerHost;
    }

    public Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public String getDefaultDateTimeFormat() {
        return defaultDateTimeFormat;
    }

    public void setDefaultDateTimeFormat(String defaultDateTimeFormat) {
        this.defaultDateTimeFormat = defaultDateTimeFormat;
    }

    public Integer getDefaultCachedTimeout() {
        return defaultCachedTimeout;
    }

    public void setDefaultCachedTimeout(Integer defaultCachedTimeout) {
        this.defaultCachedTimeout = defaultCachedTimeout;
    }

    public String getTrustedClients() {
        return trustedClients;
    }

    public void setTrustedClients(String trustedClients) {
        this.trustedClients = trustedClients;
    }

	public String getStaticImagePath() {
		return staticImagePath;
	}

	public void setStaticImagePath(String staticImagePath) {
		this.staticImagePath = staticImagePath;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

    public String getAppEmail() {
        return appEmail;
    }

    public void setAppEmail(String appEmail) {
        this.appEmail = appEmail;
    }

    public String getAppEmailPassword() {
        return appEmailPassword;
    }

    public void setAppEmailPassword(String appEmailPassword) {
        this.appEmailPassword = appEmailPassword;
    }

    public Integer getMaxPageSize() {
        return maxPageSize;
    }

    public void setMaxPageSize(Integer maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public String getStaticResourcePhysicalPath() {
        return staticResourcePhysicalPath;
    }

    public void setStaticResourcePhysicalPath(String staticResourcePhysicalPath) {
        this.staticResourcePhysicalPath = staticResourcePhysicalPath;
    }

    public String getInternalStaticResourcePhysicalPath() {
        return internalStaticResourcePhysicalPath;
    }

    public void setInternalStaticResourcePhysicalPath(String internalStaticResourcePhysicalPath) {
        this.internalStaticResourcePhysicalPath = internalStaticResourcePhysicalPath;
    }
}
