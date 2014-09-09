package com.benjamin.oauth2.token.impl;

import com.benjamin.oauth2.token.IAuthTokenGenerator;
import com.benjamin.oauth2.token.IAuthTokenProvider;
import com.benjamin.oauth2.token.Token;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by benjamin on 9/4/14.
 */
public class SimpTokenProvider implements IAuthTokenProvider {

  private IAuthTokenGenerator authTokenGenerator = new SimpAuthTokenGenerator();

  private static ConcurrentHashMap<String, Token> tokens = new ConcurrentHashMap<String, Token>();

  @Override
  public void initializer() {
    TokenCheckTask.checkTokenExpires();
  }

  @Override
  public boolean checkToken(Token token){

    return false;
  }

  @Override
  public void saveToken(String key,Token token) {
    if(key == null)
      key = token.getValue();
    tokens.put(key,token);
  }

  @Override
  public Token getToken(String key) {
    Token token = tokens.get(key);
    return token;
  }

  @Override
  public Token refreshToken() {
    return null;
  }
  @Override
  public void deleteToken(Token token){
    String key = token.getValue();
    if(tokens.containsKey(key)){
      tokens.remove(key);
    }
  }

  @Override
  public void destroy() {
    TokenCheckTask.timerTask.cancel();
    tokens.clear();
    tokens = null;
  }

  static class TokenCheckTask {

    private static TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        Collection<Token> tokenCollection = tokens.values();
        if(tokens.size() > 0){
          Iterator<Token> tokenIterator = tokenCollection.iterator();
          while (tokenIterator.hasNext()){
            Token token = tokenIterator.next();
            //token 已经过期了
            if(System.currentTimeMillis() - token.getGeneratorTime() > token.getAccess()){
              tokenIterator.remove();
              tokens.remove(token.getValue());
            }
          }
        }
      }
    };
    static void checkTokenExpires(){
      Timer timer = new Timer();
      timer.scheduleAtFixedRate(timerTask,0,1000);
    }
  }
}
