package com.benjamin.oauth2.token.impl;

import com.benjamin.oauth2.token.IAuthTokenProvider;
import com.benjamin.oauth2.token.Token;
import sun.invoke.empty.Empty;

import java.util.Collection;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by benjamin on 9/4/14.
 */
public class SimpTokenProvider extends SimpAuthTokenGenerator implements IAuthTokenProvider {

  private static ConcurrentHashMap<String, Token> tokens = new ConcurrentHashMap<String, Token>();

  @Override
  public void initializer() {

  }

  @Override
  public boolean checkToken(Token token){

    return false;
  }

  @Override
  public void saveToken(Token token) {
    tokens.put(token.getValue(),token);
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
  }

  static class TokenCheckTask {
    static TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        Collection<Token> tokenCollection = tokens.values();
        Iterator<Token> tokenIterator = tokenCollection.iterator();
        while (tokenIterator.hasNext()){

        }
      }
    };
    static void checkTokenExpires(){
      Timer timer = new Timer();
      timer.scheduleAtFixedRate(timerTask,0,1000);
    }
  }
}
