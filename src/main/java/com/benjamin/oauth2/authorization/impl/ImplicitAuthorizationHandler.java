package com.benjamin.oauth2.authorization.impl;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by benjamin on 9/4/14.
 */
public class ImplicitAuthorizationHandler extends GrantTypeAuthorizationHandlerAdapter {
  @Override
  public void handleImplicitGrantType(ServletRequest request, ServletResponse response) {
    super.handleImplicitGrantType(request, response);
  }
}
