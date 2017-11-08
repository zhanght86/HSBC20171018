package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统相同客户查询部分
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
 * @version 1.0
 */
public class PersonChkBL {
private static Logger logger = Logger.getLogger(PersonChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	private String mManageCom;
	private String mpassflag; // 通过标记
	private String mInsuredNo = "";
	private String mflag = ""; // A 投保人 I b被保人
	private String mLoadFlag = "";

	/** 业务处理相关变量 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private String mContNo = "";
	private String mOldPolNo = "";

	/** 投保人表 */
	private LCAppntSet mLCAppntSet = new LCAppntSet();
	private LCAppntSet mAllLCAppntSet = new LCAppntSet();

	/** 被保险人表 */
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet();
	private LCInsuredSet mAllLCInsuredSet = new LCInsuredSet();

	/** 客户信息表 */
	private LDPersonSet mLDPersonSet = new LDPersonSet();
	private LDPersonSet mAllLDPersonSet = new LDPersonSet();

	public PersonChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功
		int j = 0; // 符合条件数据个数

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		// GlobalInput tGlobalInput = new GlobalInput();
		// this.mOperate = tGlobalInput.;

		logger.debug("---2---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---PersonChkBL getInputData---");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		} else {
			flag = 1;
		}
		logger.debug("---PersonChkBL checkData---");

		if (flag == 0) {
			CError tError = new CError();
			tError.moduleName = "PersonChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "校验失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---PersonChkBL dealData---");
		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---PersonChkBL prepareOutputData---");
		// 数据提交
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		if (dealOnePol() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {
		// 健康信息
		if (mflag.equals("A")) {
			if (prepareAppnt() == false) {
				return false;
			}
		}
		if (mflag.equals("I")) {
			if (prepareInsured() == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperate = tGlobalInput.Operator;
		mManageCom = tGlobalInput.ManageCom;

		mLCContSchema.setSchema((LCContSchema) cInputData
				.getObjectByObjectName("LCContSchema", 0));

		int flag = 0; // 怕判断是不是所有保单都失败
		int j = 0; // 符合条件保单个数

		if (mLCContSchema != null) {
			mContNo = mLCContSchema.getContNo();
			mInsuredNo = mLCContSchema.getInsuredNo();
			mflag = mLCContSchema.getAppFlag();
			mLoadFlag = mLCContSchema.getRemark();
			logger.debug("mLoadFlag:" + mLoadFlag);
			logger.debug("Contno:" + mContNo);
			logger.debug("InsuredNo:" + mInsuredNo);
			logger.debug("flag:" + mflag);
		} else {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PersonChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备客户信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareAppnt() {
		/**
		 * 取保单信息
		 */
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (tLCContDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PersonChkBL";
			tError.functionName = "prepareAppnt";
			tError.errorMessage = "没有" + mContNo + "保单!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema = tLCContDB.getSchema();

		String tAppntNo = tLCContSchema.getAppntNo();

		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(tLCContSchema.getContNo());
		tLCAppntDB.setAppntNo(tAppntNo);

		if (tLCAppntDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PersonChkBL";
			tError.functionName = "prepareAppnt";
			tError.errorMessage = "没有" + tAppntNo + "客户!";
			this.mErrors.addOneError(tError);
			return false;
		}

		tLCAppntSchema = tLCAppntDB.getSchema();
		mLCAppntSet.add(tLCAppntSchema);

		// LCAppntDB t2LCAppntDB = new LCAppntDB();
		// LCAppntSet tLCAppntSet = new LCAppntSet();
		String tsql = "";
		// 注释以下分支，问题件修改环节投被保人校验也从ldperson中查询相似客户。---yeshu,20071225
		/*
		 * if (mLoadFlag.equals("3")) { tsql = " select standbyflag1 from
		 * lcissuepol where 1=1 " + " and issuetype = '99' and contno = '" +
		 * tLCContSchema.getContNo() + "'" + " and operatepos = 'A'" + " and
		 * serialno in (select max(serialno) from lcissuepol " + " where
		 * issuetype = '99' and operatepos = 'A' and contno = '" +
		 * tLCContSchema.getContNo() + "')"; } else {
		 */
		tsql = "select distinct customerno from LDPerson where name = '"
				+ "?name?" + "'" + " and Birthday = '"
				+ "?Birthday?" + "'" + " and Sex = '"
				+ "?Sex?" + "' " + " and CustomerNo <> '"
				+ "?CustomerNo?" + "' "
				+ " union select distinct customerno from ldperson where 1=1 "
				+ " and IDType = '" + "?IDType?" + "'"
				+ " and IDNo = '" + "?IDNo?" + "'"
				+ " and IDType is not null" + " and IDNo is not null"
				+ " and CustomerNo <> '" + "?CustomerNo?" + "' ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tsql);
		sqlbv.put("name", tLCAppntDB.getAppntName());
		sqlbv.put("Birthday", tLCAppntDB.getAppntBirthday());
		sqlbv.put("Sex", tLCAppntDB.getAppntSex());
		sqlbv.put("CustomerNo", tAppntNo);
		sqlbv.put("IDType", tLCAppntDB.getIDType());
		sqlbv.put("IDNo", tLCAppntDB.getIDNo());
		// }
		// t2LCAppntDB.setName(tLCAppntDB.getName());
		// t2LCAppntDB.setBirthday(tLCAppntDB.getBirthday());
		// t2LCAppntDB.setSex(tLCAppntDB.getSex());

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		logger.debug(tsql);
		if (tSSRS.MaxRow > 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				LDPersonDB tLDPersonDB = new LDPersonDB();
				tLDPersonDB.setCustomerNo(tSSRS.GetText(1, i));

				if (tLDPersonDB.getInfo() == false) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PersonChkBL";
					tError.functionName = "prepareAppnt";
					tError.errorMessage = "没有" + tSSRS.GetText(1, i) + "客户信息!";
					this.mErrors.addOneError(tError);
					// return false;
				} else {
					LDPersonSchema tLDPersonSchema = new LDPersonSchema();
					tLDPersonSchema = tLDPersonDB.getSchema();
					mLDPersonSet.add(tLDPersonSchema);
				}
			}
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PersonChkBL";
			tError.functionName = "prepareAppnt";
			tError.errorMessage = "没有相同客户信息!";
			this.mErrors.addOneError(tError);
			// return false;
		}

		return true;
	}

	/**
	 * 准备分红信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareInsured() {
		/**
		 * 取保单信息
		 */
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (tLCContDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PersonChkBL";
			tError.functionName = "prepareInsured";
			tError.errorMessage = "没有" + mContNo + "保单!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// LCContSchema tLCContSchema = new LCContSchema();
		// tLCContSchema = tLCContDB.getSchema();

		// String tInsuredNo = tLCContSchema.getInsuredNo();

		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mContNo);
		tLCInsuredDB.setInsuredNo(mInsuredNo);
		if (tLCInsuredDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PersonChkBL";
			tError.functionName = "prepareInsured";
			tError.errorMessage = "没有" + mInsuredNo + "客户!";
			this.mErrors.addOneError(tError);
			return false;
		}

		tLCInsuredSchema = tLCInsuredDB.getSchema();
		mLCInsuredSet.add(tLCInsuredSchema);

		String tsql = "";
		// 注释以下分支，问题件修改环节投被保人校验也从ldperson中查询相似客户。---yeshu,20071225
		/*
		 * if (mLoadFlag.equals("3")) { tsql = " select standbyflag1 from
		 * lcissuepol where 1=1 " + " and issuetype = '99' and contno = '" +
		 * tLCContDB.getContNo() + "'" + " and operatepos = 'I'" + " and
		 * serialno in (select max(serialno) from lcissuepol " + " where
		 * issuetype = '99' and operatepos = 'I' and contno = '" +
		 * tLCContDB.getContNo() + "')"; } else {
		 */
		tsql = "select distinct customerno from LDPerson where name = '"
				+ "?name?" + "' and Birthday = '"
				+ "?Birthday?" + "' and Sex = '"
				+ "?Sex?" + "' and CustomerNo <> '" + "?CustomerNo?"
				+ "'"
				+ " union select distinct customerno from ldperson where 1=1 "
				+ " and IDType = '" + "?IDType?" + "'"
				+ " and IDNo = '" + "?IDNo?" + "'"
				+ " and IDType is not null" + " and IDNo is not null"
				+ " and CustomerNo <> '" + "?CustomerNo?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tsql);
		sqlbv1.put("name", tLCInsuredDB.getName());
		sqlbv1.put("Birthday", tLCInsuredDB.getBirthday());
		sqlbv1.put("Sex", tLCInsuredDB.getSex());
		sqlbv1.put("CustomerNo", mInsuredNo);
		sqlbv1.put("IDType", tLCInsuredDB.getIDType());
		sqlbv1.put("IDNo", tLCInsuredDB.getIDNo());
		// }

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv1);

		if (tSSRS.MaxRow > 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				LDPersonDB tLDPersonDB = new LDPersonDB();
				tLDPersonDB.setCustomerNo(tSSRS.GetText(1, i));

				if (tLDPersonDB.getInfo() == false) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PersonChkBL";
					tError.functionName = "prepareInsured";
					tError.errorMessage = "没有" + tSSRS.GetText(1, i) + "客户信息!";
					this.mErrors.addOneError(tError);
					// return false;
				} else {
					LDPersonSchema tLDPersonSchema = new LDPersonSchema();
					tLDPersonSchema = tLDPersonDB.getSchema();
					mLDPersonSet.add(tLDPersonSchema);
				}
			}
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PersonChkBL";
			tError.functionName = "prepareInsured";
			tError.errorMessage = "没有相同客户信息!";
			this.mErrors.addOneError(tError);
			// return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		int j = 1;
		LDPersonSet tLDPersonSet = new LDPersonSet();
		tLDPersonSet.set(mLDPersonSet);
		mAllLDPersonSet.add(tLDPersonSet);

		mInputData.clear();
		mInputData.add(mAllLDPersonSet);

		if (mflag.equals("A")) {
			LCAppntSet tLCAppntSet = new LCAppntSet();
			tLCAppntSet.set(mLCAppntSet);
			mAllLCAppntSet.add(tLCAppntSet);

			mInputData.add(mAllLCAppntSet);
		}

		if (mflag.equals("I")) {
			LCInsuredSet tLCInsuredSet = new LCInsuredSet();
			tLCInsuredSet.set(mLCInsuredSet);
			mAllLCInsuredSet.add(tLCInsuredSet);

			mInputData.add(mAllLCInsuredSet);
		}

		logger.debug("size1:" + mInputData.size());
	}

	public VData getResult() {
		return mInputData;
	}
}
