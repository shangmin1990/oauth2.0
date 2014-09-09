package com.benjamin.oauth2.token;

/**
 * Created by benjamin on 9/3/14.
 */
public interface IAuthTokenProvider {
  //initial
  void initializer();
  //check Token
  boolean checkToken(Token token);
  //
  void saveToken(String key,Token token);
  //
  Token getToken(String key);
  //
  Token refreshToken();
  //
  void deleteToken(Token token);
  //
  void destroy();
}
