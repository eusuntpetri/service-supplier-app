package ro.unitbv.servicesupplier.client.ui.client;

import ro.unitbv.servicesupplier.client.logic.interactor.client.BillPaymentInteractor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.client.ui.base.HasBackOption;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.client.BillPaymentRequest;
import ro.unitbv.servicesupplier.model.dto.BillDto;

import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
class BillViewMenu extends OptionViewMenu implements BillPaymentInteractor.View, HasBackOption {

   private ClientBillsViewMenu previousMenu;
   private List<BillDto> billContextList;
   private BillDto viewingBill;

   BillViewMenu(ClientBillsViewMenu previousMenu, BillDto viewingBill,
                List<BillDto> billContextList) {
      this.previousMenu = previousMenu;
      this.viewingBill = viewingBill;
      this.billContextList = billContextList;
   }

   @Override
   protected String getViewTitle() {
      return "Bill for subscription to " + viewingBill.getServiceDescription();
   }

   @Override
   protected void renderView() {
      System.out.println(viewingBill.toString());

      super.renderView();
   }

   @Override
   protected int getNumOfOptions() {
      return 1;
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      return "Pay bill";
   }

   @Override
   protected void doActionForOption(int option) {
      // option == 1;

      printWorkingMessage();

      BillPaymentRequest request = new BillPaymentRequest(
            LoginSession.getClient().getId(), viewingBill.getId());

      new BillPaymentInteractor(this).initiate(request);
   }

   @Override
   public void onBillPaymentSuccess() {
      System.out.println("Bill payment successful!");
      viewingBill.setPaid();

      if (billContextList != null) {
         billContextList.remove(viewingBill);

         if (billContextList.isEmpty()) {
            System.out.println();
            printlnIndented("There are no more bills to view.");

            new ClientMainViewMenu().proceed();
            return;
         }
      }

      goBack();
   }

   @Override
   public void onBillPaymentFailure(String message) {
      System.out.println(" Bill payment failure: " + message);
      goBack();
   }

   @Override
   public void goBack() {
      previousMenu.proceed();
   }
}
