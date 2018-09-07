package daggerok;

import daggerok.app.http.JSON;
import daggerok.app.http.Server;
import daggerok.app.security.LoginContextConfig;
import daggerok.app.security.MyLoginContextBuilder;
import daggerok.context.DaggerokContext;
import io.vavr.control.Try;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.singletonMap;

@Slf4j
public class App {
  public static void main(String[] args) {
    final DaggerokContext context = DaggerokContext.create(App.class)
                                                   .register(LoginContextConfig.class, new LoginContextConfig("jaas.config"))
                                                   .initialize();
    final MyLoginContextBuilder contextBuilder = context.getBean(MyLoginContextBuilder.class);
    final Server server = context.getBean(Server.class);
    server.withHandler("/", httpExchange -> {
      final OutputStream responseBody = httpExchange.getResponseBody();
      final String body = JSON.stringify(singletonMap("hello", "world"));
      httpExchange.getResponseHeaders().add("content-type", "application/json");
      Try.run(() -> {
        log.info("beans amount: {}", context.size());
        contextBuilder.login();
        httpExchange.sendResponseHeaders(200, body.length());
        responseBody.write(body.getBytes(UTF_8));
        responseBody.flush();
      }).getOrElseThrow(App::handleError);
    });
    server.start();
  }

  private static RuntimeException handleError(final Throwable throwable) {
    log.error(throwable.getLocalizedMessage(), throwable);
    return new RuntimeException(throwable);
  }
}
