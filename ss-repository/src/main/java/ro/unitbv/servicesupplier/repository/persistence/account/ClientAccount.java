package ro.unitbv.servicesupplier.repository.persistence.account;

import ro.unitbv.servicesupplier.model.dto.ClientAccountDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.Persistable;
import ro.unitbv.servicesupplier.repository.persistence.bill.Bill;
import ro.unitbv.servicesupplier.repository.persistence.service.Service;
import ro.unitbv.servicesupplier.repository.persistence.subscription.Subscription;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Petri on 14-May-16.
 */
@Entity
@DiscriminatorValue("CLNT")
public class ClientAccount extends Account implements Persistable {

   private String firstName;
   private String lastName;
   private double balance;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "subscriber")
   private Set<Subscription> subscriptions;

   protected ClientAccount() {
      // Required by JPA.
   }

   public ClientAccount(ClientAccountDto accountDetails) {
      super(accountDetails.getCredentials());
      this.firstName = accountDetails.getFirstName();
      this.lastName = accountDetails.getLastName();
      this.balance = accountDetails.getBalance();
      subscriptions = new HashSet<>();
   }

   public Subscription subscribeToService(Service service, boolean autoPaid) {
      Subscription subscription = new Subscription()
            .setSubscriber(this)
            .setService(service)
            .setAutomaticPayment(autoPaid);
      DatabaseManager.getInstance().persist(subscription);
      DatabaseManager.getInstance().refresh(this);
      DatabaseManager.getInstance().refresh(service);

      return subscription;
   }

   public String getFirstName() {
      return firstName;
   }

   public ClientAccount setFirstName(String firstName) {
      DatabaseManager.getInstance().beginTransaction();
      this.firstName = firstName;
      DatabaseManager.getInstance().commitTransaction();
      return this;
   }

   public String getLastName() {
      return lastName;
   }

   public ClientAccount setLastName(String lastName) {
      DatabaseManager.getInstance().beginTransaction();
      this.lastName = lastName;
      DatabaseManager.getInstance().commitTransaction();
      return this;
   }

   public double getBalance() {
      return balance;
   }

   public ClientAccount setBalance(double balance) {
      this.balance = balance;
      return this;
   }

   public void populate(double amount) {
      if (amount <= 0) throw new IllegalStateException("Cannot populate with negative amount!");
      setBalance(balance + amount);
   }

   public void retract(double amount) {
      if (amount <= 0) throw new IllegalStateException("Cannot retract a negative amount!");
      if (amount > balance) throw new IllegalStateException("Cannot retract an amount larger than balance!");
      setBalance(balance - amount);
   }

   public List<Subscription> getSubscriptions() {
      return new ArrayList<>(subscriptions);
   }

   public List<Subscription> getActiveSubscriptions() {
      List<Subscription> activeSubscriptions = new ArrayList<>();

      for (Subscription subscription : subscriptions) {
         if (!subscription.isCanceled()) {
            activeSubscriptions.add(subscription);
         }
      }

      return activeSubscriptions;
   }

   public Subscription getSubscriptionTo(Service service) {
      for (Subscription subscription : subscriptions) {
         if (subscription.getService().getId() == service.getId()) {
            return subscription;
         }
      }
      return null;
   }

   public boolean isSubscribedTo(Service service) {
      return getSubscriptionTo(service) != null;
   }

   public List<Bill> getAutomaticallyPayableBills() {
      List<Bill> autoPayBills = new ArrayList<>();

      for (Subscription subscription : subscriptions) {
         if (!subscription.isPaidAutomatically())
            continue;
         autoPayBills.addAll(subscription.getUnpaidBills());
      }
      return autoPayBills;
   }
}
