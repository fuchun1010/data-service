package com.tank;

import cn.hutool.bloomfilter.BloomFilterUtil;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Zodiac;
import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONStrFormater;
import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Maps;
import com.tank.util.vo.health.Health;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import lombok.*;
import lombok.experimental.Accessors;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.String.format;

/**
 * @author tank198435163.com
 */
public class HuToolsTest {

  @Test
  public void testMd5() {
    val health = new Health();
    health.setKey("s0001").setMessage("hello,health");
    val jsonObj = new JSONObject(health);
    val jsonStr = jsonObj.toJSONString(2);
    System.out.println(JSONStrFormater.format(jsonStr));
    val md5Value = SecureUtil.md5(jsonStr);
    System.out.println(md5Value);
  }


  @Test
  public void testTimeConverter() {
    val result = TimeUnit.MINUTES.toMillis(1);
    System.out.println(result);
  }

  @Test
  public void testLFUCache() {
    val cache = CacheUtil.<Integer, Integer>newLFUCache(100, TimeUnit.MINUTES.toMillis(200));
    IntStream.rangeClosed(0, 120).boxed().forEach(i -> {
      cache.put(i, i);
      try {
        Thread.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(cache.size());
  }

  @Test
  public void testMapBloomFilter() {
    val m = 1024;
    val filter = BloomFilterUtil.createBitMap(m);
    filter.add("hello");
    filter.add("welcome to china");
    System.out.println(filter.contains("hello"));
  }

  @Test
  public void testStr() {
    val result = StrUtil.isBlank("");
    Assert.assertTrue(result);
  }

  @Test
  public void testDate() {
    val dateTime = DateUtil.parse("2020-11-12 12:32:11", "yyyy-MM-dd HH:mm:ss");
    val day = dateTime.dayOfYear();
    val dateTimeStr = DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");
    System.out.println(day);
    System.out.println(dateTimeStr);
  }

  @Test
  public void testLoadClassByName() {
    val personDto = new PersonDto();
    personDto.setName("jack").setBirthday("1998-11-12");
    val birthDate = DateUtil.parse(personDto.getBirthday(), DatePattern.NORM_DATE_FORMAT);
    val chineseZodiac = Zodiac.getChineseZodiac(birthDate);
    val zodiac = Zodiac.getZodiac(birthDate);
    personDto.setChineseZodiac(chineseZodiac).setZodiac(zodiac);

    val jsonObject = new JSONObject(personDto);
    val jsonStr = jsonObject.toJSONString(2);
    val prettyJsonStr = JSONStrFormater.format(jsonStr);
    System.out.println(prettyJsonStr);

    val jsonClazz = Try.of(() -> Thread.currentThread().getContextClassLoader().loadClass(PersonDto.class.getName()))
            .getOrElseThrow(() -> new ClassCastException("转换异常"));

    val converter = new JSONObject(jsonStr, true);
    final PersonDto target = converter.toBean((Type) jsonClazz);
    Assert.assertEquals(target.getName(), "jack");
    Assert.assertEquals(target.getBirthday(), "1998-11-12");
    val age = DateUtil.date().between(birthDate).betweenYear(true);
    System.out.println(age);
  }

  @Test
  public void testGuess() {
    val result = Double.doubleToLongBits(81.6f);
    System.out.println(result);
  }

  @Test
  public void testRandomWeight() {
    val node1 = new NetworkNode().setIp("192.168.0.1").setPort(9000).setScore(1);
    val node2 = new NetworkNode().setIp("192.168.0.2").setPort(9000).setScore(5);
    val node3 = new NetworkNode().setIp("192.168.0.3").setPort(9000).setScore(10);
    val weightRandom = new WeightRandom<NetworkNode>();
    Stream.of(node1, node2, node3).forEach(node -> weightRandom.add(node, node.getScore()));
    IntStream.rangeClosed(0, 3).boxed().forEach(index -> {
      val result = weightRandom.next().toString();
      System.out.println(result);
    });
    weightRandom.clear();
  }

  @Test
  public void testTailMap() {
    val map = Maps.<Integer, Integer>newTreeMap();
    map.put(1, 2);
    map.put(7, 3);
    map.put(10, 6);
    val result = map.tailMap(6, false);
    System.out.println(result.firstKey());
    System.out.println(map.lastKey());
  }

  @Test
  @SneakyThrows
  public void testCron() {
    System.out.println(DateUtil.now());
    val latch = new CountDownLatch(1);
    val result = CronUtil.schedule("*/1 * * * * ? *", (Task) () -> {
      System.out.println(DateUtil.now());
      latch.countDown();
    });
    CronUtil.setMatchSecond(true);
    CronUtil.start();
    System.out.println(result);
    latch.await();
  }

  @Test
  @SneakyThrows
  public void testGuavaCache() {
    val cache = CacheBuilder.<String, String>newBuilder()
            .maximumSize(200L)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .refreshAfterWrite(100, TimeUnit.MILLISECONDS)
            .weakKeys()
            .removalListener(notification -> System.out.println(format("expire reason:%s", notification.getCause().name())))
            .build(new CacheLoader<String, String>() {
              @Override
              @ParametersAreNonnullByDefault
              public String load(@NonNull final String key) throws Exception {
                return key;
              }
            });
    cache.put("sz_distribution", "hello");
    System.out.println(cache.get("sz_distribution"));
  }

  @Test
  public void testStrUtil() {
    val result = StrUtil.format("this is :{}, age is:{}", "jack", 21);
    System.out.println(result);
  }


  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @Accessors(chain = true)
  private static class PersonDto {
    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
              .add("name", this.name)
              .add("birthday", birthday)
              .toString();
    }

    private String name;
    private String birthday;
    private String zodiac;
    private String chineseZodiac;
  }


  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @Accessors(chain = true)
  private static class NetworkNode {

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this).add("port", this.port)
              .add("ip", this.ip).add("score", this.score).toString();
    }

    private Integer port;
    private String ip;
    private Integer score;
  }

  private int hash(Object value) {
    int h = 0;
    int seed = 10;
    int cap = 100;
    return (value == null) ? 0 : Math.abs(seed * (cap - 1) & ((h = value.hashCode()) ^ (h >>> 16)));
  }

}
