package se.ayar.atmLaboration.service;

import se.ayar.atmLaboration.exception.ATMException;
import se.ayar.atmLaboration.model.ATMCard;
import se.ayar.atmLaboration.model.ATMReceipt;
import se.ayar.atmLaboration.model.BankReceipt;

public final class ATMSessionImpl extends AbstractATMSession
{
	private boolean canWithdrawAmount = true;
	private boolean canCheckBalance = true;
	private long transactionId;

	public ATMSessionImpl(ATMCard atmCard, Bank bank)
	{
		super(atmCard, bank);
	}

	private boolean validTransaction(int amount, long balance)
	{
		if ((amount >= 100) && (amount <= 10000)
				&& (amount % 100 == 0) && (canWithdrawAmount)
				&& (balance >= amount))
		{
			return true;
		}
		return false;
	}

	@Override
	public long withdrawAmount(int amount)
	{
		long balance = bank.getBalance(atmCard.getAccountHolderId());

		if (validTransaction(amount, balance))
		{
			canWithdrawAmount = false;
			transactionId = bank.withdrawAmount(atmCard.getAccountHolderId(), amount);
			return transactionId;
		}
		throw new ATMException("can not withdraw money");
	}

	@Override
	public ATMReceipt requestReceipt(long transactionId)
	{
		BankReceipt bankReceipt = bank.requestReceipt(transactionId);

		return new ATMReceipt(bankReceipt.getTransactionId(), bankReceipt.getAmount());
	}

	@Override
	public long checkBalance()
	{
		if (canCheckBalance)
		{
			canCheckBalance = false;

			return bank.getBalance(atmCard.getAccountHolderId());
		}
		throw new ATMException("can not get balance");
	}

	@Override
	public long getTransactionId()
	{
		return transactionId;
	}
}
