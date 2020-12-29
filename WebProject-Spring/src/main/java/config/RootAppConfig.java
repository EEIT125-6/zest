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
	@Bean
	public DataSource msSQLDataSource() {
		ComboPooledDataSource ds = new ComboPooledDataSource();
//		ds.setUser("scott");
//		ds.setPassword("tiger");
		
		ds.setUser("sa");
		ds.setPassword("sa123456");
		try {
			ds.setDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
//		ds.setJdbcUrl("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=WebProject");
//		ds.setJdbcUrl("jdbc:sqlserver://10.31.25.130:1433;DatabaseName=WebProject");
		ds.setJdbcUrl("jdbc:sqlserver://localhost;DatabaseName=DemoLab");
		ds.setInitialPoolSize(4);
		ds.setMaxPoolSize(8);
		ds.setMaxIdleTime(3500); //12-13
		return ds;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
		/* 掃描的package暫時留空，有需要時請填入 */
		factory.setPackagesToScan(new String[] { 
				"xun",
				"board"
				});
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
		properties.put("hibernate.transaction.coordinator_class","jdbc"); //12-13 原本的例子 有特別填寫個 所以補上  這個內容似乎只有jdbc 和 JBossAS  
		properties.put("hibernate.bytecode.use_reflection_optimizer","false"); //12-13 映射優化 取消
		return properties;
	}
}
