oauth2.0
========

an simple implemention of oauth2

### The easy tutorial

First, create a PasswordValidator override validPassword

```
public class MyOauth2Login implements PasswordValidator {
  @Override
  public boolean validPassword(String username, String password) {
    if(username!=null && password !=null && username.equals("test") && password.equals("test")){
      return true;
    }else {
      return false;
    }
  }
}
```

Next you must declare class in oauth.properties like this:

```
password-validator.class=com.benjamin.todos.web.oauth2.MyOauth2Login
```

the default package of oauth.properties is /config/oauth.properties, if you want to use a special directory
you can config it in web.xml

```
<context-param>
    <param-name>oauthLocation</param-name>
    <param-value>/config/oauth.properties</param-value>
</context-param>
```

In your web.xml you can config like this:

```
<servlet>
    <servlet-name>oauth2servlet</servlet-name>
    <servlet-class>com.benjamin.oauth2.authorization.servlet.AuthorizationServlet</servlet-class>
  </servlet>

  <servlet-mapping>
    <servlet-name>oauth2servlet</servlet-name>
    <url-pattern>/oauth2/*</url-pattern>
  </servlet-mapping>

  <listener>
    <listener-class>com.benjamin.oauth2.OAuth2Initializer</listener-class>
  </listener>

  <filter>
    <filter-name>oauth2</filter-name>
    <filter-class>com.benjamin.oauth2.OAuthFilter</filter-class>
  </filter>

  <filter-mapping>
    <filter-name>oauth2</filter-name>
    <url-pattern>/api/*</url-pattern>
  </filter-mapping>
  ```
