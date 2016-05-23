package ro.unitbv.servicesupplier.server.conversion;

import ro.unitbv.servicesupplier.model.dto.AccountDto;
import ro.unitbv.servicesupplier.model.dto.ClientAccountDto;
import ro.unitbv.servicesupplier.model.dto.ProviderAccountDto;
import ro.unitbv.servicesupplier.repository.persistence.account.Account;
import ro.unitbv.servicesupplier.repository.persistence.account.ClientAccount;
import ro.unitbv.servicesupplier.repository.persistence.account.ProviderAccount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class AccountConverter {

   public static List<AccountDto> toDto(List<Account> accounts) {
      if (accounts == null || accounts.isEmpty()) return Collections.emptyList();

      List<AccountDto> accountDetails = new ArrayList<>();

      for (Account account : accounts) {
         accountDetails.add(toDto(account));
      }

      return accountDetails;
   }

   public static AccountDto toDto(Account account) {
      if (account instanceof ClientAccount) {
         return toDto((ClientAccount) account);
      }
      return toDto((ProviderAccount) account);
   }

   public static ClientAccountDto toDto(ClientAccount client) {
      return new ClientAccountDto.Builder()
            .withId(client.getId())
            .withUsername(client.getUsername())
            .withFirstName(client.getFirstName())
            .withLastName(client.getLastName())
            .worth(client.getBalance())
            .build();
   }

   public static ProviderAccountDto toDto(ProviderAccount provider) {
      return new ProviderAccountDto.Builder()
            .withId(provider.getId())
            .withUsername(provider.getUsername())
            .withName(provider.getName())
            .build();
   }

}
