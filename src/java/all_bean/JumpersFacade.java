package all_bean;

import all.Jumpers;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class JumpersFacade extends AbstractFacade<Jumpers> {

    @PersistenceContext(unitName = "shop_onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JumpersFacade() {
        super(Jumpers.class);
    }
    
    @Override
    public Jumpers find(Object id) throws NoResultException {
        Jumpers c = null;
        c = (Jumpers) em.createNamedQuery("Jumpers.findByItemId")
                .setParameter("itemId", id).getSingleResult();
        return c;
    }
    
    public Jumpers getByName(String n) throws NoResultException {
        Jumpers current = null;
        current = (Jumpers) em.createNamedQuery("Jumpers.findByName")
                .setParameter("name", n).getSingleResult();
        return current;
    }
    public Jumpers getAllByType(String t){
        Jumpers current = null;
        current = (Jumpers) getEntityManager().createNamedQuery("Jumpers.findByType")
                .setParameter("type", t).getSingleResult();
        return current;
    }

}
