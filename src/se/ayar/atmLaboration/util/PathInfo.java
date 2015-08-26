package se.ayar.atmLaboration.util;

import java.util.ArrayList;
import java.util.List;

public class PathInfo
{
	List<String> pathInfo;

	public PathInfo(String req)
	
	{
		this.pathInfo = new ArrayList<String>();
		String[] pathInfo = req.split("/");
		for (int i = 1; i < pathInfo.length; i++)
		{
			this.pathInfo.add(pathInfo[i]);

		}

	}

	public List<String> getPathInfo()
	{
		return pathInfo;
	}

	@Override
	public String toString()
	{
		return "PathInfo [pathInfo=" + pathInfo + "]";
	}

}
