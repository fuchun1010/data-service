package com.tank.clickhouse.datasource;


import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import io.vavr.Function3;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * @author tank198435163.com
 */
public class DataSource {

  public static DataSource createDataSource() {
    return DATA_SOURCE;
  }


  public <I, R> Optional<R> fetchSingle(@NonNull final String namespace,
                                        @NonNull final String sqlId,
                                        @NonNull final Optional<I> inputOpt,
                                        @NonNull final Function3<String, I, SqlSession, Cursor<R>> function) {

    return this.fetchMultiple(namespace, sqlId, inputOpt, function)
            .filter(list -> !list.isEmpty())
            .map(list -> list.get(0));

  }

  @SneakyThrows
  public <I, R> Optional<List<R>> fetchMultiple(@NonNull final String namespace,
                                                @NonNull final String sqlId,
                                                @NonNull final Optional<I> inputOpt,
                                                @NonNull final Function3<String, I, SqlSession, Cursor<R>> function) {
    Cursor<R> cursor = null;
    val sqlLocation = StrUtil.format("{}.{}", namespace, sqlId);
    try (val session = this.sqlSessionFactory.openSession()) {
      cursor = inputOpt.map(d -> function.apply(sqlLocation, d, session))
              .orElse(function.apply(sqlLocation, null, session));
      Iterator<R> iterator = cursor.iterator();
      List<R> lists = Lists.newArrayList();
      while (iterator.hasNext()) {
        R target = iterator.next();
        lists.add(target);
      }
      return Optional.ofNullable(lists);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (Objects.nonNull(cursor)) {
        cursor.close();
      }
    }
    return Optional.empty();
  }

  public <I, R> boolean cdc(@NonNull final String namespace,
                            @NonNull final String sqlId,
                            @NonNull final Optional<I> inputOpt,
                            @NonNull final BiConsumer<String, I> consumer) {
    val sqlLocation = StrUtil.format("{}.{}", namespace, sqlId);
    try (val session = this.sqlSessionFactory.openSession()) {
      if (inputOpt.isPresent()) {
        consumer.accept(sqlLocation, inputOpt.get());
      } else {
        consumer.accept(sqlLocation, null);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
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
