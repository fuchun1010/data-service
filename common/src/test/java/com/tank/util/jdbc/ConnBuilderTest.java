package com.tank.util.jdbc;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.Objects;

public class ConnBuilderTest {

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