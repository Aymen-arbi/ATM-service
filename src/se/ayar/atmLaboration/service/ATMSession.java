package se.ayar.atmLaboration.service;

import se.ayar.atmLaboration.model.ATMReceipt;

public interface ATMSession
{
	long withdrawAmount(int amount);

	ATMReceipt requestReceipt(long transactionId);

	long checkBalance();

	long getTransactionId();
}
