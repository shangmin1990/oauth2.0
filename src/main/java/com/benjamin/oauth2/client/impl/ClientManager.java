package com.benjamin.oauth2.client.impl;

import com.benjamin.oauth2.client.IClientManager;

/**
 * Created by benjamin on 9/15/14.
 */
public class ClientManager implements IClientManager<String> {

  @Override
  public void registClient(String client) {

  }

  @Override
  public boolean checkClientId(String client) {
    if(client == null || client.isEmpty()){
      return false;
    }else {
      //TODO check clientId
      return client.equals("12345");
    }

  }

  @Override
  public void revokeClientId(String client) {

  }
}
