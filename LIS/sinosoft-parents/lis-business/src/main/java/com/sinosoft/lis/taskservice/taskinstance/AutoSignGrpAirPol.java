package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.taskservice.*;
import com.sinosoft.lis.tbgrp.LCGrpContSignBL;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;


/**
 * <p>
 * Title: lis
 * </p>
 * <p>
 * Description: 短期险团单自动签单自动运行
 * </p>
 * <p>
 * Copyright: Copyright (c) 2000
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author duanyh
 * @version 1.0
 */

public class AutoSignGrpAirPol extends TaskThread {
private static Logger logger = Logger.getLogger(AutoSignGrpAirPol.class);

	public AutoSignGrpAirPol() {
	}

	public boolean dealMain() {
		try {
			/* 业务处理逻辑 */
			GlobalInput tG = new GlobalInput();
			tG.ComCode = "86";
			tG.ManageCom = "86";
			tG.Operator = "Auto";
			logger.debug("AutoSignGrpAirPol开始短期险自动签单...");

			String sql = "select * from lcgrpcont  where appflag='0' and approveflag='9' and uwflag='9'"
					+ " and exists(select '1' from lcgrppol where grpcontno=lcgrpcont.grpcontno  and standbyflag2='AirPol')"
					+ " and exists (select '1' from ljtempfee where otherno=lcgrpcont.prtno and riskcode in(select code from ldcode where codetype='airriskcode'))";

			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			LCGrpContSet tLCGrpContSet = new LCGrpContSet();
			tLCGrpContSet = tLCGrpContDB.executeQuery(sql);
			for (int i = 1; i <= tLCGrpContSet.size(); i++) {
				LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
				tLCGrpContSchema = tLCGrpContSet.get(i);
				logger.debug("开始了："+tLCGrpContSchema.getPrtNo());
				LCGrpContSignBL tLCGrpContSignBL = new LCGrpContSignBL();
				VData tVData = new VData();
				tVData.addElement(tG);
				tVData.addElement(tLCGrpContSchema);
				if (!tLCGrpContSignBL.submitData(tVData, "")) {
					logger.debug("Error: "
							+ tLCGrpContSignBL.mErrors.getFirstError());
					//将保单挂起状态恢复
					tLCGrpContSchema.setState("0");
					MMap tMMap = new MMap();
					tMMap.put(tLCGrpContSchema, "UPDATE");
					VData tempVData = new VData();
					tempVData.add(tMMap);
					PubSubmit tPubSubmit = new PubSubmit();
					if (!tPubSubmit.submitData(tempVData)) {
						logger.debug("修改保单挂起状态失败！！！");
					}
					continue;
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		logger.debug("AutoSignGrpAirPol结束航意险自动签单...");
		return true;
	}

	public static void main(String[] args) {
		AutoSignGrpAirPol tAutoSignGrpAirPol = new AutoSignGrpAirPol();
		tAutoSignGrpAirPol.dealMain();
	}
}
