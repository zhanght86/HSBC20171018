package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全确认逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 2.0
 */
public class EdorConfirmBL {
private static Logger logger = Logger.getLogger(EdorConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private VData pInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 保全申请类型 */
	private String mContType;

	/** 全局数据 */
	private MMap map = new MMap();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPGrpEdorMainSet mLPGrpEdorMainSet = new LPGrpEdorMainSet();
	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	private String mStrTemplatePath = "";

	private String mOverDueFlag = ""; // 逾期标志 add by zhangtao at 2005-06-01

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public EdorConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据校验操作，校验团体保全主表是否通过核保，是否需要交费,是否已经交费
		// =====UPD=====zhangtao=======2005-06-01========================BGN=============
		if (!checkData()) {
			if (!mOverDueFlag.equals("OverDue")) {
				return false;
			} else {
				// 逾期终止，不再往后执行，但须继续往外层提交
				if (!prepareOutputData()) {
					return false;
				}
				logger.debug("==逾期终止 @ EdorConfirmBL==");
				return true;
			}
		}
		// =====UPD=====zhangtao=======2005-06-01========================END=============
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	/**
	 * 准备处理好的数据，返回给调用类
	 * 
	 * @return
	 */
	private boolean prepareOutputData() {

		mInputData.clear();
		mInputData.add(map);
		mResult.clear();
		mResult.add(map);
		mResult.add(mOverDueFlag);
		return true;
	}

	/**
	 * 业务逻辑处理方法
	 * 
	 * @return
	 */
	public boolean dealData() {
		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

		pInputData = new VData();
		String tEdorNo = mLPEdorMainSchema.getEdorNo();
		for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
			tLPEdorItemSchema = mLPEdorItemSet.get(i);
			String tEdorType = tLPEdorItemSchema.getEdorType();

			tLPEdorItemSchema.setEdorState("0");
			logger.debug("==========befor XXConfirmBL==============");
			try {
				Class tClass = Class.forName("com.sinosoft.lis.bq.PEdor"
						+ tEdorType + "ConfirmBL");
				EdorConfirm tEdorConfirm = (EdorConfirm) tClass.newInstance();
				VData tVData = new VData();

				tVData.add(mGlobalInput);
				tVData.add(tLPEdorItemSchema);

				if (!tEdorConfirm.submitData(tVData, "CONFIRM||" + tEdorType)) {
					CError.buildErr(this, "保全项目" + tEdorType + "申请时失败！失败原因："
							+ tEdorConfirm.mErrors.getFirstError());

					return false;
				} else {
					logger.debug("==========after XXConfirmBL==============");
					VData rVData = tEdorConfirm.getResult();
					MMap tMap = new MMap();
					tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
					if (tMap == null) {
						CError.buildErr(this, "得到保全项目为:" + tEdorType
								+ "的保全确认结果时失败！");
						return false;

					} else {
						map.add(tMap);
					}
				}

			} catch (ClassNotFoundException ex) {
				logger.debug("====ClassNotFoundException===");
				map.put(tLPEdorItemSchema, "UPDATE");
			} catch (Exception ex) {
				CError.buildErr(this, "保全项目" + tEdorType + "申请确认时失败！");
				return false;
			}

		}
		mLPEdorMainSchema.setEdorState("0");
		mLPEdorMainSchema.setConfDate(theCurrentDate);
		mLPEdorMainSchema.setConfTime(theCurrentTime);
		mLPEdorMainSchema.setConfOperator(mGlobalInput.Operator);

		mLPEdorMainSet.add(mLPEdorMainSchema);
		// map.put(mLPGrpEdorMainSchema,"UPDATE");
		map.put(mLPEdorMainSchema, "UPDATE");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return
	 */
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
			mLPEdorMainSchema = (LPEdorMainSchema) mInputData
					.getObjectByObjectName("LPEdorMainSchema", 0);
			mLPGrpEdorMainSchema = (LPGrpEdorMainSchema) mInputData
					.getObjectByObjectName("LPGrpEdorMainSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			mContType = (String) mInputData.get(1);
			mStrTemplatePath = (String) mInputData.get(3); // LR保全打印时用
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		logger.debug("start check data .....");
		boolean flag = true;
		if (mContType.equals("I")) {
			LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
			String sql = "select * from LPEdorMain where edorno = '"
					+ "?edorno?"
					+ "' and uwstate in ('0','5')";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("edorno", mLPEdorMainSchema.getEdorNo());
			LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.executeQuery(sqlbv);
			if (tLPEdorMainSet != null && tLPEdorMainSet.size() > 0) {
				CError tError = new CError();
				tError.errorMessage = "该申请核保没有通过!";
				mErrors.addOneError(tError);
				return false;
			}
			mGlobalInput.ManageCom = mLPEdorMainSchema.getManageCom();
		} else {
			LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String sql = "select * from LPGrpEdorMain where edorno = '"
					+ "?edorno?"
					+ "' and uwstate in ('0','5')";
			sqlbv.sql(sql);
			sqlbv.put("edorno", mLPGrpEdorMainSchema.getEdorNo());
			LPGrpEdorMainSet tLPGrpEdorMainSet = tLPGrpEdorMainDB
					.executeQuery(sqlbv);
			if (tLPGrpEdorMainSet != null && tLPGrpEdorMainSet.size() > 0) {
				CError tError = new CError();
				tError.errorMessage = "该申请核保没有通过!";
				mErrors.addOneError(tError);
				return false;
			}
			mGlobalInput.ManageCom = mLPGrpEdorMainSchema.getManageCom();
		}

		LJSGetSet tLJSGetSet = new LJSGetSet();
		LJSGetDB tLJSGetDB = new LJSGetDB();
		tLJSGetDB.setOtherNo(mLPEdorMainSchema.getEdorNo());
		tLJSGetDB.setOtherNoType("3");
		tLJSGetSet = tLJSGetDB.query();
		if (tLJSGetSet.size() > 0) {
			String aGetNoticeNo = tLJSGetSet.get(1).getGetNoticeNo();
			LJFinaConfirm tLJFinaConfirm = new LJFinaConfirm(aGetNoticeNo, "O");
			tLJFinaConfirm.setOperator(mGlobalInput.Operator);
			tLJFinaConfirm.setLimit(PubFun.getNoLimit(mGlobalInput.ManageCom));
			if (!tLJFinaConfirm.submitData()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorConfirmBL";
				tError.functionName = "checkData";
				tError.errorMessage = "保全退费核销失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(mLPEdorMainSchema.getEdorNo());
		tLJSPayDB.setOtherNoType("3");
		tLJSPaySet = tLJSPayDB.query();
		if (tLJSPaySet.size() > 0) {
			String aGetNoticeNo = tLJSPaySet.get(1).getGetNoticeNo();
			LJFinaConfirm tLJFinaConfirm = new LJFinaConfirm(aGetNoticeNo, "I");
			tLJFinaConfirm.setOperator(mGlobalInput.Operator);
			tLJFinaConfirm.setLimit(PubFun.getNoLimit(mGlobalInput.ManageCom));
			logger.debug("start LJFinaConfirm...");
			if (!tLJFinaConfirm.submitData()) {
				// =====ADD====zhangtao========2005-05-26================BGN=====================
				// 交费逾期校验
				for (int i = 1; i <= tLJSPaySet.size(); i++) {
					LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
					tLJTempFeeDB.setTempFeeNo(tLJSPaySet.get(i)
							.getGetNoticeNo());
					tLJTempFeeDB.setTempFeeType("4"); // 保全交费
					LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.query();
					if (tLJTempFeeSet != null && tLJTempFeeSet.size() > 1) {
						// 已经交费
						continue;
					} else {
						// 没有交费
						String sPayDate = tLJSPaySet.get(i).getPayDate();
						int itvs = PubFun.calInterval(sPayDate, theCurrentDate,
								"D");
						if (itvs > 22) {
							// 超过了补费止期（22天）
							mOverDueFlag = "OverDue";
							logger.debug("== 逾期终止 ==");
							// 设置保全申请为逾期终止状态
							setEdorAppStop();
							return false;
						}
					}
				}
				// =====ADD====zhangtao========2005-05-26================END=====================

				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorConfirmBL";
				tError.functionName = "checkData";
				tError.errorMessage = "保全交费核销失败！请确认是否已交费！";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return flag;
	}

	// =====ADD====zhangtao========2005-05-26================BGN=====================
	/**
	 * 设置保全申请批改状态为逾期终止状态
	 */
	private boolean setEdorAppStop() {
		// 更新保全申请主表
		StringBuffer sbSQL = new StringBuffer();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sbSQL.append(" UPDATE LPEdorApp SET ").append(" EdorState = '4', ")
				.append("Operator = '").append("?Operator?").append(
						"' ,ModifyDate = '").append("?theCurrentDate?").append(
						"' ,ModifyTime = '").append("?theCurrentTime?").append("'")
				.append(" WHERE EDORACCEPTNO = '").append(
						"?EDORACCEPTNO?").append("'");
		sqlbv.sql(sbSQL.toString());
		sqlbv.put("Operator", mGlobalInput.Operator);
		sqlbv.put("theCurrentDate", theCurrentDate);
		sqlbv.put("theCurrentTime", theCurrentTime);
		sqlbv.put("EDORACCEPTNO", mLPEdorMainSchema.getEdorAcceptNo());
		map.put(sqlbv, "UPDATE");
		logger.debug("== UPDATE LPEdorApp ==" + sbSQL.toString());

		// 更新保全批改表
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorMain SET ").append(" EdorState = '4', ")
				.append(" Operator = '").append("?Operator?").append(
						"' ,ModifyDate = '").append("?theCurrentDate?").append(
						"' ,ModifyTime = '").append("?theCurrentTime?").append("'")
				.append(" WHERE EDORACCEPTNO = '").append(
						"?EDORACCEPTNO?").append("'");
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sbSQL.toString());
		sqlbv1.put("Operator", mGlobalInput.Operator);
		sqlbv1.put("theCurrentDate", theCurrentDate);
		sqlbv1.put("theCurrentTime", theCurrentTime);
		sqlbv1.put("EDORACCEPTNO", mLPEdorMainSchema.getEdorAcceptNo());
		map.put(sqlbv1, "UPDATE");
		logger.debug("== UPDATE LPEdorMain ==" + sbSQL.toString());

		// 更新保全项目表
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorItem SET ").append(" EdorState = '4', ")
				.append("Operator = '").append("?Operator?").append(
						"' ,ModifyDate = '").append("?theCurrentDate?").append(
						"' ,ModifyTime = '").append("?theCurrentTime?").append("'")
				.append(" WHERE EDORACCEPTNO = '").append(
						"?EDORACCEPTNO?").append("'");
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sbSQL.toString());
		sqlbv2.put("Operator", mGlobalInput.Operator);
		sqlbv2.put("theCurrentDate", theCurrentDate);
		sqlbv2.put("theCurrentTime", theCurrentTime);
		sqlbv2.put("EDORACCEPTNO", mLPEdorMainSchema.getEdorAcceptNo());
		map.put(sqlbv2, "UPDATE");
		logger.debug("== UPDATE LPEdorItem ==" + sbSQL.toString());

		return true;
	}
	// =====ADD====zhangtao========2005-05-26================END=====================

}
