package all_bean;

import all.Shoes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class ShoesFacade extends AbstractFacade<Shoes> {

    @PersistenceContext(unitName = "shop_onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ShoesFacade() {
        super(Shoes.class);
    }
    
    @Override
    public Shoes find(Object id) throws NoResultException {
        Shoes c = null;
        c = (Shoes) em.createNamedQuery("Shoes.findByItemId")
                .setParameter("itemId", id).getSingleResult();
        return c;
    }
    
    public Shoes getByName(String n) throws NoResultException {
        Shoes current = null;
        current = (Shoes) em.createNamedQuery("Shoes.findByName")
                .setParameter("name", n).getSingleResult();
        return current;
    }

    public Shoes getAllByType(String t){
        Shoes current = null;
        current = (Shoes) getEntityManager().createNamedQuery("Shoes.findByType")
                .setParameter("type", t).getSingleResult();
        return current;
    }
}
