package ro.unitbv.servicesupplier.server.techno.system;

import ro.unitbv.servicesupplier.server.techno.system.autopay.PaymentLedgerSweeper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Petri on 18-May-16.
 */
public final class TechnoSystem implements Runnable {

   private static final TechnoSystem INSTANCE = new TechnoSystem();

   private final ExecutorService AUTOMATIC_TASK_EXECUTOR = Executors.newFixedThreadPool(1);

   private static final PaymentLedgerSweeper LEDGER_SWEEPER = PaymentLedgerSweeper.getInstance();

   private boolean isTerminated = true;

   private TechnoSystem() {
      // Singleton, statically accessed.
   }

   public static TechnoSystem getInstance() {
      return INSTANCE;
   }

   @Override
   public void run() {
      this.isTerminated = false;

      AUTOMATIC_TASK_EXECUTOR.execute(LEDGER_SWEEPER);

      while (!Thread.currentThread().isInterrupted()) {
         try {
            TimeUnit.DAYS.sleep(365);
         } catch (InterruptedException e) {
            break;
         }
      }
      this.isTerminated = true;

      LEDGER_SWEEPER.waitWhileSweeping();
      AUTOMATIC_TASK_EXECUTOR.shutdownNow();
   }

   public static boolean isTerminated() {
      return INSTANCE.isTerminated;
   }

}
