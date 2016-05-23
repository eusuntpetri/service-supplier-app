package ro.unitbv.servicesupplier.repository.persistence.account;

import ro.unitbv.servicesupplier.model.dto.AccountCredentialDto;
import ro.unitbv.servicesupplier.repository.mysql.DatabaseManager;
import ro.unitbv.servicesupplier.repository.persistence.Persistable;
import ro.unitbv.servicesupplier.repository.persistence.notice.Notice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

/**
 * Created by Petri on 14-May-16.
 */
@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "ACC_TYPE")
@MappedSuperclass
public abstract class Account implements Persistable {

   @Id
   @GeneratedValue
   private long id;

   private String username;
   private String password;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
   private List<Notice> notices;

   protected Account() {
      // Required by JPA.
   }

   Account(AccountCredentialDto credentials) {
      this.username = credentials.getUsername();
      this.password = credentials.getPassword();
      this.notices = new ArrayList<>();
   }

   @Override
   public long getId() {
      return id;
   }

   public String getUsername() {
      return username;
   }

   public String getPassword() {
      return password;
   }

   public Account setPassword(String password) {
      DatabaseManager.getInstance().beginTransaction();
      this.password = password;
      DatabaseManager.getInstance().commitTransaction();
      return this;
   }

   public List<Notice> getNotices() {
      return notices;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Account account = (Account) o;

      return id == account.id;

   }

   @Override
   public int hashCode() {
      return (int) (id ^ (id >>> 32));
   }
}
