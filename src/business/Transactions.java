package business;

/**
 * Clase para transacciones, ingreso, retirada y transferencia.
 */

import java.time.LocalDateTime;
import exceptions.CustomerIllegalArgumentException;
import exceptions.DAOException;

public class Transactions {
  private Customer customer;
  private Account account;
  private int numberAccount;
  private int amount;
  private LocalDateTime dateTime;
  private String type;
  private int transferAccountNumber;
  private String concept;
  private String dateTimeString;

  public Transactions(int numberAccount, int amount, String concept) throws DAOException {
    setNumberAccount(numberAccount);
    setAmount(amount);
    this.dateTime = LocalDateTime.now();
    this.type = "";
    this.transferAccountNumber = 0;
    setConcept(concept);
  }

  
  public Transactions(int numberAccount, int amount, int transferAccountNumber, String concept) throws DAOException {
    setNumberAccount(numberAccount);
    setAmount(amount);
    this.dateTime = LocalDateTime.now();
    this.type = "Transferencia";
    this.transferAccountNumber = transferAccountNumber;
    setConcept(concept);
  }

  public int getNumberAccount() {
    return numberAccount;
  }

  public void setNumberAccount(int numberAccount) throws DAOException {
    this.numberAccount = numberAccount;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    if (amount < 0) {
      throw new CustomerIllegalArgumentException("El importe debe ser mayor que 0");
      } else {
        this.amount = amount;
      }
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getTransferAccountNumber() {
    return transferAccountNumber;
  }

  public void setTransferAccountNumber(int transferAccountNumber) {
    this.transferAccountNumber = transferAccountNumber;
  }

  public String getConcept() {
    return concept;
  }

  public void setConcept(String concept) {
    if (concept.isBlank()) {
      concept = "Sin concepto";
    } else {
      this.concept = concept;
    }
    
  }

  public String getDateTimeString() {
    return dateTimeString;
  }

  public void setDateTimeString(String dateTimeString) {
    this.dateTimeString = dateTimeString;
  }

  @Override
  public String toString() {
    return "Transactions [numberAccount=" + numberAccount + ", amount=" + amount + ", dateTime="
        + dateTime + ", type=" + type + ", transferAccountNumber=" + transferAccountNumber
        + ", concept=" + concept + ", dateTimeString=" + dateTimeString + "]";
  }






}
