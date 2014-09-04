package com.benjamin.oauth2.handler.impl;

import com.benjamin.oauth2.handler.RequestHandler;
import com.benjamin.oauth2.token.GrantType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by benjamin on 9/4/14.
 */
public abstract class AbstractRequestHandler implements RequestHandler {
  @Override
  public void handleRequest(ServletRequest request, ServletResponse response) {
    GrantType grantType = Enum.valueOf(GrantType.class,request.getParameter("grant_type")) ;
    if (GrantType.PASSWORD == grantType){

    }
  }
  public abstract void handlePasswordGrantType(ServletRequest request, ServletResponse response);

  public abstract void handleAuthCodeGrantType(ServletRequest request, ServletResponse response);

  public abstract void handleImplicitGrantType(ServletRequest request, ServletResponse response);
}
