package all_bean;

import all.Shirts;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class ShirtsFacade extends AbstractFacade<Shirts> {

    @PersistenceContext(unitName = "shop_onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ShirtsFacade() {
        super(Shirts.class);
    }
    
    @Override
    public Shirts find(Object id) throws NoResultException {
        Shirts c = null;
        c = (Shirts) em.createNamedQuery("Shirts.findByItemId")
                .setParameter("itemId", id).getSingleResult();
        return c;
    }
    
    public Shirts getByName(String n) throws NoResultException {
        Shirts current = null;
        current = (Shirts) em.createNamedQuery("Shirts.findByName")
                .setParameter("name", n).getSingleResult();
        return current;
    }
    
    public Shirts getAllByType(String t){
        Shirts current = null;
        current = (Shirts) getEntityManager().createNamedQuery("Shirts.findByType")
                .setParameter("type", t).getSingleResult();
        return current;
    }
}
