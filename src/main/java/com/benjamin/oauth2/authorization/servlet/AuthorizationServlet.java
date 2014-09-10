package com.benjamin.oauth2.authorization.servlet;

import com.benjamin.oauth2.authorization.AuthorizationHandler;
import com.benjamin.oauth2.authorization.impl.ImplicitAuthorizationHandler;
import com.benjamin.oauth2.authorization.impl.PasswordAuthorizationHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by benjamin on 9/9/14.
 */
public class AuthorizationServlet extends HttpServlet {

  private List<AuthorizationHandler> authorizationHandlers = new ArrayList<AuthorizationHandler>();

  @Override
  public void init() throws ServletException {
    authorizationHandlers.add(new PasswordAuthorizationHandler());
    authorizationHandlers.add(new ImplicitAuthorizationHandler());
    super.init();
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      for(AuthorizationHandler authorizationHandler: authorizationHandlers){
        authorizationHandler.handleAuthorization(req, resp);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
