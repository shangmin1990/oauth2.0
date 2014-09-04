package com.benjamin.oauth2;

import com.benjamin.oauth2.handler.RequestHandler;
import com.benjamin.oauth2.handler.impl.ImplicitRequestHandler;
import com.benjamin.oauth2.token.Token;
import com.benjamin.oauth2.token.impl.SimpTokenProvider;
import com.benjamin.oauth2.util.PropertiesUtil;
import com.benjamin.oauth2.util.WebUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by benjamin on 9/3/14.
 */
public class OAuthFilter implements Filter, Constant{

  private SimpTokenProvider simpTokenProvider = new SimpTokenProvider();
  private List<RequestHandler> requestHandlers = new ArrayList<RequestHandler>();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    requestHandlers.add(new ImplicitRequestHandler());
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    String tokenValue = httpServletRequest.getParameter(PropertiesUtil.getString(PARAMETER_NAME));
//    String tokenValue = httpServletRequest.getParameter("token");
    if(tokenValue == null || tokenValue.trim().isEmpty()){
      WebUtil.replyNoAccess(httpServletRequest, httpServletResponse);
    } else {
      Token token = simpTokenProvider.getToken(tokenValue);
      if(simpTokenProvider.checkToken(token)){
        for(RequestHandler requestHandler : requestHandlers){
          requestHandler.handleRequest(servletRequest,servletResponse);
        }
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
