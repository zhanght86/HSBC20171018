package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;

public class GetTotalSum {
private static Logger logger = Logger.getLogger(GetTotalSum.class);
	public GetTotalSum() {
	}

	public static String getTotalPay(String serialno) {
		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = new SSRS();

		String sql = "select Totalmoney, Totalnum from lybanklog where SerialNo = '"
				+ "?serialno?" + "'";
		 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	        sqlbv1.sql(sql);
	        sqlbv1.put("serialno", serialno.trim());

		testSSRS = exeSql.execSQL(sqlbv1);
		return testSSRS.GetText(1, 1);
	}

	public static String getTotalPic(String serialno) {
		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = new SSRS();

		String sql = "select Totalmoney, Totalnum from lybanklog where SerialNo = '"
				+ "?serialno1?" + "'";
		 SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	        sqlbv2.sql(sql);
	        sqlbv2.put("serialno1", serialno.trim());
		testSSRS = exeSql.execSQL(sqlbv2);

		return testSSRS.GetText(1, 2);
	}
}
