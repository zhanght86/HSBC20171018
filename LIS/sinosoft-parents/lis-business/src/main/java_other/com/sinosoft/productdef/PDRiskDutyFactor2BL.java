

package com.sinosoft.productdef;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.PD_LMRiskDutyFactorSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CommonBase;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PDRiskDutyFactor2BL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private MMap map = new MMap();

	private String submitFlag;

	public PDRiskDutyFactor2BL() {
	}

	private PD_LMRiskDutyFactorSet mPD_LMRiskDutyFactorSet;

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if(cOperate.equals("1")){
			mOperate = "INSERT";
		}else if(cOperate.equals("2")){
			mOperate = "UPDATE";
		}else if(cOperate.equals("3")){
			mOperate = "DELETE";
		}
		getInputData(cInputData);
		if (!dealData()) {
			return false;
		}
		submit();
		return true;
	}

	private boolean submit() {
		VData data = new VData();
		data.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(data, "")) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}
		return true;
	}

	private boolean dealData() {

		for (int i = 1; i <= mPD_LMRiskDutyFactorSet.size(); i++) {
			mPD_LMRiskDutyFactorSet.get(i).setMakeDate(PubFun.getCurrentDate());
			mPD_LMRiskDutyFactorSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mPD_LMRiskDutyFactorSet.get(i).setMakeTime(PubFun.getCurrentTime());
			mPD_LMRiskDutyFactorSet.get(i).setModifyTime(PubFun.getCurrentTime());
		}
		map.put(mPD_LMRiskDutyFactorSet, "DELETE&INSERT");
		return true;
	}

	private void getInputData(VData inputData) {
		TransferData transferData = (TransferData) inputData
				.getObjectByObjectName("TransferData", 0);
		// submitFlag = (String) transferData.getValueByName("submitFlag");
		mPD_LMRiskDutyFactorSet = (PD_LMRiskDutyFactorSet) inputData
				.getObjectByObjectName("PD_LMRiskDutyFactorSet", 0);

	}

	private boolean check() {
		return true;
	}

	public static void main(String[] args) {
	}
}
