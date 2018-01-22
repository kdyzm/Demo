package com.server2.service.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@ComponentScan(basePackages = { "com.server2.service", })
@MapperScan("com.server2.service.mapper")
public class Config {

	private static final Logger logger = LoggerFactory.getLogger(Config.class);

	@Value("${database.mysql.driverClassName}")
	private String driverClassName;
	@Value("${database.mysql.userName}")
	private String userName;
	@Value("${database.mysql.password}")
	private String password;
	@Value("${database.mysql.url}")
	private String url;

	@Bean
	public DataSource dataSource() {

		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName(driverClassName);
		ds.setUrl(url);
		ds.setUsername(userName);
		ds.setPassword(password);
		ds.setTestWhileIdle(false);

		try {
			ds.getConnection();
		} catch (Exception e) {
			logger.error("", e);
		}
		List<Filter> filters = new ArrayList<>();

		Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
		slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
		filters.add(slf4jLogFilter);

		StatFilter statfilter = new StatFilter();
		statfilter.setMergeSql(true);
		statfilter.setLogSlowSql(true);
		statfilter.setSlowSqlMillis(1000L);
		filters.add(statfilter);

		ds.setProxyFilters(filters);

		return ds;
	}
}
