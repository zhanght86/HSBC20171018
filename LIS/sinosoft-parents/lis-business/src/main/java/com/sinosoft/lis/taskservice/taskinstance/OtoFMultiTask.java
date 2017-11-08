/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.otof.OtoFUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 财务凭证自动提取－后台自动处理入口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zy
 * @version 1.0
 * @CreateDate：2007-12-29
 */
public class OtoFMultiTask extends TaskThread {
private static Logger logger = Logger.getLogger(OtoFMultiTask.class);
	// private static String[] Otofargs;
	public CErrors mErrors = new CErrors();

	public OtoFMultiTask() {
	}

	public boolean dealMain() {
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  OtoFTask Execute !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理OtoFMultiTask!");

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		String flag = "true";
		String tString = "";
		String tAccountDate = "";

		// if (Otofargs.length == 0)
		// {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		java.util.Date today = new java.util.Date();
		java.util.Date finday = PubFun.calDate(today, -1, "D",
				new java.util.Date());
		tString = df.format(finday);
		logger.debug("set yesterday as bussdate");


		String bdate = tString;
		String edate = bdate;
		String tFlag = "1"; // 自动提取

		Logger log = Logger.getLogger("com.sinosoft.lis.otof");
		log.info("************开始提取" + tString + "的数据************");

		if (flag.equals("true")) {
			

			for (int i = 1; i <= 8; i++) {
				Integer itemp = new Integer(i);
	        	String mComSql = "select distinct trim(comcode) from ldcom where comcode like '86%' and char_length(trim(comcode))=8 order by trim(comcode)";
	        	SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(mComSql);
	        	ExeSQL mExeSQL = new ExeSQL();
	        	SSRS comSSRS = mExeSQL.execSQL(sqlbv1);
	        	Vector mTaskWaitList = new Vector();
	        	for (int j=1;j<=comSSRS.MaxRow;j++)
	        	{
	        		String cManageCom= comSSRS.GetText(j, 1);
					VData vData = new VData();
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("mStartDate", bdate);
					tTransferData.setNameAndValue("mEndDate", edate);
					tTransferData.setNameAndValue("itemp", itemp);
					tTransferData.setNameAndValue("DateFlag", tFlag);
					tTransferData.setNameAndValue("mInputDate", tAccountDate);
					tTransferData.setNameAndValue("cManageCom", cManageCom);
					vData.addElement(tG);
					vData.addElement(tTransferData);
					mTaskWaitList.add(vData);
	        		
	        	}
				 ServiceA tService = new ServiceA("com.sinosoft.lis.otof.OtoFMultiBL",mTaskWaitList, 15,15);
				 tService.start();
	        	

			}

			// end for
		}

		// end if
		log.info("**********提取" + tString + "数据结束*************");


		return true;
	}

	public static void main(String[] args) {
		// Otofargs = args;

		OtoFMultiTask OtoFMultiTask = new OtoFMultiTask();
		logger.debug("before OtoFMultiTask!");
		OtoFMultiTask.run();
	}
}
