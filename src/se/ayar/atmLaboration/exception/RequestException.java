package se.ayar.atmLaboration.exception;

public class RequestException extends RuntimeException
{
	private static final long serialVersionUID = -4530644977993679782L;

	public RequestException(String msg)
	{
		super(msg);
	}

	public RequestException(String msg, Throwable cause)
	{
		super(msg, cause);
	}

}
