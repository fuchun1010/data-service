package com.tank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author tank198435163.com
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class AbstractLeaf {

  private String goodsId;
  private String name;
  private String salesPrice;
  private String weight;
  private String unitPrice;
  private String quantity;
  private String itemSnapshotId;
  private String goodsSn;
  private String orgCode;
  private String channelId;
  private String goodsTradeType;
}
