package com.neu.me.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.neu.me.exception.UserException;
import com.neu.me.pojo.Items;
import com.neu.me.pojo.Like;
import com.neu.me.pojo.Person;

public class LikeDao extends DAO {

	 public LikeDao() {
	    }
	 
	 public Like get(Items item, Person person)
    	     throws UserException {
        try {
            Criteria cri = getSession().createCriteria(Like.class);
            cri.add(Restrictions.eq("itemid", item));
            cri.add(Restrictions.eq("person", person));
	          cri.setMaxResults(1);
	          List<Like> like = cri.list();
	            return like.get(0);
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not get like ", e);
	        }
	        finally{
				close();
			}
	    }
	  public void delete(Like like)
	            throws UserException {
	        try {
	            begin();
	            getSession().delete(like);
	            System.out.println("deleteting");
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not delete like ", e);
	        }
	        finally{
				close();
			}
	    }

}
