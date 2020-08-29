package com.tank.clickhouse.datasource;


import io.vavr.Function3;
import lombok.NonNull;
import lombok.val;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.util.List;
import java.util.Optional;

/**
 * @author tank198435163.com
 */
public class DataSource {

  public static DataSource createDataSource() {
    return DATA_SOURCE;
  }

  public <I, R> Optional<List<R>> crud(@NonNull final String namespace,
                                       @NonNull final String sqlId,
                                       @NonNull final Optional<I> inputOpt,
                                       @NonNull final Function3<String, I, SqlSession, List<R>> function) {
    try (val session = this.sqlSessionFactory.openSession()) {
      return inputOpt.map(i -> Optional.ofNullable(function.apply(String.format("%s.%s", namespace, sqlId), i, session))).orElseGet(() -> Optional.ofNullable(function.apply(String.format("%s.%s", namespace, sqlId), null, session)));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();

  }


  private DataSource() {
    val resource = "mybatis/source.xml";
    try (val in = Resources.getResourceAsStream(resource)) {
      this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private final static DataSource DATA_SOURCE = new DataSource();

  private SqlSessionFactory sqlSessionFactory;

}
