package com.benjamin.oauth2.authorization.impl;

import com.benjamin.oauth2.authorization.PasswordValidator;
import com.benjamin.oauth2.token.IAuthTokenProvider;
import com.benjamin.oauth2.token.impl.SimpTokenProvider;
import com.benjamin.oauth2.util.PropertiesUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by benjamin on 9/4/14.
 * 模板方法
 */
public class GrantTypeAuthorizationHandlerAdapter extends AbstractAuthorizationHandler {

  private static final String PASSWORD_VALIDATOR = "password-validator";

  private static final String TEST = "test";

  protected PasswordValidator passwordValidator;

  public GrantTypeAuthorizationHandlerAdapter(){
    findPasswordValidator();
  }

  @Override
  public void handlePasswordGrantType(ServletRequest request, ServletResponse response) {

  }

  @Override
  public void handleAuthCodeGrantType(ServletRequest request, ServletResponse response) {

  }

  @Override
  public void handleImplicitGrantType(ServletRequest request, ServletResponse response) {

  }

  @Override
  public void handleClientGrantType(ServletRequest request, ServletResponse response) {

  }
  private void findPasswordValidator(){
    String validator = PropertiesUtil.getString(PASSWORD_VALIDATOR);
    try{
      passwordValidator = (PasswordValidator) Class.forName(validator).newInstance();
      String name = PasswordValidator.class.getName();
      if(passwordValidator.getClass().getInterfaces().length == 0){
        throw new Exception("实现类必须实现 "+ name + " 接口");
      }

      if(!passwordValidator.getClass().getInterfaces()[0].getName().equals(name)){
        throw new Exception("实现类必须实现 "+ name + " 接口");
      }

    }catch (ClassNotFoundException e){
      e.printStackTrace();
    }catch (IllegalAccessException e){
      e.printStackTrace();
    }catch (InstantiationException e){
      e.printStackTrace();
    }catch (ClassCastException e){
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
