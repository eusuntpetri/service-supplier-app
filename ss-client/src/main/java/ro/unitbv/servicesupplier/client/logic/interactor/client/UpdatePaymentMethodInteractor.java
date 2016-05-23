package ro.unitbv.servicesupplier.client.logic.interactor.client;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.model.communication.request.client.PaymentMethodUpdateRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;

/**
 * Created by Petri on 18-May-16.
 */
public class UpdatePaymentMethodInteractor implements
      Interactor<PaymentMethodUpdateRequest, SuccessResponse> {

   private View view;
   private boolean automationRequested;

   public UpdatePaymentMethodInteractor(View view) {
      this.view = view;
   }

   @Override
   public void initiate(PaymentMethodUpdateRequest request) {
      this.automationRequested = request.isSolicitingAutomation();

      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(SuccessResponse response) {
      view.onPaymentMethodUpdateSuccess(automationRequested);
   }

   @Override
   public void onFailure(FailureResponse response) {

      if (response.getCode() == ServerResponse.Codes.SAME_PAY_METHOD_VALUE) {
         view.onPaymentMethodUpdateFailure(
               "Payment method for that subscription was already set to " +
                     (automationRequested ? "AUTOMATIC" : "MANUAL") + ".");
         return;
      }

      view.onPaymentMethodUpdateFailure(response.getMessage());
   }


   public interface View {

      void onPaymentMethodUpdateSuccess(boolean wasAutomated);

      void onPaymentMethodUpdateFailure(String message);

   }
}
