package com.sinosoft.lis.actuary.finder;
import org.apache.log4j.Logger;

import com.sinosoft.utility.SQLwithBindVariables;



public class LCDutyFinder extends AbstractrModifiedContFinder implements IModifiedContFinder {
private static Logger logger = Logger.getLogger(LCDutyFinder.class);

	protected SQLwithBindVariables getFindModifiedContsSql(String startdate, String enddate) {
		String sql = "select distinct contno from lcduty where modifydate between '"
				+ "?modifydate1?" + "' and '"
				+ "?modifydate2?"+"'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("modifydate1", startdate);
		sqlbv2.put("modifydate2", enddate);
		return sqlbv2;
	}

}
