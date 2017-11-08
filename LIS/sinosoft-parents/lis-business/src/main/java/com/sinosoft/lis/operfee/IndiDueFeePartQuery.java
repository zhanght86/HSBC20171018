package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.vbl.LCContBLSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class IndiDueFeePartQuery {
private static Logger logger = Logger.getLogger(IndiDueFeePartQuery.class);
	/* 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/* 往后台传输数据的容器 */
	private VData mResult = new VData();

	private GlobalInput tGI = new GlobalInput();

	/* 数据操作字符串 */
	private String mOperate;

	/* 业务处理相关变量 */
	private LCContSet mLCContSet = new LCContBLSet();
	private String[][] xResult ;  //最终返回的结果集,多维数组。
	private int xLength =0; //返回数组长度

	private TransferData mTransferData = new TransferData();

	private String mContNo,mPrtNo, mMangeCom, mStartDate, mRiskCode, mAgentCode,
			mEndDate, mSubDate,mRnewUWFlag = "";

	private String mSecPayMode, mContType;

	public IndiDueFeePartQuery() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类
		this.mOperate = cOperate;
		if (!getInputData(cInputData)) {
			return false;
		}
		// 进行业务处理
//		if (mOperate.equals("LCCont")) {
//			if (!queryLCCont()) {
//				return false;
//			}
//		} else if (mOperate.equals("LCPol")) {
//			if (!queryLCPol()) {
//				return false;
//			}
//		} else if (mOperate.equals("AllCont")) {
//			if (!queryAllCont()) {
//				return false;
//			}
//		} else if (mOperate.equals("AllContSQL")) {
//			if (!queryAllContSQL()) {
//				return false;
//			}
//		} else if (mOperate.equals("GrpCont")) {
//			if (!queryGrpContSQL()) {
//				return false;
//			}
//		}
		if(mOperate.equals("ZC")||mOperate.equals("GQ"))
		{
			if (!queryAllCont()) {
				return false;
			}
		}
		else
		{
			logger.debug("IndiDueFeePartQuery.mOperate:-----"+mOperate);
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/* 从传来的数据中找到所有的对象和值 */
	private boolean getInputData(VData cInputData) {
		tGI = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		if (tGI == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IndiDueFeePartBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			CError tError = new CError();
			tError.moduleName = "IndiDueFeePartQuery";
			tError.functionName = "getInputData";
			tError.errorMessage = "请输入查询条件!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("Contno");
		mPrtNo = (String) mTransferData.getValueByName("Prtno");
		mMangeCom = (String) mTransferData.getValueByName("MangeCom");
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");
		mAgentCode = (String) mTransferData.getValueByName("AgentCode");
		mSecPayMode = (String) mTransferData.getValueByName("SecPayMode");
		mContType = (String) mTransferData.getValueByName("ContType");
		mRnewUWFlag =(String) mTransferData.getValueByName("RnewUWFlag");

		if (mMangeCom == null || mMangeCom.equals("")) // 如果不录入管理机构则管理机构取全局
		{
			mMangeCom = tGI.ManageCom;
		}

		if (mMangeCom != null && !mMangeCom.equals("")) {
			String UserManagecom = tGI.ManageCom;
			int UserManagecomLength = UserManagecom.length();
			int ManagecomLength = mMangeCom.length();
			if (ManagecomLength < UserManagecomLength) {
				CError tError = new CError();
				tError.moduleName = "IndiDueFeePartQuery";
				tError.functionName = "getInputData";
				tError.errorMessage = "机构越权操作!";
				this.mErrors.addOneError(tError);
				return false;
			} else if (!mMangeCom.substring(0, UserManagecomLength).equals(
					UserManagecom)) {
				CError tError = new CError();
				tError.moduleName = "IndiDueFeePartQuery";
				tError.functionName = "getInputData";
				tError.errorMessage = "机构越权操作!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	public boolean queryLCPol() {
		LCPolSet tLCPolSet = new LCPolSet();
		// 催收提前天数--默认62天
		// 从上一个页面中得到相应的值并进行运算出催缴的时间段
		int aheaddays = 63;
		String SubDay;
		PubFun date = new PubFun();
		FDate tTranferDate = new FDate();

		// 计算出这段时间的间隔天数*/
		// 保存成功处理的纪录数Integer.parseInt(AheadDays)Integer.parseInt(AheadDays)
		// int operSuccCount = 0;
		// if (mStartDate == null || mStartDate.equals(""))
		// {
		// mStartDate = date.getCurrentDate();
		// mSubDate = date.calDate(date.getCurrentDate(), aheaddays, "D", null);
		// }
		// else
		// {
		// int tSEInterval = date.calInterval(mStartDate, mEndDate, "D");
		// mSubDate = date.calDate(mStartDate, aheaddays + tSEInterval, "D",
		// null);
		// }
		String tStartDateSql = "";
		String tEndDateSql = "";
		String tRiskCodeSql = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if (mStartDate != null && !mStartDate.equals("")) {
			tStartDateSql = " and paytodate>='?mStartDate?'";
			sqlbv.put("mStartDate", mStartDate);
		}

		// if (mEndDate != null && !mEndDate.equals(""))
		// {
		// tEndDateSql = " and paytodate<='" + mEndDate + "'";
		// }

		String sqlStr = "select * from lcpol"
				+ " where paytodate<payenddate"
				+ " and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=lcpol.riskcode)"
				+ " and (StopFlag='0' or StopFlag is null)"
				+ " and not exists (SELECT polno FROM ljspayperson WHERE  ljspayperson.polno=lcpol.polno and PayType='ZC')"
				+ tStartDateSql
				+ tEndDateSql
				// + tRiskCodeSql
				+ " and appflag='1'" + " and grppolno='00000000000000000000'"
                //排除催收完毕的保单
				+ " and not exists(select 1  from LCRnewStateHistory where contno = lcpol.contno and state ='4') "
				+ " and ManageCom like concat('?mMangeCom?','%')"
				+ " and payintv>0 and ContNo = '?mContNo?'";

		// if(mContNo != null && mContNo.equals(""))
		// {
		// sqlStr = sqlStr + "";
		// }

	
		sqlbv.sql(sqlStr);
		sqlbv.put("mContNo", mContNo);
		sqlbv.put("mMangeCom", mMangeCom);
		logger.debug("in BL SQL=" + sqlStr);

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolSet = tLCPolDB.executeQuery(sqlbv);

		if (tLCPolSet.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "LCContQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "保单表查询失败!";
			this.mErrors.addOneError(tError);
			mLCContSet.clear();

			return false;
		}

		if (tLCPolSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "没有查询到符合条件的保单表!";
			this.mErrors.addOneError(tError);
			mLCContSet.clear();

			return false;
		}
		mResult.clear();
		mResult.add(tLCPolSet);

		return true;

	}

	/* 个人报单表 */
	public boolean queryLCCont() {
		// 催收提前天数--默认62天
		// 从上一个页面中得到相应的值并进行运算出催缴的时间段
		int aheaddays = 63;
		String SubDay;
		PubFun date = new PubFun();
		FDate tTranferDate = new FDate();

		// 计算出这段时间的间隔天数*/
		// 保存成功处理的纪录数Integer.parseInt(AheadDays)Integer.parseInt(AheadDays)
		// int operSuccCount = 0;
		String tStartDateSql = "";
		String tEndDateSql = "";
		String tRiskCodeSql = "";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		// 日期为交费对应日
		if (mStartDate != null && !mStartDate.equals("")) {
			// mStartDate = date.getCurrentDate();
			// mSubDate = date.calDate(date.getCurrentDate(), aheaddays, "D",
			// null);
			tStartDateSql = " and paytodate>='?mStartDate?'";
			sqlbv1.put("mStartDate", mStartDate);
		}
		// else
		// {
		// int tSEInterval = date.calInterval(mStartDate, mEndDate, "D");
		// mSubDate = date.calDate(mStartDate, aheaddays + tSEInterval, "D",
		// null);
		// }

		if (mEndDate != null && !mEndDate.equals("")) {
			tEndDateSql = " and paytodate<='?mEndDate?'";
			sqlbv1.put("mEndDate", mEndDate);
		}

		if (mRiskCode != null && !mRiskCode.equals("")) {
			tRiskCodeSql = " RiskCode = '?mRiskCode?'";
			sqlbv1.put("mRiskCode", mRiskCode);
		}
		String sqlStr = "select * from lccont where contno in (select distinct contno from lcpol"
				+ " where paytodate<payenddate"
				+ " and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=lcpol.riskcode)"
				+ " and (StopFlag='0' or StopFlag is null)"
				+ " and not exists (SELECT polno FROM ljspayperson WHERE  ljspayperson.polno=lcpol.polno and paytype='ZC')"
				+ tStartDateSql
				+ tEndDateSql
				+ tRiskCodeSql
				+ " and appflag='1'"
				//排除催收完毕的保单
				+ " and not exists(select 1  from LCRnewStateHistory where contno = lccont.contno and state ='4') "
				+ " and ManageCom like concat('?mMangeCom?','%')" + " and payintv>0)";
		sqlbv1.put("mMangeCom", mMangeCom);

		if (mContNo != null && !mContNo.equals("")) {
			sqlStr = sqlStr + " and ContNo = '?mContNo?'";
			sqlbv1.put("mContNo", mContNo);
		}

		if (mAgentCode != null && !mAgentCode.equals("")) {
			sqlStr = sqlStr + " and AgentCode = '?mAgentCode?'";
			sqlbv1.put("mAgentCode", mAgentCode);
		}
		if (mSecPayMode != null && !mSecPayMode.equals("")) {
			sqlStr = sqlStr + " and PayLocation = '?mSecPayMode?'";
			sqlbv1.put("mSecPayMode", mSecPayMode);
		}
		if (mContType != null && !mContType.equals("")) {
			sqlStr = sqlStr + " and SaleChnl = '?mContType?'";
			sqlbv1.put("mContType", mContType);
		}
		sqlbv1.sql(sqlStr);
		logger.debug("in BL SQL=" + sqlStr);
		LCContDB tLCContDB = new LCContDB();
		mLCContSet = tLCContDB.executeQuery(sqlbv1);

		if (tLCContDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCContDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "LCContQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "保单表查询失败!";
			this.mErrors.addOneError(tError);
			mLCContSet.clear();

			return false;
		}

		if (mLCContSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "没有查询到符合条件的保单表!";
			this.mErrors.addOneError(tError);
			mLCContSet.clear();
			logger.debug("没有找到存在的表单");

			return false;
		}

		mResult.clear();
		mResult.add(mLCContSet);
		return true;
	}

	// 查询要抽档的期缴保单与趸交自动续保的保单
	public boolean queryAllCont() {
		String tSqlPara = "";
		String tSqlPara2 = "";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		// 日期为交费对应日
		if (mStartDate != null && !mStartDate.equals("")) {
			tSqlPara += " and paytodate>='?mStartDate?'";
			sqlbv2.put("mStartDate", mStartDate);
		}
		if (mEndDate != null && !mEndDate.equals("")) {
			tSqlPara += " and paytodate<='?mEndDate?'";
			sqlbv2.put("mEndDate", mEndDate);
		}
		if (mRiskCode != null && !mRiskCode.equals("")) {
			tSqlPara += " and RiskCode = '?mRiskCode?'";
			sqlbv2.put("mRiskCode", mRiskCode);
		}
		if (mMangeCom != null && !mMangeCom.equals("")) {
			tSqlPara += " and ManageCom like concat('?mMangeCom1?','%')";
			sqlbv2.put("mMangeCom1", mMangeCom);
		}

		if (mContNo != null && !mContNo.equals("")) {
			tSqlPara2 += " and ContNo = '?mContNo?'";
			sqlbv2.put("mContNo", mContNo);
		}
		if (mPrtNo != null && !mPrtNo.equals("")) {
			tSqlPara2 += " and PrtNo = '?mPrtNo?'";
			sqlbv2.put("mPrtNo", mPrtNo);
		}
		if (mAgentCode != null && !mAgentCode.equals("")) {
			tSqlPara2 += " and AgentCode = '?mAgentCode?'";
			sqlbv2.put("mAgentCode", mAgentCode);
		}
		if (mSecPayMode != null && !mSecPayMode.equals("")) {
			tSqlPara2 += " and PayLocation = '?mSecPayMode?'";
			sqlbv2.put("mSecPayMode", mSecPayMode);
		}
		if (mContType != null && !mContType.equals("")) {
			tSqlPara2 += " and salechnl = '?mContType?'";
			sqlbv2.put("mContType", mContType);
		} 
//		else {
//			tSqlPara2 += " and salechnl = '02'";
//		}
		if (mMangeCom != null && !mMangeCom.equals("")) {
			tSqlPara2 += " and ManageCom like concat('?mMangeCom2?','%')";
			sqlbv2.put("mMangeCom2", mMangeCom);
		}
		if (mRnewUWFlag != null && !mRnewUWFlag.equals("")) {
			if(mRnewUWFlag.equals("N"))
			{
				tSqlPara2 += " and not exists(select 1  from LCRnewStateHistory where contno = lcpol.contno and state ='2') ";
			}	
		}
		String sqlStr = "";
		String tsql = "";
		if(mOperate.equals("ZC"))
		{
			// 正常续期续保
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			sqlStr = "select  contno,prtno,(select AppntName from lccont where lccont.contno=lcpol.contno) from lcpol where grpcontno='00000000000000000000' and ( paytodate < payenddate "
	                +" or( PaytoDate = PayEndDate and RnewFlag = '-1'and((PolNo = MainPolNo and adddate(PayToDate,15) > now()) "
	                +" or(PolNo <> MainPolNo and PayToDate < (select Payenddate from LCPol b	where b.PolNo = lcpol.MainPolNo "
	    			+" and AppFlag = '1'and rownum = 1)))))"
	    			+" and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=lcpol.riskcode)" // 催缴标记,Y--催付、N--不催付
					+ tSqlPara
					+ " and appflag='1'"// 投保单/保单标志,1 - 承保
					+ " and (StopFlag='0' or StopFlag is null)"// 停交标志,0 --正常,1--停交
					//+ " and not exists (SELECT polno FROM ljspayperson WHERE  ljspayperson.polno=lcpol.polno and paytype='ZC')"
					+ " and not exists (SELECT contno FROM ljspay WHERE  ljspay.otherno=lcpol.contno and othernotype in ('02','03','2','3'))"// 2续期交费（渠道—个人）,3续期交费（渠道—银代）,02预收续期保费个人,03预收续期保费银代
					+ " and not exists (SELECT contno FROM ljspayperson WHERE  ljspayperson.contno=lcpol.contno )"//凡是有催收的都不予处理
					//排除催收完毕的保单
					+ " and not exists(select 1  from LCRnewStateHistory where contno = lcpol.contno and state ='4') "
					+ tSqlPara2;
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				sqlStr = "select  contno,prtno,(select AppntName from lccont where lccont.contno=lcpol.contno) from lcpol where grpcontno='00000000000000000000' and ( paytodate < payenddate "
		                +" or( PaytoDate = PayEndDate and RnewFlag = '-1'and((PolNo = MainPolNo and adddate(PayToDate,15) > now()) "
		                +" or(PolNo <> MainPolNo and PayToDate < (select Payenddate from LCPol b	where b.PolNo = lcpol.MainPolNo "
		    			+" and AppFlag = '1' limit 0,1)))))"
		    			+" and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=lcpol.riskcode)" // 催缴标记,Y--催付、N--不催付
						+ tSqlPara
						+ " and appflag='1'"// 投保单/保单标志,1 - 承保
						+ " and (StopFlag='0' or StopFlag is null)"// 停交标志,0 --正常,1--停交
						//+ " and not exists (SELECT polno FROM ljspayperson WHERE  ljspayperson.polno=lcpol.polno and paytype='ZC')"
						+ " and not exists (SELECT contno FROM ljspay WHERE  ljspay.otherno=lcpol.contno and othernotype in ('02','03','2','3'))"// 2续期交费（渠道—个人）,3续期交费（渠道—银代）,02预收续期保费个人,03预收续期保费银代
						+ " and not exists (SELECT contno FROM ljspayperson WHERE  ljspayperson.contno=lcpol.contno )"//凡是有催收的都不予处理
						//排除催收完毕的保单
						+ " and not exists(select 1  from LCRnewStateHistory where contno = lcpol.contno and state ='4') "
						+ tSqlPara2;
			}
			// 主险缴费满期，附加险自动续保
			tsql = " select  contno,prtno,(select AppntName from lccont where lccont.contno=lcpol.contno) from lcpol where grpcontno='00000000000000000000' and mainpolno<>polno"
				    +" and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=lcpol.riskcode)" // 催缴标记,Y--催付、N--不催付
					+ tSqlPara
					+ " and rnewflag='-1'"
					+ " and appflag='1'"
					+ " and (StopFlag='0' or StopFlag is null)"
					+ " and mainpolno in (select polno from lcpol where polno=mainpolno and payintv<>0 and paytodate>=payenddate and paytodate<enddate )"
					+ " and  not exists (select otherno from ljspay where othernotype in ('2','3') and ljspay.otherno=lcpol.contno )"
					+ " and not exists (SELECT contno FROM ljspayperson WHERE  ljspayperson.contno=lcpol.contno )"//凡是有催收的都不予处理
					//排除催收完毕的保单
					+ " and not exists(select 1  from LCRnewStateHistory where contno = lcpol.contno and state ='4') "
					+ tSqlPara2;
		}
		else
		{
			logger.debug("过期催收!!");
			// 正常续期续保
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			sqlStr = "select  contno,prtno,(select AppntName from lccont where lccont.contno=lcpol.contno) from lcpol where grpcontno='00000000000000000000' and ( paytodate < payenddate "
	                +" or( PaytoDate = PayEndDate and RnewFlag = '-1'and((PolNo = MainPolNo and 1=1) "
	                +" or(PolNo <> MainPolNo and PayToDate < (select Payenddate from LCPol b	where b.PolNo = lcpol.MainPolNo "
	    			+" and AppFlag = '1'and rownum = 1)))))"
	    			+" and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=lcpol.riskcode)" // 催缴标记,Y--催付、N--不催付
					+ tSqlPara
					+ " and appflag='1'"// 投保单/保单标志,1 - 承保
					+ " and (StopFlag='0' or StopFlag is null)"// 停交标志,0 --正常,1--停交
					//+ " and not exists (SELECT polno FROM ljspayperson WHERE  ljspayperson.polno=lcpol.polno and paytype='ZC')"
					+ " and not exists (SELECT contno FROM ljspay WHERE  ljspay.otherno=lcpol.contno and othernotype in ('02','03','2','3'))"// 2续期交费（渠道—个人）,3续期交费（渠道—银代）,02预收续期保费个人,03预收续期保费银代
					//排除催收完毕的保单
					+ " and not exists(select 1  from LCRnewStateHistory where contno = lcpol.contno and state ='4') "
					+ tSqlPara2;
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				sqlStr = "select  contno,prtno,(select AppntName from lccont where lccont.contno=lcpol.contno) from lcpol where grpcontno='00000000000000000000' and ( paytodate < payenddate "
		                +" or( PaytoDate = PayEndDate and RnewFlag = '-1'and((PolNo = MainPolNo and 1=1) "
		                +" or(PolNo <> MainPolNo and PayToDate < (select Payenddate from LCPol b	where b.PolNo = lcpol.MainPolNo "
		    			+" and AppFlag = '1' limit 0,1)))))"
		    			+" and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=lcpol.riskcode)" // 催缴标记,Y--催付、N--不催付
						+ tSqlPara
						+ " and appflag='1'"// 投保单/保单标志,1 - 承保
						+ " and (StopFlag='0' or StopFlag is null)"// 停交标志,0 --正常,1--停交
						//+ " and not exists (SELECT polno FROM ljspayperson WHERE  ljspayperson.polno=lcpol.polno and paytype='ZC')"
						+ " and not exists (SELECT contno FROM ljspay WHERE  ljspay.otherno=lcpol.contno and othernotype in ('02','03','2','3'))"// 2续期交费（渠道—个人）,3续期交费（渠道—银代）,02预收续期保费个人,03预收续期保费银代
						//排除催收完毕的保单
						+ " and not exists(select 1  from LCRnewStateHistory where contno = lcpol.contno and state ='4') "
						+ tSqlPara2;
			}
			// 主险缴费满期，附加险自动续保
			tsql = " select  contno,prtno,(select AppntName from lccont where lccont.contno=lcpol.contno) from lcpol where grpcontno='00000000000000000000' and mainpolno<>polno"
				    +" and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=lcpol.riskcode)" // 催缴标记,Y--催付、N--不催付
					+ tSqlPara
					+ " and rnewflag='-1'"
					+ " and appflag='1'"
					+ " and (StopFlag='0' or StopFlag is null)"
					+ " and mainpolno in (select polno from lcpol where polno=mainpolno and payintv<>0 and paytodate>=payenddate and paytodate<enddate )"
					+ " and  not exists (select otherno from ljspay where othernotype in ('2','3') and ljspay.otherno=lcpol.contno )"
					//+ " and not exists (SELECT polno FROM ljspayperson WHERE ljspayperson.polno=lcpol.polno and paytype='ZC') "
					//排除催收完毕的保单
					+ " and not exists(select 1  from LCRnewStateHistory where contno = lcpol.contno and state ='4') "
					+ tSqlPara2;
			
		}
		/*
		// 主险趸交，附加险自动续保
		String jsql = " select * from lccont where contno in (select contno from lcpol a where a.mainpolno <> a.polno"
			    +" and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=a.riskcode)" // 催缴标记,Y--催付、N--不催付
				+ tSqlPara
				+ " and a.rnewflag='-1'"
				+ " and a.appflag='1'"
				+ " and (a.StopFlag='0' or a.StopFlag is null)"
				+ " and a.mainpolno in (select polno from lcpol b where b.polno=b.mainpolno and b.PayIntv = '0' and b.enddate>a.enddate)"
				+ " and a.contno not in (select otherno from ljspay where othernotype in ('2','3'))"
				+ " and not exists (SELECT polno FROM ljspayperson WHERE ljspayperson.polno=a.polno and paytype='ZC'))"
				//排除催收完毕的保单
				+ " and not exists(select 1  from LCRnewStateHistory where contno = lccont.contno and state ='4') "
				+ tSqlPara2;
         */
		// 正常续期续保
//		logger.debug("正常续期续保,sqlStr=" + sqlStr);
//		LCContDB tLCContDB = new LCContDB();
//		mLCContSet = tLCContDB.executeQuery(sqlStr);
//
//		// 主险期满，附加险续保
//		logger.debug("主险期满，附加险续保,tsql=" + tsql);
//		LCContSet nLCContSet = new LCContSet();
//		nLCContSet = tLCContDB.executeQuery(tsql);
//		if (nLCContSet.size() > 0)
//			mLCContSet.add(nLCContSet);

		/*
		// 主险趸交，附加险续保
		logger.debug("主险趸交，附加险续保,jsql=" + jsql);
		nLCContSet = new LCContSet();
		nLCContSet = tLCContDB.executeQuery(jsql);
		if (nLCContSet.size() > 0)
			mLCContSet.add(nLCContSet);
         */
		// 错误处理
//		if (tLCContDB.mErrors.needDealError() == true) {
//			this.mErrors.copyAllErrors(tLCContDB.mErrors);
//			CError tError = new CError();
//			tError.moduleName = "LCContQueryBL";
//			tError.functionName = "queryData";
//			tError.errorMessage = "保单表查询失败!";
//			this.mErrors.addOneError(tError);
//			mLCContSet.clear();
//			return false;
//		}
//		if (mLCContSet.size() == 0) {
//			CError tError = new CError();
//			tError.moduleName = "LCPolQueryBL";
//			tError.functionName = "queryData";
//			tError.errorMessage = "没有查询到符合条件的保单表!";
//			this.mErrors.addOneError(tError);
//			mLCContSet.clear();
//			logger.debug("没有找到存在的表单");
//			return false;
//		}
		
		String sql_count = "select count(*) from ("+sqlStr+" union "+tsql+") g";
//		String 
		sqlbv2.sql(sql_count);
		ExeSQL tExeSQL = new ExeSQL();
		xLength =0;
		xLength =Integer.parseInt(tExeSQL.getOneValue(sqlbv2));
		if(xLength<=0)
		{
			CError tError = new CError();
			tError.moduleName = "LCPolQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "没有查询到符合条件的保单!可能原因：1.可能已经存在催收！2.未进入应收期！";
			this.mErrors.addOneError(tError);
			mLCContSet.clear();
			logger.debug("没有找到存在的表单");
			return false;
		}
		
		 Connection conn = DBConnPool.getConnection();
	       PreparedStatement prmt=null;
	       ResultSet rs=null;
	       xResult = new String[xLength][4];
	       try{
		       SQLwithBindVariables sqlbvN=new SQLwithBindVariables();
			   sqlbvN.sql(sqlStr+" union "+tsql);
			   sqlbvN.put("mStartDate", mStartDate);
			   sqlbvN.put("mEndDate", mEndDate);
			   sqlbvN.put("mContNo", mContNo);
			   sqlbvN.put("mMangeCom2", mMangeCom);
			   sqlbvN.put("mMangeCom1", mMangeCom);
			   SSRS nSSRS = new SSRS();
				ExeSQL tExeSQL1 = new ExeSQL();
				nSSRS = tExeSQL1.execSQL(sqlbvN);
		       for(int i=0;i<xLength;i++)
		       {
		    	   if(nSSRS.MaxRow>0)
		    	   {
		    		   xResult[i][0]=nSSRS.GetText(i+1, 1).trim();
		    		   xResult[i][1]=nSSRS.GetText(i+1, 2).trim();
		    		   xResult[i][2]=nSSRS.GetText(i+1, 3).trim();
		    		   if(mRnewUWFlag != null && !mRnewUWFlag.equals(""))
		    		   {
		    			   String check_str=" select (case count(*) when 0 then 'N' else 'Y' end) from LCRnewStateHistory " +
							" where contno = '?contno?' and state ='2' ";
		    			   SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		    			   sqlbv3.sql(check_str);
		    			   sqlbv3.put("contno", xResult[i][0]);
		    			   String rnewuwflag = tExeSQL.getOneValue(sqlbv3);
		    			   xResult[i][3]=rnewuwflag;
		    		   }
		    		   else
		    		   {
		    			   xResult[i][3]="";
		    		   }
		    	   }
		       }
		       rs.close();
		       prmt.close();
		       conn.close();
	       }catch(Exception exe)
	       {
	    	   try
	           {
	               if (rs != null)
	               {
	                   rs.close();
	               }

	               if (prmt != null)
	               {
	                   // 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
	                   try
	                   {
	                	   prmt.close();
	                   }
	                   catch (SQLException ex)
	                   {
	                       ex.printStackTrace();
	                   }
	                   finally
	                   {
	                	   prmt.close();
	                   }
	               }
	               if(!conn.isClosed())
	               {
	            	   conn.close();
	               }
	           }
	           catch (SQLException ex)
	           {
	               ex.printStackTrace();
	           }
	       }
		
		//当进行可催收保单查询时，需要置上是否二核标记
//		if (mRnewUWFlag != null && !mRnewUWFlag.equals("")) 
//		{
//			//是否存在二核标记
//			String rnewuwflag="";
//			ExeSQL rnew_ExeSQL = new ExeSQL();
//			for(int i=1;i<=xLength;i++)
//			{
//				rnewuwflag="";
//				String check_str=" select decode(count(*),0,'N','Y') from LCRnewStateHistory " +
//					" where contno = '"+mLCContSet.get(i).getContNo()+"' and state ='2' ";
//				rnewuwflag = rnew_ExeSQL.getOneValue(check_str);
//				xResult[i-1][0]=mLCContSet.get(i).getContNo();	
//				xResult[i-1][1]=mLCContSet.get(i).getPrtNo();
//				xResult[i-1][2]=mLCContSet.get(i).getAppntName();
//				xResult[i-1][3]=rnewuwflag;			
//			}
//		}
//		else
//		{
//			for(int i=1;i<=xLength;i++)
//			{
//				xResult[i-1][0]=mLCContSet.get(i).getContNo();	
//				xResult[i-1][1]=mLCContSet.get(i).getPrtNo();
//				xResult[i-1][2]=mLCContSet.get(i).getAppntName();
//				xResult[i-1][3]="";			
//			}
//		}
        
		mResult.clear();
		mResult.add(xResult);
		return true;
	}

	public boolean getNextConts(int part) {
		String tStartDateSql = "";
		String tEndDateSql = "";
		String tRiskCodeSql = "";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		// 日期为交费对应日
		if (mStartDate != null && !mStartDate.equals("")) {
			tStartDateSql = " and paytodate>='" + mStartDate + "'";
			sqlbv4.put("mStartDate", mStartDate);
			sqlbv5.put("mStartDate", mStartDate);
		}

		if (mEndDate != null && !mEndDate.equals("")) {
			tEndDateSql = " and paytodate<='?mEndDate?'";
			sqlbv4.put("mEndDate", mEndDate);
			sqlbv5.put("mEndDate", mEndDate);
		}

		if (mRiskCode != null && !mRiskCode.equals("")) {
			tRiskCodeSql = " RiskCode = '?mRiskCode?'";
			sqlbv4.put("mRiskCode", mRiskCode);
			sqlbv5.put("mRiskCode", mRiskCode);
		}
		// 正常续期续保
		String sqlStr = "select * from lccont where contno in (select distinct contno from lcpol"
				+ " where paytodate<payenddate"
				+ " and riskcode  in (select riskcode from lmriskpay WHERE urgepayflag='Y' )"
				+ " and (StopFlag='0' or StopFlag is null)"
				+ " and polno not in  (SELECT polno FROM ljspayperson WHERE   paytype='ZC')"
				+ tStartDateSql
				+ tEndDateSql
				+ tRiskCodeSql
				+ " and appflag='1'"
				+ " and ManageCom like concat('?mMangeCom?','%')" + " and payintv>0 " + " and paytodate < enddate)";
		sqlbv4.put("mMangeCom", mMangeCom);
		// 主险趸交，负险自动续保 主险缴费满期，附加险自动续保
		String tsql = " select * from lccont where contno in (select contno from lcpol where mainpolno<>polno"
				+ tStartDateSql
				+ tEndDateSql
				+ tRiskCodeSql
				+ " and rnewflag='-1'"
				+ " and appflag='1'"
				+ " and (StopFlag='0' or StopFlag is null)"
				+ " and mainpolno in (select polno from lcpol where polno=mainpolno and (PayIntv = '0' or paytodate=payenddate))"
				+ " and contno not in (select otherno from ljspay where othernotype in ('2','3'))) ";
		if (mContNo != null && !mContNo.equals("")) {
			sqlStr = sqlStr + " and ContNo = '?mContNo?'";
			sqlbv4.put("mContNo", mContNo);
			tsql = tsql + " and ContNo = '?mContNo?'";
			sqlbv5.put("mContNo", mContNo);
			// jsql = jsql + " and ContNo = '" + mContNo + "'";
		}

		if (mAgentCode != null && !mAgentCode.equals("")) {
			sqlStr = sqlStr + " and AgentCode = '?mAgentCode?'";
			sqlbv4.put("mAgentCode", mAgentCode);
			tsql = tsql + " and AgentCode = '?mAgentCode?'";
			sqlbv5.put("mAgentCode", mAgentCode);
			// jsql = jsql + " and AgentCode = '" + mAgentCode + "'";
		}
		if (mSecPayMode != null && !mSecPayMode.equals("")) {
			sqlStr = sqlStr + " and PayLocation = '?mSecPayMode?'";
			sqlbv4.put("mSecPayMode", mSecPayMode);
			tsql = tsql + " and PayLocation = '?mSecPayMode?'";
			sqlbv5.put("mSecPayMode", mSecPayMode);
			// jsql = jsql + " and PayLocation = '" + mSecPayMode + "'";
		}
		if (mContType != null && !mContType.equals("")) {
			sqlStr = sqlStr + " and SaleChnl = '?mContType?'";
			sqlbv4.put("mContType", mContType);
			tsql = tsql + " and SaleChnl = '?mContType?'";
			sqlbv5.put("mContType", mContType);
			// jsql = jsql + " and SaleChnl = '" + mContType + "'";
		}
		logger.debug("in BL SQL=" + sqlStr);
		LCContDB tLCContDB = new LCContDB();
		sqlbv4.sql(sqlStr);
		mLCContSet = tLCContDB.executeQuery(sqlbv4);

		// 主险期满，附加险续保
		LCContDB nLCContDB = new LCContDB();
		LCContSet nLCContSet = new LCContSet();
		logger.debug("主险期满，附加险续保=" + tsql);
		sqlbv5.sql(tsql);
		nLCContSet = nLCContDB.executeQuery(sqlbv5);
		if (nLCContSet.size() > 0)
			mLCContSet.add(nLCContSet);

		if (tLCContDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCContDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "LCContQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "保单表查询失败!";
			this.mErrors.addOneError(tError);
			mLCContSet.clear();

			return false;
		}

		if (mLCContSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "没有查询到符合条件的保单表!";
			this.mErrors.addOneError(tError);
			mLCContSet.clear();
			logger.debug("没有找到存在的表单");

			return false;
		}

		mResult.clear();
		mResult.add(mLCContSet);

		return true;

	}

	private boolean queryAllContSQL() {
		String tStartDateSql = "";
		String tEndDateSql = "";
		String tRiskCodeSql = "";
		// 日期为交费对应日
		if (mStartDate != null && !mStartDate.equals("")) {
			tStartDateSql = " and paytodate>='" + mStartDate + "'";
		}

		if (mEndDate != null && !mEndDate.equals("")) {
			tEndDateSql = " and paytodate<='" + mEndDate + "'";
		}

		if (mRiskCode != null && !mRiskCode.equals("")) {
			tRiskCodeSql = " RiskCode = '" + mRiskCode + "'";
		}
		// 正常续期续保
		String sqlStr = "select * from lccont where contno in (select distinct contno from lcpol"
				+ " where paytodate<payenddate"
				+ " and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=lcpol.riskcode)"
				+ " and (StopFlag='0' or StopFlag is null)"
				+ " and not exists (SELECT polno FROM ljspayperson WHERE  ljspayperson.polno=lcpol.polno and paytype='ZC')"
				+ tStartDateSql
				+ tEndDateSql
				+ tRiskCodeSql
				+ " and appflag='1'"
				//排除催收完毕的保单
				+ " and not exists(select 1  from LCRnewStateHistory where contno = lccont.contno and state ='4') "
				+ " and ManageCom like '"
				+ mMangeCom
				+ "%'" + " and payintv>0 " + " and paytodate < enddate)";
		// 主险趸交，负险自动续保 主险缴费满期，附加险自动续保
		String tsql = " select * from lccont where contno in (select contno from lcpol where mainpolno<>polno"
				+ tStartDateSql
				+ tEndDateSql
				+ tRiskCodeSql
				+ " and rnewflag='-1'"
				+ " and appflag='1'"
				//排除催收完毕的保单
				+ " and not exists(select 1  from LCRnewStateHistory where contno = lcpol.contno and state ='4') "
				+ " and (StopFlag='0' or StopFlag is null)"
				+ " and mainpolno in (select polno from lcpol where polno=mainpolno and (PayIntv = '0' or paytodate=payenddate))"
				+ " and contno not in (select otherno from ljspay where othernotype in ('2','3'))) ";
		// //主险缴费满期，附加险自动续保
		// String jsql = "select * from lccont where contno in (select contno
		// from lcpol where mainpolno=polno and paytodate=payenddate and
		// PayIntv>0 and paytodate<enddate"
		// + tStartDateSql
		// + tEndDateSql
		// + tRiskCodeSql
		// + " and appflag='1'"
		// + " and (StopFlag='0' or StopFlag is null))"
		// + " and contno not in (select otherno from ljspay where othernotype
		// in ('2','3'))"
		// + " and contno in (select contno from lcpol where rnewflag = '-1' and
		// mainpolno <>polno and appflag='1' and (StopFlag='0' or StopFlag is
		// null))"
		// ;
		if (mContNo != null && !mContNo.equals("")) {
			sqlStr = sqlStr + " and ContNo = '" + mContNo + "'";
			tsql = tsql + " and ContNo = '" + mContNo + "'";
			// jsql = jsql + " and ContNo = '" + mContNo + "'";
		}

		if (mAgentCode != null && !mAgentCode.equals("")) {
			sqlStr = sqlStr + " and AgentCode = '" + mAgentCode + "'";
			tsql = tsql + " and AgentCode = '" + mAgentCode + "'";
			// jsql = jsql + " and AgentCode = '" + mAgentCode + "'";
		}
		if (mSecPayMode != null && !mSecPayMode.equals("")) {
			sqlStr = sqlStr + " and PayLocation = '" + mSecPayMode + "'";
			tsql = tsql + " and PayLocation = '" + mSecPayMode + "'";
			// jsql = jsql + " and PayLocation = '" + mSecPayMode + "'";
		}
		if (mContType != null && !mContType.equals("")) {
			sqlStr = sqlStr + " and SaleChnl = '" + mContType + "'";
			tsql = tsql + " and SaleChnl = '" + mContType + "'";
			// jsql = jsql + " and SaleChnl = '" + mContType + "'";
		}

		mResult.add(sqlStr + " union all " + tsql);
		return true;
	}

	/**
	 * 团体待抽档数据查询
	 */
	public boolean queryGrpContSQL() {
		String tStartDateSql = "";
		String tEndDateSql = "";
		String tRiskCodeSql = "";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		// 日期为交费对应日
		if (mStartDate != null && !mStartDate.equals("")) {
			tStartDateSql = " and paytodate>='?mStartDate?'";
			sqlbv6.put("mStartDate", mStartDate);
			sqlbv7.put("mStartDate", mStartDate);
			sqlbv8.put("mStartDate", mStartDate);
		}

		if (mEndDate != null && !mEndDate.equals("")) {
			tEndDateSql = " and paytodate<='?mEndDate?'";
			sqlbv6.put("mEndDate", mEndDate);
			sqlbv7.put("mEndDate", mEndDate);
			sqlbv8.put("mEndDate", mEndDate);
			
		}

		if (mRiskCode != null && !mRiskCode.equals("")) {
			tRiskCodeSql = " RiskCode = '?mRiskCode?'";
			sqlbv6.put("mRiskCode", mRiskCode);
			sqlbv7.put("mRiskCode", mRiskCode);
			sqlbv8.put("mRiskCode", mRiskCode);
		}

		// 续期
		String sqlStr = "select * from LCGrpCont where GrpContNo in "
				+ "(select distinct GrpContNo from LCGrpPol"
				+ " where 1=1"
				+ " and exists (select 1 from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=LCGrpPol.riskcode)"
				+ " and not exists (SELECT 1 FROM LJSPayGrp WHERE  LJSPayGrp.GrpPolNo=LCGrpPol.GrpPolNo and paytype='ZC')"
				+ tStartDateSql
				+ tEndDateSql
				+ tRiskCodeSql
				+ " and appflag='1'"
				+ " and ManageCom like concat('?mMangeCom?','%'))"
				+ " and not exists (SELECT 1 FROM ljspay WHERE  ljspay.otherno=LCGrpCont.GrpContNo and othernotype='1')"
				+ " and ManageCom like concat('?mMangeCom?','%')";
		sqlbv6.put("mMangeCom", mMangeCom);
		// 主险缴费满期，附加险自动续保
		String tsql = " select * from lcgrpcont where grpcontno in (select grpcontno from lcpol where mainpolno<>polno"
				+ tStartDateSql
				+ tEndDateSql
				+ tRiskCodeSql
				+ " and rnewflag='-1'"
				+ " and appflag='1'"
				+ " and (StopFlag='0' or StopFlag is null)"
				+ " and mainpolno in (select polno from lcpol where polno=mainpolno and  paytodate>=payenddate and paytodate<enddate and payintv<>0)"
				+ " and contno not in (select otherno from ljspay where othernotype in ('2','3'))"
				+ " and managecom like concat('?mMangeCom?','%'))"
				+ " and ManageCom like concat('?mMangeCom?','%')";
		sqlbv7.put("mMangeCom", mMangeCom);
		// 主险趸交，附加险自动续保
		String jsql = " select * from lcgrpcont where grpcontno in (select grpcontno from lcpol a where a.mainpolno <> a.polno"
				+ tStartDateSql
				+ tEndDateSql
				+ tRiskCodeSql
				+ " and a.rnewflag='-1'"
				+ " and a.appflag='1'"
				+ " and (a.StopFlag='0' or a.StopFlag is null)"
				+ " and a.mainpolno in (select polno from lcpol b where b.polno=b.mainpolno and b.PayIntv = '0' and b.enddate>a.enddate"
				// + " and riskcode in (select riskcode from lmriskapp where
				// riskperiod='L')"
				+ ")"
				+ " and a.contno not in (select otherno from ljspay where othernotype in ('2','3'))"
				+ " and managecom like concat('?mMangeCom?','%'))"
				+ " and ManageCom like concat('?mMangeCom?','%')";
		sqlbv8.put("mMangeCom", mMangeCom);

		if (mContNo != null && !mContNo.equals("")) {
			sqlStr = sqlStr + " and GrpContNo = '?mContNo?'";
			tsql = tsql + " and grpContNo = '?mContNo?'";
			jsql = jsql + " and grpContNo = '?mContNo?'";
			sqlbv6.put("mContNo", mContNo);
			sqlbv7.put("mContNo", mContNo);
			sqlbv8.put("mContNo", mContNo);
		}

		if (mAgentCode != null && !mAgentCode.equals("")) {
			sqlStr = sqlStr + " and AgentCode = '?mAgentCode?'";
			tsql = tsql + " and AgentCode = '?mAgentCode?'";
			jsql = jsql + " and AgentCode = '?mAgentCode?'";
			sqlbv6.put("mAgentCode", mAgentCode);
			sqlbv7.put("mAgentCode", mAgentCode);
			sqlbv8.put("mAgentCode", mAgentCode);
		}

		// 按照缴费方式
		if (mSecPayMode != null && !mSecPayMode.equals("")) {
			sqlStr = sqlStr + " and PayLocation = '?mSecPayMode?'";
			tsql = tsql + " and PayLocation = '?mSecPayMode?'";
			jsql = jsql + " and PayLocation = '?mSecPayMode?'";
			sqlbv6.put("mSecPayMode", mSecPayMode);
			sqlbv7.put("mSecPayMode", mSecPayMode);
			sqlbv8.put("mSecPayMode", mSecPayMode);
		}
		// 按照保单类型来判断
		if (mContType != null && !mContType.equals("")) {
			sqlStr = sqlStr + " and SaleChnl = '?mContType?'";
			tsql = tsql + " and SaleChnl = '?mContType?'";
			jsql = jsql + " and SaleChnl = '?mContType?'";
			sqlbv6.put("mContType", mContType);
			sqlbv7.put("mContType", mContType);
			sqlbv8.put("mContType", mContType);
		}

		logger.debug("in BL SQL=" + sqlStr);
		sqlbv6.sql(sqlStr);
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		tLCGrpContSet = tLCGrpContDB.executeQuery(sqlbv6);

		// 主险期满，附加险续保
		LCGrpContDB nLCGrpContDB = new LCGrpContDB();
		LCGrpContSet nLCGrpContSet = new LCGrpContSet();
		logger.debug("主险期满，附加险续保=" + tsql);
		sqlbv7.sql(tsql);
		nLCGrpContSet = nLCGrpContDB.executeQuery(sqlbv7);
		if (nLCGrpContSet.size() > 0)
			tLCGrpContSet.add(nLCGrpContSet);

		// 主险期满，负险续保
		nLCGrpContDB = new LCGrpContDB();
		nLCGrpContSet = new LCGrpContSet();
		logger.debug("主险期满，负险续保=========" + jsql);
		sqlbv8.sql(jsql);
		nLCGrpContSet = nLCGrpContDB.executeQuery(sqlbv8);
		if (nLCGrpContSet.size() > 0)
			tLCGrpContSet.add(nLCGrpContSet);

		if (tLCGrpContDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "LCContQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "保单表查询失败!";
			this.mErrors.addOneError(tError);
			tLCGrpContSet.clear();

			return false;
		}

		if (tLCGrpContSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "没有查询到符合条件的保单表!";
			this.mErrors.addOneError(tError);
			tLCGrpContSet.clear();
			logger.debug("没有找到存在的表单");
			return false;
		}

		mResult.clear();
		mResult.add(tLCGrpContSet);
		return true;
	}

	public String getStartTime() {
		return mStartDate;
	}

	public String getSubTime() {
		return mSubDate;
	}

	public static void main(String[] args) {
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2006-6-1");
		tTransferData.setNameAndValue("EndDate", "2006-7-1");
		tTransferData.setNameAndValue("ContNo", "2301190000000023");

		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "863200";
		tGI.Operator = "001";

		VData tv = new VData();
		tv.add(tGI);
		tv.add(tTransferData);

		IndiDueFeePartQuery indiduefeepartquery = new IndiDueFeePartQuery();
		if (!indiduefeepartquery.submitData(tv, "AllCont")) {
			logger.debug("查询催收保单失败");
		} else {
			logger.debug("处理成功");
		}
	}
}
