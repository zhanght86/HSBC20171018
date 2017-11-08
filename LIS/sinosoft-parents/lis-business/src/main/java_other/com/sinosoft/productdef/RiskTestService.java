

package com.sinosoft.productdef;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;

public class RiskTestService {
	
	////////////////////////////
	private String mRiskCode = "";
	
	private String mTemplateID = "";
	private String mRiskType = "";
	
	
	private static String mAppnt = "Appnt";
	private static String mInsured = "Insured";
	private static String mRisk = "Risk";
	private static String mDuty = "Duty";
	private static String mPay = "Pay";
	
	private static String mPubTemplateID = "0000000000";
	
	
	private TransferData mTransferData = new TransferData();
	
	public RiskTestService(HttpServletRequest request)
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
		//获取险种类别
		String tSQL_RiskType = "select risktype from pd_lmriskapp where riskcode='"+this.mRiskCode+"'";
		this.mRiskType = tExeSQL.getOneValue(tSQL_RiskType);
		//获取模板类别
		
		String tSQL_TemplateID = "select templateid from PDT_RiskTypeTemplate where risktype = '"+mRiskType+"'";
		this.mTemplateID = tExeSQL.getOneValue(tSQL_TemplateID);
		
		//看险种是否有自己的
		tSQL_TemplateID = "select templateid from PDT_RiskTypeTemplate where risktype = '"+mRiskCode+"'";
		String t = tExeSQL.getOneValue(tSQL_TemplateID);
		if(t!=null&&!t.equals(""))
		{
			mTemplateID = t;
		}
	}
	
	public RiskTestService(String tRiskCode)
	{
		this.mRiskCode = tRiskCode;
		if(this.mRiskCode!=null&&!this.mRiskCode.equals(""))
		{
			getDefaultProp();
		}
	}
	
	public void setLanguage(String tLanguage)
	{
	}
	
	
	/*
	 * 生成投保人的信息 
	 */
	public String initAppntScript()
	{
		String tAppntScript = "";
		
		//生成投保人 标签
		tAppntScript =  tAppntScript + "<DIV id=DivLCAppntIndButton STYLE=\"display:''\"> "
					 + " <Table> "
					 + " <TR> "
					 + " <TD class= common> "
					 + " <IMG  src= \"../common/images/butExpand.gif\" style= \"cursor:hand;\" > "
					 + "</TD> "
					 + "<TD class= titleImg>"
					 + "投保人信息"
					 + "</TD> "
					 + "</TR> "
					 + "</Table>"
					 + "</DIV>";
		
		
		//div
		tAppntScript = tAppntScript + "<DIV id='DivLCAppnt' STYLE=\"display:''\"> ";
		
		
		//table 
		tAppntScript = tAppntScript + "<table id='TableAppnt' class=common >";
		
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL_ShowType = " select showtyperela,showmethod,showsql from PDT_RiskShowType "
			                + " where templateid='"+this.mPubTemplateID+"' and showtype='"+this.mAppnt+"' ";
		SSRS tSSRS_ShowType = new SSRS();
		tSSRS_ShowType = tExeSQL.execSQL(tSQL_ShowType);
		for(int i=1;i<=tSSRS_ShowType.getMaxRow();i++)
		{
			String tShowTypeRela = tSSRS_ShowType.GetText(i, 1);
			String tShowMethod = tSSRS_ShowType.GetText(i, 2);
			String tShowSQL = tSSRS_ShowType.GetText(i, 3);
			
			if(tShowTypeRela!=null&&tShowTypeRela.equals("0"))
			{
				//tongmeng 2011-06-20 modify
				//投保人显示的信息固定,不按照版本进行修改 
				String tSQL_ShowCol = "select colcode,colname,optionflag,colproperties from PDT_RiskShowCol "
					                + " where templateid='"+this.mPubTemplateID+"' "
					                + " and showtype='"+this.mAppnt+"' order by colorder";
				
				SSRS tSSRS_ShowCol = new SSRS();
				tSSRS_ShowCol = tExeSQL.execSQL(tSQL_ShowCol);
				for(int n=1;n<=tSSRS_ShowCol.getMaxRow();n++)
				{
					String tColCode = tSSRS_ShowCol.GetText(n, 1);
					String tColName = tSSRS_ShowCol.GetText(n, 2);
					String tOptionFlag = tSSRS_ShowCol.GetText(n, 3);
					String tColProperties = tSSRS_ShowCol.GetText(n, 4);
					if(n==1)
                	{
						tAppntScript = tAppntScript + "<tr class=common>" ;
                	}
					String tCurrentTDValue = prepareTDValue(tColCode, tColName, tOptionFlag,tColProperties);
					
					tAppntScript = tAppntScript + tCurrentTDValue ;
					
					if(n%4==0)
                	{
						tAppntScript = tAppntScript + "</tr>" 
						             + "<tr class=common>";
                	}
					
                	if(n==tSSRS_ShowCol.getMaxRow())
                	{
                		tAppntScript = tAppntScript + "</tr>" ;
                	}
				}
			}
		}
		
		
		tAppntScript = tAppntScript + "</table>";
		
		tAppntScript = tAppntScript + "</Div>";
		return tAppntScript;
	}
	
	/*
	 * 生成被保人的信息 
	 */
	public String initInsuredScript()
	{
		String tInsuredScript = "";
		
		//生成投保人 标签
		tInsuredScript =  tInsuredScript + "<DIV id=DivLCInsuredButton STYLE=\"display:''\"> "
					 + " <Table> "
					 + " <TR> "
					 + " <TD class= common> "
					 + " <IMG  src= \"../common/images/butExpand.gif\" style= \"cursor:hand;\" > "
					 + "</TD> "
					 + "<TD class= titleImg>"
					 + "被保人信息"
					 + "</TD> "
					 + "</TR> "
					 + "</Table>"
					 + "</DIV>";
		
		
		//div
		tInsuredScript = tInsuredScript + "<DIV id='DivLCInsured' STYLE=\"display:''\"> ";
		
		
		//table 
		tInsuredScript = tInsuredScript + "<table id='TableInsured' class=common >";
		
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL_ShowType = " select showtyperela,showmethod,showsql from PDT_RiskShowType "
			                + " where templateid='"+this.mPubTemplateID+"' and showtype='"+this.mInsured+"' ";
		SSRS tSSRS_ShowType = new SSRS();
		tSSRS_ShowType = tExeSQL.execSQL(tSQL_ShowType);
		for(int i=1;i<=tSSRS_ShowType.getMaxRow();i++)
		{
			String tShowTypeRela = tSSRS_ShowType.GetText(i, 1);
			String tShowMethod = tSSRS_ShowType.GetText(i, 2);
			String tShowSQL = tSSRS_ShowType.GetText(i, 3);
			
			if(tShowTypeRela!=null&&tShowTypeRela.equals("0"))
			{
				String tSQL_ShowCol = "select colcode,colname,optionflag,colproperties from PDT_RiskShowCol "
					                + " where templateid='"+this.mPubTemplateID+"' "
					                + " and showtype='"+this.mInsured+"' order by colorder";
				
				SSRS tSSRS_ShowCol = new SSRS();
				tSSRS_ShowCol = tExeSQL.execSQL(tSQL_ShowCol);
				for(int n=1;n<=tSSRS_ShowCol.getMaxRow();n++)
				{
					String tColCode = tSSRS_ShowCol.GetText(n, 1);
					String tColName = tSSRS_ShowCol.GetText(n, 2);
					String tOptionFlag = tSSRS_ShowCol.GetText(n, 3);
					String tColProperties = tSSRS_ShowCol.GetText(n, 4);
					if(n==1)
                	{
						tInsuredScript = tInsuredScript + "<tr class=common>" ;
                	}
					String tCurrentTDValue = prepareTDValue(tColCode, tColName, tOptionFlag,tColProperties);
					
					tInsuredScript = tInsuredScript + tCurrentTDValue ;
					
					if(n%4==0)
                	{
						tInsuredScript = tInsuredScript + "</tr>" 
						             + "<tr class=common>";
                	}
					
                	if(n==tSSRS_ShowCol.getMaxRow())
                	{
                		tInsuredScript = tInsuredScript + "</tr>" ;
                	}
				}
			}
		}
		
		
		tInsuredScript = tInsuredScript + "</table>";
		
		tInsuredScript = tInsuredScript + "</Div>";
		return tInsuredScript;
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
					 + " <TD class= common> "
					 + " <IMG  src= \"../common/images/butExpand.gif\" style= \"cursor:hand;\" > "
					 + "</TD> "
					 + "<TD class= titleImg>"
					 + "险种信息"
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

	
	/*
	 * 生成缴费信息 
	 */
	public String initPayScript()
	{
		String tPayScript = "";
		
		//生成投保人 标签
		
		
		ExeSQL tExeSQL = new ExeSQL();
		//String tSQL_ShowType = " select showtyperela,showmethod,showsql from PDT_RiskShowType "
		//	                + " where templateid='"+this.mTemplateID+"' and showtype='"+this.mPay+"' ";
		String tSQL_ShowType = "select 1 from PDT_RiskShowType where showtype='Pay' ";
		String tPayFlag = tExeSQL.getOneValue(tSQL_ShowType);
		
		//SSRS tSSRS_ShowType = new SSRS();
		//tSSRS_ShowType = tExeSQL.execSQL(tSQL_ShowType);
		//if(tSSRS_ShowType.getMaxRow()>0)
		{
			
			if(tPayFlag!=null&&tPayFlag.equals("1"))
			{
			tPayScript =  tPayScript + "<DIV id=DivLCRiskPayButton STYLE=\"display:''\"> "
			 + " <Table> "
			 + " <TR> "
			 + " <TD class= common> "
			 + " <IMG  src= \"../common/images/butExpand.gif\" style= \"cursor:hand;\" > "
			 + "</TD> "
			 + "<TD class= titleImg>"
			 + "可选缴费信息"
			 + "</TD> "
			 + "</TR> "
			 + "</Table>"
			 + "</DIV>";


			//div
			tPayScript = tPayScript + "<DIV id='DivLCRiskPay' STYLE=\"display:''\"> ";
			}
			else
			{
				tPayScript =  tPayScript + "<DIV id=DivLCRiskPayButton STYLE=\"display:'none'\"> "
			 + " <Table> "
			 + " <TR> "
			 + " <TD class= common> "
			 + " <IMG  src= \"../common/images/butExpand.gif\" style= \"cursor:hand;\" > "
			 + "</TD> "
			 + "<TD class= titleImg>"
			 + "可选缴费信息"
			 + "</TD> "
			 + "</TR> "
			 + "</Table>"
			 + "</DIV>";


			//div
			tPayScript = tPayScript + "<DIV id='DivLCRiskPay' STYLE=\"display:'none'\"> ";
			}

			//table 
			tPayScript = tPayScript + "<table id='TableRiskPay' class=common >";
			
			
		}
		//for(int i=1;i<=tSSRS_ShowType.getMaxRow();i++)
		{
			//String tShowTypeRela = tSSRS_ShowType.GetText(i, 1);
			//String tShowMethod = tSSRS_ShowType.GetText(i, 2);
			//String tShowSQL = tSSRS_ShowType.GetText(i, 3);
				String tShowTypeRela ="1";
			String tShowMethod = "1";
			String tShowSQL = "select a.dutycode, a.payplancode, a.payplanname, '' from lmdutypayrela a where dutycode in (select dutycode from lmriskduty where riskcode = '?RiskCode?' and SpecFlag = 'N')";
			
			if(tShowTypeRela!=null&&tShowTypeRela.equals("0"))
			{
				//直接查表生成
				tPayScript = tPayScript + prepareByShowColTable(tPayScript, tExeSQL,this.mPay);
			}
			else
			{
				//按照SQL生成  
				if(tShowMethod.equals("1"))
				{
					//MultLine 形式 
					String tDivPrem = "";
					tDivPrem = "<Div  id= \"divPremGrid1\" style= \"display: ''\"> "
							 + "<table  class= common>"
							 + "<tr  class= common> "
							 + "<td text-align: left colSpan=1> "
							 + "<span id=\"spanPremGrid\" >"
							 + "</span>"
							 + "</td>"
							 + "</tr>"
							 + "</table>"
							 + "</div>";
					
					tPayScript = tPayScript + tDivPrem;
					//获取表头信息 
					//
					String tSQL_TableTitle = "select colcode,colname,optionflag,colproperties,nvl((select codename from ldcode where codetype='riskparamsdetail' and code=a.colcode ),'') from PDT_RiskShowCol a "
						                   + " where templateid='"+this.mTemplateID+"' and showtype='"+this.mPay+"' order by colorder";
					//String tSQL_TableTitle =  "select codename,codename,'0',codealias from ldcode where codetype='riskparamscol' order by othersign/1 ";
					SSRS tTitle_SSRS = new SSRS();
					tTitle_SSRS = tExeSQL.execSQL(tSQL_TableTitle);
					int multLineColNum = tTitle_SSRS.getMaxRow();
					String tInitMultLine = "";
					tInitMultLine = initPremFunction(tTitle_SSRS); 
					
					this.mTransferData.setNameAndValue("initPremGrid",tInitMultLine);
					
					
					//
					tShowSQL = StrTool.replaceEx(tShowSQL, "?RiskCode?",this.mRiskCode);
					String tFuncQueryPremGrid = " function QueryPremGrid() {" 
						                      + "var tSql = \""+tShowSQL+"\";"
						                      //+ " var turnPage = new turnPageClass(); "
											  + "turnPage.queryModal(tSql, PremGrid); "
											  + " PremGrid.lock; "
											  + "}";
					this.mTransferData.setNameAndValue("QueryPremGrid",tFuncQueryPremGrid);
				}	
			}
		}
		
		//if(tSSRS_ShowType.getMaxRow()>0)
		//if(tPayFlag!=null&&tPayFlag.equals("1"))
		{
			tPayScript = tPayScript + "</table>";
		
			tPayScript = tPayScript + "</Div>";
		}
		
		System.out.println();
		return tPayScript;
	}

	/**
	 * 初始化缴费需要使用的函数  
	 * @param tTitle_SSRS
	 * @return
	 */
	private String initPremFunction(SSRS tTitle_SSRS) {
		String tInitMultLine;
		tInitMultLine = "function initPremGrid() { "
			          + " var iArray = new Array(); " 
			          + "  try { "
			          ;
		tInitMultLine = tInitMultLine + prepareArray(0, "序号", "30px","0","","Idx");  
		
		for(int m=1;m<=tTitle_SSRS.getMaxRow();m++ )
		{
			String tCurrent = "";
			String tCode = tTitle_SSRS.GetText(m, 1);
			String tValue = tTitle_SSRS.GetText(m, 2);
			String tColProperties = tTitle_SSRS.GetText(m, 4);
			String tOptionFlag = tTitle_SSRS.GetText(m, 3);
			String tShowCodeList = tTitle_SSRS.GetText(m, 5);
			if(tColProperties==null||tColProperties.equals(""))
			{
				tColProperties = "80px";
			}
			tCurrent = prepareArray(m, tValue, tColProperties,tOptionFlag,tShowCodeList,tCode);  
			tInitMultLine = tInitMultLine + tCurrent;
		}
		
		tInitMultLine = tInitMultLine + "if(typeof(window.spanPremGrid) == \"undefined\" )"
					  + " { "
					  + " return false; "
					  + " } "
					  + " else "
					  + " { "
					  + " PremGrid = new MulLineEnter( \"fm\" , \"PremGrid\" ); "
					  + " PremGrid.mulLineCount = 0; "   
					  + " PremGrid.displayTitle = 1; "
					  + " PremGrid.canChk = 1; "
					  + " PremGrid.hiddenSubtraction = 1; "
					  + " PremGrid.canSel=0; "
					  + " PremGrid.hiddenPlus=1; "
					  + " PremGrid.loadMulLine(iArray); "  
		 			  + "} "
		 			  
					  + " } "
					  + " catch(ex) "
					  + " { "
					  + " return false; "
					  + " } "
					  + " return true; "
					  + " } ";
		
		return tInitMultLine;
	}

	private String prepareArray(int m, String tValue, String tColProperties,String tOptionFlag,String tShowList,String tCode) {
		String tCurrent;
		tCurrent = "iArray["+m+"]=new Array(); "
		         + "iArray["+m+"][0]=\""+tValue+"\" ;"
				 + "iArray["+m+"][1]=\""+tColProperties+"\" ;"
				 + "iArray["+m+"][2]=10 ;"
				 + "iArray["+m+"][3]=2 ;";
		
		if(tOptionFlag!=null&&tOptionFlag.equals("1"))
		{
			tCurrent = tCurrent 
			         +  "iArray["+m+"][4]=\""+tCode+"\";"
			         +  "iArray["+m+"][9]=\""+tValue+"|code:"+tCode+"\";"
			         ;
			
			//System.out.println("tCurrent:"+tCurrent);
		}
		/*
		   iArray[7]=new Array();
      iArray[7][0]="币种";         		//列名
      iArray[7][1]="50px";            		        //列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      iArray[7][4]="Currency";              	        //是否引用代码:null||""为不引用
      iArray[7][9]="币种|code:Currency";
		 */
		return tCurrent;
	}

	
	
	private String prepareByShowColTable(String tRiskScript, ExeSQL tExeSQL,String tType) {
		String tSQL_ShowCol = "select colcode,colname,optionflag,colproperties,nvl((select codename from ldcode where codetype='riskparamsdetail' and code=a.colcode ),'') from PDT_RiskShowCol a "
			                + " where templateid='"+this.mTemplateID+"' "
			                + " and showtype='"+tType+"' order by colorder";
		
		SSRS tSSRS_ShowCol = new SSRS();
		tSSRS_ShowCol = tExeSQL.execSQL(tSQL_ShowCol);
		for(int n=1;n<=tSSRS_ShowCol.getMaxRow();n++)
		{
			String tColCode = tSSRS_ShowCol.GetText(n, 1);
			String tColName = tSSRS_ShowCol.GetText(n, 2);
			String tOptionFlag = tSSRS_ShowCol.GetText(n, 3);
			String tColProperties = tSSRS_ShowCol.GetText(n, 5);
			
			if(n==1)
			{
				tRiskScript = tRiskScript + "<tr class=common>" ;
			}
			String tCurrentTDValue = prepareTDValue(tColCode, tColName, tOptionFlag,tColProperties);
			
			tRiskScript = tRiskScript + tCurrentTDValue ;
			
			if(n%4==0)
			{
				tRiskScript = tRiskScript + "</tr>" 
				             + "<tr class=common>";
			}
			
			if(n==tSSRS_ShowCol.getMaxRow())
			{
				tRiskScript = tRiskScript + "</tr>" ;
			}
		}
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
