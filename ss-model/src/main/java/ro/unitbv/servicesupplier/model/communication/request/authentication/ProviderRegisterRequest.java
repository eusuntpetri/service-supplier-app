package ro.unitbv.servicesupplier.model.communication.request.authentication;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;
import ro.unitbv.servicesupplier.model.dto.ProviderAccountDto;

import java.io.Serializable;

/**
 * Created by Petri on 15-May-16.
 */
public class ProviderRegisterRequest implements ServerRequest, Serializable {

   private ProviderAccountDto accountDetails;

   public ProviderRegisterRequest(ProviderAccountDto accountDetails) {
      this.accountDetails = accountDetails;
   }

   public ProviderAccountDto getAccountDetails() {
      return accountDetails;
   }

   @Override
   public int getCode() {
      return Codes.REGISTER_PROVIDER;
   }
}
