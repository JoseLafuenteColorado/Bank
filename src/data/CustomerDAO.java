package data;

/**
 * Interfaz para definir los métodos que tendrá el CustomerDAOSql
 */

import java.util.List;
import business.Customer;
import exceptions.DAOException;

public interface CustomerDAO {
  public void add(Customer customer) throws DAOException;
  public void set(Customer customer) throws DAOException;
  public Customer get(String dni) throws DAOException;
  public void remove(String dni) throws DAOException;
  public List<Customer>getCustomers() throws DAOException;
  public List<Customer>getCustomers(String filter) throws DAOException;
  public int checkCustomer(Customer customer) throws DAOException;
  public int checkCustomer(String dni) throws DAOException;
  
  
  
  
  

}
