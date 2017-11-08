package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCRReportPrtSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCRReportPrtSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: BL层业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 * @date 2005-08-18
 */

public class GrpReportBL {
private static Logger logger = Logger.getLogger(GrpReportBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	private String mContNo;
	private String mPrtSeq;
	private String mCustomerNo;
	private String mMissionID;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private Reflections ref = new Reflections();
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();

	/** 业务处理相关变量 */
	private LCRReportPrtSchema mLCRReportPrtSchema = new LCRReportPrtSchema();
	private LCRReportPrtSet mLCRReportPrtSet = new LCRReportPrtSet();
	private LCRReportPrtSet mmLCRReportPrtSet = new LCRReportPrtSet();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();

	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("now in ContBL submit");
		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false)
			return false;
		logger.debug("---getInputData---");
		// 校验传入的数据

		if (this.checkData() == false)
			return false;
		logger.debug("---checkData---");
		// }

		// 根据业务逻辑对数据进行处理

		if (this.dealData() == false)
			return false;

		// 打印队列

		if (preparePrint() == false) {
			return false;
		}

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		// 数据提交、保存

		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mResult, "INSERT")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---commitData---");

		return true;
	}

	private boolean checkData() {
		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		return true;
	}

	private boolean dealData() {
		for (int i = 1; i <= mLCRReportPrtSet.size(); i++) {

			LCRReportPrtSchema tLCRReportPrtSchema = new LCRReportPrtSchema();
			tLCRReportPrtSchema.setSchema(mLCRReportPrtSet.get(i));
			tLCRReportPrtSchema.setOperator(mGlobalInput.Operator);
			tLCRReportPrtSchema.setMakeDate(PubFun.getCurrentDate());
			// tLCRReportPrtSchema.setCustomerNo(mCustomerNo);
			tLCRReportPrtSchema.setMakeTime(PubFun.getCurrentTime());

			tLCRReportPrtSchema.setModifyDate(PubFun.getCurrentDate());
			tLCRReportPrtSchema.setModifyTime(PubFun.getCurrentTime());

			mmLCRReportPrtSet.add(tLCRReportPrtSchema);

		}
		if (mmLCRReportPrtSet != null) {
			map.put(mmLCRReportPrtSet, "INSERT");
		}

		return true;
	}

	private boolean getInputData() {
		try {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));

			this.mLCRReportPrtSet = (LCRReportPrtSet) mInputData
					.getObjectByObjectName("LCRReportPrtSet", 0);

			this.mLCRReportPrtSchema = (LCRReportPrtSchema) mInputData
					.getObjectByObjectName("LCRReportPrtSchema", 0);

			if (mGlobalInput == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "TbWorkFlowBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "前台传输全局公共数据失败!";
				this.mErrors.addOneError(tError);

				return false;
			}

			mContNo = mLCRReportPrtSchema.getContNo();

			if (mContNo == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCContDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "UWRReportAfterInitService";
				tError.functionName = "getInputData";
				tError.errorMessage = "前台传输业务数据中ContNo失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mCustomerNo = mLCRReportPrtSchema.getCustomerNo();
			if (mCustomerNo == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCContDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "UWRReportAfterInitService";
				tError.functionName = "getInputData";
				tError.errorMessage = "前台传输业务数据中CustomerNo失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "checkData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;

		}
		return true;
	}

	private boolean prepareOutputData() {
		try {
			map.put(mLOPRTManagerSchema, "INSERT");
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWUserBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	// add by haopan -2006-11-21
	private boolean preparePrint() {

		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("URGEInterval");

		if (tLDSysVarDB.getInfo() == false) {
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "prepareURGE";
			tError.errorMessage = "没有描述催发间隔!";
			this.mErrors.addOneError(tError);
			return false;
		}
		FDate tFDate = new FDate();
		int tInterval = Integer.parseInt(tLDSysVarDB.getSysVarValue());
		logger.debug(tInterval);

		Date tDate = PubFun.calDate(tFDate.getDate(PubFun.getCurrentDate()),
				tInterval, "D", null);
		logger.debug(tDate); // 取预计催办日期
		// 准备打印管理表数据
		mLOPRTManagerSchema.setPrtSeq(mLCRReportPrtSchema.getPrtSeq());
		mLOPRTManagerSchema.setOtherNo(mContNo);
		mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRP_MEET); // 生调
		mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
		mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
		mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setPatchFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		mLOPRTManagerSchema.setStandbyFlag1(mCustomerNo); // 体检人编码
		mLOPRTManagerSchema.setStandbyFlag2(mLCContSchema.getPrtNo()); // 被保险人编码
		// mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
		mLOPRTManagerSchema.setOldPrtSeq(mLCRReportPrtSchema.getPrtSeq());
		mLOPRTManagerSchema.setForMakeDate(tDate);

		return true;
	}
	// add end

}
