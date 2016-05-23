package ro.unitbv.servicesupplier.client.ui.main;

import ro.unitbv.servicesupplier.client.ServiceSupplierClient;
import ro.unitbv.servicesupplier.client.ui.authentication.login.LoginViewMenu;
import ro.unitbv.servicesupplier.client.ui.authentication.register.RegisterViewMenu;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;

/**
 * Created by Petri on 12-May-16.
 */
public class MainViewMenu extends OptionViewMenu {

   private static final int OP_LOGIN = 1;
   private static final int OP_REGISTER = 2;
   private static final int OP_EXIT = 3;

   @Override
   public String getViewTitle() {
      return "Main menu";
   }

   @Override
   public int getNumOfOptions() {
      return 3;
   }

   @Override
   public String getOptionLabel(int optionNum) {
      switch (optionNum) {
         case OP_LOGIN:
            return "Log in";
         case OP_REGISTER:
            return "Register";
         case OP_EXIT:
            return "Exit";
         default:
            return null;
      }
   }

   @Override
   protected void doActionForOption(int option) {
      switch (option) {
         case OP_LOGIN:
            new LoginViewMenu().proceed();
            break;
         case OP_REGISTER:
            new RegisterViewMenu().proceed();
            break;
         case OP_EXIT:
            ServiceSupplierClient.getInstance().shutdownClient();
      }
   }

}
