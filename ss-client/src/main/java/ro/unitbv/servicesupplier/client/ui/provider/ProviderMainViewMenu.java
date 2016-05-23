package ro.unitbv.servicesupplier.client.ui.provider;

import ro.unitbv.servicesupplier.client.logic.session.LoginSession;
import ro.unitbv.servicesupplier.client.ui.common.NoticesViewMenu;
import ro.unitbv.servicesupplier.client.ui.main.MainViewMenu;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;

/**
 * Created by Petri on 16-May-16.
 */
public class ProviderMainViewMenu extends OptionViewMenu {

   private static final int OP_VIEW_ACCOUNT = 1;
   private static final int OP_VIEW_SERVICES = 2;
   private static final int OP_ADD_SERVICE = 3;
   private static final int OP_VIEW_NOTICES = 4;
   private static final int OP_LOGOUT = 5;

   @Override
   protected int getNumOfOptions() {
      return 5;
   }

   @Override
   protected String getViewTitle() {
      return LoginSession.getProvider().getName() + " Administration Panel";
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      switch (optionNum) {
         case OP_VIEW_ACCOUNT:
            return "Your account";
         case OP_VIEW_SERVICES:
            return "View services";
         case OP_ADD_SERVICE:
            return "Add service";
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
         case OP_VIEW_ACCOUNT:
            new ProviderAccountViewMenu().proceed();
            return;
         case OP_VIEW_SERVICES:
            new ProviderServicesViewMenu().proceed();
            return;
         case OP_ADD_SERVICE:
            new AddServiceViewMenu().proceed();
            return;
         case OP_VIEW_NOTICES:
            new NoticesViewMenu().proceed();
            return;
         case OP_LOGOUT:
            System.out.println();
            printlnIndented("Thank you for using Service Supplier. Goodbye!");
            LoginSession.end();
            new MainViewMenu().proceed();
      }
   }

}
