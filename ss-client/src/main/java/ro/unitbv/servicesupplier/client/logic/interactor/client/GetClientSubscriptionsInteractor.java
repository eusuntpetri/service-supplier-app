package ro.unitbv.servicesupplier.client.logic.interactor.client;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.model.communication.request.client.ClientSubscriptionsRequest;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.common.SubscriptionsResponse;
import ro.unitbv.servicesupplier.model.dto.SubscriptionDto;

import java.util.List;

/**
 * Created by Petri on 16-May-16.
 */
public class GetClientSubscriptionsInteractor implements
      Interactor<ClientSubscriptionsRequest, SubscriptionsResponse> {

   private View view;

   public GetClientSubscriptionsInteractor(View view) {
      this.view = view;
   }

   @Override
   public void initiate(ClientSubscriptionsRequest request) {
      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(SubscriptionsResponse response) {
      List<SubscriptionDto> subscriptions = response.getSubscriptions();

      if (subscriptions == null || subscriptions.isEmpty()) {
         view.onNoSubscripionsFound();
         return;
      }

      view.onSubscriptionLoadSuccess(subscriptions);
   }

   @Override
   public void onFailure(FailureResponse response) {
      view.onSubscriptionLoadFailure(" Failed to retrieve subscriptions: " + response.getMessage());
   }


   public interface View {

      void onSubscriptionLoadSuccess(List<SubscriptionDto> subscriptions);

      void onNoSubscripionsFound();

      void onSubscriptionLoadFailure(String message);

   }
}
