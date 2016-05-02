package com.tanl.kitserver.conf;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过xml文件（persistence.xml），配置ManagerFactory
 * <p>
 * Created by Administrator on 2016/4/27.
 */
//@Configuration
public class JPAConfiguration {

	/**
	 * basic manager factory, the function is smaller than others.It has been defined in the persistence.xml.
	 *
	 * @return the factory
	 */
	//@Bean
	public LocalEntityManagerFactoryBean entityManagerFactory () {

		LocalEntityManagerFactoryBean entityManagerBean = new LocalEntityManagerFactoryBean();
		entityManagerBean.setPersistenceUnitName("test-jpa");
		return entityManagerBean;
	}

	/**
	 * basic manager factory, It has been defined in the persistence.xml,and well be found by jndi strategy.
	 *
	 * @return the factory
	 */
//	@Bean(name = "jndiEntityManagerFactory")
	public JndiObjectFactoryBean jndiEntityManagerFactory () {

		JndiObjectFactoryBean ben = new JndiObjectFactoryBean();
		ben.setJndiName("test-jpa");
		return ben;
	}
//	@Bean
//	@Autowired
//	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
//
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(entityManagerFactory);
//		return transactionManager;
//	}

	/**
	 * It's powerful and flexible entity manager factory.
	 *
	 * @return a instance of {@link LocalContainerEntityManagerFactoryBean}.
	 */
//	@Bean
//	@Autowired
	public LocalContainerEntityManagerFactoryBean containerEntityManagerFactory () {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//		factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan("com.tanl.kitserver");
		factoryBean.setJpaPropertyMap(jpaProperties());
		return factoryBean;
	}

	/**
	 * config a connect of mysql by jdbc
	 * @return dataSource
	 */
//	@Bean
	public DataSource dataSource () {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/KitServer");
		dataSource.setUsername("root");
		dataSource.setPassword("tanlin");

		return dataSource;
	}

	/**
	 * set properties for connection.
	 *
	 * @return properties map.
	 */
	private Map<String, ?> jpaProperties () {

		Map<String, String> jpaPropertiesMap = new HashMap<String, String>();
		jpaPropertiesMap.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		jpaPropertiesMap.put("hibernate.hbm2ddl.auto", "update");
		return jpaPropertiesMap;
	}
}
