package business;

import java.time.LocalDateTime;

public class Movement {
  private int numberAccount;
  private int amount;
  private LocalDateTime dateTime;
  private MovementType type;
  private int transferAccountNumber;
  private String concept;
  private String dateTimeString;
  
  public Movement(int numberAccount, int amount, String dateTimeString, MovementType type,
      int transferAccountNumber, String concept) {
    this.numberAccount = numberAccount;
    this.amount = amount;
    this.dateTimeString = dateTimeString;
    this.type = type;
    this.transferAccountNumber = transferAccountNumber;
    this.concept = concept;
  }

  public Movement(int numberAccount, int amount, LocalDateTime dateTime, MovementType type,
      int transferAccountNumber, String concept) {
    this.numberAccount = numberAccount;
    this.amount = amount;
    this.dateTime = LocalDateTime.now();
    this.type = type;
    this.transferAccountNumber = transferAccountNumber;
    this.concept = concept;
  }
  
  public Movement(int numberAccount, int ammount, LocalDateTime dateTime, MovementType type,
      String concept) {
   this.numberAccount = numberAccount;
   this.amount = ammount;
   this.dateTime = LocalDateTime.now();
   this.type = type;
   this.concept = concept;
 }
  
  public Movement(int int1, int int2, String string, String string2, int int3, String string3) {
    // TODO Auto-generated constructor stub
  }

 
 

  @Override
  public String toString() {
    return "Movement [numberAccount=" + numberAccount
        + ", amount=" + amount + ", dateTime=" + dateTime + ", type=" + type
        + ", transferAccountNumber=" + transferAccountNumber + ", concept=" + concept
        + ", dateTimeString=" + dateTimeString + "]";
  }

  public int getNumberMovement() {
    return numberAccount;
  }
  
  public int getNumberAccount() {
    return numberAccount;
  }
  
  public double getAmount() {
    return amount;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public MovementType getMovement() {
    return type;
  }

  public int getTransferAccountNumber() {
    return transferAccountNumber;
  }

  public String getConcept() {
    return concept;
  }

  public String getDateTimeString() {
    return dateTimeString;
  }
  
  
  
  
  
  
  
  
  
  
  
}

