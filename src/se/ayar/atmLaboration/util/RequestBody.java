package se.ayar.atmLaboration.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import se.ayar.atmLaboration.exception.RequestException;

public final class RequestBody
{

	private final Map<String, String> pairs;

	public RequestBody(HttpServletRequest request) throws IOException
	{
		final BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
		String[] keyValuePairs;
		pairs = new HashMap<>();

		while ((line = br.readLine()) != null)
		{
			line.trim();
			final String[] parts = line.split(",");
			for (String part : parts)
			{
				keyValuePairs = part.split(":");
				if(keyValuePairs.length % 2 == 0)
				{
					for (int i = 0; i < keyValuePairs.length; i += 2)
					{
						pairs.put(keyValuePairs[i].trim(), keyValuePairs[i + 1].trim());
					}
				}
				else
				{
					pairs.clear();
					throw new IllegalArgumentException();
				}
			}
		}
	}

	public String getString(String key)
	{
		if (pairs.containsKey(key))
		{
			return pairs.get(key);
		}
		throw new RequestException("can not get key");

	}

	public int getInt(String key)
	{
		return Integer.parseInt(getString(key));
	}

	@Override
	public String toString()
	{
		return "RequestBody [pairs=" + pairs + "]";
	}
	
	
}
