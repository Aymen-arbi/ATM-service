package se.ayar.atmLaboration.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.ayar.atmLaboration.exception.ATMException;
import se.ayar.atmLaboration.model.ATMCard;
import se.ayar.atmLaboration.model.ATMReceipt;
import se.ayar.atmLaboration.model.Operation;
import se.ayar.atmLaboration.service.ATM;
import se.ayar.atmLaboration.service.ATMSession;
import se.ayar.atmLaboration.service.Bank;
import se.ayar.atmLaboration.service.BankImp;
import se.ayar.atmLaboration.util.PathInfo;
import se.ayar.atmLaboration.util.RequestBody;

@WebServlet("/*")
public class ATMServlet extends HttpServlet
{
	private static final long serialVersionUID = 3183187411012989615L;

	private static final AtomicInteger sessionId = new AtomicInteger();
	protected static final Map<Integer, ATMSession> sessions = new HashMap<Integer, ATMSession>();
	private static final ATM atm;

	static
	{
		Bank bank = new BankImp();
		List<Bank> banks = new ArrayList<>();
		banks.add(bank);
		atm = new ATM(banks);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PathInfo pathInfo = new PathInfo(request.getPathInfo());
		List<String> path = pathInfo.getPathInfo();
		if ((path.get(0).equals("transaction")) && (path.get(2).equals("receipt")))
		{
			ATMSession atmSession = sessions.get(Integer.parseInt(path.get(1)));
			ATMReceipt atmReceipt = atmSession.requestReceipt(atmSession.getTransactionId());
			
			response.getWriter().println(atmReceipt.getTransactionId());
			response.getWriter().println(atmReceipt.getAmount());
			response.getWriter().println(atmReceipt.getDate());

		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PathInfo pathInfo = new PathInfo(request.getPathInfo());
		List<String> path = pathInfo.getPathInfo();


		if (path.size() == 1 && path.get(0).equals("session"))
		{
			ATMSession atmSession = getSession(request, response);
			Integer sessionId = getNextSessionId();

			if (atmSession != null)
			{
				sessions.put(sessionId, atmSession);
				response.getWriter().println(sessionId);
			}
		}

		else if (path.size() == 1 && path.get(0).equals("operation"))
		{
			RequestBody requestBody = new RequestBody(request);

			Operation operation = new Operation(requestBody, sessions.get(requestBody.getInt("sessionId")));
			response.getWriter().println(operation.doOperation());
		}
	}

	private ATMSession getSession(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			RequestBody requestBody = new RequestBody(request);
			System.out.println(requestBody.toString());
			ATMCard card = atm.getBank(requestBody.getString("bankId")).getAccount(requestBody.getString("accountHolderId")).getCard();
			ATMSession atmSession = atm.verifyPin(requestBody.getInt("pin"), card);
			return atmSession;
		}
		catch (ATMException e)
		{
			response.sendError(401, e.getMessage());
			return null;
		}
		catch (NumberFormatException e)
		{
			response.sendError(401, e.getMessage());
			return null;
		}
	}

	private int getNextSessionId()
	{
		return sessionId.incrementAndGet();
	}
}
