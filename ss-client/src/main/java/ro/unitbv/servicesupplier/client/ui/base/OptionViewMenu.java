package ro.unitbv.servicesupplier.client.ui.base;

import java.io.IOException;

/**
 * Created by Petri on 12-May-16.
 */
public abstract class OptionViewMenu extends ViewMenu {

   protected abstract int getNumOfOptions();

   protected abstract String getOptionLabel(int optionNum);

   protected abstract void doActionForOption(int option);

   @Override
   public void proceed() {
      printMenuHeader();
      renderView();
      listenForOption();
   }

   @Override
   protected void renderView() {
      for (int i = 1; i <= getNumOfOptions(); i++) {
         printlnIndented(i + ") " + getOptionLabel(i));
      }
      if (this instanceof HasBackOption) {
         renderBackOption();
      }
      System.out.println();
   }

   private void renderBackOption() {
      printlnIndented("0) Back");
   }

   protected void listenForOption() {
      printDoubleIndented("Select option: ");
      int option = -1;
      try {
         option = Integer.valueOf(sysScan.readLine());
      } catch (NumberFormatException e) {
         // Option remains -1.
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      if (optionIsValid(option)) {
         if (option == 0) {
            ((HasBackOption) this).goBack();
            return;
         }
         doActionForOption(option);
      } else {
         printInvalidOptionMessage();
         listenForOption();
      }
   }

   private void printInvalidOptionMessage() {
      printlnDoubleIndented("Invalid option selected! Please try again.");
   }

   private boolean optionIsValid(int option) {
      int minOption = this instanceof HasBackOption ? 0 : 1;
      return option >= minOption && option <= getNumOfOptions();
   }

}
