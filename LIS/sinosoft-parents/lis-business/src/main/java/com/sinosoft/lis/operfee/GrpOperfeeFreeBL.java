package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCContHangUpStateBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WL
 * @version 1.0
 */

public class GrpOperfeeFreeBL {
private static Logger logger = Logger.getLogger(GrpOperfeeFreeBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 数据操作字符串 */
	private String mOperate;
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private MMap map = new MMap();
	private String mContNo = "";
	private String mInsuredNo = "";
	private String mPolNo = "";
	private String mHangUpType = "";
	private String mHangUpNo = "";
	private String mOperator2 = "";
	private GlobalInput tGI = new GlobalInput();

	// 应收个人交费表
	private LCContHangUpStateSet mLCContHangUpStateSet = new LCContHangUpStateSet();
	private LCContHangUpStateSet mLCContHangUpStateUpdateSet = new LCContHangUpStateSet();
	private TransferData mTransferData = new TransferData();

	// 业务处理相关变量
	public GrpOperfeeFreeBL() {
	}

	public static void main(String[] args) {

	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;

		if (!getInputData(cInputData)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		GrpOperfeeFreeBLS tGrpOperfeeFreeBLS = new GrpOperfeeFreeBLS();
		tGrpOperfeeFreeBLS.submitData(mInputData, cOperate);

		// 如果有需要处理的错误，则返回
		if (tGrpOperfeeFreeBLS.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tGrpOperfeeFreeBLS.mErrors);
		}

		// 进行数据提交
		// PubSubmit tPubSubmit = new PubSubmit();
		// logger.debug("UPDATE");
		// if (!tPubSubmit.submitData(mInputData, cOperate))
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tPubSubmit.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "GrpOperfeeFreeBL";
		// tError.functionName = "PubSubmit.submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		mInputData = null;
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		boolean tReturn = false;
		if (this.mOperate.equals("UPDATE")) { // 查询应收个人表:没有该纪录，则添加一条；有则更新这条纪录
			LCContHangUpStateSchema tLCContHangUpStateSchema = new LCContHangUpStateSchema();
			LCContHangUpStateBL tLCContHangUpStateBL;
			int iMax = mLCContHangUpStateSet.size();
			logger.debug("count=" + iMax);
			for (int i = 1; i <= iMax; i++) {
				tLCContHangUpStateBL = new LCContHangUpStateBL();
				tLCContHangUpStateSchema.setSchema(mLCContHangUpStateSet.get(i)
						.getSchema());
				tLCContHangUpStateBL.setSchema(mLCContHangUpStateSet.get(i)
						.getSchema());
				mContNo = tLCContHangUpStateSchema.getContNo();
				mInsuredNo = tLCContHangUpStateSchema.getInsuredNo();
				mPolNo = tLCContHangUpStateSchema.getPolNo();
				mHangUpType = tLCContHangUpStateSchema.getHangUpType();
				mHangUpNo = tLCContHangUpStateSchema.getHangUpNo();
				mOperator2 = tGI.Operator;

				tLCContHangUpStateBL.setContNo(mContNo);
				tLCContHangUpStateBL.setInsuredNo(mInsuredNo);
				tLCContHangUpStateBL.setPolNo(mPolNo);
				tLCContHangUpStateBL.setHangUpType(mHangUpType);
				tLCContHangUpStateBL.setHangUpNo(mHangUpNo);
				tLCContHangUpStateBL.setNBFlag("0");
				tLCContHangUpStateBL.setPosFlag("0");
				tLCContHangUpStateBL.setClaimFlag("0");
				tLCContHangUpStateBL.setAgentFlag("0");
				tLCContHangUpStateBL.setRNFlag("0");
				tLCContHangUpStateBL.setOperator(mOperator2);
				tLCContHangUpStateBL.setMakeDate(CurrentDate);
				tLCContHangUpStateBL.setMakeTime(CurrentTime);
				tLCContHangUpStateBL.setModifyDate(CurrentDate);
				tLCContHangUpStateBL.setModifyTime(CurrentTime);
				mLCContHangUpStateUpdateSet.add(tLCContHangUpStateBL);

				// String tLCContHangUpState = " update lCContHangUpState set NBFlag=
				// '0',PosFlag = '0',ClaimFlag = '0',AgentFlag='0',RNFlag='0', "
				// + " ModifyDate = '" + CurrentDate +
				// "',ModifyTime = '" + CurrentTime +
				// "' where contno = '" +
				// mContNo + "' ";
				// map.put(tLCContHangUpState, "UPDATE");
				// tLCContHangUpStateSchema.setContNo(mContNo);
				// tLCContHangUpStateSchema.setInsuredNo(tLCContHangUpStateSchema.getInsuredNo());
				// tLCContHangUpStateSchema.setPolNo(tLCContHangUpStateSchema.getPolNo());
				// tLCContHangUpStateSchema.setHangUpType(tLCContHangUpStateSchema.getHangUpType());
				// tLCContHangUpStateSchema.setHangUpNo(tLCContHangUpStateSchema.getHangUpNo());
				// tLCContHangUpStateSchema.setOperator(tLCContHangUpStateSchema.getOperator());
				// tLCContHangUpStateSchema.setMakeDate(tLCContHangUpStateSchema.getMakeDate());
				// tLCContHangUpStateSchema.setMakeTime(tLCContHangUpStateSchema.getMakeTime());
				// tLCContHangUpStateSchema.setNBFlag("0");
				// tLCContHangUpStateSchema.setPosFlag("0");
				// tLCContHangUpStateSchema.setClaimFlag("0");
				// tLCContHangUpStateSchema.setAgentFlag("0");
				// tLCContHangUpStateSchema.setRNFlag("0");
				// tLCContHangUpStateSchema.setModifyDate(CurrentDate);
				// tLCContHangUpStateSchema.setModifyTime(CurrentTime);
				// map.put(tLCContHangUpStateSchema,"UPDATE");
				tReturn = true;

			}
		}
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {
		// 应收个人纪录集合
		// mTransferData = (TransferData) mInputData.getObjectByObjectName(
		// "TransferData", 0);
		// mContNo = (String) mTransferData.getValueByName("ContNo");
		// mHangUpNO = (String) mTransferData.getValueByName("HangUpNo");
		// String tSumCount = (String) mTransferData.getValueByName("SumCount");
		// 应收个人纪录集合
		mLCContHangUpStateSet.set((LCContHangUpStateSet) mInputData
				.getObjectByObjectName("LCContHangUpStateSet", 0));
		tGI = ((GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0));

		if (mLCContHangUpStateSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpOperfeeFreeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tGI == null || mTransferData == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpDueFeeMultiBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		mInputData = new VData();
		try {
			// 注意：类型一致，但是序号不同.0,1
			// mInputData.add(mLJSPayPersonInSertSet); //添加应收个人交费表
			mInputData.add(mLCContHangUpStateUpdateSet); // 更新保单挂起状态表
			logger.debug("prepareOutputData:");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpOperfeeFreeBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
}
