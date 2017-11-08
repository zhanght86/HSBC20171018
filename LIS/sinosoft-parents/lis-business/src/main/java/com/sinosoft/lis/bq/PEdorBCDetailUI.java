package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * 被保险人资料变更功能类
 */

public class PEdorBCDetailUI {
private static Logger logger = Logger.getLogger(PEdorBCDetailUI.class);
	public CErrors mErrors = new CErrors();
	private VData mInputData = new VData();
	private VData mResult = new VData();
	private String mOperate;

	public PEdorBCDetailUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("Operate : " + cOperate);
		PEdorBCDetailBLF tPEdorBCDetailBLF = new PEdorBCDetailBLF();
		logger.debug("   ----- UI BEGIN");
		if (tPEdorBCDetailBLF.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			// this.mErrors.copyAllErrors(tPEdorBCDetailBLF.mErrors);
			this.mErrors.copyAllErrors(tPEdorBCDetailBLF.getErrors());
			return false;
		} else
			mResult = tPEdorBCDetailBLF.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PEdorBCDetailUI aPEdorBCDetailUI = new PEdorBCDetailUI();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPBnfSet tLPBnfSet = new LPBnfSet();
		LPBnfSchema tLPBnfSchema = new LPBnfSchema();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		tLPEdorItemSchema.setPolNo("210110000001044");
		tLPEdorItemSchema.setEdorNo("410000000000863");
		tLPEdorItemSchema.setEdorType("BC");
		tLPEdorItemSchema.setContNo("230110000000501");
		tLPEdorItemSchema.setInsuredNo("0000495500 ");
		tLPEdorItemSchema.setEdorAcceptNo("86000000000966");

		tLPBnfSchema.setBnfGrade("1");
		tLPBnfSchema.setBnfLot("1");
		tLPBnfSchema.setBnfType("0");
		tLPBnfSchema.setCustomerNo("");
		tLPBnfSchema.setEdorNo("410000000000863");
		tLPBnfSchema.setEdorType("BC");
		tLPBnfSchema.setIDNo("1415");
		tLPBnfSchema.setIDType("1");
		tLPBnfSchema.setName("cccc");
		tLPBnfSchema.setPolNo("210110000001044");
		tLPBnfSchema.setRelationToInsured("06");
		tLPBnfSet.add(tLPBnfSchema);

		tInputData.addElement(tLPBnfSet);
		tInputData.addElement(tLPEdorItemSchema);
		tInputData.addElement(tGlobalInput);
		// aPEdorBCDetailUI.submitData(tInputData,"INSERT||PERSON");
		aPEdorBCDetailUI.submitData(tInputData, "INSERT||MAIN");
		logger.debug("  ----- test ...");
	}
}
