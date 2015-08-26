package se.ayar.atmLaboration.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import se.ayar.atmLaboration.exception.ATMException;
import se.ayar.atmLaboration.model.Account;
import se.ayar.atmLaboration.model.BankReceipt;

public class BankImp implements Bank
{
	private AtomicInteger transactionId;
	private final static String BANK_ID = "1234-5678";

	private final Map<String, Account> accounts;
	private final Map<Long, BankReceipt> receipts;

	public BankImp()
	{
		transactionId = new AtomicInteger();
		accounts = new HashMap<String, Account>();
		receipts = new HashMap<Long, BankReceipt>();

		for (int i = 0; i < 10; i++)
		{
			accounts.put(("accountHolder" + i), new Account("accountHolder" + i, BANK_ID, 1000 + i));
		}

	}

	@Override
	public String getBankId()
	{
		return BANK_ID;
	}

	@Override
	public long getBalance(String accountHolderId)
	{
		return accounts.get(accountHolderId).getBalance();
	}

	@Override
	public long withdrawAmount(String accountHolder, int amount)
	{
		if (accounts.get(accountHolder).getBalance() > amount)
		{
			accounts.get(accountHolder).setBalance(accounts.get(accountHolder).getBalance() -amount);
			long transactionId = getNextId();
			receipts.put(transactionId, new BankReceipt(BANK_ID, transactionId, amount, new Date()));
			return transactionId;
		}
		throw new ATMException("Not enough balance");

	}

	@Override
	public BankReceipt requestReceipt(long transactionId)
	{
		if (receipts.get(transactionId) == null)
		{
			throw new ATMException("wrong transaction id");
		}
		return receipts.get(transactionId);
	}

	@Override
	public Account getAccount(String accountHolderId)
	{
		if (accounts.get(accountHolderId) == null)
		{
			throw new ATMException("account not connected to bank");
		}
		return accounts.get(accountHolderId);
	}

	private int getNextId()
	{
		return this.transactionId.incrementAndGet();
	}


}
