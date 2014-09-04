package com.benjamin.oauth2;

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
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {

  }
}
