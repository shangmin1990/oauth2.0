package com.benjamin.oauth2.util;

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
}
