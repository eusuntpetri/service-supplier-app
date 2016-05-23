package ro.unitbv.servicesupplier.server.conversion;

import ro.unitbv.servicesupplier.model.dto.SubscriptionDto;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Petri on 17-May-16.
 */
public class SubscriptionConverter {

   public static List<SubscriptionDto> toDto(List<Subscription> subscriptions) {
      if (subscriptions == null || subscriptions.isEmpty()) return Collections.emptyList();

      return subscriptions.stream()
            .map(SubscriptionConverter::toDto)
            .collect(Collectors.toList());
   }

   public static SubscriptionDto toDto(Subscription subscription) {
      return new SubscriptionDto.Builder()
            .withId(subscription.getId())
            .bySubscriber(AccountConverter.toDto(subscription.getSubscriber()))
            .toService(ServiceConverter.toDto(subscription.getService()))
            .activatedAt(subscription.getActivationDate())
            .isBlocked(subscription.isBlocked())
            .unpaidSince(subscription.getPeriodSincePayment())
            .automatedPayment(subscription.isPaidAutomatically())
            .withBills(BillConverter.toDto(subscription.getIssuedBills()))
            .build();
   }

}
