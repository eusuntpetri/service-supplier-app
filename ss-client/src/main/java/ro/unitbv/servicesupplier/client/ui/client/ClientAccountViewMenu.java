package ro.unitbv.servicesupplier.client.ui.client;

import ro.unitbv.servicesupplier.client.logic.interactor.common.UpdateAccountInteractor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.client.ui.base.HasBackOption;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.common.AccountRequest;
import ro.unitbv.servicesupplier.model.dto.ClientAccountDto;

/**
 * Created by Petri on 17-May-16.
 */
class ClientAccountViewMenu extends OptionViewMenu implements UpdateAccountInteractor.View, HasBackOption {

   private static final int OP_POPULATE = 1;
   private static final int OP_EDIT = 2;

   private boolean upToDate;

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
            new AccountRequest(LoginSession.getClient().getId());

      new UpdateAccountInteractor(this).initiate(request);
   }

   private void resume() {
      super.proceed();
   }

   @Override
   protected void renderView() {
      ClientAccountDto client = LoginSession.getClient();

      printlnIndented("  Username: " + client.getCredentials().getUsername());
      printlnIndented("First name: " + client.getFirstName());
      printlnIndented(" Last name: " + client.getLastName());
      System.out.println();
      printlnDoubleIndented("Account balance: $" + client.getBalance() +
            (upToDate ? "" : " - OUTDATED"));
      System.out.println();

      super.renderView();
   }

   @Override
   protected int getNumOfOptions() {
      return 2;
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      switch (optionNum) {
         case OP_POPULATE:
            return "Populate your account";
         case OP_EDIT:
            return "Edit account details";
         default:
            return null;
      }
   }

   @Override
   protected void doActionForOption(int option) {
      switch (option) {
         case OP_POPULATE:
            new PopulateAccountViewMenu().proceed();
            return;
         case OP_EDIT:
            // new ClientEditAccountViewMenu().proceed();
            printlnDoubleIndented("In progress.");
            listenForOption();
      }
   }

   @Override
   public void onAccountUpdateSuccess() {
      upToDate = true;

      System.out.println(" Account updated successfully.");
      resume();
   }

   @Override
   public void onAccountUpdateFailure(String message) {
      upToDate = false;

      System.out.println(" Failed to update account details: " + message);
      System.out.println();
      printlnDoubleIndented("Potentially outdated cached details will be displayed!");

      resume();
   }

   @Override
   public void goBack() {
      new ClientMainViewMenu().proceed();
   }
}
