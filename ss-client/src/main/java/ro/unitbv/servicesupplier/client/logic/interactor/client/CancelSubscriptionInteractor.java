package ro.unitbv.servicesupplier.client.logic.interactor.client;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.model.communication.request.client.SubscriptionCancelRequest;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;

/**
 * Created by Petri on 18-May-16.
 */
public class CancelSubscriptionInteractor implements
      Interactor<SubscriptionCancelRequest, SuccessResponse> {

   private View view;

   public CancelSubscriptionInteractor(View view) {
      this.view = view;
   }

   @Override
   public void initiate(SubscriptionCancelRequest request) {
      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(SuccessResponse response) {
      view.onSubscriptionCancelSuccess();
   }

   @Override
   public void onFailure(FailureResponse response) {
      view.onSubscriptionCancelFailure(response.getMessage());
   }


   public interface View {

      void onSubscriptionCancelSuccess();

      void onSubscriptionCancelFailure(String message);

   }
}
