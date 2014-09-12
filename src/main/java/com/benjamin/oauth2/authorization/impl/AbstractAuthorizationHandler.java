package com.benjamin.oauth2.authorization.impl;

import com.benjamin.oauth2.Constant;
import com.benjamin.oauth2.authorization.AuthorizationHandler;
import com.benjamin.oauth2.authorization.servlet.exception.NoGrantTypeFoundException;
import com.benjamin.oauth2.token.GrantType;
import com.benjamin.oauth2.token.IAuthTokenProvider;
import com.benjamin.oauth2.token.Token;
import com.benjamin.oauth2.token.impl.SimpTokenProvider;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by benjamin on 9/4/14.
 * handle authorization request.
 * 对应oauth种的四种授权方式
 */
public abstract class AbstractAuthorizationHandler implements AuthorizationHandler, Constant{

  private static final String GRANT_TYPE = "grant_type";

  protected IAuthTokenProvider tokenProvider = null;

  protected static final String USERNAME = "username";

  protected static final String PASSWORD = "password";

  public AbstractAuthorizationHandler(){
    super();
    tokenProvider = SimpTokenProvider.getInstance();
  }

  public AbstractAuthorizationHandler(IAuthTokenProvider authTokenProvider){
    super();
    this.tokenProvider = authTokenProvider;
  }

  @Override
  public void handleAuthorization(ServletRequest request, ServletResponse response) throws Exception{
    GrantType grantType = Enum.valueOf(GrantType.class,request.getParameter(GRANT_TYPE).toUpperCase()) ;
    if (GrantType.PASSWORD == grantType){
      handlePasswordGrantType(request, response);
    } else if(GrantType.AUTHORIZATION_CODE == grantType){
      handleAuthCodeGrantType(request, response);
    } else if(GrantType.IMPLICIT == grantType){
      handleImplicitGrantType(request, response);
    } else if (GrantType.CLIENT == grantType){
      handleClientGrantType(request, response);
    } else{
      throw new NoGrantTypeFoundException( "Grant_type " + grantType +" not support");
    }
//    postAuthorization(request, response, token);
  }

//  private void postAuthorization(ServletRequest request, ServletResponse response, Token token) {
//    Enumeration enumeration = request.getParameterNames();
//    List<String> params = new ArrayList<String>();
//    while (enumeration.hasMoreElements()){
//      if (!TOKEN.equals(params))
//      params.add((String) enumeration.nextElement());
//    }
//    Collections.sort(params, String.CASE_INSENSITIVE_ORDER);
//    StringBuilder stringBuilder = new StringBuilder();
//    for(String paramName: params){
//      stringBuilder.append(paramName);
//      stringBuilder.append("=");
//      stringBuilder.append(request.getParameter(paramName));
//      stringBuilder.append("&");
//    }
//    String sortedParams = stringBuilder.toString();
//    System.out.println(sortedParams);
//  }


  /**
   * 用户名,密码模式验证.
   * 此模式要求客户端具有高度可信任性.
   * 例如知名公司开发的客户端应用与自己开发的客户端应用可使用此方法
   * @param request
   * @param response
   */
  public abstract void handlePasswordGrantType(ServletRequest request, ServletResponse response);

  /**
   * 授权码验证模式.
   * 此模式是最严谨,流程最全面的授权模式
   * @param request
   * @param response
   */
  public abstract void handleAuthCodeGrantType(ServletRequest request, ServletResponse response);

  /**
   * 隐式授权模式
   * @param request
   * @param response
   */
  public abstract void handleImplicitGrantType(ServletRequest request, ServletResponse response);

  /**
   * 客户端模式
   * @param request
   * @param response
   */
  public abstract void handleClientGrantType(ServletRequest request, ServletResponse response);

}
