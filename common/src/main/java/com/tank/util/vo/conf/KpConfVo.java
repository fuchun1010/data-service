package com.tank.util.vo.conf;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

/**
 * kafka producer yaml mapping class
 * tips: all field type must be String type
 *
 * @author tank198435163.com
 */
@Getter
@Setter
public class KpConfVo {

  public String toBrokerList() {
    Preconditions.checkArgument(Objects.isNull(brokers) || brokers.trim().length() > 0, "broker list not allowed empty");
    return !brokers.contains(",") ? format("%s:%s", brokers, this.port) : Stream.of(brokers.split(","))
            .map(String::trim)
            .map(broker -> format("%s:%s", broker, this.port))
            .collect(Collectors.joining(","));
  }

  private String port;

  private String ack;

  @Getter(value = AccessLevel.PRIVATE)
  private String brokers;
}
