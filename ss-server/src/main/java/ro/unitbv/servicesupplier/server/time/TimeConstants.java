package ro.unitbv.servicesupplier.server.time;

import java.util.concurrent.TimeUnit;

/**
 * Created by Petri on 19-May-16.
 */
public interface TimeConstants {

   TimeUnit TIME_UNIT = TimeUnit.SECONDS;
   int UNITS_PER_PERIOD = 2;
   long SWEEP_CYCLE_MILLIS = TIME_UNIT.toMillis(UNITS_PER_PERIOD);

}
