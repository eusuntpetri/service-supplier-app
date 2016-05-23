package ro.unitbv.servicesupplier.server.conversion;

import ro.unitbv.servicesupplier.model.dto.BillDto;
import ro.unitbv.servicesupplier.repository.persistence.account.ClientAccount;
import ro.unitbv.servicesupplier.repository.persistence.bill.Bill;
import ro.unitbv.servicesupplier.repository.persistence.service.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Petri on 17-May-16.
 */
public class BillConverter {

   public static List<BillDto> toDto(List<Bill> bills) {
      if (bills == null || bills.isEmpty()) return Collections.emptyList();

      List<BillDto> billDetails = new ArrayList<>();

      for (Bill bill : bills) {
         billDetails.add(toDto(bill));
      }

      return billDetails;
   }

   public static BillDto toDto(Bill bill) {
      ClientAccount client = bill.getSubscription().getSubscriber();
      Service service = bill.getSubscription().getService();

      return new BillDto.Builder()
            .withId(bill.getId())
            .toClient(client.getFirstName() + " " + client.getLastName())
            .forService(service.getDescription())
            .fromProvider(service.getProvider().getName())
            .issuedAt(bill.getIssueDate())
            .due(bill.getPaymentDue())
            .overdue(bill.getPaymentOverdue())
            .isPaid(bill.isPaid())
            .build();
   }

}
