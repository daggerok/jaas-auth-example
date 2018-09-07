package daggerok.app.http;

import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

public class JSON {

  public static String stringify(final Map<String, Object> map) {
    final StringBuilder builder = new StringBuilder("{");
    final String keyValues = map.keySet()
                                .parallelStream()
                                .map(key -> format("\"%s\":\"%s\"", key, map.getOrDefault(key, "")))
                                .collect(joining(","));
    return builder.append(keyValues)
                  .append("}")
                  .toString();
  }

  private JSON() {}
}
