package com.neu.me.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.me.exception.UserException;
import com.neu.me.pojo.Category;
import com.neu.me.pojo.Person;
import com.neu.me.pojo.useraccount;



public class UserDao extends DAO{

	 public UserDao() {
	    }

	    public useraccount get(String username)
	            throws UserException {
	        try {
	        	System.out.println("inside DAO");
	            begin();
	            Query q = getSession().createQuery("from useraccount where status = 'Active' and username = :username");
	            q.setString("username", username);
	            useraccount useracc = (useraccount) q.uniqueResult();
	            commit();
	            return useracc;
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not get user " + username, e);
	        }
	        finally{
				close();
			}
	    }
	    
	    public boolean checkUser(String username)
	            throws UserException {
	        try {
	        	
	            Query q = getSession().createQuery("from UserAccount where status = 'Active' and username = :username");
	            q.setString("username", username);
	            useraccount useracc = (useraccount) q.uniqueResult();
	           
	            if(useracc != null)
	            return true;
	            else
	            	return false;
	        } catch (HibernateException e) {
	            
	           return false;
	        }
	        finally{
				close();
			}
	    }
	   
	    public useraccount getById(int uid)
	            throws UserException {
	        try {
	        	System.out.println("inside DAO");
	         
	            useraccount useracc = (useraccount) getSession().get(useraccount.class, uid);
	         
	            return useracc;
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not get user ", e);
	        }
	        finally{
				close();
			}
	    }
	   
	    public List<useraccount> getAll()
	            throws UserException {
	        try {
	        	
	            begin();
	            Query q = getSession().createQuery("from UserAccount");
	            
	            List<useraccount> useracc = q.list();
	            commit();
	            return useracc;
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not get user ");
	        }
	        finally{
				close();
			}
	    }
	   
	    public useraccount getUser(String username,String password)
	            throws UserException {
	        try {
	        	System.out.println("inside DAO getuser");
	            begin();
	            System.out.println("create query");
	            Query q = getSession().createQuery("from useraccount where username = :username and password =:password");
	            
	            q.setString("username", username);
	            q.setString("password", password);
	            System.out.println(q);
	            System.out.println("parameter passed");
	            useraccount useracc = (useraccount) q.uniqueResult();
	            System.out.println("retriving result");
	            if(useracc == null)
	            {
	            	throw new UserException("Could not get user " + username);	
	            }
	            commit();
	            return useracc;
	        } catch (HibernateException e) {
	            rollback();
	            System.out.println("user not found exception");
	            throw new UserException("Could not get user " + username, e);
	        }
	        finally{
				close();
			}
	    }

	    public useraccount create(String valfirst,String vallast,String valemail,String phone,int age,String username,String pass,String role)
	            throws UserException {
	        try {
	            begin();
	           
	            Person person = new Person(valfirst,vallast,valemail,phone,age);
	            useraccount useracc = new useraccount(username, pass,role);
	            person.setUserAccount(useracc);
	            useracc.setPerson(person);
	            getSession().save(useracc);
	            commit();
	            return useracc;
	        } catch (HibernateException e) {
	            rollback();
	            //throw new AdException("Could not create user " + username, e);
	            throw new UserException("Exception while creating user: " + e.getMessage());
	        }
	        finally{
				close();
			}
	    }

	    public void delete(useraccount user)
	            throws UserException {
	        try {
	            begin();
	            getSession().delete(user);
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not delete user " + user.getUsername(), e);
	        }
	        finally{
				close();
			}
	    }
	    public void update(useraccount user) throws UserException {
	        try {
	            begin();
	            
	            getSession().update(user);
	            commit();
	            
	        } catch (HibernateException e) {
	            rollback();
	            //throw new AdException("Could not create the category", e);
	            throw new UserException("Exception while updating useraccount: " + e.getMessage());
	        }
	        finally{
				close();
			}
	    }
}
