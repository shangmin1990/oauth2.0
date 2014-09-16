package com.benjamin.oauth2.authorization;


/**
 * Created by benjamin on 9/10/14.
 */
public interface PasswordValidator {
  /**
   * 用户名密码登录 需要用户自己实现
   * @param username
   * @param password
   * @return
   */
  boolean validPassword(String username, String password);
}
