package com.benjamin.oauth2.token;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by benjamin on 9/3/14.
 */
public interface IAuthTokenProvider {
  /**
   * AuthTokenProvider初始化 调用的方法
   */
  void initializer();

  /**
   * 检查token的合法性
   * @param key
   * @param tokenValue
   * @return
   */
  boolean checkToken(String key,String tokenValue);

  /**
   * 保存一个Token(可能是 AccessToken 与 refreshToken)
   * @param key
   * @param token
   */
  void saveToken(String key, Token token);

  /**
   * 获取token
   * @param key
   * @return
   */
  Token getToken(String key);

  /**
   * 获取accessToken
   * @param key
   * @return
   */
  Token getAccessToken(String key);

  /**
   * 通过 refreshToken获取 新的Token
   * @param token
   * @return
   */
  Token refreshToken(Token token);

  /**
   * 删除一个Token
   * @param token
   */
  void deleteToken(Token token);

  /**
   * 销毁方法
   */
  void destroy();
}
