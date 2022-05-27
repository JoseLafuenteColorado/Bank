package business;

import java.time.LocalDateTime;


public class Movement {
  private int numberMovement;
  private int numberAccount;
  private int amount;
  private LocalDateTime dateTime;
  private String type;
  private int transferAccountNumber;
  private String concept;
  private String dateTimeString;
  
  public Movement(int numberMovement, int numberAccount, int amount, LocalDateTime dateTime, String type,
      int transferAccountNumber, String concept) {
    this.numberMovement = numberMovement;
    this.numberAccount = numberAccount;
    this.amount = amount;
    this.dateTime = LocalDateTime.now();
    this.type = type;
    this.transferAccountNumber = transferAccountNumber;
    this.concept = concept;
  }
  
  public Movement(int numberMovement, int numberAccount, int amount, String dateTimeString, String type,
      int transferAccountNumber, String concept) {
    this.numberMovement = numberMovement;
    this.numberAccount = numberAccount;
    this.amount = amount;
    this.dateTimeString = dateTimeString;
    this.type = type;
    this.transferAccountNumber = transferAccountNumber;
    this.concept = concept;
  }
  
  public Movement(int numberAccount, int amount, String dateTimeString, String type,
      int transferAccountNumber, String concept) {
    this.numberAccount = numberAccount;
    this.amount = amount;
    this.dateTimeString = dateTimeString;
    this.type = type;
    this.transferAccountNumber = transferAccountNumber;
    this.concept = concept;
  }

  public Movement(int numberAccount, int amount, LocalDateTime dateTime, String type,
      int transferAccountNumber, String concept) {
    this.numberAccount = numberAccount;
    this.amount = amount;
    this.dateTime = LocalDateTime.now();
    this.type = type;
    this.transferAccountNumber = transferAccountNumber;
    this.concept = concept;
  }
  
  public Movement(int numberAccount, int amount, LocalDateTime dateTime, String type,
      String concept) {
   this.numberAccount = numberAccount;
   this.amount = amount;
   this.dateTime = LocalDateTime.now();
   this.type = type;
   this.concept = concept;
 }
  
  public Movement(int numberAccount, int amount, String dateTimeString, String type,
      String concept) {
   this.numberAccount = numberAccount;
   this.amount = amount;
   this.dateTimeString = dateTimeString;
   this.type = type;
   this.concept = concept;
 }
  
  

  @Override
  public String toString() {
    return "Movement [numberAccount=" + numberAccount + ", amount=" + amount + ", dateTime="
        + dateTime + ", type=" + type + ", transferAccountNumber=" + transferAccountNumber
        + ", concept=" + concept + ", dateTimeString=" + dateTimeString + "]";
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

  public String getMovement() {
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

