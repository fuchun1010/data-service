package com.tank.util.vo.health;

import com.google.common.base.MoreObjects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author tank198435163.com
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CustomRecordMeta {

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("topic", topic)
            .add("partitionId", partitionId)
            .add("offset", offset)
            .toString();
  }

  private String topic;

  private int partitionId;

  private long offset;

}
