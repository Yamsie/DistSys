package all_bean;

import all.Cart;
import all.CartPK;
import all.CustomersController;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CartFacade extends AbstractFacade<Cart> {

    @PersistenceContext(unitName = "shop_onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CartFacade() {
        super(Cart.class);
    }
    
    public Cart addItemToCart(int id, int itemId, int q){
        Cart current = null;
        current = (Cart) em.createNamedQuery("Cart.addItem").setParameter("customerId", id)
        .setParameter("itemId", itemId).setParameter("quantity", q);
        return current;
    }
    
    public List checkoutCart(){
        CartPK pk = new CartPK();
        int cust = CustomersController.getCustomersInstance().getCustomerId();
        pk.setCustomerId(cust);
        Query q =  em.createNamedQuery("Cart.findByCustomerId")
                .setParameter("customerId", pk.getCustomerId());
        List cartItems = q.getResultList();
        return cartItems;
    }
    
    public void removeAllFromCart(int custId){
        //int custId = CustomersController.getCustomersInstance().getCustomerId();
        int i=0;
        Query q =  em.createNamedQuery("Cart.findByCustomerId").setParameter("customerId", custId);
        List cartItems = q.getResultList();
        while(i < cartItems.size()){
            Cart c = (Cart) cartItems.get(i);
            em.remove(c);
            i++;
        }
    }
}
