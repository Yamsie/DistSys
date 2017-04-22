package all;

import all.util.JsfUtil;
import all.util.PaginationHelper;
import all_bean.CartFacade;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("cartController")
@SessionScoped
public class CartController implements Serializable {

    private Cart current;
    private DataModel items = null;
    @EJB
    private all_bean.CartFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    @EJB
    private all_bean.SalesFacade salesFacade;
    @EJB
    private all_bean.ProductsFacade productsFacade;

    public CartController() {
    }

    public Cart getSelected() {
        if (current == null) {
            current = new Cart();
            current.setCartPK(new all.CartPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private CartFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }
    
    public String checkout() throws SQLException{
        List cartItems = ejbFacade.checkoutCart(); 
        String rMessage = "/sales/List";
        //if no records, start at 1
        int saleId = (salesFacade.getRecentSaleId()) + 1;
        if(cartItems.size() == 0){
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("NoItems"));
            return rMessage = null;
        }
        for(int i=0; i < cartItems.size(); i++){
            Cart c = (Cart) cartItems.get(i);
            Products p = productsFacade.find((c.getCartPK().getItemId()));
            if(p.getQuantity() - c.getQuantity() >= 0){
                ejbFacade.remove(c); //remove entery from cart
                //Date d = new Date();
                salesFacade.create(new Sales(saleId, new Date(),  c.getCartPK().getItemId(), c.getCartPK().getCustomerId()));
                saleId ++; //increase sale id
                p.setQuantity(p.getQuantity() - c.getQuantity()); //decrement quantitiy in DB
                productsFacade.edit(p);//write product quantity decrement to DB
            }
            else if(p.getQuantity() - c.getQuantity() < 0){
                rMessage = null;
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("NotEnoughStock"));
            }
        }
        return rMessage;
    }

    public String prepareList() {
        recreateModel();
        return "/cart/List";
    }
    
    public void addItem(){
        current = (Cart) getItems().getRowData();
    }

    public String prepareView() {
        current = (Cart) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Cart();
        current.setCartPK(new all.CartPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CartCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Cart) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CartUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Cart) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CartDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Cart getCart(all.CartPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Cart.class)
    public static class CartControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CartController controller = (CartController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cartController");
            return controller.getCart(getKey(value));
        }

        all.CartPK getKey(String value) {
            all.CartPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new all.CartPK();
            key.setCustomerId(Integer.parseInt(values[0]));
            key.setItemId(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(all.CartPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getCustomerId());
            sb.append(SEPARATOR);
            sb.append(value.getItemId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cart) {
                Cart o = (Cart) object;
                return getStringKey(o.getCartPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Cart.class.getName());
            }
        }
    }
    
    public String cancelCart(){
        int custId = 12345;
        ejbFacade.removeAllFromCart(custId);
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AllCartDeleted"));
        return prepareList();
    }
}
