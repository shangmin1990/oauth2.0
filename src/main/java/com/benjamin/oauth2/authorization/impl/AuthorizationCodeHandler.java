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
  public void handleAuthCodeGrantType(ServletRequest request, ServletResponse response) {
    String username = request.getParameter(USERNAME);
    String password = request.getParameter(PASSWORD);
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    if(username != null && password != null && !username.isEmpty() && !password.isEmpty()){
      boolean result = passwordValidator.validPassword(username,password);
      if(result){
        //返回accessToken
        tokenProvider.getAuthTokenGenerator().generateAccessToken();
      }
    }
    super.handleAuthCodeGrantType(request, response);
  }
}
