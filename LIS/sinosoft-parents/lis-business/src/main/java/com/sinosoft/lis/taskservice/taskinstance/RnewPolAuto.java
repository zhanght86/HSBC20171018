package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.operfee.RnDealBLF;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * Title:个单续期续保自动催收任务
 * 
 * Description:个单续期续保自动催收任务
 * 
 * Copyright: Copyright (c) 2008.10
 * 
 * Company: SinoSoft
 * 
 * @author mw
 */

public class RnewPolAuto extends TaskThread {
private static Logger logger = Logger.getLogger(RnewPolAuto.class);
//全局变量
private GlobalInput mGlobalInput = new GlobalInput();
	public RnewPolAuto() {
	}

	public boolean dealMain(VData tVData) {
		/* 业务处理逻辑 */
		logger.debug("续期续保自动催收开始：RnewPolAuto...");
		mGlobalInput=(GlobalInput) tVData.getObjectByObjectName(
				"GlobalInput", 0);	    
	
	  	
		String AheadDays = ""; // 催收提前天数--默认60天
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("aheaddays");
		if (tLDSysVarDB.getInfo() == false) {
			AheadDays = "60";
		} else {
			AheadDays = tLDSysVarDB.getSysVarValue();
		}

		LDSysVarDB xLDSysVarDB = new LDSysVarDB();
		xLDSysVarDB.setSysVar("ExtendLapseDates");
		String ExtendDays ="";
		if (xLDSysVarDB.getInfo() == false) 
		{
		  ExtendDays = "0";//默认为0天
		} 
		else 
		{
		  ExtendDays = xLDSysVarDB.getSysVarValue();
		}

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", PubFun.calDate(PubFun
				.getCurrentDate(), -(Integer.parseInt(AheadDays) + Integer.parseInt(ExtendDays)), "D",
				null));
		tTransferData.setNameAndValue("EndDate", PubFun.calDate(PubFun
				.getCurrentDate(), Integer.parseInt(AheadDays), "D", null));
		logger.debug("StartDate"
				+ tTransferData.getValueByName("StartDate"));
		logger.debug("EndDate" + tTransferData.getValueByName("EndDate"));

//		GlobalInput tGlobalInput = new GlobalInput();
//		tGlobalInput.Operator = "001";
//		tGlobalInput.ComCode = "86";
//		tGlobalInput.ManageCom = "86";

//		VData tVData = new VData();
		tVData.add(tTransferData);
//		tVData.add(tGlobalInput);

		RnDealBLF tRnDealBLF = new RnDealBLF();
		tRnDealBLF.submitData(tVData, "ZC");

		// RnewPol tRnewPol = new RnewPol();
		// tRnewPol.dealDueData();

		return true;
	}
	public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("续期续保自动催收开始：RnewPolAuto...");	  	
		String AheadDays = ""; // 催收提前天数--默认60天
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("aheaddays");
		if (tLDSysVarDB.getInfo() == false) {
			AheadDays = "60";
		} else {
			AheadDays = tLDSysVarDB.getSysVarValue();
		}

		LDSysVarDB xLDSysVarDB = new LDSysVarDB();
		xLDSysVarDB.setSysVar("ExtendLapseDates");
		String ExtendDays ="";
		if (xLDSysVarDB.getInfo() == false) 
		{
		  ExtendDays = "0";//默认为0天
		} 
		else 
		{
		  ExtendDays = xLDSysVarDB.getSysVarValue();
		}

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", PubFun.calDate(PubFun
				.getCurrentDate(), -(Integer.parseInt(AheadDays) + Integer.parseInt(ExtendDays)), "D",
				null));
		tTransferData.setNameAndValue("EndDate", PubFun.calDate(PubFun
				.getCurrentDate(), Integer.parseInt(AheadDays), "D", null));
		logger.debug("StartDate"
				+ tTransferData.getValueByName("StartDate"));
		logger.debug("EndDate" + tTransferData.getValueByName("EndDate"));

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ComCode = "86";
		tGlobalInput.ManageCom = "86";

		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tGlobalInput);

		RnDealBLF tRnDealBLF = new RnDealBLF();
		tRnDealBLF.submitData(tVData, "ZC");

		// RnewPol tRnewPol = new RnewPol();
		// tRnewPol.dealDueData();

		return true;
	}

	public static void main(String[] args) {
		RnewPolAuto rnewPolAuto1 = new RnewPolAuto();
		rnewPolAuto1.dealMain();
	}
}
