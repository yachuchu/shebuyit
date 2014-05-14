
package com.shebuyit.common.dao.impl;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang.NotImplementedException;
import org.apache.openjpa.persistence.QueryImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.util.Assert;

import com.shebuyit.common.dao.GenericDAO;
import com.shebuyit.common.util.Paging;
import com.shebuyit.common.util.ReflectionUtils;

public abstract class JPAGenericDAOImpl<T, PK extends Serializable> extends
		JpaDaoSupport implements GenericDAO<T, PK> {
	
	protected Class<T> entityClass;
	
	private static final String orderByTime = " desc"; 

	
	@SuppressWarnings("unchecked")
	public JPAGenericDAOImpl() {
		this.entityClass = ReflectionUtils.getSuperClassGenricTypes(getClass()).get(0);
	}
	
	public int count() {
		String sql = "select count(obj) from " + entityClass.getSimpleName() + " obj";
		int count = ((Integer)getJpaTemplate().find(sql).get(0)).intValue();
		logger.debug(entityClass.getSimpleName() + " count:" + count);
		return count;
	}

	public boolean exist(PK id) {
		return false;
	}

	public void flush() {
		 getJpaTemplate().flush();
	}
	
	public T get(PK id) {
		return getJpaTemplate().find(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		logger.debug("Get all record of :" + entityClass.getSimpleName());
		List<T> list = getJpaTemplate().find("select obj from " + entityClass.getSimpleName() + " obj");
		return list;
	}

	public List<T> getAll(Paging paging) {
		return find("select obj from " + entityClass.getSimpleName() + " obj", paging);
	}
	
	public List<T> getAll(String orderBy, boolean isAsc) {
		String ascStr = isAsc ? "asc" : "desc";
		String queryStr = "select obj from " + entityClass.getSimpleName() + " obj order by obj." + orderBy + " "
				+ ascStr;
		logger.debug("Query String: " + queryStr);
		return find(queryStr);
	}
	
	public List<T> getAll(Paging paging, String orderBy, boolean isAsc) {
		String ascStr = isAsc ? "asc" : "desc";
		String queryStr = "select obj from " + entityClass.getSimpleName() + " obj order by obj." + orderBy + " "
				+ ascStr;
		logger.debug("Query String: " + queryStr);
		return find(queryStr, paging);
	}

	public List<T> getByTime(Date start, Date end, String timeProperty, boolean isAsc) {
		Assert.notNull(timeProperty, "id cannot be null!");
		Assert.notNull(start, "id cannot be null!");
		String ascStr = isAsc ? "asc" : "desc";
		if (end == null)
			end = new Date();
		String queryStr = "select o from " + entityClass.getSimpleName()+" o where o."+timeProperty+" between :start and :end";
		queryStr += " order by o." + timeProperty  + " " + ascStr;
		return find(queryStr, start, end);
	}

	public List<T> getByTime(Date start, Date end, String timeProperty, boolean isAsc,
			Paging paging) {
		String ascStr = isAsc ? "asc" : "desc";
		if (end == null)
			end = new Date();
		String queryStr = "select o from " + entityClass.getSimpleName()
				+ " o where o." + timeProperty + " between :start and :end";
		queryStr += " order by o." + timeProperty + " " + ascStr;
		return find(queryStr, paging, start, end);
	}
	
	public void remove(T entity) {
		Assert.notNull(entity, "entity cannot be null!");
		getJpaTemplate().remove(entity);
		logger.debug(" Deleting entity executed.");
	}
	
	public void remove(final PK id) {
		T t = getJpaTemplate().find(entityClass, id);
		getJpaTemplate().remove(t);
		logger.debug("deleting entity[" + entityClass.getSimpleName() + "] executed, id=" + id);
	}

	public void save(T entity) {
		Assert.notNull(entity, "entity cannot be null!");
		getJpaTemplate().persist(entity);
		logger.debug(" Creating Entity["+ entityClass.getInterfaces() +"] executed. ");
	}

	public void update(T entity) {
		Assert.notNull(entity, "entity cannot be null!");
		getJpaTemplate().merge(entity);
		logger.debug(" Updating Entity executed. ");
	}
	
	/**
	 * Query all records in specific page with condition
	 * 
	 * @param paging
	 * @param condition - query condition (where ...)
	 * @return records found
	 */
	@Deprecated
	public List<T> getAllInCondition(Paging paging, final String condition) {
		//preparePagingInCondition(paging, condition);
		return find("select obj from " + entityClass.getSimpleName() + " obj where obj." + condition, paging);
	}
	
	/**
	 * Query all records in specific page with condition - sort the records before paging
	 * 
	 */
	@Deprecated
	public List<T> getAllInCondition(Paging paging, String orderBy, boolean isAsc, final String condition) {
		String ascStr = isAsc ? "asc" : "desc";
		String queryStr = "select obj from " + entityClass.getSimpleName() + " obj where obj." + condition 
				+ " order by obj." + orderBy + " " + ascStr;
		logger.debug("Query String: " + queryStr);
		//preparePagingInCondition(paging, condition);
		return find(queryStr, paging);
	}
	
	// isvport protected
	@SuppressWarnings("unchecked")
	protected List<T> find(String queryString, final Object... values) throws DataAccessException {
		Assert.notNull(queryString, "queryString cannot be null!");		
		return getJpaTemplate().find(queryString, values);
	}

	protected List<T> find(String queryString) throws DataAccessException {
		return find(queryString, (Object[]) null);
	}

	protected List<T> find(String queryString, final Paging paging) throws DataAccessException {
		return find(queryString, paging, (Object[]) null);
	}

	@SuppressWarnings("unchecked")
	protected List<T> find(final String queryString, final Paging paging, final Object... values)
			throws DataAccessException {
		Assert.notNull(queryString, "queryString cannot be null!");
		//preparePagingInNamedQuery(paging);
		return getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createQuery(queryString);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i + 1, values[i]);
					}
				}
				preparePaging(queryObject, paging);
				queryObject.setHint("javax.persistence.cache.retrieveMode","USE"); 
				return queryObject.getResultList();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> findByNamedQuery(String queryName) {
		Assert.notNull(queryName, "queryName cannot be null!");
		return getJpaTemplate().findByNamedQuery(queryName);
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByNamedQuery(String queryName, Object... values) {
		Assert.notNull(queryName, "queryName cannot be null!");
		return getJpaTemplate().findByNamedQuery(queryName, values);
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> findByNamedQueryAndNamedParams(final String queryName, final Map<String, ? extends Object> params) {
		Assert.notNull(queryName, "queryName cannot be null!");
		return getJpaTemplate().findByNamedQueryAndNamedParams(queryName, params);
	}
	
	protected List<T> findByNamedQuery(String queryName, Paging paging) throws DataAccessException {
		return findByNamedQuery(queryName, paging, (Object[]) null);
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByNamedQuery(final String queryName, final Paging paging, final Object... values)
			throws DataAccessException {
		//preparePagingInNamedQuery(paging,queryName,values);
		return getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createNamedQuery(queryName);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i + 1, values[i]);
					}
				}
				preparePaging(queryObject, paging);
				return queryObject.getResultList();
			}
		});
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByNamedQueryAndNamedParams(final String queryName, final Paging paging,
			final Map<String, ? extends Object> params) throws DataAccessException {
		//preparePagingIn(paging);
		return getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createNamedQuery(queryName);
				if (params != null) {
					for (Object element : params.entrySet()) {
						Map.Entry<String, Object> entry = (Map.Entry<String, Object>) element;
						queryObject.setParameter(entry.getKey(), entry.getValue());
					}
				}
				preparePaging(queryObject, paging);
				return queryObject.getResultList();
			}
		});
	}
	
	protected List<T> findByProperty(String propertyName, final Object value) {
		Assert.notNull(propertyName, "propertyName cannot be null");
		Assert.notNull(value, "value connot be null");
		String sql = "select obj from " + entityClass.getSimpleName() + " obj where obj." + propertyName + "=:value";
		return find(sql, value);
	}
	
	protected List<T> findByProperty(String propertyName, final Object value, String orderProperty, boolean isAsc) {
		Assert.notNull(propertyName, "propertyName cannot be null");
		Assert.notNull(value, "value connot be null");
		StringBuffer queryString = new StringBuffer("select obj from " + entityClass.getSimpleName() + " obj where obj." + propertyName + "=:value");
		String ascStr = isAsc ? "asc" : "desc";
		queryString.append(" order by obj." + orderProperty + " " + ascStr);
		return find(queryString.toString(), value);
	}
	
	protected List<T> findByProperty(String propertyName, final Object value, String orderProperty, boolean isAsc, Paging paging) {
		Assert.notNull(propertyName, "propertyName cannot be null");
		Assert.notNull(value, "value connot be null");
		StringBuffer queryString = new StringBuffer("select obj from " + entityClass.getSimpleName() + " obj where obj." + propertyName + "=:value");
		String ascStr = isAsc ? "asc" : "desc";
		queryString.append(" order by obj." + orderProperty + " " + ascStr);
		return find(queryString.toString(), paging,  value);
	}
	
	protected List<T> findByProperty(String[] propertyName, Object[] value) {
		Assert.notNull(propertyName, "propertyName cannot be null");
		Assert.notNull(value, "value connot be null");
		Assert.isTrue(propertyName.length == value.length, "The property and value not match");
		StringBuffer queryString = new StringBuffer("select obj from " + entityClass.getSimpleName() + " obj where");
		for(int i = 0; i < propertyName.length; i ++) {
			if(i != 0 && i != propertyName.length) queryString.append(" and ");
			queryString.append(" obj." + propertyName[i] + "=:value" + i);
		}
		return find(queryString.toString(), value);
	}
	
	protected List<T> findByProperty(String[] propertyName, Object[] value, Paging paging) {
		Assert.notNull(propertyName, "propertyName cannot be null");
		Assert.notNull(value, "value connot be null");
		Assert.isTrue(propertyName.length == value.length, "The property and value not match");
		StringBuffer queryString = new StringBuffer("select obj from " + entityClass.getSimpleName() + " obj where");
		for(int i = 0; i < propertyName.length; i ++) {
			if(i != 0 && i != propertyName.length) queryString.append(" and ");
			queryString.append(" obj." + propertyName[i] + "=:value" + i);
		}
		return find(queryString.toString(), paging, value);
	}
	protected List<T> findByProperty(String[] propertyName, Object[] value, String orderProperty, boolean isAsc, Paging paging) {
		Assert.notNull(propertyName, "propertyName cannot be null");
		Assert.notNull(value, "value connot be null");
		Assert.isTrue(propertyName.length == value.length, "The property and value not match");
		StringBuffer queryString = new StringBuffer("select obj from " + entityClass.getSimpleName() + " obj where");
		for(int i = 0; i < propertyName.length; i ++) {
			if(i != 0 && i != propertyName.length) queryString.append(" and ");
			queryString.append(" obj." + propertyName[i] + "=:value" + i);
		}
		String ascStr = isAsc ? "asc" : "desc";
		queryString.append(" order by obj." + orderProperty + " " + ascStr);
		return find(queryString.toString(), paging, value);
	}
	
	protected List<T> findByProperty(String[] propertyName, Object[] value, String orderProperty, boolean isAsc) {
		Assert.notNull(propertyName, "propertyName cannot be null");
		Assert.notNull(value, "value connot be null");
		Assert.notNull(orderProperty, "value connot be null");
		Assert.isTrue(propertyName.length == value.length, "The property and value not match");
		StringBuffer queryString = new StringBuffer("select obj from " + entityClass.getSimpleName() + " obj where");
		for(int i = 0; i < propertyName.length; i ++) {
			if(i != 0) queryString.append(" and");
			queryString.append(" obj." + propertyName[i] + "=:value" + i);
		}
		String ascStr = isAsc ? "asc" : "desc";
		queryString.append(" order by obj." + orderProperty + " " + ascStr);
		return find(queryString.toString(), value);
	}
	
	protected JpaTemplate getJpaTemplateObj(){
		return this.getJpaTemplate();
	}
	
	@SuppressWarnings("unchecked")
	private int countRecord(final Query queryObject) {
		Long countNum =  (Long)getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				QueryImpl queryImpl = (QueryImpl)queryObject;
				String queryStr = queryImpl.getQueryString();
				boolean isNative = queryImpl.isNative();
				String countRecordQuery = null;
				if (!isNative) {//JPQL, support "select * from ..." pattern
					String selectPrefix = "select ";
					queryStr = formatQuryString(queryStr);
					int startIndex = queryStr.toLowerCase().indexOf(selectPrefix) + selectPrefix.length();
					int endIndex = queryStr.toLowerCase().indexOf("from");
					String objStr = queryStr.substring(startIndex,endIndex).trim();
					logger.debug("Object String:" + objStr);
					if (objStr != null && objStr.indexOf(",") >=0 && objStr.indexOf("(")>=0){
						throw new NotImplementedException("Pagination for multi object or sub select [" + objStr + "] is not supported by now!");
					}
					
					String topHalfStr = queryStr.substring(0,startIndex);
					logger.debug("top half String:" + topHalfStr);
					
					String bottomHalfStr = queryStr.substring(endIndex,queryStr.length());
					int orderByIndex = bottomHalfStr.toLowerCase().lastIndexOf("order by");
					if (orderByIndex >= 0){
						if (bottomHalfStr.substring(orderByIndex).indexOf(")")<0 ) // sub query excluded
							bottomHalfStr = bottomHalfStr.substring(0,orderByIndex); // remove "order by" clause
					}
					logger.debug("Bottom half String:" + bottomHalfStr);
					//countRecordQuery = queryStr.substring(0,startIndex) + "count( " + objStr + " ) " + queryStr.substring(endIndex,queryStr.length());
					countRecordQuery = topHalfStr + "count( " + objStr + " ) " + bottomHalfStr;
				} else {
					throw new NotImplementedException("Pagination for native query is not supported by now!");
				}
				logger.debug("Counting record query string: " + countRecordQuery);
				Query countQueryObject = em.createQuery(countRecordQuery);
				if (queryImpl.hasPositionalParameters()) {
					for (int i = 0; i < queryImpl.getPositionalParameters().length; i++) {
						countQueryObject.setParameter(i + 1, queryImpl.getPositionalParameters()[i]);
					}
				} else {
					for (String key : (Set<String>)queryImpl.getNamedParameters().keySet()) {
						countQueryObject.setParameter(key, queryImpl.getNamedParameters().get(key));
					}
				}
				return countQueryObject.getSingleResult();
			}
		});
		logger.debug("Total record:" + countNum);
		return countNum.intValue();
	}
	
	static private String formatQuryString(String queryStr) {
		//String formatted = queryStr.toLowerCase();
		String formatted = queryStr.replaceAll("\\s{2,}", " ");
		return formatted;
	}
	
	private void preparePaging(Query queryObject, Paging paging, Object ...values) {
		paging = validatePaging(paging);
		int totalRecord = countRecord(queryObject);
		paging.setTotalRecord(totalRecord);
		int currentRecordNum = paging.getSize() * (paging.getCurrent() -1 );
		queryObject.setFirstResult(currentRecordNum);
		queryObject.setMaxResults(paging.getSize());
		logger.debug("Paging state:" + paging);
	}
	
	private Paging validatePaging(Paging paging) {
		if (paging != null) {
			if (paging.getCurrent() < 0) {
				paging.setCurrent(1);
			}
			if (paging.getSize() <= 0) {
				paging.setSize(0);
			}
			paging.setTotal(0);
		} else {
			paging = new Paging();
		}
		return paging;
	}
	
	public void unitePreparePaging(String queryString, Query queryObject, Paging paging) {
		List result = this.getJpaTemplate().find(queryString);
		paging.setTotalRecord(result.size());
		int currentRecordNum = paging.getSize() * (paging.getCurrent() -1 );
		queryObject.setFirstResult(currentRecordNum);
		queryObject.setMaxResults(paging.getSize());
		logger.debug("Paging state:" + paging);
	}
}
