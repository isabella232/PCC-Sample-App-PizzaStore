package io.pivotal.cloudcache.app;

import java.io.Serializable;
import java.security.Principal;
import java.util.Properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.gemfire.config.annotation.support.AbstractAuthInitialize;
import org.springframework.data.gemfire.util.PropertiesBuilder;
import org.springframework.util.Assert;

import org.apache.geode.security.ResourcePermission;

public class CloudcacheClientAuthInitialize extends AbstractAuthInitialize {
//  protected static final User ANALYST = User.newUser("analyst").with("p@55w0rd");
//  protected static final User SCIENTIST = User.newUser("scientist").with("w0rk!ng4u");
  protected static final String SECURITY_PASSWORD_PROPERTY = "security-password";
  protected static final String SECURITY_USERNAME_PROPERTY = "security-username";
  protected static final String SECURITY_TOKEN_PROPERTY = "security-token";

  private final User user;

  /* (non-Javadoc) */
  public static CloudcacheClientAuthInitialize create() {
//    return new CloudcacheClientAuthInitialize(RUN_COUNT.incrementAndGet() < 2 ? SCIENTIST : ANALYST);
    System.out.println("DEBUG: CloudcacheClientAuthInitialize.create()");
    return new CloudcacheClientAuthInitialize(User.newUser("i6S8C25gpnsbu3LM0qUA").with("YIhpZFDXBoYqlUU3KX7Lg"));
  }

  /* (non-Javadoc) */
  public CloudcacheClientAuthInitialize(User user) {
    System.out.println(String.format("DEBUG: CloudcacheClientAuthInitialize(%s)", user.getName()));
    Assert.notNull(user, "User cannot be null");
    this.user = user;
  }

  /* (non-Javadoc) */
  @Override
  protected Properties doGetCredentials(Properties securityProperties) {

    User user = getUser();
    System.out.println(String.format("DEBUG: CloudcacheClientAuthInitialize.doGetCredentials: user = %s", user.getName()));

    return new PropertiesBuilder()
        .setProperty(SECURITY_USERNAME_PROPERTY, "")
        .setProperty(SECURITY_PASSWORD_PROPERTY, "")
        .setProperty(SECURITY_TOKEN_PROPERTY, "Bearer bar")
        .build();
  }

  /* (non-Javadoc) */
  protected User getUser() {
    return this.user;
  }

  /**
   * @inheritDoc
   */
  @Override
  public String toString() {

    User user = getUser();

    return String.format("%1$s:%2$s", user.getName(), user.getCredentials());
  }

  @Getter
  @EqualsAndHashCode(of = { "name", "credentials" })
  public static class User implements /*Iterable<Role>,*/ Principal, Serializable {

    private String name;
    private String credentials;

    public String getCredentials() {
      return credentials;
    }

    public String getName() {
      return name;
    }

    public static User newUser(String name) {
      User user = new User();
      user.name = name;
      return user;
    }

    public boolean hasPermission(ResourcePermission permission) {
      return true;
    }

    public User with(String credentials) {

      this.credentials = credentials;

      return this;
    }
  }


}

