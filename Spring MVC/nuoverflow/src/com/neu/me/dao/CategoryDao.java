package com.neu.me.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.neu.me.exception.UserException;
import com.neu.me.pojo.Category;
import com.neu.me.pojo.useraccount;





public class CategoryDao extends DAO{

	  public CategoryDao() {
	    }

	    public List<Category> getAll()
	           {
	    	List<Category> catlist = null;
	        try {
	            
	        	
	            Query q = getSession().createQuery("from Category");
	            catlist = q.list();
	            return catlist;
	            
	        } catch (HibernateException e) {
	            
	           
	        }
	        finally{
				close();
			}
			return null;
	    }

	    public Category get(int catid)
	            throws UserException {
	        try {
	            begin();
	            Query q = getSession().createQuery("from Category where catId = :catid");
	            q.setInteger("catid", catid);
	            Category cat = (Category) q.uniqueResult();
	            commit();
	            return cat;
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not get category " + catid, e);
	        }
	        finally{
				close();
			}
	    }
	    
	    public Category create(String title) throws UserException {
	        try {
	            begin();
	            Category cat = new Category(title);
	            getSession().save(cat);
	            commit();
	            return null;
	        } catch (HibernateException e) {
	            rollback();
	            //throw new AdException("Could not create the category", e);
	            throw new UserException("Exception while creating category: " + e.getMessage());
	        }
	        finally{
				close();
			}
	    }

	    public void update(Category cat) throws UserException {
	        try {
	            begin();
	            
	            getSession().update(cat);
	            commit();
	            
	        } catch (HibernateException e) {
	            rollback();
	            //throw new AdException("Could not create the category", e);
	            throw new UserException("Exception while updating category: " + e.getMessage());
	        }
	        finally{
				close();
			}
	    }
	    
	    

	    public void delete(Category cat)
	            throws UserException {
	        try {
	            begin();
	            getSession().delete(cat);
	            System.out.println("deleteting");
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not delete category " + cat.getCategoryName(), e);
	        }
	        finally{
				close();
			}
	    }

}
