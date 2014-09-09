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

  //Token有效期(单位毫秒)
  private long finalTokenValid = 30*24*60*60*1000;
  //AccessToken有效期 30分钟(单位毫秒)
  private long accessTokenValid = 30*60*1000;

  public SimpAuthTokenGenerator(){
    try{
      int access = Integer.parseInt(PropertiesUtil.getString(ACCESS));
//      int access = 30;
      this.finalTokenValid = access;
    }catch (Exception e){

    }
  }

  @Override
  public Token generateToken() {
    String uuid = UUID.randomUUID().toString();
    Token token;
    try{
      token = new Token();
      String value = Base64.encodeBase64String(uuid.getBytes("UTF-8"));
      token.setValue(value);
      token.setGeneratorTime(System.currentTimeMillis());
      token.setAccess(this.finalTokenValid);
    }catch (UnsupportedEncodingException e){
      token = null;
    }
    return token;
  }

  @Override
  public Token generateAccessToken(){
    Token token =  generateToken();
    token.setAccess(accessTokenValid);
    return token;
  }

}
