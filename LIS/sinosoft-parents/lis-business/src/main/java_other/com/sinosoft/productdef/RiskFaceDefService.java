



package com.sinosoft.productdef;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.lis.db.PDT_RiskShowColDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.PDT_RiskShowColSchema;
import com.sinosoft.lis.vschema.PDT_RiskShowColSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RiskFaceDefService{
	
	private String mLanguage = "";
	
	////////////////////////////
	private String mRiskCode = "";
	private String mStandbyFlag1 = "";
	
	private String mTemplateID = "";
	private String mRiskType = "";
	
	
	private static String mAppnt = "Appnt";
	private static String mInsured = "Insured";
	private static String mRisk = "Risk";
	private static String mDuty = "Duty";
	private static String mPay = "Pay";
	
	private static String mPubTemplateID = "0000000000";
	
	
	private TransferData mTransferData = new TransferData();
	
	public RiskFaceDefService(HttpServletRequest request)
	{
		 mRiskCode = (String)request.getParameter("RiskCode");
		if(this.mRiskCode!=null&&!this.mRiskCode.equals(""))
		{
			getDefaultProp();
		}
	}
	
	public RiskFaceDefService()
	{
		
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
		
		String tSQL_TemplateID = "select templateid from PDT_RiskTypeTemplate where risktype = '"+mRiskCode+"' and standbyflag1 = '"+mStandbyFlag1+"'";
		this.mTemplateID = tExeSQL.getOneValue(tSQL_TemplateID);
	}
	
	public RiskFaceDefService(String tRiskCode)
	{
		this.mRiskCode = tRiskCode;
		this.mStandbyFlag1 = "01";
		if(this.mRiskCode!=null&&!this.mRiskCode.equals(""))
		{
			getDefaultProp();
		}
	}

	public RiskFaceDefService(String tRiskCode,String tStandbyFlag1)
	{
		this.mRiskCode = tRiskCode;
		this.mStandbyFlag1 = tStandbyFlag1;
		if(this.mRiskCode!=null&&!this.mRiskCode.equals(""))
		{
			getDefaultProp();
		}
	}
	
	public void setLanguage(String tLanguage)
	{
		this.mLanguage = tLanguage;
	}
	
	
	public String initRiskInput()
	{
		String tResult = "";
		return tResult;
	}
	
	
	
	/*
	 * 生成险种信息 
	 */
	public String initRiskScript()
	{
		String tRiskScript = "";
		
		//生成投保人 标签
//		tRiskScript =  tRiskScript + "<DIV id=DivLCRiskButton STYLE=\"display:''\"> "
//					 + " <Table> "
//					 + " <TR> "
//					 + " <TD> "
//					 + " <IMG  src= \"../common/images/butExpand.gif\" style= \"cursor:hand;\" > "
//					 + "</TD> "
//					 + "<TD class= titleImg>"
//					 + "险种信息"
//					 + "</TD> "
//					 + "</TR> "
//					 + "</Table>"
//					 + "</DIV>";
		
		
		//div
		//tRiskScript = tRiskScript + "<DIV id='DivLCRisk' STYLE=\"display:''\"> ";
		
		
		//table 
		//tRiskScript = tRiskScript + "<table id='TableRisk' class=common >";
		//tRiskScript = tRiskScript + "<div class='divRisk'> ";
		tRiskScript = tRiskScript + "<ul id='ulRisk'> ";
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
		
		tRiskScript = tRiskScript + "</ul> ";
		//tRiskScript = tRiskScript + "</div> ";
		//tRiskScript = tRiskScript + "</table>";
		
		//tRiskScript = tRiskScript + "</Div>";
		
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
		String tSQL_ShowType = " select showtyperela,showmethod,showsql from PDT_RiskShowType "
			                + " where templateid='"+this.mTemplateID+"' and showtype='"+this.mPay+"' ";
		SSRS tSSRS_ShowType = new SSRS();
		tSSRS_ShowType = tExeSQL.execSQL(tSQL_ShowType);
		if(tSSRS_ShowType.getMaxRow()>0)
		{	
			tPayScript =  "1";
		}
		//System.out.println();
		return tPayScript;
	}

	/*
	 * 生成责任信息 
	 */
	public String initDutyScript()
	{
		String tDutyScript = "";
		
		//生成投保人 标签
		
		
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL_ShowType = " select showtyperela,showmethod,showsql from PDT_RiskShowType "
			                + " where templateid='"+this.mTemplateID+"' and showtype='"+this.mDuty+"' ";
		SSRS tSSRS_ShowType = new SSRS();
		tSSRS_ShowType = tExeSQL.execSQL(tSQL_ShowType);
		if(tSSRS_ShowType.getMaxRow()>0)
		{	
			tDutyScript =  "1";
		}
		//System.out.println();
		return tDutyScript;
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
		tInitMultLine = tInitMultLine + prepareArray(0, "序号", "30px");  
		
		for(int m=1;m<=tTitle_SSRS.getMaxRow();m++ )
		{
			String tCurrent = "";
			String tValue = tTitle_SSRS.GetText(m, 1);
			String tColProperties = tTitle_SSRS.GetText(m, 4);
			if(tColProperties==null||tColProperties.equals(""))
			{
				tColProperties = "80px";
			}
			tCurrent = prepareArray(m, tValue, tColProperties);  
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
		String tSQL_ShowCol = "select colcode,colname,optionflag,colproperties from PDT_RiskShowCol "
			                + " where templateid='"+this.mTemplateID+"' "
			                + " and showtype='"+tType+"' order by colorder";
		
		SSRS tSSRS_ShowCol = new SSRS();
		tSSRS_ShowCol = tExeSQL.execSQL(tSQL_ShowCol);
		for(int n=1;n<=tSSRS_ShowCol.getMaxRow();n++)
		{
			String tColCode = tSSRS_ShowCol.GetText(n, 1);
			String tColName = tSSRS_ShowCol.GetText(n, 2);
			String tOptionFlag = tSSRS_ShowCol.GetText(n, 3);
			String tColProperties = tSSRS_ShowCol.GetText(n, 4);
			
			tRiskScript = tRiskScript + "<li id='"+tColCode+"'>" ;
			
			String tCurrentTDValue = prepareTDValue(tColCode, tColName, tOptionFlag,tColProperties);
			tRiskScript = tRiskScript + tCurrentTDValue ;
			
			
			
			//if(n==tSSRS_ShowCol.getMaxRow())
			{
				tRiskScript = tRiskScript + "</li>" ;
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
	
	
	//保存界面调整的数据
	public boolean savRiskOrder(VData tInputData)
	{
		String tRiskOrder = (String)tInputData.get(0);
		System.out.println("tRiskOrder:"+tRiskOrder);
		String[] riskParams = tRiskOrder.split("#");
		String tRiskCode = (String)tInputData.get(1);
		String tStandbyFlag1 = (String)tInputData.get(2);
		String tSQL = "select * from pdt_riskshowcol where showtype='Risk' and templateid = "
			        + " (select templateid from pdt_risktypetemplate where risktype='"+tRiskCode+"' and standbyflag1='"+tStandbyFlag1+"' ) order by colorder " ; 
		PDT_RiskShowColSet tPDT_RiskShowColSet  = new PDT_RiskShowColSet();
		PDT_RiskShowColSet tPDT_RiskShowColSetIns  = new PDT_RiskShowColSet();
		PDT_RiskShowColDB tPDT_RiskShowColDB = new PDT_RiskShowColDB();
		
		tPDT_RiskShowColSet =  tPDT_RiskShowColDB.executeQuery(tSQL);
		if(tPDT_RiskShowColSet.size()<=0)
		{
			return false;
		}
		//tPDT_RiskShowColSetDel.set(tPDT_RiskShowColSet);
		//调整顺序
		for(int i=0;i<riskParams.length;i++)
		{
			String tRiskParam = riskParams[i];
			System.out.println("tRiskParam:"+tRiskParam);
			if(tRiskParam==null||tRiskParam.equals(""))
			{
				continue;
			}
			for(int n=1;n<=tPDT_RiskShowColSet.size();n++)
			{
				if(tPDT_RiskShowColSet.get(n).getColCode().equalsIgnoreCase(tRiskParam))
				{
					PDT_RiskShowColSchema tPDT_RiskShowColSchema  = new PDT_RiskShowColSchema();
					tPDT_RiskShowColSchema.setSchema(tPDT_RiskShowColSet.get(n));
					tPDT_RiskShowColSchema.setColOrder(i+1);
					//tPDT_RiskShowColSchema
					tPDT_RiskShowColSetIns.add(tPDT_RiskShowColSchema);
					
					break;
				}
			}
		}
		
		MMap mMMap = new MMap();
		mMMap.put(tPDT_RiskShowColSet, "DELETE");
		mMMap.put(tPDT_RiskShowColSetIns, "INSERT");
		
		PubSubmit tPubSubmit =  new PubSubmit();
		VData tSubmitData = new VData();
		tSubmitData.add(mMMap);
		if(!tPubSubmit.submitData(tSubmitData, ""))
		{
			//System.out.println(tSubmitData.)
		}
		
		
		return true;
	}
}

