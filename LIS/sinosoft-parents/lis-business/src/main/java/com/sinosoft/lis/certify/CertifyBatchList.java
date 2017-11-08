package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
import com.sinosoft.service.BusinessService;



public class CertifyBatchList implements BusinessService {
private static Logger logger = Logger.getLogger(CertifyBatchList.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGI = new GlobalInput();
	private String mStartDate; // 统计开始日期
	private String mEndDate; // 统计结束日期

	public CertifyBatchList() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("kaishi");
		if (!getInputData(cInputData, cOperate))
			return false;

		if (!dealData())
			return false;

		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		try{
			mStartDate = (String) cInputData.get(0);
			mEndDate = (String) cInputData.get(1);
		    mGI = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);

		}catch (Exception ex) {
		ex.printStackTrace();
		CError tError = new CError();
		tError.errorMessage = "统计时发生异常！";
		this.mErrors.addOneError(tError);
		return false;

	}
		return true;
	}
		
	private boolean dealData() {
		try {
			  if (!PrePareData())
			  return false;
			}
		    catch (Exception ex) {
			ex.printStackTrace();
			CError tError = new CError();
			tError.errorMessage = "统计时发生异常！";
			this.mErrors.addOneError(tError);
			return false;

		}
		return true;
	}

	private boolean PrePareData() {

		try {
			ExeSQL tExeSQL = new ExeSQL();			
			ListTable aListTable = new ListTable();
			aListTable.setName("Certify");	
			int maxColCount=4;//列数			

			String strArr[] = new String[maxColCount];
			String tSQL_key="";	
			
			tSQL_key =" select CertifyCode,StartNo,Reason,MakeDate from LZCardBatch where flag='N'"
				     +" and makedate >='"+"?date1?"+"' and makedate <='"+"?date2?"+"' order by MakeDate,CertifyCode,StartNo";
			logger.debug("tSQL_key="+tSQL_key);	
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSQL_key);
			sqlbv.put("date1", mStartDate);
			sqlbv.put("date2", mEndDate);
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			logger.debug("tSSRS.getMaxRow():"+tSSRS.getMaxRow());
			for (int i = 0; i < tSSRS.getMaxRow(); i++) 
			{				
				strArr = new String[maxColCount];
				for(int j=0;j<maxColCount;j++)
				{
					strArr[j] = tSSRS.GetText(i+1, j+1);
				}				
				aListTable.add(strArr);
			}		
			
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("CertifyBatch.vts","printer");//最好紧接着就初始化xml文档
		    xmlexport.addListTable(aListTable, strArr);
		      
		    String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例			
			texttag.add("PrintDate", CurrentDate);	
		    if (texttag.size()>0)
		        xmlexport.addTextTag(texttag);
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError tError = new CError();
			tError.errorMessage = "统计时发生异常comm！";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("over!");
		return true;
	
	}

	public VData getResult() {
		return this.mResult;
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public static void main(String[] args) {		
	}
}
