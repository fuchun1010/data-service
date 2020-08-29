package com.tank.vo;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.beans.Transient;
import java.io.Serializable;

/**
 * @author tank198435163.com
 */
@Getter
@Setter
public class Person implements Serializable {

  @Transient
  public Integer age() {
    return DateUtil.ageOfNow(this.birthDay);
  }

  private String cardId;

  private Integer gender;

  private String birthDay;

  private Integer skinColor;

  private String nationality;

  private String jobs;
}
