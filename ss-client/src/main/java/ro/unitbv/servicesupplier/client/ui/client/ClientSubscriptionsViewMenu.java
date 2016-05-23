package ro.unitbv.servicesupplier.client.ui.client;

import ro.unitbv.servicesupplier.client.logic.interactor.client.GetClientSubscriptionsInteractor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.client.ui.base.HasBackOption;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.client.ClientSubscriptionsRequest;
import ro.unitbv.servicesupplier.model.dto.SubscriptionDto;

import java.util.Collections;
import java.util.List;

/**
 * Created by Petri on 16-May-16.
 */
class ClientSubscriptionsViewMenu extends OptionViewMenu implements GetClientSubscriptionsInteractor.View, HasBackOption {

   private List<SubscriptionDto> clientSubscriptions;

   @Override
   protected String getViewTitle() {
      return "Your subscriptions";
   }

   @Override
   public void proceed() {
      initialize();
   }

   private void initialize() {
      if (clientSubscriptions != null) {
         resume();
         return;
      }

      printWorkingMessage();

      ClientSubscriptionsRequest request =
            new ClientSubscriptionsRequest(LoginSession.getClient().getId());

      new GetClientSubscriptionsInteractor(this).initiate(request);
   }

   private void resume() {
      if (clientSubscriptions.isEmpty()) {
         System.out.println(" You have no subscriptions yet!");
         goBack();
         return;
      }

      System.out.println();
      super.proceed();
   }

   @Override
   protected int getNumOfOptions() {
      return clientSubscriptions.size();
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      optionNum--;

      return clientSubscriptions.get(optionNum).toString();
   }

   @Override
   protected void doActionForOption(int option) {
      option--;
      SubscriptionDto subscription = clientSubscriptions.get(option);

      new SubscriptionViewMenu(this, clientSubscriptions, subscription).proceed();
   }

   @Override
   public void onSubscriptionLoadSuccess(List<SubscriptionDto> subscriptions) {
      this.clientSubscriptions = subscriptions;
      resume();
   }

   @Override
   public void onNoSubscripionsFound() {
      this.clientSubscriptions = Collections.emptyList();
      resume();
   }

   @Override
   public void onSubscriptionLoadFailure(String message) {
      System.out.println(message);
      goBack();
   }

   @Override
   public void goBack() {
      new ClientMainViewMenu().proceed();
   }
}
