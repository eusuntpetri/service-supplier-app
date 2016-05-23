package ro.unitbv.servicesupplier.client.ui.client;

import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.client.ui.common.NoticesViewMenu;
import ro.unitbv.servicesupplier.client.ui.main.MainViewMenu;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;

/**
 * Created by Petri on 16-May-16.
 */
public class ClientMainViewMenu extends OptionViewMenu {

   private static final int OP_VIEW_SERVICES = 1;
   private static final int OP_VIEW_SUBSCRIPTS = 2;
   private static final int OP_VIEW_BILLS = 3;
   private static final int OP_VIEW_ACCOUNT = 4;
   private static final int OP_VIEW_NOTICES = 5;
   private static final int OP_LOGOUT = 6;

   @Override
   protected String getViewTitle() {
      return "Welcome, " + LoginSession.getClient().getFirstName() + "!";
   }

   @Override
   protected int getNumOfOptions() {
      return 6;
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      switch (optionNum) {
         case OP_VIEW_SERVICES:
            return "View available services";
         case OP_VIEW_SUBSCRIPTS:
            return "View subscriptions";
         case OP_VIEW_BILLS:
            return "View bills";
         case OP_VIEW_ACCOUNT:
            return "Your account";
         case OP_VIEW_NOTICES:
            return "View notices";
         case OP_LOGOUT:
            return "Logout";
         default:
            return null;
      }
   }

   @Override
   protected void doActionForOption(int option) {
      switch (option) {
         case OP_VIEW_SERVICES:
            new AvailableServicesViewMenu().proceed();
            break;
         case OP_VIEW_SUBSCRIPTS:
            new ClientSubscriptionsViewMenu().proceed();
            break;
         case OP_VIEW_BILLS:
            new ClientBillsViewMenu().proceed();
            break;
         case OP_VIEW_ACCOUNT:
            new ClientAccountViewMenu().proceed();
            break;
         case OP_VIEW_NOTICES:
            new NoticesViewMenu().proceed();
            break;
         case OP_LOGOUT:
            System.out.println();
            printlnIndented("Thank you for using Service Supplier. Goodbye!");
            LoginSession.end();
            new MainViewMenu().proceed();
      }
   }

}
