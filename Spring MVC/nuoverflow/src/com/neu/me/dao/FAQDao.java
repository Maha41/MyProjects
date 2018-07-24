package com.neu.me.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.me.exception.UserException;

import com.neu.me.pojo.FAQ;



public class FAQDao extends DAO {

	 public FAQDao() {
	    }

	    public List<FAQ> getAll()
	           {
	        try {
	            
	            Query q = getSession().createQuery("from FAQ");
	            List<FAQ> faqlist = new ArrayList<FAQ>();
	            faqlist = q.list() ;
	            System.out.println("size of faq"+faqlist.size());
	            
	            return faqlist;
	        } catch (HibernateException e) {
	            rollback();
	           
	        }
	        finally{
				close();
			}
			return null;
	    }
	    
	    public FAQ get(int faqid)
	            throws UserException {
	        try {
	            
	            Query q = getSession().createQuery("from FAQ where faqId = :faqid");
	            q.setInteger("faqid", faqid);
	            FAQ faq = (FAQ) q.uniqueResult();
	            
	            return faq;
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not get FAq " + faqid, e);
	        }
	        finally{
				close();
			}
	    }
	    
	    public FAQ create(String question, String answer) throws UserException {
	        try {
	            begin();
	            FAQ faq = new FAQ(question,answer);
	            getSession().save(faq);
	            commit();
	            return null;
	        } catch (HibernateException e) {
	            rollback();
	            //throw new AdException("Could not create the category", e);
	            throw new UserException("Exception while creating FAQ: " + e.getMessage());
	        }
	        finally{
				close();
			}
	    }

	    public void update(FAQ faq) throws UserException {
	        try {
	            begin();
	            
	            getSession().update(faq);
	            commit();
	            
	        } catch (HibernateException e) {
	            rollback();
	            //throw new AdException("Could not create the category", e);
	            throw new UserException("Exception while updating faq: " + e.getMessage());
	        }
	        finally{
				close();
			}
	    }
	    
	    

	    public void delete(FAQ faq)
	            throws UserException {
	        try {
	            begin();
	            getSession().delete(faq);
	            System.out.println("deleteting");
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not delete faq " + faq.getFaqId(), e);
	        }
	        finally{
				close();
			}
	    }

}
