package com.benjamin.oauth2;

import com.benjamin.oauth2.authorization.impl.ImplicitAuthorizationHandler;
import com.benjamin.oauth2.authorization.impl.PasswordAuthorizationHandler;
import com.benjamin.oauth2.util.PropertiesUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by benjamin on 9/3/14.
 */
public class OAuth2Initializer implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    String location = servletContextEvent.getServletContext().getInitParameter("oauthLocation");
//    String path = servletContextEvent.getServletContext().getRealPath(location);
//    PropertiesUtil.init(path);
    PropertiesUtil.init(location);
    System.out.println(location);
    //实例化AuthorizationHandler
    new ImplicitAuthorizationHandler();
    new PasswordAuthorizationHandler();
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {

  }
}
