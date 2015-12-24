package pomonitor.entity;

import java.util.List;

/**
 * Interface for SynonymouswordDAO.
 * @author MyEclipse Persistence Tools
 */

public interface ISynonymouswordDAO {
		/**
	 Perform an initial save of a previously unsaved Synonymousword entity. 
	 All subsequent persist actions of this entity should use the #update() method.
	 This operation must be performed within the a database transaction context for the entity's data to be permanently saved to the persistence store, i.e., database. 
	 This method uses the {@link javax.persistence.EntityManager#persist(Object) EntityManager#persist} operation.
	 	 
	 * <pre> 
	 *   EntityManagerHelper.beginTransaction();
	 *   ISynonymouswordDAO.save(entity);
	 *   EntityManagerHelper.commit();
	 * </pre>
	   @param entity Synonymousword entity to persist
	  @throws RuntimeException when the operation fails
	 */
    public void save(Synonymousword entity);
    /**
	 Delete a persistent Synonymousword entity.
	  This operation must be performed 
	 within the a database transaction context for the entity's data to be
	 permanently deleted from the persistence store, i.e., database. 
	 This method uses the {@link javax.persistence.EntityManager#remove(Object) EntityManager#delete} operation.
	 	  
	 * <pre>
	 *   EntityManagerHelper.beginTransaction();
	 *   ISynonymouswordDAO.delete(entity);
	 *   EntityManagerHelper.commit();
	 *   entity = null;
	 * </pre>
	   @param entity Synonymousword entity to delete
	 @throws RuntimeException when the operation fails
	 */
    public void delete(Synonymousword entity);
   /**
	 Persist a previously saved Synonymousword entity and return it or a copy of it to the sender. 
	 A copy of the Synonymousword entity parameter is returned when the JPA persistence mechanism has not previously been tracking the updated entity. 
	 This operation must be performed within the a database transaction context for the entity's data to be permanently saved to the persistence
	 store, i.e., database. This method uses the {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge} operation.
	 	 
	 * <pre>
	 *   EntityManagerHelper.beginTransaction();
	 *   entity = ISynonymouswordDAO.update(entity);
	 *   EntityManagerHelper.commit();
	 * </pre>
	   @param entity Synonymousword entity to update
	 @return Synonymousword the persisted Synonymousword entity instance, may not be the same
	 @throws RuntimeException if the operation fails
	 */
	public Synonymousword update(Synonymousword entity);
	public Synonymousword findById( Integer id);
	 /**
	 * Find all Synonymousword entities with a specific property value.  
	 
	  @param propertyName the name of the Synonymousword property to query
	  @param value the property value to match
	  	  @return List<Synonymousword> found by query
	 */
	public List<Synonymousword> findByProperty(String propertyName, Object value
		);
	public List<Synonymousword> findByCategory(Object category
		);
	public List<Synonymousword> findByWords(Object words
		);
	/**
	 * Find all Synonymousword entities.
	  	  @return List<Synonymousword> all Synonymousword entities
	 */
	public List<Synonymousword> findAll(
		);	
}