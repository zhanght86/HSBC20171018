package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDHealthDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDHealthSchema;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.vschema.LCPENoticeItemSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDHealthSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统体检资料查询部分
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
public class UWAutoHealthQueryBL {
private static Logger logger = Logger.getLogger(UWAutoHealthQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	private String mPolType = ""; // 保单类型
	private String mInsuredNo = "";

	/** 业务处理相关变量 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private String mPolNo = "";
	private String mContNo = "";
	private String mOldPolNo = "";

	/** 体检资料项目表 */
	private LCPENoticeItemSet mLCPENoticeItemSet = new LCPENoticeItemSet();
	private LCPENoticeItemSet mmLCPENoticeItemSet = new LCPENoticeItemSet();
	private LCPENoticeItemSet mAllLCPENoticeItemSet = new LCPENoticeItemSet();

	/** 体检资料描述表* */
	private LDHealthSet mLDHealthSet = new LDHealthSet();
	private LDHealthSet mAllLDHealthSet = new LDHealthSet();

	/** 计算公式表* */
	private LMUWSchema mLMUWSchema = new LMUWSchema();

	// private LMUWDBSet mLMUWDBSet = new LMUWDBSet();
	private LMUWSet mLMUWSet = new LMUWSet();

	private CalBase mCalBase = new CalBase();

	public UWAutoHealthQueryBL() {
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

		logger.debug("---1---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("----UWAutoHealthQueryBL getInputData----");

		mOldPolNo = mLCPolSchema.getProposalNo();
		mPolNo = mLCPolSchema.getProposalNo();

		logger.debug("----UWAutoHealthQueryBL checkData----");
		// 数据操作业务处理
		if (!dealData(mLCPolSchema)) {

			// continue;
			return false;
		}

		logger.debug("----UWAutoHealthQueryBL dealData----");
		// 准备给后台的数据
		prepareOutputData();

		logger.debug("----UWAutoHealthQueryBL prepareOutputData----");
		// 数据提交
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(LCPolSchema tLCPolSchema) {
		logger.debug("---dealData---");
		if (dealOnePol() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {
		logger.debug("---dealOnePol Begin---");
		// 健康信息
		if (prepareHealth() == false) {
			return false;
		}
		logger.debug("---dealOnePol End---");
		LDHealthSet tLDHealthSet = new LDHealthSet();
		tLDHealthSet.set(mLDHealthSet);
		mAllLDHealthSet.add(tLDHealthSet);

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

		mLCPolSchema.setSchema((LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0));

		int flag = 0; // 怕判断是不是所有保单都失败

		LCContDB tLCContDB = new LCContDB();

		// 取被保人客户号
		mInsuredNo = mLCPolSchema.getInsuredNo();
		mContNo = mLCPolSchema.getContNo();

		LCContSchema tLCContSchema = new LCContSchema();

		tLCContDB.setContNo(mLCPolSchema.getContNo());
		logger.debug("--getInputData--ContNo--" + mContNo);
		if (!tLCContDB.getInfo()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCContDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthQueryBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "投保单查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			flag = 1;
		}

		tLCContSchema = tLCContDB.getSchema();
		// 判断保单类别
		if (tLCContSchema.getContType().equals("1")) {
			mPolType = "P";
		} else {
			mPolType = "G";
		}

		if (flag == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 准备体检资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareHealth() {
		logger.debug("---prepareHealth  Begin---");
		double tMoney = 0;

		// 取被保人性别，年龄
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();

		tLCInsuredDB.setContNo(mContNo);
		tLCInsuredDB.setInsuredNo(mInsuredNo);

		if (!tLCInsuredDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "取被保人姓名失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		String tSex = tLCInsuredDB.getSex();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mContNo);
		tLCPolDB.setInsuredNo(mInsuredNo);
		LCPolSet tLCPolSet = tLCPolDB.query();
		if (tLCPolSet == null || tLCPolSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "取被保人年龄失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		int tAge = tLCPolSet.get(1).getInsuredAppAge();
		// tAge = 55;
		// 取被保人体检额度
		logger.debug("---prepareHealth  End---");
		logger.debug("---mPolType:" + mPolType.toString());

		// 计算被保人体检额度
		String tsql1 = "";
		tsql1 = "select HEALTHYAMNT('" + mInsuredNo + "','" + mContNo
				+ "') from dual where 1=1";
//		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
//		 	tsql1 = "select HEALTHYAMNT('" + mInsuredNo + "','" + mContNo + "') from dual where 1=1";
//	 	}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
//	 		tsql1 = " { call healthyamnt2( ?#@d#?,'"  + mInsuredNo + "','" + mContNo + "') }";
//	 	}
		String tReSult = new String();
		ExeSQL tExeSQL = new ExeSQL();
		tReSult = tExeSQL.getOneValue(tsql1);
		if (tExeSQL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "执行SQL语句：" + tsql1 + "失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		tMoney = tMoney + Double.parseDouble(tReSult);

		// 团体
		if (mPolType.equals("G")) {
			tMoney = GetMoney();
		}

		String tsql2 = "";

		if (tSex.equals("0")) {
			tsql2 = "select * from LDHealth where 1 = 1" + " and StartAge <= "
					+ tAge + " and EndAge >= " + tAge + " and StartMoney <= "
					+ tMoney + " and EndMoney >= " + tMoney + " and sex = '0'";
		} else {
			tsql2 = "select * from LDHealth where 1 = 1" + " and StartAge <= "
					+ tAge + " and EndAge >= " + tAge + " and StartMoney <= "
					+ tMoney + " and EndMoney >= " + tMoney
					+ " and sex in ('0','1')";

		}
		logger.debug(tsql2);

		LDHealthSchema tLDHealthSchema = new LDHealthSchema();
		LDHealthSet tLDHealthSet = new LDHealthSet();
		LDHealthDB tLDHealthDB = new LDHealthDB();

		mLDHealthSet.clear();
		mLDHealthSet = tLDHealthDB.executeQuery(tsql2);

		return true;
	}

	/**
	 * 取团体体检保额
	 */
	private double GetMoney() {
		double tResult = 0;
		double tMoney = 0;
		int tnum = 0;

		// 取人数
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();

		tLCPolDB.setGrpPolNo(mLCPolSchema.getGrpPolNo());
		tLCPolSet = tLCPolDB.query();
		tnum = tLCPolSet.size();

		// 取保额
		String tsql1 = "";
		tsql1 = "select sum(amnt) from lcpol where grppolno = '"
				+ mLCPolSchema.getGrpPolNo().trim() + "'";

		ExeSQL tExeSQL = new ExeSQL();
		String tSMoney = tExeSQL.getOneValue(tsql1);

		tMoney = Double.parseDouble(tSMoney);

		// 规则
		if (tnum > 0 && tnum <= 50) {
			tMoney = 2 * (tMoney / tnum);
			if (tMoney > 200000) {
				tMoney = 200000;
			}

			tResult = mLCPolSchema.getAmnt() - tMoney;
		}

		if (tnum >= 51 && tnum <= 100) {
			tMoney = 3 * (tMoney / tnum);
			if (tMoney > 250000) {
				tMoney = 250000;
			}

			tResult = mLCPolSchema.getAmnt() - tMoney;

		}

		if (tnum >= 101 && tnum <= 500) {
			tMoney = 4 * (tMoney / tnum);
			if (tMoney > 300000) {
				tMoney = 300000;
			}

			tResult = mLCPolSchema.getAmnt() - tMoney;

		}

		if (tnum >= 501) {
			tMoney = 5 * (tMoney / tnum);
			if (tMoney > 350000) {
				tMoney = 350000;
			}

			tResult = mLCPolSchema.getAmnt() - tMoney;

		}

		return tResult;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(mAllLDHealthSet);
	}

	public VData getResult() {
		return mInputData;
	}

}
