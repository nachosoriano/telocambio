/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author NACHO
 */
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    @Value("${c3p0.max_size}")
    private int maxSize;

    @Value("${c3p0.min_size}")
    private int minSize;

    @Value("${c3p0.acquire_increment}")
    private int acquireIncrement;

    @Value("${c3p0.idle_test_period}")
    private int idleTestPeriod;

    @Value("${c3p0.max_statements}")
    private int maxStatements;

    @Value("${c3p0.max_idle_time}")
    private int maxIdleTime;

    @Value("${c3p0.url}")
    private String url;

    @Value("${c3p0.username}")
    private String username;

    @Value("${c3p0.password}")
    private String password;

    @Value("${c3p0.driverClassName}")
    private String driverClassName;

    /* Here we register the Hibernate4Module into an ObjectMapper, then set this custom-configured ObjectMapper
     * to the MessageConverter and return it to be added to the HttpMessageConverters of our application*/
    public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
        Hibernate4Module hm = new Hibernate4Module();
        hm.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
        mapper.registerModule(hm);

        messageConverter.setObjectMapper(mapper);
        return messageConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonMessageConverter());
        super.configureMessageConverters(converters);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*    @Override
     public void configure(WebSecurity web) throws Exception {
     web.ignoring()
     // Spring Security should completely ignore URLs starting with /resources/
     .antMatchers("/resources/**");
     }

     @Override
     protected void configure(HttpSecurity http) throws Exception {
     http.authorizeRequests().antMatchers("/WSDbModel/categoria/findActivas").permitAll().and()
     .formLogin()
     .loginProcessingUrl("/WSDbModel/usuario/login")
     .usernameParameter("email")
     .passwordParameter("clave")
     .permitAll();
     /*.and()
     .authorizeRequests()
     .antMatchers("/WSDbModel/trueque/filter").permitAll()
     .antMatchers("/WSDbModel/categoria/findActivas").permitAll()
     .antMatchers("/WSDbModel/subcategoria/findByCategoriaId").permitAll()
     .antMatchers("/WSDbModel/provincia/findAll").permitAll()
     .antMatchers("/WSDbModel/usuario/login").permitAll()
     .antMatchers("/WSDbModel/trueque/register").authenticated();
     }

     @Override
     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     auth
     // enable in memory based authentication with a user named "user" and "admin"
     .inMemoryAuthentication().withUser("telocambio").password("telocambio").roles("USER")
     .and().withUser("admin").password("password").roles("USER", "ADMIN");
     }

     @Resource
     public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
     auth.inMemoryAuthentication().withUser("telocambio").password("telocambio").roles("USER");
     }
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setMaxPoolSize(maxSize);
        dataSource.setMinPoolSize(minSize);
        dataSource.setAcquireIncrement(acquireIncrement);
        dataSource.setIdleConnectionTestPeriod(idleTestPeriod);
        dataSource.setMaxStatements(maxStatements);
        dataSource.setMaxIdleTime(maxIdleTime);
        dataSource.setJdbcUrl(url);
        dataSource.setPassword(password);
        dataSource.setUser(username);
        dataSource.setDriverClass(driverClassName);
        return dataSource;
    }
}
