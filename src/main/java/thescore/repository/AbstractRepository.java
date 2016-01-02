package thescore.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thescore.interfaces.IRecord;

public abstract class AbstractRepository<PK extends Serializable, T> {
    
   private final Class<T> persistentClass;
    
   @SuppressWarnings("unchecked")
   public AbstractRepository(){
       this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
   }
    
   @Autowired
   private SessionFactory sessionFactory;

   protected Session getSession(){
       return sessionFactory.getCurrentSession();
   }

   @SuppressWarnings("unchecked")
   public T getByKey(PK key) {
       return (T) getSession().get(persistentClass, key);
   }

   public void persist(IRecord record) {
       getSession().persist(record);
   }

   public void delete(IRecord record) {
       getSession().delete(record);
   }
   
   public void deleteRecordById(String entityName, Integer id) {
       Query query = getSession().createSQLQuery("delete from " + entityName + " where id = :id");
       query.setInteger("id", id);
       query.executeUpdate();
   }
   
   protected Criteria createEntityCriteria(){
       return getSession().createCriteria(persistentClass);
   }
   
}
