package com.sinosoft.lis.bq;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPGetBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LJSGetDrawSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LJSGetDrawSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保险金一次性给付BL类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author TangPei
 * @version 1.0
 */

public class PEdorOGDetailBL {
private static Logger logger = Logger.getLogger(PEdorOGDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();

	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();

	private boolean mBomListFlag = false;
	private List mBomList = new ArrayList();
	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LJSGetDrawSet mLJSGetDrawSet = new LJSGetDrawSet(); // 把这些笔钱做状态标记
	private LPPolSchema mLPPolSchema = new LPPolSchema();
	private LCGetSchema mLCGetSchema = new LCGetSchema();
	// ==add=======liuxiaosong=======2006-12-26=========保全磁盘导入=========start====
	private TransferData mTransferData = new TransferData();

	// ==add=======liuxiaosong=======2006-12-26=========保全磁盘导入========end=======
	public PEdorOGDetailBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mLJSGetDrawSet = (LJSGetDrawSet) mInputData.getObjectByObjectName(
					"LJSGetDrawSet", 0);
			mLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName(
					"LPPolSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			if (mGlobalInput == null || mLPEdorItemSchema == null
					|| mLPPolSchema == null) {
				return false;
			}
			// ====add========liuxiaosong======2006-12-26=====================start===============
			logger.debug("保全磁盘导入数据补全");
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			if (mTransferData != null) {
				String tISDiskImport = (String) mTransferData
						.getValueByName("ISDiskImport");
				if ("yes".equals(tISDiskImport)) {
					if (!diskImprotReGetData()) {
						CError.buildErr(this, "目前还没有领取项目！");
						return false;
					}
				}
			}
			// ====add========liuxiaosong======2006-12-26=====================start===============
			// 获得mLPEdorItemSchema的其它信息
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setSchema(mLPEdorItemSchema);
			if (!tLPEdorItemDB.getInfo()) {
				logger.debug("---Error:在个险保全项目表中无匹配记录！---");
				CError tError = new CError();
				tError.moduleName = "PEdorOGDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "在个险保全项目表中无匹配记录！";
				this.mErrors.addOneError(tError);
				return false;
			}
			this.mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql("delete from ljsgetdraw where polno = '?polno?'");
			sbv1.put("polno", mLJSGetDrawSet.get(1).getPolNo());
			mMap.put(sbv1, "DELETE");
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql("delete from ljsget where otherno = '?otherno?'");
			sbv2.put("otherno", mLPEdorItemSchema.getContNo());
			mMap.put(sbv2, "DELETE");
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql("delete from loprtmanager where code='BQ17' and otherno = '?otherno?'");
			sbv3.put("otherno", mLJSGetDrawSet.get(1).getPolNo());
			mMap.put(sbv3, "DELETE");
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败！");
			return false;
		}

		return true;
	}

	// =====add======liuxiaosong====2006-12-26====保全磁盘导入特殊处理===========start=====
	/**
	 * 保全磁盘导入数据补全处理
	 * 
	 * @return boolean
	 */
	private boolean diskImprotReGetData() {
		logger.debug("\t=======@>GrpEdorIRDetailBL->diskImprotReGetData():开始磁盘导入数据补全========");
		String tEdorNO = mLPEdorItemSchema.getEdorNo();
		String tEdorType = "IR";
		String tRiskCode = (String) mTransferData.getValueByName("RiskCode");
		String tPolNo = mLPPolSchema.getPolNo();
		String tCurDate = PubFun.getCurrentDate();

		// mLPPolSchema数据补全---------------------------------------------------
		mLPPolSchema.setEdorNo(tEdorNO);
		mLPPolSchema.setEdorType(tEdorType);
		// ----------------------------------------------------------------------

		// mLJSGetDrawSchema数据补全----------------------------------------------
		// 其实mLJSGetDrawSet在磁盘导入过程中只包含一个PolNo，在此需要补充所有的
		mLJSGetDrawSet.clear();
		// 查询符合标准的领取项信息，该SQL与PEdorTypeAG.js->queryPolGrid()保持一致
		String tSQL = "SELECT distinct "
				+ " b.GetNoticeNo,b.DutyCode,b.GetDutyCode,b.GetDutyKind"
				+ " FROM LJSGetDraw b"
				+ " WHERE b.PolNo='?tPolNo?'"
				+ " and (b.RReportFlag='1' or b.ComeFlag='1')"
				+ " and b.GetDate<=to_date('?tCurDate?','YYYY-MM-DD')"
				+ " and not exists(select 'A' from LCContState where ContNo=b.ContNo and StateType='Loan' and State='1' and EndDate is null and StartDate<=b.GetDate)"
				+ " and not exists(select 'B' from LCContState where PolNo='?tCurDate?' and StateType='PayPrem' and State='1' and EndDate is null and StartDate<=b.GetDate)";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("tPolNo", tPolNo);
		sqlbv.put("tCurDate", tCurDate);

		logger.debug("磁盘导入数据补全SQL:\n" + tSQL);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		int count = tSSRS.getMaxRow(); // 最大行
		if (count == 0) {
			// 目前还没有领取项目
			return false;
		}
		for (int i = 1; i <= count; i++) {
			LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();
			tLJSGetDrawSchema.setGetNoticeNo(tSSRS.GetText(i, 1));
			tLJSGetDrawSchema.setDutyCode(tSSRS.GetText(i, 2));
			tLJSGetDrawSchema.setGetDutyKind(tSSRS.GetText(i, 4));
			tLJSGetDrawSchema.setGetDutyCode(tSSRS.GetText(i, 3));
			tLJSGetDrawSchema.setPolNo(tPolNo);
			mLJSGetDrawSet.add(tLJSGetDrawSchema);
		}
		// ----------------------------------------------------------------------
		logger.debug("\t=======@>GrpEdorIRDetailBL->diskImprotReGetData():完成磁盘导入数据补全========");
		return true;
	}

	// =====add======liuxiaosong====2006-12-26====保全磁盘导入特殊处理===========END=======

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);

		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorOGDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "无保全申请数据！";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);

			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

		if (tLPEdorItemDB.getEdorState().trim().equals("2")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorOGDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该保全已经申请确认不能修改！";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);

			return false;
		}

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLCPolDB.getInfo()) {
			CError.buildErr(this, "");
			return false;
		}
		Reflections tRef = new Reflections();
		tRef.transFields(mLPPolSchema, tLCPolDB.getSchema());

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		// 获得此时的日期和时间
		String strCurrentDate = PubFun.getCurrentDate();
		String strCurrentTime = PubFun.getCurrentTime();
		String tSql = "";
		SSRS tSSRS = null;
		ExeSQL tExeSQL = null;
		// 记录本次给付的总金额，存入保全项目表
		double tOGMoney = 0.0;
		LJSGetDrawSchema tLJSGetDrawSchema = null;
		tLJSGetDrawSchema = new LJSGetDrawSchema();
		// 生成给付子表的信息
		// 计算给付至日期
		// 计算应给付金额
		tOGMoney = this.calGetMoney(mLJSGetDrawSet);
		tLJSGetDrawSchema.setGetMoney(tOGMoney);
		tLJSGetDrawSchema.setDestrayFlag("1");

		String tGetNoticeNo;
		tGetNoticeNo = PubFun1.CreateMaxNo("GetNoticeNo", PubFun
				.getNoLimit(mLPPolSchema.getManageCom()));
		tLJSGetDrawSchema.setGetNoticeNo(tGetNoticeNo);
		String mSerialNo = "";
		String tSerialNo = mSerialNo;
		if (mSerialNo == null || mSerialNo.equals("")) {
			tSerialNo = PubFun1.CreateMaxNo("SERIALNO", PubFun
					.getNoLimit(mGlobalInput.ManageCom));
			mSerialNo = tSerialNo;
		}
		tLJSGetDrawSchema.setSerialNo(tSerialNo);
		tLJSGetDrawSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
		tLJSGetDrawSchema.setDutyCode(mLJSGetDrawSet.get(1).getDutyCode());
		tLJSGetDrawSchema
				.setGetDutyCode(mLJSGetDrawSet.get(1).getGetDutyCode());
		tLJSGetDrawSchema
				.setGetDutyKind(mLJSGetDrawSet.get(1).getGetDutyKind());
		tLJSGetDrawSchema.setLastGettoDate(mLCGetSchema.getGetEndDate());

		tLJSGetDrawSchema.setFeeFinaType("EF");
		tLJSGetDrawSchema.setCurGetToDate(mLCGetSchema.getGetEndDate());
		tLJSGetDrawSchema.setGetFirstFlag("0");
		tLJSGetDrawSchema.setGetChannelFlag("1");
		tLJSGetDrawSchema.setAppntNo(mLPPolSchema.getAppntNo());
		tLJSGetDrawSchema.setPolNo(mLPPolSchema.getPolNo());
		tLJSGetDrawSchema.setGrpName("");
		tLJSGetDrawSchema.setGrpPolNo(mLPPolSchema.getGrpPolNo());
		tLJSGetDrawSchema.setGrpContNo(mLPPolSchema.getGrpContNo());
		tLJSGetDrawSchema.setContNo(mLPPolSchema.getContNo());
		tLJSGetDrawSchema.setPolNo(mLPPolSchema.getPolNo());
		tLJSGetDrawSchema.setPolType("1");
		tLJSGetDrawSchema.setInsuredNo(mLPPolSchema.getInsuredNo());
		tLJSGetDrawSchema.setAgentCode(mLPPolSchema.getAgentCode());
		tLJSGetDrawSchema.setAgentCom(mLPPolSchema.getAgentCom());
		tLJSGetDrawSchema.setAgentGroup(mLPPolSchema.getAgentGroup());
		tLJSGetDrawSchema.setAgentType(mLPPolSchema.getAgentType());
		tLJSGetDrawSchema.setRiskCode(mLPPolSchema.getRiskCode());
		tLJSGetDrawSchema.setKindCode(mLPPolSchema.getKindCode());
		tLJSGetDrawSchema.setRiskVersion(mLPPolSchema.getRiskVersion());

		tLJSGetDrawSchema.setMakeDate(strCurrentDate);
		tLJSGetDrawSchema.setMakeTime(strCurrentTime);
		tLJSGetDrawSchema.setModifyDate(strCurrentDate);
		tLJSGetDrawSchema.setModifyTime(strCurrentTime);
		tLJSGetDrawSchema.setOperator(mGlobalInput.Operator);
		tLJSGetDrawSchema.setManageCom(mLPPolSchema.getManageCom());
		tLJSGetDrawSchema.setRReportFlag("0");
		tLJSGetDrawSchema.setComeFlag("1");
        //营改增 add zhangyingfeng 2016-07-14
        //价税分离 计算器
        TaxCalculator.calBySchema(tLJSGetDrawSchema);
        //end zhangyingfeng 2016-07-14
		mMap.put(tLJSGetDrawSchema, "DELETE&INSERT");

		// 修改对应LPGet里的相应的记录的信息，界面初始化时会在LPGet里校验记录，已选的就不显示了
		LPGetBL tLPGetBL = new LPGetBL();
		LPGetSchema tLPGetSchema = null;
		int tLJSGDSSize = mLJSGetDrawSet.size();
		logger.debug("dddddddddddd" + tLJSGDSSize);
		LPGetSet tLPGetSet = new LPGetSet();
		for (int i = 1; i <= tLJSGDSSize; i++) {
			tLJSGetDrawSchema = new LJSGetDrawSchema();
			tLJSGetDrawSchema = mLJSGetDrawSet.get(i);
			tLPGetSchema = new LPGetSchema();
			tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPGetSchema.setPolNo(tLJSGetDrawSchema.getPolNo());
			tLPGetSchema.setDutyCode(tLJSGetDrawSchema.getDutyCode());
			tLPGetSchema.setGetDutyCode(tLJSGetDrawSchema.getGetDutyCode());
			if (!tLPGetBL.queryLastLPGet(this.mLPEdorItemSchema, tLPGetSchema)) {
				logger.debug("---Error:未找到应付项对应的领取信息！---");
				return false;
			}
			tLPGetSchema = tLPGetBL.getSchema();
			// 准备要修改的信息，暂时先改变这两项
			tLPGetSchema.setGetMode("1");
			tLPGetSchema.setSumMoney(tLPGetSchema.getSumMoney()
					+ tLJSGetDrawSchema.getGetMoney());
			tLPGetSchema.setGettoDate(mLCGetSchema.getGetEndDate());
			tLPGetSchema.setGetEndDate(mLCGetSchema.getGetEndDate());
			tLPGetSchema.setGetEndState("1");
			tLPGetSchema.setOperator(this.mGlobalInput.Operator);
			tLPGetSchema.setModifyDate(strCurrentDate);
			tLPGetSchema.setModifyTime(strCurrentTime);
			tLPGetSet.add(tLPGetSchema);

		}
		mMap.put(tLPGetSet, "DELETE&INSERT");

		// 修改LPPol的领取方式和帐户相关信息
		LPPolBL tLPPolBL = new LPPolBL();
		if (!tLPPolBL.queryLastLPPol(this.mLPEdorItemSchema, this.mLPPolSchema)) {
			return false;
		}
		LPPolSchema tLPPolSchema = new LPPolSchema();
		tLPPolSchema.setSchema(tLPPolBL.getSchema());
		// 更新保全相关信息
		tLPPolSchema.setGetForm(this.mLPPolSchema.getGetForm());
		if ("0".equals(this.mLPPolSchema.getGetForm())) {
			tLPPolSchema.setGetBankCode(this.mLPPolSchema.getGetBankCode());
			tLPPolSchema.setGetBankAccNo(this.mLPPolSchema.getGetBankAccNo());
			tLPPolSchema.setGetAccName(this.mLPPolSchema.getGetAccName());
		}
		tLPPolSchema.setOperator(this.mGlobalInput.Operator);
		tLPPolSchema.setModifyDate(strCurrentDate);
		tLPPolSchema.setModifyTime(strCurrentTime);
		mMap.put(tLPPolSchema, "DELETE&INSERT");

		// 准备“批改补退费表（应收/应付）表”信息
		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
		LJSGetEndorseSchema tLJSGetEndorseSchema = null;
		LJSGetDrawSchema tempLJSGetDrawSchema = null;
		LJSGetDrawSet tempLJSGetDrawSet = null;
		String nowGetType = new String(); // 给付分类1: 0 －－ 满期金 1 －－ 年金
		int i;
		// 生成“批改补退费表（应收/应付）表”信息，一条LJSGetDraw记录对应一条LJSGetEndorse记录，2005-08-09日修改
		for (i = 1; i <= mLJSGetDrawSet.size(); i++) {
			tempLJSGetDrawSchema = new LJSGetDrawSchema();
			tempLJSGetDrawSchema = mLJSGetDrawSet.get(i);
			tempLJSGetDrawSet = new LJSGetDrawSet();
			tempLJSGetDrawSet.add(tempLJSGetDrawSchema);
			// 记录当前“给付分类1”
			nowGetType = getGetTypeByGetDutyCode(tempLJSGetDrawSchema
					.getGetDutyCode());
			tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			tLJSGetEndorseSchema = getLJSGetEndorseSchemaBySet(
					tempLJSGetDrawSet, nowGetType, i);
			tLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		}
        //营改增 add zhangyingfeng 2016-07-14
        //价税分离 计算器
        TaxCalculator.calBySchemaSet(tLJSGetEndorseSet);
        //end zhangyingfeng 2016-07-14
		mMap.put(tLJSGetEndorseSet, "DELETE&INSERT");

		// 修改“个险保全项目表”相应信息
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
		mLPEdorItemSchema.setModifyDate(strCurrentDate);
		mLPEdorItemSchema.setModifyTime(strCurrentTime);
		mLPEdorItemSchema.setGetMoney(-tOGMoney);
		mMap.put(mLPEdorItemSchema, "UPDATE");

		mResult.clear();
		mResult.add(mMap);
		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		mBqCalBase.setRiskCode(tLPPolSchema.getRiskCode());
		mBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());

		mResult.add(mBqCalBase);

		return true;
	}

	// 通过传入的LJSGetDrawSet生成一个LJSGetEndorseSchema，这些LJSGetDrawSet的GetNoticeNo都是相同的
	private LJSGetEndorseSchema getLJSGetEndorseSchemaBySet(
			LJSGetDrawSet aLJSGetDrawSet, String aGetType, int aSerial) {
		try {
			if (aLJSGetDrawSet == null) {
				return null;
			}

			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();

			LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();
			tLJSGetDrawSchema = aLJSGetDrawSet.get(1);
			LJSGetEndorseSchema rLJSGetEndorseSchema = new LJSGetEndorseSchema();
			rLJSGetEndorseSchema.setGetNoticeNo(this.mLPEdorItemSchema
					.getEdorAcceptNo());
			rLJSGetEndorseSchema.setEndorsementNo(this.mLPEdorItemSchema
					.getEdorNo());
			rLJSGetEndorseSchema.setFeeOperationType("OG");
			// 查询“补/退费财务类型”，如所传RiskCode没有对应的，就查000000的，再没有就返回null，报错
			String tSql = null;
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			if (aGetType != null) {
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSql = "SELECT CodeName"
						+ " FROM ((select '1' as flag,CodeName"
						+ " from LDCode1"
						+ " where CodeType='OG' and Code='MQJJF' and Code1='?Code1?')"
						+ " union"
						+ " (select '2' as flag,CodeName"
						+ " from LDCode1"
						+ " where CodeType='OG' and Code='MQJJF' and Code1='000000')) g"
						+ " WHERE rownum=1" + " ORDER BY flag";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					tSql = "SELECT CodeName"
							+ " FROM ((select '1' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='OG' and Code='MQJJF' and Code1='?Code1?')"
							+ " union"
							+ " (select '2' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='OG' and Code='MQJJF' and Code1='000000')) g"
							+ " ORDER BY flag" + " limit 0,1";	
				}
				sqlbv.sql(tSql);
				sqlbv.put("Code1", tLJSGetDrawSchema.getRiskCode());
			}
			

			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null) {
				CError tError = new CError();
				tError.moduleName = "PEdorOGDetailBL";
				tError.functionName = "getLJSGetEndorseSchemaBySet";
				tError.errorMessage = "未发现匹配的“补/退费财务类型”！";
				mErrors.addOneError(tError);
				return null;
			}
			rLJSGetEndorseSchema.setFeeFinaType(tSSRS.GetText(1, 1));
			rLJSGetEndorseSchema.setGrpContNo(tLJSGetDrawSchema.getGrpContNo());
			rLJSGetEndorseSchema.setContNo(this.mLPEdorItemSchema.getContNo());
			rLJSGetEndorseSchema.setGrpPolNo(tLJSGetDrawSchema.getGrpPolNo());
			rLJSGetEndorseSchema.setPolNo(this.mLPEdorItemSchema.getPolNo());
			rLJSGetEndorseSchema.setOtherNo(tLJSGetDrawSchema.getGetNoticeNo());
			rLJSGetEndorseSchema.setOtherNoType("5");
			rLJSGetEndorseSchema.setDutyCode(tLJSGetDrawSchema.getDutyCode());
			rLJSGetEndorseSchema.setPayPlanCode("000000");
			rLJSGetEndorseSchema.setAppntNo(tLJSGetDrawSchema.getAppntNo());
			rLJSGetEndorseSchema.setInsuredNo(tLJSGetDrawSchema.getInsuredNo());
			rLJSGetEndorseSchema.setGetDate(strCurrentDate);
			// 计算“补/退费金额”
			double sumMoney = 0;
			for (int i = 1; i <= aLJSGetDrawSet.size(); i++) {
				sumMoney = sumMoney + aLJSGetDrawSet.get(i).getGetMoney();
			}
			rLJSGetEndorseSchema.setGetMoney(-sumMoney); // 付钱，所以为负数
			rLJSGetEndorseSchema.setKindCode(tLJSGetDrawSchema.getKindCode());
			rLJSGetEndorseSchema.setRiskCode(tLJSGetDrawSchema.getRiskCode());
			rLJSGetEndorseSchema.setRiskVersion(tLJSGetDrawSchema
					.getRiskVersion());
			// 获得保单管理机构
			tSql = "SELECT ManageCom FROM LCCont WHERE ContNo='?ContNo?'";
			sqlbv.sql(tSql);
			sqlbv.put("ContNo", this.mLPEdorItemSchema.getContNo());
			SSRS tempSSRS = new SSRS();
			tempSSRS = tExeSQL.execSQL(sqlbv);
			if (tempSSRS != null && tempSSRS.MaxRow > 0) {
				rLJSGetEndorseSchema.setManageCom(tempSSRS.GetText(1, 1));
			}
			rLJSGetEndorseSchema.setAgentCom(tLJSGetDrawSchema.getAgentCom());
			rLJSGetEndorseSchema.setAgentType(tLJSGetDrawSchema.getAgentType());
			rLJSGetEndorseSchema.setAgentCode(tLJSGetDrawSchema.getAgentCode());
			rLJSGetEndorseSchema.setAgentGroup(tLJSGetDrawSchema
					.getAgentGroup());
			rLJSGetEndorseSchema.setGrpName(tLJSGetDrawSchema.getGrpName());
			rLJSGetEndorseSchema.setHandler(tLJSGetDrawSchema.getHandler());
			rLJSGetEndorseSchema.setPolType(tLJSGetDrawSchema.getPolType());
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
			CError tError = new CError();
			tError.moduleName = "PEdorOGDetailBL";
			tError.functionName = "getLJSGetEndorseSchemaBySet";
			tError.errorMessage = e.toString();
			mErrors.addOneError(tError);
			return null;
		}
	}

	// 通过“GetDutyCode”查询“给付分类1”
	private String getGetTypeByGetDutyCode(String gGetDutyCode) {
		try {
			if (gGetDutyCode == null) {
				logger.debug("通过“GetDutyCode”查询“给付分类1”时传入的“GetDutyCode”为null！");
				CError tError = new CError();
				tError.moduleName = "PEdorOGDetailBL";
				tError.functionName = "getGetTypeByGetDutyCode";
				tError.errorMessage = "通过“GetDutyCode”查询“给付分类1”时传入的“GetDutyCode”为null！";
				this.mErrors.addOneError(tError);
				return null;
			}
			String tSql = "SELECT GetType1 FROM LMDutyGet WHERE GetDutyCode='?GetDutyCode?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("GetDutyCode", gGetDutyCode.trim());
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.GetText(1, 1) == null) {
				logger.debug("通过“GetDutyCode”查询“给付分类1”时未找到记录！");
				CError tError = new CError();
				tError.moduleName = "PEdorOGDetailBL";
				tError.functionName = "getGetTypeByGetDutyCode";
				tError.errorMessage = "通过“GetDutyCode”查询“给付分类1”时未找到记录！";
				this.mErrors.addOneError(tError);
				return null;
			}
			return tSSRS.GetText(1, 1);
		} catch (Exception e) {
			logger.debug("通过“GetDutyCode”查询“给付分类1”时未找到记录！");
			CError tError = new CError();
			tError.moduleName = "PEdorOGDetailBL";
			tError.functionName = "getGetTypeByGetDutyCode";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			return null;
		}
	}

	// 计算给付金对应的给付金额
	private double calGetMoney(LJSGetDrawSet aLJSGetDrawSet) {
		double aGetMoney = 0.0;
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(aLJSGetDrawSet.get(1).getRiskCode());
		tLMEdorCalDB.setCalType("OGMoney");
		tLMEdorCalDB.setDutyCode(mLJSGetDrawSet.get(1).getDutyCode());

		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query(); // 查找保费累计变动金额计算信息
		if (tLMEdorCalDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
			mErrors.addOneError(new CError("查询险种责任保全计算信息失败！"));
			return aGetMoney;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
			return aGetMoney; // 没有计算信息，则不作计算
		} else {
			BqCalBase tBqCalBase = new BqCalBase();
			Calculator tCalculator = new Calculator();
			tBqCalBase.setContNo(mLPPolSchema.getContNo());
			tBqCalBase.setPolNo(mLPPolSchema.getPolNo());
			if (!prepareBOMList(mBqCalBase)) {
				CError.buildErr(this, "Prepare BOMLIST Failed...");
				return -1;
			}
			tCalculator.setBOMList(this.mBomList);// 添加BOMList
			String sCalCode = tLMEdorCalSet.get(1).getCalCode();
			tCalculator.setCalCode(sCalCode);
			tCalculator.addBasicFactor("PolNo", mLPPolSchema.getPolNo());
			String tValue = tCalculator.calculate();
			if (tCalculator.mErrors.needDealError()) {
				CError.buildErr(this, "保险金计算失败!");
				return -1;
			} else {
				aGetMoney = Arith.round(Double.parseDouble(tValue), 2);
			}

			try {
				aGetMoney = Double.parseDouble(tValue);
			} catch (Exception e) {
				CError.buildErr(this, "保险金计算结果错误!" + "错误结果：" + tValue);
				return -1;
			}

		}
		return aGetMoney;
	}
	
	/**
	 * 为全局变量mBomList赋值，如果已经赋值过，则不再赋值
	 * 
	 * @return
	 */
	private boolean prepareBOMList(BqCalBase mBqCalBase) {
		try {
			if (!this.mBomListFlag) {
				this.mPrepareBOMBQEdorBL.setBqCalBase(mBqCalBase);
				this.mBomList = this.mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
				this.mBomListFlag = true;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.mBomListFlag = false;
			return false;
		}
	}

	// 领取信息
	private LCGetSchema getGetInfo() {
		LCGetDB cLCGetDB = new LCGetDB();
		cLCGetDB.setPolNo(mLPPolSchema.getPolNo());
		cLCGetDB.setDutyCode(mLJSGetDrawSet.get(1).getDutyCode());
		cLCGetDB.setGetDutyCode(mLJSGetDrawSet.get(1).getGetDutyCode());
		if (!cLCGetDB.getInfo()) {
			CError.buildErr(this, "查询领取信息失败!");
			return null;
		}
		mLCGetSchema = cLCGetDB.getSchema();
		return mLCGetSchema;
	}

}
