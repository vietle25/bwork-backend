package com.ieltshub.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author tuannd
 * @date 09/03/2016
 * @since 1.0
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = { "com.ieltshub.entity" })
@EnableJpaRepositories(basePackages = { "com.joco.repository" })
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.ieltshub.repository", "com.ieltshub.component" })
public class RepositoryConfig {
}
