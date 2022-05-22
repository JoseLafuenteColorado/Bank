package business;

import java.util.Objects;

public class Account {
  private Customer customer;
  private String dni;
  private int stage;
  private int number;
  
  public Account(Customer customer) {
    this.customer = customer;
    activate();
  }
  
  public Account(int number, Customer customer, int stage) {
    this.number = number;
    this.customer = customer;
    this.stage = stage;
  }
  
  public Account(int number, String dni, int stage) {
    this.number = number;
    this.dni = dni;
    this.stage = stage;
  }
  
  private void activate() {
    this.stage = 1;
    
  }
  
  public boolean isActive() {
    if (stage == 1) {
      return true;
    }
    return false;
  }

  public Customer getCustomer(String dni) {
    return customer;
  }

  public int getStage() {
    return stage;
  }

  public void setStage(int stage) {
    this.stage = stage;
  }

  public int getNumber() {
    return number;
  }
  
  public String getDni() {
    return dni;
    
  }

  @Override
  public int hashCode() {
    return Objects.hash(number);
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
    return number == other.number;
  }

  @Override
  public String toString() {
    return "Account [dni=" + dni + ", stage=" + stage + ", number=" + number + "]";
  }

  
  
  
  
  
  

}
