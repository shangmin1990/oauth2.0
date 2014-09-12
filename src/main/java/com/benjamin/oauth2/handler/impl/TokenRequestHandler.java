package com.benjamin.oauth2.handler.impl;

import com.benjamin.oauth2.Constant;
import com.benjamin.oauth2.handler.IRequestHandler;
import com.benjamin.oauth2.token.Token;
import com.benjamin.oauth2.util.CodecUtil;
import com.benjamin.oauth2.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by benjamin on 9/11/14.
 */
public class TokenRequestHandler implements IRequestHandler, Constant {
  @Override
  public boolean handleRequest(HttpServletRequest request, HttpServletResponse response, Token token) throws Exception{
    String sortedParam = sortedParam(request, token);
    // 对比签名值
    //
    String clientHashValue = request.getParameter(TOKEN_MD5);
    String hashValue;
    //客户端无法获取token (ngnix 做代理,)对比token值
    if(clientHashValue == null){
      String tokenCookieValue = WebUtil.getCookieValue(request,TOKEN);
      return token.getValue().equals(tokenCookieValue);
    }else{
      if(sortedParam.isEmpty()){
        //
        hashValue = CodecUtil.md5(token.getValue());
      } else {
        hashValue = CodecUtil.md5(sortedParam);
      }
      if(hashValue.equals(clientHashValue)){
        return true;
      }
      return false;
    }
  }

  /**
   * 将请求参数按照英文首字母排序
   * example: a=b&ab=c&d=e
   * 客户端也要按照此排序规则对request做签名值,(参照sina的oauth实现(少许修改)，http://open.weibo.com/wiki/Oauth)
   * 然后对比md5值
   * @param request
   * @return
   */
  private String sortedParam(HttpServletRequest request, Token token){
    List<String> params = new ArrayList<String>();
    Enumeration<String> enumeration =  request.getParameterNames();
    while (enumeration.hasMoreElements()){
      params.add(enumeration.nextElement());
    }
    Collections.sort(params,String.CASE_INSENSITIVE_ORDER);
    StringBuilder stringBuilder = new StringBuilder();
    for(String paramName: params){
      if(!TOKEN_MD5.equals(paramName)&&!"username".equals(paramName)){
        stringBuilder.append(paramName);
        stringBuilder.append("=");
        stringBuilder.append(request.getParameter(paramName));
        stringBuilder.append("&");
      }
    }
    if(stringBuilder.toString().isEmpty()){
      return stringBuilder.toString();
    }else {
      stringBuilder.append("token=");
      stringBuilder.append(token.getValue());
    }
    return stringBuilder.toString();
  }
}
