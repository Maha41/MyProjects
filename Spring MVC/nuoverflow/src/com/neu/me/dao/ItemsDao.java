package com.neu.me.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.neu.me.exception.UserException;
import com.neu.me.pojo.Jobs;
import com.neu.me.pojo.Items;
import com.neu.me.pojo.Like;
import com.neu.me.pojo.Person;
import com.neu.me.pojo.SubCategory;
import com.neu.me.pojo.useraccount;


public class ItemsDao extends DAO {

	 public ItemsDao() {
	    }

	    public List<Items> getAll()
	           {
	        try {
	            
	            String hql = "FROM Items order by postDate desc";
	            Query q = getSession().createQuery(hql);
	            q.setCacheable(true);
	            q.setComment("Items selected "+hql);
	            List<Items> itemlist = new ArrayList<Items>();
	            itemlist = q.list() ;
	            
	            
	            return itemlist;
	        } catch (HibernateException e) {
	            rollback();
	           
	        }
	        finally{
				close();
			}
			return null;
	    }

	    public List<Items> getByPersonid(int personid)
        {
     try {
         begin();
         Query q = getSession().createQuery("FROM Items where personid =:personid ");
         q.setInteger("personid", personid);
         List<Items> itemlist = new ArrayList<Items>();
         itemlist = q.list() ;
         System.out.println("size of herbs"+itemlist.size());
         commit();
         return itemlist;
     } catch (HibernateException e) {
         rollback();
        
     }
     finally{
			close();
		}
		return null;
 }
	    public Items get(int itemid)
	            throws UserException {
	        try {
	            
	            Query q = getSession().createQuery("from Items where itemId = :itemid");
	            q.setInteger("itemid", itemid);
	            Items item = (Items) q.uniqueResult();
	            
	            return item;
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not get item " + itemid, e);
	        }
	        finally{
				close();
			}
	    }
	    
	    public Items create(String itemname,SubCategory subcat, Jobs herb, String desc,String benifits,String proc,int personid) throws UserException {
	        try {
	            begin();
	            
	            Items item = new Items(itemname,subcat,herb,benifits,proc,desc,personid);
	            
	            getSession().save(item);
	            commit();
	            return null;
	        } catch (HibernateException e) {
	            rollback();
	            
	            throw new UserException("Exception while creating item: " + e.getMessage());
	        }
	        finally{
				close();
			}
	    }

	    public void update(Items item) throws UserException {
	        try {
	            begin();
	            
	            getSession().update(item);
	            commit();
	            
	        } catch (HibernateException e) {
	            rollback();
	            
	            throw new UserException("Exception while updating item: " + e.getMessage());
	        }
	        finally{
				close();
			}
	    }
	    
	    

	    public void delete(Items item)
	            throws UserException {
	        try {
	            begin();
	            getSession().delete(item);
	            System.out.println("deleteting");
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not delete Items " + item.getItemName(), e);
	        }
	        finally{
				close();
			}
	    }

	    public boolean checkLikePresent(Items item, Person person)
	    	     throws UserException {
	        try {
	            Criteria cri = getSession().createCriteria(Like.class);
	            cri.add(Restrictions.eq("itemid", item));
	            cri.add(Restrictions.eq("person", person));
	            cri.setProjection(Projections.rowCount());
	            List<Long> result = cri.list();
	            	System.out.println("count of records"+ result.get(0));
	            
	            if(result.get(0) >=1)
	            {
	            	System.out.println("inside if of count");
	            	return true;
	            }
	            else
	            	return false;
	        } catch (HibernateException e) {
	            rollback();
	            
	            return false;
	        }
	        finally{
				close();
			}
	    }
	    public List<Items> searchItems(String key)
	    {
	        try {
	            
	        	Criteria cri = getSession().createCriteria(Items.class);
	        	cri.add(Restrictions.ilike("description", key, MatchMode.ANYWHERE));
	           // Query q = getSession().createQuery("FROM Items where description :key ");
	            //q.setString("key", "%"+key+"%");
	            
	            List<Items> itemlist = new ArrayList<Items>();
	            itemlist = cri.list() ;
	          
	            
	            
	            return itemlist;
	        } catch (HibernateException e) {
	            
	           
	        }
	        finally{
	   			close();
	   		}
	   		return null;
	    }

}
