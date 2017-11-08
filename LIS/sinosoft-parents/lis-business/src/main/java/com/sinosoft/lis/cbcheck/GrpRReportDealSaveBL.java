package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpRReportDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpRReportSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpRReportSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
 * @author guomy
 * @version 1.0
 * @date 2005-7-20 11:27
 */

public class GrpRReportDealSaveBL {
private static Logger logger = Logger.getLogger(GrpRReportDealSaveBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	private String mContNo;

	private String mGrpContNo = "";
	private String mPrtSeq = "";
	private String mAgentCode = "";
	private String mAgentName = "";
	private String mManageCom = "";

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	// private Reflections ref = new Reflections();
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();

	/** 业务处理相关变量 */
	private LCGrpRReportSchema mLCGrpRReportSchema = new LCGrpRReportSchema();
	private LCGrpRReportSet mLCGrpRReportSet = new LCGrpRReportSet();

	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();

	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private LAAgentSet mLAAgentSet = new LAAgentSet();
	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	// private LCGrpRReportSet mLCGrpRReportSet = new LCGrpRReportSet();
	// private LCGrpRReportSet mmLCGrpRReportSet = new LCGrpRReportSet();
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

		// 根据业务逻辑对数据进行处理

		if (this.dealData() == false)
			return false;

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		// 数据提交、保存

		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpRReportDealSaveBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---commitData---");

		return true;
	}

	private boolean checkData() {

		return true;
	}

	private boolean dealData() {

		// 获得团单信息
		mGrpContNo = mLCGrpRReportSchema.getGrpContNo();
		mPrtSeq = mLCGrpRReportSchema.getPrtSeq();

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);
		mLCGrpContSet = tLCGrpContDB.query();

		if (mLCGrpContSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpRReportDealSaveBL";
			tError.functionName = "dealData";
			tError.errorMessage = "团体投保单查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			mLCGrpContSchema = mLCGrpContSet.get(1);
			mAgentCode = mLCGrpContSchema.getAgentCode();// 获得代理人编码
		}

		logger.debug("---getAgentCode() successed ! AgentCode = "
				+ mAgentCode);

		// 获得代理人姓名和管理机构
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		mLAAgentSet = tLAAgentDB.query();

		if (mLAAgentSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpRReportDealSaveBL";
			tError.functionName = "dealData";
			tError.errorMessage = "代理人信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			mLAAgentSchema = mLAAgentSet.get(1);
			mAgentName = mLAAgentSchema.getName();// 获得代理人姓名
			mManageCom = mLAAgentSchema.getManageCom();// 获得管理机构
		}
		logger.debug("---ggetAgentName() successed ! AgentName = "
				+ mAgentName);
		logger.debug("---getManageCom() successed ! ManageCom = "
				+ mManageCom);

		// 查询团单生调总表
		LCGrpRReportDB tLCGrpRReportDB = new LCGrpRReportDB();
		tLCGrpRReportDB.setPrtSeq(mPrtSeq);
		mLCGrpRReportSet = tLCGrpRReportDB.query();
		if (mLCGrpRReportSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpRReportDealSaveBL";
			tError.functionName = "dealData";
			tError.errorMessage = "团单生调总表信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGrpRReportSchema = mLCGrpRReportSet.get(1);
		mLCGrpRReportSchema.setPrintFlag("N");
		mLCGrpRReportSet.clear();
		mLCGrpRReportSet.add(mLCGrpRReportSchema);

		// //set一条LCGrpRReport表记录
		// LCGrpRReportSchema tLCGrpRReportSchema = new LCGrpRReportSchema();
		//
		// tLCGrpRReportSchema.setGrpContNo(mGrpContNo);
		// tLCGrpRReportSchema.setPrtSeq(mPrtSeq);
		// tLCGrpRReportSchema.setManageCom(mManageCom);
		// tLCGrpRReportSchema.setAgentName(mAgentName);
		// tLCGrpRReportSchema.setAgentCode(mAgentCode);
		// tLCGrpRReportSchema.setPrintFlag("Y");
		// tLCGrpRReportSchema.setOperator(mGlobalInput.Operator);
		// tLCGrpRReportSchema.setMakeDate(PubFun.getCurrentDate());
		// tLCGrpRReportSchema.setMakeTime(PubFun.getCurrentTime());
		// tLCGrpRReportSchema.setModifyDate(PubFun.getCurrentDate());
		// tLCGrpRReportSchema.setModifyTime(PubFun.getCurrentTime());
		//        
		// try
		// {
		// mLCGrpRReportSchema = (LCGrpRReportSchema)tLCGrpRReportSchema.clone();
		// }
		// catch (Exception ex)
		// {
		// CError tError = new CError();
		// tError.moduleName = "GrpRReportDealSaveBL";
		// tError.functionName = "dealData";
		// tError.errorMessage = ex.toString();
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		//
		// mLCGrpRReportSet.add(mLCGrpRReportSchema);
		//
		if (mLCGrpRReportSet != null) {
			map.put(mLCGrpRReportSet, "UPDATE");
		}

		logger.debug("---准备LCGrpRReport表数据成功！");

		if (!preparePrint()) {
			// @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "GrpRReportDealSaveBL";
			// tError.functionName = "dealData";
			// tError.errorMessage = "准备打印数据失败!";
			// this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---准备打印管理表数据成功！");

		return true;
	}

	private boolean getInputData() {
		try {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));

			this.mLCGrpRReportSchema = (LCGrpRReportSchema) mInputData
					.getObjectByObjectName("LCGrpRReportSchema", 0);

			if (mGlobalInput == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "GrpRReportDealSaveBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "前台传输全局公共数据失败!";
				this.mErrors.addOneError(tError);

				return false;
			}
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "GrpRReportDealSaveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;

		}
		return true;
	}

	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpRReportDealSaveBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	private boolean preparePrint() {
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_GRP_MEET); // 团体体检
		tLOPRTManagerDB.setOtherNo(mGrpContNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_GRPPOL);
		tLOPRTManagerDB.setStandbyFlag1(mGrpContNo);
		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();

		logger.debug("---test1!!!!!!!");

		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpRReportDealSaveBL";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---test2!!!!!!!");
		logger.debug("---tLOPRTManagerSet.size() = "
				+ tLOPRTManagerSet.size());

		if (tLOPRTManagerSet.size() > 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpRReportDealSaveBL";
			tError.functionName = "preparePrint";
			tError.errorMessage = "在打印队列中已有一个处于未打印状态的体检通知书!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("---test3!!!!!!!");
		mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setOtherNo(mGrpContNo);

		mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GRPPOL); // 保单号
		mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRP_MEET); // 体检
		mLOPRTManagerSchema.setManageCom(mManageCom);
		mLOPRTManagerSchema.setAgentCode(mAgentCode);
		mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

		mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setPatchFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		mLOPRTManagerSchema.setStandbyFlag1(mGrpContNo); // 备用字段
		mLOPRTManagerSchema.setStandbyFlag4(mPrtSeq); // 备用字段处理批量时用

		mLOPRTManagerSet.add(mLOPRTManagerSchema);

		if (mLOPRTManagerSet != null) {
			map.put(mLOPRTManagerSet, mOperate);
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

}// class end
