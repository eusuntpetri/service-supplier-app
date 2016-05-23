package ro.unitbv.servicesupplier.server.processing.client;

import ro.unitbv.servicesupplier.model.communication.request.client.AccountPopulationRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.SuccessResponse;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.ClientAccount;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;
import ro.unitbv.servicesupplier.server.system.NotifierSystem;
import ro.unitbv.servicesupplier.server.techno.system.autopay.AutomaticPaymentSystem;

/**
 * Created by Petri on 17-May-16.
 */
public class AccountPopulationProcessor implements RequestProcessor<AccountPopulationRequest> {

   @Override
   public ServerResponse process(AccountPopulationRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      long id = request.getClientId();
      ClientAccount client = dm.findById(ClientAccount.class, id);

      if (client == null) {
         return new InvalidDataResponse("clientId");
      }

      double amount = request.getAmount();

      if (amount <= 0) {
         return new InvalidDataResponse("amount");
      }

      dm.beginTransaction();
      client.populate(amount);
      dm.commitTransaction();

      NotifierSystem.notifyAccount(client,
            "Your account has been populated. Amount: $" + amount);

      AutomaticPaymentSystem.attemptAutomaticPayment(client);

      return new SuccessResponse("Account balance increased successfully.");
   }
}
