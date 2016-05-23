package ro.unitbv.servicesupplier.client.ui.authentication;

import ro.unitbv.servicesupplier.client.ui.base.ViewMenu;

/**
 * Created by Petri on 15-May-16.
 */
public abstract class AuthenticationViewMenu extends ViewMenu {

   protected String listenForUsername() {
      return listenForString("Enter your username: ");
   }

   protected String listenForPassword() {
      return listenForString("Enter your password: ");
   }

   protected String listenForNewUsername() {
      return listenForString("Enter a username: ");
   }

   protected String listenForNewPassword() {
      String password, retyped;

      do {
         password = listenForString("Enter a password: ");
         retyped = listenForString("Reenter password: ");

         if (password.equals(retyped)) break;

         printlnDoubleIndented("Passwords do not match! Please try again.");
      } while (true);

      return password;
   }

}
