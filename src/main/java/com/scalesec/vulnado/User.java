package com.scalesec.vulnado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

public class User {
  private static final Logger LOGGER = Logger.getLogger(User.class.getName()); // Alterado por GFT AI Impact Bot
  private String id; // Alterado por GFT AI Impact Bot
  private String username; // Alterado por GFT AI Impact Bot
  private String hashedPassword; // Alterado por GFT AI Impact Bot

  public User(String id, String username, String hashedPassword) {
    this.id = id;
    this.username = username;
    this.hashedPassword = hashedPassword;
  }

  public String getId() { // Incluido por GFT AI Impact Bot
    return id;
  }

  public String getUsername() { // Incluido por GFT AI Impact Bot
    return username;
  }

  public String getHashedPassword() { // Incluido por GFT AI Impact Bot
    return hashedPassword;
  }

  public String token(String secret) {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
    return Jwts.builder().setSubject(this.username).signWith(key).compact(); // Alterado por GFT AI Impact Bot
  }

  public static void assertAuth(String secret, String token) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
      Jwts.parser()
        .setSigningKey(key)
        .parseClaimsJws(token);
    } catch(Exception e) {
      LOGGER.severe(e.getMessage()); // Alterado por GFT AI Impact Bot
      throw new Unauthorized(e.getMessage());
    }
  }

  public static User fetch(String un) {
    PreparedStatement stmt = null; // Alterado por GFT AI Impact Bot
    User user = null;
    try (Connection cxn = Postgres.connection()) { // Alterado por GFT AI Impact Bot
      stmt = cxn.prepareStatement("select * from users where username = ? limit 1"); // Alterado por GFT AI Impact Bot
      stmt.setString(1, un); // Incluido por GFT AI Impact Bot
      LOGGER.info("Opened database successfully"); // Alterado por GFT AI Impact Bot

      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        String userId = rs.getString("user_id"); // Alterado por GFT AI Impact Bot
        String username = rs.getString("username");
        String password = rs.getString("password");
        user = new User(userId, username, password);
      }
    } catch (Exception e) {
      LOGGER.severe(e.getClass().getName()+": "+e.getMessage()); // Alterado por GFT AI Impact Bot
    }
    return user; // Alterado por GFT AI Impact Bot
  }
}