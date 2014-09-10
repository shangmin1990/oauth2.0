package com.benjamin.oauth2.token;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by benjamin on 9/3/14.
 */
public class Token implements Comparator<Token>,Serializable{
  //此Token的唯一标识,与用户的标识信息相同,一般是 username 或者userId
  //为什么要有此字段.
//  private String identify;
//
//  public String getIdentify() {
//    return identify;
//  }
//
//  public void setIdentify(String identify) {
//    this.identify = identify;
//  }

  //Token 值
  private String value;
  // Token生成时间
  private long generatorTime;
  //有效期
  private long expires;
  //是否是临时token
  private boolean isAccessToken;
  //此token的刷新token
  private Token refreshToken;

  public boolean isAccessToken() {
    return isAccessToken;
  }

  public void setAccessToken(boolean isAccessToken) {
    this.isAccessToken = isAccessToken;
  }

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

  public long getExpires() {
    return expires;
  }

  public void setExpires(long expires) {
    this.expires = expires;
  }

  public Token getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(Token refreshToken) {
    this.refreshToken = refreshToken;
  }

  @Override
  public int compare(Token token1, Token token2) {
    return (int) (token1.getGeneratorTime() - token2.getGeneratorTime());
  }

  @Override
  public boolean equals(Object obj) {
    if(obj == null)
      return false;
    if(obj instanceof Token && this.getValue().equals(((Token) obj).getValue())){
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.getValue().hashCode();
  }

  @Override
  public String toString(){
    return this.getValue();
  }
}
