package com.evowork.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author tuannd
 * @date 09/03/2016
 * @since 1.0
 */


@Configuration
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EntityScan(basePackages = { "com.evowork.entity" })
@EnableJpaRepositories(basePackages = {"com.evowork"})
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.evowork.repository"})

public class RepositoryConfig {
}
