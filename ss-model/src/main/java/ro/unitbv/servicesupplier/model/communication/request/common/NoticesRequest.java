package ro.unitbv.servicesupplier.model.communication.request.common;

import ro.unitbv.servicesupplier.model.communication.request.ServerRequest;

import java.io.Serializable;

/**
 * Created by Petri on 17-May-16.
 */
public class NoticesRequest implements ServerRequest, Serializable {

   private long accountId;

   public NoticesRequest(long accountId) {
      this.accountId = accountId;
   }

   public long getAccountId() {
      return accountId;
   }

   @Override
   public int getCode() {
      return Codes.GET_NOTICES;
   }
}
