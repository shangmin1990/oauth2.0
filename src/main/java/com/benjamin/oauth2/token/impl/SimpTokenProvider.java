package com.benjamin.oauth2.token.impl;

import com.benjamin.oauth2.token.IAuthTokenGenerator;
import com.benjamin.oauth2.token.IAuthTokenProvider;
import com.benjamin.oauth2.token.Token;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton
 * Created by benjamin on 9/4/14.
 */
public class SimpTokenProvider implements IAuthTokenProvider {

  private IAuthTokenGenerator authTokenGenerator = new SimpAuthTokenGenerator();

  /**
   * accessToken
   */
  private static ConcurrentHashMap<String, Token> accessTokens = new ConcurrentHashMap<String, Token>();
  /**
   * token
   */
  private static ConcurrentHashMap<String, Token> tokens = new ConcurrentHashMap<String, Token>();
  /**
   * refreshToken
   */
  private static ConcurrentHashMap<String, Token> refreshTokens = new ConcurrentHashMap<String, Token>();

  private SimpTokenProvider(){
    initializer();
  }

  public static  SimpTokenProvider simpTokenProvider = new SimpTokenProvider();

  public static SimpTokenProvider getInstance(){
    return simpTokenProvider;
  }

  @Override
  public void initializer() {
    TokenCheckTask.checkTokenExpires();
  }

  @Override
  public boolean checkToken(HttpServletRequest request, Token token){

    return false;
  }

  @Override
  public void saveToken(String key,Token token) {
    if(token.isAccessToken()){
      accessTokens.put(key,token);
    }else if(token.getRefreshToken() == null){
      refreshTokens.put(key, token);
    }else{
      tokens.put(key,token);
    }
  }

  @Override
  public Token getToken(String key) {
    Token token = tokens.get(key);
    return token;
  }

  @Override
  public Token getAccessToken(String key) {
    return accessTokens.get(key);
  }

  @Override
  public Token refreshToken(Token token) {

    return null;
  }

  @Override
  public void deleteToken(Token token){
    if(token.isAccessToken()){
      if(accessTokens.containsValue(token)){
        accessTokens.remove(token);
      }
    }else if(token.getRefreshToken() == null){
      if(refreshTokens.containsValue(token)){
        refreshTokens.remove(token);
      }
    }else {
      if(tokens.containsValue(token)){
        tokens.remove(token);
      }
    }
  }

  @Override
  public void destroy() {
    TokenCheckTask.timerTask.cancel();
  }

  public IAuthTokenGenerator getAuthTokenGenerator() {
    return authTokenGenerator;
  }

  public void setAuthTokenGenerator(IAuthTokenGenerator authTokenGenerator) {
    this.authTokenGenerator = authTokenGenerator;
  }

  static class TokenCheckTask {

    private static Timer timer = new Timer();
    private static TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        Collection<Token> tokenCollection = tokens.values();
        Collection<Token> accessTokenCollection = accessTokens.values();
        if(tokens.size() > 0){
          Iterator<Token> tokenIterator = tokenCollection.iterator();
          while (tokenIterator.hasNext()){
            Token token = tokenIterator.next();
            //token 已经过期了
            if(System.currentTimeMillis() - token.getGeneratorTime() > token.getExpires()){
              tokenIterator.remove();
              tokens.remove(token);
              System.out.println("remove Token value "+ token.getValue());
              System.out.println("还剩余"+tokens.size()+"个Token");
            }
          }
        }

        if(accessTokenCollection.size() > 0){
          Iterator<Token> tokenIterator = accessTokenCollection.iterator();
          while (tokenIterator.hasNext()){
            Token token = tokenIterator.next();
            //token 已经过期了
            if(System.currentTimeMillis() - token.getGeneratorTime() > token.getExpires()){
              tokenIterator.remove();
              accessTokenCollection.remove(token);
              System.out.println("remove Token value "+ token.getValue());
              System.out.println("还剩余"+accessTokenCollection.size()+"个AccessToken");
            }
          }
        }
      }
    };
    static void checkTokenExpires(){
      timer.scheduleAtFixedRate(timerTask,0,100);
    }
  }
}
