package ro.unitbv.servicesupplier.client.logic.interactor.client;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.logic.interactor.base.Interactor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.model.communication.request.client.AccountPopulationRequest;
import ro.unitbv.servicesupplier.model.communication.response.failure.FailureResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;

/**
 * Created by Petri on 17-May-16.
 */
public class PopulateAccountInteractor implements
      Interactor<AccountPopulationRequest, SuccessResponse> {

   private View view;
   private double increaseAmount;

   public PopulateAccountInteractor(View view) {
      this.view = view;
   }


   @Override
   public void initiate(AccountPopulationRequest request) {
      this.increaseAmount = request.getAmount();

      ServiceSupplierClient.getInstance().sendRequest(request, this);
   }

   @Override
   public void onSuccess(SuccessResponse response) {
      double currentBalance = LoginSession.getClient().getBalance() + increaseAmount;

      view.onPopulateSuccess(currentBalance);
   }

   @Override
   public void onFailure(FailureResponse response) {
      view.onPopulateFailure(response.getMessage());
   }


   public interface View {

      void onPopulateSuccess(double currentBalance);

      void onPopulateFailure(String message);

   }
}
