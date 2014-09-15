package com.benjamin.oauth2.authorization.impl;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by benjamin on 9/12/14.
 */
public class AuthorizationCodeHandler extends GrantTypeAuthorizationHandlerAdapter {
  @Override
  public void handleAuthCodeGrantType(HttpServletRequest request, HttpServletResponse response) {
    boolean result = validPassword(request);
    if(result){
      //返回accessToken
      tokenProvider.getAuthTokenGenerator().generateAccessToken();
    }
    super.handleAuthCodeGrantType(request, response);
  }
}
