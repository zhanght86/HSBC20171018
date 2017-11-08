package com.sinosoft.utility;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDUWGrade_PicchDB;
import com.sinosoft.lis.vschema.LDUWGrade_PicchSet;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class Picch_UWGrade {
private static Logger logger = Logger.getLogger(Picch_UWGrade.class);

	public Picch_UWGrade() {
	}

	public String getUWGrade(double tAmnt, String tContflag) {
		String tResult = "";
		try {

			LDUWGrade_PicchDB tLDUWGrade_PicchDB = new LDUWGrade_PicchDB();
			String tsql = "select * from LDUWGrade_Picch where Amnt>=?tAmnt? and Contflag='?tContflag?' order by Amnt";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tsql);
			sqlbv.put("tAmnt", tAmnt);
			sqlbv.put("tContflag", tContflag);
			LDUWGrade_PicchSet tLDUWGrade_PicchSet = tLDUWGrade_PicchDB
					.executeQuery(sqlbv);

			if (tLDUWGrade_PicchSet.get(1) != null) {
				tResult = tLDUWGrade_PicchSet.get(1).getUWGrade();
			} else {
				tResult = "08";
			}
			logger.debug("Result=" + tResult);
		} catch (Exception ex) {
		}
		return tResult;
	}

	public static void main(String[] args) {
		Picch_UWGrade tPicch_UWGrade = new Picch_UWGrade();
		tPicch_UWGrade.getUWGrade(40000000, "1");

	}
}
