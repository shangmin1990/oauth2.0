package com.benjamin.oauth2.authorization.impl;

import com.benjamin.oauth2.token.Token;
import com.benjamin.oauth2.token.impl.SimpTokenProvider;
import com.benjamin.oauth2.util.PropertiesUtil;
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
  public void handlePasswordGrantType(HttpServletRequest request, HttpServletResponse response) {
    String username = request.getParameter(USERNAME);
    if(username == null){
      try{
        WebUtil.replyNoAccess(request,response,"Username or password is wrong");
        return;
      }catch (Exception e){
        e.printStackTrace();
        return;
      }
    }
//    String password = request.getParameter(PASSWORD);

    boolean result = validPassword(request);
    if(result){
      Token token = tokenProvider.getAuthTokenGenerator().generateToken();
//        Token access_token =  tokenProvider.getAuthTokenGenerator().generateAccessToken();
//      Token refresh_token = tokenProvider.getAuthTokenGenerator().generateRefreshToken();
      Token refresh_token = token.getRefreshToken();
      tokenProvider.saveToken(username, token);
//        tokenProvider.saveToken(username, access_token);
      tokenProvider.saveToken(username, refresh_token);
      if(WebUtil.isAjaxRequest(request)){
        WebUtil.responseToken(request,response,token);
      }else{
        Cookie tokenCookie = new Cookie("token",token.getValue());
        Cookie refreshTokenCookie = new Cookie("refreshToken",refresh_token.getValue());
        Cookie userCookie = new Cookie(PropertiesUtil.getString(USER_COOKIE_NAME, "user"), username);
        userCookie.setMaxAge(30*24*3600);
        userCookie.setPath(request.getContextPath());
//        tokenCookie.setDomain(request.getRemoteHost());
        tokenCookie.setPath(request.getContextPath());
//        refreshTokenCookie.setDomain(request.getRemoteHost());
        refreshTokenCookie.setPath(request.getContextPath());
        //有效期
        tokenCookie.setMaxAge(-1);
        //refresh Token 有效期
        refreshTokenCookie.setMaxAge(30*24*3600);
        response.addCookie(tokenCookie);
        response.addCookie(refreshTokenCookie);
        response.addCookie(userCookie);
        WebUtil.responseToken(request,response,token);
      }
    }else{
      try{
        WebUtil.replyNoAccess(request,response,"Username or password is wrong");
      }catch (Exception e){
        e.printStackTrace();
      }
    }
//    if(username != null && password != null && !username.isEmpty() && !password.isEmpty()){
//
//    }else{
//      try{
//        WebUtil.replyNoAccess(httpServletRequest,httpServletResponse,"Username or password is wrong");
//      }catch (Exception e){
//        e.printStackTrace();
//      }
//    }
    super.handlePasswordGrantType(request, response);
  }
}
