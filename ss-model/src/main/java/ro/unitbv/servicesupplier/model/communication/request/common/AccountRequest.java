package ro.unitbv.servicesupplier.model.communication.request.common;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;

import java.io.Serializable;

/**
 * Created by Petri on 19-May-16.
 */
public class AccountRequest implements ServerRequest, Serializable {

   private long accountId;

   public AccountRequest(long accountId) {
      this.accountId = accountId;
   }

   public long getAccountId() {
      return accountId;
   }

   @Override
   public int getCode() {
      return Codes.GET_ACCOUNT;
   }
}
