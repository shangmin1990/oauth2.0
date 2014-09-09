package com.benjamin.oauth2.authorization.servlet;

import com.benjamin.oauth2.authorization.AuthorizationHandler;
import com.benjamin.oauth2.authorization.impl.PasswordAuthorizationHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by benjamin on 9/9/14.
 */
public class AuthorizationServlet extends HttpServlet {

  private AuthorizationHandler authorizationHandler = null;

  @Override
  public void init() throws ServletException {
    authorizationHandler = new PasswordAuthorizationHandler();
    System.out.println("Servlet Init");
    super.init();
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      authorizationHandler.handleAuthorization(req, resp);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
