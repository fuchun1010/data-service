package com.tank.clickhouse.datasource;

import com.google.common.collect.Lists;
import com.tank.vo.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class DataSourceTest {

  @Test
  public void fetchSingle() {
    final Optional<String> personOpt = this.dataSource.crud("ch.person",
            "onePerson",
            Optional.of("500105198403050004"),
            (location, parameter, session) -> session.selectList(location, parameter)
    ).map(list -> list.get(0).toString());
    System.out.println(personOpt);
  }

  @Test
  public void fetchMultiple() {
    final Optional<List<Person>> personsOpt = this.dataSource.crud("ch.person",
            "persons",
            Optional.empty(),
            (location, parameter, session) -> session.selectList(location, parameter));
    Assert.assertTrue(personsOpt.isPresent());
    personsOpt.orElse(Lists.newArrayList())
            .stream()
            .map(Person::age)
            .forEach(System.out::println);
  }


  @Before
  public void init() {
    this.dataSource = DataSource.createDataSource();
  }

  private DataSource dataSource;

}