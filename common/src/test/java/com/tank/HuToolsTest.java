package com.tank;

import cn.hutool.bloomfilter.BloomFilterUtil;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Zodiac;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONStrFormater;
import com.google.common.base.MoreObjects;
import com.tank.util.vo.health.Health;
import io.vavr.control.Try;
import lombok.*;
import lombok.experimental.Accessors;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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

    val jsonClazz = Try.of(() -> this.getClass().getClassLoader().loadClass(PersonDto.class.getName()))
            .getOrElseThrow(() -> new ClassCastException("转换异常"));

    val converter = new JSONObject(jsonStr, true);
    final PersonDto target = converter.toBean((Type) jsonClazz);
    Assert.assertEquals(target.getName(), "jack");
    Assert.assertEquals(target.getBirthday(), "1998-11-12");
    val age = DateUtil.date().between(birthDate).betweenYear(true);
    System.out.println(age);
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

  private int hash(Object value) {
    int h = 0;
    int seed = 10;
    int cap = 100;
    return (value == null) ? 0 : Math.abs(seed * (cap - 1) & ((h = value.hashCode()) ^ (h >>> 16)));
  }

}
