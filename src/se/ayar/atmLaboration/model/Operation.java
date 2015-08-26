package se.ayar.atmLaboration.model;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import se.ayar.atmLaboration.exception.ATMException;
import se.ayar.atmLaboration.exception.RequestException;
import se.ayar.atmLaboration.service.ATMSession;
import se.ayar.atmLaboration.util.RequestBody;

public final class Operation
{
	private final RequestBody requestBody;
	private final ATMSession atmSession;
	private HttpServletResponse response;

	public Operation(RequestBody requestBody, ATMSession atmSession)
	{
		this.atmSession = atmSession;
		this.requestBody = requestBody;
	}

	public long doOperation() throws IOException
	{
		long result = 0;
		try
		{
			if (requestBody.getString("operation").equals("checkBalance"))
			{
				result = atmSession.checkBalance();
			}
			else if (requestBody.getString("operation").equals("withdraw"))
			{
				result = atmSession.withdrawAmount(requestBody.getInt("amount"));
			}
		}
		catch (ATMException e)
		{
			response.sendError(401, e.getMessage());
		}
		catch (RequestException e)
		{
			response.sendError(401, e.getMessage());
		}

		return result;
	}

}
