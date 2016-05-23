package ro.unitbv.servicesupplier.repository.persistence.service;

import ro.unitbv.servicesupplier.model.dto.ServiceDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.Persistable;
import ro.unitbv.servicesupplier.repository.persistence.account.ProviderAccount;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Petri on 14-May-16.
 */
@Entity
public class Service implements Persistable {

   @Id
   @GeneratedValue
   private long id;

   @ManyToOne
   @JoinColumn(name = "PROVIDER_ID", nullable = false)
   private ProviderAccount provider;
   private String description;
   private double payment;
   private int paymentPeriod;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
   private Set<Subscription> subscriptions;

   protected Service() {
      // Required by JPA.
   }

   public Service(ServiceDto serviceDetails) {
      this.description = serviceDetails.getDescription();
      this.payment = serviceDetails.getPayment();
      this.paymentPeriod = serviceDetails.getPaymentPeriod();
      subscriptions = new HashSet<>();
   }

   @Override
   public long getId() {
      return id;
   }

   public Service setProvider(ProviderAccount provider) {
      this.provider = provider;
      return this;
   }

   public ProviderAccount getProvider() {
      return provider;
   }

   public List<Subscription> getSubscriptions() {
      return new ArrayList<>(subscriptions);
   }

   public String getDescription() {
      return description;
   }

   public double getPayment() {
      return payment;
   }

   public Service setPayment(double payment) {
      DatabaseManager.getInstance().beginTransaction();
      this.payment = payment;
      DatabaseManager.getInstance().commitTransaction();
      return this;
   }

   public int getPaymentPeriod() {
      return paymentPeriod;
   }

   public Service setPaymentPeriod(int paymentPeriod) {
      DatabaseManager.getInstance().beginTransaction();
      this.paymentPeriod = paymentPeriod;
      DatabaseManager.getInstance().commitTransaction();
      return this;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Service service = (Service) o;

      return id == service.id;
   }

   @Override
   public int hashCode() {
      return (int) (id ^ (id >>> 32));
   }

}
