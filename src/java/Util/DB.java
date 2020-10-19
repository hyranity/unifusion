/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Models.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.*;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.hibernate.QueryException;

/**
 *
 * @author mast3
 */
public class DB {

    private EntityManager em;
    private UserTransaction utx;

    public DB(EntityManager em, UserTransaction utx) {
        this.em = em;
        this.utx = utx;
    }

    public void insert(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();

        }  catch(ConstraintViolationException ex){
            System.out.println(ex.getConstraintViolations());
            System.out.println(ex.getMessage());
        }catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | javax.transaction.RollbackException | SystemException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void update(Object object) {
        try {
            utx.begin();
            em.merge(object);
            utx.commit();

        } catch(javax.validation.ConstraintViolationException ex){
            System.out.println("CONSTRAINTS");
            System.out.println(ex.getConstraintViolations() );
        }catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | javax.transaction.RollbackException | SystemException ex) {
            System.out.println(ex.getMessage());
        } catch(Exception ex){
            System.out.println("Unknown error");
        }
    }
     
      public void delete(Object object) {
        try {
            utx.begin();
            object = em.merge(object);
            em.remove(object);
            utx.commit();

        } catch(javax.validation.ConstraintViolationException ex){
            System.out.println("CONSTRAINTS");
            System.out.println(ex.getConstraintViolations());
        }catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | javax.transaction.RollbackException | SystemException ex) {
            System.out.println(ex.getMessage());
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Unknown error");
        }
    }

    public <T> T getSingleResult(String keyName, String value, java.lang.Class<T> classType) {

        Query query = null;
        try {
            query = em.createQuery("select s from " + classType.getName() + " s where s." + keyName + " = :id", classType).setParameter("id", value);

        } catch (Exception ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        T item = null;
        
        try{
            item = classType.cast(query.getSingleResult());
        }catch(NullPointerException | NoResultException ex){
            System.out.println("No data found");
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return item;

    }
    
    public <T> ArrayList<T> getList(java.lang.Class<T> classType, Query query){
        List results = null;
        try {
             results = query.getResultList();
            
        } catch (QueryException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<T>(results);
    }

}
