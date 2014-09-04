package com.benjamin.oauth2.token;

/**
 * Created by benjamin on 9/3/14.
 */
public interface IAuthTokenGenerator {
  /**
   * 生成AccessToken
   * @return
   */
  Token generateAccessToken();

  /**
   * 生成永久Token
   * @return
   */
  Token generateToken();

}
