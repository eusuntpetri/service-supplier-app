package ro.unitbv.servicesupplier.client.ui.client;

import ro.unitbv.servicesupplier.client.logic.interactor.client.CancelSubscriptionInteractor;
import ro.unitbv.servicesupplier.client.logic.interactor.client.UpdatePaymentMethodInteractor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.client.ui.base.HasBackOption;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.client.PaymentMethodUpdateRequest;
import ro.unitbv.servicesupplier.model.communication.request.client.SubscriptionCancelRequest;
import ro.unitbv.servicesupplier.model.dto.SubscriptionDto;

import java.util.List;

/**
 * Created by Petri on 18-May-16.
 */
class SubscriptionViewMenu extends OptionViewMenu implements
      CancelSubscriptionInteractor.View, UpdatePaymentMethodInteractor.View, HasBackOption {

   private static final int OP_SWITCH_PAY_METHOD = 1;
   private static final int OP_CANCEL_SUBSCRIPT = 2;

   private ClientSubscriptionsViewMenu previousMenu;
   private List<SubscriptionDto> subscriptionContextList;
   private SubscriptionDto subscription;

   SubscriptionViewMenu(ClientSubscriptionsViewMenu previousMenu,
                        List<SubscriptionDto> subscriptionContextList,
                        SubscriptionDto subscription) {
      this.previousMenu = previousMenu;
      this.subscriptionContextList = subscriptionContextList;
      this.subscription = subscription;
   }

   @Override
   protected String getViewTitle() {
      return "Subscription to service " + subscription.getServiceDetails().getDescription();
   }

   @Override
   protected void renderView() {
      System.out.println(subscription.toString());
      super.renderView();
   }

   @Override
   protected int getNumOfOptions() {
      return 2;
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      switch (optionNum) {
         case OP_SWITCH_PAY_METHOD:
            return subscription.isPaidAutomatically()
                  ? "Set manual payment"
                  : "Automate payment";
         case OP_CANCEL_SUBSCRIPT:
            return "Cancel subscription";
         default:
            return null;
      }
   }

   @Override
   protected void doActionForOption(int option) {
      switch (option) {
         case OP_SWITCH_PAY_METHOD: {
            printWorkingMessage();

            PaymentMethodUpdateRequest request = new PaymentMethodUpdateRequest(
                  LoginSession.getClient().getId(), subscription.getId(), !subscription.isPaidAutomatically());

            new UpdatePaymentMethodInteractor(this).initiate(request);
            return;
         }
         case OP_CANCEL_SUBSCRIPT: {
            printWorkingMessage();

            SubscriptionCancelRequest request = new SubscriptionCancelRequest(
                  LoginSession.getClient().getId(), subscription.getId());

            new CancelSubscriptionInteractor(this).initiate(request);
         }
      }
   }

   @Override
   public void onPaymentMethodUpdateSuccess(boolean wasAutomated) {
      subscription.setPaymentMethod(wasAutomated);
      System.out.println(" Payment method successfully switched to: " +
            (wasAutomated
                  ? "AUTOMATIC"
                  : "MANUAL"));
      proceed();
   }

   @Override
   public void onPaymentMethodUpdateFailure(String message) {
      System.out.println(message);
      proceed();
   }

   @Override
   public void onSubscriptionCancelSuccess() {
      System.out.println(" Subscription canceled! You will receive a final payment notice.");

      subscriptionContextList.remove(subscription);
      if (subscriptionContextList.isEmpty()) {
         System.out.println();
         printlnIndented("You have no more subscriptions.");

         new ClientMainViewMenu().proceed();
         return;
      }

      goBack();
   }

   @Override
   public void onSubscriptionCancelFailure(String message) {
      System.out.println(message);
      goBack();
   }

   @Override
   public void goBack() {
      previousMenu.proceed();
   }
}
