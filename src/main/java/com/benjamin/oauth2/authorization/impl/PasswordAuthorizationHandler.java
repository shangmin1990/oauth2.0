package com.benjamin.oauth2.authorization.impl;

import com.benjamin.oauth2.token.Token;
import com.benjamin.oauth2.token.impl.SimpTokenProvider;
import com.benjamin.oauth2.util.WebUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
    if(username != null && password != null && !username.isEmpty() && !password.isEmpty()){
      boolean result = passwordValidator.validPassword(username,password);
      if(result){
        Token token =((SimpTokenProvider) tokenProvider).getAuthTokenGenerator().generateToken();
        Token access_token = ((SimpTokenProvider) tokenProvider).getAuthTokenGenerator().generateAccessToken();
        Token refresh_token = ((SimpTokenProvider) tokenProvider).getAuthTokenGenerator().generateRefreshToken();
        tokenProvider.saveToken(username, token);
        tokenProvider.saveToken(username, access_token);
        tokenProvider.saveToken(username, refresh_token);
        WebUtil.responseToken(request,response,token);
      }else{
        try{
          WebUtil.replyNoAccess((HttpServletRequest)request,(HttpServletResponse)response);
        }catch (Exception e){
          e.printStackTrace();
        }
      }
    }
    super.handlePasswordGrantType(request, response);
  }
}
