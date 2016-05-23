package ro.unitbv.servicesupplier.client.ui.client;

import ro.unitbv.servicesupplier.client.logic.interactor.client.GetClientBillsInteractor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.client.ui.base.HasBackOption;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.client.ClientBillsRequest;
import ro.unitbv.servicesupplier.model.dto.BillDto;

import java.util.Collections;
import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
class ClientBillsViewMenu extends OptionViewMenu implements GetClientBillsInteractor.View, HasBackOption {

   private List<BillDto> clientBills;
   private int billType;

   @Override
   protected String getViewTitle() {
      return "Your " + billType() + "bills";
   }

   private String billType() {
      return billType == BillChoice.OP_ALL
            ? "" : (billType == BillChoice.OP_PAID
            ? "paid "
            : "unpaid ");
   }

   @Override
   public void proceed() {
      if (billType != 0) {
         onBillTypeChosen(billType);
         return;
      }
      new BillChoice(this).proceed();
      // Wait for choice to call onBillTypeChosen().
   }

   private void onBillTypeChosen(int billType) {
      this.billType = billType;
      initialize();
   }

   private void initialize() {
      if (clientBills != null) {
         resume();
         return;
      }

      printWorkingMessage();

      Boolean choosePaidOnly = null;
      if (billType == BillChoice.OP_PAID)
         choosePaidOnly = true;
      else if (billType == BillChoice.OP_UNPAID)
         choosePaidOnly = false;

      ClientBillsRequest request =
            new ClientBillsRequest(LoginSession.getClient().getId(), choosePaidOnly);

      new GetClientBillsInteractor(this).initiate(request);
   }

   private void resume() {
      if (clientBills.isEmpty()) {
         System.out.println(" You currently have no " + billType() + "bills.");
         goBack();
         return;
      }

      System.out.println();
      super.proceed();
   }

   @Override
   protected int getNumOfOptions() {
      return clientBills.size();
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      optionNum--;

      return clientBills.get(optionNum).toString();
   }

   @Override
   protected void doActionForOption(int option) {
      option--;

      BillDto bill = clientBills.get(option);

      if (bill.isPaid()) {
         printlnDoubleIndented("No action available.");
         listenForOption();
         return;
      }

      new BillViewMenu(this, bill,
            billType == BillChoice.OP_UNPAID ? clientBills : null)
            .proceed();
   }

   @Override
   public void onBillsLoadSuccess(List<BillDto> bills) {
      this.clientBills = bills;
      resume();
   }

   @Override
   public void onNoBillsFound() {
      this.clientBills = Collections.emptyList();
      resume();
   }

   @Override
   public void onBillsLoadFailure(String message) {
      System.out.println(" Failed to retrieve bills: " + message);
      goBack();
   }

   @Override
   public void goBack() {
      new ClientMainViewMenu().proceed();
   }


   private class BillChoice extends OptionViewMenu implements HasBackOption {

      private static final int OP_PAID = 1;
      private static final int OP_UNPAID = 2;
      private static final int OP_ALL = 3;

      ClientBillsViewMenu parentMenu;

      public BillChoice(ClientBillsViewMenu parentMenu) {
         this.parentMenu = parentMenu;
      }

      @Override
      protected String getViewTitle() {
         return "Which bills would you like to view?";
      }

      @Override
      protected int getNumOfOptions() {
         return 3;
      }

      @Override
      protected String getOptionLabel(int optionNum) {
         switch (optionNum) {
            case OP_PAID:
               return "Paid";
            case OP_UNPAID:
               return "Unpaid";
            case OP_ALL:
               return "All";
            default:
               return null;
         }
      }

      @Override
      protected void doActionForOption(int option) {
         parentMenu.onBillTypeChosen(option);
      }

      @Override
      protected void printMenuHeader() {
         System.out.println();
         System.out.println(getViewTitle());
         System.out.println();
      }

      @Override
      public void goBack() {
         new ClientMainViewMenu().proceed();
      }
   }

}
