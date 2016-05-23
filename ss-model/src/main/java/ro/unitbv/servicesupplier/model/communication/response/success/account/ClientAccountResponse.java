package ro.unitbv.servicesupplier.model.communication.response.success.account;

import ro.unitbv.servicesupplier.model.dto.ClientAccountDto;

import java.io.Serializable;

/**
 * Created by Petri on 16-May-16.
 */
public class ClientAccountResponse extends AccountResponse<ClientAccountDto> implements Serializable {

   public ClientAccountResponse(ClientAccountDto accountDetails) {
      super(accountDetails);
   }

   public ClientAccountDto getAccountDetails() {
      return super.getAccountDetails();
   }
}
