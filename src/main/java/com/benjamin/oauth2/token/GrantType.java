package com.benjamin.oauth2.token;

/**
 * Created by benjamin on 9/4/14.
 */
public enum GrantType {
  //用户名密码授权模式
  PASSWORD,
  //授权码授权模式
  AUTHORIZATION_CODE,
  //隐式授权
  IMPLICIT,
  //客户端授权模式
  CLIENT
}
