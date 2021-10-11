/*
package com.meal.mechant;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class SecondJpaConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        System.setProperty("h2.bindAddress", "");
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    @Bean("cacheDatasource")
    public DataSource cacheDatasource(){
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:testdb;INIT=create schema if not exists engine;")
                .driverClassName("org.h2.Driver")
                .username("root")
                .password("root")
                .build();
    }

    @Bean("cacheTemplate")
    NamedParameterJdbcTemplate template(@Qualifier("cacheDatasource") DataSource cacheDatasource){
        return new NamedParameterJdbcTemplate(cacheDatasource);
    }

}
*/
