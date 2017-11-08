

/**
 * <p>Title: PDRiskDutyFactor</p>
 * <p>Description: 责任录入要素定义</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-13
 */

package com.sinosoft.productdef;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CommonBase;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PDRiskDutyFactorBL {
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

	public PDRiskDutyFactorBL() {
	}

	private LDCodeSet ldCodeSet;

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		getInputData(cInputData);
		if (!dealData(cInputData)) {
			return false;
		}
		if(submitFlag.equals("ldcode")){
			submit();
		}
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

	private boolean dealData(VData inputData) {
		if (submitFlag.equals("ldcode")) {
			return dealLDCode(ldCodeSet);
		} else {
			return dealRiskDutyFactor(inputData);
		}
	}

	private void getInputData(VData inputData) {
		TransferData transferData = (TransferData) inputData
				.getObjectByObjectName("TransferData", 0);
		submitFlag = (String) transferData.getValueByName("submitFlag");
		ldCodeSet = (LDCodeSet) transferData.getValueByName("LDCodeSet");

	}

	private boolean dealRiskDutyFactor(VData cInputData) {
		// TODO Auto-generated method stub
		CommonBase commonBase = new CommonBase();
		boolean result = commonBase.submitData(cInputData, mOperate);
		if (!result) {
			this.mErrors.addOneError("PDRiskDutyFactorBL.submitData处理失败，"
					+ commonBase.mErrors.getFirstError());
			return false;
		}
		return true;
	}

	private boolean dealLDCode(LDCodeSet ldCodeSet) {
		if (ldCodeSet.size() > 0) {
			LDCodeDB tLDCodeDB = new LDCodeDB();
			if ("save".equals(mOperate)) {
				for (int i = 1; i <= ldCodeSet.size(); i++) {
					LDCodeSchema tLDCodeSchema = ldCodeSet.get(i);
					tLDCodeDB.setCodeType(tLDCodeSchema.getCodeType());
					tLDCodeDB.setCode(tLDCodeSchema.getCode());
					if (tLDCodeDB.getInfo()) {
						this.mErrors.addOneError("LDCode表有重复记录为:CodeType="
								+ tLDCodeDB.getCodeType() + "  Code="
								+ tLDCodeDB.getCode() + "  请重新添加");
						return false;
					}
					map.put(tLDCodeSchema, "INSERT");
				}
			} else if ("update".equals(mOperate)) {
				String sql = "delete from ldcode where codetype='"
						+ ldCodeSet.get(1).getCodeType() + "' and code='"
						+ ldCodeSet.get(1).getCode() + "'";
				map.put(sql, "DELETE");
				for (int i = 1; i <= ldCodeSet.size(); i++) {
					LDCodeSchema tLDCodeSchema = ldCodeSet.get(i);
					map.put(tLDCodeSchema, "INSERT");
				}
			} else {
				String sql = "delete from ldcode where codetype='"
						+ ldCodeSet.get(1).getCodeType() + "' and code='"
						+ ldCodeSet.get(1).getCode() + "'";
				map.put(sql, "DELETE");
			}
		}
		return true;
	}

	private boolean check() {
		return true;
	}

	public static void main(String[] args) {
	}
}
