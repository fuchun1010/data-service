package com.tank.util.async;

import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * @author tank198435163.com
 */
public class SpScTest {

  @Test
  public void testProcessItem() throws InterruptedException {
    //
    val result = IntStream.rangeClosed(0, 20)
            .boxed()
            .map(this.intSpSc::addItem)
            .reduce(true, Boolean::logicalAnd);
    Assert.assertTrue(result);
    this.intSpSc.processItem(list -> list.forEach(System.out::println));
    Thread.sleep(2000);
  }

  @Before
  public void init() {
    this.intSpSc = new SpSc<>("int-consumer");
  }

  private SpSc<Integer> intSpSc;
}