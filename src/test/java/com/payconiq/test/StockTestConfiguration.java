package com.payconiq.test;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.payconiq.repository")
@Profile("test")
public class StockTestConfiguration {

//    @Bean
//    public RestTemplateBuilder restTemplateBuilder() {
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
//        messageConverters.add(converter);
//        return new RestTemplateBuilder().additionalMessageConverters(messageConverters);
//    }

    @Bean
    MappingJackson2HttpMessageConverter converter() {

        MappingJackson2HttpMessageConverter converter
                = new MappingJackson2HttpMessageConverter();
        return converter;
    }

    @Bean
    ServletWebServerFactory servletWebServerFactory(){
        return new TomcatServletWebServerFactory();
    }


    @Bean
    public DataSource dataSource()
    {
        EmbeddedDatabaseBuilder embeddedDatabaseBuilder=new EmbeddedDatabaseBuilder();
        return embeddedDatabaseBuilder.addScript("classpath:test-db-script/stock.sql").setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean()
    {
        LocalContainerEntityManagerFactoryBean em=new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.payconiq.model");
        em.setPersistenceUnitName("entityManagerFactory");

        JpaVendorAdapter vendorAdapter=new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf)
    {
        JpaTransactionManager transactionManager=new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}
