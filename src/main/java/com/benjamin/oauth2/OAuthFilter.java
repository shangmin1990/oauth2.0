package com.benjamin.oauth2;

import com.benjamin.oauth2.authorization.AuthorizationHandler;
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
//  private List<RequestHandler> requestHandlers = new ArrayList<RequestHandler>();
  private AuthorizationHandler requestHandler;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
//    requestHandlers.add(new ImplicitRequestHandler());
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    String path = httpServletRequest.getServletContext().getContextPath();
    System.out.println(path);
    String username = httpServletRequest.getParameter("username");
    String tokenValue = httpServletRequest.getParameter(PropertiesUtil.getString(PARAMETER_NAME));
//    String tokenValue = httpServletRequest.getParameter("token");
    Cookie[] cookies = httpServletRequest.getCookies();
    if(cookies!=null && cookies.length > 0){
      for(Cookie cookie: cookies){
        if(TOKEN.equals(cookie.getName())){
          tokenValue = cookie.getValue();
        }
      }
    }
    if(tokenValue == null || tokenValue.trim().isEmpty()){
      httpServletResponse.sendRedirect(path+"/login.html");
//      WebUtil.replyNoAccess(httpServletRequest, httpServletResponse);
    } else {
      Token token = simpTokenProvider.getToken(username);
      if(simpTokenProvider.checkToken(httpServletRequest,token)){
//        try{
//          requestHandler.handleAuthorization(servletRequest, servletResponse);
//        }catch (Exception e){
//
//        }
        filterChain.doFilter(servletRequest,servletResponse);
      }else{
        WebUtil.replyNoAccess(httpServletRequest, httpServletResponse);
      }
    }
  }

  @Override
  public void destroy() {

  }
}
