package ro.unitbv.servicesupplier.model.communication.request.authentication;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;
import ro.unitbv.servicesupplier.model.dto.ClientAccountDto;

import java.io.Serializable;

/**
 * Created by Petri on 15-May-16.
 */
public class ClientRegisterRequest implements ServerRequest, Serializable {

   private ClientAccountDto accountDetails;

   public ClientRegisterRequest(ClientAccountDto accountDetails) {
      this.accountDetails = accountDetails;
   }

   public ClientAccountDto getAccountDetails() {
      return accountDetails;
   }

   @Override
   public int getCode() {
      return Codes.REGISTER_CLIENT;
   }
}
