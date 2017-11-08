/*
 * @(#)PersonUnionBL.java	2005-04-18
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAccountDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCInsuredRelatedDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBPersonSchema;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.vschema.LCAccountSet;
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPPersonSet;
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
 * Description: 新契约复核-客户合并处理类
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
 * @CreateDate：2005-04-18
 */
public class CustomerUnionBL {
private static Logger logger = Logger.getLogger(CustomerUnionBL.class);
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
	private TransferData mTransferData = new TransferData();

	// 用来替换的客户号[new]
	private String mCustomerNo_NEW;
	// 需要被替换的客户号[old]
	private String mCustomerNo_OLD;
	
	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;
	private String mCustomerType;
	private String mPrtNo;
	private String mInOtherCont;
	private String mForceFlag;//强制关联标记 1-强制关联

	// 客户基本信息
	private LDPersonSchema mLDPersonSchema_OLD = new LDPersonSchema();
	private LDPersonSchema mLDPersonSchema_NEW = new LDPersonSchema();
	private LCAddressSet mLCAddressSet_OLD = new LCAddressSet();
	private LCAccountSet mLCAccountSet_OLD = new LCAccountSet();
	private LCAccountSet mLCAccountSet_NEW = new LCAccountSet();
	private LWMissionDB mLWMissionDB = new LWMissionDB ();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public CustomerUnionBL() {
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
		logger.debug("after getInputData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("after dealData...");

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("after prepareOutputData...");

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		logger.debug("----进入dealData()");
		if (mOperate.equals("CUSTOMER|UNION")) {
			// //判断是否向客户发问题件并回收问题件
			// if (!hasIssued()) return false;

			// 查询被替换的客户信息
			if (!getCustomerInfo()) {
				return false;
			}
			// 备份并删除被替换的客户信息
			if (!delOldCustomerInfo()) {
				return false;
			}
			// 合并客户地址信息和银行帐户信息
			if (!updNewCustomerInfo()) {
				return false;
			}
			
			/**
			 * 如果是客户关联岗调用则mActivityID中会没有值，如果是在工作流服务类中调用
			 * mActivityID的值为当前工作流活动ID
			 * */
			if(mForceFlag==null||"".equals(mForceFlag)){
				if(mActivityID==null||"".equals(mActivityID)){
					//客户关联岗，同时修改工作流中的客户号
					if (!dealMission()) {
						return false;
					}
				}else{
					
				}
			}
			String tSQL;
			ExeSQL exeSQL = new ExeSQL();

			// 查出所有与客户信息相关的表,并更新相关字段
			String strCUR_TABLE_NAME;
			String strCUR_COLUMN_NAME;
			String strCUR_CONDITION_NAME;
			//查询需要修改字段值(InsuredNo/AppntNo/CustomerNo,并关联合同号或其他信息,确定唯一一单)
			tSQL="select codename,codealias ,comcode  from ldcode1 where codetype ='customermerge' order by code";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			SSRS rSSRS =new SSRS();
			rSSRS =exeSQL.execSQL(sqlbv);
			for(int i=1;i<=rSSRS.MaxRow;i++){
				strCUR_TABLE_NAME =rSSRS.GetText(i, 1);
				strCUR_COLUMN_NAME =rSSRS.GetText(i, 2);
				strCUR_CONDITION_NAME =rSSRS.GetText(i, 3);
				if("LCACCOUNT".equals(strCUR_TABLE_NAME)){
					//账户表需要bankaccno确定唯一一条记录
					//先判断账户表中是否存在新号信息
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					sqlbv1.sql("select count(1) from "+strCUR_TABLE_NAME+" where "+strCUR_COLUMN_NAME+"='"+"?CustomerNo?"+"'");
					sqlbv1.put("CustomerNo", mCustomerNo_NEW);
					String tCount =exeSQL.getOneValue(sqlbv1);
					if("0".equals(tCount)){
						tSQL =" UPDATE "+strCUR_TABLE_NAME+" set "+strCUR_COLUMN_NAME+"='"+"?mCustomerNo_NEW?"+"'　"
						+" where "+"?COLUMN_NAME?"+"='"+"?mCustomerNo_OLD?"+"' and "+strCUR_CONDITION_NAME+" "
						+" in (select bankaccno from lccont where prtno ='"+"?prtno?"+"')";
					}
				}else if(/*"LCADDRESS".equals(strCUR_TABLE_NAME)||*/"LDPERSON".equals(strCUR_TABLE_NAME)){
					//有客户号确定唯一记录
					if("0".equals(mInOtherCont)){
						tSQL="DELETE from "+strCUR_TABLE_NAME+" where "+strCUR_COLUMN_NAME+"='"+"?mCustomerNo_OLD?"+"'";
					}
//					tSQL =" UPDATE "+strCUR_TABLE_NAME+" set "+strCUR_COLUMN_NAME+"='"+mCustomerNo_NEW+"'　"
//					+" where "+strCUR_COLUMN_NAME+"='"+mCustomerNo_OLD+"'";
				}else if("LCINSUREDRELATED".equals(strCUR_TABLE_NAME)){
					tSQL =" UPDATE "+strCUR_TABLE_NAME+" set "+strCUR_COLUMN_NAME+"='"+"?mCustomerNo_NEW?"+"'　"
					+" where "+strCUR_COLUMN_NAME+"='"+"?mCustomerNo_OLD?"+"' and polno in (select polno from "
					+" lcpol where prtno ='"+"?prtno?"+"')";
				}else{
					//通过合同号确定唯一记录
					tSQL =" UPDATE "+strCUR_TABLE_NAME+" set "+strCUR_COLUMN_NAME+"='"+"?mCustomerNo_NEW?"+"'　"
					+" where "+strCUR_COLUMN_NAME+"='"+"?mCustomerNo_OLD?"+"' and "+strCUR_CONDITION_NAME+" "
					+" ='"+"?prtno?"+"'";
				}
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tSQL);
				sqlbv2.put("mCustomerNo_NEW", mCustomerNo_NEW);
				sqlbv2.put("mCustomerNo_OLD", mCustomerNo_OLD);
				sqlbv2.put("prtno", mPrtNo);
				map.put(sqlbv2, "UPDATE");
			}
//			tSQL = " SELECT TABLE_NAME, COLUMN_NAME FROM COLS a "
//					+ " WHERE COLUMN_NAME IN ('INSUREDNO', 'CUSTOMERNO', 'APPNTNO')"
//					+ " and exists (select 'X' from user_tables where  a.TABLE_NAME = TABLE_NAME)"
//					+ " ORDER BY TABLE_NAME, COLUMN_NAME DESC";
//			SSRS rSSRS = exeSQL.execSQL(tSQL);
//			if (rSSRS != null) {
//				for (int i = 1; i <= rSSRS.getMaxRow(); i++) {
//					strCUR_TABLE_NAME = rSSRS.GetText(i, 1);
//					strCUR_COLUMN_NAME = rSSRS.GetText(i, 2);
//					if (strCUR_TABLE_NAME.equalsIgnoreCase("LBPERSON")
//							|| strCUR_TABLE_NAME.equalsIgnoreCase("LCAddress")
//							|| strCUR_TABLE_NAME.equalsIgnoreCase("LCAccount")
//							|| strCUR_TABLE_NAME.equalsIgnoreCase("LPPerson")
//							|| strCUR_TABLE_NAME.equalsIgnoreCase("LPAddress")) {
//						continue; // [客户信息备份表]不需要更新的表
//					}
//
//					tSQL = " UPDATE " + strCUR_TABLE_NAME + " SET "
//							+ strCUR_COLUMN_NAME + " = '" + mCustomerNo_NEW
//							+ "' " + " WHERE " + strCUR_COLUMN_NAME + " = '"
//							+ mCustomerNo_OLD + "' ";
//
//					// 添加更新日期、时间、操作员信息 ？？ 有无必要 ？？
//					// 暂时取消[2005-04-19]
//					map.put(tSQL, "UPDATE");
//				}
//			}
			// 修改处在连带被保人位置的客户号
			Calculator calculator = new Calculator();
			// 增加基本要素
			calculator.addBasicFactor("CustomerNo_NEW", mCustomerNo_NEW);
			calculator.addBasicFactor("CustomerNo_OLD", mCustomerNo_OLD);
			// 取计算编码
			LDCodeDB tLDCodeDB = new LDCodeDB();
			tLDCodeDB.setCodeType("khhbcalcode");
			LDCodeSet tLDCodeSet = new LDCodeSet();
			tLDCodeSet = tLDCodeDB.query();
			for (int i = 1; i <= tLDCodeSet.size(); i++) {
				calculator
						.setCalCode(tLDCodeSet.get(i).getCode().toUpperCase());
				String strSQL = calculator.calculate();
				logger.debug("strSQL=" + strSQL);
				map.put(strSQL, "UPDATE");
			}
			// ===add===zhangtao===2007-3-5=====增加并发控制，在最后提交前校验合并后的客户是否存在=======BGN=================
			String sCheckSql = "";
			if(SysConst.DBTYPE_ORACLE.equals(SysConst.DBTYPE)){
				sCheckSql = " select 1 from ldsysvar where not exists "
						+ " (select 'x' from ldperson where customerno = '"
						+ "?customerno?" + "') and rownum = 1";
			}else if(SysConst.DBTYPE_MYSQL.equals(SysConst.DBTYPE)){
				sCheckSql = " select 1 from ldsysvar where not exists "
						+ " (select 'x' from ldperson where customerno = '"
						+ "?customerno?" + "') limit 1";
			}
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(sCheckSql);
			sqlbv3.put("customerno", mCustomerNo_NEW);
			map.put(sqlbv3, "SELECT");
			// ===add===zhangtao===2007-3-5=====增加并发控制，在最后提交前校验合并后的客户是否存在=======END=================
		}

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		// 取出外部传入的 业务数据
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mCustomerNo_NEW = (String) mTransferData
				.getValueByName("CustomerNo_NEW");
		
		mPrtNo = (String) mTransferData
		.getValueByName("PrtNo");
		mForceFlag = (String) mTransferData.getValueByName("ForceFlag");
		if (mCustomerNo_NEW == null || mCustomerNo_NEW.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据[CustomerNo_NEW] 失败!";
			mErrors.addOneError(tError);
			return false;
		}
		logger.debug("== mCustomerNo_NEW ==" + mCustomerNo_NEW);

		mCustomerNo_OLD = (String) mTransferData
				.getValueByName("CustomerNo_OLD");
		if (mCustomerNo_OLD == null || mCustomerNo_OLD.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据[CustomerNo_OLD]失败!";
			mErrors.addOneError(tError);
			return false;
		}
		logger.debug("== mCustomerNo_OLD ==" + mCustomerNo_OLD);

		if (mCustomerNo_NEW.equals(mCustomerNo_OLD)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "客户号相同，不能执行合并!";
			mErrors.addOneError(tError);
			return false;
		}
		if(mForceFlag==null||"".equals(mForceFlag)){
			mMissionID=(String) mTransferData.getValueByName("MissionID");
			if (mMissionID == null || mMissionID.equals("")) {
				// @@错误处理
				CError.buildErr(this, "前台传输业务数据MissionID失败！");
				return false;
			}
			mSubMissionID=(String) mTransferData.getValueByName("SubMissionID");
			if (mSubMissionID == null || mSubMissionID.equals("")) {
				// @@错误处理
				CError.buildErr(this, "前台传输业务数据SubMissionID失败！");
				return false;
			}
			/*
			mActivityID=(String) mTransferData.getValueByName("mActivityID");
			if (mActivityID == null || mActivityID.equals("")) {
				// @@错误处理
				CError.buildErr(this, "前台传输业务数据ActivityID失败！");
				return false;
			}*/
			
			mCustomerType=(String) mTransferData.getValueByName("CustomerType");
			if (mCustomerType == null || mCustomerType.equals("")) {
				// @@错误处理
				CError.buildErr(this, "前台传输业务数据CustomerType失败！");
				return false;
			}
		}
		return true;
	}

	/**
	 * 查询被替换的客户信息
	 * 
	 * @return: boolean
	 */
	private boolean getCustomerInfo() {
		// 客户基本信息
		/** * 查询旧客户信息 */
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(mCustomerNo_OLD);
		if (!tLDPersonDB.getInfo()) {
			CError.buildErr(this, "客户号:" + mCustomerNo_OLD+"客户信息查询失败!");
			return false;
		}
		mLDPersonSchema_OLD.setSchema(tLDPersonDB.getSchema());

		/** * 查询新客户信息 */
		tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(mCustomerNo_NEW);
		if (!tLDPersonDB.getInfo()) {
			CError.buildErr(this, "客户号:" + mCustomerNo_NEW+"客户信息查询失败!");
			return false;
		}
		mLDPersonSchema_NEW.setSchema(tLDPersonDB.getSchema());

		// 客户地址信息
		LCAddressDB tLCAddressDB = new LCAddressDB();
		tLCAddressDB.setCustomerNo(mCustomerNo_OLD);
		mLCAddressSet_OLD.set(tLCAddressDB.query());
		if (tLCAddressDB.mErrors.needDealError()) {
			CError.buildErr(this, "客户号:" + mCustomerNo_NEW+"客户地址信息查询失败!");
			return false;
		}

		// 客户银行帐户信息
		LCAccountDB tLCAccountDB = new LCAccountDB();
		tLCAccountDB.setCustomerNo(mCustomerNo_OLD);
		mLCAccountSet_OLD.set(tLCAccountDB.query());
		if (tLCAccountDB.mErrors.needDealError()) {
			CError.buildErr(this, "客户号:" + mCustomerNo_NEW+"客户银行帐户信息查询失败!");
			return false;
		}

		tLCAccountDB = new LCAccountDB();
		tLCAccountDB.setCustomerNo(mCustomerNo_NEW);
		mLCAccountSet_NEW.set(tLCAccountDB.query());
		if (tLCAccountDB.mErrors.needDealError()) {
			CError.buildErr(this, "客户号:" + mCustomerNo_NEW+"客户银行帐户信息查询失败!");
			return false;
		}
		return true;
	}

	/**
	 * 备份删除被替换的客户信息
	 * 
	 * @return: boolean
	 */
	private boolean delOldCustomerInfo() {
		// 备份客户基本信息
		/*
		Reflections rf = new Reflections();
		LBPersonSchema tLBPersonSchema = new LBPersonSchema();
		rf.transFields(tLBPersonSchema, mLDPersonSchema_OLD);
		String noLimit = PubFun.getNoLimit(this.mGlobalInput.ManageCom);
		String sEdorNo = PubFun1.CreateMaxNo("EDORNO", noLimit);
		tLBPersonSchema.setEdorNo(sEdorNo);
		tLBPersonSchema.setOperator(mGlobalInput.Operator);
		tLBPersonSchema.setModifyDate(mCurrentDate);
		tLBPersonSchema.setModifyTime(mCurrentTime);
		map.put(tLBPersonSchema, "INSERT");
		*/
		// 删除客户基本信息(判断此客户是否在其他合同中也存在，如果存在，则不能删除)
		String tCheckInOtherPol =" select (case when sum(A.a) is null then 0 else sum(A.a) end) from ("
								+" select count(1) a from lcinsured where contno!='"+"?contno?"+"' and insuredno ='"+"?mCustomerNo_OLD?"+"'"
								+" union"
								+" select count(1) a from lcappnt where contno!='"+"?contno?"+"' and appntno ='"+"?mCustomerNo_OLD?"+"') A"
			;
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tCheckInOtherPol);
		sqlbv4.put("contno", mPrtNo);
		sqlbv4.put("mCustomerNo_OLD", mCustomerNo_OLD);
		ExeSQL tExeSQL =new ExeSQL();
		mInOtherCont =tExeSQL.getOneValue(sqlbv4);
		if("0".equals(mInOtherCont)){
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" DELETE ").append(" FROM  LDPERSON ").append(
					" WHERE  CUSTOMERNO = '" + "?CUSTOMERNO?" + "'");
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(sbSQL.toString());
			sqlbv5.put("CUSTOMERNO", mCustomerNo_OLD);
			map.put(sqlbv5, "DELETE");
			
			Reflections rf = new Reflections();
			LBPersonSchema tLBPersonSchema = new LBPersonSchema();
			rf.transFields(tLBPersonSchema, mLDPersonSchema_OLD);
			String noLimit = PubFun.getNoLimit(this.mGlobalInput.ManageCom);
			String sEdorNo = PubFun1.CreateMaxNo("EDORNO", noLimit);
			tLBPersonSchema.setEdorNo(sEdorNo);
			tLBPersonSchema.setOperator(mGlobalInput.Operator);
			tLBPersonSchema.setModifyDate(mCurrentDate);
			tLBPersonSchema.setModifyTime(mCurrentTime);
			map.put(tLBPersonSchema, "INSERT");
		}

		return true;
	}

	/**
	 * 合并客户地址信息和银行帐户信息
	 * 
	 * @return: boolean
	 */
	private boolean updNewCustomerInfo() {
		// 取得该客户最大地址号
		int iAdsNO = getMaxAddressNo_NEW();
		if (iAdsNO == -1) {
			return false;
		}
		int iOldAdsNo = 1;
		for (int i = 1; i <= mLCAddressSet_OLD.size(); i++) {
			// 更新客户号和地址号
			try {
				iOldAdsNo = mLCAddressSet_OLD.get(i).getAddressNo();
			} catch (Exception e) {
				CError tError = new CError();
				tError.moduleName = "CustomerUnionBL";
				tError.functionName = "updNewCustomerInfo";
				tError.errorMessage = "客户地址号码格式错误!" + "客户号：" + mCustomerNo_NEW
						+ "地址号码：" + mLCAddressSet_OLD.get(i).getAddressNo()
						+ "错误信息：" + e.toString();
				mErrors.addOneError(tError);
			}
			// mLCAddressSet_OLD.get(i).setCustomerNo(mCustomerNo_NEW);
			// mLCAddressSet_OLD.get(i).setAddressNo(String.valueOf(iOldAdsNo +
			// iAdsNO));
			// mLCAddressSet_OLD.get(i).setOperator(mGlobalInput.Operator);
			// mLCAddressSet_OLD.get(i).setModifyDate(mCurrentDate);
			// mLCAddressSet_OLD.get(i).setModifyTime(mCurrentTime);
//			map.put("update lcaddress set customerno='" + mCustomerNo_NEW
//					+ "',AddressNo=" + String.valueOf(iOldAdsNo + iAdsNO)
//					+ ",Operator='" + mGlobalInput.Operator + "',ModifyDate='"
//					+ mCurrentDate + "',ModifyTime='" + mCurrentTime
//					+ "' where customerno='" + mCustomerNo_OLD
//					+ "' and AddressNo=" + iOldAdsNo, "UPDATE");
			
		}
		//新插入一条地址信息
		LCAddressDB tLCAddressDB =new LCAddressDB();
		//LCAddressSet tLCAddressSet =new LCAddressSet();
		LCAddressSchema tLCAddressSchema =new LCAddressSchema();
		tLCAddressDB.setCustomerNo(mCustomerNo_OLD);
		tLCAddressDB.setAddressNo(iOldAdsNo);
		if(!tLCAddressDB.getInfo()){
			CError.buildErr(this, "查询客户："+mCustomerNo_OLD+";客户编码："+iOldAdsNo+"错误！");
			return false;
		}else{
			tLCAddressSchema=tLCAddressDB.getSchema();
			tLCAddressSchema.setCustomerNo(mCustomerNo_NEW);
			tLCAddressSchema.setAddressNo(String.valueOf(iOldAdsNo + iAdsNO));
			tLCAddressSchema.setOperator(mGlobalInput.Operator);
			tLCAddressSchema.setMakeDate(mCurrentDate);
			tLCAddressSchema.setMakeTime(mCurrentTime);
			tLCAddressSchema.setModifyDate(mCurrentDate);
			tLCAddressSchema.setModifyTime(mCurrentTime);
			//tLCAddressSet.add(tLCAddressSchema);
			//map.put(tLCAddressSet, "INSERT");
		}

		// map.put(mLCAddressSet_OLD, "UPDATE");

		// 更新投保人、被保人、连带被保人信息中引用的[客户地址号码]

		if (!updAddressNoRelaTable(iAdsNO,tLCAddressSchema)) {
			return false;
		}

		// 合并客户银行帐户信息
		LCAccountSet nUpdLCAccountSet = new LCAccountSet();
		LCAccountSet nDelLCAccountSet = new LCAccountSet();
		LCAccountSchema nLCAccountSchema = new LCAccountSchema();

		if (mLCAccountSet_NEW != null && mLCAccountSet_NEW.size() > 0) {
			// 新客户已经有银行帐户信息
			for (int j = 1; j <= mLCAccountSet_OLD.size(); j++) {
				nLCAccountSchema.setSchema(mLCAccountSet_OLD.get(j));
				if (exsitAccount(nLCAccountSchema)) {
					// 删除重复帐户
						nDelLCAccountSet.add(nLCAccountSchema);
				} else {
					// 更新客户号
					nLCAccountSchema.setCustomerNo(mCustomerNo_NEW);
					nLCAccountSchema.setOperator(mGlobalInput.Operator);
					nLCAccountSchema.setModifyDate(mCurrentDate);
					nLCAccountSchema.setModifyTime(mCurrentTime);
					nUpdLCAccountSet.add(nLCAccountSchema);
				}
			}
			ExeSQL tExeSQL =new ExeSQL();
			String tCheckAccount =" select count(1) from LCAccount where customerno ='"+"?customerno?"+"' "
			+" and bankaccno in (select bankaccno from lccont where prtno not in"
			+" ('"+"?prtno?"+"') and appntno='"+"?customerno?"+"')";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tCheckAccount);
			sqlbv6.put("customerno", mCustomerNo_OLD);
			sqlbv6.put("prtno", mPrtNo);
		    String tCount =tExeSQL.getOneValue(sqlbv6);
		    if("0".equals(tCount)){
		    	map.put(nDelLCAccountSet, "DELETE");
		    }
			map.put(nUpdLCAccountSet, "UPDATE");
		} else {
			// 新客户尚未创建银行帐户信息
			for (int j = 1; j <= mLCAccountSet_OLD.size(); j++) {
				nLCAccountSchema.setSchema(mLCAccountSet_OLD.get(j));
				// 更新客户号
				nLCAccountSchema.setCustomerNo(mCustomerNo_NEW);
				nLCAccountSchema.setOperator(mGlobalInput.Operator);
				nLCAccountSchema.setModifyDate(mCurrentDate);
				nLCAccountSchema.setModifyTime(mCurrentTime);
				nUpdLCAccountSet.add(nLCAccountSchema);
			}
			map.put(nUpdLCAccountSet, "UPDATE");
		}
		//保全不处理
		/*
		// 保全p表客户处理，过滤错误操作引起的多条数据问题
		LPPersonDB tLPPersonDB = new LPPersonDB();
		LPPersonSet tLPPersonSet_OLD = new LPPersonSet();
		LPPersonSet tLPPersonSet_NEWExists = new LPPersonSet();
		LPPersonSet tLPPersonSet_new = new LPPersonSet();
		tLPPersonDB.setCustomerNo(mCustomerNo_OLD);
		tLPPersonSet_OLD = tLPPersonDB.query();
		tLPPersonDB.setCustomerNo(mCustomerNo_NEW);
		tLPPersonSet_NEWExists = tLPPersonDB.query();
		for (int k = 1; k <= tLPPersonSet_OLD.size(); k++) {
			LPPersonSchema tLPPersonSchema = new LPPersonDB();
			tLPPersonSchema.setSchema(tLPPersonSet_OLD.get(k));
			tLPPersonSchema.setCustomerNo(mCustomerNo_NEW);
			tLPPersonSet_new.add(tLPPersonSchema);
		}
		String tCheckLDPerson=" select count(1) from ";
		if("0".equals(mInOtherCont)){
			map.put(tLPPersonSet_OLD, "DELETE");// 对需要被替换的客户进行删除
		}
		map.put(tLPPersonSet_new, "DELETE&INSERT");// 合并后的客户进行保存
		map.put(tLPPersonSet_NEWExists, "DELETE&INSERT");// 若合并后的客户已存在，则进行修正。

		LPAddressDB tLPAddressDB = new LPAddressDB();
		LPAddressSet tLPAddressSet_OLD = new LPAddressSet();
		LPAddressSet tLPAddressSet_NEWExists = new LPAddressSet();
		LPAddressSet tLPAddressSet_new = new LPAddressSet();
		tLPAddressDB.setCustomerNo(mCustomerNo_OLD);
		tLPAddressSet_OLD = tLPAddressDB.query();
		tLPAddressDB.setCustomerNo(mCustomerNo_NEW);
		tLPAddressSet_NEWExists = tLPAddressDB.query();
		for (int m = 1; m <= tLPAddressSet_OLD.size(); m++) {
			LPAddressSchema tLPAddressSchema = new LPAddressSchema();
			tLPAddressSchema.setSchema(tLPAddressSet_OLD.get(m));
			tLPAddressSchema.setCustomerNo(mCustomerNo_NEW);
			tLPAddressSet_new.add(tLPAddressSchema);
		}
		String tCheckInOtherPol =" select nvl(sum(A.a),0) from ("
			+" select count(1) a from lcinsured where contno!='"+mPrtNo+"' and insuredno ='"+mCustomerNo_OLD+"'"
			+" union"
			+" select count(1) a from lcappnt where contno!='"+mPrtNo+"' and appntno ='"+mCustomerNo_OLD+"') A"
			;
		ExeSQL tExeSQL =new ExeSQL();
		String tCount =tExeSQL.getOneValue(tCheckInOtherPol);
		if("0".equals(tCount)){
			map.put(tLPAddressSet_OLD, "DELETE");// 对需要被替换的客户进行删除
		}
		map.put(tLPAddressSet_new, "DELETE&INSERT");// 合并后的客户进行保存
		map.put(tLPAddressSet_NEWExists, "DELETE&INSERT");// 若合并后的客户已存在，则进行修正。
*/
		return true;
	}

	private boolean dealMission(){
		mLWMissionDB.setMissionID(mMissionID);
		mLWMissionDB.setSubMissionID(mSubMissionID);
		mLWMissionDB.setActivityID("0000001404");
		if(!mLWMissionDB.getInfo()){
			CError.buildErr(this, "查询工作流失败");
			return false;
		}
		if(mLWMissionDB.getMissionProp7().equals(mLWMissionDB.getMissionProp8())){
			mLWMissionDB.setMissionProp7(mCustomerNo_NEW);
			mLWMissionDB.setMissionProp8(mCustomerNo_NEW);
		}
		if(mCustomerType.equals("A")){
			mLWMissionDB.setMissionProp7(mCustomerNo_NEW);
		}else if(mCustomerType.equals("I")){
			mLWMissionDB.setMissionProp8(mCustomerNo_NEW);
		}else{
			CError.buildErr(this, "前台传入客户类型错误！");
			return false;
		}
		map.put(mLWMissionDB.getSchema(), "UPDATE");
		return true;
	}
	
	/**
	 * 取得该客户最大地址号
	 * 
	 * @return: int
	 */
	private int getMaxAddressNo_NEW() {
		int iAdsNO = 0;
		String strAdsNO = "";
		String tSQL = new String();
		ExeSQL exeSQL = new ExeSQL();
		// 查询该客户最大地址号
		tSQL = "select max(addressno)+1 from lcaddress where customerno='"
				+ "?customerno?" + "'";
		try {
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tSQL);
			sqlbv7.put("customerno", mCustomerNo_NEW);
			SSRS rSSRS = exeSQL.execSQL(sqlbv7);
			if (rSSRS != null) {
				strAdsNO = rSSRS.GetText(1, 1);
				if (strAdsNO == null || strAdsNO.equals("")
						|| strAdsNO.equals("null")) {
					iAdsNO = 1;
				} else {
					iAdsNO = Integer.parseInt(strAdsNO);
				}
			} else {
				iAdsNO = 0;
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "getMaxAddressNo_NEW";
			tError.errorMessage = "客户最大地址号查询失败!" + "客户号:" + mCustomerNo_NEW
					+ "错误信息：" + e.toString();
			mErrors.addOneError(tError);
			return -1;
		}
		return iAdsNO;
	}

	/**
	 * 判断帐户是否重复
	 * 
	 * @return: boolean
	 */
	private boolean exsitAccount(LCAccountSchema nLCAccountSchema) {
		for (int i = 1; i < mLCAccountSet_OLD.size(); i++) {
			if (nLCAccountSchema.getBankCode().equals(
					mLCAccountSet_OLD.get(i).getBankCode())
					&& nLCAccountSchema.getBankAccNo().equals(
							mLCAccountSet_OLD.get(i).getBankAccNo())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 更新投保人、被保人、连带被保人信息中引用的[客户地址号码]
	 * 
	 * @return: boolean
	 */
	private boolean updAddressNoRelaTable(int iAdsNO,LCAddressSchema tempLCAddressSchema ) {
		int iOldAdsNo = 1;
		String strOldAdsNo;
		// 投保人
		LCAppntSet tLCAppntSet = new LCAppntSet();
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setAppntNo(mCustomerNo_OLD);
		tLCAppntDB.setPrtNo(mPrtNo);  //处理当前保单
		tLCAppntSet.set(tLCAppntDB.query());
		if (tLCAppntDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCAppntDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "updAddressNoRelaTable";
			tError.errorMessage = "投保人信息查询失败!" + "客户号:" + mCustomerNo_OLD;
			mErrors.addOneError(tError);
			return false;
		}
		LCAddressSet tAppntLCAddressSet = new LCAddressSet();
		if (tLCAppntSet != null && tLCAppntSet.size() > 0) {
			for (int i = 1; i <= tLCAppntSet.size(); i++) {
				strOldAdsNo = tLCAppntSet.get(i).getAddressNo();
				if (strOldAdsNo == null || "".equals(strOldAdsNo)) {
					continue;
				}
				
				LCAddressSchema tCurrLCAddressSchema = new LCAddressSchema();
				tCurrLCAddressSchema.setSchema(tempLCAddressSchema);
				iOldAdsNo = Integer.parseInt(strOldAdsNo);
				tLCAppntSet.get(i).setAddressNo(
						String.valueOf(iOldAdsNo + iAdsNO));
				tLCAppntSet.get(i).setOperator(mGlobalInput.Operator);
				tLCAppntSet.get(i).setModifyDate(mCurrentDate);
				tLCAppntSet.get(i).setModifyTime(mCurrentTime);
				
				tCurrLCAddressSchema.setAddressNo(String.valueOf(iOldAdsNo + iAdsNO));
				tAppntLCAddressSet.add(tCurrLCAddressSchema);
				//
			}
			map.put(tLCAppntSet, "UPDATE");
			if(tAppntLCAddressSet.size()>0)
			{
				map.put(tAppntLCAddressSet,"DELETE&INSERT");
			}
		}

		// 被保人
		LCAddressSet tInseuredLCAddressSet = new LCAddressSet();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setInsuredNo(mCustomerNo_OLD);
		tLCInsuredDB.setPrtNo(mPrtNo); //处理当前保单
		tLCInsuredSet.set(tLCInsuredDB.query());
		if (tLCInsuredDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCInsuredDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "updAddressNoRelaTable";
			tError.errorMessage = "被保人信息查询失败!" + "客户号:" + mCustomerNo_OLD;
			mErrors.addOneError(tError);
			return false;
		}
		if (tLCInsuredSet != null && tLCInsuredSet.size() > 0) {
			for (int i = 1; i <= tLCInsuredSet.size(); i++) {
				strOldAdsNo = tLCInsuredSet.get(i).getAddressNo();
				logger.debug("strOldAdsNo:" + strOldAdsNo);

				if (strOldAdsNo == null || "".equals(strOldAdsNo)) {
					continue;
				}
				LCAddressSchema tCurrLCAddressSchema = new LCAddressSchema();
				tCurrLCAddressSchema.setSchema(tempLCAddressSchema);

				iOldAdsNo = Integer.parseInt(tLCInsuredSet.get(i)
						.getAddressNo());
				tLCInsuredSet.get(i).setAddressNo(
						String.valueOf(iOldAdsNo + iAdsNO));
				tLCInsuredSet.get(i).setOperator(mGlobalInput.Operator);
				tLCInsuredSet.get(i).setModifyDate(mCurrentDate);
				tLCInsuredSet.get(i).setModifyTime(mCurrentTime);
				tCurrLCAddressSchema.setAddressNo(String.valueOf(iOldAdsNo + iAdsNO));
				tInseuredLCAddressSet.add(tCurrLCAddressSchema);

			}
			map.put(tLCInsuredSet, "UPDATE");
			if(tInseuredLCAddressSet.size()>0)
			{
				map.put(tInseuredLCAddressSet,"DELETE&INSERT");
			}
		}

		// 连带被保人
		LCInsuredRelatedSet tLCRelatedSet = new LCInsuredRelatedSet();
		LCInsuredRelatedDB tLCRelatedDB = new LCInsuredRelatedDB();
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("select * from lcinsuredrelated where customerno ='"+"?customerno?"+"' "
                +"and polno in (select polno from lcpol where prtno ='"+"?prtno?"+"')");
		sqlbv8.put("customerno",mCustomerNo_OLD);
		sqlbv8.put("prtno",mPrtNo);
		tLCRelatedSet=tLCRelatedDB.executeQuery(sqlbv8);
		if (tLCRelatedDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCRelatedDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "updAddressNoRelaTable";
			tError.errorMessage = "连带被保人信息查询失败!" + "客户号:" + mCustomerNo_OLD;
			mErrors.addOneError(tError);
			return false;
		}
		if (tLCRelatedSet != null && tLCRelatedSet.size() > 0) {
			for (int i = 1; i <= tLCRelatedSet.size(); i++) {
				strOldAdsNo = tLCRelatedSet.get(i).getAddressNo();
				if (strOldAdsNo == null || "".equals(strOldAdsNo)) {
					continue;
				}
				iOldAdsNo = Integer.parseInt(tLCRelatedSet.get(i)
						.getAddressNo());
				tLCRelatedSet.get(i).setAddressNo(
						String.valueOf(iOldAdsNo + iAdsNO));
				tLCRelatedSet.get(i).setOperator(mGlobalInput.Operator);
				tLCRelatedSet.get(i).setModifyDate(mCurrentDate);
				tLCRelatedSet.get(i).setModifyTime(mCurrentTime);
			}
			map.put(tLCRelatedSet, "UPDATE");
		}
		return true;
	}

	/**
	 * 判断是否向客户发问题件并回收问题件
	 * 
	 * @return: boolean
	 */
	private boolean hasIssued() {
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		tLCIssuePolDB.setIssueType("22"); // ！！ 待定 ！！
		tLCIssuePolDB.setStandbyFlag1(mCustomerNo_NEW);
		tLCIssuePolDB.setStandbyFlag2(mCustomerNo_OLD);
		tLCIssuePolSet.set(tLCIssuePolDB.query());
		if (tLCIssuePolDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCIssuePolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "hasLCIssuePol";
			tError.errorMessage = "问题件信息查询失败!";
			mErrors.addOneError(tError);
			return false;
		}

		if (tLCIssuePolSet == null || tLCIssuePolSet.size() == 0) {
			mErrors.copyAllErrors(tLCIssuePolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "hasLCIssuePol";
			tError.errorMessage = "尚未征求客户意见，不能合并客户，请先发出问题件!";
			mErrors.addOneError(tError);
			return false;
		}
		String strState;
		for (int i = 1; i <= tLCIssuePolSet.size(); i++) {
			strState = tLCIssuePolSet.get(i).getState();
			if (strState == null || !strState.equals("2")) {
				// 问题件尚未回收
				mErrors.copyAllErrors(tLCIssuePolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "CustomerUnionBL";
				tError.functionName = "hasLCIssuePol";
				tError.errorMessage = "客户尚未确认，不能合并客户，请先回收问题件!";
				mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @retun: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

}
