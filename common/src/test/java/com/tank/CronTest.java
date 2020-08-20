package com.tank;

import cn.hutool.core.util.StrUtil;
import lombok.val;
import org.junit.Test;

public class CronTest {
  @Test
  public void testDelayTime() {
    long thisTime = System.currentTimeMillis();
    long secondToMillion = 1000L;
    for (; ; ) {
      val nextTime = ((thisTime / secondToMillion) + 1) * secondToMillion;
      val sleepTime = nextTime - System.currentTimeMillis();
      System.out.println(StrUtil.format("startTime:[{}],nextTime:[{}],sleepTime:[{}]", thisTime, nextTime, sleepTime));
      if (!safeSleep(sleepTime)) {
        break;
      }
    }

    System.out.println(StrUtil.format("ok"));

  }

  private boolean safeSleep(long millis) {
    long done = 0;
    long before;
    long spendTime;
    while (done >= 0 && done < millis) {
      before = System.currentTimeMillis();
      if (!sleep(millis - done)) {
        break;
      }
      spendTime = System.currentTimeMillis() - before;
      if (spendTime < 0) {
        break;
      }
      done += spendTime;
    }

    return false;
  }

  private boolean sleep(long millis) {
    try {
      Thread.sleep(millis);
      return false;
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return true;
  }
}
