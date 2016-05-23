package ro.unitbv.servicesupplier.model.communication.response.success.account;

import ro.unitbv.servicesupplier.model.dto.ProviderAccountDto;

import java.io.Serializable;

/**
 * Created by Petri on 16-May-16.
 */
public class ProviderAccountResponse extends AccountResponse<ProviderAccountDto> implements Serializable {

   public ProviderAccountResponse(ProviderAccountDto accountDetails) {
      super(accountDetails);
   }

   public ProviderAccountDto getAccountDetails() {
      return super.getAccountDetails();
   }
}
