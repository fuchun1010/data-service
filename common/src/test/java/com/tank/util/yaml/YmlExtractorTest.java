package com.tank.util.yaml;

import com.tank.util.vo.conf.KpConfVo;
import lombok.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author tank198435163.com
 */
public class YmlExtractorTest {

  @Test
  public void extractConfig() {
    val config = this.ymlExtractor.extractConfig(ServerConf.class);
    System.out.println();
  }

  @Test
  public void extractKafkaProducerConfig() {
    this.ymlExtractor = new YmlExtractor("kafka-producer-conf.yaml");
    val config = this.ymlExtractor.extractConfig(KpConfVo.class);
    assertEquals(config.toBrokerList(), "localhost:9092");
    assertEquals(config.getPort(), "9092");
    assertEquals(config.getAck(), "1");
  }

  @Test
  public void extractKafkaProducerConfig2() {
    this.ymlExtractor = new YmlExtractor("kafka-producer-conf2.yaml");
    val config = this.ymlExtractor.extractConfig(KpConfVo.class);
    assertEquals(config.toBrokerList(), "192.168.0.9:9092,192.168.0.10:9092");
    assertEquals(config.getPort(), "9092");
    assertEquals(config.getAck(), "1");
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