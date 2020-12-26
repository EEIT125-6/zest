package config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class RootAppConfig {
	public static final String dbAccount = "scott";
	public static final String dbPassword = "tiger";
	public static final String dbDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String sqlType = "sqlserver";
	public static final String sqlUrl = "127.0.0.1";
	public static final String sqlPort = "1433";
	public static final String dbName = "WebProject";
	
	@Bean
	public DataSource msSQLDataSource() {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setUser(dbAccount);
		ds.setPassword(dbPassword);
		try {
			ds.setDriverClass(dbDriver);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		ds.setJdbcUrl("jdbc:"+sqlType+"://"+sqlUrl+":"+sqlPort+";DatabaseName="+dbName);
		ds.setInitialPoolSize(4);
		ds.setMaxPoolSize(8);
		return ds;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
		/* 掃描的package暫時留空，有需要時請填入 */
		factory.setPackagesToScan(new String[] { "xun", "webUser" });
		factory.setDataSource(msSQLDataSource());
		factory.setHibernateProperties(additionalPropertiesMsSQL());
		return factory;
	}

	@Bean(name = "transactionManager")
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}
	
	private Properties additionalPropertiesMsSQL() {
		Properties properties=new Properties();
		properties.put("hibernate.dialect", org.hibernate.dialect.SQLServer2012Dialect.class);
		properties.put("hibernate.show_sql", Boolean.TRUE);
		properties.put("hibernate.format_sql", Boolean.TRUE);
		properties.put("default_batch_fetch_size", 10);
		properties.put("hibernate.hbm2ddl.auto", "update");
//		properties.put("hibernate.transaction.coordinator_class","jdbc"); // By Mimicker0903-12-13 原本的例子 有特別填寫個 所以補上
//		properties.put("hibernate.bytecode.use_reflection_optimizer","false"); // By Mimicker0903-12-13 映射優化 取消
		return properties;
	}
}
