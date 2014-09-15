package com.benjamin.oauth2.authorization.impl;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by benjamin on 9/4/14.
 */
public class ImplicitAuthorizationHandler extends GrantTypeAuthorizationHandlerAdapter {
  @Override
  public void handleImplicitGrantType(HttpServletRequest request, HttpServletResponse response) {
    super.handleImplicitGrantType(request, response);
  }
}
