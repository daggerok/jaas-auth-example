package daggerok.app.security;

import javax.inject.Inject;

public class LoginContextConfig {

  String configPath = "jaas.config";

  @Inject
  public LoginContextConfig(String configPath) {
    this.configPath = configPath;
  }
}
