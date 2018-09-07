package daggerok.app.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServerConfig {

  private int port = 8080;
  private String contextRoot = "/";
}
