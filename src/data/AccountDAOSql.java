package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import business.Account;
import business.Customer;
import exceptions.DAOException;

public class AccountDAOSql implements AccountDAO{
  private Connection connection;

  public AccountDAOSql(Connection connection) {
    this.connection = connection;
  }

  private int executeUpdate(String sql) throws SQLException, DAOException {
    try {
      Statement statement = connection.createStatement();
      return statement.executeUpdate(sql);  
    } catch (Exception e) {
      throw new DAOException(e);
    }
  }

  @Override
  public void add(Customer customer) throws DAOException {
    String sql = "INSERT INTO accounts VALUES(null, '" + customer.getDni() + "', 1)";
    try {
      executeUpdate(sql);
    } catch (SQLException | DAOException e) {

    }
  }

  @Override
  public void cancelAccount(int number) throws SQLException, DAOException  {
    String sql = "UPDATE accounts SET ACTIVE=0 WHERE number = '" + number + "'";
    if(executeUpdate(sql) == 0 ) {
      throw new DAOException("No existe una cuenta con el número de cuenta " + number);
    }
    executeUpdate(sql);
  }


  @Override
  public void depositMoney(Account account) throws DAOException {
    // TODO Auto-generated method stub

  }

  @Override
  public void withdrawMoney(Account account) throws DAOException {
    // TODO Auto-generated method stub

  }

  @Override
  public void transferMoney(Account account) throws DAOException {
    // TODO Auto-generated method stub

  }

  @Override
  public Account get(int number) throws DAOException {
    String sql = "SELECT * FROM accounts CROSS JOIN customers ON accounts.dni= customers.dni WHERE accounts.dni= '" + number + "'";
    try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      if (!resultSet.next()) {
        throw new DAOException("No exsite la cuenta: " + number);
      }

      Customer customer = new Customer(resultSet.getString("dni"), resultSet.getString("name"), resultSet.getString("address"),
          resultSet.getString("phone"));
      return new Account(resultSet.getInt("number"), customer, 
          resultSet.getInt("active"));
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

  @Override
  public Customer getCustomer(int number) throws DAOException {
    String sql = "SELECT * FROM accounts CROSS JOIN customers ON accounts.dni= customers.dni WHERE accounts.dni= '" + number + "'";
    try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      if(!resultSet.next()) {
        throw new DAOException("No existe la cuenta " + number);
      }

      Customer customer = new Customer(resultSet.getString("dni"), 
          resultSet.getString("name"), resultSet.getString("address"), resultSet.getString("phone"));

      return customer;
    } catch(SQLException e) {
      throw new DAOException(e);
    }
  }

  @Override
  public boolean isActive(int number) throws DAOException {
    String sql = "SELECT stage FROM accounts WHERE number = '" + number + "'";
    try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      if (resultSet.getInt("stage") == 0) {
        throw new DAOException("La cuenta " + number + " No está activa");
      }
      return true;
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }

}
