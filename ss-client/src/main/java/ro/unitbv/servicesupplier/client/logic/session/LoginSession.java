package ro.unitbv.servicesupplier.client.logic.session;

import ro.unitbv.servicesupplier.model.dto.ClientAccountDto;
import ro.unitbv.servicesupplier.model.dto.ProviderAccountDto;

/**
 * Created by Petri on 16-May-16.
 */
public final class LoginSession {

   private static ClientAccountDto loggedInClient;
   private static ProviderAccountDto loggedInProvider;

   private LoginSession() {
      // Static access only.
   }

   public static ClientAccountDto getClient() {
      return loggedInClient;
   }

   public static ProviderAccountDto getProvider() {
      return loggedInProvider;
   }

   public static void begin(ClientAccountDto client) {
      loggedInClient = client;
      loggedInProvider = null;
   }

   public static void begin(ProviderAccountDto provider) {
      loggedInProvider = provider;
      loggedInClient = null;
   }

   public static void end() {
      loggedInClient = null;
      loggedInProvider = null;
   }

   public static boolean isOfClient() {
      return loggedInClient != null;
   }

   public static boolean isOfProvider() {
      return loggedInProvider != null;
   }

   public static boolean isActive() {
      return isOfClient() || isOfProvider();
   }

}
