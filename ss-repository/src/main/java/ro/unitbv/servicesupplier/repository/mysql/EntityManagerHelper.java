package ro.unitbv.servicesupplier.repository.mysql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Petri on 15-May-16.
 */
class EntityManagerHelper {

   private static final EntityManagerFactory entityManagerFactory;
   private static final ThreadLocal<EntityManager> localEntityManager;

   static {
      entityManagerFactory = Persistence.createEntityManagerFactory("ss-provider");
      localEntityManager = new ThreadLocal<>();
   }

   static EntityManager getEntityManager() {
      EntityManager eManager = localEntityManager.get();

      if (eManager == null) {
         eManager = entityManagerFactory.createEntityManager();
         localEntityManager.set(eManager);
      }

      return eManager;
   }

   static void beginTransaction() {
      getEntityManager().getTransaction().begin();
   }

   static void rollbackTransaction() {
      getEntityManager().getTransaction().rollback();
   }

   static void commitTransaction() {
      getEntityManager().getTransaction().commit();
   }

   static void closeManager() {
      EntityManager em = localEntityManager.get();
      if (em == null) {
         return;
      }
      em.close();
   }
}
