package com.manning.springdata.hibernate.chapter02.config;


import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;



import javax.sql.DataSource;
import java.util.Properties;


@EnableJpaRepositories("com.manning.springdata.hibernate.chapter02.repo")
public class DBConfig {


  @Bean
  public DataSource dataSource() {

    var dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl("jdbc:postgresql://localhost/prospringdb");
    dataSource.setUsername("guest");
    dataSource.setPassword("welcome1");
    return dataSource;
  }

  // EntityManagerFactory from Jakarta for newest Spring
  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
    //
    return new JpaTransactionManager();
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    var jpaVendorAdapter = new HibernateJpaVendorAdapter();
    jpaVendorAdapter.setDatabase(Database.POSTGRESQL);
    jpaVendorAdapter.setShowSql(true);
    return jpaVendorAdapter;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    var localContainerEntityManagerFactoryBean =
            new LocalContainerEntityManagerFactoryBean();
    localContainerEntityManagerFactoryBean.setDataSource(this.dataSource());
    var properties = new Properties();
    properties.put("hibernate.hbm2ddl.auto", "create");
    localContainerEntityManagerFactoryBean.setJpaProperties(properties);
    localContainerEntityManagerFactoryBean.setJpaVendorAdapter(this.jpaVendorAdapter());
    localContainerEntityManagerFactoryBean.setPackagesToScan("com.manning.springdata.hibernate.chapter02");
    return localContainerEntityManagerFactoryBean;
  }


}
