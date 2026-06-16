package com.fitness.config;

import com.fitness.interceptor.AuditInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan("com.fitness")
public class WebConfig implements WebMvcConfigurer {

    private final AuditInterceptor auditInterceptor;

    public WebConfig(AuditInterceptor auditInterceptor) {
        this.auditInterceptor = auditInterceptor;
    }

    @Bean
    public MessageSource messageSource() {

        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();

        source.setBasename("classpath:messages");
        source.setDefaultEncoding("UTF-8");

        return source;
    }

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {

        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();

        sessionLocaleResolver.setDefaultLocale(Locale.forLanguageTag("ru"));
        return sessionLocaleResolver;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {

        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();

        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(auditInterceptor);
    }
}
