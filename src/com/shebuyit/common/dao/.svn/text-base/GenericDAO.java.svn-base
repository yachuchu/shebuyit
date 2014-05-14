/*==============================================================
 * IBM Confidential OCO Source Material                           *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * The source code for this program is not published or otherwise *
 * divested of its trade secrets, irrespective of what has        *
 * been deposited with the U.S. Copyright Office.                 *
 * ============================================================   */
package com.shebuyit.common.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;

public interface GenericDAO<T, PK extends Serializable> {

	public void setEntityManagerFactory(EntityManagerFactory emf);

	/**
	 * return the number of the record
	 * 
	 * @return long
	 */
	public int count();

	/**
	 * 
	 */
	public boolean exist(final PK id);
	
	/**
	 * 
	 */
	public void flush();

	/**
	 * get entity by primary key
	 */
	public T get(final PK id);

	/**
	 * get all entities
	 */
	public List<T> getAll();
	/**
	 * get entities list
	 * 
	 * @param orderBy
	 * @param isAsc
	 * @return
	 */
	public List<T> getAll(String orderBy, boolean isAsc);

	public List<T> getByTime(Date start, Date end, String timeProperty, boolean isAsc);

	/**
	 * save entity
	 */
	public void save(final T entity);

	/**
	 * remove entity
	 */
	public void remove(T entity);

	/**
	 * remove entity by primary key
	 */
	public void remove(PK id);

	/**
	 * update entity
	 */
	public void update(T entity);
	
	

}
