package daggerok.app.security;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

@Slf4j
@Singleton
public class MyCalbackHandler implements CallbackHandler {

  @Override
  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    log.info("handling callback...");
  }
}
