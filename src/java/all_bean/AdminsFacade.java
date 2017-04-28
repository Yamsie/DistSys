/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all_bean;

import all.Admins;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author James
 */
@Stateless
public class AdminsFacade extends AbstractFacade<Admins> {

    @PersistenceContext(unitName = "shop_onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Admins getByUsernameAndPassword(String username, String password) {
        Admins c = null;
        try {
        c = (Admins) em.createNamedQuery("Admins.checkCredentials")
                .setParameter("name", username).setParameter("password", password).getSingleResult();
        return c;
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public AdminsFacade() {
        super(Admins.class);
    }
    
}
