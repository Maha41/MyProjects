package com.neu.me.dao;

import org.hibernate.HibernateException;

import com.neu.me.exception.UserException;

import com.neu.me.pojo.Comments;
import com.neu.me.pojo.useraccount;

public class CommentDao extends DAO{

	  public CommentDao() {
	    }
	  public Comments getById(int comid)
	            throws UserException {
	        try {
	        	System.out.println("inside DAO");
	         
	            Comments com = (Comments) getSession().get(Comments.class, comid);
	         
	            return com;
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not get comment ", e);
	        }
	        finally{
				close();
			}
	    }
	  public void delete(Comments com)
	            throws UserException {
	        try {
	            begin();
	            getSession().delete(com);
	            System.out.println("deleteting");
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not delete comment ", e);
	        }
	        finally{
				close();
			}
	    }
}
