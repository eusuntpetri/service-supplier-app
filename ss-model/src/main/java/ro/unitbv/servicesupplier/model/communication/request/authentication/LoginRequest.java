package ro.unitbv.servicesupplier.model.communication.request.authentication;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;
import ro.unitbv.servicesupplier.model.dto.AccountCredentialDto;

import java.io.Serializable;

/**
 * Created by Petri on 14-May-16.
 */
public class LoginRequest implements ServerRequest, Serializable {

   private AccountCredentialDto credentials;

   public LoginRequest(AccountCredentialDto credentials) {
      this.credentials = credentials;
   }

   public AccountCredentialDto getCredentials() {
      return credentials;
   }

   @Override
   public int getCode() {
      return Codes.LOGIN;
   }
}
