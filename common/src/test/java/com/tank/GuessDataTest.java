package com.tank;

import lombok.NonNull;
import lombok.val;
import lombok.var;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author tank198435163.com
 */
public class GuessDataTest {

  @Test
  public void testInput() {
    val random = ThreadLocalRandom.current();
    var counter = 100;
    IntStream.range(0, counter).boxed().map(d -> random.nextInt(10)).forEach(System.out::println);
  }

  @Test
  public void testPlay() {

    val playGame = new PlayGame<Integer>();
    val dataGenerator = new DataGenerator();
    val userInput = new UserInputs();

    val results = playGame.generateData(4, dataGenerator::defaultNextRandomLessThan);
    final Map<Integer, Integer> map = playGame.toDataWithIndex(results, d -> {
      for (int i = 0; i < results.size(); i++) {
        if (results.get(i).compareTo(d) == 0) {
          return i;
        }
      }
      return -1;
    });
    val inputs = userInput.transform2List("1,2,3,4");
    val result = playGame.publishResult(inputs, map);
    System.out.println(result);

  }

  @Test
  public void testPlayFixData() {
    val userInput = new UserInputs();
    List<Integer> inputs = userInput.transform2List("1,2,3,4");

    val map = new HashMap<Integer, Integer>(32);

    map.put(1, 0);
    map.put(4, 1);
    map.put(7, 2);
    map.put(8, 3);

    val playGame = new PlayGame<Integer>();

    val result = playGame.publishResult(inputs, map);
    System.out.println(result);
  }

  private static class UserInputs {

    public UserInputs() {
      this(4);
    }

    public UserInputs(@NonNull final Integer times) {
      this.counter = new AtomicInteger(times);
    }

    public List<Integer> transform2List(@NonNull final String arr) {
      if (arr.length() == 0) {
        throw new IllegalArgumentException("arr not allowed empty");
      }
      return Arrays.stream(arr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }

    public boolean continued() {
      return counter.decrementAndGet() == 0;
    }

    private final AtomicInteger counter;

  }

  private static class PlayGame<T> {

    public <K> Map<K, T> toDataWithIndex(@NonNull final List<T> list,
                                         @NonNull final Function<T, K> function) {

      final Map<K, T> maps = new HashMap<>();

      for (T data : list) {
        final K key = function.apply(data);
        maps.put(key, data);
      }

      return maps;
    }

    public List<T> generateData(@NonNull Integer loopTimes, Supplier<T> supplier) {

      T preData = null;
      int counter = 0;
      List<T> collections = new ArrayList<>();

      while (loopTimes != counter) {
        T result = supplier.get();
        if (!result.equals(preData)) {
          counter++;
          collections.add(result);
          preData = result;
        }
      }

      return collections;
    }

    /**
     * wrap user action
     *
     * @param userInputs
     * @param answer
     * @return
     */
    public String publishResult(@NonNull List<Integer> userInputs,
                                @NonNull Map<Integer, Integer> answer) {

      int fullCorrect = 0;
      int partialCorrect = 0;

      val size = userInputs.size();
      for (int index = 0; index < size; index++) {

        val value = userInputs.get(index);

        val isFullCorrect = answer.containsKey(value) && answer.get(value) == index;
        if (isFullCorrect) {
          fullCorrect++;
          continue;
        }

        val isPartialCorrect = answer.containsKey(value);
        if (isPartialCorrect) {
          partialCorrect++;
        }
      }

      return String.format("%dA%dB", fullCorrect, partialCorrect);
    }

  }

  private static class DataGenerator {

    public int defaultNextRandomLessThan() {
      return this.defaultNextRandomLessThan(10);
    }

    public int defaultNextRandomLessThan(@NonNull final Integer seed) {
      if (seed <= 0) {
        throw new IllegalArgumentException("seed not allowed less than 0");
      }
      return random.nextInt(seed);
    }

    final private ThreadLocalRandom random = ThreadLocalRandom.current();

  }
}
