package com.benjamin.oauth2.authorization.impl;

import com.benjamin.oauth2.client.IClientManager;
import com.benjamin.oauth2.client.impl.ClientManager;
import com.benjamin.oauth2.token.Token;
import com.benjamin.oauth2.util.PropertiesUtil;
import com.benjamin.oauth2.util.WebUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by benjamin on 9/12/14.
 */
public class AuthorizationCodeHandler extends GrantTypeAuthorizationHandlerAdapter {

  private IClientManager<String> clientManager;

  public AuthorizationCodeHandler(){
    super();
    clientManager = new ClientManager();
  }
  @Override
  public void handleAuthCodeGrantType(HttpServletRequest request, HttpServletResponse response) {
//    boolean result = validPassword(request);
//    if(result){
//      //返回accessToken
//      Token token = tokenProvider.getAuthTokenGenerator().generateAccessToken();
//
//    }
    String username = WebUtil.getCookieValue(request, userCookieName);
    //未登录过啊 必须登录啊
    if(username == null){
      try {
        response.sendRedirect(request.getContextPath()+"/loginPage.html");
//        request.getRequestDispatcher("/loginPage.html").forward(request,response);
        return;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    String responseType = request.getParameter(RESPONSE_TYPE);
    String clientId = request.getParameter(CLIENT_ID);
    String redirect_uri = request.getParameter(REDIRECT_URI);
    if(responseType!= null && !responseType.isEmpty() && responseType.equals("code") && request.getPathInfo().indexOf("authorize") >= 0 ){
      String state = request.getParameter(STATE);
      if(clientManager.checkClientId(clientId)){
        Token token = tokenProvider.getAuthTokenGenerator().generateAccessToken();
        tokenProvider.saveToken(username, token);
//        req.getRequestDispatcher("access_token?code="+token.getValue()+"&redirect_uri="+redirect_uri+"&state="+state+"&grant_type=authorization_code").forward(req, resp);
        if(redirect_uri == null || redirect_uri.isEmpty()){
          redirect_uri = PropertiesUtil.getString(REDIRECT_URI);
        }
        StringBuilder stringBuilder = new StringBuilder(redirect_uri);
        stringBuilder.append("?code=");
        stringBuilder.append(token.getValue());
        stringBuilder.append("&client_id=");
        stringBuilder.append(clientId);
        if(state!=null && !state.isEmpty()){
          stringBuilder.append("&state=");
          stringBuilder.append(state);
        }
        try{
          response.sendRedirect(stringBuilder.toString());
        }catch (IOException e){
          e.printStackTrace();
        }
      }
      //直接获取token
    }else if(request.getPathInfo().indexOf("access_token") >= 0){
      if(clientManager.checkClientId(clientId)){
        String code = request.getParameter("code");
        if(code == null || code.isEmpty()){
          try {
            WebUtil.replyNoAccess(request, response, "No Request code found");
          } catch (IOException e) {
            e.printStackTrace();
          }
        }else{
          Token request_token = tokenProvider.getAccessToken(username);
          String value = request_token.getValue();
          if(value.equals(code)){
            Token token = tokenProvider.getAuthTokenGenerator().generateToken();
            tokenProvider.saveToken(username, token);
//            WebUtil.responseToken(request, response, token);
            Cookie tokenCookie = new Cookie("token",token.getValue());
            Token refresh_token = token.getRefreshToken();
            Cookie refreshTokenCookie = new Cookie("refreshToken",refresh_token.getValue());
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
            try {
              response.sendRedirect(redirect_uri);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
    super.handleAuthCodeGrantType(request, response);
  }
}
