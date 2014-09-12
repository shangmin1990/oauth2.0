package com.benjamin.oauth2.authorization.servlet.exception;

import com.sun.jndi.url.dns.dnsURLContext;

/**
 * Created by benjamin on 9/12/14.
 */
public class NoGrantTypeFoundException extends Exception {

  public NoGrantTypeFoundException(){
    super();
  }
  public NoGrantTypeFoundException(String message){
    super(message);
  }

  public NoGrantTypeFoundException(String message, Throwable throwable){
    super(message, throwable);
  }
}
