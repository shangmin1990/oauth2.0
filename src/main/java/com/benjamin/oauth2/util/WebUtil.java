package com.benjamin.oauth2.util;

import com.benjamin.oauth2.token.Token;
import net.sf.json.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by benjamin on 9/3/14.
 */
public class WebUtil {

  private static final String AJAX_HEADER = "X-Requested-With";

  private static final String XMLHTTPREQUEST = "xmlhttprequest";

  private WebUtil(){

  }

  public static void replyNoAccess(HttpServletRequest request,HttpServletResponse response) throws IOException {
    String encoding = request.getCharacterEncoding();
    response.setCharacterEncoding(encoding);
    response.setStatus(401);
    PrintWriter out = response.getWriter();
    out.println("No Access");
    out.flush();
    out.close();
  }

  public static void responseToken(ServletRequest request, ServletResponse servletResponse, Token token){
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    String encoding = httpServletRequest.getCharacterEncoding();
    httpServletResponse.setContentType("application/json;charset=UTF-8");
    httpServletResponse.setHeader("Cache-Control","no-store");
    httpServletResponse.setHeader("Pragma","no-cache");
    httpServletResponse.setCharacterEncoding(encoding);
    PrintWriter out = null;
    try{
      out = httpServletResponse.getWriter();
      JSONObject jsonObject = JSONObject.fromObject(token);
      String result = jsonObject.toString();
      out.println(result);
    }catch (IOException e){
      e.printStackTrace();
    }finally {
      if(out!=null){
        out.flush();
        out.close();
      }
    }
  }

  public static boolean isAjaxRequest(HttpServletRequest request){
    String requestType = request.getHeader(AJAX_HEADER);
    return requestType != null && requestType.toLowerCase().equals(XMLHTTPREQUEST);
  }
}
