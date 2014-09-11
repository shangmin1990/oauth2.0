package com.benjamin.oauth2.handler;

import com.benjamin.oauth2.token.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by benjamin on 9/11/14.
 */
public interface IRequestHandler {
  /**
   * handle
   * @param request
   * @param response
   */
  boolean handleRequest(HttpServletRequest request, HttpServletResponse response, Token token) throws Exception;
}
