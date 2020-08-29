package com.tank.clickhouse.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * @author tank198435163.com
 */
public class HikariCpDataSourceFactory extends UnpooledDataSourceFactory {

  public HikariCpDataSourceFactory() {
    this.dataSource = new HikariDataSource();
  }

}
