package all_bean;

import all.Products;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProductsFacade extends AbstractFacade<Products> {

    @PersistenceContext(unitName = "shop_onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductsFacade() {
        super(Products.class);
    }
    
    public Products getByName(String n){
        Products current = null;
        current = (Products) getEntityManager().createNamedQuery("Products.findByName")
                .setParameter("name", n).getSingleResult();
        return current;
    }
}
