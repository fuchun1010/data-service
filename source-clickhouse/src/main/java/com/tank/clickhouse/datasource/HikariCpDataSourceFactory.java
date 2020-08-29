package com.tank.clickhouse.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * @author tank198435163.com
 */
public class HikariCpDataSourceFactory extends UnpooledDataSourceFactory {

  public HikariCpDataSourceFactory() {
    this.dataSource = new HikariDataSource();
    this.dataSource.setConnectionTestQuery("select 1");
    this.dataSource.setMaximumPoolSize(10);
    this.dataSource.setIdleTimeout(60000);
    this.dataSource.setConnectionTimeout(1000L);
  }

  private final HikariDataSource dataSource;
}
