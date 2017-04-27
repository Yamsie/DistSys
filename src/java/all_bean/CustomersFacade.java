package all_bean;

import all.Customers;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    

    public List findCust(Object id){
        List c = em.createNamedQuery("Customers.findByCustomerId").setParameter("customerId", id).getResultList();
        return c;
    }
    
    public List getByName(String n){
        Query q = em.createNamedQuery("Customers.findByName")
                .setParameter("name", n);
        List namedItems = q.getResultList();
        return namedItems;
    }
}