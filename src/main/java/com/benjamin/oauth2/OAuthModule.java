package com.benjamin.oauth2;

import com.benjamin.oauth2.token.IAuthTokenGenerator;
import com.benjamin.oauth2.token.impl.SimpAuthTokenGenerator;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

/**
 * Created by benjamin on 9/3/14.
 */
public class OAuthModule implements Module {
  @Override
  public void configure(Binder binder) {
    binder.bind(IAuthTokenGenerator.class).to(SimpAuthTokenGenerator.class).in(Scopes.SINGLETON);
  }
}
