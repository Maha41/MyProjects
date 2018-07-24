package com.neu.me.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.me.exception.UserException;
import com.neu.me.pojo.Person;


public class PersonDao extends DAO {

	public PersonDao()
	{
		
	}
    public List<Person> getAll()
            throws UserException {
        try {
        	
            begin();
            Query q = getSession().createQuery("from Person");
            
            List<Person> personList = q.list();
            commit();
            return personList;
        } catch (HibernateException e) {
            rollback();
            throw new UserException("Could not get person ");
        }
        finally{
			close();
		}
    }

}
