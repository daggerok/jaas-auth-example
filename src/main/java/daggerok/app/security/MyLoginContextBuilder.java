package daggerok.app.security;

import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

public class MyLoginContextBuilder {

  public static final String key = "java.security.auth.login.config";

  private final LoginContextConfig config;
  private final CallbackHandler myCalbackHandler;

  @Inject
  public MyLoginContextBuilder(final LoginContextConfig config, final MyCalbackHandler myCalbackHandler) {
    this.config = config;
    this.myCalbackHandler = myCalbackHandler;
  }

  @SneakyThrows
  public LoginContext build() {
    final String defaultValue = null == config
        || null == config.configPath
        || "".equals(config.configPath.trim())
        ? "jaas.config" : config.configPath;
    System.setProperty(key, System.getProperty(key, defaultValue));
    return new LoginContext("MyJaas", myCalbackHandler);
  }

  @SneakyThrows
  public void login() {
    this.build().login();
  }
}
