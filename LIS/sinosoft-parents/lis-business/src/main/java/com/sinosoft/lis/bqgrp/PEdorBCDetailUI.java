package com.sinosoft.lis.bqgrp;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * 被保险人资料变更功能类
 */

public class PEdorBCDetailUI implements BusinessService {
	private static Logger logger = Logger.getLogger(PEdorBCDetailUI.class);

	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private String mOperate;

	public PEdorBCDetailUI() {
	}

	@SuppressWarnings("unchecked")
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("Operate : " + cOperate);
		PEdorBCDetailBL tPEdorBCDetailBL = new PEdorBCDetailBL();
		logger.debug("   ----- UI BEGIN");
		if (tPEdorBCDetailBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorBCDetailBL.mErrors);
			return false;
		} else
			mResult = tPEdorBCDetailBL.getResult();

		mResult.clear();
		mResult.add("success");
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PEdorBCDetailUI aPEdorBCDetailUI = new PEdorBCDetailUI();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPBnfSet tLPBnfSet = new LPBnfSet();
		LPBnfSchema tLPBnfSchema = new LPBnfSchema();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setEdorNo("410000000000517");
		tLPEdorItemSchema.setEdorType("BC");
		tLPEdorItemSchema.setContNo("230110000000555");
		tLPEdorItemSchema.setInsuredNo("0000494300 ");
		tLPEdorItemSchema.setEdorAcceptNo("86000000000527");

		tLPBnfSchema.setBnfGrade("1");
		tLPBnfSchema.setBnfLot("1.0");
		tLPBnfSchema.setBnfType("0");
		tLPBnfSchema.setCustomerNo("");
		tLPBnfSchema.setEdorNo("410000000000517");
		tLPBnfSchema.setEdorType("BC");
		tLPBnfSchema.setIDNo("1415");
		tLPBnfSchema.setIDType("1");
		tLPBnfSchema.setName("cccc");
		tLPBnfSchema.setPolNo("210110000001136");
		tLPBnfSchema.setRelationToInsured("06");
		tLPBnfSet.add(tLPBnfSchema);

		tInputData.addElement(tLPBnfSet);
		tInputData.addElement(tLPEdorItemSchema);
		tInputData.addElement(tGlobalInput);
		aPEdorBCDetailUI.submitData(tInputData, "INSERT||PERSON");

		logger.debug("  ----- test ...");
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
