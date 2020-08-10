package com.tank.util.yaml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;

import java.io.File;
import java.util.Objects;

/**
 * @author tank198435163.com
 */
public class YmlExtractor {

  public YmlExtractor(@NonNull final String fileName) {
    this.fileName = fileName;
    this.mapper = new ObjectMapper(new YAMLFactory());
    this.mapper.findAndRegisterModules();
    this.mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
  }

  @SneakyThrows
  public <T> T extractConfig(@NonNull final Class<T> clazz) {
    val classLoader = Thread.currentThread().getContextClassLoader();
    val url = classLoader.getResource(this.fileName);
    val file = new File(Objects.nonNull(url) ? url.getFile() : "/");
    return mapper.readValue(file, clazz);
  }

  private final String fileName;

  private final ObjectMapper mapper;

}
