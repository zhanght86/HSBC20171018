/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.otof.OtoFUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.CErrors;
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
public class OtoFTask extends TaskThread {
private static Logger logger = Logger.getLogger(OtoFTask.class);
	// private static String[] Otofargs;
	public CErrors mErrors = new CErrors();

	public OtoFTask() {
	}

	public boolean dealMain() {
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  OtoFTask Execute !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理OtoFTask!");

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
		// }
		// else
		// {
		// tString = Otofargs[0];
		// logger.debug("set inputdate as bussdate");
		// }

		String bdate = tString;
//		bdate = "2008-11-26";
		String edate = bdate;
		String tFlag = "1"; // 自动提取

		Logger log = Logger.getLogger("com.sinosoft.lis.otof");
		log.info("************开始提取" + tString + "的数据************");

		if (flag.equals("true")) {
			

			for (int i = 1; i <= 8; i++) {
				Integer itemp = new Integer(i);
				VData vData = new VData();
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("mStartDate", bdate);
				tTransferData.setNameAndValue("mEndDate", edate);
				tTransferData.setNameAndValue("itemp", itemp);
				tTransferData.setNameAndValue("DateFlag", tFlag);
				tTransferData.setNameAndValue("mInputDate", tAccountDate);
				tTransferData.setNameAndValue("cManageCom", tG.ManageCom);
				vData.addElement(tG);
				vData.addElement(tTransferData);

				log.info("凭证" + i + "加载完成");
				OtoFUI tOtoFUI = new OtoFUI();
				if (!tOtoFUI.submitData(vData, "Buss")) {
					CErrors cerr = tOtoFUI.mErrors;
					log.error("凭证" + i + "提取失败，原因：" + cerr.toString());
					flag = "false";
				} else {
				}
			}

			// end for
		}

		// end if
		log.info("**********提取" + tString + "数据结束*************");

		/* end 业务处理逻辑 add by ck */
		return true;
	}

	public static void main(String[] args) {
		// Otofargs = args;

		OtoFTask tOtoFTask = new OtoFTask();
		logger.debug("before OtoFTask!");
		tOtoFTask.run();
	}
}
