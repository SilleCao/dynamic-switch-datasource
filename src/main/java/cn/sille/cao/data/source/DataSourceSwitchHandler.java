package cn.sille.cao.data.source;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Sille_Cao
 * @date 11/29/2021 11:07 PM
 * @description ...
 */
@Slf4j
@Component
public class DataSourceSwitchHandler {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DynamicDataSource dataSource;
    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    private static final String MYSQL_URL = "jdbc:mysql://127.0.0.1:3306/sille-test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String MYSQL_DIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "****";

    public void switchDataSource(){
        log.info("Starting to switch Data Source...");
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(HikariDataSource.class);
        beanDefinitionBuilder.addPropertyValue("driverClassName", MYSQL_DIVER_CLASS_NAME);
        beanDefinitionBuilder.addPropertyValue("jdbcUrl", MYSQL_URL);
        beanDefinitionBuilder.addPropertyValue("username", MYSQL_USERNAME);
        beanDefinitionBuilder.addPropertyValue("password", MYSQL_PASSWORD);
        registry.registerBeanDefinition(DataSourceName.MYSQL_DATASOURCE.toString(), beanDefinitionBuilder.getBeanDefinition());

        DataSource mySQLDataSource = applicationContext.getBean(DataSourceName.MYSQL_DATASOURCE.toString(), DataSource.class);
        DynamicDataSource.getTargetDataSourceMap().put(DataSourceName.MYSQL_DATASOURCE, mySQLDataSource);
        dataSource.setDefaultTargetDataSource(mySQLDataSource);
        DynamicDataSource.getTargetDataSourceMap().remove(DataSourceName.H2_DATA_SOURCE);
        dataSource.afterPropertiesSet();
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.hbm2ddl.auto", "none");
        entityManagerFactory.setJpaProperties(properties);
        entityManagerFactory.afterPropertiesSet();
    }
}
