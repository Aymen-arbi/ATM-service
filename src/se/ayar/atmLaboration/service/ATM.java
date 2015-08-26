package se.ayar.atmLaboration.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.ayar.atmLaboration.exception.ATMException;
import se.ayar.atmLaboration.exception.ATMSecurityException;
import se.ayar.atmLaboration.model.ATMCard;

public final class ATM
{
	private final Map<String, Bank> banks;

	public ATM(List<Bank> banks)
	{
		if (banks == null || banks.isEmpty())
		{
			throw new IllegalArgumentException();
		}
		this.banks = new HashMap<>();
		for (Bank bank : banks)
		{
			this.banks.put(bank.getBankId(), bank);
		}
	}

	public ATMSession verifyPin(int pin, ATMCard card)
	{
		if (card.verifyPin(pin))
		{
			return new ATMSessionImpl(card, getBank(card.getBankId()));
		}
		throw new ATMSecurityException("Could not verifie pin code");
	}

	public Bank getBank(String bankId)
	{
		if (banks.containsKey(bankId))
		{
			return banks.get(bankId);
		}
		throw new ATMException("Could not connect to bank");
	}
}
