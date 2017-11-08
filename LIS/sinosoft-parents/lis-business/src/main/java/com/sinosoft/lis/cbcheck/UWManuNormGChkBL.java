package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinosoft.ibrms.bom.BOMAgent;
import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.ibrms.bom.BOMElement;
import com.sinosoft.ibrms.bom.BOMInsured;
import com.sinosoft.ibrms.bom.BOMPol;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWErrorDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LMUWDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWErrorSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LDSysTraceSchema;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LBTempFeeClassSet;
import com.sinosoft.lis.vschema.LBTempFeeSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LDSysTraceSet;
import com.sinosoft.lis.vschema.LDUserSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保个人人工核保部分
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
 * @author Zhangrong
 * @version 1.0
 */
public class UWManuNormGChkBL {
private static Logger logger = Logger.getLogger(UWManuNormGChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperator; // 操作员
	private String mManageCom;
	private String mpassflag; // 通过标记
	private String mContPassFlag = "";
	private String mBackFlag;
	private int merrcount; // 错误条数
	private String mCalCode; // 计算编码
	private String mUser;
	private FDate fDate = new FDate();
	private double mValue;
	private double mprem;
	private double mamnt;
	private String theDate = "";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** 业务处理相关变量 */
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolSet mmLCPolSet = new LCPolSet();
	private LCPolSet m2LCPolSet = new LCPolSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCContSet mLCContSet = new LCContSet();
	private LCContSet mAllLCContSet = new LCContSet();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private LCGrpContSet mAllLCGrpContSet = new LCGrpContSet();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private String mGrpContNo = "";
	private String mContNo = "";
	private String mPolNo = "";
	private String mUWFlag = ""; // 核保标志
	private Date mvalidate = null;
	private String mUWIdea = ""; // 核保结论
	private String mUPUWCode = "";
	private String mpostday; // 延长天数
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别
	private String mReason = ""; // 加费原因
	private String mSpecReason = ""; // 特约原因
	private String mQuesFlag = ""; // 是否有问题件标记
	private String mQuesOrgFlag = ""; // 机构问题件标记
	private String mQuesOpeFlag = ""; // 操作员问题件标记
	private String mPolType = ""; // 保单类型
	private String mAgentPrintFlag = ""; // 是否有返回业务员要打印的问题件标记
	private String mAgentQuesFlag = ""; // 是否有返回业务员问题件标记
	private boolean mChgContMaster = false; // 是否更新LCCUWMaster

	// modify by Minim
	private String mBackUWGrade = "";
	private String mBackAppGrade = "";
	private Reflections mReflections = new Reflections();
	private String mPrtNo = "";
	private LJSPaySet outLJSPaySet = new LJSPaySet();
	private LJTempFeeSet outLJTempFeeSet = new LJTempFeeSet();
	private LBTempFeeSet outLBTempFeeSet = new LBTempFeeSet();
	private LJTempFeeClassSet outLJTempFeeClassSet = new LJTempFeeClassSet();
	private LBTempFeeClassSet outLBTempFeeClassSet = new LBTempFeeClassSet();

	/** 核保主表 */
	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSet mAllLCCUWMasterSet = new LCCUWMasterSet();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterSet mAllLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();

	/** 核保子表 */
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();
	private LCCUWSubSet mAllLCCUWSubSet = new LCCUWSubSet();
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCUWSubSet mAllLCUWSubSet = new LCUWSubSet();

	/** 核保错误信息表 */
	private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();
	private LCUWErrorSet mAllLCErrSet = new LCUWErrorSet();

	/** 打印管理表 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LOPRTManagerSet mAllLOPRTManagerSet = new LOPRTManagerSet();

	/** 轨迹锁表* */
	private LDSysTraceSchema mLDSysTraceSchema = new LDSysTraceSchema();
	private LDSysTraceSet mLDSysTraceSet = new LDSysTraceSet();
	private LDSysTraceSet mAllLDSysTraceSet = new LDSysTraceSet();

	/** 计算公式表* */
	private LMUWSchema mLMUWSchema = new LMUWSchema();
	private LMUWSet mLMUWSet = new LMUWSet();
	private CalBase mCalBase = new CalBase();
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mGetNoticeNo = "";

	public UWManuNormGChkBL() {
	}

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

		// 得到外部传入的数据,将数据备份到本类中
		logger.debug("---UWManuNormGChkBL getInputData Begin ---");

		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("---UWManuNormGChkBL getInputData End ---");

		// 校验该投保单是否已复核过,是则可进行核保,否则不能进行
		if (!checkApprove()) {
			return false;
		}

		// 校验核保级别
		logger.debug("---UWManuNormGChkBL checkUWGrade Begin---");

		if (!checkUWGrade()) {
			return false;
		}

		logger.debug("---UWManuNormGChkBL checkUWGrade End---");

		// 生成给付通知书号
		String tLimit = PubFun.getNoLimit(mManageCom);
		mGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit); // 产生即付通知书号
		logger.debug("---tLimit---" + tLimit);

		/*
		 * //拒保、延期或撤单时，校验银行在途数据 if (mUWFlag.equals("a") || mUWFlag.equals("1") ||
		 * mUWFlag.equals("2")) { //查询应收总表数据 String strSql = "select * from
		 * ljspay where trim(otherno) in " + " (select trim(polno) from lcpol
		 * where prtno='" + mPrtNo + "' " + " union " + " select
		 * trim(proposalno) from lcpol where prtno='" + mPrtNo + "' " + " union " + "
		 * select trim(prtno) from lcpol where prtno='" + mPrtNo + "' )";
		 * 
		 * outLJSPaySet = (new LJSPayDB()).executeQuery(strSql);
		 * 
		 * for (int i=0; i<outLJSPaySet.size(); i++) { if
		 * (outLJSPaySet.get(i+1).getBankOnTheWayFlag().equals("1")) {
		 * logger.debug("有银行在途数据，不允许拒保、延期或撤单!"); // @@错误处理 CError tError =
		 * new CError(); tError.moduleName = "UWManuNormGChkBL";
		 * tError.functionName = "submitData"; tError.errorMessage =
		 * "有银行在途数据，不允许拒保、延期或撤单!"; this.mErrors .addOneError(tError) ; return
		 * false; } }
		 * 
		 * //查询暂交费表数据 strSql = "select * from ljtempfee where EnterAccDate is
		 * null and trim(otherno) in " + " (select trim(polno) from lcpol where
		 * prtno='" + mPrtNo + "' " + " union " + " select trim(proposalno) from
		 * lcpol where prtno='" + mPrtNo + "' " + " union " + " select
		 * trim(prtno) from lcpol where prtno='" + mPrtNo + "' )";
		 * 
		 * outLJTempFeeSet = (new LJTempFeeDB()).executeQuery(strSql);
		 * 
		 * if (outLJTempFeeSet.size() > 0) { for (int i=0; i<outLJTempFeeSet.size();
		 * i++) { LBTempFeeSchema tLBTempFeeSchema = new LBTempFeeSchema();
		 * mReflections.transFields(tLBTempFeeSchema, outLJTempFeeSet.get(i+1));
		 * tLBTempFeeSchema.setBackUpSerialNo(PubFun1.CreateMaxNo("LBTempFee",
		 * 20)); outLBTempFeeSet.add(tLBTempFeeSchema);
		 * 
		 * LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		 * tLJTempFeeClassDB.setTempFeeNo(outLJTempFeeSet.get(i+1).getTempFeeNo());
		 * outLJTempFeeClassSet.add(tLJTempFeeClassDB.query()); }
		 * 
		 * for (int i=0; i<outLJTempFeeClassSet.size(); i++) {
		 * LBTempFeeClassSchema tLBTempFeeClassSchema = new
		 * LBTempFeeClassSchema();
		 * mReflections.transFields(tLBTempFeeClassSchema,
		 * outLJTempFeeClassSet.get(i+1));
		 * tLBTempFeeClassSchema.setBackUpSerialNo(PubFun1.CreateMaxNo("LBTFClass",
		 * 20)); outLBTempFeeClassSet.add(tLBTempFeeClassSchema); } } //
		 * logger.debug("outLJTempFeeSet:" + outLJTempFeeSet.encode()); //
		 * logger.debug("outLBTempFeeSet:" + outLBTempFeeSet.encode()); }
		 */

		// commented by zhr 2004.11
		if (mUWFlag.equals("1") || mUWFlag.equals("2") || mUWFlag.equals("4")
				|| mUWFlag.equals("9")) {
			// 次标准体校验核保员级别
			if (!checkStandGrade()) {
				return false;
			}

			if (!checkBackPol()) {
				return false;
			}
		}

		// 拒保或延期要校验核保员级别
		if (mUWFlag.equals("1") || mUWFlag.equals("2")) {
			if (!checkUserGrade()) {
				return false;
			}
		}

		// 校验主附险
		logger.debug("校验主附险" + mUWFlag);

		if (!mUWFlag.equals("1") && !mUWFlag.equals("2")
				&& !mUWFlag.equals("3") && !mUWFlag.equals("6")
				&& !mUWFlag.equals("8") && !mUWFlag.equals("a")
				&& !mUWFlag.equals("b") && !mUWFlag.equals("z")) {
			if (!checkMain()) {
				return false;
			}
		}

		if (!checkData()) {
			return false;
		}

		// 数据操作业务处理
		logger.debug("数据操作业务处理" + mUWFlag);

		if (!dealData()) {
			return false;
		}

		logger.debug("---UWManuNormGChkBL dealData---");

		// 准备给后台的数据
		prepareOutputData();
		logger.debug("---UWManuNormGChkBL prepareOutputData---");

		// 数据提交
		PubSubmit tSubmit = new PubSubmit();
		if (!cOperate.equals("submit")) {

			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "UWManuNormGChkBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);

				return false;
			}
		}
		/*
		 * //撤单退费处理 logger.debug("Start Return Tempfee");
		 * if(mUWFlag.equals("a") || mUWFlag.equals("1") || mUWFlag.equals("2"))
		 * if(!returnFee()) return false;
		 */

		// commented by zhr 2004.11
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据操作撤单业务处理 输出：如果出错，则返回false,否则返回true
	 */

	/*
	 * private boolean returnFee() { logger.debug("In ReturnFee");
	 * 
	 * String payMode=""; //交费方式 String BankCode="";//银行编码 String
	 * BankAccNo="";//银行账号 String AccName=""; //户名
	 * 
	 * //准备TransferData数据 String strSql=""; LCPolDB tLCPolDB = new LCPolDB();
	 * 
	 * tLCPolDB.setPolNo(mAllLCPolSet.get(1).getPolNo()); if( tLCPolDB.getInfo() ==
	 * false ) { // @@错误处理 this.mErrors.copyAllErrors( tLCPolDB.mErrors );
	 * 
	 * return false; }
	 * 
	 * //测试该投保单是否有暂交费待退 strSql = "select * from ljtempfee where trim(otherno) in ( " +
	 * "select proposalno from lcpol where mainpolno ='" + tLCPolDB.getPolNo() +
	 * "'" + " union select '"+tLCPolDB.getPrtNo() +"' from dual )" + " and
	 * EnterAccDate is not null " + " and confdate is null";
	 * 
	 * LJTempFeeDB sLJTempFeeDB=new LJTempFeeDB(); LJTempFeeSet
	 * sLJTempFeeSet=new LJTempFeeSet();
	 * sLJTempFeeSet=sLJTempFeeDB.executeQuery(strSql);
	 * logger.debug("暂交费数量: "+sLJTempFeeSet.size());
	 * if(sLJTempFeeSet.size()==0) { logger.debug("Out ReturnFee"); return
	 * true; }
	 * 
	 * //如果通知书号不为空，找出退费方式（优先级依次为支票，银行，现金） GetPayType tGetPayType=new
	 * GetPayType();
	 * if(tGetPayType.getPayTypeForLCPol(tLCPolDB.getPrtNo())==false) { //
	 * @@错误处理 this.mErrors.copyAllErrors( tGetPayType.mErrors );
	 * logger.debug("查询出错信息 :"+tGetPayType.mErrors); return false; } else {
	 * logger.debug("BankCode: "+tGetPayType.getPayMode());
	 * logger.debug("AccNo"+tGetPayType.getBankCode());
	 * logger.debug("AccName"+tGetPayType.getBankAccNo());
	 * 
	 * payMode=tGetPayType.getPayMode(); //交费方式
	 * BankCode=tGetPayType.getBankCode();//银行编码
	 * BankAccNo=tGetPayType.getBankAccNo();//银行账号
	 * AccName=tGetPayType.getAccName(); //户名 }
	 * 
	 * TransferData sTansferData=new TransferData();
	 * sTansferData.setNameAndValue("PayMode",payMode); if(payMode.equals("1")) {
	 * sTansferData.setNameAndValue("BankFlag","0" ); } else {
	 * sTansferData.setNameAndValue("BankCode",BankCode);
	 * sTansferData.setNameAndValue("AccNo",BankAccNo);
	 * sTansferData.setNameAndValue("AccName",AccName );
	 * sTansferData.setNameAndValue("BankFlag","1" ); }
	 * sTansferData.setNameAndValue("GetNoticeNo", mGetNoticeNo);
	 * 
	 * LJTempFeeSet tLJTempFeeSet=new LJTempFeeSet(); LJAGetTempFeeSet
	 * tLJAGetTempFeeSet=new LJAGetTempFeeSet();
	 * 
	 * strSql="select uwidea from LCUWMaster where
	 * polno='"+tLCPolDB.getPolNo()+"'"; LCUWMasterDB sLCUWMasterDB=new
	 * LCUWMasterDB(); LCUWMasterSet sLCUWMasterSet=new LCUWMasterSet();
	 * sLCUWMasterSet=sLCUWMasterDB.executeQuery(strSql);
	 * 
	 * 
	 * logger.debug("核保意见: "+mAllLCUWMasterSet.get(1).getUWIdea());
	 * 
	 * for (int index=1; index<=sLJTempFeeSet.size(); index++) {
	 * logger.debug("HaveDate In Second1");
	 * 
	 * LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
	 * tLJTempFeeSchema.setTempFeeNo(sLJTempFeeSet.get(index).getTempFeeNo() );
	 * tLJTempFeeSchema.setTempFeeType(sLJTempFeeSet.get(index).getTempFeeType() );
	 * tLJTempFeeSchema.setRiskCode(sLJTempFeeSet.get(index).getRiskCode() );
	 * tLJTempFeeSet.add(tLJTempFeeSchema);
	 * 
	 * LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
	 * //tLJAGetTempFeeSchema.setGetReasonCode(mAllLCUWMasterSet.get(1).getUWIdea());
	 * tLJAGetTempFeeSchema.setGetReasonCode("99");
	 * 
	 * tLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);
	 * 
	 * logger.debug("HaveDate In Second2"); try {
	 * logger.debug("TempFeeNo:
	 * "+sLJTempFeeSet.get(index).getTempFeeNo());
	 * logger.debug("TempFeeType:
	 * "+sLJTempFeeSet.get(index).getTempFeeType());
	 * logger.debug("RiskCode: "+sLJTempFeeSet.get(index).getRiskCode()); }
	 * catch (Exception e) { logger.debug("无银行数据!"); }
	 *  }
	 * 
	 *  // 准备传输数据 VData VData tVData = new VData(); tVData.add(tLJTempFeeSet);
	 * tVData.add(tLJAGetTempFeeSet); tVData.add(sTansferData);
	 * tVData.add(mGlobalInput);
	 *  // 数据传输 logger.debug("--------开始传输数据---------"); TempFeeWithdrawBL
	 * tTempFeeWithdrawBL = new TempFeeWithdrawBL();
	 * if(tTempFeeWithdrawBL.submitData(tVData, "INSERT")==true)
	 * logger.debug("---ok---"); else logger.debug("---NO---");
	 * 
	 * if (tTempFeeWithdrawBL.mErrors.needDealError()) { // @@错误处理
	 * this.mErrors.copyAllErrors(tTempFeeWithdrawBL.mErrors); CError tError =
	 * new CError(); tError.moduleName = "UWManuNormGChkBL"; tError.functionName =
	 * "submitData"; tError.errorMessage = "核保成功,但退费失败!"; this.mErrors
	 * .addOneError(tError) ; return false; }
	 * 
	 * logger.debug("Out ReturnFee"); return true ; }
	 */

	// commented by zhr 2004.11
	private boolean checkData() {
		// 校验合同单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);

		LCContSet tLCContSet = tLCContDB.query();

		if ((tLCContSet == null) || (tLCContSet.size() <= 0)) {
			CError tError = new CError();
			tError.moduleName = "UWGrpManuAddChkBL";
			tError.functionName = "preparePol";
			tError.errorMessage = "合同" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 校验团单信息
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);

		LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();

		if ((tLCGrpContSet == null) || (tLCGrpContSet.size() <= 0)) {
			CError tError = new CError();
			tError.moduleName = "UWGrpManuAddChkBL";
			tError.functionName = "preparePol";
			tError.errorMessage = "合同" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("begin dealOnePol()");
		if (preparePol() == false) {
			return false;
		}
		logger.debug("---dealOnePol end---");
		return true;
	}

	/**
	 * 操作一张保单及相关附加险的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */

	/*
	 * private boolean dealAllPol() {
	 *  // 校验增加附加险申请 if (checkAddPol() == false) return false;
	 *  // 保单 if (prepareAllPol() == false) return false;
	 *  // 核保信息 if (prepareAllUW() == false) return false;
	 * 
	 * TimeAccept();
	 * 
	 * LCPolSet tLCPolSet = new LCPolSet(); tLCPolSet.set( mLCPolSet );
	 * mAllLCPolSet.add( tLCPolSet );
	 * 
	 * LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet(); tLCUWMasterSet.set(
	 * mLCUWMasterSet ); mAllLCUWMasterSet.add( tLCUWMasterSet );
	 * 
	 * LCUWSubSet tLCUWSubSet = new LCUWSubSet(); tLCUWSubSet.set( mLCUWSubSet );
	 * mAllLCUWSubSet.add( tLCUWSubSet );
	 * 
	 * return true; }
	 */

	// commented by zhr 2004.11
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		String getflag = "true"; // 判断承保条件是否接收

		GlobalInput tGlobalInput = new GlobalInput();
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;

		// 取投保单
		mLCPolSet.set((LCPolSet) cInputData
				.getObjectByObjectName("LCPolSet", 0));

		int n = mLCPolSet.size();

		if (n == 1) {
			LCPolSchema tLCPolSchema = mLCPolSet.get(1);
			mUWFlag = tLCPolSchema.getUWFlag();
			mPolNo = tLCPolSchema.getPolNo();
			mUWIdea = tLCPolSchema.getRemark();

			// 校验是不是以下核保结论
			if ((mUWFlag == null) || mUWFlag.equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "UWManuNormGChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "没有选择核保结论";
				this.mErrors.addOneError(tError);

				return false;
			}

			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mPolNo);
			logger.debug("--保单表中的保单号:  " + mPolNo);

			if (tLCPolDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);

				CError tError = new CError();
				tError.moduleName = "UWManuNormGChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = mPolNo + "投保单查询失败!";
				this.mErrors.addOneError(tError);

				return false;
			} else {
				mLCPolSchema.setSchema(tLCPolDB);
				mContNo = mLCPolSchema.getContNo();
				mGrpContNo = mLCPolSchema.getGrpContNo();
			}

			// 判断是团单下个单还是个人单
			if (!mLCPolSchema.getGrpPolNo().equals("00000000000000000000")) {
				mPolType = "2";
			}

			/*
			 * if (mUWFlag.equals("3")) {
			 * mLCPremSet.set((LCPremSet)cInputData.getObjectByObjectName("LCPremSet",0));
			 * n = mLCPremSet.size(); if (n > 0) { }
			 * 
			 * mLCSpecSet.set((LCSpecSet)cInputData.getObjectByObjectName("LCSpecSet",0));
			 * n = mLCSpecSet.size(); if (n == 1) { } }
			 * 
			 * mLCUWMasterSet.set((LCUWMasterSet)cInputData.getObjectByObjectName("LCUWMasterSet",0));
			 * n = mLCUWMasterSet.size(); if (n == 1) { LCUWMasterSchema
			 * tLCUWMasterSchema = mLCUWMasterSet.get(1);
			 * 
			 * LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
			 * tLCUWMasterDB.setPolNo( mPolNo);
			 * tLCUWMasterDB.setProposalNo(mPolNo);
			 * logger.debug("--核保主表中的保单号: "+mPolNo); if
			 * (tLCUWMasterDB.getInfo() == false) { // @@错误处理
			 * this.mErrors.copyAllErrors( tLCUWMasterDB.mErrors ); CError
			 * tError = new CError(); tError.moduleName = "UWManuNormGChkBL";
			 * tError.functionName = "getInputData"; tError.errorMessage =
			 * mPolNo+"投保单查询失败!"; this.mErrors .addOneError(tError); return
			 * false; } }
			 * 
			 * else { return false; }
			 */
			/*
			 * //取附加险 tLCPolDB = new LCPolDB(); tLCPolDB.setMainPolNo(mPolNo);
			 * m2LCPolSet = tLCPolDB.query();
			 * 
			 * //add by Minim if (m2LCPolSet.size() > 0) mPrtNo =
			 * m2LCPolSet.get(1).getPrtNo();
			 */
		} else {
			return false;
		}

		return true;
	}

	/**
	 * 校验投保单是否复核 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkApprove() {
		if ((mLCPolSchema.getApproveCode() == null)
				|| mLCPolSchema.getApproveDate().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "checkApprove";
			tError.errorMessage = "投保单尚未进行复核操作，不能核保!（投保单号：" + mPolNo.trim()
					+ "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUWGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperator);
		logger.debug("mOperator" + mOperator);

		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		mUWPopedom = tUWPopedom;
		mAppGrade = mUWPopedom;
		logger.debug("tUWPopedom" + tUWPopedom);

		if ((tUWPopedom == null) || tUWPopedom.equals("")) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "操作员无核保权限，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setPolNo(mLCPolSchema.getPolNo());
		tLCUWMasterDB.setProposalNo(mLCPolSchema.getPolNo());
		logger.debug("tUWPopedom" + tUWPopedom);

		if (!tLCUWMasterDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "没有核保信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			String tappgrade = tLCUWMasterDB.getAppGrade();

			if ((tappgrade == null) || tappgrade.equals("")) {
				CError tError = new CError();
				tError.moduleName = "UWManuNormGChkBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "该投保单无核保级别，不能核保!";
				this.mErrors.addOneError(tError);

				return false;
			}

			logger.debug("tappgrade+tUWPopedom" + tappgrade + tUWPopedom);

			if (tUWPopedom.compareTo(tappgrade) < 0) {
				CError tError = new CError();
				tError.moduleName = "UWManuNormGChkBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "已经提交上级核保，不能核保!（操作员：" + mOperator + "）";
				this.mErrors.addOneError(tError);

				return false;
			}
		}

		LCUWErrorDB tLCUWErrorDB = new LCUWErrorDB();
		tLCUWErrorDB.setPolNo(mLCPolSchema.getPolNo());

		String tpolno = mLCPolSchema.getPolNo();
		String tsql = "select * from lcuwerror where polno = '"
				+ "?polno?"
				+ "' and uwno = (select max(uwno) from lcuwerror where polno = '"
				+ "?polno?" + "')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tsql);
		sqlbv.put("polno", tpolno.trim());
		LCUWErrorSet tLCUWErrorSet = tLCUWErrorDB.executeQuery(sqlbv);

		int errno = tLCUWErrorSet.size();

		if (errno > 0) {
			for (int i = 1; i <= errno; i++) {
				LCUWErrorSchema tLCUWErrorSchema = new LCUWErrorSchema();
				tLCUWErrorSchema = tLCUWErrorSet.get(i);

				String terrgrade = tLCUWErrorSchema.getUWGrade();
				logger.debug("terrgrade" + terrgrade);

				if ((terrgrade != null)
						&& (tUWPopedom.compareTo(terrgrade) < 0)
						&& !mUWFlag.equals("6") && !mUWFlag.equals("8")
						&& !mUWFlag.equals("3")) {
					CError tError = new CError();
					tError.moduleName = "UWManuNormGChkBL";
					tError.functionName = "checkUWGrade";
					tError.errorMessage = "核保级别不够，请录入核保意见，申请待上级核保!（操作员："
							+ mOperator + "）";
					this.mErrors.addOneError(tError);

					return false;
				}

				logger.debug("tUWPopedom" + tUWPopedom);
			}
		}

		return true;
	}

	/**
	 * 校验是否有撤销投保申请
	 * 
	 * @return
	 */
	private boolean checkBackPol() {
		/*
		 * LCApplyRecallPolDB tLCApplyRecallPolDB = new LCApplyRecallPolDB();
		 * LCApplyRecallPolSet tLCApplyRecallPolSet = new LCApplyRecallPolSet();
		 * 
		 * tLCApplyRecallPolDB.setProposalNo(mPolNo);
		 * tLCApplyRecallPolDB.setInputState("0");
		 * 
		 * tLCApplyRecallPolSet = tLCApplyRecallPolDB.query();
		 * 
		 * if(tLCApplyRecallPolSet.size() > 0) { CError tError = new CError();
		 * tError.moduleName = "UWManuNormGChkBL"; tError.functionName =
		 * "checkBackPol"; tError.errorMessage = "有撤单申请不能下此结论！"; this.mErrors
		 * .addOneError(tError); return false;
		 */

		// zhr 暂留，也许以后有用。
		return true;
	}

	/**
	 * 校验申请附加险
	 * 
	 * @return
	 */

	/*
	 * private boolean checkAddPol() { LCAddPolDB tLCAddPolDB = new
	 * LCAddPolDB(); LCAddPolSet tLCAddPolSet = new LCAddPolSet();
	 * 
	 * tLCAddPolDB.setMainPolNo(mPolNo); tLCAddPolDB.setInputState("0");
	 * 
	 * tLCAddPolSet = tLCAddPolDB.query();
	 * 
	 * if(tLCAddPolSet.size() > 0) { CError tError = new CError();
	 * tError.moduleName = "UWManuNormGChkBL"; tError.functionName =
	 * "checkAddPol"; tError.errorMessage = "有附加险申请，不能下此结论！"; this.mErrors
	 * .addOneError(tError); return false; }
	 * 
	 * return true; }
	 */

	// comment by zhr 2004.11
	/**
	 * 校验该主险的附加险是否已核保通过
	 */
	private boolean checkMain() {
		if (mLCPolSchema.getMainPolNo().equals(mLCPolSchema.getPolNo())) {
			String tsql = "select * from lcpol where mainpolno = '"
					+ "?mainpolno?" + "' and mainpolno <> polno";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tsql);
			sqlbv1.put("mainpolno", mLCPolSchema.getPolNo());
			logger.debug("附险查询:" + tsql);

			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();

			tLCPolSet = tLCPolDB.executeQuery(sqlbv1);

			if (tLCPolSet.size() > 0) {
				for (int i = 1; i <= tLCPolSet.size(); i++) {
					LCPolSchema tLCPolSchema = new LCPolSchema();

					tLCPolSchema = tLCPolSet.get(i);

					if (!tLCPolSchema.getUWFlag().equals("4")
							&& !tLCPolSchema.getUWFlag().equals("9")) {
						// CError tError = new CError();
						// tError.moduleName = "UWManuNormGChkBL";
						// tError.functionName = "checkMain";
						// tError.errorMessage = "该主险有附加险尚未核保通过！";
						// this.mErrors.addOneError(tError);

						// return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * 个人单核保数据准备 输出：如果发生错误则返回false,否则返回true
	 */
	private void CheckPolInit(LCPolSchema tLCPolSchema) {
		mCalBase = new CalBase();
		mCalBase.setPrem(tLCPolSchema.getPrem());
		mCalBase.setGet(tLCPolSchema.getAmnt());
		mCalBase.setMult(tLCPolSchema.getMult());
		mCalBase.setAppAge(tLCPolSchema.getInsuredAppAge());
		mCalBase.setSex(tLCPolSchema.getInsuredSex());
		mCalBase.setJob(tLCPolSchema.getOccupationType());
		mCalBase.setCount(tLCPolSchema.getInsuredPeoples());
		mCalBase.setPolNo(tLCPolSchema.getPolNo());
	}

	/**
	 * 个人单核保 输出：如果发生错误则返回false,否则返回true
	 */
	private String CheckPol() {
		// 准备数据
		CheckPolInit(mLCPolSchema);

		String tUWGrade = "";
		
		String contno = mLCPolSchema.getContNo();
		String insuredno = mLCPolSchema.getInsuredNo();
		String agentcode = mLCPolSchema.getAgentCode();
		
		//准备计算信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(contno);
		if(!tLCContDB.getInfo()){
			CError.buildErr(this, "查询合同信息失败！");
			return null;
		}
		
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(contno);
		if(!tLCAppntDB.getInfo()){
			CError.buildErr(this, "查询投保人信息失败！");
			return null;
		}
		
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(agentcode);
		if(!tLAAgentDB.getInfo()){
			CError.buildErr(this, "查询代理人信息失败！");
			return null;
		}
		
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(contno);
		tLCInsuredDB.setInsuredNo(insuredno);
		if(!tLCInsuredDB.getInfo()){
			CError.buildErr(this, "查询被保人信息失败！");
			return null;
		}
		
		BOMCont cont = new BOMCont();
		cont = DealBOMCont(tLCContDB.getSchema());
		
		
		/**备用**/
		//准备被保人BOMAppnt数据
		BOMAppnt appnt = new BOMAppnt();		
		appnt = DealBOMAppnt(tLCAppntDB.getSchema());
		
		//准备代理人BOMAgent数据
		BOMAgent agent = new BOMAgent();
		agent = DealBOMAgent(tLAAgentDB.getSchema());
		
		//准备被保人BOMInsured数据
		BOMInsured insured = new BOMInsured();
		insured = DealBOMInsured(tLCInsuredDB.getSchema());
		
		//准备险种BOMPol数据
		BOMPol pol = new BOMPol();//一个险种
		pol = DealBOMPol(mLCPolSchema);
		
		//准备受益人BOMBnf数据
		BOMElement element = new BOMElement();
		List list = new ArrayList();
		list.add(cont);
		list.add(appnt);
		list.add(agent);
		list.add(insured);
		list.add(pol);
		
		list.add(element);
		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);

		// 增加基本要素
		mCalculator.addBasicFactor("Get", mCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("Years", mCalBase.getYears());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("ValiDate", "");
		mCalculator.addBasicFactor("Count", mCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo());
		mCalculator.addBasicFactor("InsuredNo", mLCPolSchema.getInsuredNo());

		String tStr = "";
		tStr = mCalculator.calculate();

		if (tStr.trim().equals("")) {
			tUWGrade = "";
		} else {
			tUWGrade = tStr.trim();
		}

		logger.debug("AmntGrade:" + tUWGrade);

		return tUWGrade;
	}
	private BOMCont DealBOMCont(LCContSchema tLCContSchema){
		BOMCont cont = new BOMCont();
		try{			
			cont.setSellType(tLCContSchema.getSellType());//出单方式
			//tongmeng 2009-02-19
			//问题件信息先设置默认值
			cont.setComIssueFlag("0");//机构问题件标记
			cont.setCustomerIssueFlag("0");//客户问题件标记
			cont.setAppntIssueFlag("0");//投保人问题件标记
			cont.setAgentIssueFlag("0");//业务员问题件标记			
			cont.setAutoPayFlag(tLCContSchema.getAutoPayFlag());
			cont.setBankAccNo(tLCContSchema.getBankAccNo());
			cont.setBankCode(tLCContSchema.getBankCode());
			cont.setCardFlag(tLCContSchema.getCardFlag());
			cont.setContNo(tLCContSchema.getContNo());			
			cont.setManageCom(tLCContSchema.getManageCom());			
			cont.setOutPayFlag(tLCContSchema.getOutPayFlag());	
			cont.setPrem(tLCContSchema.getPrem());
			cont.setAmnt(tLCContSchema.getAmnt());
			cont.setMult(tLCContSchema.getMult());
			cont.setContNo(tLCContSchema.getContNo());
		}catch(Exception e){
			CError.buildErr(this, "准备BOMCont时出错！");
			e.printStackTrace();
		}
		return cont;
	}
	
	

	private BOMAppnt DealBOMAppnt(LCAppntSchema tLCAppntSchema){
		BOMAppnt appnt = new BOMAppnt();
		ExeSQL tempExeSQL = new ExeSQL();
		try{			
			appnt.setAppntName(tLCAppntSchema.getAppntName());
			appnt.setAppntNo(tLCAppntSchema.getAppntNo());
			appnt.setAppntSex(tLCAppntSchema.getAppntSex());		
			
			appnt.setNationality(tLCAppntSchema.getNationality());
			appnt.setNativePlace(tLCAppntSchema.getNativePlace());
			appnt.setOccupationCode(tLCAppntSchema.getOccupationCode());
			appnt.setOccupationType(tLCAppntSchema.getOccupationType());
			appnt.setPosition(tLCAppntSchema.getPosition());
			appnt.setRelationToInsured(tLCAppntSchema.getRelationToInsured());
			appnt.setRgtAddress(tLCAppntSchema.getRgtAddress());
			appnt.setSalary(new Double(tLCAppntSchema.getSalary()));
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return appnt;
	}
	
	private BOMAgent DealBOMAgent(LAAgentSchema tLAAgentSchema){
		BOMAgent agent = new BOMAgent();
		String tBlackList="select blacklisflag from latree where agentcode='"+"?agentcode?"+"'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tBlackList);
		sqlbv2.put("agentcode", tLAAgentSchema.getAgentCode());
		ExeSQL tempExeSQL = new ExeSQL();
		String tBlackFlag = tempExeSQL.getOneValue(sqlbv2);
		agent.setAgentBlankFlag(tBlackFlag);//黑名单标记
		agent.setAgentCode(tLAAgentSchema.getAgentCode());
		agent.setAgentKind(tLAAgentSchema.getAgentKind());
		agent.setAgentState(tLAAgentSchema.getAgentState());
		agent.setBranchType(tLAAgentSchema.getBranchType());
		agent.setInsideFlag(tLAAgentSchema.getInsideFlag());
		agent.setManageCom(tLAAgentSchema.getManageCom());
		agent.setQuafNo(tLAAgentSchema.getQuafNo());
		agent.setSaleQuaf(tLAAgentSchema.getSaleQuaf());		
		return agent;
	}
	
	private BOMInsured DealBOMInsured(LCInsuredSchema tLCInsuredSchema){
		BOMInsured Insured = new BOMInsured();
		ExeSQL tempExeSQL = new ExeSQL();
		try{
			//参考AutoUWCheckBL.DealBOMInsured
			Insured.setInsuredNo(tLCInsuredSchema.getInsuredNo());
			
			String polApplyDateSql = "select PolApplyDate from lccont where contno='"+"?contno?"+"'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(polApplyDateSql);
			sqlbv3.put("contno", tLCInsuredSchema.getContNo());
			String tpolApplyDate = tempExeSQL.getOneValue(sqlbv3);
			int tInsAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(), tpolApplyDate, "Y");
			Insured.setInsuredAppAge(Double.valueOf(String.valueOf(tInsAge)));//投保年龄
			
			Insured.setInsuredStat(tLCInsuredSchema.getInsuredStat());
			Insured.setMarriage(tLCInsuredSchema.getMarriage());
			Insured.setRelationToAppnt(tLCInsuredSchema.getRelationToAppnt());
			Insured.setSalary(Double.valueOf(String.valueOf(tLCInsuredSchema.getSalary())));
			Insured.setSex(tLCInsuredSchema.getSex());

			String sumMultSql = "select (case when sum( case when mult=0 then 1 else mult end) is null then 0 else sum( case when mult=0 then 1 else mult end) end) from lcpol where insuredno='"+"?insuredno?"+"'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(sumMultSql);
			sqlbv4.put("insuredno", tLCInsuredSchema.getInsuredNo());
			String tsumMult = tempExeSQL.getOneValue(sqlbv4);
			Insured.setSumMult(tsumMult);//累计投保份数

			//既往异常投保/理赔史 OuncommonConTent
			String tOuncommonConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0117','A0118','D0115','D0116','D0117','C0108','A0528','A0529')"
				+" and contno='"+"?contno?"+"' and customerno='"+"?customerno?"+"' ";//and CustomerNoType='1'";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tOuncommonConTent_sql);
			sqlbv5.put("contno", tLCInsuredSchema.getContNo());
			sqlbv5.put("customerno", tLCInsuredSchema.getInsuredNo());
			String tOuncommonConTent = "";
			tOuncommonConTent = tempExeSQL.getOneValue(sqlbv5);
			if(tOuncommonConTent!=null&&!tOuncommonConTent.equals("")&&Integer.parseInt(tOuncommonConTent)>0){
				Insured.setOuncommonConTent("1");
			}else{
				Insured.setOuncommonConTent("0");
			}

		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Insured;
	}
	
	private BOMPol DealBOMPol(LCPolSchema tLCPolSchema){
		BOMPol Pol = new BOMPol();
		ExeSQL tempExeSQL = new ExeSQL();
		try{			
			Pol.setAmnt(Double.valueOf(String.valueOf(tLCPolSchema.getAmnt())));
			Pol.setUWFlag(tLCPolSchema.getUWFlag());
			Pol.setPrem(new Double(tLCPolSchema.getPrem()));
			Pol.setInsuredNo(tLCPolSchema.getInsuredNo());
			Pol.setMainPolNo(tLCPolSchema.getMainPolNo());
			Pol.setMult(new Double(tLCPolSchema.getMult()));
			Pol.setPayYears(new Double(tLCPolSchema.getPayYears()));
			Pol.setPolNo(tLCPolSchema.getPolNo());
			Pol.setInsuYear(Double.valueOf(String.valueOf(tLCPolSchema.getInsuYear())));
			Pol.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
			Pol.setCurrency(tLCPolSchema.getCurrency());
			Pol.setLiveGetMode(tLCPolSchema.getLiveGetMode());
			Pol.setBonusGetMode(tLCPolSchema.getBonusGetMode());
			Pol.setRiskCode(tLCPolSchema.getRiskCode());
			if(!(tLCPolSchema.getCValiDate()==null||"".equals(tLCPolSchema.getCValiDate()))){
				theDate=tLCPolSchema.getCValiDate()+" 00:00:00";
				Pol.setCValiDate(sdf.parse(theDate));
			}
			Pol.setFloatRate(new Double(tLCPolSchema.getFloatRate()));
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Pol;
	}

	/**
	 * 核保险种信息校验,准备核保算法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean CheckKinds(String tFlag) {
		mLMUWSet.clear();

		LMUWSchema tLMUWSchema = new LMUWSchema();

		// 查询算法编码
		if (tFlag.equals("1")) {
			tLMUWSchema.setUWType("13"); // 非标准体
		}

		if (tFlag.equals("2")) {
			tLMUWSchema.setUWType("14"); // 拒保延期
		}

		tLMUWSchema.setRiskCode("000000");
		tLMUWSchema.setRelaPolType("I");

		LMUWDB tLMUWDB = new LMUWDB();
		tLMUWDB.setSchema(tLMUWSchema);

		mLMUWSet = tLMUWDB.query();

		if (tLMUWDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMUWDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "CheckKinds";
			tError.errorMessage = "核保级别校验算法读取失败!";
			this.mErrors.addOneError(tError);

			// mLMUWDBSet.clear();
			return false;
		}

		return true;
	}

	/**
	 * 次标准体校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkStandGrade() {
		CheckKinds("1");

		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();

		tLCUWMasterDB.setProposalNo(mPolNo);

		if (tLCUWMasterDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "checkStandGrade";
			tError.errorMessage = "LCUWMaster表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			tLCUWMasterSchema = tLCUWMasterDB.getSchema();
		}

		// 有特约，加费，保险计划变更为次标准体
		if (!tLCUWMasterSchema.getSpecFlag().equals("0")
				|| !tLCUWMasterSchema.getChangePolFlag().equals("0")) {
			if (mLMUWSet.size() > 0) {
				for (int i = 1; i <= mLMUWSet.size(); i++) {
					LMUWSchema tLMUWSchema = new LMUWSchema();
					tLMUWSchema = mLMUWSet.get(i);

					mCalCode = tLMUWSchema.getCalCode(); // 次标准体计算公式代码

					String tempuwgrade = CheckPol();

					if (tempuwgrade != null) {
						if (mUWPopedom.compareTo(tempuwgrade) < 0) {
							CError tError = new CError();
							tError.moduleName = "UWManuNormGChkBL";
							tError.functionName = "prepareAllPol";
							tError.errorMessage = "无此次标准体投保件核保权限，需要上报上级核保师!";
							this.mErrors.addOneError(tError);

							return false;
						}
					}
				}
			}
		}

		return true;
	}

	/**
	 * 拒保，撤单校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUserGrade() {
		CheckKinds("2");

		if (mLMUWSet.size() > 0) {
			for (int i = 1; i <= mLMUWSet.size(); i++) {
				LMUWSchema tLMUWSchema = new LMUWSchema();
				tLMUWSchema = mLMUWSet.get(i);

				mCalCode = tLMUWSchema.getCalCode(); // 延期拒保计算公式代码

				String tempuwgrade = CheckPol();

				if (tempuwgrade != null) {
					if (mUWPopedom.compareTo(tempuwgrade) < 0) {
						CError tError = new CError();
						tError.moduleName = "UWManuNormGChkBL";
						tError.functionName = "prepareAllPol";
						tError.errorMessage = "无此单拒保，延期权限，需上报上级核保师!";
						this.mErrors.addOneError(tError);

						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * 校验是否有返回业务员问题件
	 * 
	 * @return
	 */
	private boolean checkAgentQuest(LCPolSchema tLCPolSchema) {
		String tsql = "";
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

		tsql = "select * from lcissuepol where proposalno = '"
				+ "?proposalno?"
				+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tsql);
		sqlbv6.put("proposalno", tLCPolSchema.getProposalNo());
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv6);

		if (tLCIssuePolSet.size() > 0) {
			mAgentPrintFlag = "1";
			mAgentQuesFlag = "1";
		}

		return true;
	}

	/**
	 * 校验是否有返回保户问题件
	 * 
	 * @return
	 */
	private boolean checkQuest(LCPolSchema tLCPolSchema) {
		String tsql = "";
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

		tsql = "select * from lcissuepol where proposalno = '"
				+ "?proposalno?"
				+ "' and backobjtype = '3' and replyresult is null and needprint = 'Y'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tsql);
		sqlbv7.put("proposalno", tLCPolSchema.getProposalNo());
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv7);

		if (tLCIssuePolSet.size() > 0) {
			mQuesFlag = "1";
		}

		return true;
	}

	/**
	 * 校验是否有返回机构问题件
	 * 
	 * @return
	 */
	private boolean checkQuestOrg(LCPolSchema tLCPolSchema) {
		String tsql = "";
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

		tsql = "select * from lcissuepol where proposalno = '"
				+ "?proposalno?"
				+ "' and backobjtype = '4' and replyresult is null";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tsql);
		sqlbv8.put("proposalno", tLCPolSchema.getProposalNo());
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv8);

		if (tLCIssuePolSet.size() > 0) {
			mQuesOrgFlag = "1";
		}

		return true;
	}

	/**
	 * 校验是否有返操作员问题件
	 * 
	 * @return
	 */
	private boolean checkQuestOpe(LCPolSchema tLCPolSchema) {
		String tsql = "";
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

		tsql = "select * from lcissuepol where proposalno = '"
				+ "?proposalno?"
				+ "' and backobjtype = '1' and replyresult is null";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tsql);
		sqlbv9.put("proposalno", tLCPolSchema.getProposalNo());
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv9);

		if (tLCIssuePolSet.size() > 0) {
			mQuesOpeFlag = "1";
		}

		return true;
	}

	/**
	 * 准备保单信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePol() {
		logger.debug("preparePol ----- Begin");
		// 拒保退费
		if (mUWFlag.equals("1")) {
			String tSQL = "update lcgrpcont set prem=(select sum(prem) from lcpol "
					+ "where grpcontno='"
					+ "?grpcontno?"
					+ "' and riskcode<>'"
					+ "?riskcode?"
					+ "' and uwflag<>'1')"
					+ ",operator='"
					+ "?operator?"
					+ "',modifydate='"
					+ "?modifydate?"
					+ "',modifytime='"
					+ "?modifytime?"
					+ "' where grpcontno='" + "?grpcontno?" + "'";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tSQL);
			sqlbv10.put("grpcontno", mLCPolSchema.getGrpContNo());
			sqlbv10.put("riskcode", mLCPolSchema.getRiskCode());
			sqlbv10.put("operator", mOperator);
			sqlbv10.put("modifydate", PubFun.getCurrentDate());
			sqlbv10.put("modifytime", PubFun.getCurrentTime());
			mMap.put(sqlbv10, "UPDATE");
		}

		VData tVData = new VData();
		VData tResult = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpContNo", mLCPolSchema.getGrpContNo());
		tTransferData.setNameAndValue("ContNo", mLCPolSchema.getContNo());
		tTransferData.setNameAndValue("RiskCode", mLCPolSchema.getRiskCode());
		tTransferData.setNameAndValue("UWFlag", mUWFlag);
		tTransferData.setNameAndValue("UWIdea", mUWIdea);
		tTransferData.setNameAndValue("ContType", "2");
		tTransferData.setNameAndValue("PostDay", mpostday);
		tTransferData.setNameAndValue("ValiDate", mvalidate);
		tTransferData.setNameAndValue("Reason", mReason);
		tTransferData.setNameAndValue("SpecReason", mSpecReason);
		tTransferData.setNameAndValue("AppGrade", mAppGrade);
		tTransferData.setNameAndValue("UPUWCode", mUPUWCode);
		tVData.add(tTransferData);
		tVData.add(mGlobalInput);

		// 对险种保单下核保结论
		UWManuNormPolChkBL tUWManuNormPolChkBL = new UWManuNormPolChkBL();
		if (!tUWManuNormPolChkBL.submitData(tVData, "")) {
			this.mErrors.copyAllErrors(tUWManuNormPolChkBL.mErrors);
			return false;
		}
		tResult = tUWManuNormPolChkBL.getResult();
		mMap.add((MMap) tResult.getObjectByObjectName("MMap", 0));

		logger.debug("tUWManuNormPolChkBL ----- End");

		// 对保单下核保结论
		UWManuNormContChkBL tUWManuNormContChkBL = new UWManuNormContChkBL();
		if (!tUWManuNormContChkBL.submitData(tVData, "")) {
			this.mErrors.copyAllErrors(tUWManuNormContChkBL.mErrors);
			return false;
		}
		tResult = tUWManuNormContChkBL.getResult();
		mMap.add((MMap) tResult.getObjectByObjectName("MMap", 0));

		logger.debug("mMap..Size=" + mMap.toString());

		return true;
	}

	/**
	 * 准备核保信息 输出：如果发生错误则返回false,否则返回true
	 */

	/*
	 * private boolean CondAccept() { int n; //int i =0; int max; String sql;
	 * String specno = "";
	 * 
	 * logger.debug("--dealData-dealOneData-CondAccept---");
	 * 
	 * //形成特约表信息 //n=0; n = mLCSpecSet.size(); logger.debug(" 特约条数: "+n);
	 * if (n> 0) { LCSpecSchema tLCSpecSchema = new LCSpecSchema(); //
	 * tLCSpecSchema.setSpecNo(PubFun1.CreateMaxNo("SpecNo",PubFun.getNoLimit(mGlobalInput.ComCode)));
	 * tLCSpecSchema.setPolNo(mPolNo); // tLCSpecSchema.setPolType("1");
	 * tLCSpecSchema.setEndorsementNo("");
	 * tLCSpecSchema.setSpecType(mLCSpecSet.get(1).getSpecType());
	 * tLCSpecSchema.setSpecCode(mLCSpecSet.get(1).getSpecCode());
	 * tLCSpecSchema.setSpecContent(mLCSpecSet.get(1).getSpecContent());
	 * tLCSpecSchema.setPrtFlag("1"); tLCSpecSchema.setBackupType("");
	 * tLCSpecSchema.setOperator(mOperator);
	 * tLCSpecSchema.setMakeDate(PubFun.getCurrentDate());
	 * tLCSpecSchema.setMakeTime(PubFun.getCurrentTime());
	 * tLCSpecSchema.setModifyDate(PubFun.getCurrentDate());
	 * tLCSpecSchema.setModifyTime(PubFun.getCurrentTime());
	 * 
	 * LCSpecDB tLCSpecDB = new LCSpecDB(); LCSpecSet tLCSpecSet = new
	 * LCSpecSet(); LBSpecSet tLBSpecSet = new LBSpecSet();
	 * logger.debug("mPolNo:"+mPolNo) ; tLCSpecDB.setPolNo(mPolNo) ;
	 * tLCSpecSet = tLCSpecDB.query() ;
	 * logger.debug("tLCSpecSet.size()"+tLCSpecSet.size()) ; for(int i=1 ;
	 * i<= tLCSpecSet.size(); i++) { LBSpecSchema tLBSpecSchema = new
	 * LBSpecSchema(); mReflections.transFields(tLBSpecSchema,tLCSpecSet.get(i) );
	 * 
	 * tLBSpecSchema.setModifyDate(PubFun.getCurrentDate()) ;
	 * tLBSpecSchema.setModifyTime(PubFun.getCurrentTime()) ;
	 * tLBSpecSet.add(tLBSpecSchema) ; }
	 * 
	 * logger.debug("tLBSpecSet.size()"+tLBSpecSet.size()) ;
	 * mLCSpecSet.clear(); mLBSpecSet.clear() ; mLBSpecSet.set(tLBSpecSet);
	 * mLCSpecSet.add(tLCSpecSchema);
	 * logger.debug("mLBSpecSet.size()"+mLBSpecSet.size()) ; }
	 * 
	 * //形成特约加费信息 n = mLCPremSet.size(); logger.debug(" 特约加费条数: "+n); if(
	 * n > 0) { //取责任信息 LCDutyDB tLCDutyDB = new LCDutyDB();
	 * tLCDutyDB.setPolNo(mLCPolSchema.getPolNo()); mLCDutySet =
	 * tLCDutyDB.query();
	 * 
	 * //更新责任项 if(mLCDutySet.size() > 0) { for(int m = 1;m <=
	 * mLCDutySet.size();m++) { int maxno = 0; LCDutySchema tLCDutySchema = new
	 * LCDutySchema(); tLCDutySchema = mLCDutySet.get(m);
	 * 
	 * //减去该责任的原加费金额 sql = "select * from LCPrem where payplancode like
	 * '000000%' and polno = '"+mLCPolSchema.getPolNo().trim()+"' and dutycode =
	 * '"+tLCDutySchema.getDutyCode().trim()+"'"; LCPremDB t2LCPremDB = new
	 * LCPremDB(); LCPremSet t2LCPremSet = new LCPremSet();
	 * 
	 * t2LCPremSet = t2LCPremDB.executeQuery(sql);
	 * 
	 * if(t2LCPremSet.size() > 0) { for(int j = 1;j<=t2LCPremSet.size();j++) {
	 * LCPremSchema t2LCPremSchema = new LCPremSchema(); t2LCPremSchema =
	 * t2LCPremSet.get(j);
	 * 
	 * tLCDutySchema.setPrem(tLCDutySchema.getPrem()-t2LCPremSchema.getPrem());
	 * mLCPolSchema.setPrem(mLCPolSchema.getPrem()-t2LCPremSchema.getPrem()); } }
	 * 
	 * //为投保单表和责任表加上本次的特约加费.同时形成特约加费信息 for (int i = 1;i<= n;i++) { LCPremSchema
	 * ttLCPremSchema = new LCPremSchema(); ttLCPremSchema = mLCPremSet.get(i);
	 * LCPremSchema tLCPremSchema = new LCPremSchema(); double tPrem;
	 * 
	 * if(ttLCPremSchema.getDutyCode().equals(tLCDutySchema.getDutyCode())) {
	 * maxno = maxno + 1; LCPremDB tLCPremDB = new LCPremDB();
	 * 
	 * tLCPremDB.setPolNo(mLCPolSchema.getPolNo());
	 * //tLCPremDB.setDutyCode(tLCPremSchema.getDutyCode());//原whn的
	 * tLCPremDB.setDutyCode(tLCDutySchema.getDutyCode());//SXY添加的 LCPremSet
	 * tLCPremSet = tLCPremDB.query(); tLCPremSchema = tLCPremSet.get(1);
	 * 
	 * String PayPlanCode = ""; PayPlanCode = String.valueOf(maxno); for (int j =
	 * PayPlanCode.length();j<8;j++) { PayPlanCode = "0"+PayPlanCode; }
	 * 
	 * logger.debug("payplancode"+PayPlanCode);
	 * 
	 * //保单总保费 tPrem = mLCPolSchema.getPrem() + ttLCPremSchema.getPrem();
	 * 
	 * //tLCPremSchema.setPolNo(mLCPolSchema.getPolNo());//以下注销处表明其信息是沿用已前该责任加费信息
	 * //tLCPremSchema.setDutyCode(mmaxDutyCode);
	 * tLCPremSchema.setPayPlanCode(PayPlanCode);
	 * //tLCPremSchema.setGrpPolNo(mLCPolSchema.get);
	 * tLCPremSchema.setPayPlanType(ttLCPremSchema.getPayPlanType());
	 * //tLCPremSchema.setPayTimes(); //tLCPremSchema.setPayIntv();
	 * //tLCPremSchema.setMult();
	 * tLCPremSchema.setStandPrem(ttLCPremSchema.getPrem());
	 * tLCPremSchema.setPrem(ttLCPremSchema.getPrem());
	 * //tLCPremSchema.setSumPrem(); //tLCPremSchema.setRate();
	 * tLCPremSchema.setPayStartDate(ttLCPremSchema.getPayStartDate());
	 * tLCPremSchema.setPayEndDate(ttLCPremSchema.getPayEndDate());
	 * //tLCPremSchema.setPaytoDate(); tLCPremSchema.setState("1");//承保加费标示为2
	 * modify by sxy at 2004=-03-11 //tLCPremSchema.setBankCode();
	 * //tLCPremSchema.setBankAccNo(); //tLCPremSchema.setAppntNo();
	 * //tLCPremSchema.setAppntType("1"); //投保人类型
	 * tLCPremSchema.setModifyDate(PubFun.getCurrentDate());
	 * tLCPremSchema.setModifyTime(PubFun.getCurrentTime());
	 * 
	 * //更新保险责任 mLCDutySet.remove(tLCDutySchema);
	 * tLCDutySchema.setPrem(tLCDutySchema.getPrem()+tLCPremSchema.getPrem());
	 * mLCDutySet.add(tLCDutySchema);
	 * 
	 * mmLCPremSet.add(tLCPremSchema);
	 * 
	 * //更新保单数据 mLCPolSchema.setPrem(tPrem);
	 *  }
	 *  }
	 *  } }
	 *  } return true; }
	 */

	// commented by zhr 2004.11
	/**
	 * 检查是不是需要送核保通知书到打印队列
	 * 
	 * @return
	 */
	private String checkBackOperator(String tPrintFlag) {
		LCPolSet tLCPolSet = new LCPolSet();

		for (int i = 1; i <= mLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();

			tLCPolSchema = mLCPolSet.get(i);

			// 有返回保户需要打印
			// String tsql = "select * from lcpol where ProposalNo in ( select
			// ProposalNo from LCIssuePol where ((makedate >= (select
			// max(makedate) from lcissuepol where backobjtype in ('1','4') and
			// ProposalNo = '"+tLCPolSchema.getPolNo()+"' and makedate is not
			// null)) or ((select max(makedate) from lcissuepol where
			// backobjtype in ('1','4') and ProposalNo =
			// '"+tLCPolSchema.getPolNo()+"') is null))"
			String tsql = "select * from lcpol where  ProposalNo in ( select ProposalNo from LCIssuePol where 1 = 1 "
					+ " and backobjtype  = '3'"
					+ " and ProposalNo = '"
					+ "?ProposalNo?"
					+ "'"
					+ " and makedate is not null"
					+ " and replyresult is null"
					+ " and needprint = 'Y')";
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tsql);
			sqlbv11.put("ProposalNo", tLCPolSchema.getPolNo());
			logger.debug("printchecksql:" + tsql);

			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet t2LCPolSet = new LCPolSet();
			t2LCPolSet = tLCPolDB.executeQuery(sqlbv11);

			if (t2LCPolSet.size() > 0) {
				tPrintFlag = "2";
			}

			// 只返回给操作员,机构不打印
			// tsql = "select * from lcpol where ProposalNo in ( select
			// ProposalNo from LCIssuePol where ((makedate >= (select
			// max(makedate) from lcissuepol where backobjtype in ('2','3') and
			// ProposalNo = '"+tLCPolSchema.getPolNo()+"' and makedate is not
			// null)) or ((select max(makedate) from lcissuepol where
			// backobjtype in ('3','2') and ProposalNo =
			// '"+tLCPolSchema.getPolNo()+"') is null))"
			tsql = "select * from lcpol where  ProposalNo in ( select ProposalNo from LCIssuePol where 1 = 1 "
					+ " and backobjtype = '1'"
					+ " and ProposalNo = '"
					+ "?ProposalNo?"
					+ "'"
					+ " and makedate is not null"
					+ " and replyresult is null)"
					+ " and ProposalNo not in ( select ProposalNo from LCIssuePol where 1 = 1 "
					+ " and backobjtype in ('2','3')"
					+ " and ProposalNo = '"
					+ "?ProposalNo?"
					+ "'"
					+ " and makedate is not null"
					+ " and replyresult is null"
					+ " and needprint = 'Y')"
					+ " and ProposalNo not in ( select ProposalNo from LCIssuePol where 1 = 1 "
					+ " and backobjtype = '4'"
					+ " and ProposalNo = '"
					+ "?ProposalNo?"
					+ "'"
					+ " and makedate is not null" + " and replyresult is null)";
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(tsql);
			sqlbv12.put("ProposalNo", tLCPolSchema.getPolNo());
			logger.debug("printchecksql2:" + tsql);
			tLCPolDB = new LCPolDB();
			t2LCPolSet = new LCPolSet();

			t2LCPolSet = tLCPolDB.executeQuery(sqlbv12);

			if (t2LCPolSet.size() > 0) {
				// 复核标记
				tLCPolSchema.setApproveFlag("1");
			}

			tLCPolSet.add(tLCPolSchema);
		}

		mLCPolSet.clear();
		mLCPolSet.add(tLCPolSet);

		if (tPrintFlag.equals("2")) {
			tPrintFlag = "0";
		} else {
			tPrintFlag = "1";
		}

		return tPrintFlag;
	}

	/**
	 * 打印信息表
	 * 
	 * @return
	 */
	private boolean print() {
		String tIfPrintFlag = "0";

		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		tLOPRTManagerSchema.setOtherNo(mPolNo);
		logger.debug("polno:" + mPolNo);
		tLOPRTManagerSchema.setManageCom(mLCPolSchema.getManageCom());
		tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
		tLOPRTManagerSchema.setAgentCode(mLCPolSchema.getAgentCode());
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL);

		if (mUWFlag.equals("1")) { // 拒保通知书
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_DECLINE);
			tLOPRTManagerSchema.setOtherNo(mGetNoticeNo);
			tLOPRTManagerSchema.setStandbyFlag1(mPolNo);
		}

		if (mUWFlag.equals("2")) { // 延期通知书
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_DEFER);
			tLOPRTManagerSchema.setOtherNo(mGetNoticeNo);
			tLOPRTManagerSchema.setStandbyFlag1(mPolNo);
		}

		if (mUWFlag.equals("8")) { // 核保通知书
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_UW);

			// 只有问题件的时候校验
			tIfPrintFlag = "0";

			if (mBackFlag.equals("1")) {
				tIfPrintFlag = checkBackOperator(tIfPrintFlag);
			}
		}

		if (mUWFlag.equals("a")) { // 撤单
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_WITHDRAW);
			tLOPRTManagerSchema.setOtherNo(mGetNoticeNo);
			tLOPRTManagerSchema.setStandbyFlag1(mPolNo);
		}

		VData tVData = new VData();
		PrintManagerBL tPrintManagerBL = new PrintManagerBL();

		tVData.add(tLOPRTManagerSchema);
		tVData.add(mGlobalInput);

		logger.debug("Start PrintManagerBL Submit...");

		if (tIfPrintFlag.equals("0")) { // 只返回给操作员问题件无需发核保通知书
			if (!tPrintManagerBL.submitData(tVData, "REQUEST")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPrintManagerBL.mErrors);

				CError tError = new CError();
				tError.moduleName = "UWManuNormGChkBL";
				tError.functionName = "print";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);

				return false;
			}
		}

		mLOPRTManagerSet.add(tLOPRTManagerSchema);

		return true;
	}

	/**
	 * 返回业务员问题件件送打印队列
	 * 
	 * @return
	 */
	private boolean printAgent() {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		tLOPRTManagerSchema.setOtherNo(mPolNo);
		tLOPRTManagerSchema.setManageCom(mLCPolSchema.getManageCom());
		tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
		tLOPRTManagerSchema.setAgentCode(mLCPolSchema.getAgentCode());
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL);
		tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_AGEN_QUEST);

		VData tVData = new VData();
		PrintManagerBL tPrintManagerBL = new PrintManagerBL();

		tVData.add(tLOPRTManagerSchema);
		tVData.add(mGlobalInput);

		logger.debug("Start PrintManagerBL Submit...");

		if (!tPrintManagerBL.submitData(tVData, "REQUEST")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPrintManagerBL.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "print";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 延期承保 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean TimeAccept() {
		return true;
	}

	/**
	 * 待上级核保 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean uplevel() {
		LCUWErrorDB tLCUWErrorDB = new LCUWErrorDB();
		tLCUWErrorDB.setPolNo(mLCPolSchema.getPolNo());

		String tpolno = mLCPolSchema.getPolNo();
		String tsql = "select * from lcuwerror where polno = '"
				+ "?polno?"
				+ "' and uwno = (select max(uwno) from lcuwerror where polno = '"
				+ "?polno?" + "')";
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql(tsql);
		sqlbv13.put("polno", tpolno.trim());
		LCUWErrorSet tLCUWErrorSet = tLCUWErrorDB.executeQuery(sqlbv13);
		String tcurrgrade = "A";
		String terrgrade = "";
		logger.debug(" in uplevel()");

		int errno = tLCUWErrorSet.size();
		int j = 0;

		if (errno > 0) {
			for (int i = 1; i <= errno; i++) {
				LCUWErrorSchema tLCUWErrorSchema = new LCUWErrorSchema();
				tLCUWErrorSchema = tLCUWErrorSet.get(i);
				terrgrade = tLCUWErrorSchema.getUWGrade();
				logger.debug("上报级别:terrgrade" + terrgrade);

				if ((j == 0) && (mUWPopedom.compareTo(terrgrade) < 0)) {
					j++;
					tcurrgrade = terrgrade;
				} else {
					if ((mUWPopedom.compareTo(terrgrade) < 0)
							&& (terrgrade.compareTo(tcurrgrade) > 0)) {
						tcurrgrade = terrgrade;
					}
				}

				logger.debug("上报级别:tcurrgrade" + tcurrgrade);
			}

			mAppGrade = tcurrgrade;
		}

		logger.debug("上报级别:mAppGrade" + mAppGrade);

		if ((errno == 0)
				|| ((mUWPopedom.compareTo(mAppGrade) >= 0) && (mUWPopedom
						.compareTo("L") < 0))) {
			char[] temp;
			char tempgrade;
			temp = mUWPopedom.toCharArray();
			tempgrade = (char) ((int) temp[0] + 1);
			logger.debug("上报级别:" + tempgrade);
			mAppGrade = String.valueOf(tempgrade);
		}

		// 指定上报级别
		logger.debug("上报级别:mUPUWCode+mOperator" + mUPUWCode + mOperator);

		if ((mUPUWCode != null) && !mUPUWCode.equals(mOperator)) {
			LDUserDB tLDUserDB = new LDUserDB();
			tsql = "select * from lduser where usercode = '" + "?usercode?" + "'";
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(tsql);
			sqlbv14.put("usercode", mUPUWCode);
			LDUserSet tLDUserSet = tLDUserDB.executeQuery(sqlbv14);

			if (tLDUserSet.size() != 1) {
				CError tError = new CError();
				tError.moduleName = "UWManuNormGChkBL";
				tError.functionName = "uplever";
				tError.errorMessage = "指定核保师信息有误!";
				this.mErrors.addOneError(tError);

				return false;
			} else {
				logger.debug("上报级别:mAppGrade+tLDUserSet.get(1).getUWPopedom()"
								+ mAppGrade + tLDUserSet.get(1).getUWPopedom());

				if (mAppGrade.compareTo(tLDUserSet.get(1).getUWPopedom()) > 0) {
					CError tError = new CError();
					tError.moduleName = "UWManuNormGChkBL";
					tError.functionName = "uplever";
					tError.errorMessage = "指定核保师级别太底!";
					this.mErrors.addOneError(tError);

					return false;
				} else {
					mAppGrade = tLDUserSet.get(1).getUWPopedom();
				}
			}
		}

		logger.debug("上报级别:mAppGrade" + mAppGrade);

		// 撤销核保申请锁
		mLDSysTraceSchema.setPolNo(mPolNo);
		mLDSysTraceSchema.setOperator(mOperator);
		mLDSysTraceSchema.setPolState(1001);

		mLDSysTraceSet.add(mLDSysTraceSchema);

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		// mMap.put(mAllLCPolSet, "UPDATE");
		// mMap.put(mAllLCContSet, "UPDATE");
		// mMap.put(mAllLCGrpContSet, "UPDATE");
		// mMap.put(mAllLCUWMasterSet, "DELETE&INSERT");
		// mMap.put(mAllLCUWSubSet, "INSERT");

		// if (mChgContMaster) {
		// mMap.put(mAllLCCUWMasterSet, "DELETE&INSERT");
		// mMap.put(mAllLCCUWSubSet, "INSERT");
		// }

		mResult.clear();
		mResult.add(mMap);
	}
}
