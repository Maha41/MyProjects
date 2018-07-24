package com.neu.me.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.me.exception.UserException;

import com.neu.me.pojo.Jobs;



public class JobsDao extends DAO {

    public JobsDao() {
    }

    public List<Jobs> getAll()
           {
        try {
            String hql = "from Jobs";
            Query q = getSession().createQuery(hql);
            List<Jobs> joblist = new ArrayList<Jobs>();
           q.setCacheable(true);
           q.setComment("HQL job query"+hql);

           joblist = q.list() ;
            System.out.println("size of jobs"+joblist.size());

            
            return joblist;
        } catch (HibernateException e) {
            
           
        }
        finally{
			close();
		}
		return null;
    }

    public Jobs get(int jobid)
            throws UserException {
        try {
            begin();
            Query q = getSession().createQuery("from Jobs where jobid = :jobid");
            q.setInteger("jobid", jobid);
            Jobs job = (Jobs) q.uniqueResult();
            commit();
            return job;
        } catch (HibernateException e) {
            rollback();
            throw new UserException("Could not get job " + jobid, e);
        }
        finally{
			close();
		}
    }

   public Jobs create(Jobs job)
            throws UserException {
        try {
      
        	begin();
            getSession().save(job);
            commit();
            return job;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new UserException("Exception while creating job: " + e.getMessage());
        }
    }

    public void delete(Jobs job)
            throws UserException {
        try {
            begin();
            getSession().delete(job);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new UserException("Could not delete job " + job.getJobName(), e);
        }
    }
    
    public void update(Jobs job) throws UserException {
        try {
            begin();
            
            getSession().update(job);
            commit();
            
        } catch (HibernateException e) {
            rollback();

            throw new UserException("Exception while updating job: " + e.getMessage());
        }
        finally{
			close();
		}
    }
    
}
