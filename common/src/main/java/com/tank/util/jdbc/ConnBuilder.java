package com.tank.util.jdbc;

import com.google.common.collect.Maps;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.control.Try;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

/**
 * @author tank198435163.com
 */
public class ConnBuilder<T extends Connection> {

  public ConnBuilder() {
    initConnection();
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
    return Optional.ofNullable(this.connectionMapping.get(connectionType));
  }

  private void initConnection() {

    final Function3<String, String, String, Try<Connection>> ckConnection = (url, user, password) -> Try
            .of(() -> Class.forName("ru.yandex.clickhouse.ClickHouseDriver"))
            .transform(driver -> Try.of(() -> DriverManager.getConnection(url, user, password)));

    connectionMapping.put(ConnectionType.CK, ckConnection);
  }

  private final Map<ConnectionType, Function3<String, String, String, Try<Connection>>> connectionMapping = Maps.newHashMap();


}
