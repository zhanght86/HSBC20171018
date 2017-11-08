



package com.sinosoft.productdef;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;

public class PDSugIncomeConditionService {
	
	private String mLanguage = "";
	
	////////////////////////////
	private String mRiskCode = "";
	
	private String mTemplateID = "";
	private String mRiskType = "";
	private String mStandbyFlag1 = "";
	
	private static String mAppnt = "Appnt";
	private static String mInsured = "Insured";
	private static String mRisk = "Risk";
	private static String mDuty = "Duty";
	private static String mPay = "Pay";
	
	private static String mPubTemplateID = "0000000000";
	
	
	private TransferData mTransferData = new TransferData();
	
	public PDSugIncomeConditionService(HttpServletRequest request)
	{
		 mRiskCode = (String)request.getParameter("RiskCode");
		if(this.mRiskCode!=null&&!this.mRiskCode.equals(""))
		{
			getDefaultProp();
		}
	}

	//初始化需要使用的函数的脚本 
	public String initFuncScript()
	{
		String tFuncScript = "";
		Vector tNameVector = this.mTransferData.getValueNames();
		
		String tFunc_InitForm = "function initForm() {";
		tFunc_InitForm = tFunc_InitForm + "try {";
		for(int i=0;i<tNameVector.size();i++)
		{
			String tName = (String)tNameVector.get(i);
			tFuncScript =  tFuncScript + "" + (String)this.mTransferData.getValueByName(tName);
			tFunc_InitForm = tFunc_InitForm + "try {";
			tFunc_InitForm  = tFunc_InitForm + tName+"();";
			tFunc_InitForm = tFunc_InitForm + "} catch(e){}";
		}
		tFunc_InitForm = tFunc_InitForm + "} catch(e){}" + " }";
		
		
		
		return tFunc_InitForm + tFuncScript;
	}
	
	//获取默认的信息 
	private void getDefaultProp() {
		ExeSQL tExeSQL = new ExeSQL();
		
		String tSQL_TemplateID = "select templateid from PDT_RiskTypeTemplate where risktype = '"+mRiskCode+"' and standbyflag1='"+mStandbyFlag1+"'";
		this.mTemplateID = tExeSQL.getOneValue(tSQL_TemplateID);
	}
	
	public PDSugIncomeConditionService(String tRiskCode,String tStandbyFlag1)
	{
		this.mRiskCode = tRiskCode;
		this.mStandbyFlag1=tStandbyFlag1;
		if(this.mRiskCode!=null&&!this.mRiskCode.equals(""))
		{
			getDefaultProp();
		}
	}
	
	public void setLanguage(String tLanguage)
	{
		this.mLanguage = tLanguage;
	}
	

	
	/*
	 * 生成险种信息 
	 */
	public String initRiskScript()
	{
		String tRiskScript = "";
		
		//生成投保人 标签
		tRiskScript =  tRiskScript + "<DIV id=DivLCRiskButton STYLE=\"display:''\"> "
					 + " <Table> "
					 + " <TR> "
					 + " <TD> "
					 + " <IMG  src= \"../common/images/butExpand.gif\" style= \"cursor:hand;\" > "
					 + "</TD> "
					 + "<TD class= titleImg>"
					 + "收益算法条件定义"
					 + "</TD> "
					 + "</TR> "
					 + "</Table>"
					 + "</DIV>";
		
		
		//div
		tRiskScript = tRiskScript + "<DIV id='DivLCRisk' STYLE=\"display:''\"> ";
		
		
		//table 
		tRiskScript = tRiskScript + "<table id='TableRisk' class=common >";
		
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL_ShowType = " select showtyperela,showmethod,showsql from PDT_RiskShowType "
			                + " where templateid='"+this.mTemplateID+"' and showtype='"+this.mRisk+"' ";
		SSRS tSSRS_ShowType = new SSRS();
		tSSRS_ShowType = tExeSQL.execSQL(tSQL_ShowType);
		for(int i=1;i<=tSSRS_ShowType.getMaxRow();i++)
		{
			String tShowTypeRela = tSSRS_ShowType.GetText(i, 1);
			String tShowMethod = tSSRS_ShowType.GetText(i, 2);
			String tShowSQL = tSSRS_ShowType.GetText(i, 3);
			
			if(tShowTypeRela!=null&&tShowTypeRela.equals("0"))
			{
				//直接查表生成
				tRiskScript = prepareByShowColTable(tRiskScript, tExeSQL,this.mRisk);
			}
			else
			{
				//按照SQL生成  
			}
		}
		
		
		tRiskScript = tRiskScript + "</table>";
		
		tRiskScript = tRiskScript + "</Div>";
		
		System.out.println();
		return tRiskScript;
	}

	

	private String prepareArray(int m, String tValue, String tColProperties) {
		String tCurrent;
		tCurrent = "iArray["+m+"]=new Array(); "
		         + "iArray["+m+"][0]=\""+tValue+"\" ;"
				 + "iArray["+m+"][1]=\""+tColProperties+"\" ;"
				 + "iArray["+m+"][2]=10 ;"
				 + "iArray["+m+"][3]=2 ;";
		
		return tCurrent;
	}

	
	
	private String prepareByShowColTable(String tRiskScript, ExeSQL tExeSQL,String tType) {
		String tSQL_ShowCol = "select colcode,colname,optionflag,colproperties,nvl((select codename from ldcode where codetype='riskparamsdetail' and code=a.colcode ),'') from PDT_RiskShowCol a "
            + " where templateid='"+this.mTemplateID+"' "
            + " and showtype='"+tType+"' and optionflag='1' order by colorder";
		
		SSRS tSSRS_ShowCol = new SSRS();
		tSSRS_ShowCol = tExeSQL.execSQL(tSQL_ShowCol);
		for(int n=1;n<=tSSRS_ShowCol.getMaxRow();n++)
		{
			String tColCode = tSSRS_ShowCol.GetText(n, 1);
			String tColName = tSSRS_ShowCol.GetText(n, 2);
			String tOptionFlag = tSSRS_ShowCol.GetText(n, 3);
			String tColProperties = tSSRS_ShowCol.GetText(n, 5);
			tRiskScript = tRiskScript + "<tr class=common>" ;
			String tCurrentTDValue = prepareTDValue(tColCode, tColName, tOptionFlag,tColProperties);
			
			tRiskScript = tRiskScript + tCurrentTDValue ;
			tRiskScript = tRiskScript + "</tr>";
		}
		tRiskScript = tRiskScript + "<tr class=common>"
								  + "<td class=title>开始日期：</td>"
							  	  + "<td class=input>"
							  	  + "<input class='coolDatePicker' name=STARTDATE dateFormat='short' elementtype='nacessary' >"
							  	  + "</td>"
							  	  + "</tr>"
							  	  + "<tr class=common>"
							  	  + "<td class=title>结束日期：</td>"
							  	  + "<td class=input>"
							  	  + "<input class='coolDatePicker' name=ENDDATE dateFormat='short' elementtype='nacessary' >"
							  	  + "</td>"
							  	  + "</tr>";
		return tRiskScript;
	}
	
	
	
	
/**
 * 按照传的值生成相应的html的 TD数据
 * @param tColCode
 * @param tColName
 * @param tOptionFlag
 * @param tColProperties
 * @return
 */
	private String prepareTDValue(String tColCode, String tColName,
			String tOptionFlag, String tColProperties) {
		
		String tRelpaceProperties = StrTool.replaceEx(tColProperties, "?RiskCode?",this.mRiskCode);
		tRelpaceProperties = tRelpaceProperties.replaceAll("showCodeList", "showMyCodeList");
		String tTD_Name = "";
		tTD_Name = "<TD CLASS=title>"+tColName+"</TD>" ;
		
		String tTD_Value = "";
		
		tTD_Value = "<TD CLASS=input>";
		if(tOptionFlag.equals("0"))
		{
			tTD_Value = tTD_Value+ "<Input class=common NAME="+tColCode+" "+tRelpaceProperties+" >";
		}
		else
		{
			//tColProperties.replace("\" ", "\" ");
			tTD_Value = tTD_Value+ "<Input NAME="+tColCode+" class=codeno "+tRelpaceProperties+" >"
			          + "<Input class=codename NAME="+tColCode+"Name >";;
			
		}
		
		tTD_Value = tTD_Value + "</TD>";
		
		
		return tTD_Name + tTD_Value;
	}
	
}


