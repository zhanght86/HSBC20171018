/*
 * @(#)EdorDetailBL.java	2005-06-28
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMRiskEdorItemDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LMRiskEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-保全项目明细保存公用处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：zhangtao
 * @version：1.0
 * @CreateDate：2005-06-28
 */
public class EdorDetailBL {
private static Logger logger = Logger.getLogger(EdorDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 传输数据 */
	LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	LPEdorItemSchema mLPEdorItemSchema_befor = new LPEdorItemSchema();
	// 计算要素
	private BqCalBase mBqCalBase = new BqCalBase();
	private String mOtherNoType = "";
	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public EdorDetailBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("EdorDetailBL after getInputData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("EdorDetailBL after dealData...");

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("EdorDetailBL after prepareOutputData...");

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError.buildErr(this, "数据提交失败");
			return false;
		}
		mInputData = null;

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		// 校验保全申请当前处理状态
		if (!checkEdorState()) {
			return false;
		}

		// 调用各保全项目对应的明细处理类
		if (!calPEdorXXDetailBL()) {
			return false;
		}

		// 执行各保全项目明细保存校验规则
		if (!exeCheckRule(mLPEdorItemSchema_befor)) {
			return false;
		}
		// ====DEL======zhangtao========2006-01-14========不用再校验=========BGN==========
		// if (!checkRisk())
		// {
		// return false;
		// }
		// ====DEL======zhangtao========2006-01-14========不用再校验=========BGN==========

		// 更新在当前保全项目之后又有变更的保全项目批改状态为未录入
		updEdorState();

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {

		// 只取出共通类用到的公用数据
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);

		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			mInputData.add(mResult);
		} catch (Exception ex) {
			CError tError = new CError();
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验保全项目基本信息
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkEdorState() {
		// ===add===zhangtao====2006-06-06====生产库发现无条件查询导致内存溢出，故在此先校验查询条件=====BGN=======
		if ((mLPEdorItemSchema.getEdorAcceptNo() == null || mLPEdorItemSchema
				.getEdorAcceptNo().equals(""))
				&& (mLPEdorItemSchema.getEdorNo() == null || mLPEdorItemSchema
						.getEdorNo().equals(""))
		// (mLPEdorItemSchema.getEdorType() == null ||
		// mLPEdorItemSchema.getEdorType().equals("")) &&
		// (mLPEdorItemSchema.getContNo() == null ||
		// mLPEdorItemSchema.getContNo().equals(""))
		// (mLPEdorItemSchema.getInsuredNo() == null ||
		// mLPEdorItemSchema.getInsuredNo().equals("")) &&
		// (mLPEdorItemSchema.getPolNo() == null ||
		// mLPEdorItemSchema.getPolNo().equals(""))
		) {
			CError.buildErr(this, "保全项目查询条件为空!");
			return false;
		}
		// ===add===zhangtao====2006-06-06====生产库发现无条件查询导致内存溢出，故在此先校验查询条件=====END=======
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		//modify by xiongzh 09-10-16 当为客户层保全时，外头传入的mLPEdorItemSchema.getContNo()为0000，不适用于查询
		//并且lpedoritem中InsuredNo为保单实际被保人号，而外头传入的mLPEdorItemSchema.getInsuredNo()固定为客户号
		//因此需要屏蔽ContNo，InsuredNo查询条件
		//tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		//tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());

		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			mErrors.addOneError(new CError("查询批改项目信息失败!"));
			return false;
		}
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "查询批改项目信息失败!");
			return false;
		}

		mLPEdorItemSchema_befor.setSchema(tLPEdorItemSet.get(1));

		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		if (!tLPEdorAppDB.getInfo()) {
			// @@错误处理
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全受理信息查询失败!";
			mErrors.addOneError(tError);
			return false;
		}

		mOtherNoType = tLPEdorAppDB.getOtherNoType();
		String sEdorState = tLPEdorAppDB.getEdorState();

		if (sEdorState.equals("1") || sEdorState.equals("3")
				|| sEdorState.equals("5")) {
			// 1-录入完成 3-等待录入 5-复核修改
			return true;
		} else {
			// 给出错误提示
			if (sEdorState.equals("2")) {
				CError tError = new CError();
				tError.errorMessage = "保全受理已申请确认，不能修改保全项目!";
				mErrors.addOneError(tError);
				return false;
			}
			if (sEdorState.equals("4")) {
				CError tError = new CError();
				tError.errorMessage = "保全受理已逾期终止，不能修改保全项目!";
				mErrors.addOneError(tError);
				return false;
			}
			if (sEdorState.equals("0")) {
				CError tError = new CError();
				tError.errorMessage = "保全受理已保全确认，不能修改保全项目!";
				mErrors.addOneError(tError);
				return false;
			}
			CError tError = new CError();
			tError.errorMessage = "保全受理在目前状态下不能修改保全项目!";
			mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 执行保全项目校验规则
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	public boolean exeCheckRule(LPEdorItemSchema tLPEdorItemSchema) {

		String sDisplayType = tLPEdorItemSchema.getDisplayType();
		StringBuffer sbSQL = new StringBuffer();
		// =====del=====zhangtao========2005-08-15================BGN====================
		// if (sDisplayType.equals("1")) //保单级
		// {
		// //查询该保单下所有险种的校验规则[包含共通的校验规则，险种编码为"000000"]
		// sbSQL
		// .append("select distinct calcode, msg from lmcheckfield ")
		// .append(" where trim(riskcode) in ")
		// .append(" (select distinct trim(riskcode) from lcpol where contno =
		// '")
		// .append(tLPEdorItemSchema.getContNo())
		// .append("' union select '000000' from dual ) ")
		// .append(" and fieldname = '")
		// .append(tLPEdorItemSchema.getEdorType())
		// .append("Detail")
		// .append("' ");
		// }
		// if (sDisplayType.equals("2")) //客户级
		// {
		// //查询该被保人所有险种的校验规则[包含共通的校验规则，险种编码为"000000"]
		// sbSQL
		// .append("select distinct calcode, msg from lmcheckfield ")
		// .append(" where trim(riskcode) in ")
		// .append(" (select distinct trim(riskcode) from lcpol where contno =
		// '")
		// .append(tLPEdorItemSchema.getContNo())
		// .append("' and insuredno = '")
		// .append(tLPEdorItemSchema.getInsuredNo())
		// .append("' union select '000000' from dual ) ")
		// .append(" and fieldname = '")
		// .append(tLPEdorItemSchema.getEdorType())
		// .append("Detail")
		// .append("' ");
		// }
		// if (sDisplayType.equals("3")) //险种级
		// {
		// //查询该险种的校验规则[包含共通的校验规则，险种编码为"000000"]
		// sbSQL
		// .append("select distinct calcode, msg from lmcheckfield ")
		// .append(" where trim(riskcode) in ")
		// .append(" (select distinct trim(riskcode) from lcpol where polno =
		// '")
		// .append(tLPEdorItemSchema.getPolNo())
		// .append("' union select '000000' from dual ) ")
		// .append(" and fieldname = '")
		// .append(tLPEdorItemSchema.getEdorType())
		// .append("Detail")
		// .append("' ");
		// }
		// =====del=====zhangtao========2005-08-15================END====================

		// 首先查出该保全项目公用校验规则（险种无关）
		sbSQL.append(
				"select distinct calcode, msg, serialno from lmcheckfield ")
				.append(" where trim(riskcode) = '000000' ").append(
						" and fieldname = concat('").append(
						"?fieldname?").append("','Detail")
				.append("') order by char_length(trim(serialno)), serialno ");
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sbSQL.toString());
		sqlbv.put("fieldname", tLPEdorItemSchema.getEdorType());
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保全项目明细保存校验规则查询失败!");
			return false;
		}

		boolean bResult; // 计算结果
		String sCalCode = ""; // 计算代码
		String sErroMsg = ""; // 规则信息

		if (mBqCalBase == null) {
			mBqCalBase = new BqCalBase();
		}
		mBqCalBase.setContNo(tLPEdorItemSchema.getContNo());
		mBqCalBase.setEdorNo(tLPEdorItemSchema.getEdorNo());
		mBqCalBase.setEdorAppDate(tLPEdorItemSchema.getEdorAppDate());
		mBqCalBase.setEdorType(tLPEdorItemSchema.getEdorType());
		mBqCalBase.setInsuredNo(tLPEdorItemSchema.getInsuredNo()); // IC校验添加
																	// 2006-04-27
		BqCalBL tBqCalBL;
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			for (int j = 1; j <= tSSRS.getMaxRow(); j++) {
				sCalCode = tSSRS.GetText(j, 1);
				sErroMsg = tSSRS.GetText(j, 2);

				tBqCalBL = new BqCalBL(mBqCalBase, sCalCode, "");
				bResult = tBqCalBL.calEdorItemDetailValidate();
				if (!tBqCalBL.mErrors.needDealError()) {
					CError.buildErr(this, "不符合校验规则，不能保存保全项目变更! " + " 校验规则："
							+ sErroMsg);
					return false;
				}

//				if (!bResult) {
//					CError.buildErr(this, "不符合校验规则，不能保存保全项目变更! " + " 校验规则："
//							+ sErroMsg);
//					return false;
//				}
			}
		}

		// 查出该保全项目变更涉及的险种（P表数据）
		sbSQL.setLength(0);
		sbSQL
				.append(
						"select polno, riskcode, managecom, mainpolno from lppol ")
				.append(" where edorno = '").append(
						"?edorno?").append(
						"' and trim(edortype) = '").append(
						"?edortype?").append("' ");
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sbSQL.toString());
		sqlbv.put("edorno", tLPEdorItemSchema.getEdorNo());
		sqlbv.put("edortype", tLPEdorItemSchema.getEdorType());
		tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保全险种信息查询失败!");
			return false;
		}
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			SSRS ttSSRS;
			for (int j = 1; j <= tSSRS.getMaxRow(); j++) {
				sbSQL.setLength(0);
				sbSQL
						.append(
								"select distinct calcode, msg, serialno from lmcheckfield ")
						.append(" where trim(riskcode) = '").append(
								"?riskcode?").append(
								"' and fieldname = '").append(
								"?fieldname?").append(
								"' order by char_length(trim(serialno)), serialno ");
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(sbSQL.toString());
				sqlbv.put("riskcode", tSSRS.GetText(j, 2));
				sqlbv.put("fieldname", tLPEdorItemSchema.getEdorType()+"Detail");
				tExeSQL = new ExeSQL();
				ttSSRS = tExeSQL.execSQL(sqlbv);
				if (tExeSQL.mErrors.needDealError()) {
					CError.buildErr(this, "保全项目明细保存校验规则查询失败!");
					return false;
				}
				mBqCalBase.setPolNo(tSSRS.GetText(j, 1));
				mBqCalBase.setRiskCode(tSSRS.GetText(j, 2));
				mBqCalBase.setManageCom(tSSRS.GetText(j, 3));
				mBqCalBase.setMainPolNo(tSSRS.GetText(j, 4));
				if (ttSSRS != null && ttSSRS.getMaxRow() > 0) {
					for (int k = 1; k <= ttSSRS.getMaxRow(); k++) {
						sCalCode = ttSSRS.GetText(k, 1);
						sErroMsg = ttSSRS.GetText(k, 2);

						tBqCalBL = new BqCalBL(mBqCalBase, sCalCode, "");
						tBqCalBL.setLPEdorItemSchema(tLPEdorItemSchema);
						bResult = tBqCalBL.calEdorItemDetailValidate();
						if (!tBqCalBL.mErrors.needDealError()) {
							CError.buildErr(this, "执行保全项目明细保存校验规则失败!");
							return false;
						}

//						if (!bResult) {
//							CError.buildErr(this, "不符合校验规则，不能保存保全项目变更! "
//									+ " 校验规则：" + sErroMsg);
//							return false;
//						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * 调用保全项目对应的明细处理类
	 * 
	 * @return: boolean
	 */
	private boolean calPEdorXXDetailBL() {
		// 调用各保全项目对应的明细处理类
		String sEdorType = mLPEdorItemSchema.getEdorType();
		try {
			Class tClass = Class.forName("com.sinosoft.lis.bq.PEdor"
					+ sEdorType + "DetailBLF");
			EdorDetail tEdorDetail = (EdorDetail) tClass.newInstance();

			if (!tEdorDetail.submitData(mInputData, mOperate)) {
				mErrors.copyAllErrors(tEdorDetail.getErrors());
				return false;
			} else {
				logger.debug("== after XXDetailBLF ==");
				mResult = tEdorDetail.getResult();
				mBqCalBase = (BqCalBase) mResult.getObjectByObjectName(
						"BqCalBase", 0);
			}
		} catch (ClassNotFoundException BLFEX) {
			try {
				Class tClass = Class.forName("com.sinosoft.lis.bq.PEdor"
						+ sEdorType + "DetailBL");
				EdorDetail tEdorDetail = (EdorDetail) tClass.newInstance();

				if (!tEdorDetail.submitData(mInputData, mOperate)) {
					mErrors.copyAllErrors(tEdorDetail.getErrors());
					return false;
				} else {
					logger.debug("=== after XXDetailBL ===");
					mResult = tEdorDetail.getResult();
					mBqCalBase = (BqCalBase) mResult.getObjectByObjectName(
							"BqCalBase", 0);
				}
			} catch (ClassNotFoundException BLEX) {
				CError.buildErr(this, "找不到保全项目" + sEdorType + "对应的明细保存处理类!");
				return false;
			} catch (Exception ex) {
				CError.buildErr(this, "保全项目" + sEdorType + "明细保存失败!");
				return false;
			}
		} catch (Exception ex) {
			CError.buildErr(this, "保全项目" + sEdorType + "明细保存失败!");
			return false;
		}

		return true;
	}

	/**
	 * 校验该保全项目保存的LPPol险种，在明细保存提交完后再做
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkRisk() {
		if (mLPEdorItemSchema.getPolNo() != null
				&& !mLPEdorItemSchema.getPolNo().equals("000000")) {
			// 险种级，在添加时已经校验，不用再校验
			return true;
		}
		if (checkRiskEdorItem("000000", mLPEdorItemSchema.getEdorType())) {
			// 该保全项目对所有险种都可以添加
			mErrors.clearErrors();
			return true;
		}

		String sql = " select distinct riskcode from lppol "
				+ " where edorno = '" + "?edorno?"
				+ "' and edortype = '" + "?edortype?" + "' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("edortype", mLPEdorItemSchema.getEdorType());
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保全险种查询失败!");
			return false;
		}
		if (tSSRS == null) {
			return true;
		}
		String sRiskCode;
		for (int j = 1; j <= tSSRS.getMaxRow(); j++) {
			sRiskCode = tSSRS.GetText(j, 1);
			if (!checkRiskEdorItem(sRiskCode, mLPEdorItemSchema.getEdorType())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 校验险种是否可以添加保全项目
	 * 
	 * @param sRiskCode
	 * @param sEdorType
	 * @return: boolean
	 */
	private boolean checkRiskEdorItem(String sRiskCode, String sEdorType) {
		LMRiskEdorItemDB tLMRiskEdorItemDB = new LMRiskEdorItemDB();
		tLMRiskEdorItemDB.setRiskCode(sRiskCode);
		tLMRiskEdorItemDB.setEdorCode(sEdorType);
		LMRiskEdorItemSet tLMRiskEdorItemSet = tLMRiskEdorItemDB.query();
		if (tLMRiskEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "险种保全项目定义查询失败!");
			return false;
		}
		if (tLMRiskEdorItemSet == null || tLMRiskEdorItemSet.size() < 1) {
			if (!sRiskCode.equals("000000")) {
				CError.buildErr(this, "该险种不能添加该保全项目!" + "险种代码：" + sRiskCode
						+ "保全项目代码：" + sEdorType);
			}
			return false;
		}

		return true;
	}

	/**
	 * 更新在当前保全项目之后又有变更的保全项目批改状态为未录入
	 * 
	 */
	private boolean updEdorState() {
		
		StringBuffer sbSQL = new StringBuffer();
		if (mOtherNoType.equals("1")) // 以个人客户号申请保全受理
		{
			sbSQL.setLength(0);
			sbSQL.append(" UPDATE LPEdorItem set EdorState = '1' ").append(
					" WHERE EdorAcceptNo = '").append(
					"?EdorAcceptNo?").append(
					"' AND EdorType = '").append(
					"?EdorType?").append("'");
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sbSQL.toString());
			sqlbv1.put("EdorAcceptNo", mLPEdorItemSchema_befor.getEdorAcceptNo());
			sqlbv1.put("EdorType", mLPEdorItemSchema_befor.getEdorType());
			map.put(sqlbv1, "UPDATE");
		} else {
			sbSQL.setLength(0);
			sbSQL.append(" UPDATE LPEdorItem set EdorState = '1' ").append(
					" WHERE EdorAcceptNo = '").append(
					"?EdorAcceptNo?").append(
					"' and EdorNo = '").append(
					"?EdorNo?").append(
					"' and EdorType = '").append(
					"?EdorType?").append(
					"' and ContNo = '").append(
					"?ContNo?").append(
					"' and InsuredNo = '").append(
					"?InsuredNo?").append(
					"' and PolNo = '").append(
					"?PolNo?").append("'");
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sbSQL.toString());
			sqlbv2.put("EdorAcceptNo", mLPEdorItemSchema_befor.getEdorAcceptNo());
			sqlbv2.put("EdorNo", mLPEdorItemSchema_befor.getEdorNo());
			sqlbv2.put("EdorType", mLPEdorItemSchema_befor.getEdorType());
			sqlbv2.put("ContNo", mLPEdorItemSchema_befor.getContNo());
			sqlbv2.put("InsuredNo", mLPEdorItemSchema_befor.getInsuredNo());
			sqlbv2.put("PolNo", mLPEdorItemSchema_befor.getPolNo());
			map.put(sqlbv2, "UPDATE");
		}

		// 如果在当前保全项目上次变更之后又有其他的保全项目有变更，
		// 则在当前保全项目这次变更后，其他有变更的保全项目重新保存

		// 这里需要保证 LPEdorItem 数据是在当前这次项目明细保存提交之前的数据
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorItem set EdorState = '3' ").append(
				" WHERE EdorNo = '")
				.append("?EdorNo?").append(
						"' AND ContNo = '").append(
						"?ContNo?").append(
						"' AND (ModifyDate > '").append(
						"?ModifyDate?").append(
						"' or (ModifyDate = '").append(
						"?ModifyDate?").append(
						"' and ModifyTime > '").append(
						"?ModifyTime?").append("')) ")
				.append(" AND (MakeDate > '").append(
						"?MakeDate?").append(
						"' or (MakeDate = '").append(
						"?MakeDate?").append(
						"' and MakeTime > '").append(
						"?MakeTime?").append("')) ")
				.append("  AND not (EdorAcceptNo = '").append(
						"?EdorAcceptNo?").append(
						"' and EdorNo = '").append(
						"?EdorNo?").append(
						"' and EdorType = '").append(
						"?EdorType?").append(
						"' and ContNo = '").append(
						"?ContNo?").append(
						"' and InsuredNo = '").append(
						"?InsuredNo?").append(
						"' and PolNo = '").append(
						"?PolNo?").append("')");
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sbSQL.toString());
		sqlbv3.put("EdorNo", mLPEdorItemSchema_befor.getEdorNo());
		sqlbv3.put("ContNo", mLPEdorItemSchema_befor.getContNo());
		sqlbv3.put("ModifyDate", mLPEdorItemSchema_befor.getModifyDate());
		sqlbv3.put("ModifyTime", mLPEdorItemSchema_befor.getModifyTime());
		sqlbv3.put("MakeDate", mLPEdorItemSchema_befor.getMakeDate());
		sqlbv3.put("MakeTime", mLPEdorItemSchema_befor.getMakeTime());
		sqlbv3.put("EdorAcceptNo", mLPEdorItemSchema_befor.getEdorAcceptNo());
		sqlbv3.put("EdorType", mLPEdorItemSchema_befor.getEdorType());
		sqlbv3.put("InsuredNo", mLPEdorItemSchema_befor.getInsuredNo());
		sqlbv3.put("PolNo", mLPEdorItemSchema_befor.getPolNo());
		logger.debug(sbSQL.toString());

		map.put(sqlbv3, "UPDATE");

		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		mResult.clear();
		return mResult;
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		String sql = "select * from lpedoritem where edoracceptno = '6120050815000010' and edortype = 'CT'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv);
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		// tLPEdorItemSchema.setEdorAcceptNo("86000000001532");
		// tLPEdorItemSchema.setEdorNo("410000000001252");
		// tLPEdorItemSchema.setEdorType("FM");
		// tLPEdorItemSchema.setContNo("230110000000573");
		// tLPEdorItemSchema.setInsuredNo("0000499420");
		// tLPEdorItemSchema.setPolNo("210110000001105");

		// LPPolSchema tLPPolSchema = new LPPolSchema();
		// tLPPolSchema.setPayYears(0);
		//
		// VData tVData = new VData();
		// tVData.add(tG);
		// tVData.add(tLPEdorItemSchema);
		// tVData.add(tLPPolSchema);

		EdorDetailBL tEdorDetailBL = new EdorDetailBL();
		if (!tEdorDetailBL.exeCheckRule(tLPEdorItemSchema)) {
			logger.debug(tEdorDetailBL.mErrors.getError(0).errorMessage);
		}

		// if (!tEdorDetailBL.submitData(tVData, "EDORITEM|INPUT"))
		// {
		// logger.debug(tEdorDetailBL.mErrors.getError(0).errorMessage);
		// }
		// String CurrentDate = PubFun.getCurrentDate();
		// Date dt = PubFun.calDate(new FDate().getDate(CurrentDate), -1, "M",
		// null);
		// String dayAfterCurrent = new FDate().getString(dt);
		// logger.debug("====" + dayAfterCurrent);
	}
}
