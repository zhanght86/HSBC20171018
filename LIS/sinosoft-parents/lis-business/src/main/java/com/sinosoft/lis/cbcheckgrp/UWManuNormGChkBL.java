package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWErrorDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDUWGradePersonDB;
import com.sinosoft.lis.db.LDUWUserDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LMUWDB;
import com.sinosoft.lis.f1printgrp.PrintManagerBL;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWErrorSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
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
import com.sinosoft.lis.vschema.LDUWGradePersonSet;
import com.sinosoft.lis.vschema.LDUWUserSet;
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
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保个人人工核保部分
 * </p>
 * 
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

	private String riskcode = "";
	private String specFlag = "";

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
		// String tLimit = PubFun.getNoLimit(mManageCom);
		// mGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit); //产生即付通知书号
		// logger.debug("---tLimit---" + tLimit);

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
		// if (mUWFlag.equals("1") || mUWFlag.equals("2") || mUWFlag.equals("4") ||
		// mUWFlag.equals("9"))
		// {
		// //次标准体校验核保员级别
		// if (!checkStandGrade())
		// {
		// return false;
		// }
		//
		// if (!checkBackPol())
		// {
		// return false;
		// }
		// }
		// 拒保或延期要校验核保员级别
		// if (mUWFlag.equals("1") || mUWFlag.equals("2"))
		// {
		// if (!checkUserGrade())
		// {
		// return false;
		// }
		// }
		// 校验主附险
		// logger.debug("校验主附险" + mUWFlag);
		// if (!mUWFlag.equals("1") && !mUWFlag.equals("2") && !mUWFlag.equals("3") &&
		// !mUWFlag.equals("6") && !mUWFlag.equals("8") && !mUWFlag.equals("a") &&
		// !mUWFlag.equals("b") && !mUWFlag.equals("z"))
		// {
		// if (!checkMain())
		// {
		// return false;
		// }
		// }
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
	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("IN dealData()" + mUWFlag);

		// if(!mUWFlag.equals("1")&&!mUWFlag.equals("2")&&!mUWFlag.equals("8"))
		// {
		logger.debug("begin dealOnePol()");

		if (dealOnePol() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {
		// 保单
		if (preparePol() == false) {
			return false;
		}

		// 核保信息
		if (prepareUW() == false) {
			return false;
		}

		/*
		 * if (mUWFlag.equals("3")) { CondAccept();
		 * 
		 * LBSpecSet tLBSpecSet = new LBSpecSet(); tLBSpecSet.set(mLBSpecSet);
		 * mAllLBSpecSet.add(mLBSpecSet) ;
		 * 
		 * LCPremSet tLCPremSet = new LCPremSet(); tLCPremSet.set( mmLCPremSet );
		 * mAllLCPremSet.add( tLCPremSet );
		 * 
		 * LCSpecSet tLCSpecSet = new LCSpecSet(); tLCSpecSet.set( mLCSpecSet );
		 * mAllLCSpecSet.add( tLCSpecSet );
		 * 
		 * LCDutySet tLCDutySet = new LCDutySet(); tLCDutySet.set(mLCDutySet);
		 * mAllLCDutySet.add(tLCDutySet); }
		 */
		// commented by zhr 2004.11
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		tLCGrpContSet.set(mLCGrpContSet);
		mAllLCGrpContSet.add(tLCGrpContSet);

		LCContSet tLCContSet = new LCContSet();
		tLCContSet.set(mLCContSet);
		mAllLCContSet.add(tLCContSet);

		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet.set(mLCPolSet);
		mAllLCPolSet.add(tLCPolSet);

		if (mChgContMaster) {
			LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
			tLCCUWMasterSet.set(mLCCUWMasterSet);
			mAllLCCUWMasterSet.add(tLCCUWMasterSet);

			LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
			tLCCUWSubSet.set(mLCCUWSubSet);
			mAllLCCUWSubSet.add(tLCCUWSubSet);
		}

		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		tLCUWMasterSet.set(mLCUWMasterSet);
		mAllLCUWMasterSet.add(tLCUWMasterSet);

		LCUWSubSet tLCUWSubSet = new LCUWSubSet();
		tLCUWSubSet.set(mLCUWSubSet);
		mAllLCUWSubSet.add(tLCUWSubSet);

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

			riskcode = tLCPolSchema.getRiskCode();
			specFlag = tLCPolSchema.getStandbyFlag3();// 批量通过标志 =5

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

		LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		String sql = "select * From lduwuser where usercode='" + mOperator
				+ "' and uwtype='2'";
		LDUWUserSet tLDUWUserSet = new LDUWUserSet();
		tLDUWUserSet = tLDUWUserDB.executeQuery(sql);

		if (tLDUWUserSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "checkData";
			tError.errorMessage = "核保师:" + mOperator + "没有进行核保权限配置!";
			this.mErrors.addOneError(tError);
			return false;

		}
		if (specFlag != null && specFlag.equals("5")) {
			logger.debug("采用了批量通过！");
			return true;
		}

		String uwpopedom = tLDUWUserSet.get(1).getUWPopedom();
		LDUWGradePersonDB tLDUWGradePersonDB = new LDUWGradePersonDB();
		sql = "select * From lduwgradeperson where uwpopedom='" + uwpopedom+"' "
				//+ "' and uwkind='10' " 
				+ "and uwtype='2'";
		LDUWGradePersonSet tLDUWGradePersonSet = new LDUWGradePersonSet();
		tLDUWGradePersonSet = tLDUWGradePersonDB.executeQuery(sql);
		if (tLDUWGradePersonSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "checkData";
			tError.errorMessage = "数据库描述数据不全！";
			this.mErrors.addOneError(tError);
			return false;

		}
		//当前团险lduwgradeperson表中的standflag字段没有值 modify by liuqh 2009-01-05
//		if (mUWFlag.equals("1")
//				&& tLDUWGradePersonSet.get(1).getStandFlag().equals("N")) {
//			CError tError = new CError();
//			tError.moduleName = "UWManuNormChkBL";
//			tError.functionName = "checkData";
//			tError.errorMessage = "该核保员没有拒保的权限！";
//			this.mErrors.addOneError(tError);
//			return false;
//
//		}
//
//		if (mUWFlag.equals("2")
//				&& tLDUWGradePersonSet.get(1).getDelayFlag().equals("N")) {
//			CError tError = new CError();
//			tError.moduleName = "UWManuNormChkBL";
//			tError.functionName = "checkData";
//			tError.errorMessage = "该核保员没有延期的权限！";
//			this.mErrors.addOneError(tError);
//			return false;
//
//		}
		if (mUWFlag.equals("4")) {
			logger.debug("集体合同号=" + mGrpContNo);
			ExeSQL exeSql = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = exeSql
					.execSQL("select sum(prem) from lcprem where grpcontno='"
							+ mGrpContNo
							+ "' and polno='"
							+ mPolNo
							+ "' and PayPlanType!='0' and dutycode in (select dutycode from lmriskduty where riskcode='"
							+ riskcode + "') group by grpcontno");
			if (tSSRS.MaxRow == 0) {

				CError tError = new CError();
				tError.moduleName = "UWManuNormChkBL";
				tError.functionName = "checkData";
				tError.errorMessage = "请选择正常承保！";
				this.mErrors.addOneError(tError);
				return false;

			}
			// ********校验通融级别

			tSSRS = exeSql
					.execSQL("select sum(prem) from lcprem where grpcontno='"
							+ mGrpContNo
							+ "' and PayPlanType!='0' group by grpcontno");
			if (tSSRS.MaxRow > 0
					&& parseFloat(tSSRS.GetText(1, 1)) > tLDUWGradePersonSet
							.get(1).getMaxDieAmnt()) {

				CError tError = new CError();
				tError.moduleName = "UWManuNormChkBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该核保员的通融承保级别不够,请上报！";
				this.mErrors.addOneError(tError);
				return false;

			}

		}
		if (mUWFlag.equals("9")) {

			logger.debug("集体合同号=" + mGrpContNo);
			ExeSQL exeSql = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = exeSql
					.execSQL("select sum(prem) from lcprem where grpcontno='"
							+ mGrpContNo
							+ "' and polno='"
							+ mPolNo
							+ "' and PayPlanType!='0' and dutycode in (select dutycode from lmriskduty where riskcode='"
							+ riskcode + "') group by grpcontno");
			if (tSSRS.MaxRow > 0) {
				CError tError = new CError();
				tError.moduleName = "UWManuNormChkBL";
				tError.functionName = "checkData";
				tError.errorMessage = "已经有加费，只能进行通融承保！";
				this.mErrors.addOneError(tError);
				return false;

			}

		}

		// *********************************************//

		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setPolNo(mLCPolSchema.getPolNo());
		tLCUWMasterDB.setProposalNo(mLCPolSchema.getPolNo());

		if (!tLCUWMasterDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "没有核保信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}
		// else
		// {
		// String tappgrade = tLCUWMasterDB.getAppGrade();
		//
		// if ((tappgrade == null) || tappgrade.equals(""))
		// {
		// CError tError = new CError();
		// tError.moduleName = "UWManuNormGChkBL";
		// tError.functionName = "checkUWGrade";
		// tError.errorMessage = "该投保单无核保级别，不能核保!";
		// this.mErrors.addOneError(tError);
		//
		// return false;
		// }
		//
		// }

		return true;
	}

	private float parseFloat(String s) {
		float f1 = 0;
		String tmp = "";
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
					|| s1.equals("9") || s1.equals(".")) {
				tmp = tmp + s1;
			} else if (tmp.length() > 0) {
				break;
			}
		}
		f1 = Float.parseFloat(tmp);
		// logger.debug("tmp:"+tmp+" f1:"+f1);
		return f1;
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
					+ mLCPolSchema.getPolNo() + "' and mainpolno <> polno";
			logger.debug("附险查询:" + tsql);

			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();

			tLCPolSet = tLCPolDB.executeQuery(tsql);

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
				+ tLCPolSchema.getProposalNo()
				+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'";

		tLCIssuePolSet = tLCIssuePolDB.executeQuery(tsql);

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
				+ tLCPolSchema.getProposalNo()
				+ "' and backobjtype = '3' and replyresult is null and needprint = 'Y'";

		tLCIssuePolSet = tLCIssuePolDB.executeQuery(tsql);

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
				+ tLCPolSchema.getProposalNo()
				+ "' and backobjtype = '4' and replyresult is null";

		tLCIssuePolSet = tLCIssuePolDB.executeQuery(tsql);

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
				+ tLCPolSchema.getProposalNo()
				+ "' and backobjtype = '1' and replyresult is null";

		tLCIssuePolSet = tLCIssuePolDB.executeQuery(tsql);

		if (tLCIssuePolSet.size() > 0) {
			mQuesOpeFlag = "1";
		}

		return true;
	}

	/**
	 * 准备保单信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePol() {
		mLCPolSchema.setUWFlag(mUWFlag);
		mLCPolSchema.setUWCode(mOperator);
		mLCPolSchema.setUWDate(PubFun.getCurrentDate());
		mLCPolSchema.setUWTime(PubFun.getCurrentTime());
		mLCPolSchema.setOperator(mOperator);
		mLCPolSchema.setModifyDate(PubFun.getCurrentDate());
		mLCPolSchema.setModifyTime(PubFun.getCurrentTime());
		mLCPolSchema.setUWDate(PubFun.getCurrentDate());

		mLCPolSet.clear();
		mLCPolSet.add(mLCPolSchema);

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

		mLCContSchema = tLCContSet.get(1);
		mLCContSet.clear();
		mLCContSet.add(mLCContSchema);

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

		mLCGrpContSchema = tLCGrpContSet.get(1);
		mLCGrpContSet.clear();
		mLCGrpContSet.add(mLCGrpContSchema);

		// 拒保退费
		if (mUWFlag.equals("1")) {
			mLCContSchema.setPrem(mLCContSchema.getPrem()
					- mLCPolSchema.getPrem());
			mLCGrpContSchema.setPrem(mLCGrpContSchema.getPrem()
					- mLCPolSchema.getPrem());
			mLCContSchema.setOperator(mOperator);
			mLCContSchema.setModifyDate(PubFun.getCurrentDate());
			mLCContSchema.setModifyTime(PubFun.getCurrentTime());
			mLCGrpContSchema.setOperator(mOperator);
			mLCGrpContSchema.setModifyDate(PubFun.getCurrentDate());
			mLCGrpContSchema.setModifyTime(PubFun.getCurrentTime());
		}

		return true;
	}

	/**
	 * 准备核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareUW() {
		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setProposalNo(mPolNo);

		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		tLCUWMasterSet = tLCUWMasterDB.query();

		if (tLCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWMaster表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int n = tLCUWMasterSet.size();
		logger.debug("该投保单的核保主表记录条数:   " + n);

		if (n == 1) {
			tLCUWMasterSchema = tLCUWMasterSet.get(1);
			// 人工核保后uwno加一
			int uwno = tLCUWMasterSet.get(1).getUWNo();
			uwno++;
			tLCUWMasterSchema.setUWNo(uwno);

			tLCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCUWMasterSchema.setState(mUWFlag);
			tLCUWMasterSchema.setUWIdea(mUWIdea);
			tLCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保

			// tLCUWMasterSchema.setUWGrade(mUWPopedom);
			tLCUWMasterSchema.setAppGrade(mAppGrade);

			// if (mUWFlag.equals("6"))
			// {
			// tLCUWMasterSchema.setOperator(mUPUWCode); //上报核保指定核保师功能的实现借助将当前核保师改为待核保师方式实现
			// }
			// else
			// {
			tLCUWMasterSchema.setOperator(mOperator);
			// }
			// 操作员

			tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

			// 延期
			if (mUWFlag.equals("2")) // 此代码冗余
			{
				tLCUWMasterSchema.setPostponeDay(mpostday);
				tLCUWMasterSchema.setPostponeDate(mvalidate);
			}

			// 条件承保
			if (mUWFlag.equals("4")) {
				tLCUWMasterSchema.setSpecReason(mSpecReason); // 特约原因
				tLCUWMasterSchema.setAddPremReason(mReason);
			}

			// if (mUWFlag.equals("3"))
			// {
			// if (tLCUWMasterSchema.getPrintFlag().equals("1"))
			// {
			// CError tError = new CError();
			// tError.moduleName = "UWManuNormGChkBL";
			// tError.functionName = "prepareUW";
			// tError.errorMessage = "已经发核保通知不可录入!";
			// this.mErrors.addOneError(tError);
			//
			// return false;
			// }
			//
			// tLCUWMasterSchema.setSpecFlag("1");
			// }
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWMaster表取数据不唯一!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCUWMasterSet.clear();
		mLCUWMasterSet.add(tLCUWMasterSchema);

		// 核保轨迹表
		LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
		LCUWSubDB tLCUWSubDB = new LCUWSubDB();
		tLCUWSubDB.setProposalNo(mPolNo);

		LCUWSubSet tLCUWSubSet = new LCUWSubSet();
		tLCUWSubSet = tLCUWSubDB.query();

		if (tLCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int m = tLCUWSubSet.size();
		logger.debug("subcount=" + m);

		if (m > 0) {
			m++; // 核保次数
			tLCUWSubSchema = new LCUWSubSchema();
			tLCUWSubSchema.setUWNo(m); // 第几次核保
			tLCUWSubSchema.setContNo(mContNo);
			tLCUWSubSchema.setPolNo(mPolNo);
			tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
			tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema
					.getProposalContNo());
			tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo());
			tLCUWSubSchema.setInsuredNo(tLCUWMasterSchema.getInsuredNo());
			tLCUWSubSchema.setInsuredName(tLCUWMasterSchema.getInsuredName());
			tLCUWSubSchema.setAppntNo(tLCUWMasterSchema.getAppntNo());
			tLCUWSubSchema.setAppntName(tLCUWMasterSchema.getAppntName());
			tLCUWSubSchema.setAgentCode(tLCUWMasterSchema.getAgentCode());
			tLCUWSubSchema.setAgentGroup(tLCUWMasterSchema.getAgentGroup());
			tLCUWSubSchema.setUWGrade(tLCUWMasterSchema.getUWGrade()); // 核保级别
			tLCUWSubSchema.setAppGrade(tLCUWMasterSchema.getAppGrade()); // 申请级别
			tLCUWSubSchema.setAutoUWFlag(tLCUWMasterSchema.getAutoUWFlag());
			tLCUWSubSchema.setState(tLCUWMasterSchema.getState());
			tLCUWSubSchema.setPassFlag(tLCUWMasterSchema.getState());
			tLCUWSubSchema.setPostponeDay(tLCUWMasterSchema.getPostponeDay());
			tLCUWSubSchema.setPostponeDate(tLCUWMasterSchema.getPostponeDate());
			tLCUWSubSchema.setUpReportContent(tLCUWMasterSchema
					.getUpReportContent());
			tLCUWSubSchema.setHealthFlag(tLCUWMasterSchema.getHealthFlag());
			tLCUWSubSchema.setSpecFlag(tLCUWMasterSchema.getSpecFlag());
			tLCUWSubSchema.setSpecReason(tLCUWMasterSchema.getSpecReason());
			tLCUWSubSchema.setQuesFlag(tLCUWMasterSchema.getQuesFlag());
			tLCUWSubSchema.setReportFlag(tLCUWMasterSchema.getReportFlag());
			tLCUWSubSchema.setChangePolFlag(tLCUWMasterSchema
					.getChangePolFlag());
			tLCUWSubSchema.setChangePolReason(tLCUWMasterSchema
					.getChangePolReason());
			tLCUWSubSchema.setAddPremReason(tLCUWMasterSchema
					.getAddPremReason());
			tLCUWSubSchema.setPrintFlag(tLCUWMasterSchema.getPrintFlag());
			tLCUWSubSchema.setPrintFlag2(tLCUWMasterSchema.getPrintFlag2());
			tLCUWSubSchema.setUWIdea(tLCUWMasterSchema.getUWIdea());
			tLCUWSubSchema.setOperator(tLCUWMasterSchema.getOperator()); // 操作员
			tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom());
			tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCUWSubSet.clear();
		mLCUWSubSet.add(tLCUWSubSchema);

		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = tLCPolDB
				.executeQuery("select * from LCPol where ContNo = '" + mContNo
						+ "' and PolNo <> '" + mPolNo + "'");

		if ((tLCPolSet == null) || (tLCPolSet.size() < 0)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCCont表中无法查到合同号为" + mContNo + "的保单记录";
			this.mErrors.addOneError(tError);

			return false;
		}

		n = tLCPolSet.size();

		LCPolSchema tLCPolSchema = null;
		mChgContMaster = false;

		mContPassFlag = mUWFlag;

		for (int i = 1; i <= n; i++) {
			tLCPolSchema = tLCPolSet.get(i);

			if (tLCPolSchema.getUWFlag().equals("0")
					|| tLCPolSchema.getUWFlag().equals("5")) {
				return true;
			} else if (mContPassFlag.equals("9") || mContPassFlag.equals("4")) {
				mContPassFlag = "9";
				continue;
			}

			mContPassFlag = tLCPolSchema.getUWFlag();
		}

		mChgContMaster = true;
		mLCContSchema.setUWFlag(mContPassFlag);
		mLCContSchema.setUWOperator(mOperator);
		mLCContSchema.setUWDate(PubFun.getCurrentDate());
		mLCContSchema.setUWTime(PubFun.getCurrentTime());
		mLCContSchema.setOperator(mOperator);
		mLCContSchema.setModifyDate(PubFun.getCurrentDate());
		mLCContSchema.setModifyTime(PubFun.getCurrentTime());

		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);
		tLCCUWMasterDB.setProposalContNo(mContNo);

		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet = tLCCUWMasterDB.query();

		if (tLCCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWMaster表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		n = tLCCUWMasterSet.size();
		logger.debug("该投保单的核保主表记录条数:   " + n);

		if (n == 1) {
			tLCCUWMasterSchema = tLCCUWMasterSet.get(1);
			// 人工核保后uwno加一 add by heyq 2005-1-3
			int uwno = tLCCUWMasterSet.get(1).getUWNo();
			uwno++;
			tLCCUWMasterSchema.setUWNo(uwno);
			tLCCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCCUWMasterSchema.setState(mUWFlag);
			tLCCUWMasterSchema.setUWIdea(mUWIdea);
			tLCCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保

			// tLCCUWMasterSchema.setUWGrade(mUWPopedom);
			tLCCUWMasterSchema.setAppGrade(mAppGrade);

			if (mUWFlag.equals("6")) {
				tLCCUWMasterSchema.setOperator(mUPUWCode); // 上报核保指定核保师功能的实现借助将当前核保师改为待核保师方式实现
			} else {
				tLCCUWMasterSchema.setOperator(mOperator);
			}
			// 操作员

			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

			// 延期
			if (mUWFlag.equals("2")) // 此代码冗余
			{
				tLCCUWMasterSchema.setPostponeDay(mpostday);
				tLCCUWMasterSchema.setPostponeDate(mvalidate);
			}

			// 条件承保
			if (mUWFlag.equals("4")) {
				tLCCUWMasterSchema.setSpecReason(mSpecReason); // 特约原因
				tLCCUWMasterSchema.setAddPremReason(mReason);
			}

			// if (mUWFlag.equals("4"))
			// {
			// if (tLCCUWMasterSchema.getPrintFlag().equals("1"))
			// {
			// CError tError = new CError();
			// tError.moduleName = "UWManuNormGChkBL";
			// tError.functionName = "prepareUW";
			// tError.errorMessage = "已经发核保通知不可录入!";
			// this.mErrors.addOneError(tError);
			//
			// return false;
			// }
			//
			// tLCCUWMasterSchema.setSpecFlag("1");
			// }
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWMaster表取数据不唯一!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCCUWMasterSet.clear();
		mLCCUWMasterSet.add(tLCCUWMasterSchema);

		// 核保轨迹表
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mContNo);

		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();

		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		m = tLCCUWSubSet.size();
		logger.debug("subcount=" + m);

		if (m > 0) {
			m++; // 核保次数
			tLCCUWSubSchema = new LCCUWSubSchema();
			tLCCUWSubSchema.setUWNo(m); // 第几次核保
			tLCCUWSubSchema.setContNo(tLCCUWMasterSchema.getContNo());
			tLCCUWSubSchema.setGrpContNo(tLCCUWMasterSchema.getGrpContNo());
			tLCCUWSubSchema.setProposalContNo(tLCCUWMasterSchema
					.getProposalContNo());
			tLCCUWSubSchema.setInsuredNo(tLCCUWMasterSchema.getInsuredNo());
			tLCCUWSubSchema.setInsuredName(tLCCUWMasterSchema.getInsuredName());
			tLCCUWSubSchema.setAppntNo(tLCCUWMasterSchema.getAppntNo());
			tLCCUWSubSchema.setAppntName(tLCCUWMasterSchema.getAppntName());
			tLCCUWSubSchema.setAgentCode(tLCCUWMasterSchema.getAgentCode());
			tLCCUWSubSchema.setAgentGroup(tLCCUWMasterSchema.getAgentGroup());
			tLCCUWSubSchema.setUWGrade(tLCCUWMasterSchema.getUWGrade()); // 核保级别
			tLCCUWSubSchema.setAppGrade(tLCCUWMasterSchema.getAppGrade()); // 申请级别
			tLCCUWSubSchema.setAutoUWFlag(tLCCUWMasterSchema.getAutoUWFlag());
			tLCCUWSubSchema.setState(tLCCUWMasterSchema.getState());
			tLCCUWSubSchema.setPassFlag(tLCCUWMasterSchema.getState());
			tLCCUWSubSchema.setPostponeDay(tLCCUWMasterSchema.getPostponeDay());
			tLCCUWSubSchema.setPostponeDate(tLCCUWMasterSchema
					.getPostponeDate());
			tLCCUWSubSchema.setUpReportContent(tLCCUWMasterSchema
					.getUpReportContent());
			tLCCUWSubSchema.setHealthFlag(tLCCUWMasterSchema.getHealthFlag());
			tLCCUWSubSchema.setSpecFlag(tLCCUWMasterSchema.getSpecFlag());
			tLCCUWSubSchema.setSpecReason(tLCCUWMasterSchema.getSpecReason());
			tLCCUWSubSchema.setQuesFlag(tLCCUWMasterSchema.getQuesFlag());
			tLCCUWSubSchema.setReportFlag(tLCCUWMasterSchema.getReportFlag());
			tLCCUWSubSchema.setChangePolFlag(tLCCUWMasterSchema
					.getChangePolFlag());
			tLCCUWSubSchema.setChangePolReason(tLCCUWMasterSchema
					.getChangePolReason());
			tLCCUWSubSchema.setAddPremReason(tLCCUWMasterSchema
					.getAddPremReason());
			tLCCUWSubSchema.setPrintFlag(tLCCUWMasterSchema.getPrintFlag());
			tLCCUWSubSchema.setPrintFlag2(tLCCUWMasterSchema.getPrintFlag2());
			tLCCUWSubSchema.setUWIdea(tLCCUWMasterSchema.getUWIdea());
			tLCCUWSubSchema.setOperator(tLCCUWMasterSchema.getOperator()); // 操作员
			tLCCUWSubSchema.setManageCom(tLCCUWMasterSchema.getManageCom());
			tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCCUWSubSet.clear();
		mLCCUWSubSet.add(tLCCUWSubSchema);

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
					+ tLCPolSchema.getPolNo()
					+ "'"
					+ " and makedate is not null"
					+ " and replyresult is null"
					+ " and needprint = 'Y')";

			logger.debug("printchecksql:" + tsql);

			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet t2LCPolSet = new LCPolSet();
			t2LCPolSet = tLCPolDB.executeQuery(tsql);

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
					+ tLCPolSchema.getPolNo()
					+ "'"
					+ " and makedate is not null"
					+ " and replyresult is null)"
					+ " and ProposalNo not in ( select ProposalNo from LCIssuePol where 1 = 1 "
					+ " and backobjtype in ('2','3')"
					+ " and ProposalNo = '"
					+ tLCPolSchema.getPolNo()
					+ "'"
					+ " and makedate is not null"
					+ " and replyresult is null"
					+ " and needprint = 'Y')"
					+ " and ProposalNo not in ( select ProposalNo from LCIssuePol where 1 = 1 "
					+ " and backobjtype = '4'"
					+ " and ProposalNo = '"
					+ tLCPolSchema.getPolNo()
					+ "'"
					+ " and makedate is not null" + " and replyresult is null)";

			logger.debug("printchecksql2:" + tsql);
			tLCPolDB = new LCPolDB();
			t2LCPolSet = new LCPolSet();

			t2LCPolSet = tLCPolDB.executeQuery(tsql);

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

		if (mUWFlag.equals("1")) // 拒保通知书
		{
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_DECLINE);
			tLOPRTManagerSchema.setOtherNo(mGetNoticeNo);
			tLOPRTManagerSchema.setStandbyFlag1(mPolNo);
		}

		if (mUWFlag.equals("2")) // 延期通知书
		{
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_DEFER);
			tLOPRTManagerSchema.setOtherNo(mGetNoticeNo);
			tLOPRTManagerSchema.setStandbyFlag1(mPolNo);
		}

		if (mUWFlag.equals("8")) // 核保通知书
		{
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_UW);

			// 只有问题件的时候校验
			tIfPrintFlag = "0";

			if (mBackFlag.equals("1")) {
				tIfPrintFlag = checkBackOperator(tIfPrintFlag);
			}
		}

		if (mUWFlag.equals("a")) // 撤单
		{
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_WITHDRAW);
			tLOPRTManagerSchema.setOtherNo(mGetNoticeNo);
			tLOPRTManagerSchema.setStandbyFlag1(mPolNo);
		}

		VData tVData = new VData();
		PrintManagerBL tPrintManagerBL = new PrintManagerBL();

		tVData.add(tLOPRTManagerSchema);
		tVData.add(mGlobalInput);

		logger.debug("Start PrintManagerBL Submit...");

		if (tIfPrintFlag.equals("0")) // 只返回给操作员问题件无需发核保通知书
		{
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
				+ tpolno.trim()
				+ "' and uwno = (select max(uwno) from lcuwerror where polno = '"
				+ tpolno.trim() + "')";
		LCUWErrorSet tLCUWErrorSet = tLCUWErrorDB.executeQuery(tsql);
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
			tsql = "select * from lduser where usercode = '" + mUPUWCode + "'";

			LDUserSet tLDUserSet = tLDUserDB.executeQuery(tsql);

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
		mMap.put(mAllLCPolSet, "UPDATE");
		mMap.put(mAllLCContSet, "UPDATE");
		mMap.put(mAllLCGrpContSet, "UPDATE");
		mMap.put(mAllLCUWMasterSet, "DELETE&INSERT");
		mMap.put(mAllLCUWSubSet, "INSERT");

		if (mChgContMaster) {
			mMap.put(mAllLCCUWMasterSet, "DELETE&INSERT");
			mMap.put(mAllLCCUWSubSet, "INSERT");
		}

		mResult.clear();
		mResult.add(mMap);
	}
}
