package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 查询保单的各种方法
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class QueryLCPolTypeBL {
private static Logger logger = Logger.getLogger(QueryLCPolTypeBL.class);

	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCPolSet mLCPolSet = new LCPolSet();

	public QueryLCPolTypeBL() {
	}

	public static void main(String[] args) {
	}

	public LCPolSet getResult() {
		return mLCPolSet;
	}

	/**
	 * 根据印刷号查询个人保单表 附属查询条件：
	 * 
	 * @param tLCPolSchema
	 * @return
	 */
	public boolean queryA(LCPolSchema tLCPolSchema) {
		if (tLCPolSchema == null) {
			getError("queryA");
			return false;
		}
		if (tLCPolSchema.getPrtNo() == null) {
			getError("queryA", "传入的印刷号是空值！");
			return false;
		}
		String strSQL = "select * from LCPol where PrtNo='?PrtNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.put("PrtNo", tLCPolSchema.getPrtNo());
		strSQL = strSQL
				+ " and (StopFlag='0' or StopFlag is null) and GrpPolNo='00000000000000000000'";
		if (tLCPolSchema.getManageCom() != null) {
			String MaxManageCom = PubFun.RCh(tLCPolSchema.getManageCom(), "9",
					8);
			String MinManageCom = PubFun.RCh(tLCPolSchema.getManageCom(), "0",
					8);
			;
			strSQL = strSQL + " and ManageCom>='?MinManageCom?' and ManageCom<='?MaxManageCom?'";
			sqlbv.put("MinManageCom", MinManageCom);
			sqlbv.put("MaxManageCom", MaxManageCom);
		}
		if (tLCPolSchema.getAppFlag() != null) {
			strSQL = strSQL + " and AppFlag='?AppFlag?'";
			sqlbv.put("AppFlag", tLCPolSchema.getAppFlag());
		}

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setSchema(mLCPolSchema);
		mLCPolSet = tLCPolDB.executeQuery(sqlbv);
		if (tLCPolDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			getError("queryA", "数据库查询失败!");
			mLCPolSet.clear();
			return false;
		}
		if (mLCPolSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			getError("queryA", "未找到相关数据!");
			mLCPolSet.clear();
			return false;
		}

		return true;
	}

	/**
	 * 根据保单号查询个人保单表
	 * 
	 * @param tLCPolSchema
	 * @return
	 */
	public boolean queryB(LCPolSchema tLCPolSchema) {
		if (tLCPolSchema == null) {
			getError("queryB");
			return false;
		}
		if (tLCPolSchema.getPolNo() == null) {
			getError("queryB", "传入的保单号是空值！");
			return false;
		}
		String strSQL = "select * from LCPol where PolNo='?PolNo?'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.put("PolNo", tLCPolSchema.getPolNo());
		strSQL = strSQL
				+ " and (StopFlag='0' or StopFlag is null) and GrpPolNo='00000000000000000000'";
		if (tLCPolSchema.getManageCom() != null) {
			String MaxManageCom = PubFun.RCh(tLCPolSchema.getManageCom(), "9",
					8);
			String MinManageCom = PubFun.RCh(tLCPolSchema.getManageCom(), "0",
					8);
			;
			strSQL = strSQL + " and ManageCom>='?MinManageCom?' and ManageCom<='?MaxManageCom?'";
			sqlbv1.put("MinManageCom", MinManageCom);
			sqlbv1.put("MaxManageCom", MaxManageCom);
		}
		if (tLCPolSchema.getAppFlag() != null) {
			strSQL = strSQL + " and AppFlag='?AppFlag?'";
			sqlbv1.put("AppFlag", tLCPolSchema.getAppFlag());
		}
		if (tLCPolSchema.getPayLocation() != null) {
			strSQL = strSQL + "  and PayLocation='?PayLocation?'  ";
			sqlbv1.put("PayLocation", tLCPolSchema.getPayLocation());
		}
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setSchema(mLCPolSchema);
		sqlbv1.sql(strSQL);
		mLCPolSet = tLCPolDB.executeQuery(sqlbv1);
		if (tLCPolDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			getError("queryB", "数据库查询失败!");
			mLCPolSet.clear();
			return false;
		}
		if (mLCPolSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			getError("queryB", "未找到相关数据!");
			mLCPolSet.clear();
			return false;
		}

		return true;
	}

	/**
	 * 根据时间段查询个人保单表（交费位置=8 --银行转帐(事后转账)）
	 * 
	 * @param tLCPolSchema
	 * @return
	 */
	public boolean queryC(LCPolSchema tLCPolSchema) {
		if (tLCPolSchema == null) {
			getError("queryC");
			return false;
		}
		if (tLCPolSchema.getGetStartDate() == null
				|| tLCPolSchema.getPayEndDate() == null) {
			getError("queryC", "传入的起始日期是空值！");
			return false;
		}
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		String strSQL = "select * from LCPol ";
		strSQL = strSQL + " where ((MakeDate>='?GetStartDate?' and MakeDate<='?PayEndDate?')) ";
		strSQL = strSQL
				+ " and (StopFlag='0' or StopFlag is null) and GrpPolNo='00000000000000000000'";
		sqlbv2.put("GetStartDate", tLCPolSchema.getGetStartDate());
		sqlbv2.put("PayEndDate", tLCPolSchema.getPayEndDate());
		if (tLCPolSchema.getManageCom() != null) {
			String MaxManageCom = PubFun.RCh(tLCPolSchema.getManageCom(), "9",
					8);
			String MinManageCom = PubFun.RCh(tLCPolSchema.getManageCom(), "0",
					8);
			;
			strSQL = strSQL + " and ManageCom>='?MinManageCom?' and ManageCom<='?MaxManageCom?'";
			sqlbv2.put("MinManageCom", MinManageCom);
			sqlbv2.put("MaxManageCom", MaxManageCom);
			
		}
		if (tLCPolSchema.getAppFlag() != null) {
			strSQL = strSQL + " and AppFlag='?AppFlag?'";
			sqlbv2.put("AppFlag", tLCPolSchema.getAppFlag());
		}
		if (tLCPolSchema.getPayLocation() != null) {
			strSQL = strSQL + "  and PayLocation='?PayLocation?'  ";
			sqlbv2.put("PayLocation", tLCPolSchema.getPayLocation());
		}
		if (tLCPolSchema.getPayMode() != null) {
			strSQL = strSQL + "  and PayMode='?PayMode?'  ";
			sqlbv2.put("PayMode", tLCPolSchema.getPayMode());
		}
		if (tLCPolSchema.getSaleChnl() != null) {
			strSQL = strSQL + "  and SaleChnl='?SaleChnl?'  ";
			sqlbv2.put("SaleChnl", tLCPolSchema.getSaleChnl());
		}

		strSQL = strSQL
				+ "  and (UWFlag is null or UWFlag not in ('a', '0', '1')) ";

		logger.debug("Sql" + strSQL);

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setSchema(mLCPolSchema);
		sqlbv2.sql(strSQL);
		mLCPolSet = tLCPolDB.executeQuery(sqlbv2);
		if (tLCPolDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			getError("queryC", "数据库查询失败!");
			mLCPolSet.clear();
			return false;
		}
		if (mLCPolSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			getError("queryC", "未找到相关数据!");
			mLCPolSet.clear();
			return false;
		}

		return true;
	}

	/**
	 * 填充错误信息
	 * 
	 * @param funName
	 * @param errMsg
	 */
	public void getError(String funName, String errMsg) {
		CError tError = new CError();
		tError.moduleName = "QueryLCPolTypeBL";
		tError.functionName = funName;
		tError.errorMessage = errMsg;
		mErrors.addOneError(tError);
	}

	/**
	 * 填充错误信息(自动赋错误信息：传入参数不能为空)
	 * 
	 * @param funName
	 */
	public void getError(String funName) {
		CError tError = new CError();
		tError.moduleName = "QueryLCPolTypeBL";
		tError.functionName = funName;
		tError.errorMessage = "传入参数不能为空！";
		mErrors.addOneError(tError);
	}
}
