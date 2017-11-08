package com.sinosoft.lis.actuary.finder;
import org.apache.log4j.Logger;

import com.sinosoft.utility.SQLwithBindVariables;



public class LCGetFinder extends AbstractrModifiedContFinder implements IModifiedContFinder {
private static Logger logger = Logger.getLogger(LCGetFinder.class);

	protected SQLwithBindVariables getFindModifiedContsSql(String startdate, String enddate) {
		String sql = "select distinct contno from lcget where modifydate between '"
				+ "?modifydate1?" + "' and '"
				+ "?modifydate2?"+"'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("modifydate1", startdate);
		sqlbv1.put("modifydate2", enddate);
		return sqlbv1;
	}

}
