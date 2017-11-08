package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LMCheckFieldDB;
import com.sinosoft.lis.db.LMEdorWTDB;
import com.sinosoft.lis.db.LMEdorZTDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPReturnLoanDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDUserSet;
import com.sinosoft.lis.vschema.LMCheckFieldSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPReturnLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
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
 * @author Tjj modified by Alex
 * @version 1.0
 */
public class CheckFieldBL {
private static Logger logger = Logger.getLogger(CheckFieldBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 控件名称 */
	private String mFieldName;
	/** 控件位置 */
	private String mPageLocation;
	/** 时间间隔 */
	private int mInterval = 0;
	/** 限制时间间隔 */
	private int mLimitDay = 0;
	/** 保单失效标志 */
	private int mPolValiFlag = 0; // 1无效 ；0有效
	/** 借款金额 */
	private double mLoanMoney = 0.0;
	/** 限制时间间隔 */
	private double mTrayMoney = 0.0;
	/** 操作员编码* */
	private String mOperator;

	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	BqCalBase mBqCalBase = new BqCalBase();
	// LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema(); // 由于数据表结构改变，将原来LPEdorMainSchema对应于现在的LPEdorItemSchema－－Alex
	LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String DisplayFlag = "";

	public CheckFieldBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----Start CheckFiedBL----");

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);
		// cOperate = this.getOperate();
		if (!this.prepareData()) {
			return false;
		}
		logger.debug("----End CheckFiedBL---- ");
		if (mErrors != null && mErrors.getErrorCount() > 0) {
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean prepareData() {
		boolean tVerifyFlag = false;

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mResult.clear();
		for (int i = 0; i < mInputData.size(); i++) {
			Object tElement = mInputData.get(i);
			if (tVerifyFlag == false && tElement.equals("VERIFY||BEGIN")) {
				tVerifyFlag = true;
			}
			if (tVerifyFlag == true) {
				mPageLocation = (String) tElement;
				// 分支开始
				if (mPageLocation.equals("PEDORINPUT#EDORTYPE")) // 个人保全
				{
					String tIntervalType;
					LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
					LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
					tLPEdorItemSet = (LPEdorItemSet) mInputData
							.getObjectByObjectName("LPEdorItemSet", 0);
					logger.debug("==============================================");
					logger.debug("tLPEdorItemSet.size() = "
							+ tLPEdorItemSet.size());
					logger.debug("==============================================");
					for (int j = 1; j <= tLPEdorItemSet.size(); j++) {
						logger.debug("j:" + j);
						tLPEdorItemSchema = tLPEdorItemSet.get(j);
						logger.debug("tLPEdorItemSchema = "
								+ tLPEdorItemSchema.getEdorAppNo());
						logger.debug("tLPEdorItemSchema = "
								+ tLPEdorItemSchema.getContNo());

					}

					TransferData tTransferData = new TransferData();
					tTransferData = (TransferData) mInputData
							.getObjectByObjectName("TransferData", 0);
					DisplayFlag = (String) tTransferData
							.getValueByName("DisplayType");

					if (tLPEdorItemSchema == null) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "CheckFieldBL";
						tError.functionName = "prepareData";
						tError.errorMessage = "保全前台LPEdorItemSchema信息传输失败!";
						this.mErrors.addOneError(tError);
						return false;
					}

					mLPEdorItemSchema.setSchema(tLPEdorItemSchema);

					// 校验保单,险种信息
					if ("CM".equals(tLPEdorItemSchema.getEdorType())) { // 判断
						LCInsuredSet tLCInsuredSet = new LCInsuredSet();
						LCInsuredDB tLCInsuredDB = new LCInsuredDB();
						tLCInsuredDB.setContNo(tLPEdorItemSchema.getContNo());
						tLCInsuredDB.setInsuredNo(tLPEdorItemSchema
								.getInsuredNo());
						tLCInsuredSet = tLCInsuredDB.query();
						if (tLCInsuredDB.mErrors.needDealError()) {
							CError.buildErr(this, "查询被保险人信息错误！");
							return false;
						}
						if (tLCInsuredSet.size() == 0) // 投保人信息变更
						{
							DisplayFlag = "1";
						}

					}
					if ("1".equals(DisplayFlag)) {
						LCPolDB tLCPolDB = new LCPolDB();
						tLCPolDB.setContNo(tLPEdorItemSchema.getContNo());
						LCPolSet tLCPolSet = tLCPolDB.query();
						if (tLCPolSet == null || tLCPolSet.size() < 1) {
							CError tError = new CError();
							tError.moduleName = "CheckFieldBL";
							tError.functionName = "prepareData";
							tError.errorMessage = "保单"
									+ tLPEdorItemSchema.getContNo() + "下无险种信息!";
							this.mErrors.addOneError(tError);
							return false;
						}
						LCPolSchema tLCPolSchema = new LCPolSchema();
						tLCPolSchema.setPolNo("000000");
						tLCPolSchema.setRiskCode("000000");
						tLCPolSchema.setContNo(mLPEdorItemSchema.getContNo());
						mLCPolSet.add(tLCPolSchema);
					} else if ("2".equals(DisplayFlag)) {
						LCPolDB tLCPolDB = new LCPolDB();
						tLCPolDB.setContNo(tLPEdorItemSchema.getContNo());
						tLCPolDB.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
						LCPolSet tLCPolSet = tLCPolDB.query();
						if (tLCPolSet == null || tLCPolSet.size() < 1) {
							CError tError = new CError();
							tError.moduleName = "CheckFieldBL";
							tError.functionName = "prepareData";
							tError.errorMessage = "保单"
									+ tLPEdorItemSchema.getContNo() + "的被保人号为"
									+ tLPEdorItemSchema.getInsuredNo()
									+ "的被保人下无险种信息!";
							this.mErrors.addOneError(tError);
							return false;
						}
						mLCPolSet.add(tLCPolSet);

					} else if ("3".equals(DisplayFlag)) {
						LCPolDB tLCPolDB = new LCPolDB();
						tLCPolDB.setPolNo(tLPEdorItemSchema.getPolNo());
						if (!tLCPolDB.getInfo()) {
							CError tError = new CError();
							tError.moduleName = "CheckFieldBL";
							tError.functionName = "prepareData";
							tError.errorMessage = "保单"
									+ tLPEdorItemSchema.getContNo() + "下险种"
									+ tLPEdorItemSchema.getPolNo() + "信息查询失败!";
							this.mErrors.addOneError(tError);
							return false;
						}
						mLCPolSchema.setSchema(tLCPolDB);
						mLCPolSet.add(mLCPolSchema);

					}

					// if(!tLPEdorItemSchema.getPolNo().equals("000000")){
					// LCPolDB tLCPolDB = new LCPolDB();
					// tLCPolDB.setPolNo(tLPEdorItemSchema.getPolNo());
					// if (!tLCPolDB.getInfo())
					// {
					// CError tError = new CError();
					// tError.moduleName = "CheckFieldBL";
					// tError.functionName = "prepareData";
					// tError.errorMessage = "险种" + tLPEdorItemSchema.getPolNo()
					// +
					// "信息查询失败!";
					// this.mErrors.addOneError(tError);
					// return false;
					// }
					// mLCPolSchema.setSchema(tLCPolDB);
					// }
					LCContDB tLCContDB = new LCContDB();
					tLCContDB.setContNo(tLPEdorItemSchema.getContNo());
					if (!tLCContDB.getInfo()) {
						CError tError = new CError();
						tError.moduleName = "CheckFieldBL";
						tError.functionName = "prepareData";
						tError.errorMessage = "保单"
								+ tLPEdorItemSchema.getContNo() + "信息查询失败!";
						this.mErrors.addOneError(tError);
						return false;
					}
					mLCContSchema.setSchema(tLCContDB);

					// 准备计算数据
					// 准备保全项目类别数据
					mFieldName = mLPEdorItemSchema.getEdorType();

					// 准备保全操作员代码
					if (mGlobalInput == null) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "CheckFieldBL";
						tError.functionName = "prepareData";
						tError.errorMessage = "保全前台GlobalInput信息传输失败!";
						this.mErrors.addOneError(tError);
						return false;
					}

					if (mGlobalInput.Operator != null) {
						mOperator = mGlobalInput.Operator;
					}
					LDUserDB tLDUserDB = new LDUserDB();
					tLDUserDB.setUserCode(mOperator);
					LDUserSet tLDUserSet = tLDUserDB.query();
					if (tLDUserSet == null || tLDUserSet.size() != 1) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "CheckFieldBL";
						tError.functionName = "prepareData";
						tError.errorMessage = "保全操作员信息查询失败!";
						this.mErrors.addOneError(tError);
						return false;
					}

					for (int j = 0; j < mLCPolSet.size(); j++) {
						mLCPolSchema = mLCPolSet.get(j + 1);

						// 准备借款金额
						if (!hasLoanMoney()) {
							return false;
						}

						// 准备垫款金额
						if (!hasTrayMoney()) {
							return false;
						}
						// 准备保单送达日期于保全生效日期之间的间隔数据
						if (mLPEdorItemSchema.getEdorType().equals("WT")) {

							int tInterval = 0;
							LMEdorWTDB tLMEdorWTDB = new LMEdorWTDB();
							tLMEdorWTDB.setRiskCode(mLCPolSchema.getRiskCode());
							tLMEdorWTDB.setRiskVersion(mLCPolSchema
									.getRiskVersion());
							if (tLMEdorWTDB.getInfo()) {
								tInterval = PubFun.calInterval(mLCContSchema
										.getCustomGetPolDate(),
										mLPEdorItemSchema.getEdorValiDate(),
										tLMEdorWTDB.getHesitateType());

							}
							// LMEdorWTDB tLMEdorWTDB = new LMEdorWTDB();
							// tLMEdorWTDB.setRiskCode(mLCPolSchema.getRiskCode());
							// tLMEdorWTDB.setRiskVersion(mLCPolSchema.getRiskVersion());
							// if (tLMEdorWTDB.getInfo())
							// {
							// mInterval = PubFun.calInterval(mLCContSchema.
							// getCustomGetPolDate(),
							// mLPEdorItemSchema.getEdorValiDate(),
							// tLMEdorWTDB.getHesitateType());
							// if (tInterval<0)
							// {
							// // @@错误处理
							// CError tError = new CError();
							// tError.moduleName = "CheckFieldBL";
							// tError.functionName = "prepareData";
							// tError.errorMessage = "保单送达日期不能小于保全生效日期!";
							// this.mErrors .addOneError(tError) ;
							// return false;
							// }
							//
							// int limitDay = 10;
							// if (mGlobalInput == null) {
							// logger.debug("mGlobalInput is null");
							// limitDay = 10;
							// } else {
							// String usercode = mGlobalInput.Operator;
							// logger.debug("usercode: " + usercode);
							// LDUserDB tLDUserDB = new LDUserDB();
							// tLDUserDB.setUserCode(usercode);
							// LDUserSet tLDUserSet = tLDUserDB.query();
							// if (tLDUserSet.size() != 0) {
							// String edorPopedom =
							// tLDUserSet.get(1).getEdorPopedom();
							// logger.debug("edorPopedom :" +
							// edorPopedom);
							// if (edorPopedom != null &&
							// edorPopedom.trim().compareTo("F") == 0) {
							// limitDay = 20;
							// }
							// }
							// }
							//
							// if (tInterval>limitDay)
							// {
							// // @@错误处理
							// CError tError = new CError();
							// tError.moduleName = "CheckFieldBL";
							// tError.functionName = "prepareData";
							// tError.errorMessage = "超过犹豫期退保期间，无法进行此保全项目!";
							// this.mErrors .addOneError(tError) ;
							// return false;
							// }
							//
							// }
						}

						if (mLPEdorItemSchema.getEdorType().equals("ZT")) {
							logger.debug("-------ZT------------");
							LMEdorZTDB tLMEdorZTDB = new LMEdorZTDB();
							tLMEdorZTDB.setRiskCode(mLCPolSchema.getRiskCode());
							tLMEdorZTDB.setRiskVersion(mLCPolSchema
									.getRiskVersion());

							if (tLMEdorZTDB.getInfo()) {

								// 判断没有达到生存退保期间，原来为10，现改为从lmedorzt中获取，at
								// 2003-12-11 by Minim
								if (mLCPolSchema.getPayIntv() == 0) {
									tIntervalType = tLMEdorZTDB.getOnePayType();
									mLimitDay = tLMEdorZTDB.getOnePayStart();
								} else {
									tIntervalType = tLMEdorZTDB.getCycType();
									mLimitDay = tLMEdorZTDB.getCycStart();
								}

								mInterval = PubFun.calInterval(mLCContSchema
										.getCustomGetPolDate(),
										mLPEdorItemSchema.getEdorValiDate(),
										tIntervalType);

							}

						}

						if (mLPEdorItemSchema.getEdorType().equals("RE")) {
							FDate tFDate = new FDate();
							String tLapseDate = EdorCalZT.CalLapseDate(
									mLCPolSchema.getRiskCode(), mLCPolSchema
											.getPaytoDate());

							mInterval = PubFun.calInterval(mLPEdorItemSchema
									.getEdorValiDate(), tLapseDate, "D");
							mLimitDay = 0;

							// 准备失效标志
							boolean tPolValiFlag = EdorCalZT.JudgeLapse(
									mLCPolSchema.getRiskCode(), mLCPolSchema
											.getPaytoDate(), PubFun
											.getCurrentDate(),
									String.valueOf(mLCPolSchema.getPayIntv()),
									mLCPolSchema.getEndDate());
							if (tPolValiFlag) {
								mPolValiFlag = 1; // 失效
							} else {
								mPolValiFlag = 0; // 有效
							}
						}

						// 初始化计算元素数据
						mBqCalBase = this.initBqCalBase();

						if (!this.CalVerify("N")) {
							return false;
						}
						// if (!this.CalVerify("Y") )return false;
					}

				} else if (mPageLocation.equals("GEDORINPUT#EDORTYPE")) // 团体保全
				{
					// continue;
					String tIntervalType;
					LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
					tLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
							.getObjectByObjectName("LPGrpEdorItemSchema", 0);

					if (tLPGrpEdorItemSchema == null) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "CheckFieldBL";
						tError.functionName = "prepareData";
						tError.errorMessage = "保全前台LPEdorItemSchema信息传输失败!";
						this.mErrors.addOneError(tError);
						return false;
					}

					mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSchema);

					// 校验保单,险种信息
					LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
					tLCGrpPolDB
							.setGrpPolNo(tLPGrpEdorItemSchema.getGrpContNo());
					LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
					if (tLCGrpPolSet.size() < 1) {
						CError tError = new CError();
						tError.moduleName = "CheckFieldBL";
						tError.functionName = "prepareData";
						tError.errorMessage = "用"
								+ tLPGrpEdorItemSchema.getGrpContNo()
								+ "查询险种信息失败!";
						this.mErrors.addOneError(tError);
						return false;
					}
					mLCGrpPolSchema.setSchema(tLCGrpPolSet.get(1));

					LCGrpContDB tLCGrpContDB = new LCGrpContDB();
					tLCGrpContDB.setGrpContNo(tLPGrpEdorItemSchema
							.getGrpContNo());
					if (!tLCGrpContDB.getInfo()) {
						CError tError = new CError();
						tError.moduleName = "CheckFieldBL";
						tError.functionName = "prepareData";
						tError.errorMessage = "保单"
								+ tLPGrpEdorItemSchema.getGrpContNo()
								+ "信息查询失败!";
						this.mErrors.addOneError(tError);
						return false;
					}
					mLCGrpContSchema.setSchema(tLCGrpContDB);

					// 准备计算数据
					// 准备保全项目类别数据
					mFieldName = mLPGrpEdorItemSchema.getEdorType();

					// 准备保全操作员代码
					if (mGlobalInput == null) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "CheckFieldBL";
						tError.functionName = "prepareData";
						tError.errorMessage = "保全前台GlobalInput信息传输失败!";
						this.mErrors.addOneError(tError);
						return false;
					}

					if (mGlobalInput.Operator != null) {
						mOperator = mGlobalInput.Operator;
					}
					LDUserDB tLDUserDB = new LDUserDB();
					tLDUserDB.setUserCode(mOperator);
					LDUserSet tLDUserSet = tLDUserDB.query();
					if (tLDUserSet == null || tLDUserSet.size() != 1) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "CheckFieldBL";
						tError.functionName = "prepareData";
						tError.errorMessage = "保全操作员信息查询失败!";
						this.mErrors.addOneError(tError);
						return false;
					}

					// 准备借款金额
					if (!grpHasLoanMoney()) {
						return false;
					}

					// 准备垫款金额
					if (!grpHasTrayMoney()) {
						return false;
					}
					// 准备保单送达日期于保全生效日期之间的间隔数据
					if (mLPGrpEdorItemSchema.getEdorType().equals("WT")) {
						LMEdorWTDB tLMEdorWTDB = new LMEdorWTDB();
						tLMEdorWTDB.setRiskCode(mLCGrpPolSchema.getRiskCode());
						tLMEdorWTDB.setRiskVersion(mLCGrpPolSchema
								.getRiskVersion());
						if (tLMEdorWTDB.getInfo()) {
							mInterval = PubFun.calInterval(mLCGrpContSchema
									.getCustomGetPolDate(),
									mLPGrpEdorItemSchema.getEdorValiDate(),
									tLMEdorWTDB.getHesitateType());

						}
					}

					if (mLPGrpEdorItemSchema.getEdorType().equals("ZT")) {
						logger.debug("-------ZT------------");
						LMEdorZTDB tLMEdorZTDB = new LMEdorZTDB();
						tLMEdorZTDB.setRiskCode(mLCGrpPolSchema.getRiskCode());
						tLMEdorZTDB.setRiskVersion(mLCGrpPolSchema
								.getRiskVersion());

						if (tLMEdorZTDB.getInfo()) {

							// 判断没有达到生存退保期间，原来为10，现改为从lmedorzt中获取，at 2003-12-11
							// by Minim
							if (mLCGrpPolSchema.getPayIntv() == 0) {
								tIntervalType = tLMEdorZTDB.getOnePayType();
								mLimitDay = tLMEdorZTDB.getOnePayStart();
							} else {
								tIntervalType = tLMEdorZTDB.getCycType();
								mLimitDay = tLMEdorZTDB.getCycStart();
							}

							mInterval = PubFun.calInterval(mLCGrpContSchema
									.getCustomGetPolDate(),
									mLPGrpEdorItemSchema.getEdorValiDate(),
									tIntervalType);

						}

					}

					if (mLPGrpEdorItemSchema.getEdorType().equals("RE")) {
						String tLapseDate = EdorCalZT.CalLapseDate(
								mLCGrpPolSchema.getRiskCode(), mLCGrpPolSchema
										.getPaytoDate());

						mInterval = PubFun.calInterval(mLPGrpEdorItemSchema
								.getEdorValiDate(), tLapseDate, "D");
						mLimitDay = 0;

						// 准备失效标志
						// boolean tPolValiFlag = EdorCalZT.JudgeLapse(
						// mLCGrpPolSchema.getRiskCode(),
						// mLCGrpPolSchema.getPaytoDate(),
						// PubFun.getCurrentDate(),
						// String.valueOf(mLCGrpPolSchema.getPayIntv()),
						// mLCPolSchema.getEndDate());
						// if (tPolValiFlag)
						// {
						// mPolValiFlag = 1; //失效
						// }
						// else
						// {
						// mPolValiFlag = 0; // 有效
						// }
					}

					// 初始化计算元素数据
					mBqCalBase = this.initBqCalBase();

					if (!this.CalVerify("N")) {
						return false;
					}

					// if (!this.CalVerify("Y") )return false;

				}
			}
		}

		return true;
	}

	/**
	 * 校验
	 * 
	 * @return
	 */
	private boolean CalVerify(String returnType) {
		// 得到checkField的信息
		LMCheckFieldSet tLMCheckFieldSet = new LMCheckFieldSet();
		LMCheckFieldDB tLMCheckFieldDB = new LMCheckFieldDB();

		if (mFieldName == null) {
			mFieldName = "";
		}
		if (mPageLocation == null) {
			mPageLocation = "";
		}
		String tString = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if (mPageLocation.equals("PEDORINPUT#EDORTYPE")) {
			tString = " select * from LMCheckField where " + " riskcode = '"
					+ "?riskcode?" + "'"
					+ " and pageLocation = '" + "?mPageLocation?" + "'"
					+ " and FieldName = '" + "?mFieldName?" + "'" + " union "
					+ " select * from LMCheckField where "
					+ " riskcode ='000000' " + " and pageLocation = '"
					+ "?mPageLocation?" + "'" + " and FieldName = 'BQ'" + " union "
					+ " select * from LMCheckField where " + " riskcode = '"
					+ "?riskcode?" + "'"
					+ " and pageLocation = '" + "?mPageLocation?" + "'"
					+ " and FieldName = 'BQ'" + " union "
					+ " select * from LMCheckField where "
					+ " riskcode = '000000'" + " and pageLocation = '"
					+ "?mPageLocation?" + "'" + " and FieldName = '" + "?mFieldName?"
					+ "'" + " order by 4 ";
			sqlbv.sql(tString);
			sqlbv.put("riskcode", mLCPolSchema.getRiskCode());
			sqlbv.put("mPageLocation", mPageLocation);
			sqlbv.put("mFieldName", mFieldName);
		} else {
			tString = " select * from LMCheckField where " + " riskcode = '"
					+ "?riskcode?" + "'"
					+ " and pageLocation = '" + "?mPageLocation?" + "'"
					+ " and FieldName = '" + "?mFieldName?" + "'" + " union "
					+ " select * from LMCheckField where "
					+ " riskcode ='000000' " + " and pageLocation = '"
					+ "?mPageLocation?" + "'" + " and FieldName = 'BQ'" + " union "
					+ " select * from LMCheckField where " + " riskcode = '"
					+ "?riskcode?" + "'"
					+ " and pageLocation = '" + "?mPageLocation?" + "'"
					+ " and FieldName = 'BQ'" + " union "
					+ " select * from LMCheckField where "
					+ " riskcode = '000000'" + " and pageLocation = '"
					+ "?mPageLocation?" + "'" + " and FieldName = '" + "?mFieldName?"
					+ "'" + " order by 4 ";
			sqlbv.sql(tString);
			sqlbv.put("riskcode", mLCGrpPolSchema.getRiskCode());
			sqlbv.put("mPageLocation", mPageLocation);
			sqlbv.put("mFieldName", mFieldName);
		}

		tLMCheckFieldSet = tLMCheckFieldDB.executeQuery(sqlbv);
		logger.debug("SQLString:" + tString);
		for (int j = 1; j <= tLMCheckFieldSet.size(); j++) {
			BqCalBL tBqCalBL = new BqCalBL(mBqCalBase, tLMCheckFieldSet.get(j)
					.getCalCode(), "");
			if (!tBqCalBL.calValiEndorse(returnType)) {
				if (tLMCheckFieldSet.get(j).getMsgFlag().equals("Y")) {
					mResult.addElement(tLMCheckFieldSet.get(j).getMsg());
				}
				if (tLMCheckFieldSet.get(j).getReturnValiFlag().equals("N")) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "CheckFieldBL";
					tError.functionName = "prepareData";
					tError.errorMessage = tLMCheckFieldSet.get(j).getMsg();
					this.mErrors.addOneError(tError);
				}
			}
		}
		return true;
	}

	/**
	 * 准备计算要素
	 * 
	 * @return
	 */
	private BqCalBase initBqCalBase() {

		BqCalBase tBqCalBase = new BqCalBase();
		if (mPageLocation.equals("PEDORINPUT#EDORTYPE")) {
			// 得到交费年期
			tBqCalBase.setPayEndYear(mLCPolSchema.getPayEndYear());
			// 得到投保年龄，性别
			tBqCalBase.setAppAge(PubFun.calInterval(mLCPolSchema
					.getInsuredBirthday(), mLPEdorItemSchema.getEdorValiDate(),
					"Y"));
			tBqCalBase.setSex(mLCPolSchema.getInsuredSex());
			tBqCalBase.setRiskCode(mLCPolSchema.getRiskCode());
			tBqCalBase.setCValiDate(mLCPolSchema.getCValiDate());
			if (mLPEdorItemSchema.getEdorValiDate() != null
					&& !mLPEdorItemSchema.getEdorValiDate().trim().equals("")) {
				tBqCalBase.setEdorValiDate(mLPEdorItemSchema.getEdorValiDate());
			} else {
				tBqCalBase.setEdorValiDate("null");
			}
			tBqCalBase.setMult(mLCPolSchema.getMult());
			tBqCalBase.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
			tBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tBqCalBase.setEdorType(mLPEdorItemSchema.getEdorType());
			tBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
			logger.debug("++++++++++++++++++++++"
					+ mLPEdorItemSchema.getEdorAcceptNo());
			logger.debug("++++++++++++++++++++++"
					+ mLPEdorItemSchema.getContNo());
			tBqCalBase.setPolNo(mLPEdorItemSchema.getPolNo());
			tBqCalBase.setInterval(mInterval);
			tBqCalBase.setLimitDay(mLimitDay);
			tBqCalBase.setPolValiFlag(mPolValiFlag);
			tBqCalBase.setTrayMoney(mTrayMoney);
			tBqCalBase.setLoanMoney(mLoanMoney);
			tBqCalBase.setOperator(mOperator);

		} else {
			// 得到交费年期
			// tBqCalBase.setPayEndYear(mLCPolSchema.getPayEndYear());
			// 得到投保年龄，性别
			// tBqCalBase.setAppAge(PubFun.calInterval(mLCPolSchema.
			// getInsuredBirthday(),
			// mLPEdorItemSchema.
			// getEdorValiDate(), "Y"));
			// tBqCalBase.setSex(mLCPolSchema.getInsuredSex());
			tBqCalBase.setRiskCode(mLCGrpPolSchema.getRiskCode());
			tBqCalBase.setCValiDate(mLCGrpPolSchema.getCValiDate());
			if (mLPGrpEdorItemSchema.getEdorValiDate() != null
					&& !mLPGrpEdorItemSchema.getEdorValiDate().trim()
							.equals("")) {
				tBqCalBase.setEdorValiDate(mLPGrpEdorItemSchema
						.getEdorValiDate());
			} else {
				tBqCalBase.setEdorValiDate("null");
			}
			tBqCalBase.setMult(mLCGrpPolSchema.getMult());
			tBqCalBase.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tBqCalBase.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			// tBqCalBase.setPolNo(mLPEdorItemSchema.getPolNo());
			tBqCalBase.setInterval(mInterval);
			tBqCalBase.setLimitDay(mLimitDay);
			tBqCalBase.setPolValiFlag(mPolValiFlag);
			tBqCalBase.setTrayMoney(mTrayMoney);
			tBqCalBase.setLoanMoney(mLoanMoney);
			tBqCalBase.setOperator(mOperator);
		}
		return tBqCalBase;
	}

	// private boolean prepareData()
	// {
	// boolean tVerifyFlag = false;
	//
	// mGlobalInput =
	// (GlobalInput)mInputData.getObjectByObjectName("GlobalInput",0);
	// mResult.clear();
	// // try {
	//
	// for (int i=0;i<mInputData.size();i++)
	// {
	// Object tElement = mInputData.get(i);
	// if (tVerifyFlag==false&&tElement.equals("VERIFY||BEGIN"))
	// {
	// tVerifyFlag = true;
	// }
	// if (tVerifyFlag==true)
	// {
	// mPageLocation = (String)tElement;
	// //分支开始
	// if (mPageLocation.equals("PEDORINPUT#EDORTYPE"))
	// {
	//
	// LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
	// tLPEdorMainSchema =
	// (LPEdorMainSchema)mInputData.getObjectByObjectName("LPEdorMainSchema",0);
	//
	// LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
	// tLPEdorMainDB.setSchema(tLPEdorMainSchema);
	// if (tLPEdorMainDB.getInfo())
	// {
	// tLPEdorMainSchema.setSchema(tLPEdorMainDB.getSchema());
	// }
	//
	//
	// //校验特殊的保全项目
	// String tSql = "select count(*) from lpedormain where
	// polno='"+tLPEdorItemSchema.getPolNo()+"' and edorstate <> '0' and
	// edortype in ('WT','ZT','XT')";
	// logger.debug("---- tSql :" + tSql);
	// ExeSQL tExeSQL = new ExeSQL();
	// String tReturn = tExeSQL.getOneValue(tSql);
	// if (tReturn==null||tReturn.trim().equals(""))
	// tReturn ="0";
	// if (!tReturn.trim().equals("0"))
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "该保单已经申请了退保,不能增加新项目!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	//
	// /*
	// if (tLPEdorMainSchema.getEdorState().trim().equals("2")) {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "已申请确认的保全不能进行修改!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	// */
	// mLPEdorMainSchema.setSchema(tLPEdorMainSchema);
	// mFieldName = mLPEdorMainSchema.getEdorType();
	//
	// LCPolDB tLCPolDB = new LCPolDB();
	// tLCPolDB.setPolNo(mLPEdorMainSchema.getPolNo());
	// if (!tLCPolDB.getInfo())
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "保单查询失败!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	// mLCPolSchema.setSchema(tLCPolDB.getSchema());
	// mBqCalBase = this.initBqCalBase();
	// int tInterval=0;
	// boolean tSucc = false;
	//
	// //附险不可以变更投保人
	// if (mLPEdorMainSchema.getEdorType().equals("IA"))
	// {
	// LCPolDB tLCPolDB1 = new LCPolDB();
	// String strSql = "select * from lcpol where polno = '" +
	// mLPEdorMainSchema.getPolNo() + "'";
	// LCPolSet tLCPolSet = tLCPolDB1.executeQuery(strSql);
	// if (tLCPolSet.size() == 0) {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "保单不存在!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	// LCPolSchema tLCPolSchema = new LCPolSchema();
	// tLCPolSchema = tLCPolSet.get(1);
	// if (!tLCPolSchema.getPolNo().equals(tLCPolSchema.getMainPolNo())) {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "附险不可以变更投保人!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	//
	// }
	//
	// }
	// else if (mLPEdorMainSchema.getEdorType().equals("GT"))
	// {
	// logger.debug("----GT----------");
	//
	// //如果此保单正在进行过失退保，则不让进行此保全项目
	// String strSql = "select * from lpedormain where edornno = 'XT' and polno
	// = '" + mLCPolSchema.getPolNo() + "'";
	// LPEdorMainDB ttLPEdorMainDB = new LPEdorMainDB();
	// LPEdorMainSet tLPEdorMainSet = ttLPEdorMainDB.executeQuery(strSql) ;
	// if (tLPEdorMainSet != null && tLPEdorMainSet.size() > 0) {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "此保单正在进行过失退保，不能再次申请!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	//
	// if (hasLoanMoney()) {
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "此保单存在借款，无法进行此保全项目!";
	// this.mErrors.addOneError(tError) ;
	// return false;
	// }
	//
	// if (hasTrayMoney()) {
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "此保单存在垫交，无法进行此保全项目!";
	// this.mErrors.addOneError(tError) ;
	// return false;
	// }
	//
	// tSucc = true;
	// }
	// else if (mLPEdorMainSchema.getEdorType().equals("WT"))
	// {
	// logger.debug("----WT----------");
	// LMEdorWTDB tLMEdorWTDB = new LMEdorWTDB();
	// tLMEdorWTDB.setRiskCode(mLCPolSchema.getRiskCode());
	// tLMEdorWTDB.setRiskVersion(mLCPolSchema.getRiskVersion());
	// if (tLMEdorWTDB.getInfo())
	// {
	// if (mLCPolSchema.getCustomGetPolDate()==null)
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "保单送达日期不能为空!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	// if (mLCPolSchema.getCustomGetPolDate()==null)
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "保单保全生效日期不能为空!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	// logger.debug("--------------------");
	// logger.debug("getCustomGetPolDate : " +
	// mLCPolSchema.getCustomGetPolDate());
	// logger.debug("getEdorValiDate : " +
	// mLPEdorMainSchema.getEdorValiDate());
	// logger.debug("hestType : " + tLMEdorWTDB.getHesitateType());
	// tInterval =
	// PubFun.calInterval(mLCPolSchema.getCustomGetPolDate(),mLPEdorMainSchema.getEdorValiDate(),tLMEdorWTDB.getHesitateType());
	// logger.debug("<<<<<<<<<tInterval : " + tInterval);
	// if (tInterval<0)
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "保单送达日期不能小于保全生效日期!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	//
	// int limitDay = 10;
	// if (mGlobalInput == null) {
	// logger.debug("mGlobalInput is null");
	// limitDay = 10;
	// } else {
	// String usercode = mGlobalInput.Operator;
	// logger.debug("usercode: " + usercode);
	// LDUserDB tLDUserDB = new LDUserDB();
	// tLDUserDB.setUserCode(usercode);
	// LDUserSet tLDUserSet = tLDUserDB.query();
	// if (tLDUserSet.size() != 0) {
	// String edorPopedom = tLDUserSet.get(1).getEdorPopedom();
	// logger.debug("edorPopedom :" + edorPopedom);
	// if (edorPopedom != null && edorPopedom.trim().compareTo("F") == 0) {
	// limitDay = 20;
	// }
	// }
	// }
	//
	// logger.debug("tInterval days limit :" + limitDay);
	//
	// if (tInterval>limitDay)
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "超过犹豫期退保期间，无法进行此保全项目!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	//
	// } else {
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "此险种无法进行此保全项目!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	//
	// if (hasLoanMoney()) {
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "此保单存在借款，无法进行此保全项目!";
	// this.mErrors.addOneError(tError) ;
	// return false;
	// }
	//
	// tSucc= true;
	// }
	// else if (mLPEdorMainSchema.getEdorType().equals("ZT"))
	// {
	// logger.debug("-------ZT------------");
	// LMEdorZTDB tLMEdorZTDB = new LMEdorZTDB();
	// tLMEdorZTDB.setRiskCode(mLCPolSchema.getRiskCode());
	// tLMEdorZTDB.setRiskVersion(mLCPolSchema.getRiskVersion());
	//
	// if (tLMEdorZTDB.getInfo())
	// {
	// if (mLCPolSchema.getCustomGetPolDate()==null)
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "保单送达日期不能为空!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	// if (mLCPolSchema.getCustomGetPolDate()==null)
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "保单保全生效日期不能为空!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	//
	// //判断没有达到生存退保期间，原来为10，现改为从lmedorzt中获取，at 2003-12-11 by Minim
	// int dateStart = 0;
	// String tIntervalType;
	// if (mLCPolSchema.getPayIntv()==0) {
	// tIntervalType = tLMEdorZTDB.getOnePayType();
	// mLimitDay = tLMEdorZTDB.getOnePayStart();
	// }
	// else {
	// tIntervalType = tLMEdorZTDB.getCycType();
	// dateStart = tLMEdorZTDB.getCycStart();
	// }
	//
	// tInterval =
	// PubFun.calInterval(mLCPolSchema.getCustomGetPolDate(),mLPEdorMainSchema.getEdorValiDate(),tIntervalType);
	// logger.debug("<<<<<<<<<tInterval : " + tInterval);
	// // if (tInterval<0)
	// // {
	// // // @@错误处理
	// // CError tError = new CError();
	// // tError.moduleName = "CheckFieldBL";
	// // tError.functionName = "prepareData";
	// // tError.errorMessage = "保单送达日期不能小于保全生效日期!";
	// // this.mErrors .addOneError(tError) ;
	// // return false;
	// // }
	// // if (tInterval <= limitDay)
	// // {
	// // // @@错误处理
	// // CError tError = new CError();
	// // tError.moduleName = "CheckFieldBL";
	// // tError.functionName = "prepareData";
	// // tError.errorMessage = "没有达到生存退保期间，无法进行此保全项目!";
	// // this.mErrors .addOneError(tError) ;
	// // return false;
	// // }
	//
	//
	// if (hasLoanMoney()) {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "该保单尚未失效!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	//
	//
	// tSucc= true;
	// } else { //在LMEdorZT表中没有此条目
	//
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "此保单无法进行此保全项目!";
	// this.mErrors.addOneError(tError) ;
	// return false;
	// }
	//
	// }
	// else if (mLPEdorMainSchema.getEdorType().equals("PT"))
	// {
	//
	// if (hasLoanMoney() || hasTrayMoney()) {
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "此保单无法进行此保全项目!";
	// this.mErrors.addOneError(tError) ;
	// return false;
	// }
	//
	// tSucc = true;
	// }
	// else if (mLPEdorMainSchema.getEdorType().equals("IC"))
	// {
	//
	// if (hasLoanMoney()) {
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "此保单存在借款，无法进行此保全项目!";
	// this.mErrors.addOneError(tError) ;
	// return false;
	// }
	//
	// if (hasTrayMoney()) {
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "此保单存在垫交，无法进行此保全项目!";
	// this.mErrors.addOneError(tError) ;
	// return false;
	// }
	// tSucc = true;
	// }
	// else if (mLPEdorMainSchema.getEdorType().equals("RE"))
	// {
	// FDate tFDate = new FDate();
	// String tLapseDate =
	// EdorCalZT.CalLapseDate(mLCPolSchema.getRiskCode(),mLCPolSchema.getPaytoDate());
	// if
	// (tFDate.getDate(mLPEdorMainSchema.getEdorValiDate()).before(tFDate.getDate(tLapseDate)))
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "该保单尚未失效!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	//
	// tSucc= true;
	// }
	// else if (mLPEdorMainSchema.getEdorType().equals("BC"))
	// {
	// String polno = mLCPolSchema.getPolNo();
	// String mainPolNo = mLCPolSchema.getMainPolNo();
	// if (!polno.equals(mainPolNo)) {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "附险保单不进行受益人变更!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	//
	// tSucc = true;
	//
	// }
	// else if (mLPEdorMainSchema.getEdorType().equals("BB"))
	// {
	// String polno = mLCPolSchema.getPolNo();
	// String mainPolNo = mLCPolSchema.getMainPolNo();
	// if (!polno.equals(mainPolNo)) {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "附险保单不能单独读进行被保险人资料变更!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	//
	// tSucc = true;
	//
	// }
	//
	// else if (mLPEdorMainSchema.getEdorType().equals("MO")) {
	// FDate tFDate = new FDate();
	// boolean checkFlag;
	//
	// //失效判断
	// checkFlag = EdorCalZT.JudgeLapse(mLCPolSchema.getRiskCode(),
	// mLCPolSchema.getPaytoDate(),
	// PubFun.getCurrentDate(),String.valueOf(mLCPolSchema.getPayIntv()),mLCPolSchema.getEndDate());
	// if (checkFlag) {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "该保单效力已中止!";
	// this.mErrors.addOneError(tError) ;
	// return false;
	// }
	//
	// //宽限期判断
	// // if
	// (tFDate.getDate(mLCPolSchema.getPaytoDate()).before(tFDate.getDate(PubFun.getCurrentDate())))
	// {
	// // // @@错误处理
	// // CError tError = new CError();
	// // tError.moduleName = "CheckFieldBL";
	// // tError.functionName = "prepareData";
	// // tError.errorMessage = "该保单在宽限期内!";
	// // this.mErrors.addOneError(tError) ;
	// // return false;
	// // }
	//
	// //犹豫期判断
	// // logger.debug("CustomGetPolDate: " +
	// mLCPolSchema.getCustomGetPolDate());
	// // if (mLCPolSchema.getCustomGetPolDate() != null) {
	// // if (PubFun.calInterval(mLCPolSchema.getCustomGetPolDate(),
	// PubFun.getCurrentDate(), "D") < 10) {
	// // // @@错误处理
	// // CError tError = new CError();
	// // tError.moduleName = "CheckFieldBL";
	// // tError.functionName = "prepareData";
	// // tError.errorMessage = "该保单在犹豫期内!";
	// // this.mErrors.addOneError(tError) ;
	// // return false;
	// // }
	// // }
	//
	// if (!this.CalVerify("N")) return false;
	// }
	// /* else if (mLPEdorMainSchema.getEdorType().equals("AC"))
	// {
	// String polno = mLCPolSchema.getPolNo();
	// String mainPolNo = mLCPolSchema.getMainPolNo();
	// if (!polno.equals(mainPolNo)) {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "附险保单不能单独进行投保人变更!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	//
	// tSucc = true;
	//
	// } */
	// else
	// {
	// tSucc= true;
	// logger.debug("-------------no descriptions--------");
	// }
	// if (tSucc)
	// {
	// mBqCalBase.setInterval(tInterval);
	// if (!this.CalVerify("Y"))
	// return false;
	// }
	// }
	// else
	// {
	// continue;
	// }
	// }
	// }
	//
	// /* } catch (Exception e ) {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "CheckFieldBL";
	// tError.functionName = "prepareData";
	// tError.errorMessage = "保全修改失败!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	// */
	// return true;
	// }

	// 判断是否有垫款
	protected boolean hasTrayMoney() {
		logger.debug("-------start tray check----");

		// 获取借款业务的借款总额
		LOLoanDB tLOLoanDB = new LOLoanDB();
		tLOLoanDB.setPolNo(mLCPolSchema.getPolNo());
		tLOLoanDB.setLoanType("1"); // 1 代表还垫款
		tLOLoanDB.setPayOffFlag("0");

		LOLoanSet tLOLoanSet = tLOLoanDB.query();

		logger.debug("tLoLoanSet :" + tLOLoanSet.size());
		for (int j = 1; j <= tLOLoanSet.size(); j++) {
			mTrayMoney += (double) tLOLoanSet.get(j).getLeaveMoney();
			logger.debug(tLOLoanSet.get(j).getLeaveMoney());
		}
		logger.debug("mTrayMoney :" + mTrayMoney);

		// 获取保全项目表中申请确认，但未保全确认的还款数据总额
		LPEdorItemDB tLPEdorItemDB1 = new LPEdorItemDB();
		tLPEdorItemDB1.setPolNo(mLCPolSchema.getPolNo());
		tLPEdorItemDB1.setEdorType("TR"); // TR为还款
		tLPEdorItemDB1.setEdorState("2"); // 2为申请确认状态
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB1.query();
		logger.debug("tLPEdorMainSet size:" + tLPEdorItemSet.size());

		if (mLPEdorItemSchema.getEdorNo() != null) {
			tLPEdorItemDB1.setPolNo(mLCPolSchema.getPolNo());
			tLPEdorItemDB1.setEdorType("TR"); // RF为还款
			tLPEdorItemDB1.setEdorState("1"); // 1为申请状态
			tLPEdorItemSet.add(tLPEdorItemDB1.query());
		}

		// 获取保全还款的具体数据
		LPReturnLoanSet tLPReturnLoanSet = new LPReturnLoanSet();
		for (int j = 0; j < tLPEdorItemSet.size(); j++) {
			logger.debug("here");
			LPEdorItemSchema tLPEdorItemSchema1 = tLPEdorItemSet.get(j + 1);
			LPReturnLoanDB tLPReturnLoanDB = new LPReturnLoanDB();

			tLPReturnLoanDB.setPolNo(tLPEdorItemSchema1.getPolNo());
			tLPReturnLoanDB.setEdorNo(tLPEdorItemSchema1.getEdorNo());
			tLPReturnLoanDB.setEdorType("TR");
			tLPReturnLoanSet.add(tLPReturnLoanDB.query());
		}
		for (int j = 1; j <= tLPReturnLoanSet.size(); j++) {
			mTrayMoney -= tLPReturnLoanSet.get(j).getReturnMoney();
		}

		logger.debug("mTrayMoney :" + mTrayMoney);
		if (mTrayMoney > 0) {
			return true;
		}
		return true;

	}

	// 判断是否有借款
	protected boolean hasLoanMoney() {
		logger.debug("-------start loan check----");
		// 如果本次批改中有借款LF项目，那么认为就有借款
		LPEdorItemDB tLPEdorItemDB2 = new LPEdorItemDB();
		tLPEdorItemDB2.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB2.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB2.setEdorType("LF");
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB2.query();
		if (tLPEdorItemSet != null && tLPEdorItemSet.size() > 0) {
			mLoanMoney = 1;
			return true;
		}

		// 校验未保全确认的借款项目
		LPEdorItemDB tLPEdorItemDB3 = new LPEdorItemDB();
		tLPEdorItemDB3.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB3.setEdorType("LF");
		tLPEdorItemDB3.setEdorState("2");
		LPEdorItemSet tLPEdorItemSet2 = tLPEdorItemDB3.query();
		if (tLPEdorItemSet2.size() > 0) {
			mLoanMoney = 1;
			return true;
		}

		// 获取借款业务的借款总额
		LOLoanDB tLOLoanDB = new LOLoanDB();
		tLOLoanDB.setPolNo(mLCPolSchema.getPolNo());
		tLOLoanDB.setLoanType("0");
		tLOLoanDB.setPayOffFlag("0");
		LOLoanSet tLOLoanSet = tLOLoanDB.query();
		logger.debug("tLoLoanSet :" + tLOLoanSet.size());
		for (int j = 1; j <= tLOLoanSet.size(); j++) {
			mLoanMoney += (double) tLOLoanSet.get(j).getLeaveMoney();
		}
		logger.debug("mLoanMoney :" + mLoanMoney);

		// 获取保全项目表中申请确认，但未保全确认的还款数据总额
		LPEdorItemDB tLPEdorItemDB1 = new LPEdorItemDB();
		tLPEdorItemDB1.setPolNo(mLCPolSchema.getPolNo());
		tLPEdorItemDB1.setEdorType("RF"); // RF为还款
		tLPEdorItemDB1.setEdorState("2"); // 2为申请确认状态
		LPEdorItemSet tLPEdorItemSet1 = tLPEdorItemDB1.query();
		logger.debug("tLPEdorMainSet1 size:" + tLPEdorItemSet1.size());

		if (mLPEdorItemSchema.getEdorNo() != null) {
			tLPEdorItemDB1.setPolNo(mLCPolSchema.getPolNo());
			tLPEdorItemDB1.setEdorType("RF"); // RF为还款
			tLPEdorItemDB1.setEdorState("1"); // 1为申请状态
			tLPEdorItemSet.add(tLPEdorItemDB1.query());
		}

		// 获取保全还款的具体数据
		LPReturnLoanSet tLPReturnLoanSet = new LPReturnLoanSet();
		for (int j = 0; j < tLPEdorItemSet.size(); j++) {
			logger.debug("here");
			LPEdorItemSchema tLPEdorItemSchema1 = tLPEdorItemSet.get(j + 1);
			LPReturnLoanDB tLPReturnLoanDB = new LPReturnLoanDB();

			tLPReturnLoanDB.setPolNo(tLPEdorItemSchema1.getPolNo());
			tLPReturnLoanDB.setEdorNo(tLPEdorItemSchema1.getEdorNo());
			tLPReturnLoanDB.setEdorType("RF");
			tLPReturnLoanSet.add(tLPReturnLoanDB.query());
		}
		for (int j = 1; j <= tLPReturnLoanSet.size(); j++) {
			mLoanMoney -= tLPReturnLoanSet.get(j).getReturnMoney();
		}

		logger.debug("mLoanMoney :" + mLoanMoney);
		if (mLoanMoney > 0) {
			return true;
		}
		return true;
	}

	/**
	 * grpHasTrayMoney
	 * 
	 * @return boolean
	 */
	private boolean grpHasTrayMoney() {
		return true;
	}

	/**
	 * grpHasLoanMoney
	 * 
	 * @return boolean
	 */
	private boolean grpHasLoanMoney() {
		return false;
	}

	public static void main(String[] args) {
		VData tVCheckData = new VData();

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setPolNo("210110000000556");
		tLPEdorItemSchema.setContNo("230110000000167");
		tLPEdorItemSchema.setEdorType("ZT");
		tLPEdorItemSchema.setEdorAppDate("2005-1-10");
		tLPEdorItemSchema.setEdorValiDate("2005-1-10");
		tLPEdorItemSchema.setOperator("001");

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		tVCheckData.addElement(tGlobalInput);
		tVCheckData.addElement(tLPEdorItemSchema);
		tVCheckData.addElement("VERIFY||BEGIN");
		tVCheckData.addElement("PEDORINPUT#EDORTYPE");
		CheckFieldUI tCheckFieldUI = new CheckFieldUI();
		tCheckFieldUI.submitData(tVCheckData, "CHECK||FIELD");
	}

}
