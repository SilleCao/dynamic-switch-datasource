package cn.sille.cao.data.source;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author Sille_Cao
 * @date 11/29/2021 10:46 PM
 * @description ...
 */
@Configuration
public class DataSourceConfig {
    @Bean("H2_DATA_SOURCE")
    public DataSource h2DataSource(){
//        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("org.h2.Driver");
//        dataSourceBuilder.url("jdbc:h2:mem:test-db");
//        dataSourceBuilder.username("SilleCao");
//        dataSourceBuilder.password("");

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.h2.Driver");
        hikariConfig.setJdbcUrl("jdbc:h2:mem:test-db");
        hikariConfig.setUsername("SilleCao");
        hikariConfig.setPassword("");
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        return hikariDataSource;
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource h2DataSource){
        DynamicDataSource.getTargetDataSourceMap().put(DataSourceName.H2_DATA_SOURCE, h2DataSource);
        DynamicDataSource dynamicDataSource = new DynamicDataSource(h2DataSource, DynamicDataSource.getTargetDataSourceMap());
        return dynamicDataSource;
    }
}
