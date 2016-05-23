package ro.unitbv.servicesupplier.model.dto;

import ro.unitbv.servicesupplier.model.dto.builder.IBuilder;

import java.io.Serializable;

/**
 * Created by Petri on 15-May-16.
 */
public class ClientAccountDto extends AccountDto implements Serializable {

   private String firstName;
   private String lastName;
   private double balance;

   private ClientAccountDto(Builder builder) {
      super(builder);
      this.firstName = builder.firstName;
      this.lastName = builder.lastName;
      this.balance = builder.balance;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public double getBalance() {
      return balance;
   }

   public void setBalance(double balance) {
      this.balance = balance;
   }


   public static class Builder extends AccountDto.Builder<Builder> implements IBuilder<ClientAccountDto> {

      private String firstName;
      private String lastName;
      private double balance;

      public Builder withFirstName(String firstName) {
         this.firstName = firstName;
         return this;
      }

      public Builder withLastName(String lastName) {
         this.lastName = lastName;
         return this;
      }

      public Builder worth(double balance) {
         this.balance = balance;
         return this;
      }

      @Override
      public ClientAccountDto build() {
         return new ClientAccountDto(this);
      }
   }

}
