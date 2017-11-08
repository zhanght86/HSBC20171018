package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LXbalanceSubSchema;
import com.sinosoft.lis.vschema.LXbalanceSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 卡单结算
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Gaoht
 * @version 1.0
 */
public class FinFeeCardBalanceBLF {
private static Logger logger = Logger.getLogger(FinFeeCardBalanceBLF.class);
	public CErrors mErrors = new CErrors();
	private String mNo = "";

	public FinFeeCardBalanceBLF() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		FinFeeCardBalanceBL tFinFeeCardBalanceBL = new FinFeeCardBalanceBL();
		if (!tFinFeeCardBalanceBL.submitData(cInputData, "")) {
			CError.buildErr(this, tFinFeeCardBalanceBL.mErrors.getFirstError());
			return false;
		} else {
			mNo = tFinFeeCardBalanceBL.getBalanceNo();
			MMap tMap = new MMap();
			tMap.add(tFinFeeCardBalanceBL.getMap());
			VData tVData = new VData();
			tVData.add(tMap);
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(tVData, "")) {
				// @@错误处理
				CError.buildErr(this, "提交数据失败");
				return false;
			} else {
				if (!tFinFeeCardBalanceBL.CertifyTakeBack()) {
					CError.buildErr(this, tFinFeeCardBalanceBL.mErrors
							.getFirstError());
					return false;
				}
			}
		}
		return true;
	}

	public String getBalanceNo() {
		return mNo;
	}

	public static void main(String[] args) {
		FinFeeCardBalanceBLF finfeecardbalanceblf = new FinFeeCardBalanceBLF();
		LXbalanceSubSchema tLXbalanceSubSchema = new LXbalanceSubSchema();
		LXbalanceSubSet tLXbalanceSubSet = new LXbalanceSubSet();
		GlobalInput tGI = new GlobalInput();

		tLXbalanceSubSchema = new LXbalanceSubSchema();
		tLXbalanceSubSchema.setCardType("UA065");
		tLXbalanceSubSchema.setStartNo("20000000000011");
		tLXbalanceSubSchema.setEndNo("20000000000012");
		tLXbalanceSubSchema.setCount(2);
		tLXbalanceSubSchema.setStandbyFlag1("");
		tLXbalanceSubSchema.setStandbyFlag2("00");
		tLXbalanceSubSchema.setUnitPrice("1000");
		tLXbalanceSubSchema.setManageCom("862100");
		tLXbalanceSubSchema.setAgentCode("00000056");
		tLXbalanceSubSchema.setAgentCom("");
		tLXbalanceSubSet.add(tLXbalanceSubSchema);

		tLXbalanceSubSchema = new LXbalanceSubSchema();
		tLXbalanceSubSchema.setCardType("UA065");
		tLXbalanceSubSchema.setStartNo("20000000000014");
		tLXbalanceSubSchema.setEndNo("20000000000015");
		tLXbalanceSubSchema.setCount(2);
		tLXbalanceSubSchema.setStandbyFlag1("");
		tLXbalanceSubSchema.setStandbyFlag2("00");
		tLXbalanceSubSchema.setUnitPrice("1000");
		tLXbalanceSubSchema.setManageCom("862100");
		tLXbalanceSubSchema.setAgentCode("00000056");
		tLXbalanceSubSchema.setAgentCom("");
		tLXbalanceSubSet.add(tLXbalanceSubSchema);

		tGI.ComCode = "862100";
		tGI.ManageCom = "86210000";
		tGI.Operator = "GaoHt";
		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tLXbalanceSubSet);
		if (!finfeecardbalanceblf.submitData(tVData, "")) {
			logger.debug(finfeecardbalanceblf.mErrors.getError(0).errorMessage);
		}

	}
}
