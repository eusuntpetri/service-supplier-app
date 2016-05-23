package ro.unitbv.servicesupplier.model.communication.response.success.account;

import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;
import ro.unitbv.servicesupplier.model.dto.AccountDto;

import java.io.Serializable;

/**
 * Created by Petri on 16-May-16.
 */
public class AccountResponse<T extends AccountDto> extends SuccessResponse implements Serializable {

   private T accountDetails;

   public AccountResponse(T accountDetails) {
      super("Operation successful.");
      this.accountDetails = accountDetails;
   }

   public T getAccountDetails() {
      return accountDetails;
   }

}
