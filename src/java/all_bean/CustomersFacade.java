/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all_bean;

import all.Customers;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author James
 */
@Stateless
public class CustomersFacade extends AbstractFacade<Customers> {

    @PersistenceContext(unitName = "shop_onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Customers getByUsernameAndPassword(String username, String password) {
        Customers c = null;
        try {
        c = (Customers) em.createNamedQuery("Customers.checkCredentials")
                .setParameter("username", username).setParameter("password", password).getSingleResult();
        return c;
        } catch (NoResultException e) {
            return null;
        }
       
    }

    public CustomersFacade() {
        super(Customers.class);
    }
    
    @Override
    public Customers find(Object id) throws NoResultException {
        Customers c = null;
        c = (Customers) em.createNamedQuery("Customers.findByCustomerId")
                .setParameter("customerId", id).getSingleResult();
        return c;
    }
    
    public Customers getByName(String n) throws NoResultException {
        Customers current = null;
        current = (Customers) em.createNamedQuery("Customers.findByName")
                .setParameter("name", n).getSingleResult();
        return current;
    }
}