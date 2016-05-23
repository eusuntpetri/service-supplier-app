package ro.unitbv.servicesupplier.model.communication.response.success.common;

import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;
import ro.unitbv.servicesupplier.model.dto.SubscriptionDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Petri on 16-May-16.
 */
public class SubscriptionsResponse extends SuccessResponse implements Serializable {

   private List<SubscriptionDto> subscriptions;

   public SubscriptionsResponse(List<SubscriptionDto> subscriptions) {
      super("Subscriptions retrieved successfully.");
      this.subscriptions = subscriptions;
   }

   public List<SubscriptionDto> getSubscriptions() {
      return subscriptions;
   }
}
