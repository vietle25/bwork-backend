package com.ieltshub;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ieltshub.filter.ApiValidateInterceptor;
import com.ieltshub.filter.CustomRequestHeaderInterceptor;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages={"com.ieltshub", "com.ieltshub.enumeration", "com.ieltshub.repository", "com.ieltshub.job"})
@EnableJpaRepositories("com.ieltshub.repository")
@EnableScheduling
public class BackendApplication implements WebMvcConfigurer {
//
	@Autowired
    ApiValidateInterceptor apiValidateInterceptor;

    @Autowired
    CustomRequestHeaderInterceptor customRequestHeaderInterceptor;

	public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
//        for (String name : applicationContext.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
	}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiValidateInterceptor)
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/signup")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/user/log-me-in")
                .excludePathPatterns("/user/log-me-in/facebook")
                .excludePathPatterns("/user/log-me-in/google")
                .excludePathPatterns("/user/forget-password")
                .excludePathPatterns("/user/token/timeout")
                .excludePathPatterns("/call/*")
                .excludePathPatterns("/user/m/config")
                .excludePathPatterns("/common/version");
        registry.addInterceptor(customRequestHeaderInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    /*@Bean
    public FilterRegistrationBean loggingFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoggingFilter());
        return registration;
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    class CustomPasswordEncoder implements PasswordEncoder {
        private Argon2 argon2;

        public CustomPasswordEncoder() {
            this.argon2 = Argon2Factory.create();
        }

        @Override
        public String encode(CharSequence rawPassword) {
            return argon2.hash(2, 1024, 2, rawPassword.toString());
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return argon2.verify(encodedPassword, rawPassword.toString());
        }
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);

        template.setValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
