package com.benjamin.oauth2.token;

/**
 * Created by benjamin on 9/3/14.
 */
public class Token {
  //Token 值
  private String value;
  // Token生成时间
  private long generatorTime;
  //Token 刷新时间
  private long refreshTime;
  //有效期
  private long access;

  public GrantType getTokenType() {
    return tokenType;
  }

  public void setTokenType(GrantType tokenType) {
    this.tokenType = tokenType;
  }

  //
  private GrantType tokenType;

  private Token refreshToken;

  public Token(){

  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public long getGeneratorTime() {
    return generatorTime;
  }

  public void setGeneratorTime(long generatorTime) {
    this.generatorTime = generatorTime;
  }

  public long getRefreshTime() {
    return refreshTime;
  }

  public void setRefreshTime(long refreshTime) {
    this.refreshTime = refreshTime;
  }

  public long getAccess() {
    return access;
  }

  public void setAccess(long access) {
    this.access = access;
  }
}
