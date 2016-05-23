package ro.unitbv.servicesupplier.repository.mysql;

import ro.unitbv.servicesupplier.repository.persistence.Persistable;
import ro.unitbv.servicesupplier.repository.persistence.account.Account;

import javax.persistence.NoResultException;
import java.util.List;

import static ro.unitbv.servicesupplier.repository.mysql.EntityManagerHelper.getEntityManager;

/**
 * Created by Petri on 14-May-16.
 */
public class DatabaseManager {

   private static DatabaseManager managerInstance;

   private DatabaseManager() {
      // Singleton.
   }

   public static DatabaseManager getInstance() {
      return managerInstance != null
            ? managerInstance
            : (managerInstance = new DatabaseManager());
   }

   public <T extends Persistable> T persist(T entity) {
      EntityManagerHelper.beginTransaction();
      getEntityManager().persist(entity);
      EntityManagerHelper.commitTransaction();
      return entity;
   }

   public <T extends Persistable> void remove(T entity) {
      getEntityManager().remove(entity);
   }

   public <T extends Persistable> void refresh(T entity) {
      try {
         getEntityManager().refresh(entity);
      } catch (IllegalArgumentException e) {
         getEntityManager().merge(entity);
      }
   }

   public <T extends Persistable> List<T> findAll(Class<T> clazz) {
      List<T> results = EntityManagerHelper.getEntityManager()
            .createQuery("SELECT t FROM " + clazz.getSimpleName() + " t", clazz)
            .getResultList();
      for (T t : results) {
         refresh(t);
      }
      return results;
   }

   public <T extends Persistable> T findById(Class<T> clazz, long id) {
      T t = getEntityManager().find(clazz, id);
      refresh(t);
      return t;
   }

   public Account findAccountByUsername(String username) {
      try {
         return (Account) getEntityManager()
               .createQuery("SELECT t FROM Account t WHERE t.username = ?1")
               .setParameter(1, username)
               .getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   public void beginTransaction() {
      EntityManagerHelper.beginTransaction();
   }

   public void rollbackTransaction() {
      EntityManagerHelper.rollbackTransaction();
   }

   public void commitTransaction() {
      EntityManagerHelper.commitTransaction();
   }

   public void close() {
      EntityManagerHelper.closeManager();
   }
}
