package com.tank.util.yaml;

import lombok.*;
import org.junit.Before;
import org.junit.Test;

/**
 * @author tank198435163.com
 */
public class YmlExtractorTest {

  @Test
  public void extractConfig() {
    val config = this.ymlExtractor.extractConfig(ServerConf.class);
    System.out.println();
  }

  @Before
  public void init() {
    this.ymlExtractor = new YmlExtractor("test.yaml");
  }

  private YmlExtractor ymlExtractor;

  /**
   * @author tank198435163.com
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  private static class ServerConf {
    private Server server;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Server {
      private int port;
    }
  }
}