package com.neu.me.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.me.exception.UserException;
import com.neu.me.pojo.Category;
import com.neu.me.pojo.SubCategory;

public class SubCategoryDao extends DAO {

	  public SubCategoryDao() {
	    }

	    public List<SubCategory> getAll()
	           {
	        try {
	            begin();
	            Query q = getSession().createQuery("from SubCategory");
	            List<SubCategory> subcatlist = new ArrayList<SubCategory>();
	            subcatlist = q.list() ;
	            System.out.println("size of herbs"+subcatlist.size());
	            commit();
	            return subcatlist;
	        } catch (HibernateException e) {
	            rollback();
	           
	        }
	        finally{
				close();
			}
			return null;
	    }

	    public SubCategory get(int subcatid)
	            throws UserException {
	        try {
	            begin();
	            Query q = getSession().createQuery("from SubCategory where subCatId = :subcatid");
	            q.setInteger("subcatid", subcatid);
	            SubCategory subcat = (SubCategory) q.uniqueResult();
	            commit();
	            return subcat;
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not get category " + subcatid, e);
	        }
	        finally{
				close();
			}
	    }
	    
	    public List<SubCategory> getListByCategory(Category cat)
	            throws UserException {
	        try {
	            begin();
	            List<SubCategory> subcatlist = new ArrayList<SubCategory>();
	            Query q = getSession().createQuery("from SubCategory where category = :cat");
	            q.setInteger("cat", cat.getCatId());
	            subcatlist = q.list();
	            commit();
	            return subcatlist;
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not get subcategory " );
	        }
	        finally{
				close();
			}
	    }
	    
	    public SubCategory create(String title,int catid) throws UserException {
	        try {
	            begin();
	            System.out.println("inside saving subcategory");
	            SubCategory subcat = new SubCategory(title,catid);
	            System.out.println("saved subcat"+subcat.getSubCatName());
	            getSession().save(subcat);
	            System.out.println("final commit");
	            commit();
	            System.out.println("existing");
	            return subcat;
	        } catch (HibernateException e) {
	            rollback();
	            //throw new AdException("Could not create the category", e);
	            throw new UserException("Exception while creating category: " + e.getMessage());
	        }
	        finally{
				close();
			}
	    }

	    public SubCategory update(SubCategory subcat) throws UserException {
	        try {
	            begin();
	            
	            getSession().update(subcat);
	            commit();
	            return null;
	        } catch (HibernateException e) {
	            rollback();
	            //throw new AdException("Could not create the category", e);
	            throw new UserException("Exception while updating subcategory: " + e.getMessage());
	        }
	        finally{
				close();
			}
	    }
	    public void delete(SubCategory subcat)
	            throws UserException {
	        try {
	            begin();
	            getSession().delete(subcat);
	            System.out.println("deleteting");
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not delete subcategory " + subcat.getSubCatName(), e);
	        }
	        finally{
				close();
			}
	    }


}
