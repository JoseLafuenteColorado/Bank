package business;

public class Account {
  private Customer customer;
  private int stage;
  private int number;
  
  public Account(Customer customer) {
    this.customer = customer;
    activate();
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

  public Account(int number, Customer customer, int stage) {
    this.number = number;
    this.customer = customer;
    this.stage = stage;
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

  
  
  
  
  

}
