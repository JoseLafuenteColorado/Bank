package data;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import exceptions.DAOException;
import util.Util;

public class CustomerAccessDAOSql implements CustomerAccessDAO {
  private Connection connection;
  private CustomerDAO customerDAO;
  private CustomerDAOSql customerDAOSql;

  public CustomerAccessDAOSql(Connection connection) {
    this.connection = connection;
  }

  /** Ejecuta una consulta a la base de datos
   * @param sql
   * @return int si la consulta ha tenido éxito.
   * @throws DAOException
   */

  private int executeUpdate(String sql) throws SQLException, DAOException {
    try {
      Statement statement = connection.createStatement();
      return statement.executeUpdate(sql);  
    } catch (Exception e) {
      throw new DAOException(e);
    }
  }

  /**
   * Añade un nuevo acceso al cliente a la BBDD.
   * @throws DAOException 
   * @throws GeneralSecurityException 
   * @throws NoSuchAlgorithmException 
   */

  public void addCustomerAccess(String dni, String password) throws DAOException, NoSuchAlgorithmException, GeneralSecurityException {
    dni = Util.formatDni(dni);
    String passwordEncrypt = Util.getMD5(password);
    /*if (customerDAOSql.checkCustomer(dni) == 0) {
      throw new DAOException("Acceso denegado, NO es usted cliente de nuestro banco");
    }*/
    String sql = "INSERT INTO passwords VALUES('" +passwordEncrypt+ "', '" +dni+ "')";
    try {
      executeUpdate(sql);
      System.out.println("Registro confirmado");
    } catch (SQLException e) {
      throw new DAOException(e);
    }
  }


  /**
   * Chequea en la BBDD si el dni y la contraseña proporcionada por el cliente
   * son correctos
   * @param dni, password
   * @return true
   * @throws DAOException
   * @throws GeneralSecurityException 
   * @throws NoSuchAlgorithmException 
   */

  @Override
  public boolean chekAccess(String dni, String password) throws DAOException, NoSuchAlgorithmException, GeneralSecurityException {
    dni = Util.formatDni(dni);

    String sql = "SELECT password, dni FROM passwords WHERE dni = '" +dni+ "'";

    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);
      String passwordToCompare = Util.getMD5(password); 
      if (!resultSet.next()) {
        throw new DAOException("No existe el dni: " + dni + " en la base de datos");
      } else if (!resultSet.getString("dni").equals(dni)) {
        throw new DAOException("dni incorrecto");
      } else if (!resultSet.getString("password").equals(passwordToCompare)) {
        throw new DAOException("password incorrecto");
      }
      executeUpdate(sql);

    } catch (SQLException e) {
      throw new DAOException(e);
    }
    return true;
  }

  public void modifyPass(String dni, String oldPassword, String newPassword) throws DAOException {
    String newPasswordToEncrypt = Util.getMD5(newPassword);
    String sql = "UPDATE passwords SET password = '" +newPasswordToEncrypt+ "' WHERE dni = '" +dni+ "'";
    try {
      executeUpdate(sql);
      System.out.println("Contraseña actualizada");
    } catch (SQLException e) {
      throw new DAOException("No se ha podido actualizar la contraseña");
    }
  }
  
  
}
