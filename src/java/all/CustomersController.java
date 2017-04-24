package all;

import all.util.JsfUtil;
import all.util.PaginationHelper;
import all_bean.CustomersFacade;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;

@Named("customersController")
@SessionScoped
public class CustomersController implements Serializable {

    private Customers current;
    private DataModel items = null;
    @EJB
    private all_bean.CustomersFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String username;
    private String password;
    private String searchItem;
    private String searchChoice;

    public String getSearchChoice() {
        return searchChoice;
    }

    public void setSearchChoice(String searchChoice) {
        this.searchChoice = searchChoice;
    }

    public String getSearchItem() {
        return searchItem;
    }

    public void setSearchItem(String searchItem) {
        this.searchItem = searchItem;
    }
    
    public Customers getCurrent() {
        return this.current;
    }
    
    
    public CustomersController() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String hashedString = String.format("%064x", new BigInteger(1, hash)); //new String(hash);
        this.password = hashedString.toUpperCase();
    }

    public Customers getSelected() {
        if (current == null) {
            current = new Customers();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CustomersFacade getFacade() {
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
    
    public String logout() {
        this.current = null;
        this.username = null;
        this.password = null;
        
        return "login.xhtml";
    }

    public String checkCredentials() {
        Customers c = getFacade().getByUsernameAndPassword(this.username, new String(this.password));
        
        if (c != null) {
            current = c;
            return "index.xhtml";
        } else {
            current = null;
            return "login.xhtml";
        }        
    }
    
    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Customers) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareSearchView() {
        current = null;
        items = null;
        String returnMessage = "";
        try{
            if(this.searchChoice.equals("name")){
                List custList = ejbFacade.getByName(searchItem);
                items = new ListDataModel(custList);
                returnMessage = "customers/List";
            }
            else{
                int id = Integer.parseInt(this.searchItem.trim());
                current = getCustomers(id); 
                returnMessage = "customers/View";
            }
        }
        catch(Exception ex) {}
        return returnMessage;
    }
    
    public String prepareCreate() {
        current = new Customers();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CustomersCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Customers) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CustomersUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Customers) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CustomersDeleted"));
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

    public Customers getCustomers(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Customers.class)
    public static class CustomersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CustomersController controller = (CustomersController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "customersController");
            return controller.getCustomers(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Customers) {
                Customers o = (Customers) object;
                return getStringKey(o.getCustomerId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Customers.class.getName());
            }
        }

    }

}
