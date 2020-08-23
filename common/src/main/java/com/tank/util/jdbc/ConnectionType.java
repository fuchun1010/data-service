package com.tank.util.jdbc;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tank198435163.com
 */
@AllArgsConstructor
public enum ConnectionType {

  /**
   * list all supported db connection
   */
  CK("Ck"), MYSQL("Mysql");

  @Getter
  private final String type;
}
