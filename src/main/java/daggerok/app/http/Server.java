package daggerok.app.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
public class Server {

  private final Map<String, Consumer<HttpExchange>> endpoints = new ConcurrentHashMap<>(1);

  private final ServerConfig serverConfig;

  @Inject
  public Server(final ServerConfig serverConfig) {
    this.serverConfig = serverConfig;
  }

  @SneakyThrows
  public HttpServer start() {

    if (endpoints.isEmpty())
      throw new RuntimeException("No registered http handlers where found.");

    final int port = serverConfig.getPort();
    final InetSocketAddress address = new InetSocketAddress(port);
    final HttpServer httpServer = HttpServer.create(address, port);

    endpoints.forEach((path, handler) -> httpServer.createContext(path, handler::accept));
    httpServer.start();

    final InetSocketAddress actualAddress = httpServer.getAddress();
    log.info("server listening: {}:{}", actualAddress.getHostString(), actualAddress.getPort());

    return httpServer;
  }

  public Server withHandler(final String contextPath, final Consumer<HttpExchange> handler) {
    Objects.requireNonNull(contextPath, "contextPath may not be null");
    Objects.requireNonNull(handler, "contextPath may not be null");
    final String path = "".equals(contextPath.trim()) ? serverConfig.getContextRoot() : contextPath;
    endpoints.put(path, handler);
    return this;
  }
}
