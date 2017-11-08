



package com.sinosoft.productdef;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.StrTool;

public class ProposalUtil {
	
	private HeaderConfImpl headConfigImpl = new HeaderConfImpl();
	
	private ParseProfitHeaderImpl _parseXmlTable = new ParseProfitHeaderImpl();

	public String previewProfitHead(String riskCode, String type){
		
		
		XmlTable xmlTable = _parseXmlTable.getProfitHeader(riskCode, type);
		
		String htmlStr = headConfigImpl.getheaderConfStr(xmlTable);
		
		return htmlStr;
	}
	
	public boolean testSql(String sql){
		String tStr="";
		String tStr1="";
		//首先对sql的关键字段进行校验 
		int i_1 = sql.toLowerCase().indexOf("select");
		int i_2 = sql.toLowerCase().indexOf("from");
		int i_3 = sql.toLowerCase().indexOf("where");
		if(i_1==-1||i_2==-1||i_3==-1){
			return false;
		}else{
			int index = 0;
			index = sql.length() - sql.replaceAll("\\?","").length();
			if (index % 2 != 0) {
				return false;
			}
				while (true) {
					tStr = PubFun.getStr(sql, 2, "?");
					if (tStr.equals("")) {
						break;
					}
					if(!tStr.trim().equals("AppDate")){
						tStr1 = "?" + tStr + "?";
						sql = StrTool.replaceEx(sql, tStr1, "1");
					}else{
						String date = PubFun.getCurrentDate();
						tStr1 = "?" + tStr + "?";
						sql = StrTool.replaceEx(sql, tStr1, date);
					}
				}
				ExeSQL exeSql = new ExeSQL();
				exeSql.execSQL(sql);
				int no = exeSql.mErrors.getErrorCount();
				if(no>0){
					return false;
				}else{
					return true;
				}
		}
	}
	
	public static void main(String args[]){
		ProposalUtil u = new ProposalUtil();
		//String dd = u.previewProfitHead("test1109","1");
		System.out.println(u.testSql("SELECT * from PD_LMTabHeadRela where RiskCode = 'test1109'"));
		//System.out.println("@出事预览表头@"+dd);
	}
	
}


