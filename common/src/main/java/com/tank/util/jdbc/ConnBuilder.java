package com.tank.util.jdbc;

import com.google.common.collect.Lists;
import io.vavr.CheckedFunction1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.control.Try;
import lombok.NonNull;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author tank198435163.com
 */
public class ConnBuilder<T extends Connection> {

  public <R> List<R> queryWithoutParameters(@NonNull final T conn,
                                            @NonNull final String sql,
                                            @NonNull final CheckedFunction1<ResultSet, R> function) throws Exception {
    return Try.of(() -> conn.prepareStatement(sql))
            .map(pst -> {
              ResultSet rs = null;
              List<R> results = Lists.newArrayList();
              try {
                rs = pst.executeQuery();
                while (rs.next()) {
                  R result = function.apply(rs);
                  results.add(result);
                }
                return results;
              } catch (Throwable throwable) {
                throwable.printStackTrace();
              } finally {
                try {
                  pst.close();
                  if (Objects.nonNull(rs)) {
                    System.out.println("close resultSet ok");
                    rs.close();
                  }
                } catch (Exception exp) {
                  exp.printStackTrace();
                }
              }
              return results;
            })
            .getOrElseThrow(() -> new Exception("clickhouse查询无参数sql异常"));
  }

  public Boolean changeData(
          @NonNull final Connection conn,
          @NonNull final String sql,
          @NonNull final Object[] params,
          @NonNull final Function2<PreparedStatement, Object[], Boolean> fun
  ) {

    return Try.of(() -> conn.prepareStatement(sql))
            .map(pst -> {
              try {
                return (boolean) fun.apply(pst, params);
              } catch (Exception e) {
                e.printStackTrace();
              } finally {
                try {
                  conn.close();
                } catch (final SQLException ex) {
                  ex.printStackTrace();
                }
              }
              return false;
            })
            .onFailure(Throwable::printStackTrace)
            .getOrElse(false);
  }

  public Optional<Function3<String, String, String, Try<Connection>>> fetchConnection(@NonNull final ConnectionType connectionType) {
    return Optional.ofNullable(this.create(connectionType));
  }

  public Function3<String, String, String, Try<Connection>> create(@NonNull final ConnectionType connectionType) {
    return (url, username, password) -> Try.of(() -> Class.forName(connectionType.getClassDriver()))
            .transform(driver -> Try.of(() -> DriverManager.getConnection(url, username, password)));
  }

}
