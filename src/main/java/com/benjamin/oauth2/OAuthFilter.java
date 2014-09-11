package com.benjamin.oauth2;

import com.benjamin.oauth2.handler.IRequestHandler;
import com.benjamin.oauth2.handler.impl.TokenRequestHandler;
import com.benjamin.oauth2.token.Token;
import com.benjamin.oauth2.token.impl.SimpTokenProvider;
import com.benjamin.oauth2.util.PropertiesUtil;
import com.benjamin.oauth2.util.WebUtil;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by benjamin on 9/3/14.
 */
public class OAuthFilter implements Filter, Constant{

  private static final String TOKEN = "token";
  private SimpTokenProvider simpTokenProvider = SimpTokenProvider.getInstance();
  private IRequestHandler requestHandler;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
//    requestHandlers.add(new ImplicitRequestHandler());
    requestHandler = new TokenRequestHandler();
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    String path = httpServletRequest.getServletContext().getContextPath();
    String username = httpServletRequest.getParameter("username");
    String tokenValue = httpServletRequest.getParameter(PropertiesUtil.getString(PARAMETER_NAME));
//    String tokenValue = httpServletRequest.getParameter("token");
    Cookie[] cookies = httpServletRequest.getCookies();
    if(cookies!=null && cookies.length > 0){
      for(Cookie cookie: cookies){
        if(TOKEN.equals(cookie.getName())){
          tokenValue = cookie.getValue();
        }
        if("username".equals(cookie.getName())){
          username = cookie.getValue();
        }
      }
    }
    if(tokenValue == null || tokenValue.trim().isEmpty()){
      if(WebUtil.isAjaxRequest(httpServletRequest)){
         WebUtil.replyNoAccess(httpServletRequest, httpServletResponse);
      }else{
        String schema = httpServletRequest.getScheme();
        String localAddr  = httpServletRequest.getLocalAddr();
        int port = httpServletRequest.getLocalPort();
        String redirectUrl = schema+"://"+localAddr+":"+port+path+"/login.html";
        httpServletResponse.sendRedirect(redirectUrl);
      }
//      WebUtil.replyNoAccess(httpServletRequest, httpServletResponse);
    } else {
      Token token = simpTokenProvider.getToken(username);
      if(token != null){
        try{
          boolean result = requestHandler.handleRequest(httpServletRequest,httpServletResponse,token);
          if(result){
            filterChain.doFilter(servletRequest,servletResponse);
          }else {
            //Token 不对
          }
        }catch (Exception e){
          e.printStackTrace();
        }
      }else{
        //TODO token 过期了,需要用refreshToken来获取新的token
        WebUtil.replyNoAccess(httpServletRequest, httpServletResponse);
      }
    }
  }

  @Override
  public void destroy() {

  }
}
