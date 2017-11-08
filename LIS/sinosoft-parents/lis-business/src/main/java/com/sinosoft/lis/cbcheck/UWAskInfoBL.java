package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCInformationDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCInformationItemSchema;
import com.sinosoft.lis.schema.LCInformationSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCAppntIndSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpAppntSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCInformationItemSet;
import com.sinosoft.lis.vschema.LCInformationSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCSpecNoteSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
public class UWAskInfoBL {
private static Logger logger = Logger.getLogger(UWAskInfoBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mManageCom;
	private String mpassflag; // 通过标记
	private int merrcount; // 错误条数
	private String mCalCode; // 计算编码
	private String mUser;
	private FDate fDate = new FDate();
	private double mValue;
	private String mInsuredNo = "";

	/** 业务处理相关变量 */
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	// private LCGrpContSet mmLCGrpContSet = new LCGrpContSet();
	// private LCGrpContSet m2LCGrpContSet = new LCGrpContSet();
	// private LCGrpContSet mAllLCGrpContSet = new LCGrpContSet();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private String mPolNo = "";
	private String mOldPolNo = "";
	/** 保费项表 */
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCPremSet mAllLCPremSet = new LCPremSet();
	/** 领取项表 */
	private LCGetSet mLCGetSet = new LCGetSet();
	private LCGetSet mAllLCGetSet = new LCGetSet();
	/** 责任表 */
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCDutySet mAllLCDutySet = new LCDutySet();
	/** 特别约定表 */
	private LCSpecSet mLCSpecSet = new LCSpecSet();
	private LCSpecSet mAllLCSpecSet = new LCSpecSet();
	/** 特别约定注释表 */
	private LCSpecNoteSet mLCSpecNoteSet = new LCSpecNoteSet();
	private LCSpecNoteSet mAllLCSpecNoteSet = new LCSpecNoteSet();
	/** 核保主表 */
	private LCCUWMasterSet mLCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSet mAllLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();
	/** 核保子表 */
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCUWSubSet mAllLCUWSubSet = new LCUWSubSet();
	private LCUWSubSchema mLCUWSubSchema = new LCUWSubSchema();
	/** 核保错误信息表 */
	private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();
	private LCUWErrorSet mAllLCErrSet = new LCUWErrorSet();
	/** 告知表 */
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	private LCCustomerImpartSet mAllLCCustomerImpartSet = new LCCustomerImpartSet();
	/** 投保人表 */
	private LCAppntIndSet mLCAppntIndSet = new LCAppntIndSet();
	private LCAppntIndSet mAllLCAppntIndSet = new LCAppntIndSet();

	/** 受益人表 */
	private LCBnfSet mLCBnfSet = new LCBnfSet();
	private LCBnfSet mAllLCBnfSet = new LCBnfSet();
	/** 被保险人表 */
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet();
	private LCInsuredSet mAllLCInsuredSet = new LCInsuredSet();
	/** 体检资料主表 */
	private LCInformationSet mLCInformationSet = new LCInformationSet();
	private LCInformationSet mAllLCInformationSet = new LCInformationSet();
	private LCInformationSchema mLCInformationSchema = new LCInformationSchema();
	private LCInformationSchema mmLCInformationSchema = new LCInformationSchema();
	/** 体检资料项目表 */
	private LCInformationItemSet mLCInformationItemSet = new LCInformationItemSet();
	private LCInformationItemSet mmLCInformationItemSet = new LCInformationItemSet();
	private LCInformationItemSet mAllLCInformationItemSet = new LCInformationItemSet();
	/** 打印管理表 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LOPRTManagerSet mAllLOPRTManagerSet = new LOPRTManagerSet();
	/** 计算公式表* */
	private LMUWSchema mLMUWSchema = new LMUWSchema();
	// private LMUWDBSet mLMUWDBSet = new LMUWDBSet();
	private LMUWSet mLMUWSet = new LMUWSet();

	private CalBase mCalBase = new CalBase();

	private GlobalInput mGlobalInput = new GlobalInput();

	public UWAskInfoBL() {
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
		// GlobalInput tGlobalInput = new GlobalInput();
		// this.mOperate = tGlobalInput.;

		logger.debug("---1---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---UWAutoHealthBL getInputData---");

		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		// tLCGrpContSchema = ( LCGrpContSchema )mLCGrpContSet.get( 1 );
		mmLCInformationSchema = mLCInformationSet.get(1);
		String tProposalNo = mmLCInformationSchema.getGrpContNo();
		mOldPolNo = mmLCInformationSchema.getGrpContNo();
		mPolNo = mmLCInformationSchema.getGrpContNo();
		mInsuredNo = mmLCInformationSchema.getCustomerNo();

		// 校验数据是否打印
		if (!checkPrint()) {
			return false;
		}

		logger.debug("---UWAutoHealthBL checkData---");
		// 数据操作业务处理
		if (!dealData(tLCGrpContSchema)) {
			// continue;
			return false;
		} else {
			flag = 1;
			j++;
			// tLCGrpContSchema.setSchema( tLCGrpContDB );
			// m2LCGrpContSet.add(tLCGrpContSchema);
		}

		if (flag == 0) {
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "submitData";
			tError.errorMessage = "没有自动通过保单!";
			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---UWAutoHealthBL dealData---");
		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---UWAutoHealthBL prepareOutputData---");
		// 数据提交
		// UWAskInfoBLS tUWAskInfoBLS = new UWAskInfoBLS();
		// logger.debug("Start UWAutoHealthBL Submit...");
		// if (!tUWAskInfoBLS.submitData(mInputData,mOperate))
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tUWAskInfoBLS.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "UWAutoHealthBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		logger.debug("---UWAutoHealthBL commitData---");
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

		LCInformationSet tLCInformationSet = new LCInformationSet();
		tLCInformationSet.set(mLCInformationSet);
		mAllLCInformationSet.add(tLCInformationSet);

		LCInformationItemSet tLCInformationItemSet = new LCInformationItemSet();
		tLCInformationItemSet.set(mLCInformationItemSet);
		mAllLCInformationItemSet.add(tLCInformationItemSet);

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
		// mmLCGrpContSet.set((LCGrpContSet)cInputData.getObjectByObjectName("LCGrpContSet",0));

		// logger.debug("LCGrpContSet="+mmLCGrpContSet.get(1).getContNo());
		mLCInformationSet.set((LCInformationSet) cInputData
				.getObjectByObjectName("LCInformationSet", 0));
		mmLCInformationItemSet.set((LCInformationItemSet) cInputData
				.getObjectByObjectName("LCInformationItemSet", 0));
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// int n = mmLCGrpContSet.size();
		int flag = 0; // 怕判断是不是所有保单都失败
		int j = 0; // 符合条件保单个数

		if (1 > 0) {
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			// LCGrpContSchema tLCGrpContSchema = mmLCGrpContSet.get(1);

			// 取被保人客户号
			LCInformationSchema tLCInformationSchema = new LCInformationSchema();
			tLCInformationSchema = mLCInformationSet.get(1);
			mInsuredNo = tLCInformationSchema.getCustomerNo();

			tLCGrpContDB.setGrpContNo(tLCInformationSchema.getGrpContNo());
			logger.debug("--BL--Pol--"
					+ tLCInformationSchema.getGrpContNo());
			String temp = tLCInformationSchema.getGrpContNo();
			logger.debug("temp" + temp);
			mLCGrpContSet = tLCGrpContDB.query();
			if (mLCGrpContSet.size() == 0) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAutoHealthBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "投保单查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				mLCGrpContSchema = mLCGrpContSet.get(1);
				flag = 1;
				j++;

			}

		}

		if (flag == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 校验是否打印
	 * 
	 * @return
	 */
	private boolean checkPrint() {
		LCInformationDB tLCInformationDB = new LCInformationDB();
		tLCInformationDB.setGrpContNo(mPolNo);
		tLCInformationDB.setCustomerNo(mInsuredNo);
		LCInformationSet tLCInformationSet = tLCInformationDB.query();
		if (tLCInformationSet.size() == 0) {
		} else {
			if (tLCInformationSet.get(1).getPrintFlag().equals("0")) {
				CError tError = new CError();
				tError.moduleName = "UWAutoHealthBL";
				tError.functionName = "checkPrint";
				tError.errorMessage = "体检通知已经录入尚未打印，不能录入新体检资料!";
				this.mErrors.addOneError(tError);
				return false;
			}
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

		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_PE); // 体检
		tLOPRTManagerDB.setOtherNo(mPolNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_INDPOL); // 保单号
		tLOPRTManagerDB.setStandbyFlag1(mInsuredNo);
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
		mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL); // 保单号
		mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PE); // 体检
		mLOPRTManagerSchema.setManageCom(mLCGrpContSchema.getManageCom());
		mLOPRTManagerSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
		mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

		mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setPatchFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		// // mLOPRTManagerSchema.setStandbyFlag1(mInsuredNo);//被保险人编码
		//
		// VData tVData = new VData();
		// PrintManagerBL tPrintManagerBL = new PrintManagerBL();

		// tVData.add(tLOPRTManagerSchema);
		// tVData.add( mGlobalInput);
		//
		// logger.debug("Start UWAutoHealthBL Submit...");
		// if(!tPrintManagerBL.submitData(tVData,"REQUEST"))
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tPrintManagerBL.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "UWAutoHealthBL";
		// tError.functionName = "print";
		// tError.errorMessage = "数据提交打印队列失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }

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

		LCInformationSchema tLCInformationSchema = new LCInformationSchema();
		tLCInformationSchema = mLCInformationSet.get(1);

		// 取被保人姓名
		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();

		tLCGrpAppntDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		LCGrpAppntSet tLCGrpAppntSet = new LCGrpAppntSet();
		tLCGrpAppntSet = tLCGrpAppntDB.query();

		if (tLCGrpAppntSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "取被保人姓名失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInsuredNo = tLCGrpAppntSet.get(1).getCustomerNo();

		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
		mPrtSeq = tPrtSeq;
		tLCInformationSchema.setPrtSeq(mPrtSeq);
		logger.debug("mPrtSeq=" + mPrtSeq);

		tLCInformationSchema.setPrintFlag("0");
		tLCInformationSchema.setPrtNo(mLCGrpContSchema.getPrtNo());
		tLCInformationSchema.setCustomerNo(mInsuredNo);
		tLCInformationSchema.setCustomerNoType("0");
		tLCInformationSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
		tLCInformationSchema.setAgentName(tLAAgentDB.getName());
		tLCInformationSchema.setManageCom(mLCGrpContSchema.getManageCom());

		tLCInformationSchema.setOperator(mOperate); // 操作员
		tLCInformationSchema.setMakeDate(PubFun.getCurrentDate());
		tLCInformationSchema.setMakeTime(PubFun.getCurrentTime());
		tLCInformationSchema.setModifyDate(PubFun.getCurrentDate());
		tLCInformationSchema.setModifyTime(PubFun.getCurrentTime());
		tLCInformationSchema.setRemark(mLCInformationSchema.getRemark());

		mLCInformationSet.clear();
		mLCInformationSet.add(tLCInformationSchema);

		// 取体检资料项目信息
		mLCInformationItemSet.clear();
		merrcount = mmLCInformationItemSet.size();
		if (merrcount > 0) {
			for (int i = 1; i <= merrcount; i++) {
				// 取出错信息
				LCInformationItemSchema tLCInformationItemSchema = new LCInformationItemSchema();
				tLCInformationItemSchema = mmLCInformationItemSet.get(i);
				// 生成流水号
				String tserialno = "" + i;

				tLCInformationItemSchema.setGrpContNo(mPolNo);
				tLCInformationItemSchema.setPrtNo(mLCGrpContSchema.getPrtNo());
				tLCInformationItemSchema.setPrtSeq(mPrtSeq);
				// 由于先没有该字段，先把它注释掉，用到的时候再看（张星）
				tLCInformationItemSchema.setInformationKind("0");
				tLCInformationItemSchema
						.setInformationCode(tLCInformationItemSchema
								.getInformationCode()); // 补充资料编码
				tLCInformationItemSchema
						.setInformationName(tLCInformationItemSchema
								.getInformationName()); // 补充资料内容
				tLCInformationItemSchema.setRemark(tLCInformationItemSchema
						.getRemark());

				tLCInformationItemSchema.setMakeDate(PubFun.getCurrentDate());
				tLCInformationItemSchema.setMakeTime(PubFun.getCurrentTime());
				tLCInformationItemSchema.setModifyDate(PubFun.getCurrentDate()); // 当前值
				tLCInformationItemSchema.setModifyTime(PubFun.getCurrentTime());

				LCInformationItemSchema ttLCInformationItemSchema = new LCInformationItemSchema();
				ttLCInformationItemSchema.setSchema(tLCInformationItemSchema);

				mLCInformationItemSet.add(ttLCInformationItemSchema);
			}
		}

		// //核保主表信息
		// LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		// tLCCUWMasterDB.setGrpContNo(mPolNo);
		// LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		// tLCCUWMasterSet = tLCCUWMasterDB.query();
		//
		// if(tLCCUWMasterSet == null)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "UWAutoHealthBL";
		// tError.functionName = "prepareHealth";
		// tError.errorMessage = "无核保主表信息!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		//
		// mLCCUWMasterSchema = tLCCUWMasterSet.get(1).getSchema();
		//
		// //校验是否已经发放核保通知书
		// if (mLCCUWMasterSchema.getPrintFlag().equals("1"))
		// {
		// CError tError = new CError();
		// tError.moduleName = "UWAutoHealthBL";
		// tError.functionName = "prepareHealth";
		// tError.errorMessage = "已经发核保通知不可录入!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		// mLCCUWMasterSchema.setHealthFlag("1");

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		// mInputData.clear();
		// mInputData.add( mAllLCInformationSet );
		// mInputData.add( mAllLCInformationItemSet );
		// mInputData.add( mLCCUWMasterSchema);
		// mInputData.add(mLOPRTManagerSet);
		mResult.clear();
		MMap map = new MMap();

		map.put(mAllLCInformationSet, "INSERT");
		map.put(mAllLCInformationItemSet, "INSERT");
		map.put(mLOPRTManagerSet, "INSERT");

		mResult.add(map);
		mResult.add(mLOPRTManagerSet.get(1));
	}

	/**
	 * 返回参数
	 */
	public VData getResult() {
		return mResult;
	}

}
