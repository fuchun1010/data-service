package com.tank.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.NonNull;

/**
 * @author tank198435163.com
 */
public class JsonUtil {

  public static <T> Try<T> toObject(@NonNull final String jsonStr, @NonNull final Class<T> clazz) {
    return Try.of(() -> MAPPER.readValue(jsonStr, clazz));
  }

  public static <T> String toJson(@NonNull final T body) {
    return Try.of(() -> MAPPER.writeValueAsString(body)).getOrElse("-");
  }

  private final static ObjectMapper MAPPER = new ObjectMapper();

}
