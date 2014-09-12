package com.benjamin.oauth2.authorization.impl;

import com.benjamin.oauth2.token.Token;
import com.benjamin.oauth2.token.impl.SimpTokenProvider;
import com.benjamin.oauth2.util.WebUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * //TODO 控制请求次数,防止暴力破解
 * Created by benjamin on 9/9/14.
 */
public class PasswordAuthorizationHandler extends GrantTypeAuthorizationHandlerAdapter {
  @Override
  public void handlePasswordGrantType(ServletRequest request, ServletResponse response) {
    String username = request.getParameter(USERNAME);
    String password = request.getParameter(PASSWORD);
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    if(username != null && password != null && !username.isEmpty() && !password.isEmpty()){
      boolean result = passwordValidator.validPassword(username,password);
      if(result){
        Token token = tokenProvider.getAuthTokenGenerator().generateToken();
//        Token access_token =  tokenProvider.getAuthTokenGenerator().generateAccessToken();
        Token refresh_token = tokenProvider.getAuthTokenGenerator().generateRefreshToken();
        tokenProvider.saveToken(username, token);
//        tokenProvider.saveToken(username, access_token);
        tokenProvider.saveToken(username, refresh_token);
        if(WebUtil.isAjaxRequest((HttpServletRequest) request)){
          WebUtil.responseToken(request,response,token);
        }else{
          Cookie tokenCookie = new Cookie("token",token.getValue());
          Cookie refreshTokenCookie = new Cookie("refreshToken",refresh_token.getValue());
          //有效期
          tokenCookie.setMaxAge(-1);
          //refresh Token 有效期
          refreshTokenCookie.setMaxAge((int)(refresh_token.getExpires()/1000));
          httpServletResponse.addCookie(tokenCookie);
          httpServletResponse.addCookie(refreshTokenCookie);
          WebUtil.responseToken(request,response,token);
        }
      }else{
        try{
          WebUtil.replyNoAccess(httpServletRequest,httpServletResponse,"Username or password is wrong");
        }catch (Exception e){
          e.printStackTrace();
        }
      }
    }else{
      try{
        WebUtil.replyNoAccess(httpServletRequest,httpServletResponse,"Username or password is wrong");
      }catch (Exception e){
        e.printStackTrace();
      }
    }
    super.handlePasswordGrantType(request, response);
  }
}
