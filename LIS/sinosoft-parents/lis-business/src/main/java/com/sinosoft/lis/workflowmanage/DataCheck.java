package com.sinosoft.lis.workflowmanage;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-3-18
 * Time: 13:29:54
 * To change this template use File | Settings | File Templates.
 */
public class DataCheck
{
private static Logger logger = Logger.getLogger(DataCheck.class);

	public static boolean CheckNull(String str)
	{
		if(null==str||"".equals(str))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
