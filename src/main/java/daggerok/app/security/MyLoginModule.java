package daggerok.app.security;

import lombok.extern.slf4j.Slf4j;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.util.Map;

import static daggerok.app.security.MyLoginContextBuilder.key;

@Slf4j
public class MyLoginModule implements LoginModule {

  static final boolean result = System.getProperty("ololo", "trololo").contains("ololo");

  @Override
  public void initialize(final Subject subject,
                         final CallbackHandler callbackHandler,
                         final Map<String, ?> sharedState,
                         final Map<String, ?> options) {

    log.info("initialize security?");
    log.info("ololo value: {}", System.getProperty("ololo", null));
    log.info("key {} value: {}", key, System.getProperty(key, null));
  }

  @Override
  public boolean login() throws LoginException {
    return result;
  }

  @Override
  public boolean commit() throws LoginException {
    return result;
  }

  @Override
  public boolean abort() throws LoginException {
    return result;
  }

  @Override
  public boolean logout() throws LoginException {
    return result;
  }
}
