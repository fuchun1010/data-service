package com.tank;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author tank198435163.com
 */
public class InnerFunctionTest {

  @Test
  public void testFunctions() {
    final Map<String, BiFunction<Integer, Integer, Integer>> actions = this.fetchAction();
    val addResult = actions.get("add").apply(4, 5);
    val subResult = actions.get("sub").apply(7, 2);
    Assert.assertEquals(9, (int) addResult);
    Assert.assertEquals(5, (int) subResult);
  }

  private Map<String, BiFunction<Integer, Integer, Integer>> fetchAction() {
    final Map<String, BiFunction<Integer, Integer, Integer>> functions = new HashMap<>();
    final BiFunction<Integer, Integer, Integer> sub = (a, b) -> a - b;
    final BiFunction<Integer, Integer, Integer> add = Integer::sum;
    functions.put("sub", sub);
    functions.put("add", add);
    return functions;
  }

}
