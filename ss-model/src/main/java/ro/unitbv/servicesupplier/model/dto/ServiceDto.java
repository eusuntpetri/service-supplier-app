package ro.unitbv.servicesupplier.model.dto;

import ro.unitbv.servicesupplier.model.dto.builder.IBuilder;

import java.io.Serializable;

/**
 * Created by Petri on 15-May-16.
 */
public class ServiceDto implements Serializable {

   private long id;
   private String providerName;
   private String description;
   private double payment;
   private int paymentPeriod;

   private ServiceDto(Builder builder) {
      this.id = builder.id;
      this.providerName = builder.providerName;
      this.description = builder.description;
      this.payment = builder.payment;
      this.paymentPeriod = builder.paymentPeriod;
   }

   public long getId() {
      return id;
   }

   public String getProviderName() {
      return providerName;
   }

   public String getDescription() {
      return description;
   }

   public double getPayment() {
      return payment;
   }

   public int getPaymentPeriod() {
      return paymentPeriod;
   }

   public String toString() {
      return System.lineSeparator() +
            "\t\tService : " + description + System.lineSeparator() +
            "\t\tProvider: " + providerName + System.lineSeparator() +
            System.lineSeparator() +
            "\t\tPayment: $" + payment +
            " per " + paymentPeriod + " days" +
            System.lineSeparator();
   }


   public static class Builder implements IBuilder<ServiceDto> {

      private long id;
      private String providerName;
      private String description;
      private double payment;
      private int paymentPeriod;

      public Builder withId(long id) {
         this.id = id;
         return this;
      }

      public Builder fromProvider(String providerName) {
         this.providerName = providerName;
         return this;
      }

      public Builder withDescription(String description) {
         this.description = description;
         return this;
      }

      public Builder costing(double payment) {
         this.payment = payment;
         return this;
      }

      public Builder per(int paymentPeriod) {
         this.paymentPeriod = paymentPeriod;
         return this;
      }

      @Override
      public ServiceDto build() {
         return new ServiceDto(this);
      }
   }
}
