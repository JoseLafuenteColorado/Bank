package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import business.Customer;
import exceptions.DAOException;
import util.Util;

public class CustomerDAOSql implements CustomerDAO{
  private Connection connection;

  public CustomerDAOSql(Connection connection) {
    this.connection = connection;    
  }

  private int executeUpdate(String sql) throws DAOException {
    try {
      Statement statement = connection.createStatement();
      return statement.executeUpdate(sql);
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  @Override
  public void add(Customer customer) throws DAOException {
    String sql = "INSERT INTO customers VALUES('" + Util.formatDni(customer.getDni()) + "', '" + customer.getName()
    + "', '" + customer.getAddress() + "', '" + customer.getPhone() + "')";
    executeUpdate(sql);
  }

  @Override
  public void close() throws DAOException {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  @Override
  public void set(Customer customer) throws DAOException {
    String sql = "UPDATE customers SET name='" + customer.getName() + "', address='" + customer.getAddress() + 
        "', phone='" + customer.getPhone() + "' WHERE dni = '" + Util.formatDni(customer.getDni()) + "'";
    if (executeUpdate(sql) < 1) {
      throw new  DAOException("No existe ningún usuario con este Dni: " + customer.getDni());
    }
  }

  @Override
  public Customer get(String dni) throws DAOException {
    dni = Util.formatDni(dni);
    String sql = "SELECT * FROM customers WHERE dni = '" + dni + "'";

    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);
      if (!resultSet.next()) {
        throw new DAOException("No existe el dni: " + dni + " en la base de datos");
      }
      return new Customer(dni, resultSet.getString("name"), resultSet.getString("address"), resultSet.getString("phone")) ;

    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  @Override
  public void remove(String dni) throws DAOException {
    dni = Util.formatDni(dni);
    String sql = "DELETE FROM customers WHERE dni = '" + dni + "'";
    if (executeUpdate(sql) == 0) {
      throw new DAOException("No existe el cliente con dni: " + dni);
    }

  }

  @Override
  public List<Customer> getCustomers() throws DAOException {
    return getCustomers("SELECT * FROM customers");
  }

  @Override
  public List<Customer> getCustomers(String sql) throws DAOException {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);
      List<Customer> list = new ArrayList<>();
      while(resultSet.next()) {
        list.add(new Customer(resultSet.getString("dni"), resultSet.getString("name"), resultSet.getString("address"),
            resultSet.getString("phone")));
      }
      return list;
    } catch (SQLException e){
      throw new DAOException(e);
    }
  }

  @Override
  public int checkCustomer(Customer customer) throws DAOException {
    String sql = "SELECT * FROM customers WHERE dni = '" + customer.getDni() + "'";
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);
      return resultSet.next() ? 1:0;

    } catch (SQLException e) {
      throw new DAOException(e) ;
    }
  }

  @Override
  public Customer get(int dni) throws DAOException {
    return null;
  }

}
