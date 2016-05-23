package ro.unitbv.servicesupplier.repository.persistence.account;

import ro.unitbv.servicesupplier.model.dto.ProviderAccountDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
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
@DiscriminatorValue("PVDR")
public class ProviderAccount extends Account {

   private String name;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "provider")
   private Set<Service> providedServices;

   protected ProviderAccount() {
      // Required by JPA.
   }

   public ProviderAccount(ProviderAccountDto accountDetails) {
      super(accountDetails.getCredentials());
      this.name = accountDetails.getName();
      providedServices = new HashSet<>();
   }

   public void offerService(Service service) {
      service.setProvider(this);
      DatabaseManager.getInstance().persist(service);
      DatabaseManager.getInstance().refresh(this);
   }

   public void discontinueService(Service service) {
      DatabaseManager.getInstance().beginTransaction();
      if (providedServices.remove(service)) {
         DatabaseManager.getInstance().remove(service);
         for (Subscription subscription : service.getSubscriptions()) {
            DatabaseManager.getInstance().remove(subscription);
         }
      }
      DatabaseManager.getInstance().commitTransaction();
      for (Subscription subscription : service.getSubscriptions()) {
         DatabaseManager.getInstance().refresh(subscription.getSubscriber());
      }
   }

   public String getName() {
      return name;
   }

   public ProviderAccount setName(String name) {
      DatabaseManager.getInstance().beginTransaction();
      this.name = name;
      DatabaseManager.getInstance().commitTransaction();
      return this;
   }

   public List<Service> getProvidedServices() {
      return new ArrayList<>(providedServices);
   }
}
