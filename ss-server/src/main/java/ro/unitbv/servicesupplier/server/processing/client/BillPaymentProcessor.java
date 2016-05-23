package ro.unitbv.servicesupplier.server.processing.client;

import ro.unitbv.servicesupplier.model.communication.request.client.BillPaymentRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.client.BalanceTooLowResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InconsistentDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.ClientAccount;
import ro.unitbv.servicesupplier.repository.persistence.bill.Bill;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;
import ro.unitbv.servicesupplier.server.system.NotifierSystem;

/**
 * Created by Petri on 17-May-16.
 */
public class BillPaymentProcessor implements RequestProcessor<BillPaymentRequest> {

   @Override
   public ServerResponse process(BillPaymentRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      long clientId = request.getClientId();
      ClientAccount client = dm.findById(ClientAccount.class, clientId);

      if (client == null) {
         return new InvalidDataResponse("clientId");
      }

      long billId = request.getBillId();
      Bill bill = dm.findById(Bill.class, billId);

      if (bill == null) {
         return new InvalidDataResponse("billId");
      }
      if (bill.getSubscription().getSubscriber().getId() != clientId) {
         return new InconsistentDataResponse("Bill must be paid through the subscribing client's account.");
      }

      double paymentTotal = bill.getPaymentDue() + bill.getPaymentOverdue();

      if (paymentTotal > client.getBalance()) {
         return new BalanceTooLowResponse();
      }

      Subscription subscription = bill.getSubscription();

      dm.beginTransaction();
      client.retract(paymentTotal);
      bill.setPaid();
      subscription.resetPeriodSincePayment();
      dm.commitTransaction();

      NotifierSystem.notifyAccount(client, "You have paid a bill to " +
            subscription.getService().getProvider().getName() +
            ". Total: $" + (paymentTotal));

      if(subscription.isBlocked() && bill.getPaymentOverdue() > 0) {
         subscription.unblock();
         NotifierSystem.notifySubscriptionUnblocked(subscription);
      }

      return new SuccessResponse("Payment successful.");
   }
}
