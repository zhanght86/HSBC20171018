package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
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
 * Description: 自垫清偿回退确认BL
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

public class PEdorTRBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorTRBackConfirmBL.class);
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
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorTRBackConfirmBL() {
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

			// 获得自垫时的EdorNo，即此时LOReturnLoan里的LoanNo
			String tSql = "SELECT LoanNo" + " FROM LOReturnLoan"
					+ " WHERE ContNo='?ContNo?'"
					+ " and EdorNo='?EdorNo?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
			sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				return this.makeError("dealData", "查询原自垫批单号失败！");
			}
			// 恢复LOLoan数据为自垫
			tSql = "UPDATE LOLoan set NewLoanDate=null,payoffdate=null,leavemoney=summoney,PayOffFlag='0'," + " Operator='?Operator?',"
					+ " ModifyDate=to_date('?strCurrentDate?','YYYY-MM-DD')," + " ModifyTime='?strCurrentTime?'" + " WHERE ContNo='?ContNo?'" 
					+ " and EdorNo='?EdorNo?'"
					+ " and LoanType='1'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("Operator", this.mGlobalInput.Operator);
			sqlbv1.put("strCurrentDate", strCurrentDate);
			sqlbv1.put("strCurrentTime", strCurrentTime);
			sqlbv1.put("ContNo", mLPEdorItemSchema.getContNo());
			sqlbv1.put("EdorNo", tSSRS.GetText(1, 1));
			mMap.put(sqlbv1, "UPDATE");

			// 删除还款表数据
			tSql = "DELETE FROM LOReturnLoan WHERE" + " ContNo='?ContNo?'" + " and EdorNo='?EdorNo?'" + " and LoanType='1'";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(tSql);
			sqlbv2.put("ContNo", mLPEdorItemSchema.getContNo());
			sqlbv2.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			mMap.put(sqlbv2, "DELETE");
			// 删除自垫清偿状态
			tSql = "DELETE FROM LCContState WHERE" + " ContNo='?ContNo?'" 
					+ " and StateType='PayPrem'" + " and State='0'"
					+ " and EndDate is null";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(tSql);
			sqlbv3.put("ContNo", mLPEdorItemSchema.getContNo());
			mMap.put(sqlbv3, "DELETE");

			// 恢复自垫状态
			// 获得自垫清偿状态起期
			tSql = "SELECT to_char(StartDate,'YYYY-MM-DD')"
					+ " FROM LCContState" + " WHERE ContNo='?ContNo?'" 
					+ " and StateType='PayPrem'" + " and State='0'"
					+ " and EndDate is null";
			tSSRS = new SSRS();
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSql);
			sqlbv4.put("ContNo", mLPEdorItemSchema.getContNo());
			tSSRS = tExeSQL.execSQL(sqlbv4);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				return this.makeError("dealData", "查询清偿后非自垫状态起期失败！");
			}
			// 恢复上一个非贷款状态
			tSql = "UPDATE LCContState set EndDate=null,"
					+ " Operator='?Operator?',"
					+ " ModifyDate=to_date('?strCurrentDate?','YYYY-MM-DD'),"
					+ " ModifyTime='?strCurrentTime?'"
					+ " WHERE ContNo='?ContNo?'"
					+ " and StateType='PayPrem'"
					+ " and State='1'"
					+ " and to_date(to_char(adddate(EndDate,1),'YYYY-MM-DD'),'YYYY-MM-DD')=to_date('?EndDate?','YYYY-MM-DD')"
					+ " and EndDate is not null";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(tSql);
			sqlbv5.put("Operator", this.mGlobalInput.Operator);
			sqlbv5.put("strCurrentDate", strCurrentDate);
			sqlbv5.put("strCurrentTime", strCurrentTime);
			sqlbv5.put("ContNo", mLPEdorItemSchema.getContNo());
			sqlbv5.put("EndDate", tSSRS.GetText(1, 1));
			mMap.put(sqlbv5, "UPDATE");

			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			return this.makeError("dealData", "数据处理错误！" + e.getMessage());
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
		tError.moduleName = "PEdorTRBackConfirmBL";
		tError.functionName = vFuncName;
		tError.errorMessage = vErrMsg;
		this.mErrors.addOneError(tError);
		return false;
	}

	public static void main(String[] args) {
		PEdorTRBackConfirmBL tPEdorTRBackConfirmBL = new PEdorTRBackConfirmBL();
	}
}
