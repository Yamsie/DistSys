/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all_bean;

import all.Trousers;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author paddy
 */
@Stateless
public class TrousersFacade extends AbstractFacade<Trousers> {

    @PersistenceContext(unitName = "shop_onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrousersFacade() {
        super(Trousers.class);
    }
    
    @Override
    public Trousers find(Object id) throws NoResultException {
        Trousers c = null;
        c = (Trousers) em.createNamedQuery("Trousers.findByItemId")
                .setParameter("itemId", id).getSingleResult();
        return c;
    }
    
    public Trousers getByName(String n) throws NoResultException {
        Trousers current = null;
        current = (Trousers) em.createNamedQuery("Trousers.findByName")
                .setParameter("name", n).getSingleResult();
        return current;
    }

    public Trousers getAllByType(String t){
        Trousers current = null;
        current = (Trousers) getEntityManager().createNamedQuery("Trousers.findByType")
                .setParameter("type", t).getSingleResult();
        return current;
    }
}
