package cn.sille.cao;

import cn.sille.cao.data.source.DataSourceSwitchHandler;
import cn.sille.cao.scheduler.SchedulerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DynamicSwitchDatasourceApplication implements CommandLineRunner {

	@Autowired
	private DataSourceSwitchHandler dataSourceSwitchHandler;
	@Autowired
	private SchedulerHandler schedulerHandler;

	public static void main(String[] args) {
		SpringApplication.run(DynamicSwitchDatasourceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dataSourceSwitchHandler.switchDataSource();
		schedulerHandler.initQuartz();
	}
}
