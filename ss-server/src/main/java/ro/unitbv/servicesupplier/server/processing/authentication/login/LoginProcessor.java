package ro.unitbv.servicesupplier.server.processing.authentication.login;

import org.apache.commons.lang3.StringUtils;
import ro.unitbv.servicesupplier.model.communication.request.authentication.LoginRequest;
import ro.unitbv.servicesupplier.model.communication.response.ServerResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.authentication.InvalidLoginResponse;
import ro.unitbv.servicesupplier.model.communication.response.failure.common.InvalidDataResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.account.ClientAccountResponse;
import ro.unitbv.servicesupplier.model.communication.response.success.account.ProviderAccountResponse;
import ro.unitbv.servicesupplier.model.dto.AccountCredentialDto;
import ro.unitbv.servicesupplier.model.dto.ClientAccountDto;
import ro.unitbv.servicesupplier.model.dto.ProviderAccountDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.account.Account;
import ro.unitbv.servicesupplier.repository.persistence.account.ClientAccount;
import ro.unitbv.servicesupplier.repository.persistence.account.ProviderAccount;
import ro.unitbv.servicesupplier.server.conversion.AccountConverter;
import ro.unitbv.servicesupplier.server.encryption.EncryptUtils;
import ro.unitbv.servicesupplier.server.processing.RequestProcessor;

/**
 * Created by Petri on 16-May-16.
 */
public class LoginProcessor implements RequestProcessor<LoginRequest> {

   @Override
   public ServerResponse process(LoginRequest request) {
      DatabaseManager dm = DatabaseManager.getInstance();

      AccountCredentialDto credentials = request.getCredentials();
      String username = credentials.getUsername();
      String password = credentials.getPassword();

      if (StringUtils.isEmpty(username)) {
         return new InvalidDataResponse("username");
      } else if (StringUtils.isEmpty(password)) {
         return new InvalidDataResponse("password");
      }

      Account account = dm.findAccountByUsername(username);

      if (account == null || !EncryptUtils.verify(password, account.getPassword())) {
         return new InvalidLoginResponse();
      }

      if (account instanceof ClientAccount) {
         ClientAccount clientAccount = (ClientAccount) account;
         ClientAccountDto accountDetails = AccountConverter.toDto(clientAccount);

         return new ClientAccountResponse(accountDetails);
      }

      ProviderAccount providerAccount = (ProviderAccount) account;
      ProviderAccountDto accountDetails = AccountConverter.toDto(providerAccount);

      return new ProviderAccountResponse(accountDetails);
   }
}
