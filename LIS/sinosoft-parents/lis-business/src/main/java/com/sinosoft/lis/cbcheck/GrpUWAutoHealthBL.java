package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPENoticeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPENoticeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统体检资料录入部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @version 1.0
 */
public class GrpUWAutoHealthBL {
private static Logger logger = Logger.getLogger(GrpUWAutoHealthBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mManageCom;

	private FDate fDate = new FDate();

	/** 业务处理相关变量 */
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();

	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private String mPolNo = "";

	/** 体检资料主表 */
	private LCGrpPENoticeSet mLCGrpPENoticeSet = new LCGrpPENoticeSet();

	private LCGrpPENoticeSchema mmLCGrpPENoticeSchema = new LCGrpPENoticeSchema();

	/** 打印管理表 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LOPRTManagerSet mAllLOPRTManagerSet = new LOPRTManagerSet();

	private GlobalInput mGlobalInput = new GlobalInput();

	public GrpUWAutoHealthBL() {
	}

	String mPrtSeq;

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功
		int j = 0; // 符合条件数据个数

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		logger.debug("---1---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---UWAutoHealthBL getInputData---");

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContSchema = mLCGrpContSet.get(1);
		mPolNo = tLCGrpContSchema.getGrpContNo();
		logger.debug("mPolNo=" + mPolNo);

		tLCGrpContDB.setGrpContNo(mPolNo);
		if (!tLCGrpContDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "取集体合同信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLCGrpContSchema = tLCGrpContDB.getSchema();
		// 校验个单合同是否录入体检信息
		if (!checkPolPrint()) {
			return false;
		}

		// 校验数据是否打印
		if (!checkPrint()) {
			return false;
		}

		logger.debug("---UWAutoHealthBL checkData---");
		// 数据操作业务处理
		if (!dealData(tLCGrpContSchema)) {

			return false;
		}

		logger.debug("---UWAutoHealthBL dealData---");
		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---UWAutoHealthBL prepareOutputData---");
		// 数据提交

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(LCGrpContSchema tLCGrpContSchema) {

		if (dealOnePol() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {

		// 健康信息
		if (prepareHealth() == false) {
			return false;
		}

		// 打印队列
		if (print() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();

		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mOperate = tGlobalInput.Operator;
		mManageCom = tGlobalInput.ManageCom;
		logger.debug(mManageCom);

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLCGrpContSet.set((LCGrpContSet) cInputData.getObjectByObjectName(
				"LCGrpContSet", 0));

		return true;
	}

	/**
	 * 校验是否打印
	 * 
	 * @return
	 */
	private boolean checkPrint() {
		logger.debug("--进入打印校验方法");
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setOtherNo(mLCGrpContSchema.getGrpContNo());
		tLOPRTManagerDB.setCode("73");
		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet.size() == 0) {
		} else {
			LOPRTManagerSchema tLOPRTManagerSchema;
			for (int i = 1; i <= tLOPRTManagerSet.size(); i++) {
				tLOPRTManagerSchema = tLOPRTManagerSet.get(i);
				if (tLOPRTManagerSchema.getStateFlag().equals("0")) {
					CError tError = new CError();
					tError.moduleName = "UWAutoHealthBL";
					tError.functionName = "checkPrint";
					tError.errorMessage = "体检通知已经录入尚未打印，不能发送新体检任务!";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkPolPrint() {
		String Sql = "select count(1) from LOPRTManager where 1=1 "
				+ " and standbyflag3='" + mLCGrpContSchema.getGrpContNo() + "'";

		ExeSQL tExeSQL = new ExeSQL();
		SSRS t_ssrs = new SSRS();
		t_ssrs = tExeSQL.execSQL(Sql);

		if (t_ssrs.GetText(1, 1).equals("0")) {
			CError tError = new CError();
			tError.moduleName = "GrpUWAutoHealthBL";
			tError.functionName = "checkPolPrint";
			tError.errorMessage = "未录入个单体检通知书，不能发集体体检通知书";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	/**
	 * 打印信息表
	 * 
	 * @return
	 */
	private boolean print() {

		// 处于未打印状态的通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_GRP_PE); // 团体体检
		tLOPRTManagerDB.setOtherNo(mPolNo);
		logger.debug("mPolNo=" + mPolNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_INDPOL); // 保单号
		tLOPRTManagerDB.setStandbyFlag1(mPolNo);
		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLOPRTManagerSet.size() != 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWAutoHealthAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "在打印队列中已有一个处于未打印状态的体检通知书!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 准备打印管理表数据
		LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
		logger.debug(mGlobalInput.ComCode);

		logger.debug("mPrtSeq=" + mPrtSeq);
		mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setOtherNo(mPolNo);

		mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GRPPOL); // 保单号
		mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRP_PE); // 体检
		mLOPRTManagerSchema.setManageCom(mLCGrpContSchema.getManageCom());
		mLOPRTManagerSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
		mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

		mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setPatchFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		mLOPRTManagerSchema.setStandbyFlag1(mPolNo); // 备用字段
		mLOPRTManagerSchema.setStandbyFlag4(mPrtSeq); // 备用字段处理批量时用

		mLOPRTManagerSet.add(mLOPRTManagerSchema);
		return true;
	}

	/**
	 * 准备体检资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareHealth() {
		int tuwno = 0;
		// 取险种名称
		// 取代理人姓名
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLCGrpContSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "取代理人姓名失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCGrpPENoticeSchema tLCGrpPENoticeSchema = new LCGrpPENoticeSchema();

		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
		mPrtSeq = tPrtSeq;
		tLCGrpPENoticeSchema.setPrtSeq(mPrtSeq);
		logger.debug("mPrtSeq=" + mPrtSeq);

		tLCGrpPENoticeSchema.setPrintFlag("Y");
		tLCGrpPENoticeSchema.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		tLCGrpPENoticeSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
		tLCGrpPENoticeSchema.setAgentName(tLAAgentDB.getName());
		tLCGrpPENoticeSchema.setManageCom(mLCGrpContSchema.getManageCom());

		tLCGrpPENoticeSchema.setOperator(mOperate); // 操作员
		tLCGrpPENoticeSchema.setMakeDate(PubFun.getCurrentDate());
		tLCGrpPENoticeSchema.setMakeTime(PubFun.getCurrentTime());
		tLCGrpPENoticeSchema.setModifyDate(PubFun.getCurrentDate());
		tLCGrpPENoticeSchema.setModifyTime(PubFun.getCurrentTime());
		tLCGrpPENoticeSchema.setRemark(mmLCGrpPENoticeSchema.getRemark());

		mLCGrpPENoticeSet.clear();
		mLCGrpPENoticeSet.add(tLCGrpPENoticeSchema);

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {

		mResult.clear();
		MMap map = new MMap();

		map.put(mLCGrpPENoticeSet, "INSERT");
		map.put(mLOPRTManagerSet, "INSERT");

		mResult.add(map);

	}

}
