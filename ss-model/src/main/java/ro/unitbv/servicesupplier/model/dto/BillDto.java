package ro.unitbv.servicesupplier.model.dto;

import ro.unitbv.servicesupplier.model.dto.builder.IBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Petri on 17-May-16.
 */
public class BillDto implements Serializable, Comparable<BillDto> {

   private long id;
   private String clientName;
   private String serviceDescription;
   private String providerName;
   private Timestamp issueDate;
   private double paymentDue;
   private double paymentOverdue;
   private boolean isPaid;

   private BillDto(Builder builder) {
      this.id = builder.id;
      this.clientName = builder.clientName;
      this.serviceDescription = builder.serviceDescription;
      this.providerName = builder.providerName;
      this.issueDate = builder.issueDate;
      this.paymentDue = builder.paymentDue;
      this.paymentOverdue = builder.paymentOverdue;
      this.isPaid = builder.isPaid;
   }

   public long getId() {
      return id;
   }

   public String getClientName() {
      return clientName;
   }

   public String getServiceDescription() {
      return serviceDescription;
   }

   public String getProviderName() {
      return providerName;
   }

   public Timestamp getIssueDate() {
      return issueDate;
   }

   public double getPaymentDue() {
      return paymentDue;
   }

   public double getPaymentOverdue() {
      return paymentOverdue;
   }

   public boolean isPaid() {
      return isPaid;
   }

   public void setPaid() {
      isPaid = true;
   }

   @Override
   public int compareTo(BillDto o) {
      return this.id > o.id ? 1 : -1;
   }

   @Override
   public String toString() {
      return (isPaid ? "PAID" : "UNPAID") + System.lineSeparator() +
            "\t\t Service: " + serviceDescription + System.lineSeparator() +
            "\t\tProvider: " + providerName + System.lineSeparator() +
            "\t\t  Client: " + clientName + System.lineSeparator() +
            System.lineSeparator() +
            "\t\t  Issued at: " + issueDate + System.lineSeparator() +
            System.lineSeparator() +
            "\t\t\tPayment due: $" + paymentDue + System.lineSeparator() +
            (paymentOverdue > 0
                  ? "\t\t\t    OVERDUE: $" + paymentOverdue + System.lineSeparator() +
                  "\t\t\t      TOTAL: $" + (paymentDue + paymentOverdue)
                  : "") +
            System.lineSeparator();
   }

   public static class Builder implements IBuilder<BillDto> {

      private long id;
      private String clientName;
      private String serviceDescription;
      private String providerName;
      private Timestamp issueDate;
      private double paymentDue;
      private double paymentOverdue;
      private boolean isPaid;

      public Builder withId(long id) {
         this.id = id;
         return this;
      }

      public Builder toClient(String clientName) {
         this.clientName = clientName;
         return this;
      }

      public Builder forService(String serviceDescription) {
         this.serviceDescription = serviceDescription;
         return this;
      }

      public Builder fromProvider(String providerName) {
         this.providerName = providerName;
         return this;
      }

      public Builder issuedAt(Timestamp issueDate) {
         this.issueDate = issueDate;
         return this;
      }

      public Builder due(double paymentDue) {
         this.paymentDue = paymentDue;
         return this;
      }

      public Builder overdue(double paymentOverdue) {
         this.paymentOverdue = paymentOverdue;
         return this;
      }

      public Builder isPaid(boolean paid) {
         isPaid = paid;
         return this;
      }

      @Override
      public BillDto build() {
         return new BillDto(this);
      }
   }
}
