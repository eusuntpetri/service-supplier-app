package ro.unitbv.servicesupplier.client.logic.interactor.client;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.model.communication.request.client.BillPaymentRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;

/**
 * Created by Petri on 17-May-16.
 */
public class BillPaymentInteractor implements
      Interactor<BillPaymentRequest, SuccessResponse> {

   private View view;

   public BillPaymentInteractor(View view) {
      this.view = view;
   }

   @Override
   public void initiate(BillPaymentRequest request) {
      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(SuccessResponse response) {
      view.onBillPaymentSuccess();
   }

   @Override
   public void onFailure(FailureResponse response) {

      if (response.getCode() == ServerResponse.Codes.BALANCE_LOW) {
         view.onBillPaymentFailure("Your account balance is too low.");
         return;
      }

      view.onBillPaymentFailure(response.getMessage());
   }


   public interface View {

      void onBillPaymentSuccess();

      void onBillPaymentFailure(String message);

   }

}
