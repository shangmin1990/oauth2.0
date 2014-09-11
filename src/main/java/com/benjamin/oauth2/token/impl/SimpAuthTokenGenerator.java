package com.benjamin.oauth2.token.impl;

import com.benjamin.oauth2.Constant;
import com.benjamin.oauth2.token.IAuthTokenGenerator;
import com.benjamin.oauth2.token.Token;
import com.benjamin.oauth2.util.PropertiesUtil;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created by benjamin on 9/3/14.
 */
public class SimpAuthTokenGenerator implements IAuthTokenGenerator, Constant {

  //默认配置，可以在oauth.properties中配置此项
  //Token有效期 (单位天)
  private long tokenExpires = 30;
  //默认配置，可以在oauth.properties中配置此项
  //AccessToken有效期 30分钟 (默认配置)
  private long accessTokenExpires = 30;
  //默认配置，可以在oauth.properties中配置此项
  //永不过期
  private long refreshTokenExpires = -1;

  public SimpAuthTokenGenerator(){
    try{
      //This is just a TEST
      //设置token的有效期为两分钟
//      long access = 2*60*1000;
      long access = Long.parseLong(PropertiesUtil.getString(EXPIRES, String.valueOf(tokenExpires)))*24*60*60*1000;
      long accessTokenValidTime = Long.parseLong(PropertiesUtil.getString(ACCESS_EXPIRES, String.valueOf(accessTokenExpires)))*60*1000;
      long refreshTokenExpires = Long.parseLong(PropertiesUtil.getString(REFRESH_EXPIRES, String.valueOf(this.refreshTokenExpires)))*24*60*60*1000;
//      int access = 30;
      this.tokenExpires = access;
      this.accessTokenExpires = accessTokenValidTime;
      this.refreshTokenExpires = refreshTokenExpires;
    }catch (Exception e){

    }
  }

  @Override
  public Token generateToken() {
    Token token = generateRefreshToken();
    Token refreshToken = generateRefreshToken();
    if(token != null){
      token.setExpires(tokenExpires);
      token.setRefreshToken(refreshToken);
    }
    return token;
  }

  @Override
  public Token generateAccessToken(){
    Token token =  generateRefreshToken();
    if(token != null){
      token.setExpires(accessTokenExpires);
      token.setAccessToken(true);
    }
    return token;
  }

  @Override
  public Token generateRefreshToken() {
    String uuid = UUID.randomUUID().toString();
    Token token;
    try{
      token = new Token();
      String value = Base64.encodeBase64String(uuid.getBytes("UTF-8"));
      token.setValue(value);
      token.setGeneratorTime(System.currentTimeMillis());
      token.setExpires(this.refreshTokenExpires);
      token.setAccessToken(false);
    }catch (UnsupportedEncodingException e){
      token = null;
    }
    return token;
  }
}
