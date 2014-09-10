package com.benjamin.oauth2.util;

import com.benjamin.oauth2.token.Token;
import net.sf.json.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by benjamin on 9/3/14.
 */
public class WebUtil {

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
}
