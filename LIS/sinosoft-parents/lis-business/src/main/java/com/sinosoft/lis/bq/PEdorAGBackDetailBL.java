package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LJAGetDrawSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 年金，满期金给付回退BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorAGBackDetailBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorAGBackDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorAGBackDetailBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 得到外部传入的数据,将数据备份到本类中
		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			// 需要回退的保全项目
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return this.makeError("getInputData", "接收前台数据失败！");
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			return this.makeError("getInputData", "传入数据有误！");
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			return this.makeError("checkData", "无保全数据！");
		}

		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
		if (!"0".equals(tLPEdorItemDB.getEdorState())) {
			// @@错误处理
			return this.makeError("checkData", "此项目尚未确认生效！");
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			logger.debug("---年金，满期金给付保全回退处理---");

			// ***说明：LJAGetDraw、LJAGetEndorse、LJAPayPerson 在公共部分处理，因此以下注掉
			/*
			 * //获得此时的日期和时间 String strCurrentDate = PubFun.getCurrentDate();
			 * String strCurrentTime = PubFun.getCurrentTime();
			 * 
			 * //准备“批改补退费表（应收/应付）表”信息 LJSGetEndorseSet tLJSGetEndorseSet = new
			 * LJSGetEndorseSet(); LJSGetEndorseSchema tLJSGetEndorseSchema =
			 * null; LJAGetDrawSchema tempLJAGetDrawSchema = null; String
			 * nowGetType = new String(); //给付分类1: 0 －－ 满期金 1 －－ 年金 int tSerial =
			 * 1; //自增
			 * 
			 * //查询P表数据 LPGetDB tLPGetDB = new LPGetDB();
			 * tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			 * tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType()); LPGetSet
			 * tempLPGetSet = tLPGetDB.query(); if (tempLPGetSet == null ||
			 * tempLPGetSet.size() <= 0) { return
			 * this.makeError("dealData","查询保全领取项表数据失败！"); } LJAGetDrawDB
			 * tLJAGetDrawDB = new LJAGetDrawDB(); for (int i=1;i<=tempLPGetSet.size();i++) {
			 * //查询LJAGetDraw数据恢复到LJSGetDraw，要用到老的GetToDate，就是GetDate
			 * tLJAGetDrawDB.setPolNo(tempLPGetSet.get(i).getPolNo());
			 * tLJAGetDrawDB.setDutyCode(tempLPGetSet.get(i).getDutyCode());
			 * tLJAGetDrawDB.setGetDutyKind(tempLPGetSet.get(i).getGetDutyKind());
			 * tLJAGetDrawDB.setGetDutyCode(tempLPGetSet.get(i).getGetDutyCode());
			 * tLJAGetDrawDB.setGetDate(tempLPGetSet.get(i).getGettoDate());
			 * LJAGetDrawSet tempLJAGetDrawSet = tLJAGetDrawDB.query();
			 * //生成“批改补退费表（应收/应付）表”信息，一条LJSGetDraw记录对应一条LJSGetEndorse记录 if
			 * (tempLJAGetDrawSet != null && tempLJAGetDrawSet.size() > 0) { for
			 * (int j=1;j<=tempLJAGetDrawSet.size();j++) { tempLJAGetDrawSchema =
			 * new LJAGetDrawSchema();
			 * tempLJAGetDrawSchema.setSchema(tempLJAGetDrawSet.get(j));
			 * //记录当前“给付分类1” nowGetType =
			 * getGetTypeByGetDutyCode(tempLJAGetDrawSchema.getGetDutyCode());
			 * tLJSGetEndorseSchema =
			 * getLJSGetEndorseSchemaBySet(tempLJAGetDrawSchema,nowGetType,tSerial,strCurrentDate,strCurrentTime);
			 * if (tLJSGetEndorseSchema != null) {
			 * tLJSGetEndorseSet.add(tLJSGetEndorseSchema); tSerial++; } } } }
			 * mMap.put(tLJSGetEndorseSet,"DELETE&INSERT");
			 */

			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			return this.makeError("dealData", "数据处理错误！" + e.getMessage());
		}
		return true;
	}

	/**
	 * 通过传入的LJAGetDrawSchema生成一个LJSGetEndorseSchema
	 * 
	 * @param aLJAGetDrawSchema
	 * @param aGetType
	 * @param aSerial
	 * @return LJSGetEndorseSchema
	 */
	private LJSGetEndorseSchema getLJSGetEndorseSchemaBySet(
			LJAGetDrawSchema aLJAGetDrawSchema, String aGetType, int aSerial,
			String strCurrentDate, String strCurrentTime) {
		try {
			if (aLJAGetDrawSchema == null) {
				return null;
			}

			LJSGetEndorseSchema rLJSGetEndorseSchema = new LJSGetEndorseSchema();
			rLJSGetEndorseSchema.setGetNoticeNo(this.mLPEdorItemSchema
					.getEdorAcceptNo());
			rLJSGetEndorseSchema.setEndorsementNo(this.mLPEdorItemSchema
					.getEdorNo());
			rLJSGetEndorseSchema.setFeeOperationType("AG");
			// 查询“补/退费财务类型”，如所传RiskCode没有对应的，就查000000的，再没有就返回null，报错
			String tSql = null;
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			if (aGetType.equals("1")) {
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSql = "SELECT CodeName"
						+ " FROM ((select '1' as flag,CodeName"
						+ " from LDCode1"
						+ " where CodeType='AG' and Code='NJJF' and Code1='"
						+ "?Code1?"
						+ "')"
						+ " union"
						+ " (select '2' as flag,CodeName"
						+ " from LDCode1"
						+ " where CodeType='AG' and Code='NJJF' and Code1='000000'))"
						+ " WHERE rownum=1" + " ORDER BY flag";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					tSql = "SELECT CodeName"
							+ " FROM ((select '1' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='AG' and Code='NJJF' and Code1='"
							+ "?Code1?"
							+ "')"
							+ " union"
							+ " (select '2' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='AG' and Code='NJJF' and Code1='000000')) g"
							+ " ORDER BY flag"+ " limit 0,1" ;	
				}
				sqlbv.sql(tSql);
				sqlbv.put("Code1", aLJAGetDrawSchema.getRiskCode());
			} else {
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					tSql = "SELECT CodeName"
							+ " FROM ((select '1' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='AG' and Code='MQJJF' and Code1='"
							+ "?Code1?"
							+ "')"
							+ " union"
							+ " (select '2' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='AG' and Code='MQJJF' and Code1='000000'))"
							+ " WHERE rownum=1" + " ORDER BY flag";
					}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
						tSql = "SELECT CodeName"
								+ " FROM ((select '1' as flag,CodeName"
								+ " from LDCode1"
								+ " where CodeType='AG' and Code='MQJJF' and Code1='"
								+ "?Code1?"
								+ "')"
								+ " union"
								+ " (select '2' as flag,CodeName"
								+ " from LDCode1"
								+ " where CodeType='AG' and Code='MQJJF' and Code1='000000')) g"
								+ " ORDER BY flag"+ " limit 0,1" ;
					}
				
				sqlbv.sql(tSql);
				sqlbv.put("Code1", aLJAGetDrawSchema.getRiskCode());
			}

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				this.makeError("getLJSGetEndorseSchemaBySet",
						"未发现匹配的“补/退费财务类型”！");
				return null;
			}
			rLJSGetEndorseSchema.setFeeFinaType(tSSRS.GetText(1, 1));
			rLJSGetEndorseSchema.setGrpContNo(aLJAGetDrawSchema.getGrpContNo());
			rLJSGetEndorseSchema.setContNo(this.mLPEdorItemSchema.getContNo());
			rLJSGetEndorseSchema.setGrpPolNo(aLJAGetDrawSchema.getGrpPolNo());
			rLJSGetEndorseSchema.setPolNo(this.mLPEdorItemSchema.getPolNo());
			rLJSGetEndorseSchema.setOtherNo(aLJAGetDrawSchema.getGetNoticeNo());
			rLJSGetEndorseSchema.setOtherNoType("5");
			rLJSGetEndorseSchema.setDutyCode(aLJAGetDrawSchema.getDutyCode());
			rLJSGetEndorseSchema.setPayPlanCode("000000");
			rLJSGetEndorseSchema.setAppntNo(aLJAGetDrawSchema.getAppntNo());
			rLJSGetEndorseSchema.setInsuredNo(aLJAGetDrawSchema.getInsuredNo());
			rLJSGetEndorseSchema.setGetDate(strCurrentDate);
			rLJSGetEndorseSchema.setGetMoney(-aLJAGetDrawSchema.getGetMoney()); // “补/退费金额”
			rLJSGetEndorseSchema.setKindCode(aLJAGetDrawSchema.getKindCode());
			rLJSGetEndorseSchema.setRiskCode(aLJAGetDrawSchema.getRiskCode());
			rLJSGetEndorseSchema.setRiskVersion(aLJAGetDrawSchema
					.getRiskVersion());
			// 获得保单管理机构
			tSql = "SELECT ManageCom FROM LCCont WHERE ContNo='"
					+ "?ContNo?" + "'";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(tSql);
			sbv.put("ContNo", this.mLPEdorItemSchema.getContNo());
			SSRS tempSSRS = tExeSQL.execSQL(sbv);
			if (tempSSRS != null && tempSSRS.MaxRow > 0) {
				rLJSGetEndorseSchema.setManageCom(tempSSRS.GetText(1, 1));
			}
			rLJSGetEndorseSchema.setAgentCom(aLJAGetDrawSchema.getAgentCom());
			rLJSGetEndorseSchema.setAgentType(aLJAGetDrawSchema.getAgentType());
			rLJSGetEndorseSchema.setAgentCode(aLJAGetDrawSchema.getAgentCode());
			rLJSGetEndorseSchema.setAgentGroup(aLJAGetDrawSchema
					.getAgentGroup());
			rLJSGetEndorseSchema.setGrpName(aLJAGetDrawSchema.getGrpName());
			rLJSGetEndorseSchema.setHandler(aLJAGetDrawSchema.getHandler());
			rLJSGetEndorseSchema.setPolType(aLJAGetDrawSchema.getPolType());
			rLJSGetEndorseSchema.setApproveCode("");
			rLJSGetEndorseSchema.setApproveDate("");
			rLJSGetEndorseSchema.setApproveTime("");
			rLJSGetEndorseSchema.setGetFlag("");
			rLJSGetEndorseSchema.setSerialNo("");
			rLJSGetEndorseSchema.setOperator(this.mGlobalInput.Operator);
			rLJSGetEndorseSchema.setMakeDate(strCurrentDate);
			rLJSGetEndorseSchema.setMakeTime(strCurrentTime);
			rLJSGetEndorseSchema.setModifyDate(strCurrentDate);
			rLJSGetEndorseSchema.setModifyTime(strCurrentTime);
			rLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Get_GetDraw
					+ String.valueOf(aSerial)); // 保证主键不重复
			return rLJSGetEndorseSchema;
		} catch (Exception e) {
			this.makeError("getLJSGetEndorseSchemaBySet", e.toString());
			return null;
		}
	}

	/**
	 * 通过“GetDutyCode”查询“给付分类1”
	 * 
	 * @param gGetDutyCode
	 * @return String
	 */
	private String getGetTypeByGetDutyCode(String gGetDutyCode) {
		try {
			if (gGetDutyCode == null) {
				this.makeError("getGetTypeByGetDutyCode",
						"通过“GetDutyCode”查询“给付分类1”时传入的“GetDutyCode”为null！");
				return null;
			}
			String tSql = "SELECT GetType1 FROM LMDutyGet WHERE GetDutyCode='"
					+ "?GetDutyCode?" + "'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("GetDutyCode", gGetDutyCode.trim());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.GetText(1, 1) == null) {
				this.makeError("getGetTypeByGetDutyCode",
						"通过“GetDutyCode”查询“给付分类1”时未找到记录！");
				return null;
			}
			return tSSRS.GetText(1, 1);
		} catch (Exception e) {
			this.makeError("getGetTypeByGetDutyCode",
					"通过“GetDutyCode”查询“给付分类1”时未找到记录！");
			return null;
		}
	}

	/**
	 * 创建一个错误
	 * 
	 * @param vFuncName
	 *            发生错误的函数名
	 * @param vErrMsg
	 *            错误信息
	 * @return 布尔值（false--永远返回此值）
	 */
	private boolean makeError(String vFuncName, String vErrMsg) {
		CError tError = new CError();
		tError.moduleName = "PEdorAGBackDetailBL";
		tError.functionName = vFuncName;
		tError.errorMessage = vErrMsg;
		this.mErrors.addOneError(tError);
		return false;
	}

	public static void main(String[] args) {
		PEdorAGBackDetailBL tPEdorAGBackDetailBL = new PEdorAGBackDetailBL();
	}
}
