package business;

/**
 * Clase que genera acceso del cliente a su aplicación
 * @author José Lafuente
 *
 */

public class CustomerAccess {
  
  private String dni;
  private String password;
  
  public CustomerAccess(String dni, String password) {
    this.dni = dni;
    this.password = password;
  }

  public String getDni() {
    return dni;
  }

  public String getPassword() {
    return password;
  }

  
  
}
