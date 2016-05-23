package ro.unitbv.servicesupplier.client.logic.interactor.client;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.model.communication.request.client.SubscriptionRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.common.IdResponse;

/**
 * Created by Petri on 18-May-16.
 */
public class SubscribeToServiceInteractor implements
      Interactor<SubscriptionRequest, IdResponse> {

   private View view;

   public SubscribeToServiceInteractor(View view) {
      this.view = view;
   }

   @Override
   public void initiate(SubscriptionRequest request) {
      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(IdResponse response) {
      view.onSubscriptionSuccess(response.getId());
   }

   @Override
   public void onFailure(FailureResponse response) {

      if (response.getCode() == ServerResponse.Codes.ALREADY_SUBSCRIBED) {
         view.onSubscriptionFailure("You are already subscribed to this service.");
         return;
      }

      view.onSubscriptionFailure(response.getMessage());
   }


   public interface View {

      void onSubscriptionSuccess(long subscriptionId);

      void onSubscriptionFailure(String message);

   }
}
