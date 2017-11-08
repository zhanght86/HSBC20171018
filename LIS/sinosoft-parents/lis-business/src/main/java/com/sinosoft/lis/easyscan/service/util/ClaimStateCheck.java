package com.sinosoft.lis.easyscan.service.util;
import org.apache.log4j.Logger;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;

public class ClaimStateCheck {
private static Logger logger = Logger.getLogger(ClaimStateCheck.class);
	public static boolean isClaimAccept(String rgtno) {
		String sql = "select count(1) from llregister where rgtno='" + "?rgtno?"+ "' and EndCaseDate is not null";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("rgtno", rgtno);
		String s = new ExeSQL().getOneValue(sqlbv1);
		return Integer.parseInt(s) > 0;
	}
	/**
	 * 校验本次立案是否存在
	 * */
	public static boolean isClaimExist(String doccode,String subtype){
		String tSql = "select count(1) from es_doc_main where doccode='"+ "?doccode?" + "' and subtype='"+"?subtype?"+"'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("doccode", doccode);
		sqlbv2.put("subtype", subtype);
		String s = new ExeSQL().getOneValue(sqlbv2);
		return Integer.parseInt(s) > 0;
	}
	
	/**
	 * 校验申请书号是否已经存在
	 * */
	public static boolean isRelationExist(String doccode,String subtype){
		String tSql = "select count(1) from es_doc_relation where doccode='"+ "?doccode?" + "' and subtype='"+"?subtype?"+"'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("doccode", doccode);
		sqlbv3.put("subtype", subtype);
		String s = new ExeSQL().getOneValue(sqlbv3);
		return Integer.parseInt(s) > 0;
	}
	
	/**
	 * 时候扫描需校验报案或立案号是否存在
	 * */
	public static boolean isRgtNoExist(String doccode){
		String tSql = "select count(1) from llregister where rgtno='"+"?doccode?"+"'"
						+ " union all"
						+ " select count(1) from llreport where rptno='"+"?doccode?"+"'"
						+ " union all"
						+ " select count(1) from lwmission where missionprop1='"+"?doccode?"+"'"
						+ " and activityid='0000005010'";
		
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("doccode", doccode);
		SSRS tSSRS = new SSRS();
		tSSRS = new ExeSQL().execSQL(sqlbv4);
		for(int i=1;i<=tSSRS.MaxRow;i++){
			if(Integer.parseInt(tSSRS.GetText(i, 1)) > 0){
				return true;
			}
		}
		return false;
	}
}
