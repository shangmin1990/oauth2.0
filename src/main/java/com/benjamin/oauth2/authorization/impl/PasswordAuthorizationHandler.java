package com.benjamin.oauth2.authorization.impl;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by benjamin on 9/9/14.
 */
public class PasswordAuthorizationHandler extends GrantTypeAuthorizationHandlerAdapter {
  @Override
  public void handlePasswordGrantType(ServletRequest request, ServletResponse response) {
    System.out.println(this.getClass().getSimpleName() +" handle request ");
    super.handlePasswordGrantType(request, response);
  }
}
