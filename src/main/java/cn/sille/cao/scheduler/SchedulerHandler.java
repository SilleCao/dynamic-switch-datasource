package cn.sille.cao.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author Sille_Cao
 * @date 12/13/2021 10:34 PM
 * @description ...
 */
@Component
public class SchedulerHandler {

    @Autowired
    private DataSource dataSource;

    public void initQuartz() throws Exception {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setQuartzProperties(propertiesFactoryBean.getObject());
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();
        factory.start();
    }
}
