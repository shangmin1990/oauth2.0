package com.benjamin.oauth2.handler;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by benjamin on 9/3/14.
 */
public interface RequestHandler {

  void handleRequest(ServletRequest request,ServletResponse response);

}
