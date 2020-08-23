package com.tank.util.jdbc;

import com.google.common.collect.Lists;
import io.vavr.control.Try;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author tank198435163.com
 */
public class ConnBuilderTest {

  @Test
  public void queryWithoutParameters() throws Exception {
    val url = "jdbc:clickhouse://localhost:8123/order";
    val conn = this.connectionConnBuilder
            .fetchConnection(ConnectionType.CK)
            .map(opt -> opt.apply(url, "default", ""))
            .map(Try::get)
            .orElseThrow(() -> new Exception("connect db failure"));

    val results = this.connectionConnBuilder.<Integer>queryWithoutParameters(conn, "select count(1) as counter from tab_persons", rs -> rs.getInt("counter"));
    Assert.assertEquals(results.size(), 1);

    val genders = this.connectionConnBuilder.<Integer>queryWithoutParameters(conn, "select CAST(gender,'Int8' ) as gender from tab_persons", rs -> rs.getInt("gender"));
    Assert.assertEquals(genders.size(), 4);
  }

  @Test
  @SneakyThrows
  public void queryWithParameters() {
    val url = "jdbc:clickhouse://localhost:8123/order";
    val conn = this.connectionConnBuilder
            .fetchConnection(ConnectionType.CK)
            .map(opt -> opt.apply(url, "default", ""))
            .map(Try::get)
            .orElseThrow(() -> new Exception("connect db failure"));

    val results = this.connectionConnBuilder.<Integer>queryWithParameters(conn,
            "select count(1) as counter from tab_persons where cardId = ?",
            Lists.newArrayList("500105198403050002"),
            (pst, parameters) -> {
              try {
                pst.setString(1, parameters.get(0).toString());
              } catch (SQLException ex) {
                ex.printStackTrace();
              }
            }, rs -> rs.getInt("counter"));

    Assert.assertEquals(1, results.size());
  }

  @Test
  public void changeData() {

  }

  @Test
  @SneakyThrows
  public void fetchConnection() {
    val url = "jdbc:clickhouse://localhost:8123/order";
    val notOk = this.connectionConnBuilder.fetchConnection(ConnectionType.CK)
            .map(opt -> opt.apply(url, "default", ""))
            .map(tryConn -> Objects.isNull(tryConn.getOrNull()))
            .orElseThrow(() -> new Exception("connect clickhouse db error"));
    Assert.assertFalse(notOk);
  }

  @Before
  public void init() {
    this.connectionConnBuilder = new ConnBuilder<>();
  }

  private ConnBuilder<Connection> connectionConnBuilder;
}