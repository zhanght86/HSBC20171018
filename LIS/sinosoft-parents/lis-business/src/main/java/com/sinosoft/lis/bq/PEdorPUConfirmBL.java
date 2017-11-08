package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 减额缴清确认类 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Nicholas
 * @version 1.0
 */


import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPContStateDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class PEdorPUConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorPUConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	private Reflections mReflections = new Reflections();

	public PEdorPUConfirmBL() {
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
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");

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

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPUConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return: boolean
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPUConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询批改项目信息失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 获得此时的日期和时间
			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();
			String tSql = "";

			// LCCont表数据交换-->
			LPContDB tLPContDB = new LPContDB();
			tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
			if (!tLPContDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorPUConfirmBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询保全保单记录信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LPContSchema tLPContSchema = tLPContDB.getSchema();
			LCContSchema tLCContSchema = new LCContSchema();
			this.mReflections.transFields(tLCContSchema, tLPContSchema);
			tLCContSchema.setOperator(this.mGlobalInput.Operator);
			tLCContSchema.setModifyDate(strCurrentDate);
			tLCContSchema.setModifyTime(strCurrentTime);
			mMap.put(tLCContSchema, "DELETE&INSERT");
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
			if (!tLCContDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorPUConfirmBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询保单记录信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLCContSchema = new LCContSchema();
			tLCContSchema = tLCContDB.getSchema();
			tLPContSchema = new LPContSchema();
			this.mReflections.transFields(tLPContSchema, tLCContSchema);
			tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			// tLPContSchema.setOperator(this.mGlobalInput.Operator);
			tLPContSchema.setModifyDate(strCurrentDate);
			tLPContSchema.setModifyTime(strCurrentTime);
			mMap.put(tLPContSchema, "DELETE&INSERT");

			// Add By QianLy on 2006-8-24---------------------------
			// Modified By QianLy on 2006-10-31---------------------
			// 对退保之后的险种，不允许进行打印分红业绩报告书,只允许补打。
			// 在此对LOPRTManager表中的状态更新为已打印。（stateflag ＝ 1）
			String updtsql = "update loprtmanager"
					+ " set stateflag = '1'"
					+ " where stateflag = '0'"
					+ " and code = '"
					+ "?code?"// 分红业绩报告书
					// + "','" + PrintManagerBL.CODE_PEdorAPPRE//保费自垫通知书
					// + "','" + PrintManagerBL.CODE_PEdorContInvalid//保单失效通知书
					// + "','" + PrintManagerBL.CODE_PEdorLoanPay//保单质押贷款还款通知书
					// + "','" +
					// PrintManagerBL.CODE_PEdorAutoPayAG//领取通知书(代生存调查通知书)
					// + "','" + PrintManagerBL.CODE_PEdorAPPREEND//自垫预终止通知书
					// + "','" + PrintManagerBL.CODE_PEdorPreInvali//失效预终止通知书
					// + "','" +
					// PrintManagerBL.CODE_PEdorExpireTerminate//满期终止通知书
					// + "','" + PrintManagerBL.CODE_PEdorLOANPREEND//贷款自垫预终止通知书
					// + "','" +
					// PrintManagerBL.CODE_PEdorBankFine//保全收费银行划款成功通知书
					// + "','" + PrintManagerBL.CODE_EdorINSUACC//万能险结算状态报告书
					// + "')"
					+ "' and otherno in (select rpad(polno,lislen('loprtmanager','otherno'),' ')"
					+ " from lcpol where contno = '?contno?'"
					+ " union select rpad('?contno?',lislen('loprtmanager','otherno'),' ')"
					+ " from ldsysvar where sysvar='onerow')";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(updtsql);
			sqlbv.put("code", PrintManagerBL.CODE_BONUSPAY);
			sqlbv.put("contno", mLPEdorItemSchema.getContNo());
			mMap.put(sqlbv, "UPDATE");
			// ---------------------

			// LCPol表数据交换-->
			LPPolDB tLPPolDB = new LPPolDB();
			tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
			// tLPPolDB.setPolNo(mLPEdorItemSchema.getPolNo()); //因为是险种级保全项目
			LPPolSet chgLPPolSet = tLPPolDB.query();
			if (tLPPolDB.mErrors.needDealError()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorPUConfirmBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询保全保单险种记录信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			for (int k = 1; k <= chgLPPolSet.size(); k++) {
				LPPolSchema tLPPolSchema = chgLPPolSet.get(k);
				LCPolSchema tLCPolSchema = new LCPolSchema();
				this.mReflections.transFields(tLCPolSchema, tLPPolSchema);
				tLCPolSchema.setOperator(this.mGlobalInput.Operator);
				tLCPolSchema.setModifyDate(strCurrentDate);
				tLCPolSchema.setModifyTime(strCurrentTime);
				mMap.put(tLCPolSchema, "DELETE&INSERT");
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(tLPPolSchema.getPolNo());
				if (!tLCPolDB.getInfo()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorPUConfirmBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询保单险种记录信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLCPolSchema = new LCPolSchema();
				tLCPolSchema = tLCPolDB.getSchema();
				tLPPolSchema = new LPPolSchema();
				this.mReflections.transFields(tLPPolSchema, tLCPolSchema);
				tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPolSchema.setOperator(this.mGlobalInput.Operator);
				tLPPolSchema.setModifyDate(strCurrentDate);
				tLPPolSchema.setModifyTime(strCurrentTime);
				mMap.put(tLPPolSchema, "DELETE&INSERT");

				// LCDuty表数据交换-->
				LPDutyDB tLPDutyDB = new LPDutyDB();
				tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPDutyDB.setContNo(mLPEdorItemSchema.getContNo());
				tLPDutyDB.setPolNo(tLPPolSchema.getPolNo());
				LPDutySet tempLPDutySet = new LPDutySet();
				tempLPDutySet = tLPDutyDB.query();
				if (tempLPDutySet == null || tempLPDutySet.size() <= 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorPUConfirmBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询保全责任记录信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				LCDutySet tLCDutySet = new LCDutySet();
				for (int i = 1; i <= tempLPDutySet.size(); i++) {
					LCDutySchema tLCDutySchema = new LCDutySchema();
					this.mReflections.transFields(tLCDutySchema, tempLPDutySet
							.get(i));
					tLCDutySchema.setOperator(this.mGlobalInput.Operator);
					tLCDutySchema.setModifyDate(strCurrentDate);
					tLCDutySchema.setModifyTime(strCurrentTime);
					tLCDutySet.add(tLCDutySchema);
				}
				tSql = "DELETE FROM LCDuty WHERE ContNo='?ContNo?' and PolNo='?PolNo?'";
				SQLwithBindVariables sbv1=new SQLwithBindVariables();
				sbv1.sql(tSql);
				sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv1.put("PolNo", tLPPolSchema.getPolNo());
				mMap.put(sbv1, "DELETE");
				mMap.put(tLCDutySet, "DELETE&INSERT");
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCDutyDB.setPolNo(tLPPolSchema.getPolNo());
				LCDutySet tempLCDutySet = tLCDutyDB.query();
				if (tempLCDutySet == null || tempLCDutySet.size() <= 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorPUConfirmBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询责任记录信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				LPDutySet tLPDutySet = new LPDutySet();
				for (int i = 1; i <= tempLCDutySet.size(); i++) {
					LPDutySchema tLPDutySchema = new LPDutySchema();
					this.mReflections.transFields(tLPDutySchema, tempLCDutySet
							.get(i));
					tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPDutySchema.setOperator(this.mGlobalInput.Operator);
					tLPDutySchema.setModifyDate(strCurrentDate);
					tLPDutySchema.setModifyTime(strCurrentTime);
					tLPDutySet.add(tLPDutySchema);
				}
				tSql = "DELETE FROM LPDuty WHERE EdorNo='?EdorNo?' and EdorType='?EdorType?' and ContNo='?ContNo?' and PolNo='?PolNo?'";
				SQLwithBindVariables sbv2=new SQLwithBindVariables();
				sbv2.sql(tSql);
				sbv2.put("EdorNo", mLPEdorItemSchema.getEdorNo());
				sbv2.put("EdorType", mLPEdorItemSchema.getEdorType());
				sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv2.put("PolNo", tLPPolSchema.getPolNo());
				mMap.put(sbv2, "DELETE");
				mMap.put(tLPDutySet, "DELETE&INSERT");

				// LCPrem表数据交换-->
				LPPremDB tLPPremDB = new LPPremDB();
				tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPremDB.setContNo(mLPEdorItemSchema.getContNo());
				tLPPremDB.setPolNo(tLPPolSchema.getPolNo());
				LPPremSet tempLPPremSet = new LPPremSet();
				tempLPPremSet = tLPPremDB.query();
				if (tempLPPremSet == null || tempLPPremSet.size() <= 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorPUConfirmBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询保全保费项记录信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				LCPremSet tLCPremSet = new LCPremSet();
				for (int i = 1; i <= tempLPPremSet.size(); i++) {
					LCPremSchema tLCPremSchema = new LCPremSchema();
					this.mReflections.transFields(tLCPremSchema, tempLPPremSet
							.get(i));
					tLCPremSchema.setOperator(this.mGlobalInput.Operator);
					tLCPremSchema.setModifyDate(strCurrentDate);
					tLCPremSchema.setModifyTime(strCurrentTime);
					tLCPremSet.add(tLCPremSchema);
				}
				tSql = "DELETE FROM LCPrem WHERE ContNo='?ContNo?' and PolNo='?PolNo?'";
				SQLwithBindVariables sbv3=new SQLwithBindVariables();
				sbv3.sql(tSql);
				sbv3.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv3.put("PolNo", tLPPolSchema.getPolNo());
				mMap.put(sbv3, "DELETE");
				mMap.put(tLCPremSet, "DELETE&INSERT");
				LCPremDB tLCPremDB = new LCPremDB();
				tLCPremDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCPremDB.setPolNo(tLPPolSchema.getPolNo());
				LCPremSet tempLCPremSet = tLCPremDB.query();
				if (tempLCPremSet == null || tempLCPremSet.size() <= 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorPUConfirmBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询保费项记录信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				LPPremSet tLPPremSet = new LPPremSet();
				for (int i = 1; i <= tempLCPremSet.size(); i++) {
					LPPremSchema tLPPremSchema = new LPPremSchema();
					this.mReflections.transFields(tLPPremSchema, tempLCPremSet
							.get(i));
					tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPPremSchema.setOperator(this.mGlobalInput.Operator);
					tLPPremSchema.setModifyDate(strCurrentDate);
					tLPPremSchema.setModifyTime(strCurrentTime);
					tLPPremSet.add(tLPPremSchema);
				}
				tSql = "DELETE FROM LPPrem WHERE EdorNo='?EdorNo?' and EdorType='?EdorType?' and ContNo='?ContNo?' and PolNo='?PolNo?'";
				SQLwithBindVariables sbv4=new SQLwithBindVariables();
				sbv4.sql(tSql);
				sbv4.put("EdorNo", mLPEdorItemSchema.getEdorNo());
				sbv4.put("EdorType", mLPEdorItemSchema.getEdorType());
				sbv4.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv4.put("PolNo", tLPPolSchema.getPolNo());
				mMap.put(sbv4, "DELETE");
				mMap.put(tLPPremSet, "DELETE&INSERT");

				// LCGet表数据交换-->
				LPGetDB tLPGetDB = new LPGetDB();
				tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPGetDB.setContNo(mLPEdorItemSchema.getContNo());
				tLPGetDB.setPolNo(tLPPolSchema.getPolNo());
				LPGetSet tempLPGetSet = new LPGetSet();
				tempLPGetSet = tLPGetDB.query();
				if (tempLPGetSet == null || tempLPGetSet.size() <= 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorPUConfirmBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询保全领取项记录信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				LCGetSet tLCGetSet = new LCGetSet();
				for (int i = 1; i <= tempLPGetSet.size(); i++) {
					LCGetSchema tLCGetSchema = new LCGetSchema();
					this.mReflections.transFields(tLCGetSchema, tempLPGetSet
							.get(i));
					tLCGetSchema.setOperator(this.mGlobalInput.Operator);
					tLCGetSchema.setModifyDate(strCurrentDate);
					tLCGetSchema.setModifyTime(strCurrentTime);
					tLCGetSet.add(tLCGetSchema);
				}
				tSql = "DELETE FROM LCGet WHERE ContNo='?ContNo?' and PolNo='?PolNo?'";
				SQLwithBindVariables sbv5=new SQLwithBindVariables();
				sbv5.sql(tSql);
				sbv5.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv5.put("PolNo", tLPPolSchema.getPolNo());
				mMap.put(sbv5, "DELETE");
				mMap.put(tLCGetSet, "DELETE&INSERT");
				LCGetDB tLCGetDB = new LCGetDB();
				tLCGetDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCGetDB.setPolNo(tLPPolSchema.getPolNo());
				LCGetSet tempLCGetSet = tLCGetDB.query();
				if (tempLCGetSet == null || tempLCGetSet.size() <= 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorPUConfirmBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询领取项记录信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				LPGetSet tLPGetSet = new LPGetSet();
				for (int i = 1; i <= tempLCGetSet.size(); i++) {
					LPGetSchema tLPGetSchema = new LPGetSchema();
					this.mReflections.transFields(tLPGetSchema, tempLCGetSet
							.get(i));
					tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPGetSchema.setOperator(this.mGlobalInput.Operator);
					tLPGetSchema.setModifyDate(strCurrentDate);
					tLPGetSchema.setModifyTime(strCurrentTime);
					tLPGetSet.add(tLPGetSchema);
				}
				tSql = "DELETE FROM LPGet WHERE EdorNo='?EdorNo?' and EdorType='?EdorType?' and ContNo='?ContNo?' and PolNo='?PolNo?'";
				SQLwithBindVariables sbv6=new SQLwithBindVariables();
				sbv6.sql(tSql);
				sbv6.put("EdorNo", mLPEdorItemSchema.getEdorNo());
				sbv6.put("EdorType", mLPEdorItemSchema.getEdorType());
				sbv6.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv6.put("PolNo", tLPPolSchema.getPolNo());
				mMap.put(sbv6, "DELETE");
				mMap.put(tLPGetSet, "DELETE&INSERT");
			}

			// 改变保单状态
			LPContStateDB tLPContStateDB = new LPContStateDB();
			tLPContStateDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContStateDB.setEdorType(mLPEdorItemSchema.getEdorType());
			LPContStateSet tLPContStateSet = tLPContStateDB.query();
			if (tLPContStateSet == null || tLPContStateSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorPUConfirmBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询保全状态记录信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mMap.put(tLPContStateSet, "DELETE");
			LCContStateSet tLCContStateSet = new LCContStateSet();
			// LPContStateSet tInsertLPContStateSet = tLPContStateDB.query();
			// //把C表中主键重复的记录备份到P表，回退时要用
			LPContStateSet tInsertLPContStateSet = new LPContStateSet();
			LCContStateDB tLCContStateDB = new LCContStateDB();
			for (int i = 1; i <= tLPContStateSet.size(); i++) {
				LCContStateSchema tLCContStateSchema = new LCContStateSchema();
				this.mReflections.transFields(tLCContStateSchema,
						tLPContStateSet.get(i));
				tLCContStateSchema.setOperator(this.mGlobalInput.Operator);
				tLCContStateSchema.setModifyDate(strCurrentDate);
				tLCContStateSchema.setModifyTime(strCurrentTime);
				tLCContStateSet.add(tLCContStateSchema);
				// 查询主键重复数据，备份到P表
				tLCContStateDB.setSchema(tLCContStateSchema);
				if (tLCContStateDB.getInfo()) {
					LPContStateSchema tLPContStateSchema = new LPContStateSchema();
					this.mReflections.transFields(tLPContStateSchema,
							tLCContStateDB.getSchema());
					tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPContStateSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					tLPContStateSchema.setOperator(this.mGlobalInput.Operator);
					tLPContStateSchema.setModifyDate(strCurrentDate);
					tLPContStateSchema.setModifyTime(strCurrentTime);
					tInsertLPContStateSet.add(tLPContStateSchema);
				}
			}
			mMap.put(tLCContStateSet, "DELETE&INSERT");
			mMap.put(tInsertLPContStateSet, "DELETE&INSERT");

			// 应李卓要求，这里既然所有附加险都已经终止，那就将LCPol里附加险的AppFlag改成4
			// tSql = "UPDATE LCPol"
			// + " SET AppFlag='4',"
			// + " Operator='" + this.mGlobalInput.Operator + "',"
			// + " ModifyDate=to_date('" + strCurrentDate + "','YYYY-MM-DD'),"
			// + " ModifyTime='" + strCurrentTime + "'"
			// + " WHERE ContNo='" + mLPEdorItemSchema.getContNo() + "' and
			// MainPolNo<>PolNo";
			// mMap.put(tSql, "UPDATE");

			// PU只能在宽限期内做，所以PU生效后短期附加险自动终止，终止状态已在Detail中处理 zhangtao 2005-12-05
			String updLCPol = " UPDATE LCPol SET AppFlag = '4' "
					+ " where 1=1 "
					+ " and riskcode in (select riskcode from lmriskapp where SubRiskFlag = 'S' and riskprop <> 'G' "
					+ " and riskcode in (select riskcode from LMRiskDuty where dutycode in (select dutycode from LMDuty where InsuYear is not null))) "
					+ " and polno <> mainpolno " + " and appflag = '1' "
					+ " and mainpolno = '?mainpolno?' ";
			SQLwithBindVariables sbv7=new SQLwithBindVariables();
			sbv7.sql(updLCPol);
			sbv7.put("mainpolno", mLPEdorItemSchema.getPolNo());
			mMap.put(sbv7, "UPDATE");
			// 删除续期应收
			tSql = " delete from ljspay where othernotype = '2' and otherno = '?otherno?'";
			SQLwithBindVariables sbv8=new SQLwithBindVariables();
			sbv8.sql(tSql);
			sbv8.put("otherno", mLPEdorItemSchema.getContNo());
			mMap.put(sbv8, "DELETE");
			tSql = " delete from ljspayperson where contno = '?contno?'";
			SQLwithBindVariables sbv9=new SQLwithBindVariables();
			sbv9.sql(tSql);
			sbv9.put("contno", mLPEdorItemSchema.getContNo());
			mMap.put(sbv9, "DELETE");
			tSql = " delete from laspayperson where actupayflag = '0' and contno = '?contno?'";
			SQLwithBindVariables sbv10=new SQLwithBindVariables();
			sbv10.sql(tSql);
			sbv10.put("contno", mLPEdorItemSchema.getContNo());
			mMap.put(sbv10, "DELETE");

			// //设置保全主表数据到保全确认状态
			// this.mLPEdorItemSchema.setEdorState("0");
			// mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
			// mLPEdorItemSchema.setModifyDate(strCurrentDate);
			// mLPEdorItemSchema.setModifyTime(strCurrentTime);
			// mMap.put(mLPEdorItemSchema, "UPDATE");

			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPUConfirmBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误！ " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		PEdorPUConfirmBL tPEdorPUConfirmBL = new PEdorPUConfirmBL();
	}
}
