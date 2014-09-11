package com.benjamin.oauth2;

/**
 * Created by benjamin on 9/3/14.
 */
public interface Constant {
  String REDIRECT_URL = "redirect-url";
  String REQUEST_URL = "request-url";
  String PARAMETER_NAME = "parameter-name";
  String EXPIRES = "expires";
  String ACCESS_EXPIRES = "access-expires";
  String REFRESH_EXPIRES = "refresh-expires";
  //认证失败请求地址
  String FAILURE_URL = "authentication-failure-url";
  //认证成功的请求地址
  String SUCCESS_URL = " default-target-url";
  String TOKEN = "token";
  String TOKEN_MD5 = "tokenMd5";
}
