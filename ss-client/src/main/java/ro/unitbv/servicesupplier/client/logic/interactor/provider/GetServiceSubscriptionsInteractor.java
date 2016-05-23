package ro.unitbv.servicesupplier.client.logic.interactor.provider;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.model.communication.request.provider.ServiceSubscriptionsRequest;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.common.SubscriptionsResponse;
import ro.unitbv.servicesupplier.model.dto.SubscriptionDto;

import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class GetServiceSubscriptionsInteractor implements
      Interactor<ServiceSubscriptionsRequest, SubscriptionsResponse> {

   private View view;

   public GetServiceSubscriptionsInteractor(View view) {
      this.view = view;
   }

   @Override
   public void initiate(ServiceSubscriptionsRequest request) {
      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(SubscriptionsResponse response) {
      List<SubscriptionDto> subscriptionDetails = response.getSubscriptions();

      if (subscriptionDetails == null || subscriptionDetails.isEmpty()) {
         view.onNoSubscriptionsFound();
         return;
      }

      view.onSubscriptionsLoadSuccess(subscriptionDetails);
   }

   @Override
   public void onFailure(FailureResponse response) {
      view.onSubscriptionsLoadFailure(response.getMessage());
   }


   public interface View {

      void onSubscriptionsLoadSuccess(List<SubscriptionDto> subscriptions);

      void onNoSubscriptionsFound();

      void onSubscriptionsLoadFailure(String message);

   }
}
