package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.operfee.RNHangUp;
import com.sinosoft.lis.operfee.RnDealBLF;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 支票退票
 * <p>
 * Description:
 * </p>
 * 支票退票
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * sinosoft
 * <p>
 * Company:
 * </p>
 * sinosoft
 * 
 * @author gaoht
 * @version 1.0
 */
public class FinFeeNotSureBL {
private static Logger logger = Logger.getLogger(FinFeeNotSureBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private GlobalInput mGlobalInput = new GlobalInput();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private VData mResult = new VData();
	private String mOperate;
	private String mManageCom;
	/** 暂交费表 */
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();

	/** 暂交费关联表 */
	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
	private LJTempFeeClassSet OutLJTempFeeClassSet = new LJTempFeeClassSet();

	private LCContHangUpStateSet mLCContHangUpStateSet = new LCContHangUpStateSet();

	public FinFeeNotSureBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("-------------------After GetInput----------------");

		logger.debug("---FinFeeSureBL checkData---");
		// 数据操作业务处理
		if (!dealData()) {
			logger.debug("支票退票失败！");
			return false;
		}
		MMap map = new MMap();

		// 退票后增加抽档撤销重新生成收据号
		map.put(mLCContHangUpStateSet, "DELETE");
		map.put(mLJTempFeeSet, "UPDATE");
		map.put(OutLJTempFeeClassSet, "UPDATE");
		VData tSubmintData = new VData();
		tSubmintData.add(map);
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(tSubmintData, "")) {
			logger.debug("支票退票失败！");
			return false;
		}
		for (int i = 1; i <= mLJTempFeeSet.size(); i++) {
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			tLJTempFeeSchema = mLJTempFeeSet.get(i);
			if (tLJTempFeeSchema.getTempFeeType().equals("2")) {
				String tContNo = tLJTempFeeSchema.getOtherNo();
				IndiDueFeeCancelBL IndiDueFeeCancelBL1 = new IndiDueFeeCancelBL();
				LCContSchema tLCContSchema = new LCContSchema();
				tLCContSchema.setContNo(tContNo);
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("SubmitFlag", "NotSure");
				VData tv = new VData();
				tv.add(mGlobalInput);
				tv.add(tLCContSchema);
				tv.add(tTransferData);
				IndiDueFeeCancelBL1.submitData(tv, "");

				tTransferData = new TransferData();
				tTransferData.setNameAndValue("Contno", tContNo);
				tv = new VData();
				RnDealBLF tRnDealBLF = new RnDealBLF();
				tv.add(tTransferData);
				tv.add(mGlobalInput);
				logger.debug("准备好了数据");
				tRnDealBLF.submitData(tv, "");
			}
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperate = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;

		mLJTempFeeClassSet.set((LJTempFeeClassSet) cInputData
				.getObjectByObjectName("LJTempFeeClassSet", 0));
		// mLJTempFeeClassSet.set((LJTempFeeClassSet)cInputData.getObjectByObjectName("LJTempFeeClassSet",0));

		int n = mLJTempFeeClassSet.size();
		int flag = 0; // 怕判断是不是所有保单都失败
		int j = 0; // 符合条件保单个数

		if (n > 0) {
			flag = 1;
		} else {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "FinFeeSureBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= n; i++) {
			if (mLJTempFeeClassSet.get(i).getEnterAccDate() != null) {
				CError tError = new CError();
				tError.moduleName = "FinFeeSureBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "该支票已经到帐!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	private boolean dealData() {
		int SetNo = mLJTempFeeClassSet.size();
		for (int i = 1; i <= SetNo; i++) {
			LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
			tLJTempFeeClassSchema = mLJTempFeeClassSet.get(i);
			String TempFeeNo = tLJTempFeeClassSchema.getTempFeeNo();
			String ChNo = tLJTempFeeClassSchema.getChequeNo();
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			String Sql = "select * from ljtempfee where tempfeeno = '"
					+ "?tempfeeno?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(Sql);
			sqlbv.put("tempfeeno", TempFeeNo);
			mLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv);

			for (int m = 1; m <= mLJTempFeeSet.size(); m++) {
				mLJTempFeeSet.get(m).setConfMakeDate(CurrentDate);
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema = mLJTempFeeSet.get(m);
				if (tLJTempFeeSchema.getTempFeeType().equals("2")) {
					String tContNo = tLJTempFeeSchema.getOtherNo();
					RNHangUp tRNHangUp = new RNHangUp(mGlobalInput);
					LCContHangUpStateSchema tLCContHangUpStateSchema = new LCContHangUpStateSchema();
					tLCContHangUpStateSchema = tRNHangUp
							.undoHangUp(tLJTempFeeClassSchema.getOtherNo());
					if (tLCContHangUpStateSchema != null)
						mLCContHangUpStateSet.add(tLCContHangUpStateSchema);

				}
			}

			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
			tLJTempFeeClassDB.setSchema(tLJTempFeeClassSchema);
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.query();
			for (int n = 1; n <= tLJTempFeeClassSet.size(); n++) {
				tLJTempFeeClassSchema = new LJTempFeeClassSchema();
				tLJTempFeeClassSchema = tLJTempFeeClassSet.get(n);
				tLJTempFeeClassSchema.setEnterAccDate("3000-01-01");
				tLJTempFeeClassSchema.setConfMakeDate(CurrentDate);
				OutLJTempFeeClassSet.add(tLJTempFeeClassSchema);
			}
		}

		return true;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		tLJTempFeeClassSchema.setTempFeeNo("3100000521399");
		tLJTempFeeClassSchema.setPayMode("3");
		tLJTempFeeClassSchema.setEnterAccDate("");
		tLJTempFeeClassSchema.setChequeNo("1234324780000");
		tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		VData tVData = new VData();
		tVData.add(tLJTempFeeClassSet);
		tVData.add(tG);
		FinFeeNotSureBL tFinFeeNotSureBL = new FinFeeNotSureBL();
		tFinFeeNotSureBL.submitData(tVData, "UPDATE");
	}
}
