package cn.sille.cao.data.source;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sille_Cao
 * @date 11/29/2021 10:50 PM
 * @description ...
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<DataSourceName> DATA_SOURCE_NAME = new ThreadLocal<DataSourceName>();
    private static final Map<String, DataSourceName> PACKAGE_DATA_SOURCE = new HashMap<>();

    private static final Map<Object, Object> TARGET_DATA_SOURCE_MAP = new HashMap<>(2);

    public DynamicDataSource(DataSource defaultDataSource, Map<Object, Object> targetDatasource) {
        setDefaultTargetDataSource(defaultDataSource);
        setTargetDataSources(targetDatasource);
        afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceName dataSourceName = DATA_SOURCE_NAME.get();
        DATA_SOURCE_NAME.remove();
        return dataSourceName;
    }

    public static void setDataSourceName(DataSourceName dataSourceName){
        DATA_SOURCE_NAME.set(dataSourceName);
    }

    public static void usePackageDataSourceKey(String packageName) {
        DATA_SOURCE_NAME.set(PACKAGE_DATA_SOURCE.get(packageName));
    }

    public Map<String, DataSourceName> getPackageDataSource() {
        return PACKAGE_DATA_SOURCE;
    }

    public void setPackageDataSource(Map<String, DataSourceName> packageDataSource) {
        PACKAGE_DATA_SOURCE.putAll(packageDataSource);
    }

    public static Map<Object, Object> getTargetDataSourceMap() {
        return TARGET_DATA_SOURCE_MAP;
    }
}
