package ro.unitbv.servicesupplier.server.techno.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri on 20-May-16.
 */
public class PrivatePersistenceManager {

   private static final PrivatePersistenceManager INSTANCE = new PrivatePersistenceManager();

   private ThreadLocal<EntityManager> localEntityManager = new ThreadLocal<>();

   private PrivatePersistenceManager() {
      // Singleton.
   }

   public static PrivatePersistenceManager getInstance() {
      return INSTANCE;
   }

   private EntityManager entityManager() {
      EntityManager eManager = localEntityManager.get();

      if (eManager == null) {
         eManager = Persistence.createEntityManagerFactory("ts-provider").createEntityManager();
         localEntityManager.set(eManager);
      }

      return eManager;
   }

   public void beginTransaction() {
      entityManager().getTransaction().begin();
   }

   public void rollbackTransaction() {
      entityManager().getTransaction().rollback();
   }

   public void commitTransaction() {
      entityManager().getTransaction().commit();
   }

   public <T extends PrivatelyPersistable> T persist(T entity) {
      beginTransaction();
      entityManager().persist(entity);
      commitTransaction();
      return entity;
   }

   public <T extends PrivatelyPersistable> void remove(T entity) {
      beginTransaction();
      entityManager().remove(entity);
      commitTransaction();
   }

   public <T extends PrivatelyPersistable> List<T> findAll(Class<T> clazz) {
      List<T> results = entityManager()
            .createQuery("SELECT t FROM " + clazz.getSimpleName() + " t", clazz)
            .getResultList();
      for (T t : results) {
         refresh(t);
      }
      return new ArrayList<>(results);
   }

   public <T extends PrivatelyPersistable> T refresh(T entity) {
      try {
         entityManager().refresh(entity);
         return entity;
      } catch (IllegalArgumentException e) {
         return merge(entity);
      }
   }

   public <T extends PrivatelyPersistable> T merge(T entity) {
      return entityManager().merge(entity);
   }

   public void close() {
      EntityManager em = localEntityManager.get();
      if(em == null) {
         return;
      }
      em.close();
   }
}
