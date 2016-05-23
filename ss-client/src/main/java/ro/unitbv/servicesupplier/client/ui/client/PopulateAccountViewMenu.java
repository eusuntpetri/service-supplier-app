package ro.unitbv.servicesupplier.client.ui.client;

import ro.unitbv.servicesupplier.client.logic.interactor.client.PopulateAccountInteractor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.client.ui.base.HasBackOption;
import ro.unitbv.servicesupplier.client.ui.base.ViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.client.AccountPopulationRequest;

/**
 * Created by Petri on 17-May-16.
 */
class PopulateAccountViewMenu extends ViewMenu implements PopulateAccountInteractor.View, HasBackOption {

   @Override
   protected String getViewTitle() {
      return "Populating account";
   }

   @Override
   public void proceed() {
      renderView();

      double amount = listenForDouble("Enter amount: ");

      printWorkingMessage();

      AccountPopulationRequest request =
            new AccountPopulationRequest(LoginSession.getClient().getId(), amount);

      new PopulateAccountInteractor(this).initiate(request);
   }

   @Override
   protected void renderView() {
      printMenuHeader();
   }

   @Override
   public void onPopulateSuccess(double currentBalance) {
      System.out.println(" Population successful! Current balance: " + currentBalance);
      goBack();
   }

   @Override
   public void onPopulateFailure(String message) {
      System.out.println(" Failed to add funds to your account: " + message);
      goBack();
   }

   @Override
   public void goBack() {
      new ClientAccountViewMenu().proceed();
   }
}
