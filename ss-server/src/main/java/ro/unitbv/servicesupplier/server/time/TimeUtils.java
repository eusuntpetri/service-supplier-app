package ro.unitbv.servicesupplier.server.time;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by Petri on 18-May-16.
 */
public class TimeUtils {

   private TimeUtils() {
      // Not instantiable.
   }

   public static int timeUnitsBetween(Timestamp sinceDate, Timestamp untilDate) {
      long sinceTime = sinceDate.getTime();
      long untilTime = untilDate.getTime();
      long elapsedTime = (untilTime - sinceTime) / TimeConstants.SWEEP_CYCLE_MILLIS;
      return (int) elapsedTime;
   }

   public static int timeUnitsSince(Timestamp sinceDate) {
      return timeUnitsBetween(sinceDate, Timestamp.valueOf(LocalDateTime.now()));
   }

}
