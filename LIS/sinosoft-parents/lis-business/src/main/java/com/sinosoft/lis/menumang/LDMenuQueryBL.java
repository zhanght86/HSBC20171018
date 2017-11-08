/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDMenuDB;
import com.sinosoft.lis.schema.LDMenuGrpSchema;
import com.sinosoft.lis.schema.LDMenuSchema;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.vschema.LDMenuSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 菜单查询业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author DingZhong
 * @version 1.0
 */
public class LDMenuQueryBL {
private static Logger logger = Logger.getLogger(LDMenuQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	// private String mOperate;
	/** 业务处理相关变量 */
	/** 菜单组、菜单组到菜单的相关信息 */
	LDMenuSchema mLDMenuSchema = new LDMenuSchema();
	LDMenuSet mLDMenuSet = new LDMenuSet();
	LDUserSchema mLDUserSchema = new LDUserSchema();
	LDMenuGrpSchema mLDMenuGrpSchema = new LDMenuGrpSchema();

	// String mResultStr = "";
	int mResultNum = 0;
	private StringBuffer mResultStr = new StringBuffer(256);
	private StringBuffer mSBql = new StringBuffer(256);

	public LDMenuQueryBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 判断操作是不是查询
		if (!cOperate.equals("query") && !cOperate.equals("query_remain")) {
			// logger.debug("Operater is denied");
			return false;
		}
		// logger.debug("start submit...");
		// 将操作数据拷贝到本类中
		// this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// logger.debug("---getInputData---");

		// 进行业务处理
		if (cOperate.equals("query") && !queryMenu()) {
			return false;
		} else if (cOperate.equals("query_remain") && !queryRemainMenu()) {
			return false;
		}

		// logger.debug("---queryMenuGrpToMenu---");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public int getResultNum() {
		return mResultNum;
	}

	public String getResultStr() {
		// String resultStr = "";
		mResultStr = new StringBuffer(256);
		for (int i = 1; i <= mResultNum; i++) {
			mLDMenuSchema = mLDMenuSet.get(i);
			if (i > 1) {
				// resultStr += SysConst.RECORDSPLITER;
				mResultStr.append(SysConst.RECORDSPLITER);
			}
			// resultStr += mLDMenuSchema.getParentNodeCode();
			// resultStr += "|" + mLDMenuSchema.getNodeCode();
			// resultStr += "|" + mLDMenuSchema.getNodeName();
			// resultStr += "|" + mLDMenuSchema.getChildFlag();
			// resultStr += "|" + mLDMenuSchema.getRunScript();
			mResultStr.append(mLDMenuSchema.getParentNodeCode());
			mResultStr.append("|");
			mResultStr.append(mLDMenuSchema.getNodeCode());
			mResultStr.append("|");
			mResultStr.append(mLDMenuSchema.getNodeName());
			mResultStr.append("|");
			mResultStr.append(mLDMenuSchema.getChildFlag());
			mResultStr.append("|");
			mResultStr.append(mLDMenuSchema.getRunScript());
		}
		return mResultStr.toString();
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 检验查询条件
		logger.debug("start get input data...");
		mLDUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
				"LDUserSchema", 0);
		logger.debug("mLDUserSchema:" + mLDUserSchema);
		if (mLDUserSchema != null) {
			return true;
		}

		mLDMenuGrpSchema = (LDMenuGrpSchema) cInputData.getObjectByObjectName(
				"LDMenuGrpSchema", 0);

		if (mLDMenuGrpSchema != null) {
			return true;
		}

		mLDMenuSchema = (LDMenuSchema) cInputData.getObjectByObjectName(
				"LDMenuSchema", 0);

		if (mLDMenuSchema != null) {
			return true;
		}
		logger.debug("completed get input data");
		return true;
	}

	/**
	 * 查询符合条件的菜单的信息 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean queryMenu() {
		LDMenuSchema tLDMenuSchema = new LDMenuSchema();
		LDMenuDB tLDMenuDB = tLDMenuSchema.getDB();

		if (mLDUserSchema != null) {
			// 通过用户编码查询菜单集合
			// 构造查询语句
			// String sqlStr = "select * from LDMenu where NodeCode in ( select
			// NodeCode from LDMenuGrpToMenu ";
			// sqlStr = sqlStr +
			// "where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp ";
			// sqlStr = sqlStr +
			// "where MenuGrpCode in (select MenuGrpCode from LDUserToMenuGrp
			// where UserCode = '" +
			// mLDUserSchema.getUserCode() +
			// "') ) ) order by nodeorder";
			mSBql
					.append("select * from LDMenu where NodeCode in ( select NodeCode from LDMenuGrpToMenu ");
			mSBql
					.append("where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp ");
			mSBql
					.append("where MenuGrpCode in (select MenuGrpCode from LDUserToMenuGrp where UserCode = '");
			mSBql.append("?UserCode?");
			mSBql.append("') ) ) order by nodeorder");
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(mSBql.toString());
			sqlbv.put("UserCode", mLDUserSchema.getUserCode());

			mLDMenuSet = tLDMenuDB.executeQuery(sqlbv);
		} else if (mLDMenuGrpSchema != null) {
			// 通过菜单组编码查询菜单集合
			// 构造查询语句
			// String sqlStr = "select * from LDMenu where NodeCode in ( select
			// NodeCode from LDMenuGrpToMenu ";
			// sqlStr = sqlStr +
			// "where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp where
			// MenuGrpCode ='" +
			// mLDMenuGrpSchema.getMenuGrpCode() + "') ) order by nodeorder";
			mSBql
					.append("select * from LDMenu where NodeCode in ( select NodeCode from LDMenuGrpToMenu ");
			mSBql
					.append("where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp where MenuGrpCode ='");
			mSBql.append("?MenuGrpCode?");
			mSBql.append("') ) order by nodeorder");
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(mSBql.toString());
			sqlbv1.put("MenuGrpCode", mLDMenuGrpSchema.getMenuGrpCode());

			// LDMenuSet tLDMenuSet = tLDMenuDB.executeQuery(sqlStr);
			// logger.debug(tLDMenuSet.size());
			mLDMenuSet = tLDMenuDB.executeQuery(sqlbv1);

		} else if (mLDMenuSchema != null) {
			// 通过节点编码查询
			mLDMenuSet = tLDMenuDB.query();
		} else {
			// 默认返回全部节点
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql("select * from ldmenu order by nodeorder");
			mLDMenuSet = tLDMenuDB
					.executeQuery(sqlbv2);
		}

		if (tLDMenuDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDMenuDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDMenuQueryBL";
			tError.functionName = "queryMenu";
			tError.errorMessage = "用户菜单查询失败!";
			this.mErrors.addOneError(tError);
			mLDMenuSet.clear();
			return false;
		}

		if (mLDMenuSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuBL";
			tError.functionName = "queryMenu";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			mLDMenuSet.clear();
			return false;
		}
		mResultNum = mLDMenuSet.size();
		mResult.add(mLDMenuSet);
		// logger.debug(mResult);
		return true;
	}

	/**
	 * 查询符合条件的菜单的信息 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean queryRemainMenu() {
		LDMenuSchema tLDMenuSchema = new LDMenuSchema();
		LDMenuDB tLDMenuDB = tLDMenuSchema.getDB();

		if (mLDUserSchema != null) {
			// 通过用户编码查询菜单集合
			// logger.debug("query by LDUserSchema");
			// 构造查询语句
			// String sqlStr = "select * from LDMenu where NodeCode not in (
			// select NodeCode from LDMenuGrpToMenu ";
			// sqlStr = sqlStr +
			// "where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp ";
			// sqlStr = sqlStr +
			// "where MenuGrpCode in (select MenuGrpCode from LDUserToMenuGrp
			// where UserCode = " +
			// mLDUserSchema.getUserCode() +
			// ") ) ) order by nodeorder";
			mSBql
					.append("select * from LDMenu where NodeCode not in ( select NodeCode from LDMenuGrpToMenu ");
			mSBql
					.append("where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp ");
			mSBql
					.append("where MenuGrpCode in (select MenuGrpCode from LDUserToMenuGrp where UserCode = ");
			mSBql.append("?UserCode?");
			mSBql.append(") ) ) order by nodeorder");
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(mSBql.toString());
			sqlbv3.put("UserCode", mLDUserSchema.getUserCode());

			mLDMenuSet = tLDMenuDB.executeQuery(sqlbv3);

		} else if (mLDMenuGrpSchema != null) {
			// 通过菜单组编码查询菜单集合
			// 构造查询语句
			// String sqlStr = "select * from LDMenu where NodeCode not in (
			// select NodeCode from LDMenuGrpToMenu ";
			// sqlStr = sqlStr +
			// "where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp where
			// MenuGrpCode ='" +
			// mLDMenuGrpSchema.getMenuGrpCode() + "') ) order by nodeorder";
			mSBql
					.append("select * from LDMenu where NodeCode not in ( select NodeCode from LDMenuGrpToMenu ");
			mSBql
					.append("where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp where MenuGrpCode ='");
			mSBql.append("?MenuGrpCode?");
			mSBql.append("') ) order by nodeorder");
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(mSBql.toString());
			sqlbv4.put("MenuGrpCode", mLDMenuGrpSchema.getMenuGrpCode());

			// logger.debug(sqlStr);
			// LDMenuSet tLDMenuSet = tLDMenuDB.executeQuery(sqlStr);
			mLDMenuSet = tLDMenuDB.executeQuery(sqlbv4);

		} else if (mLDMenuSchema != null) {
			// 通过节点编码查询
			String sqlStr = "select * from LDMenu where NodeCode <> mDLMenuSchema.getNodeCode order by nodeorder";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(sqlStr);
			mLDMenuSet = tLDMenuDB.executeQuery(sqlbv5);
		} else {
			// 默认返回全部节点
			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			sqlbv6.sql("select * from ldmenu order by nodeorder");
			mLDMenuSet = tLDMenuDB
					.executeQuery(sqlbv6);
		}

		if (tLDMenuDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDMenuDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDMenuQueryBL";
			tError.functionName = "queryRemainMenu";
			tError.errorMessage = "用户菜单查询失败!";
			this.mErrors.addOneError(tError);
			mLDMenuSet.clear();
			return false;
		}

		if (mLDMenuSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuBL";
			tError.functionName = "queryRemainMenu";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			mLDMenuSet.clear();
			return false;
		}
		mResultNum = mLDMenuSet.size();
		mResult.add(mLDMenuSet);
		// logger.debug(mResult);
		return true;
	}

	public static void main(String[] args) {
		// LDMenuQueryBL tLDMenuQueryBL1 = new LDMenuQueryBL();
		// LDMenuGrpSchema tSchema = new LDMenuGrpSchema();
		// tSchema.setMenuGrpCode("007");
		// VData tVData = new VData();
		// tVData.add(tSchema);
		// tLDMenuQueryBL1.submitData(tVData, "query");
		// String str = tLDMenuQueryBL1.getResultStr();
		// logger.debug(str);
		// for (int i = 0; i < tLDMenuQueryBL1.mErrors.getErrorCount(); i++)
		// {
		// CError tError = tLDMenuQueryBL1.mErrors.getError(i);
		// logger.debug(tError.errorMessage);
		// logger.debug(tError.moduleName);
		// logger.debug(tError.functionName);
		// logger.debug("----------------");
		// }
	}
}
