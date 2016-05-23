package ro.unitbv.servicesupplier.client.ui.provider;

import ro.unitbv.servicesupplier.client.logic.interactor.common.UpdateAccountInteractor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.client.ui.base.HasBackOption;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.common.AccountRequest;
import ro.unitbv.servicesupplier.model.dto.ProviderAccountDto;

/**
 * Created by Petri on 17-May-16.
 */
class ProviderAccountViewMenu extends OptionViewMenu implements UpdateAccountInteractor.View, HasBackOption {

   @Override
   protected String getViewTitle() {
      return "Your account";
   }

   @Override
   public void proceed() {
      initialize();
   }

   private void initialize() {
      printWorkingMessage();

      AccountRequest request =
            new AccountRequest(LoginSession.getProvider().getId());

      new UpdateAccountInteractor(this).initiate(request);
   }

   private void resume() {
      super.proceed();
   }

   @Override
   protected void renderView() {
      ProviderAccountDto provider = LoginSession.getProvider();

      printlnIndented("Username: " + provider.getCredentials().getUsername());
      printlnIndented("    Name: " + provider.getName());
      System.out.println();
      super.renderView();
   }

   @Override
   protected int getNumOfOptions() {
      return 0;
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      return null;
   }

   @Override
   protected void doActionForOption(int option) {
      // No options available.
   }

   @Override
   public void onAccountUpdateSuccess() {
      System.out.println(" Account updated successfully.");
      resume();
   }

   @Override
   public void onAccountUpdateFailure(String message) {
      System.out.println(message);
      resume();
   }

   @Override
   public void goBack() {
      new ProviderMainViewMenu().proceed();
   }
}
