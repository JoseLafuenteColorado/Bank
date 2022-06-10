package business;

/**
 * Clase que recibe unos parámetros de la clase CustomerManagement, y se los pasa al CustomerDAOSql 
 * para que pueda hacer la consulta a la BBDD.
 * 
 * A su vez, recibe los datos extraidos de la base de datos y se los muestra al CustomerManagement
 * 
 * Es el "intermediario" entra capas para la clase Customer.
 */

import java.util.List;
import data.CustomerDAO;
import data.DAOManager;
import exceptions.DAOException;

public class CustomerManager {

 private CustomerDAO customerDAO;
  
  public CustomerManager(DAOManager daoManager) throws DAOException {
    this.customerDAO = daoManager.getCustomerDAO();
  }
  
  public void set(CustomerDAO customerDAO) {
    this.customerDAO = customerDAO;
  }
  
  public Customer get(String dni) throws DAOException {
    return customerDAO.get(dni);
  }

  public void add(String dni, String name, String address, String phone) throws DAOException {
    customerDAO.add(new Customer(dni, name, address, phone));
  }
  
  public void set(String dni, String name, String address, String phone) throws DAOException {
    customerDAO.set(new Customer(dni, name, address, phone));
  }

  public void setName(String dni, String name) throws DAOException {
    Customer customer = get(dni);
    customer.setName(name);
    customerDAO.set(customer);
  }
  
  public void setPhone(String dni, String phone) throws DAOException {
    Customer customer = get(dni);
    customer.setPhone(phone);
    customerDAO.set(customer);
  }
  
  public void setAddress(String dni, String address) throws DAOException {
    Customer customer = get(dni);
    customer.setAddress(address);
    customerDAO.set(customer);
  }
  
  public void remove(Customer customer) throws DAOException {
    if(customerDAO.checkCustomer(customer) == 1) {
      customerDAO.remove(customer.getDni());; 
    } else {
      throw new DAOException("El cliente tiene cuentas asociadas.");
    }
  }
  
  public List<Customer> getList() throws DAOException{
    return customerDAO.getCustomers();
  }
  
  public List<Customer> getList(String sql) throws DAOException{
    return customerDAO.getCustomers(sql);
  }

  public int checkCustomer(String dni) throws DAOException {
    return customerDAO.checkCustomer(dni);
  }
  
  
  
  
}
