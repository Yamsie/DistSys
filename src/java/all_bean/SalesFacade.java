package all_bean;

import all.CartPK;
import all.Sales;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SalesFacade extends AbstractFacade<Sales> {

    @PersistenceContext(unitName = "shop_onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SalesFacade() {
        super(Sales.class);
    }

    /*public Sales addToSales(int saleId, CartPK pk, int quantity){
        Date date= new Date();
        Sales sale = new Sales(saleId, date, pk.getItemId(), pk.getCustomerId());
        return sale;        
    }*/
    
    public int getRecentSaleId(){
        int newId = 0;
        try{
        newId = (int) em.createNamedQuery("Sales.getMaxId").getSingleResult();
        }
        catch(Exception ex){ }
        return newId;
    }
}
