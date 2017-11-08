

package com.sinosoft.lis.reinsure.faculreinsure;

import com.sinosoft.lis.db.RIDutyStateDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIDutyStateSchema;
import com.sinosoft.lis.schema.RIGrpStateSchema;
import com.sinosoft.lis.vschema.RIClaimStateSet;
import com.sinosoft.lis.vschema.RIDutyStateSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RIIndTempConclusionBL implements BusinessService {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	public VData mResult = new VData();
	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private TransferData mTransferData = new TransferData();
	private RIDutyStateSet mRIDutyStateSet;
	private RIClaimStateSet mRIClaimStateSet;

	/** 数据操作字符串 */
	private String strOperate;
	private String mTempUWConclusion;
	private String mLCTempUWConclusion; // 核赔结论
	private RIGrpStateSchema mRIGrpStateSchema = new RIGrpStateSchema();
	private String mSerialNo;
	private MMap mMap = new MMap();

	private PubSubmit tPubSubmit = new PubSubmit();

	public RIIndTempConclusionBL() {
	}

	/**
	 * 提交数据处理方法
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		strOperate = cOperate;
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();
		globalInput.ComCode = "8611";
		globalInput.Operator = "001";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpContNo", "1300111588");
		tTransferData.setNameAndValue("ContPlanCode", "");
		tTransferData.setNameAndValue("RiskCode", "");
		tTransferData.setNameAndValue("DutyCode", "");
		tTransferData.setNameAndValue("InsuredNo", "");
		tTransferData.setNameAndValue("InsuredName", "");
		tTransferData.setNameAndValue("TempUWConclusion", "");
		tTransferData.setNameAndValue("TempUWConclusionConf", "");
		tTransferData.setNameAndValue("UWFlag", "2");
		tTransferData.setNameAndValue("DeleteType", "02");
		//
		RIDutyStateSchema tRIDutyStateSchema = new RIDutyStateSchema();
		RIDutyStateDB tRIDutyStateDB = new RIDutyStateDB();
		tRIDutyStateDB.setState("04");
		RIDutyStateSet mRIDutyStateSet = tRIDutyStateDB.query();
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(globalInput);
		tVData.add(mRIDutyStateSet);
		// 数据传输
		RIIndTempConclusionBL tRIIndTempConclusionBL = new RIIndTempConclusionBL();
		// tRIIndTempConclusionBL.submitData(tVData, "03");

		try {
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(mMap);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean dealData() {
		mTempUWConclusion = (String) mTransferData
				.getValueByName("TempUWConclusion");
		mLCTempUWConclusion = (String) mTransferData
				.getValueByName("LCTempUWConclusion");
		String tOpeData = (String) mTransferData.getValueByName("OpeData");
		;
		String tUWFlag = (String) mTransferData.getValueByName("UWFlag");
		String tTempUWConclusionConf = (String) mTransferData
				.getValueByName("TempUWConclusionConf");
		System.out.println(" BL  tTempUWConclusionConf : "
				+ tTempUWConclusionConf);
		String tDeleteType = (String) mTransferData
				.getValueByName("DeleteType");
		// strOperate：01－全部查询结果；02－选中查询结果
		if (strOperate.equals("01")) {
			// 全部查询结果
		} else if (strOperate.equals("02")) {
			// 对选中结果进行操作
			if (tUWFlag.equals("1") || tUWFlag.equals("0")) { // 核保临分结论 ；再保临分结论
				for (int i = 1; i <= mRIDutyStateSet.size(); i++) {
					mRIDutyStateSet.get(i).setOperator(globalInput.Operator);
					mRIDutyStateSet.get(i).setMakeDate(PubFun.getCurrentDate());
					mRIDutyStateSet.get(i).setMakeTime(PubFun.getCurrentTime());
				}
			} else if (tUWFlag.equals("2")) {// 再保核赔结论
				for (int i = 1; i <= mRIClaimStateSet.size(); i++) {
					mRIClaimStateSet.get(i).setOperator(globalInput.Operator);
					mRIClaimStateSet.get(i)
							.setMakeDate(PubFun.getCurrentDate());
					mRIClaimStateSet.get(i)
							.setMakeTime(PubFun.getCurrentTime());
				}
			}
		} else if (strOperate.equals("03")) { // 取消临分结论
			if (tDeleteType.equals("01")) {
				for (int i = 1; i <= mRIDutyStateSet.size(); i++) {
					mRIDutyStateSet.get(i).setOperator(globalInput.Operator);
					mRIDutyStateSet.get(i).setMakeDate(PubFun.getCurrentDate());
					mRIDutyStateSet.get(i).setMakeTime(PubFun.getCurrentTime());
				}
			} else if (tDeleteType.equals("02")) {
				RIDutyStateDB tRIDutyStateDB = new RIDutyStateDB();
				String tSql = " select * from ridutystate a where exists (select * from ripoldutyindview b where trim(b.proposalno)=trim(a.ProposalNo) and trim(b.contno)='"
						+ tOpeData
						+ "' and trim(a.state)='00' and trim(a.UWConclusion)='02')  ";
				mRIDutyStateSet = tRIDutyStateDB.executeQuery(tSql);
				if (tRIDutyStateDB.mErrors.needDealError()) {
					buildError("update", "保存临分核保结论时出现错误!");
					return false;
				}
				for (int i = 1; i <= mRIDutyStateSet.size(); i++) {
					mRIDutyStateSet.get(i).setState("02");
				}
			} else if (tDeleteType.equals("03")) {
				RIDutyStateDB tRIDutyStateDB = new RIDutyStateDB();
				String tSql = " select * from ridutystate a where exists (select * from ripoldutyindview b where trim(b.proposalno)=trim(a.ProposalNo) and trim(b.contno)='"
						+ tOpeData
						+ "' and trim(a.state)='04' and trim(a.UWConclusion)='02')  ";
				mRIDutyStateSet = tRIDutyStateDB.executeQuery(tSql);
				if (tRIDutyStateDB.mErrors.needDealError()) {
					buildError("update", "保存临分核保结论时出现错误!");
					return false;
				}
				for (int i = 1; i <= mRIDutyStateSet.size(); i++) {
					mRIDutyStateSet.get(i).setState("02");
				}
			}
		} else if (strOperate.equals("04")) {
			// 处理审核完毕
			System.out.println(" 处理审核完毕 ");
			String sql = "update RIGrpState set state='04' where SerialNo='"
					+ mSerialNo + "'";
			mMap.put(sql, "UPDATE");
		}
		// if (mTempUWConclusion.trim().equals("99")) { // 如果是取消
		// if (tUWFlag.equals("1")) { // 核保临分意见
		// mMap.put(mRIDutyStateSet, "DELETE");
		// } else {// 再保临分结论
		// for (int i = 1; i <= mRIDutyStateSet.size(); i++) {
		// mRIDutyStateSet.get(i).setOperator(globalInput.Operator);
		// mRIDutyStateSet.get(i).setMakeDate(PubFun.getCurrentDate());
		// mRIDutyStateSet.get(i).setMakeTime(PubFun.getCurrentTime());
		// mRIDutyStateSet.get(i).setState("02");
		// mRIDutyStateSet.get(i).setUWConclusion("02");
		// mMap.put(mRIDutyStateSet, "UPDATE");
		// }
		// }
		// } else if (mLCTempUWConclusion != null
		// && mLCTempUWConclusion.trim().equals("99")) { // 核赔结论取消
		// if (tUWFlag.equals("2") || tUWFlag.equals("0")) {
		// mMap.put(mRIClaimStateSet, "DELETE");
		// }
		// } else { // 除'99'之外,均采用"DELETE&INSERT"
		// if (tUWFlag.equals("1") || tUWFlag.equals("0")) {
		// mMap.put(mRIDutyStateSet, "DELETE&INSERT");
		// } else {
		// mMap.put(mRIClaimStateSet, "DELETE&INSERT");
		// }
		// }
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("update", "保存临分核保结论时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mInputData = cInputData;
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mSerialNo = (String) mTransferData.getValueByName("SerialNo");
		if (!strOperate.equals("01")) { // 对选中结果下结论
			this.mRIDutyStateSet = (RIDutyStateSet) cInputData
					.getObjectByObjectName("RIDutyStateSet", 0);
			this.mRIClaimStateSet = (RIClaimStateSet) cInputData
					.getObjectByObjectName("RIClaimStateSet", 0);
		}
		return true;
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIIndTempConclusionBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}
}
