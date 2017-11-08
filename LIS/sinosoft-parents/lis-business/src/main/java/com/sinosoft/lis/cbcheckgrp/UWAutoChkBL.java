package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWErrorDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWErrorDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDUWGradePersonDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMUWDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCUWErrorSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWErrorSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.vschema.LCCUWErrorSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LDUWGradePersonSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保个人单自动核保部分
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
 * @modified by ZhangRong 2004.11
 * @version 2.0
 */
public class UWAutoChkBL {
private static Logger logger = Logger.getLogger(UWAutoChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperator;
	private String mPolPassFlag = "0"; // 险种通过标记
	private String mContPassFlag = "0"; // 合同通过标记
	private String mUWGrade = "";
	private String mCalCode; // 计算编码
	private String mUser;
	private FDate fDate = new FDate();
	private double mValue;
	private LCContSet mAllLCContSet = new LCContSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
	private String mGrpContNo = "";
	private String mContNo = "";
	private String mPContNo = "";
	private String mOldPolNo = "";
	private boolean mGrpContFlag = false;
	private String mApproveFlag = "";
	/** 合同核保主表 */
	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSet mAllLCCUWMasterSet = new LCCUWMasterSet();

	/** 合同核保子表 */
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();
	private LCCUWSubSet mAllLCCUWSubSet = new LCCUWSubSet();

	/** 合同核保错误信息表 */
	private LCCUWErrorSet mLCCUWErrorSet = new LCCUWErrorSet();
	private LCCUWErrorSet mAllLCCUWErrorSet = new LCCUWErrorSet();

	/** 各险种核保主表 */
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterSet mAllLCUWMasterSet = new LCUWMasterSet();

	/** 各险种核保子表 */
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCUWSubSet mAllLCUWSubSet = new LCUWSubSet();

	/** 核保错误信息表 */
	private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();
	private LCUWErrorSet mAllLCErrSet = new LCUWErrorSet();
	private CalBase mCalBase = new CalBase();

	public UWAutoChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("=====This is UWAutoChkBL->submitData=====\n");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		// 得到外部传入的数据,将数据备份到本类中
		LCContSchema tLCContSchema = getInputData(cInputData);
		if (tLCContSchema == null) {
			return false;
		}

		// 验证数据
		if (!checkData(tLCContSchema)) {
			return false;
		}

		// 数据处理
		if (!dealData(tLCContSchema)) {
			return false;
		}
		// logger.debug("=====UWAutoChkBL dealData=====\n");
		// 准备给后台的数据
		mResult.clear();
		if (prepareOutputData(tLCContSchema)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "提交的数据准备失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.add(mMap);
		PubSubmit tSubmit = new PubSubmit();

		// 数据提交
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		cInputData.clear();

		// 给调用程序（团单自动核保）回传核保结论。
		cInputData.add(mContPassFlag);

		// 给调用程序（团单自动核保）回传核保级别
		cInputData.add(mUWGrade);

		return true;
	}

	/**
	 * 数据操作类业务处理(针对团单下的个单) 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(LCContSchema tLCContSchema) {
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolSchema tLCPolSchema = null;

		//初始化自核级别
		mUWGrade = "B1";
		// 复核通过标志
		mApproveFlag = "9";
		// 获得合同保单号
		mContNo = tLCContSchema.getContNo();
		// 获得团单合同号
		mGrpContNo = tLCContSchema.getGrpContNo();
		if (!mGrpContNo.equals(SysConst.ZERONO)) {

			// 判断是否团单
			mGrpContFlag = true;
		}
		// 合同投保单号
		this.mPContNo = tLCContSchema.getProposalContNo();
		tLCPolDB.setContNo(mContNo);
		// 查询合同下的所有险种
		tLCPolSet = tLCPolDB.query();
		int nPolCount = tLCPolSet.size();
		int nPolIndex = 0;
		// 未通过的核保规则
		LMUWSet tLMUWSetUnpass = new LMUWSet();
		// 所有核保规则
		LMUWSet tLMUWSetAll = null;
		// 需要风险检测的特殊核保规则
		LMUWSet tLMUWSetSpecial = null;
		LMUWSchema tLMUWSchema = null;
		mContPassFlag = "0";
		for (nPolIndex = 1; nPolIndex <= nPolCount; nPolIndex++) {
			tLCPolSchema = tLCPolSet.get(nPolIndex);
			// 获得保单险种号
			mOldPolNo = tLCPolSchema.getPolNo();
			// 获得险种号
			String tkinds = tLCPolSchema.getRiskCode();
			// 准备算法，获取某险种的所有核保规则的集合
			tLMUWSetUnpass.clear();
			if (tLMUWSetAll != null) {
				tLMUWSetAll.clear();
			}
			if (tLMUWSetSpecial != null) {
				tLMUWSetSpecial.clear();
			}
			// 获得险种核保规则
			tLMUWSetAll = CheckKinds(tLCPolSchema);
			if (tLMUWSetAll == null) {
				return false;
			}
			// 准备数据，从险种信息中获取各项计算信息
			CheckPolInit(tLCPolSchema);
			// 个人单核保
			// 核保通过标志，初始为未核保
			this.mPolPassFlag = "0";
			// 核保规则数量
			int n = tLMUWSetAll.size();
			if (n == 0) {
				// 无核保规则时置标志为通过
				mPolPassFlag = "9";
			} else {

				// 目前所有的险种均有一些公共的核保规则,所以必定走该分枝
				int j = 0;
				for (int i = 1; i <= n; i++) {
					// 取计算编码
					tLMUWSchema = new LMUWSchema();
					tLMUWSchema = tLMUWSetAll.get(i);
					// 得到险种核保算法
					this.mCalCode = tLMUWSchema.getCalCode();
					// 调用个单自核函数
					if (CheckPol(tLCPolSchema.getInsuredNo(), tLCPolSchema
							.getRiskCode()) == 0) {
						// 通过检验核保规则
					} else {
						// 核保不通过
						//没有通过，需要将lmuw同当前的mUWGrade作比较，将最大的保存到mUWGrade中
						String tUWGrade =tLMUWSchema.getUWGrade();
						if ((tUWGrade == null)
								|| (mUWGrade.compareTo(tUWGrade) < 0)) {
							// 当需要人工核保时候当即tLMUWSetUnpass.size()>0时,mUWGrade应该不为null,
							// 否则是自动核保规则中核保级别字段缺少了数据
							mUWGrade = tUWGrade;
						}
						j++;
						tLMUWSetUnpass.add(tLMUWSchema);
						// 待人工核保
						mPolPassFlag = "5";
						// 合同单待人工核保
						mContPassFlag = "5";
					}
				}

				// 需要人工核保时候，校验核保返回核保员核保级别，由于必须人工核保，都要返回核保权限
				// 设置为最低级别
//				mUWGrade = "A";
				if (!mPolPassFlag.trim().equals("5")) {
					// 核保通过
					mPolPassFlag = "9";
				}
			}
			if (dealOnePol(tLCPolSchema, tLMUWSetUnpass) == false) {

				// 更新核保记录
				return false;
			}
		}

		/* 合同核保 */
		// 未通过的合同核保规则
		LMUWSet tLMUWSetContUnpass = new LMUWSet();

		// 所有合同核保规则
		LMUWSet tLMUWSetContAll = CheckKinds3();

		// 准备数据，从险种信息中获取各项计算信息
		if (mPolPassFlag.trim().equals("5")) {
			mContPassFlag = "5";
		}

		// 个人合同核保
		// 记录核保规则数量
		int tCount = tLMUWSetContAll.size();

		if (tCount == 0) {
			// 核保通过
			// mContPassFlag = "9";
			// 个人的核保结论不是自己的集合，它是与险种相关的，如果有险种未通过则认为未通过。
			// 无核保规则则置标志为通过
		} else {
			// 目前所有的险种均有一些公共的核保规则,所以必定走该分枝
			int j = 0;
			for (int index = 1; index <= tCount; index++) {
				// 取计算编码
				tLMUWSchema = new LMUWSchema();
				tLMUWSchema = tLMUWSetContAll.get(index);
				this.mCalCode = tLMUWSchema.getCalCode();
				if (CheckPol(tLCContSchema.getInsuredNo(), "000000") == 0) {
				} else {
//					没有通过，需要将lmuw同当前的mUWGrade作比较，将最大的保存到mUWGrade中
					String tUWGrade =tLMUWSchema.getUWGrade();
					if ((tUWGrade == null)
							|| (mUWGrade.compareTo(tUWGrade) < 0)) {
						// 当需要人工核保时候当即tLMUWSetUnpass.size()>0时,mUWGrade应该不为null,
						// 否则是自动核保规则中核保级别字段缺少了数据
						mUWGrade = tUWGrade;
					}
					j++;
					tLMUWSetContUnpass.add(tLMUWSchema);

					// 核保不通过，待人工核保
					mContPassFlag = "5";
					// 取核保级别
				}
			}
			if (!mContPassFlag.trim().equals("5")) {
				mContPassFlag = "9";
			}
		}
		dealOneCont(tLCContSchema, tLMUWSetContUnpass);
		return true;
	}

	/**
	 * 根据保额校验核保级别
	 * 
	 * @return
	 */
	private String checkRiskAmnt(LCPolSchema tLCPolSchema) {
		String tUWGrade = "";

		// 计算
		// Calculator mCalculator = new Calculator();
		// mCalculator.setCalCode(mCalCode);
		//
		// //增加基本要素
		// mCalculator.addBasicFactor("Get", mCalBase.getGet());
		// mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		// mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		// mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		// mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		// mCalculator.addBasicFactor("Job", mCalBase.getJob());
		// mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		// mCalculator.addBasicFactor("GetStartDate", "");
		// mCalculator.addBasicFactor("Years", mCalBase.getYears());
		// mCalculator.addBasicFactor("Grp", "");
		// mCalculator.addBasicFactor("GetFlag", "");
		// mCalculator.addBasicFactor("ValiDate", "");
		// mCalculator.addBasicFactor("Count", mCalBase.getCount());
		// mCalculator.addBasicFactor("FirstPayDate", "");
		// mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo());
		// mCalculator.addBasicFactor("InsuredNo", tLCPolSchema.getInsuredNo());
		// mCalculator.addBasicFactor("RiskCode", tLCPolSchema.getRiskCode());
		//
		// String tStr = "";
		// tStr = mCalculator.calculate();
		//
		// if (tStr.trim().equals(""))
		// {
		// tUWGrade = "";
		// }
		// else
		// {
		// tUWGrade = tStr.trim();
		// }
		// 取那么多的SQL执行效率低，现改为直接从数据库中取值
		// 1:判断险类 2：查询LDUWGRADEPERSON
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		String sql = "select * From lmriskapp where riskcode='"
				+ tLCPolSchema.getRiskCode() + "'"; // 1:核保 2：核赔
		LMRiskAppSet tLMRiskAppSet = new LMRiskAppSet();
		tLMRiskAppSet = tLMRiskAppDB.executeQuery(sql);
		logger.debug(sql);
		if (tLMRiskAppDB.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "CheckKinds";
			tError.errorMessage = "险种分类信息查询失败!";
			this.mErrors.addOneError(tError);
		}
		if (tLMRiskAppSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "CheckKinds";
			tError.errorMessage = "险种分类信息查询失败!";
			this.mErrors.addOneError(tError);
		}
		String temrikstype = tLMRiskAppSet.get(1).getRiskType();
		String risktype = "10";
		if (temrikstype.trim().equals("H")) {
			risktype = "30";
		}
		LDUWGradePersonDB tLDUWGradePersonDB = new LDUWGradePersonDB();
		sql = "select * From lduwgradeperson where uwkind='" + risktype
				+ "' and maxamnt>=" + tLCPolSchema.getAmnt()
				+ " and uwtype='1'";
		LDUWGradePersonSet tLDUWGradePersonSet = new LDUWGradePersonSet();
		tLDUWGradePersonSet = tLDUWGradePersonDB.executeQuery(sql);
		logger.debug(sql);
		if (tLDUWGradePersonSet.size() == 0) {
			tUWGrade = "A";
		} else {
			tUWGrade = tLDUWGradePersonSet.get(1).getUWPopedom();
		}
		logger.debug("自核的返回的核保级别:" + tUWGrade);
		return tUWGrade;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol(LCPolSchema tLCPolSchema, LMUWSet tLMUWSetUnpass) {
		// 保单
		if (preparePol(tLCPolSchema) == false) {
			return false;
		}

		// 核保信息
		if (preparePolUW(tLCPolSchema, tLMUWSetUnpass) == false) {
			return false;
		}
		LCPolSchema tLCPolSchemaDup = new LCPolSchema();
		tLCPolSchemaDup.setSchema(tLCPolSchema);
		mAllLCPolSet.add(tLCPolSchemaDup);
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		tLCUWMasterSet.set(mLCUWMasterSet);
		mAllLCUWMasterSet.add(tLCUWMasterSet);
		LCUWSubSet tLCUWSubSet = new LCUWSubSet();
		tLCUWSubSet.set(mLCUWSubSet);
		mAllLCUWSubSet.add(tLCUWSubSet);
		LCUWErrorSet tLCUWErrorSet = new LCUWErrorSet();
		tLCUWErrorSet.set(mLCUWErrorSet);
		mAllLCErrSet.add(tLCUWErrorSet);
		return true;
	}

	/**
	 * 操作一张合同单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOneCont(LCContSchema tLCContSchema,
			LMUWSet tLMUWSetContUnpass) {
		if (!prepareContUW(tLCContSchema, tLMUWSetContUnpass)) {
			return false;
		}

		LCContSchema tLCContSchemaDup = new LCContSchema();
		tLCContSchemaDup.setSchema(tLCContSchema);
		mAllLCContSet.add(tLCContSchemaDup);
		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet.set(mLCCUWMasterSet);
		mAllLCCUWMasterSet.add(tLCCUWMasterSet);
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet.set(mLCCUWSubSet);
		mAllLCCUWSubSet.add(tLCCUWSubSet);
		LCCUWErrorSet tLCCUWErrorSet = new LCCUWErrorSet();
		tLCCUWErrorSet.set(mLCCUWErrorSet);
		mAllLCCUWErrorSet.add(tLCCUWErrorSet);
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private LCContSchema getInputData(VData cInputData) { // rewrited by
															// zhangrong
															// 2004.11.16
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperator = tGlobalInput.Operator;

		LCContSchema tLCContSchema = (LCContSchema) cInputData
				.getObjectByObjectName("LCContSchema", 0); // 从输入数据中获取合同记录的数据
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tLCContSchema.getContNo());

		if (tLCContDB.getInfo()) { // 验证LCCont表中是否存在该合同项记录
			return tLCContDB.getSchema();
		} else {
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "合同号为" + tLCContSchema.getContNo() + "未查询到!";
			this.mErrors.addOneError(tError);

			return null;
		}
	}

	private boolean checkData(LCContSchema tLCContSchema) {
		// 校验核保级别
		// if (!checkUWGrade()) {
		// return false;
		// }

		// 校验是否复核
		// if (!checkApprove(tLCContSchema))
		// {
		// return false;
		// }

		return true;
	}

	/**
	 * 校验投保单是否复核 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkApprove(LCContSchema tLCContSchema) {
		if ((tLCContSchema.getApproveFlag() == null)
				|| !tLCContSchema.getApproveFlag().equals("9")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "checkApprove";
			tError.errorMessage = "投保单尚未进行复核操作，不能核保!（投保单号："
					+ tLCContSchema.getProposalContNo().trim() + "）";
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

		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();

		if ((tUWPopedom == null) || tUWPopedom.equals("")) {
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "操作员无核保权限，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 核保险种信息校验,准备核保算法 输出：如果发生错误则返回false,否则返回true
	 */
	private LMUWSet CheckKinds(LCPolSchema tLCPolSchema) {
		String tsql = "";
		LMUWSchema tLMUWSchema = new LMUWSchema();

		// 查询算法编码
		if (!mGrpContFlag) {
			tsql = "select * from lmuw where (riskcode = '000000' and relapoltype = 'I' and uwtype = '11') or (riskcode = '"
					+ tLCPolSchema.getRiskCode().trim()
					+ "' and relapoltype = 'I' and uwtype = '1')  order by calcode";
		} else {
			// 个单险种规则
			tsql = "select * from lmuw where (riskcode = '000000' and relapoltype = 'I' and uwtype = '15') or (riskcode = '"
					+ tLCPolSchema.getRiskCode().trim()
					+ "' and relapoltype = 'I' and uwtype = '15')  order by calcode";
		} // 团单下个单险种规则
		LMUWDB tLMUWDB = new LMUWDB();
		LMUWSet tLMUWSet = tLMUWDB.executeQuery(tsql);
		if (tLMUWDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMUWDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "CheckKinds";
			tError.errorMessage = tLCPolSchema.getRiskCode().trim()
					+ "险种核保信息查询失败!";
			this.mErrors.addOneError(tError);
			tLMUWSet.clear();
			return null;
		}
		return tLMUWSet;
	}

	/**
	 * 核保险种信息校验,准备核保算法 输出：如果发生错误则返回false,否则返回true
	 */
	private LMUWSet CheckKinds2(LCPolSchema tLCPolSchema) {
		String tsql = "";
		LMUWSchema tLMUWSchema = new LMUWSchema();

		// 查询算法编码
		if (!mGrpContFlag) {
			tsql = "select * from lmuw where riskcode = '000000' and relapoltype = 'I' and uwtype = '12'";
		} else {
			// 个单险种上报核保级别规则
			tsql = "select * from lmuw where riskcode = '000000' and relapoltype = 'I' and uwtype = '16'";
		}
		// 团单下个单险种上报核保级别规则
		LMUWDB tLMUWDB = new LMUWDB();
		LMUWSet tLMUWSet = tLMUWDB.executeQuery(tsql);
		if (tLMUWDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMUWDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "CheckKinds2";
			tError.errorMessage = tLCPolSchema.getRiskCode().trim()
					+ "险种信息核保查询失败!";
			this.mErrors.addOneError(tError);
			tLMUWSet.clear();
			return null;
		}
		return tLMUWSet;
	}

	/**
	 * 核保险种信息校验,准备核保算法 输出：如果发生错误则返回false,否则返回true
	 */
	private LMUWSet CheckKinds3() {
		String tsql = "";
		LMUWSchema tLMUWSchema = new LMUWSchema();

		// 查询算法编码，合同单核保规则
		tsql = "select * from lmuw where riskcode = '000000' and relapoltype = 'I' and uwtype = '19'";
		LMUWDB tLMUWDB = new LMUWDB();
		LMUWSet tLMUWSet = tLMUWDB.executeQuery(tsql);
		if (tLMUWDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMUWDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "CheckKinds3";
			tError.errorMessage = "合同险种核保信息查询失败!";
			this.mErrors.addOneError(tError);
			tLMUWSet.clear();
			return null;
		}
		return tLMUWSet;
	}

	/**
	 * 个人单核保数据准备 输出：如果发生错误则返回false,否则返回true
	 */
	private void CheckPolInit(LCPolSchema tLCPolSchema) {
		this.mCalBase = new CalBase();
		this.mCalBase.setPrem(tLCPolSchema.getPrem());
		this.mCalBase.setGet(tLCPolSchema.getAmnt());
		this.mCalBase.setMult(tLCPolSchema.getMult());
		this.mCalBase.setAppAge(tLCPolSchema.getInsuredAppAge());
		this.mCalBase.setPolTypeFlag(tLCPolSchema.getPolTypeFlag());
		this.mCalBase.setSex(tLCPolSchema.getInsuredSex());
		this.mCalBase.setJob(tLCPolSchema.getOccupationType());
		this.mCalBase.setCount(tLCPolSchema.getInsuredPeoples());
		this.mCalBase.setPolNo(tLCPolSchema.getPolNo());
		this.mCalBase.setContNo(mContNo);
	}

	/**
	 * 个人单核保数据准备 输出：如果发生错误则返回false,否则返回true
	 */
	private void CheckContInit(LCContSchema tLCContSchema) {
		mCalBase = new CalBase();
		mCalBase.setPrem(tLCContSchema.getPrem());
		mCalBase.setGet(tLCContSchema.getAmnt());
		mCalBase.setMult(tLCContSchema.getMult());
		mCalBase.setSex(tLCContSchema.getInsuredSex());
		mCalBase.setContNo(mContNo);
	}

	/**
	 * 个人单核保 输出：如果发生错误则返回false,否则返回true
	 */
	private double CheckPol(String tInsuredNo, String tRiskCode) {
		// LCPolSchema tLCPolSchema)
		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);

		// 增加基本要素
		mCalculator.addBasicFactor("Get", mCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("PolTypeFlag", mCalBase.getPolTypeFlag());
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
		mCalculator.addBasicFactor("ContNo", mCalBase.getContNo());
		mCalculator.addBasicFactor("InsuredNo", tInsuredNo);
		mCalculator.addBasicFactor("RiskCode", tRiskCode);
		String tStr = "";
		tStr = mCalculator.calculate();
		if ((tStr == null) || tStr.trim().equals("")) {
			this.mValue = 0;
		} else {
			this.mValue = Double.parseDouble(tStr);
		}
		// logger.debug(mValue);
		return this.mValue;
	}

	/**
	 * 准备保单信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePol(LCPolSchema tLCPolSchema) {
		/*-- add by heyq for NCL 将复核功能和自核合并--*/
		tLCPolSchema.setApproveFlag(mApproveFlag);
		tLCPolSchema.setApproveCode(mOperator);
		tLCPolSchema.setApproveDate(PubFun.getCurrentDate());
		tLCPolSchema.setApproveTime(PubFun.getCurrentTime());
		/** --end add 20050530----------------------* */

		logger.debug("险种核保标志" + mPolPassFlag);
		if (mPolPassFlag == null || mPolPassFlag.equals("")) {
			tLCPolSchema.setUWFlag("9");
		} else {
			tLCPolSchema.setUWFlag(mPolPassFlag);
		}
		tLCPolSchema.setUWCode(mOperator);
		tLCPolSchema.setUWDate(PubFun.getCurrentDate());
		tLCPolSchema.setUWTime(PubFun.getCurrentTime());
		tLCPolSchema.setModifyDate(PubFun.getCurrentDate());
		tLCPolSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	/**
	 * 准备合同核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareContUW(LCContSchema tLCContSchema,
			LMUWSet tLMUWSetContUnpass) {
		/*-- add by heyq for NCL 将复核功能和自核合并--*/
		tLCContSchema.setApproveFlag(mApproveFlag);
		tLCContSchema.setApproveCode(mOperator);
		tLCContSchema.setApproveDate(PubFun.getCurrentDate());
		tLCContSchema.setApproveTime(PubFun.getCurrentTime());
		/** --end add 20050530----------------------* */
		if (mContPassFlag == null || mContPassFlag.equals("")
				|| mContPassFlag.equals("0")) {
			tLCContSchema.setUWFlag("9"); // 置核保标志
		} else {
			tLCContSchema.setUWFlag(mContPassFlag); // 置核保标志
		}

		tLCContSchema.setUWOperator(mOperator);
		tLCContSchema.setUWDate(PubFun.getCurrentDate());
		tLCContSchema.setUWTime(PubFun.getCurrentTime());
		tLCContSchema.setModifyDate(PubFun.getCurrentDate());
		tLCContSchema.setModifyTime(PubFun.getCurrentTime());

		// 合同核保主表
		boolean firstUW = true;
		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);

		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet = tLCCUWMasterDB.query();

		if (tLCCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareContUW";
			tError.errorMessage = mContNo + "合同核保总表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (tLCCUWMasterSet.size() == 0) {
			tLCCUWMasterSchema.setContNo(mContNo);
			tLCCUWMasterSchema.setGrpContNo(tLCContSchema.getGrpContNo());
			tLCCUWMasterSchema.setProposalContNo(tLCContSchema
					.getProposalContNo());
			tLCCUWMasterSchema.setUWNo(1);
			tLCCUWMasterSchema.setInsuredNo(tLCContSchema.getInsuredNo());
			tLCCUWMasterSchema.setInsuredName(tLCContSchema.getInsuredName());
			tLCCUWMasterSchema.setAppntNo(tLCContSchema.getAppntNo());
			tLCCUWMasterSchema.setAppntName(tLCContSchema.getAppntName());
			tLCCUWMasterSchema.setAgentCode(tLCContSchema.getAgentCode());
			tLCCUWMasterSchema.setAgentGroup(tLCContSchema.getAgentGroup());
			tLCCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCCUWMasterSchema.setPostponeDay("");
			tLCCUWMasterSchema.setPostponeDate("");
			tLCCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCCUWMasterSchema.setState(mContPassFlag);
			tLCCUWMasterSchema.setPassFlag(mContPassFlag);
			tLCCUWMasterSchema.setHealthFlag("0");
			tLCCUWMasterSchema.setSpecFlag("0");
			tLCCUWMasterSchema.setQuesFlag("0");
			tLCCUWMasterSchema.setReportFlag("0");
			tLCCUWMasterSchema.setChangePolFlag("0");
			tLCCUWMasterSchema.setPrintFlag("0");
			tLCCUWMasterSchema.setPrintFlag2("0");
			tLCCUWMasterSchema.setManageCom(tLCContSchema.getManageCom());
			tLCCUWMasterSchema.setUWIdea("");
			tLCCUWMasterSchema.setUpReportContent("");
			tLCCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			firstUW = false;
			tLCCUWMasterSchema = tLCCUWMasterSet.get(1);
			tLCCUWMasterSchema.setUWNo(tLCCUWMasterSchema.getUWNo() + 1);
			tLCCUWMasterSchema.setState(mContPassFlag);
			tLCCUWMasterSchema.setPassFlag(mContPassFlag);
			tLCCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		}

		mLCCUWMasterSet.clear();
		mLCCUWMasterSet.add(tLCCUWMasterSchema);

		// 合同核保轨迹表
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mContNo);

		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();

		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareContUW";
			tError.errorMessage = mContNo + "合同核保轨迹表查失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int nUWNo = tLCCUWSubSet.size();

		if (nUWNo > 0) {
			tLCCUWSubSchema.setUWNo(++nUWNo); // 第几次核保
		} else {
			tLCCUWSubSchema.setUWNo(1); // 第1次核保
		}

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
		tLCCUWSubSchema.setPostponeDate(tLCCUWMasterSchema.getPostponeDate());
		tLCCUWSubSchema.setUpReportContent(tLCCUWMasterSchema
				.getUpReportContent());
		tLCCUWSubSchema.setHealthFlag(tLCCUWMasterSchema.getHealthFlag());
		tLCCUWSubSchema.setSpecFlag(tLCCUWMasterSchema.getSpecFlag());
		tLCCUWSubSchema.setSpecReason(tLCCUWMasterSchema.getSpecReason());
		tLCCUWSubSchema.setQuesFlag(tLCCUWMasterSchema.getQuesFlag());
		tLCCUWSubSchema.setReportFlag(tLCCUWMasterSchema.getReportFlag());
		tLCCUWSubSchema.setChangePolFlag(tLCCUWMasterSchema.getChangePolFlag());
		tLCCUWSubSchema.setChangePolReason(tLCCUWMasterSchema
				.getChangePolReason());
		tLCCUWSubSchema.setAddPremReason(tLCCUWMasterSchema.getAddPremReason());
		tLCCUWSubSchema.setPrintFlag(tLCCUWMasterSchema.getPrintFlag());
		tLCCUWSubSchema.setPrintFlag2(tLCCUWMasterSchema.getPrintFlag2());
		tLCCUWSubSchema.setUWIdea(tLCCUWMasterSchema.getUWIdea());
		tLCCUWSubSchema.setOperator(tLCCUWMasterSchema.getOperator()); // 操作员
		tLCCUWSubSchema.setManageCom(tLCCUWMasterSchema.getManageCom());
		tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		mLCCUWSubSet.clear();
		mLCCUWSubSet.add(tLCCUWSubSchema);

		// 核保错误信息表
		LCCUWErrorSchema tLCCUWErrorSchema = new LCCUWErrorSchema();
		LCCUWErrorDB tLCCUWErrorDB = new LCCUWErrorDB();
		tLCCUWErrorDB.setContNo(mContNo);

		LCCUWErrorSet tLCCUWErrorSet = new LCCUWErrorSet();
		tLCCUWErrorSet = tLCCUWErrorDB.query();

		if (tLCCUWErrorDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWErrorDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareContUW";
			tError.errorMessage = mContNo + "合同错误信息表查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		tLCCUWErrorSchema.setSerialNo("0");

		if (nUWNo > 0) {
			tLCCUWErrorSchema.setUWNo(nUWNo);
		} else {
			tLCCUWErrorSchema.setUWNo(1);
		}

		tLCCUWErrorSchema.setContNo(mContNo);
		tLCCUWErrorSchema.setGrpContNo(tLCCUWSubSchema.getGrpContNo());
		tLCCUWErrorSchema
				.setProposalContNo(tLCCUWSubSchema.getProposalContNo());
		tLCCUWErrorSchema.setInsuredNo(tLCCUWSubSchema.getInsuredNo());
		tLCCUWErrorSchema.setInsuredName(tLCCUWSubSchema.getInsuredName());
		tLCCUWErrorSchema.setAppntNo(tLCCUWSubSchema.getAppntNo());
		tLCCUWErrorSchema.setAppntName(tLCCUWSubSchema.getAppntName());
		tLCCUWErrorSchema.setManageCom(tLCCUWSubSchema.getManageCom());
		tLCCUWErrorSchema.setUWRuleCode(""); // 核保规则编码
		tLCCUWErrorSchema.setUWError(""); // 核保出错信息
		tLCCUWErrorSchema.setCurrValue(""); // 当前值
		tLCCUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
		tLCCUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
		tLCCUWErrorSchema.setUWPassFlag(mPolPassFlag);

		// 取核保错误信息
		mLCCUWErrorSet.clear();

		int merrcount = tLMUWSetContUnpass.size();

		if (merrcount > 0) {
			for (int i = 1; i <= merrcount; i++) {
				// 取出错信息
				LMUWSchema tLMUWSchema = new LMUWSchema();
				tLMUWSchema = tLMUWSetContUnpass.get(i);

				// 生成流水号
				String tserialno = "" + i;

				tLCCUWErrorSchema.setSerialNo(tserialno);
				tLCCUWErrorSchema.setUWRuleCode(tLMUWSchema.getUWCode()); // 核保规则编码
				tLCCUWErrorSchema.setUWError(tLMUWSchema.getRemark().trim()); // 核保出错信息，即核保规则的文字描述内容
				tLCCUWErrorSchema.setUWGrade(tLMUWSchema.getUWGrade());
				tLCCUWErrorSchema.setCurrValue(""); // 当前值

				LCCUWErrorSchema ttLCCUWErrorSchema = new LCCUWErrorSchema();
				ttLCCUWErrorSchema.setSchema(tLCCUWErrorSchema);
				mLCCUWErrorSet.add(ttLCCUWErrorSchema);
			}
		}

		return true;
	}

	/**
	 * 准备险种核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePolUW(LCPolSchema tLCPolSchema,
			LMUWSet tLMUWSetUnpass) {
		int tuwno = 0;
		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setProposalNo(mOldPolNo);
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		tLCUWMasterSet = tLCUWMasterDB.query();
		if (tLCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mOldPolNo + "个人核保总表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		int n = tLCUWMasterSet.size();
		if (n == 0) {
			tLCUWMasterSchema.setContNo(mContNo);
			tLCUWMasterSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCUWMasterSchema.setPolNo(mOldPolNo);
			tLCUWMasterSchema.setProposalContNo(mPContNo);
			tLCUWMasterSchema.setProposalNo(tLCPolSchema.getProposalNo());
			tLCUWMasterSchema.setUWNo(1);
			tLCUWMasterSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCUWMasterSchema.setInsuredName(tLCPolSchema.getInsuredName());
			tLCUWMasterSchema.setAppntNo(tLCPolSchema.getAppntNo());
			tLCUWMasterSchema.setAppntName(tLCPolSchema.getAppntName());
			tLCUWMasterSchema.setAgentCode(tLCPolSchema.getAgentCode());
			tLCUWMasterSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
			tLCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCUWMasterSchema.setPostponeDay("");
			tLCUWMasterSchema.setPostponeDate("");
			tLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCUWMasterSchema.setState(mPolPassFlag);
			tLCUWMasterSchema.setPassFlag(mPolPassFlag);
			tLCUWMasterSchema.setHealthFlag("0");
			tLCUWMasterSchema.setSpecFlag("0");
			tLCUWMasterSchema.setQuesFlag("0");
			tLCUWMasterSchema.setReportFlag("0");
			tLCUWMasterSchema.setChangePolFlag("0");
			tLCUWMasterSchema.setPrintFlag("0");
			tLCUWMasterSchema.setManageCom(tLCPolSchema.getManageCom());
			tLCUWMasterSchema.setUWIdea("");
			tLCUWMasterSchema.setUpReportContent("");
			tLCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
			tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else if (n == 1) {
			tLCUWMasterSchema = tLCUWMasterSet.get(1);
			tuwno = tLCUWMasterSchema.getUWNo();
			tuwno = tuwno + 1;
			tLCUWMasterSchema.setUWNo(tuwno);
			tLCUWMasterSchema.setProposalContNo(mPContNo);
			tLCUWMasterSchema.setState(mPolPassFlag);
			tLCUWMasterSchema.setPassFlag(mPolPassFlag);
			tLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mOldPolNo + "个人核保总表取数据不唯一!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCUWMasterSet.clear();
		mLCUWMasterSet.add(tLCUWMasterSchema);
		// 核保轨迹表
		LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
		LCUWSubDB tLCUWSubDB = new LCUWSubDB();
		tLCUWSubDB.setProposalNo(mOldPolNo);
		LCUWSubSet tLCUWSubSet = new LCUWSubSet();
		tLCUWSubSet = tLCUWSubDB.query();
		if (tLCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mOldPolNo + "个人核保轨迹表查失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		int m = tLCUWSubSet.size();
		if (m > 0) {
			tLCUWSubSchema.setUWNo(++m); // 第几次核保
		} else {
			tLCUWSubSchema.setUWNo(1); // 第1次核保
		}
		tLCUWSubSchema.setContNo(mContNo);
		tLCUWSubSchema.setPolNo(mOldPolNo);
		tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
		tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema.getProposalContNo());
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
		tLCUWSubSchema.setChangePolFlag(tLCUWMasterSchema.getChangePolFlag());
		tLCUWSubSchema.setChangePolReason(tLCUWMasterSchema
				.getChangePolReason());
		tLCUWSubSchema.setAddPremReason(tLCUWMasterSchema.getAddPremReason());
		tLCUWSubSchema.setPrintFlag(tLCUWMasterSchema.getPrintFlag());
		tLCUWSubSchema.setPrintFlag2(tLCUWMasterSchema.getPrintFlag2());
		tLCUWSubSchema.setUWIdea(tLCUWMasterSchema.getUWIdea());
		tLCUWSubSchema.setOperator(tLCUWMasterSchema.getOperator()); // 操作员
		tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom());
		tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		mLCUWSubSet.clear();
		mLCUWSubSet.add(tLCUWSubSchema);

		// 核保错误信息表
		LCUWErrorSchema tLCUWErrorSchema = new LCUWErrorSchema();
		LCUWErrorDB tLCUWErrorDB = new LCUWErrorDB();
		tLCUWErrorDB.setProposalNo(mOldPolNo);
		LCUWErrorSet tLCUWErrorSet = new LCUWErrorSet();
		tLCUWErrorSet = tLCUWErrorDB.query();
		if (tLCUWErrorDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWErrorDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mOldPolNo + "个人错误信息表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCUWErrorSchema.setSerialNo("0");
		if (m > 0) {
			tLCUWErrorSchema.setUWNo(m);
		} else {
			tLCUWErrorSchema.setUWNo(1);
		}
		tLCUWErrorSchema.setContNo(mContNo);
		tLCUWErrorSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
		tLCUWErrorSchema.setProposalContNo(mPContNo);
		tLCUWErrorSchema.setPolNo(mOldPolNo);
		tLCUWErrorSchema.setProposalNo(tLCPolSchema.getProposalNo());
		tLCUWErrorSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
		tLCUWErrorSchema.setInsuredName(tLCPolSchema.getInsuredName());
		tLCUWErrorSchema.setAppntNo(tLCPolSchema.getAppntNo());
		tLCUWErrorSchema.setAppntName(tLCPolSchema.getAppntName());
		tLCUWErrorSchema.setManageCom(tLCPolSchema.getManageCom());
		tLCUWErrorSchema.setUWRuleCode(""); // 核保规则编码
		tLCUWErrorSchema.setUWError(""); // 核保出错信息
		tLCUWErrorSchema.setCurrValue(""); // 当前值
		tLCUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
		tLCUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
		tLCUWErrorSchema.setUWPassFlag(mPolPassFlag);

		// 取核保错误信息
		mLCUWErrorSet.clear();
		int merrcount = tLMUWSetUnpass.size();
		if (merrcount > 0) {
			for (int i = 1; i <= merrcount; i++) {
				// 取出错信息
				LMUWSchema tLMUWSchema = new LMUWSchema();
				tLMUWSchema = tLMUWSetUnpass.get(i);

				// 生成流水号
				String tserialno = "" + i;
				tLCUWErrorSchema.setSerialNo(tserialno);
				tLCUWErrorSchema.setUWRuleCode(tLMUWSchema.getUWCode()); // 核保规则编码
				tLCUWErrorSchema.setUWError(tLMUWSchema.getRemark().trim()); // 核保出错信息，即核保规则的文字描述内容
				tLCUWErrorSchema.setUWGrade(tLMUWSchema.getUWGrade());
				tLCUWErrorSchema.setCurrValue(""); // 当前值
				LCUWErrorSchema ttLCUWErrorSchema = new LCUWErrorSchema();
				ttLCUWErrorSchema.setSchema(tLCUWErrorSchema);
				mLCUWErrorSet.add(ttLCUWErrorSchema);
			}
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareOutputData(LCContSchema tLCContSchema) {
		mMap.put(tLCContSchema, "UPDATE");
		mMap.put(mLCCUWMasterSet.get(1), "DELETE&INSERT");
		mMap.put(mLCCUWSubSet, "INSERT");
		mMap.put(mLCCUWErrorSet, "INSERT");

		mMap.put(mAllLCPolSet, "UPDATE");

		// int n = mAllLCUWMasterSet.size();

		// for (int i = 1; i <= n; i++)
		// {
		// LCUWMasterSchema tLCUWMasterSchema = mAllLCUWMasterSet.get(i);
		// mMap.put(tLCUWMasterSchema, "DELETE&INSERT");
		// }
		mMap.put(mAllLCUWMasterSet, "DELETE&INSERT");

		mMap.put(mAllLCUWSubSet, "INSERT");
		mMap.put(mAllLCErrSet, "INSERT");

		return false;
	}
}
