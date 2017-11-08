package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

import com.sinosoft.lis.db.OFinaLogDB;
import com.sinosoft.lis.otof.OtoFLABL;
import com.sinosoft.lis.otof.OtoFUI;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class AutoOtoFACharge extends TaskThread
{
private static Logger logger = Logger.getLogger(AutoOtoFACharge.class);


	/**
	 * 业务员押金凭证自动提取
	 * 2008-7-25 zy
	 */
	
	
	  public CErrors mErrors = new CErrors();

	  public boolean dealMain()
	  {
	    /*业务处理逻辑*/
	    logger.debug("开始业务员押金财务凭证自动提取...");

	      GlobalInput tG = new GlobalInput();
	      tG.Operator = "001";
	      tG.ManageCom = "86";

	      String flag = "true";
	      String tString = "";


	      String pattern = "yyyy-MM-dd";
	      SimpleDateFormat df = new SimpleDateFormat(pattern);
	      java.util.Date today = new java.util.Date();
	      java.util.Date finday = PubFun.calDate(today, -1, "D",new java.util.Date());
	      tString = df.format(finday);
	      logger.debug("set yesterday as bussdate");

	      String bdate = tString;
//	      bdate="2009-04-27";
	      finday = new FDate().getDate(bdate);
	      String edate = bdate;
	      String tFlag = "1"; //自动提取


	      if (flag.equals("true"))
	      {


	        for (int i = 82; i <= 83; i++)
	        {
	        	String mComSql = "select distinct trim(comcode) from ldcom where comcode like '86%' and length(trim(comcode))=6 order by trim(comcode)";
	        	SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	        	sqlbv.sql(mComSql);
	        	SSRS tSsrs = new SSRS();
	        	ExeSQL tExeSQL = new ExeSQL();
	        	tSsrs = tExeSQL.execSQL(sqlbv);
	        	for (int j=1;j<=tSsrs.MaxRow;j++)
	        	{
	        		OFinaLogDB tOFinaLogDB = new OFinaLogDB();
	        		String tRecordNo="";  
	        		tRecordNo=tSsrs.GetText(j, 1)+new SimpleDateFormat("yyyyMMdd").format(finday)+String.valueOf(i);
	        		tOFinaLogDB.setRecordNo(tRecordNo);
	        		logger.debug("记录号为------"+tRecordNo);
	        		//如果已经对该机构进行了凭证提取的处理，无论提取是否成功，都不在重复进行处理
	        		if(tOFinaLogDB.getCount()>0)
	        			continue;
	                Integer itemp = new Integer(i);
	                VData vData = new VData();
//	                vData.addElement(tG);
//	                vData.addElement(bdate);
//	                vData.addElement(edate);
//	                vData.addElement(itemp);
//	                vData.addElement(tFlag);
//	                vData.addElement(""); //新增界面记帐日期
//	                vData.addElement(tSsrs.GetText(j, 1));//新增对传入机构的处理
	                TransferData mTransferData = new TransferData();
	                mTransferData.setNameAndValue("itemp",itemp );
	                mTransferData.setNameAndValue("DateFlag", "1");
	                mTransferData.setNameAndValue("mInputDate", "");
	                mTransferData.setNameAndValue("cManageCom", tSsrs.GetText(j, 1));
	                mTransferData.setNameAndValue("mStartDate", bdate);
	                mTransferData.setNameAndValue("mEndDate",edate );
	                logger.debug("提取日期------"+bdate);
	                logger.debug("提取日期------"+finday);
					vData.addElement(tG);
					vData.addElement(mTransferData);
			        OtoFLABL tOtoFLABL = new OtoFLABL();
	                if (!tOtoFLABL.submitData(vData, "PRINT"))
	                {
	                  CErrors cerr = tOtoFLABL.mErrors;
	                  flag = "false";
	                }
	        	}
	        }

	        //end for
	      }	    return true;
	  }
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		AutoOtoFACharge tAutoOtoFACharge = new AutoOtoFACharge();
		tAutoOtoFACharge.dealMain();

	}

}
