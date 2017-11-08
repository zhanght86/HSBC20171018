package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/**
 * <p>Title:保全报表</p>
 * <p>Description: 1张报表</p>
 * <p>bq1：协议退保险种差值统计报表</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @version 1.0
 */

public class GTRiskMarginStatBL {
private static Logger logger = Logger.getLogger(GTRiskMarginStatBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
		public CErrors mErrors = new CErrors();
		private VData mResult = new VData();

		/** 全局数据 */
		private GlobalInput mGlobalInput = new GlobalInput();
		private String mStartDate = "";
		private String mEndDate = "";
		private String mManageCom = "";
    private String mSaleChnl = "";

		public GTRiskMarginStatBL() {
		}
		/**传输数据的公共方法*/
		public boolean submitData(VData cInputData, String cOperate)
		{
				if (!cOperate.equals("PRINT"))
				{
						CError.buildErr(this, "不支持的操作字符串");
						return false;
				}

				if (!getInputData(cInputData))
				{
						return false;
				}

				if (!getPrintData())
				{
						return false;
				}

				return true;
		}

		/**
		 * 从输入数据中得到所有对象
		 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
		 */
		private boolean getInputData(VData cInputData)
		{
				mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",0));
				TransferData tTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);

				if (tTransferData == null || mGlobalInput == null)
				{
						CError.buildErr(this, "传入后台的参数缺少！");
						return false;
				}

				mStartDate = (String)tTransferData.getValueByName("StartDate");
				mEndDate = (String)tTransferData.getValueByName("EndDate");
				mManageCom = (String)tTransferData.getValueByName("ManageCom");
				mSaleChnl = (String)tTransferData.getValueByName("SaleChnl");

				if(mStartDate.equals("") || mEndDate.equals("") || mManageCom.equals(""))
				{
						CError.buildErr(this, "没有得到足够的查询信息！");
						return false;
				}

				return true;
		}

		private boolean getPrintData()
		{
				XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
				xmlexport.createDocument("GTRiskMarginStat.vts", "");

				ListTable tlistTable = new ListTable();
				tlistTable.setName("GTMargin");
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				String strSaleChnl = "";

				if(mSaleChnl != null && !mSaleChnl.equals(""))
				{
					 strSaleChnl = " and exists (select 1 from lcpol where salechnl='"
									       + "?mSaleChnl?" + "' and polno=a.polno)";
				}

				String sql = "select riskcode,(case when sum(getmoney) is not null then sum(getmoney)  else 0 end),(case when sum(a.serialno) is not null then sum(a.serialno)  else 0 end),(case when sum(getmoney - (case when a.serialno is not null then a.serialno  else 0 end)) is not null then sum(getmoney - (case when a.serialno is not null then a.serialno  else 0 end))  else 0 end) from ljagetendorse  a where managecom like concat('"
							     + "?mManageCom?" + "','%') and grppolno='00000000000000000000' and feeoperationtype = 'XT' "
									 + ReportPubFun.getWherePart("makedate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)
									 + strSaleChnl + " group  by riskcode  ";
				sqlbv1.sql(sql);
				sqlbv1.put("mManageCom", mManageCom);
				sqlbv1.put("mSaleChnl", mSaleChnl);
				sqlbv1.put("mStartDate", mStartDate);
				sqlbv1.put("mEndDate", mEndDate);
				//logger.debug(sql);
				SSRS tssrs = new ExeSQL().execSQL(sqlbv1);
				if(tssrs == null || tssrs.getMaxRow() <= 0)
				{
						CError.buildErr(this, "查询到的数据为空！");
						return false;
				}

				
			

				String[] strArr = new String[4];
				double [] sum ={0,0,0};
				for(int i = 1 ; i <= tssrs.getMaxRow() ; i ++)
				{     strArr[0] = ReportPubFun.getRiskName(tssrs.GetText(i,1)); 
						strArr[1] = ReportPubFun.functionJD(Double.parseDouble(tssrs.GetText(i,2)),"0.00"); 
						strArr[2] = ReportPubFun.functionJD(Double.parseDouble(tssrs.GetText(i,3)),"0.00"); 
						strArr[3] = ReportPubFun.functionJD(Double.parseDouble(tssrs.GetText(i,4)),"0.00"); 
						tlistTable.add(strArr);
						
				strArr = new String[4];	
				
				sum[0]+=Double.parseDouble(tssrs.GetText(i,2));
				sum[1]+=Double.parseDouble(tssrs.GetText(i,3));
				
				sum[2]+=Double.parseDouble(tssrs.GetText(i,4));	
					
				}

				String[] tArr = new String[4];
				tArr[0] = "合计";
				tArr[1] = ReportPubFun.functionJD(sum[0],"0.00");
				tArr[2] = ReportPubFun.functionJD(sum[1],"0.00");
				tArr[3] = ReportPubFun.functionJD(sum[2],"0.00");
				tlistTable.add(tArr);

			
				xmlexport.addListTable(tlistTable, tArr);

				TextTag texttag = new TextTag(); //新建一个TextTag的实例
				texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
				texttag.add("StartDate", mStartDate);
				texttag.add("EndDate", mEndDate);
				texttag.add("Date", PubFun.getCurrentDate());
				logger.debug("大小" + texttag.size());
				if (texttag.size() > 0)
				{
						xmlexport.addTextTag(texttag);
				}

//        xmlexport.outputDocumentToFile("e:\\","GTMarginStat.xml");//输出xml文档到文件
				mResult.clear();
				mResult.addElement(xmlexport);

				return true;
		}

		public VData getResult()
		{
				return this.mResult;
		}

		public static void main (String []args)
		{
				GlobalInput tGlobalInput = new GlobalInput();
				tGlobalInput.ComCode = "86";
				tGlobalInput.ManageCom = "8613";
				tGlobalInput.Operator = "DEBUG";

				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("StartDate","2005-04-15");
				tTransferData.setNameAndValue("EndDate","2005-04-15");
				tTransferData.setNameAndValue("ManageCom","8613");
				tTransferData.setNameAndValue("SaleChnl","");

				VData tVData = new VData();
				tVData.add(tGlobalInput);
				tVData.add(tTransferData);

				GTRiskMarginStatBL tGTRiskMarginStatBL = new GTRiskMarginStatBL();
				tGTRiskMarginStatBL.submitData(tVData,"PRINT");
    }
}
