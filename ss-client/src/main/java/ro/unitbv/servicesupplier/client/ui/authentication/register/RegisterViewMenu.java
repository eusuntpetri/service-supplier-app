package ro.unitbv.servicesupplier.client.ui.authentication.register;

import ro.unitbv.servicesupplier.client.ui.base.HasBackOption;
import ro.unitbv.servicesupplier.client.ui.main.MainViewMenu;
import ro.unitbv.servicesupplier.client.ui.base.OptionViewMenu;

/**
 * Created by Petri on 12-May-16.
 */
public class RegisterViewMenu extends OptionViewMenu implements HasBackOption {

   private static final int OP_CLIENT = 1;
   private static final int OP_PROVIDER = 2;

   @Override
   protected String getViewTitle() {
      return "Register";
   }

   @Override
   protected int getNumOfOptions() {
      return 2;
   }

   @Override
   protected String getOptionLabel(int optionNum) {
      switch (optionNum) {
         case OP_CLIENT:
            return "As client";
         case OP_PROVIDER:
            return "As provider";
         default:
            return null;
      }
   }

   @Override
   protected void doActionForOption(int option) {
      switch (option) {
         case OP_CLIENT:
            new ClientRegisterViewMenu().proceed();
            break;
         case OP_PROVIDER:
            new ProviderRegisterViewMenu().proceed();
            break;
      }
   }

   @Override
   public void goBack() {
      new MainViewMenu().proceed();
   }
}
