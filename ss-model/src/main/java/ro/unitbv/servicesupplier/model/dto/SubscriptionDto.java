package ro.unitbv.servicesupplier.model.dto;

import ro.unitbv.servicesupplier.model.dto.builder.IBuilder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

/**
 * Created by Petri on 16-May-16.
 */
public class SubscriptionDto implements Serializable {

   private long id;
   private ClientAccountDto subscriberDetails;
   private ServiceDto serviceDetails;
   private Timestamp activationDate;
   private boolean isBlocked;
   private int periodSincePayment;
   private boolean paymentAutomated;
   private List<BillDto> issuedBills = Collections.emptyList();

   private SubscriptionDto(Builder builder) {
      this.id = builder.id;
      this.subscriberDetails = builder.subscriber;
      this.serviceDetails = builder.service;
      this.activationDate = builder.activationDate;
      this.isBlocked = builder.isBlocked;
      this.periodSincePayment = builder.periodSincePayment;
      this.paymentAutomated = builder.paymentAutomated;
      this.issuedBills = builder.issuedBills;
   }

   public long getId() {
      return id;
   }

   public ClientAccountDto getSubscriberDetails() {
      return subscriberDetails;
   }

   public ServiceDto getServiceDetails() {
      return serviceDetails;
   }

   public Timestamp getActivationDate() {
      return activationDate;
   }

   public boolean isBlocked() {
      return isBlocked;
   }

   public int getPeriodSincePayment() {
      return periodSincePayment;
   }

   public boolean isPaidAutomatically() {
      return paymentAutomated;
   }

   public void setPaymentMethod(boolean automated) {
      this.paymentAutomated = automated;
   }

   public List<BillDto> getIssuedBills() {
      return issuedBills;
   }

   @Override
   public String toString() {
      return (isBlocked ? "BLOCKED AWAITING OVERDUE PAYMENT" + System.lineSeparator() : "") +
            System.lineSeparator() +
            "\t\t" + serviceDetails.getProviderName() + System.lineSeparator() +
            "\t\t" + serviceDetails.getDescription() + System.lineSeparator() +
            "\t\tSubscribed since: " + activationDate + System.lineSeparator() +
            System.lineSeparator() +
            "\t\tPayment: $" + serviceDetails.getPayment() +
            " per " + serviceDetails.getPaymentPeriod() + " days" + System.lineSeparator() +
            "\t\tPayment method: " + (isPaidAutomatically() ? "AUTOMATIC" : "MANUAL") +
            System.lineSeparator();
   }


   public static class Builder implements IBuilder<SubscriptionDto> {

      private long id;
      private ClientAccountDto subscriber;
      private ServiceDto service;
      private Timestamp activationDate;
      private boolean isBlocked;
      private int periodSincePayment;
      private boolean paymentAutomated;
      private List<BillDto> issuedBills;

      public Builder withId(long id) {
         this.id = id;
         return this;
      }

      public Builder bySubscriber(ClientAccountDto subscriber) {
         this.subscriber = subscriber;
         return this;
      }

      public Builder toService(ServiceDto service) {
         this.service = service;
         return this;
      }

      public Builder activatedAt(Timestamp creationDate) {
         this.activationDate = creationDate;
         return this;
      }

      public Builder isBlocked(boolean value) {
         this.isBlocked = value;
         return this;
      }

      public Builder unpaidSince(int periodSincePayment) {
         this.periodSincePayment = periodSincePayment;
         return this;
      }

      public Builder automatedPayment(boolean value) {
         this.paymentAutomated = value;
         return this;
      }

      public Builder withBills(List<BillDto> issuedBills) {
         this.issuedBills = issuedBills;
         return this;
      }

      @Override
      public SubscriptionDto build() {
         return new SubscriptionDto(this);
      }
   }

}
