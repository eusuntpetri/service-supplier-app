package ro.unitbv.servicesupplier.server.processing.common;

import ro.unitbv.servicesupplier.model.communication.request.common.AccountRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.account.AccountResponse;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.Account;
import ro.unitbv.servicesupplier.server.conversion.AccountConverter;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;

/**
 * Created by Petri on 20-May-16.
 */
public class AccountRequestProcessor implements RequestProcessor<AccountRequest> {

   @Override
   public ServerResponse process(AccountRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      long id = request.getAccountId();
      Account account = dm.findById(Account.class, id);

      if (account == null) {
         return new InvalidDataResponse("accountId");
      }

      return new AccountResponse<>(AccountConverter.toDto(account));
   }
}
