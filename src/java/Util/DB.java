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

        } catch (Exception ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void update(Object object) {
        try {
            utx.begin();
            em.merge(object);
            utx.commit();

        } catch (Exception ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public <T> T get(String id, Class<T> classType) {

        TypedQuery<T> query = null;
        try {
            query = em.createQuery("select s from Student s where s.studid = :id", classType).setParameter("id", id);

        } catch (Exception ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return classType.cast(query.getSingleResult());

    }

}
