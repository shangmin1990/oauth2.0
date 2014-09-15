package com.benjamin.oauth2.authorization.servlet;

import com.benjamin.oauth2.authorization.AuthorizationHandler;
import com.benjamin.oauth2.authorization.impl.ImplicitAuthorizationHandler;
import com.benjamin.oauth2.authorization.impl.PasswordAuthorizationHandler;
import com.benjamin.oauth2.authorization.servlet.exception.NoGrantTypeFoundException;
import com.benjamin.oauth2.client.IClientManager;
import com.benjamin.oauth2.client.impl.ClientManager;
import com.benjamin.oauth2.token.IAuthTokenProvider;
import com.benjamin.oauth2.token.Token;
import com.benjamin.oauth2.token.impl.SimpTokenProvider;
import com.benjamin.oauth2.util.WebUtil;
import com.sun.corba.se.impl.oa.toa.TOA;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by benjamin on 9/9/14.
 */
public class AuthorizationServlet extends HttpServlet {

  private List<AuthorizationHandler> authorizationHandlers = new ArrayList<AuthorizationHandler>();

  private IClientManager<String> clientManager;

  private IAuthTokenProvider authTokenProvider;

  @Override
  public void init() throws ServletException {
    authorizationHandlers.add(new PasswordAuthorizationHandler());
    authorizationHandlers.add(new ImplicitAuthorizationHandler());
    clientManager = new ClientManager();
    authTokenProvider = SimpTokenProvider.getInstance();
    super.init();
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String responseType = req.getParameter("response_type");
    //code 换取access_Token
    if(responseType!= null && !responseType.isEmpty() && req.getPathInfo().indexOf("authorize")>=0){
      String clientId = req.getParameter("client_id");
      String state = req.getParameter("state");
      String redirect_uri = req.getParameter("redirect_uri");
      if(clientManager.checkClientId(clientId)){
        Token token = authTokenProvider.getAuthTokenGenerator().generateAccessToken();
        req.getRequestDispatcher("access_token?code="+token.getValue()+"&redirect_uri="+redirect_uri+"&state="+state+"&grant_type=authorization_code").forward(req, resp);
      }
    //直接获取token
    }else if(req.getPathInfo().indexOf("access_token") >=0 ){
      try {
        for(AuthorizationHandler authorizationHandler: authorizationHandlers){
          authorizationHandler.handleAuthorization(req, resp);
        }
      } catch (NoGrantTypeFoundException e){
        e.printStackTrace();
      } catch (Exception e) {
        resp.setStatus(500);
        resp.setCharacterEncoding(req.getCharacterEncoding());
        PrintWriter out = resp.getWriter();
        e.printStackTrace(out);
        out.flush();
        out.close();
      }
    }
  }
}
