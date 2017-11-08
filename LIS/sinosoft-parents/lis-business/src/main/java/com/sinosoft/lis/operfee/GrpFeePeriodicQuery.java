package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.util.Calendar;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * 团单需要抽档查询程序。提取需要抽档的团单
 * 
 * @author Administrator
 * 
 */

public class GrpFeePeriodicQuery {
private static Logger logger = Logger.getLogger(GrpFeePeriodicQuery.class);

	public CErrors mErrors = new CErrors(); // 错误处理类，每个需要错误处理的类中都放置该类
	private VData mResult = new VData(); // 往后台传输数据的容器
	private GlobalInput tGI = new GlobalInput();
	private String mOperate; // 数据操作字符串
	/* 业务处理相关变量 */

	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema(); // 团体保单表
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private TransferData mTransferData = new TransferData();
	private String mGrpContNo, mMangeCom, // 团单保单号，管理机构
			mStartDate = "", mRiskCode, mAgentCode, mEndDate, mSubDate = ""; // 多个全局变量为进行查询使用
	// 开始日期，险种代码， 代理人， 结束时间，
	private String mSaleChnl = "";
	private String mSecPayMode, mContType;
	private String CurrentDate = PubFun.getCurrentDate(); // 得到当前日期
	private String CurrentTime = PubFun.getCurrentTime(); // 得到当前时间

	public GrpFeePeriodicQuery() {
	}

	public static void main(String[] args) {
		GrpFeePeriodicQuery GrpFeePeriodicQuery = new GrpFeePeriodicQuery();
		LCContSchema tLCContSchema = new LCContSchema();
		// String tContNo = "2301190000000023";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2007-5-29");
		tTransferData.setNameAndValue("EndDate", "2007-5-29");
		// tTransferData.setNameAndValue("MangeCom","2301190000000023");
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "8621";
		tGI.Operator = "001";
		tTransferData.setNameAndValue("MangeCom", "862100");

		// tTransferData.setNameAndValue("ContNo", "2301190000000023");
		VData tv = new VData();

		tv.add(tGI);
		tv.add(tTransferData);

		Calendar c = Calendar.getInstance();

		if (!GrpFeePeriodicQuery.submitData(tv, "AllCont")) {
			logger.debug("查询催收保单失败");
		} else {
			logger.debug("处理成功");
		}

		logger.debug(Calendar.getInstance().getTimeInMillis()
				- c.getTimeInMillis());

	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类
		this.mOperate = cOperate;
		if (!getInputData(cInputData)) {
			return false;
		}
		// //进行业务处理
		// if (mOperate.equals("LCCont"))
		// {
		// if (!queryLCCont())
		// {
		// return false;
		// }
		// }
		// else if (mOperate.equals("LCPol"))
		// {
		// if (!queryLCPol())
		// {
		// return false;
		// }
		// }
		// else

		if (mOperate.equals("AllCont")) {
			if (!queryAllCont()) {
				return false;
			}
		} else if (mOperate.equals("AllContSQL")) {
			if (!queryAllContSQL()) {
				return false;
			}
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

		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		mMangeCom = (String) mTransferData.getValueByName("MangeCom");
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");
		mAgentCode = (String) mTransferData.getValueByName("AgentCode");
		mSecPayMode = (String) mTransferData.getValueByName("SecPayMode");
		mContType = (String) mTransferData.getValueByName("ContType");

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
				String r = mMangeCom.substring(0, UserManagecomLength);
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

	// public boolean queryLCPol()
	// {
	// LCPolSet tLCPolSet = new LCPolSet();
	// //催收提前天数--默认62天
	// //从上一个页面中得到相应的值并进行运算出催缴的时间段
	// int aheaddays = 63;
	// String SubDay;
	// PubFun date = new PubFun();
	// FDate tTranferDate = new FDate();
	//
	// //计算出这段时间的间隔天数*/
	// //保存成功处理的纪录数Integer.parseInt(AheadDays)Integer.parseInt(AheadDays)
	// // int operSuccCount = 0;
	// // if (mStartDate == null || mStartDate.equals(""))
	// // {
	// // mStartDate = date.getCurrentDate();
	// // mSubDate = date.calDate(date.getCurrentDate(), aheaddays, "D", null);
	// // }
	// // else
	// // {
	// // int tSEInterval = date.calInterval(mStartDate, mEndDate, "D");
	// // mSubDate = date.calDate(mStartDate, aheaddays + tSEInterval, "D",
	// null);
	// // }
	// String tStartDateSql = "";
	// String tEndDateSql = "";
	// String tRiskCodeSql = "";
	// if (mStartDate != null && !mStartDate.equals(""))
	// {
	// tStartDateSql = " and paytodate>='" + mStartDate + "'";
	// }
	//
	// // if (mEndDate != null && !mEndDate.equals(""))
	// // {
	// // tEndDateSql = " and paytodate<='" + mEndDate + "'";
	// // }
	//
	// String sqlStr = "select * from lcpol"
	// + " where paytodate<payenddate"
	// +" and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and
	// lmriskpay.riskcode=lcpol.riskcode)"
	// +" and (StopFlag='0' or StopFlag is null)"
	// +" and not exists (SELECT polno FROM ljspayperson WHERE
	// ljspayperson.polno=lcpol.polno and PayType='ZC')"
	// + tStartDateSql
	// + tEndDateSql
	// // + tRiskCodeSql
	// +" and appflag='1'"
	// +" and grppolno='00000000000000000000'"
	// +" and ManageCom like '" + mMangeCom + "%'"
	// +" and payintv>0 and ContNo = '" + mGrpContNo + "'";
	//
	// // if(mGrpContNo != null && mGrpContNo.equals(""))
	// // {
	// // sqlStr = sqlStr + "";
	// // }
	//
	// logger.debug("in BL SQL=" + sqlStr);
	//
	// LCPolDB tLCPolDB = new LCPolDB();
	// tLCPolSet = tLCPolDB.executeQuery(sqlStr);
	//
	// if (tLCPolSet.mErrors.needDealError() == true)
	// {
	// // @@错误处理
	// this.mErrors.copyAllErrors(tLCPolDB.mErrors);
	//
	// CError tError = new CError();
	// tError.moduleName = "LCContQueryBL";
	// tError.functionName = "queryData";
	// tError.errorMessage = "保单表查询失败!";
	// this.mErrors.addOneError(tError);
	// mLCGrpContSet.clear();
	//
	// return false;
	// }
	//
	// if (tLCPolSet.size() == 0)
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "LCPolQueryBL";
	// tError.functionName = "queryData";
	// tError.errorMessage = "没有查询到符合条件的保单表!";
	// this.mErrors.addOneError(tError);
	// mLCGrpContSet.clear();
	//
	// return false;
	// }
	// mResult.clear();
	// mResult.add(tLCPolSet);
	//
	// return true;
	//
	// }
	//
	// /*个人报单表*/
	// public boolean queryLCCont()
	// {
	// //催收提前天数--默认62天
	// //从上一个页面中得到相应的值并进行运算出催缴的时间段
	// int aheaddays = 63;
	// String SubDay;
	// PubFun date = new PubFun();
	// FDate tTranferDate = new FDate();
	//
	// //计算出这段时间的间隔天数*/
	// //保存成功处理的纪录数Integer.parseInt(AheadDays)Integer.parseInt(AheadDays)
	// // int operSuccCount = 0;
	// String tStartDateSql = "";
	// String tEndDateSql = "";
	// String tRiskCodeSql = "";
	// //日期为交费对应日
	// if (mStartDate != null && !mStartDate.equals(""))
	// {
	// // mStartDate = date.getCurrentDate();
	// // mSubDate = date.calDate(date.getCurrentDate(), aheaddays, "D", null);
	// tStartDateSql = " and paytodate>='" + mStartDate + "'";
	// }
	// // else
	// // {
	// // int tSEInterval = date.calInterval(mStartDate, mEndDate, "D");
	// // mSubDate = date.calDate(mStartDate, aheaddays + tSEInterval, "D",
	// null);
	// // }
	//
	// if (mEndDate != null && !mEndDate.equals(""))
	// {
	// tEndDateSql = " and paytodate<='" + mEndDate + "'";
	// }
	//
	// if (mRiskCode != null && !mRiskCode.equals(""))
	// {
	// tRiskCodeSql = " RiskCode = '" + mRiskCode + "'";
	// }
	// String sqlStr =
	// "select * from lccont where contno in (select distinct contno from lcpol"
	// + " where paytodate<payenddate"
	// + " and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and
	// lmriskpay.riskcode=lcpol.riskcode)"
	// + " and (StopFlag='0' or StopFlag is null)"
	// + " and not exists (SELECT polno FROM ljspayperson WHERE
	// ljspayperson.polno=lcpol.polno and paytype='ZC')"
	// + tStartDateSql
	// + tEndDateSql
	// + tRiskCodeSql
	// + " and appflag='1'"
	// + " and ManageCom like '" + mMangeCom + "%'"
	// + " and payintv>0)"
	// ;
	//
	// if (mGrpContNo != null && !mGrpContNo.equals(""))
	// {
	// sqlStr = sqlStr + " and ContNo = '" + mGrpContNo + "'";
	// }
	//
	// if (mAgentCode != null && !mAgentCode.equals(""))
	// {
	// sqlStr = sqlStr + " and AgentCode = '" + mAgentCode + "'";
	// }
	// if (mSecPayMode != null && !mSecPayMode.equals(""))
	// {
	// sqlStr = sqlStr + " and PayLocation = '" + mSecPayMode + "'";
	// }
	// if (mContType != null && !mContType.equals(""))
	// {
	// sqlStr = sqlStr + " and SaleChnl = '" + mContType + "'";
	// }
	// logger.debug("in BL SQL=" + sqlStr);
	// LCContDB tLCContDB = new LCContDB();
	// mLCGrpContSet = tLCContDB.executeQuery(sqlStr);
	//
	// if (tLCContDB.mErrors.needDealError() == true)
	// {
	// // @@错误处理
	// this.mErrors.copyAllErrors(tLCContDB.mErrors);
	//
	// CError tError = new CError();
	// tError.moduleName = "LCContQueryBL";
	// tError.functionName = "queryData";
	// tError.errorMessage = "保单表查询失败!";
	// this.mErrors.addOneError(tError);
	// mLCGrpContSet.clear();
	//
	// return false;
	// }
	//
	// if (mLCGrpContSet.size() == 0)
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "LCPolQueryBL";
	// tError.functionName = "queryData";
	// tError.errorMessage = "没有查询到符合条件的保单表!";
	// this.mErrors.addOneError(tError);
	// mLCGrpContSet.clear();
	// logger.debug("没有找到存在的表单");
	//
	// return false;
	// }
	//
	// mResult.clear();
	// mResult.add(mLCGrpContSet);
	//
	// return true;
	// }
	// 查询要抽档的期缴保单与趸交自动续保的保单

	// 输出当前操作
	public void printStr(String str) {
		logger.debug("++++++++++++++++++++++++++++++++++++++++++");
		logger.debug("            " + str);
		logger.debug("++++++++++++++++++++++++++++++++++++++++++");

	}

	public boolean queryAllCont() {
		printStr("这里开始抽档的主SQL");// 打印当前主功能
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
		String sqlStr = "select * " + "from lcgrpcont " + "where grpcontno in "
				+ "(select distinct grpcontno " + "from lcgrppol "
				+ "where paytodate < payenddate "
				+ " and exists (select riskcode " + " from lmriskpay "
				+ " WHERE urgepayflag = 'Y' "
				+ " and lmriskpay.riskcode = lcgrppol.riskcode) "
				+ "and not exists " + "(SELECT grppolno " + "FROM LJSPayGrp "
				+ "WHERE LJSPayGrp.Grppolno = lcgrppol.grppolno "
				+ "and paytype = 'ZC') " + tStartDateSql + tEndDateSql
				+ tRiskCodeSql + "and ManageCom like '"
				+ mMangeCom
				+ "%' "
				+
				// "and grpcontno = '240210000000039' "+
				"and grpcontno not in "
				+ "(select otherno from ljspay where othernotype in ('1')) "
				+ "and managecom like '" + mMangeCom + "%' and appflag = '1') "
				+ "and ManageCom like '" + mMangeCom + "%' ";

		// " select * " +
		// " from lcgrpcont "+
		// " where grpcontno in(" +
		// "select distinct grpcontno "+
		// " from lcgrppol "+
		// " where paytodate < payenddate "+
		// // " and exists (select riskcode "+
		// // " from lmriskpay "+
		// // " WHERE urgepayflag = 'Y' "+
		// // " and lmriskpay.riskcode = lcgrppol.riskcode) "+
		// " and not exists (SELECT grppolno "+
		// " FROM LJSPayGrp "+
		// " WHERE LJSPayGrp.Grppolno = lcgrppol.grppolno "+
		// " and paytype = 'ZC')"+
		// tStartDateSql
		// + tEndDateSql
		// + tRiskCodeSql +
		// // " and appflag = '1' "+
		// // " and payintv > 0 " +
		// " and ManageCom like '" + mMangeCom + "%' and grpcontno =
		// '240210000000039' "+
		// " and grpcontno not in "+
		// " (select otherno from ljspay where thernotype in ('1')) " +
		// " and managecom like '" + mMangeCom + "%')" +
		// " and ManageCom like '" + mMangeCom + "%'";

		// 主险缴费满期，附加险自动续保
		// String tsql = " select * from lccont where contno in (select contno
		// from lcpol where mainpolno<>polno"
		// + tStartDateSql
		// + tEndDateSql
		// + tRiskCodeSql
		// + " and rnewflag='-1'"
		// + " and appflag='1'"
		// + " and (StopFlag='0' or StopFlag is null)"
		// + " and mainpolno in (select polno from lcpol where polno=mainpolno
		// and paytodate>=payenddate and paytodate<enddate and payintv<>0)"
		// + " and contno not in (select otherno from ljspay where othernotype
		// in ('2','3'))"
		// + " and managecom like '"+ mMangeCom +"%') "
		// + " and ManageCom like '" + mMangeCom + "%'"
		// ;
		// // //主险趸交，附加险自动续保
		// String jsql = " select * from lccont where contno in (select contno
		// from lcpol a where a.mainpolno <> a.polno"
		// + tStartDateSql
		// + tEndDateSql
		// + tRiskCodeSql
		// + " and a.rnewflag='-1'"
		// + " and a.appflag='1'"
		// + " and (a.StopFlag='0' or a.StopFlag is null)"
		// + " and a.mainpolno in (select polno from lcpol b where
		// b.polno=b.mainpolno and b.PayIntv = '0' and b.enddate>a.enddate"
		// //+ " and riskcode in (select riskcode from lmriskapp where
		// riskperiod='L')"
		// + ")" +
		// " and a.contno not in (select otherno from ljspay where othernotype
		// in ('2','3'))"+
		// " and managecom like '"+ mMangeCom +"%') "
		// + " and ManageCom like '" + mMangeCom + "%'"
		// ;
		if (mGrpContNo != null && !mGrpContNo.equals("")) {
			sqlStr = sqlStr + " and GrpContNo = '" + mGrpContNo + "'";
			// tsql = tsql + " and ContNo = '" + mGrpContNo + "'";
			// jsql = jsql + " and ContNo = '" + mGrpContNo + "'";
			//
		}

		if (mAgentCode != null && !mAgentCode.equals("")) {
			sqlStr = sqlStr + " and AgentCode = '" + mAgentCode + "'";
			// tsql = tsql + " and AgentCode = '" + mAgentCode + "'";
			// jsql = jsql + " and AgentCode = '" + mAgentCode + "'";
		}
		// 按照缴费方式
		// if (mSecPayMode != null && !mSecPayMode.equals(""))
		// {
		// sqlStr = sqlStr + " and PayLocation = '" + mSecPayMode + "'";
		// // tsql = tsql + " and PayLocation = '" + mSecPayMode + "'";
		// // jsql = jsql + " and PayLocation = '" + mSecPayMode + "'";
		// }
		// 按照保单类型来判断
		// if (mContType != null && !mContType.equals(""))
		// {
		// sqlStr = sqlStr + " and SaleChnl = '" + mContType + "'";
		// // tsql = tsql + " and SaleChnl = '" + mContType + "'";
		// // jsql = jsql + " and SaleChnl = '" + mContType + "'";
		// }

		if (mSaleChnl != null && !mSaleChnl.equals("")) {

		}

		logger.debug("in BL SQL=" + sqlStr);
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		mLCGrpContSet = tLCGrpContDB.executeQuery(sqlStr);

		// //主险期满，附加险续保
		// LCContDB nLCContDB = new LCContDB();
		// LCContSet nLCContSet = new LCContSet();
		// logger.debug("主险期满，附加险续保=" + tsql);
		// nLCContSet = nLCContDB.executeQuery(tsql);
		// if(nLCContSet.size()>0)
		// mLCGrpContSet.add(nLCContSet);
		//
		// //主险期满，负险续保
		// nLCContDB = new LCContDB();
		// nLCContSet = new LCContSet();
		// logger.debug("主险期满，负险续保========="+jsql);
		// nLCContSet = nLCContDB.executeQuery(jsql);
		// if(nLCContSet.size()>0)
		// mLCGrpContSet.add(nLCContSet);

		if (tLCGrpContDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "LCContQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "保单表查询失败!";
			this.mErrors.addOneError(tError);
			mLCGrpContSet.clear();

			return false;
		}

		if (mLCGrpContSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "没有查询到符合条件的保单表!";
			this.mErrors.addOneError(tError);
			mLCGrpContSet.clear();
			logger.debug("没有找到存在的表单");

			return false;
		}

		mResult.clear();
		mResult.add(mLCGrpContSet);
		logger.debug("++++++++++++++++++++++++++++++++++++++++++");
		logger.debug("              查询出应抽档的数据共" + mLCGrpContSet.size());
		logger.debug("++++++++++++++++++++++++++++++++++++++++++");

		logger.debug(mLCGrpContSet.size());
		return true;
	}

	public boolean queryAllContSQL() {
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
		if (mGrpContNo != null && !mGrpContNo.equals("")) {
			sqlStr = sqlStr + " and ContNo = '" + mGrpContNo + "'";
			tsql = tsql + " and ContNo = '" + mGrpContNo + "'";
			// jsql = jsql + " and ContNo = '" + mGrpContNo + "'";
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

	public String getStartTime() {
		return mStartDate;
	}

	public String getSubTime() {
		return mSubDate;
	}
}
