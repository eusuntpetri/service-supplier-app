package ro.unitbv.servicesupplier.model.dto;

import java.io.Serializable;

/**
 * Created by Petri on 15-May-16.
 */
public class AccountCredentialDto implements Serializable {

   private String username;
   private String password;

   public String getUsername() {
      return username;
   }

   public AccountCredentialDto setUsername(String username) {
      this.username = username;
      return this;
   }

   public String getPassword() {
      return password;
   }

   public AccountCredentialDto setPassword(String password) {
      this.password = password;
      return this;
   }

}
