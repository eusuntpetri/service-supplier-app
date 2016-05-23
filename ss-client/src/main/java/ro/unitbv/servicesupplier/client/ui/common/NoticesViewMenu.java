package ro.unitbv.servicesupplier.client.ui.common;

import ro.unitbv.servicesupplier.client.logic.interactor.common.GetNoticesInteractor;
import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.client.ui.client.ClientMainViewMenu;
import ro.unitbv.servicesupplier.client.ui.base.HasBackOption;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;
import ro.unitbv.servicesupplier.client.ui.provider.ProviderMainViewMenu;
import ro.unitbv.servicesupplier.model.communication.request.common.NoticesRequest;
import ro.unitbv.servicesupplier.model.dto.NoticeDto;

import java.util.Collections;
import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class NoticesViewMenu extends OptionViewMenu implements GetNoticesInteractor.View, HasBackOption {

   private List<NoticeDto> notices;

   @Override
   public void proceed() {
      initialize();
   }

   private void initialize() {
      if (notices != null) {
         resume();
         return;
      }

      printWorkingMessage();

      NoticesRequest request = new NoticesRequest(
            LoginSession.isOfClient()
                  ? LoginSession.getClient().getId()
                  : LoginSession.getProvider().getId());

      new GetNoticesInteractor(this).initiate(request);
   }

   private void resume() {
      if (notices.isEmpty()) {
         System.out.println(" You have no notices.");
         goBack();
         return;
      }
      System.out.println();
      super.proceed();
   }

   @Override
   protected int getNumOfOptions() {
      return notices.size();
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      optionNum--;
      NoticeDto notice = notices.get(optionNum);

      return "Received at " + notice.getIssueDate() + ":" + System.lineSeparator() +
            System.lineSeparator() +
            "\t\t" + notice.getMessage() +
            System.lineSeparator();
   }

   @Override
   protected void doActionForOption(int option) {
      printlnDoubleIndented("No action available.");
      listenForOption();
   }

   @Override
   protected String getViewTitle() {
      return "Your notices";
   }

   @Override
   public void onNoticesLoadSuccess(List<NoticeDto> notices) {
      this.notices = notices;
      resume();
   }

   @Override
   public void onNoNoticesFound() {
      this.notices = Collections.emptyList();
      resume();
   }

   @Override
   public void onNoticesLoadFailure(String message) {
      System.out.println(" Failed to retrieve notices: " + message);
      goBack();
   }

   @Override
   public void goBack() {
      if (LoginSession.isOfClient()) {
         new ClientMainViewMenu().proceed();
      } else {
         new ProviderMainViewMenu().proceed();
      }
   }
}
