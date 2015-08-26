package se.ayar.atmLaboration.service;

import se.ayar.atmLaboration.model.Account;
import se.ayar.atmLaboration.model.BankReceipt;

public interface Bank
{
	String getBankId();

	long getBalance(String accountHolderId);

	long withdrawAmount(String accountHolder, int amount);

	BankReceipt requestReceipt(long transactionId);

	Account getAccount(String accountHolderId);

}
