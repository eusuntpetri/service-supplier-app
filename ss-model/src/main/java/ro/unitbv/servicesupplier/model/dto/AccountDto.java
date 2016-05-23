package ro.unitbv.servicesupplier.model.dto;

import java.io.Serializable;

/**
 * Created by Petri on 15-May-16.
 */
public abstract class AccountDto implements Serializable {

   private long id;
   private AccountCredentialDto credentials;

   protected AccountDto(Builder builder) {
      this.id = builder.id;
      credentials = new AccountCredentialDto()
            .setUsername(builder.username)
            .setPassword(builder.password);
   }

   public long getId() {
      return id;
   }

   public AccountCredentialDto getCredentials() {
      return credentials;
   }

   @SuppressWarnings("unchecked")
   public abstract static class Builder<T extends Builder> {

      private long id;
      private String username;
      private String password;

      public T withId(long id) {
         this.id = id;
         return (T) this;
      }

      public T withUsername(String username) {
         this.username = username;
         return (T) this;
      }

      public T withPassword(String password) {
         this.password = password;
         return (T) this;
      }
   }

}
