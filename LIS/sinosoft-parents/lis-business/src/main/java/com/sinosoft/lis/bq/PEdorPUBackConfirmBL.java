package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 减额缴清回退确认BL
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

public class PEdorPUBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorPUBackConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private Reflections mReflections = new Reflections();

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorPUBackConfirmBL() {
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
				return this.makeError("dealData", "查询保全保单记录信息失败！");
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
				return this.makeError("dealData", "查询保单记录信息失败！");
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
					return this.makeError("dealData", "查询保单险种记录信息失败！");
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
					return this.makeError("dealData", "查询保全责任记录信息失败！");
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
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
				sqlbv.put("PolNo", tLPPolSchema.getPolNo());
				mMap.put(sqlbv, "DELETE");
				mMap.put(tLCDutySet, "DELETE&INSERT");
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCDutyDB.setPolNo(tLPPolSchema.getPolNo());
				LCDutySet tempLCDutySet = tLCDutyDB.query();
				if (tempLCDutySet == null || tempLCDutySet.size() <= 0) {
					// @@错误处理
					return this.makeError("dealData", "查询责任记录信息失败！");
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
				SQLwithBindVariables sbv=new SQLwithBindVariables();
				sbv.sql(tSql);
				sbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
				sbv.put("EdorType", mLPEdorItemSchema.getEdorType());
				sbv.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv.put("PolNo", tLPPolSchema.getPolNo());
				mMap.put(sbv, "DELETE");
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
					return this.makeError("dealData", "查询保全保费项记录信息失败！");
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
				SQLwithBindVariables sbv1=new SQLwithBindVariables();
				sbv1.sql(tSql);
				sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv1.put("PolNo", tLPPolSchema.getPolNo());
				mMap.put(sbv1, "DELETE");
				mMap.put(tLCPremSet, "DELETE&INSERT");
				LCPremDB tLCPremDB = new LCPremDB();
				tLCPremDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCPremDB.setPolNo(tLPPolSchema.getPolNo());
				LCPremSet tempLCPremSet = tLCPremDB.query();
				if (tempLCPremSet == null || tempLCPremSet.size() <= 0) {
					// @@错误处理
					return this.makeError("dealData", "查询保费项记录信息失败！");
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
				SQLwithBindVariables sbv2=new SQLwithBindVariables();
				sbv2.sql(tSql);
				sbv2.put("EdorNo", mLPEdorItemSchema.getEdorNo());
				sbv2.put("EdorType", mLPEdorItemSchema.getEdorType());
				sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv2.put("PolNo", tLPPolSchema.getPolNo());
				mMap.put(sbv2, "DELETE");
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
					return this.makeError("dealData", "查询保全领取项记录信息失败！");
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
				SQLwithBindVariables sbv3=new SQLwithBindVariables();
				sbv3.sql(tSql);
				sbv3.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv3.put("PolNo", tLPPolSchema.getPolNo());
				mMap.put(sbv3, "DELETE");
				mMap.put(tLCGetSet, "DELETE&INSERT");
				LCGetDB tLCGetDB = new LCGetDB();
				tLCGetDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCGetDB.setPolNo(tLPPolSchema.getPolNo());
				LCGetSet tempLCGetSet = tLCGetDB.query();
				if (tempLCGetSet == null || tempLCGetSet.size() <= 0) {
					// @@错误处理
					return this.makeError("dealData", "查询领取项记录信息失败！");
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
				SQLwithBindVariables sbv4=new SQLwithBindVariables();
				sbv4.sql(tSql);
				sbv4.put("EdorNo", mLPEdorItemSchema.getEdorNo());
				sbv4.put("EdorType", mLPEdorItemSchema.getEdorType());
				sbv4.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv4.put("PolNo", tLPPolSchema.getPolNo());
				mMap.put(sbv4, "DELETE");
				mMap.put(tLPGetSet, "DELETE&INSERT");

				// 回退险种减额缴清状态信息
				if (!updRPUState(tLCPolSchema.getContNo(), tLCPolSchema
						.getPolNo())) {
					return false;
				}
			}

			// 查出由于减额缴清而终止的附加险，将其状态恢复
			String sql = " select contno, polno from lccontstate "
					+ " where startdate = '?startdate?' "
					+ " and statereason = '08' and state = '1' and statetype = 'Terminate' "
					+ " and  contno = '?contno?'";
			SQLwithBindVariables sbv5=new SQLwithBindVariables();
			sbv5.sql(sql);
			sbv5.put("startdate", mLPEdorItemSchema.getEdorValiDate());
			sbv5.put("contno", mLPEdorItemSchema.getContNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sbv5);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				for (int i = 1; i <= tSSRS.MaxRow; i++) {
					// 回退终止状态
					if (!updTerminateState(tSSRS.GetText(i, 1), tSSRS.GetText(
							i, 2))) {
						return false;
					}
					// 更新AppFlag
					sql = " update lcpol set appflag = '1' where polno = '?polno?'";
					SQLwithBindVariables sbv6=new SQLwithBindVariables();
					sbv6.sql(sql);
					sbv6.put("polno", tSSRS.GetText(i, 2));
					mMap.put(sbv6, "UPDATE");
				}
			}
			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			return this.makeError("dealData", "数据处理错误！" + e.getMessage());
		}
		return true;
	}

	/**
	 * 回退险种减额缴清状态
	 */
	private boolean updRPUState(String sContNo, String sPolNo) {

		String delSql = " delete from lccontstate where trim(statetype) = 'RPU' and trim(state) = '1' "
				+ " and enddate is null and startdate = '?startdate?' and contno = '"
				+ "?sContNo?" +
				// "' and insuredno = '" + sInsuredNo + //数据转换为000000
				"' and polno = '?sPolNo?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(delSql);
		sbv1.put("startdate", mLPEdorItemSchema.getEdorValiDate());
		sbv1.put("sContNo", sContNo);
		sbv1.put("sPolNo", sPolNo);
		mMap.put(sbv1, "DELETE");

		String sEndDate = PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(),
				-1, "D", null);
		String sql = " select * from lccontstate where trim(statetype) = 'RPU' "
				+ " and enddate = '?sEndDate?'  and contno = '"
				+ "?sContNo?"
				+
				// "' and insuredno = '" + sInsuredNo +
				"' and polno = '?sPolNo?'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sql);
		sbv2.put("sEndDate", sEndDate);
		sbv2.put("sContNo", sContNo);
		sbv2.put("sPolNo", sPolNo);
		logger.debug(sql);
		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = tLCContStateDB.executeQuery(sbv2);
		if (tLCContStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单状态查询失败!");
			return false;
		}
		if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
			tLCContStateSet.get(1).setEndDate("");
			mMap.put(tLCContStateSet.get(1), "UPDATE");
		}

		return true;
	}

	/**
	 * 回退由于减额缴清而终止的附加险状态
	 * 
	 * @param sContNo
	 * @param sPolNo
	 */
	private boolean updTerminateState(String sContNo, String sPolNo) {

		String delSql = " delete from lccontstate "
				+ " where trim(statetype) = 'Terminate' and trim(state) = '1' and trim(statereason) = '08'"
				+ " and enddate is null and startdate = '?startdate?' and contno = '?sContNo?' and polno = '?sPolNo?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(delSql);
		sbv1.put("startdate", mLPEdorItemSchema.getEdorValiDate());
		sbv1.put("sContNo", sContNo);
		sbv1.put("sPolNo", sPolNo);
		mMap.put(sbv1, "DELETE");

		String sEndDate = PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(),
				-1, "D", null);
		String sql = " select * from lccontstate where trim(statetype) = 'Terminate' "
				+ " and enddate = '?sEndDate?'  and contno = '?sContNo?' and polno = '?sPolNo?'";
		logger.debug(sql);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sql);
		sbv2.put("sEndDate", sEndDate);
		sbv2.put("sContNo", sContNo);
		sbv2.put("sPolNo", sPolNo);
		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = tLCContStateDB.executeQuery(sbv2);
		if (tLCContStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单状态查询失败!");
			return false;
		}
		if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
			tLCContStateSet.get(1).setEndDate("");
			mMap.put(tLCContStateSet.get(1), "UPDATE");
		}

		return true;
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
		tError.moduleName = "PEdorPUBackConfirmBL";
		tError.functionName = vFuncName;
		tError.errorMessage = vErrMsg;
		this.mErrors.addOneError(tError);
		return false;
	}

	public static void main(String[] args) {
		PEdorPUBackConfirmBL tPEdorPUBackConfirmBL = new PEdorPUBackConfirmBL();
	}
}
