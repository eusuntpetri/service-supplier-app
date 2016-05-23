package ro.unitbv.servicesupplier.server.encryption;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by Petri on 19-May-16.
 */
public final class EncryptUtils {

   private EncryptUtils() {
      // Not instantiable.
   }

   public static String saltAndPepper(String plain) {
      return BCrypt.hashpw(plain, BCrypt.gensalt());
   }

   public static boolean verify(String plain, String hashed) {
      return BCrypt.checkpw(plain, hashed);
   }
}
