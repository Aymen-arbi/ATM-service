package se.ayar.atmLaboration.model;

public class Account
{
	private ATMCard atmCard;
	private long balance;

	public Account(String accountHolderId, String bankId, int pin)
	{
		this.setBalance(100000);
		this.atmCard = new ATMCard(accountHolderId, bankId, pin);
	}

	public long getBalance()
	{
		return balance;
	}

	public void setBalance(long balance)
	{
		this.balance = balance;
	}

	public ATMCard getCard()
	{
		return atmCard;
	}

}
