package business;
/**
 * Clase de Cuentas Corrientes
 */

import java.util.Objects;

public class Account {
  private Customer customer;
  private String dni;
  private int stage;
  private int numberAccount;
  
  public Account(Customer customer) {
    this.customer = customer;
    activate();
  }
  
  public Account(int numberAccount, Customer customer, int stage) {
    this.numberAccount = numberAccount;
    this.customer = customer;
    this.stage = stage;
  }
  
  public Account(int numberAccount, String dni, int stage) {
    this.numberAccount = numberAccount;
    this.dni = dni;
    this.stage = stage;
  }
  
  /**
   * Activa la cuenta
   */
  private void activate() {
    this.stage = 1;
    
  }
  
  /**
   * Comprueba si la cuenta está activa
   * @return true si está activa y false si no lo está
   */
  public boolean isActive() {
    if (stage == 1) {
      return true;
    }
    return false;
  }

  /**
   * Devuelve un objeto de la clase customer del dni pasado
   * @param dni
   * @return customer
   */
  public Customer getCustomer(String dni) {
    return customer;
  }

  public int getStage() {
    return stage;
  }

  public void setStage(int stage) {
    this.stage = stage;
  }

  public int getNumberAccount() {
    return numberAccount;
  }
  
  public String getDni() {
    return dni;
    
  }

  @Override
  public int hashCode() {
    return Objects.hash(numberAccount);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Account other = (Account) obj;
    return numberAccount == other.numberAccount;
  }

  @Override
  public String toString() {
    return "Account [dni=" + dni + ", stage=" + stage + ", numberAccount=" + numberAccount + "]";
  }

  
  
  
  
  
  

}
