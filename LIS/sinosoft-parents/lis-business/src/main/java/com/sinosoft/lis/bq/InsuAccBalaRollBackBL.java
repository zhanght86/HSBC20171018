package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 万能险保单账户结算回滚类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: ChinaSoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 * @CreateDate：2007-11-22
 */
public class InsuAccBalaRollBackBL {
private static Logger logger = Logger.getLogger(InsuAccBalaRollBackBL.class);

	/** 全局变量 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 保险帐户号码，通常情况下为险种编码 */
	private String mInsuAccNo;

	/** 保单险种编码 */
	private String mRiskCode;

	/** 保单险种号码 */
	private String mPolNo;
	/**
	 * 保单号
	 */
	private MMap map = new MMap();
	private String mContNo;

	private String mManageCom = "";
	private String mBalaDate = "";
	public String mContent;
	String sCurrentDate = PubFun.getCurrentDate();

	/**
	 * submitData
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		if (!getInputData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}
		if (!prepareData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			buildError("InsuAccBalaRollBack", "数据提交失败！");
			return false;
		}

		return true;
	}

	private boolean getInputData() {
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		mBalaDate = (String) mTransferData.getValueByName("BalaDate");
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mManageCom == null || mManageCom.trim().equals(""))
			mManageCom = "86";
		if (mContNo == null || mContNo.trim().equals(""))
			mContNo = "";
		if (mBalaDate == null || mBalaDate.trim().equals(""))
			mBalaDate = "";
		return true;
	}

	private boolean dealData() {
		logger.debug("万能险保单结算回滚运行开始。。。。");

		String tSQLLCInsuAcc = "";
		String tSQLLCInsuAccClass = "";
		String tSQLLCInsuAccTrace = "";
		String tSQLLCInsuAccFee = "";
		String tSQLLCInsuAccFeeClass = "";
		String tSQLLCInsuAccFeeClassDel = "";
		String tSQLLCInsuAccFeeTrace = "";
		String tSQLLOPrtManager = "";
		String tBalaDate = "";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		SQLwithBindVariables sbv6=new SQLwithBindVariables();
		SQLwithBindVariables sbv7=new SQLwithBindVariables();
		SQLwithBindVariables sbv8=new SQLwithBindVariables();
		if ("".equals(mBalaDate)) // 不指定則回復到生效日
		{
			mBalaDate = "(select cvalidate from lcpol a where a.polno = b.polno)";
			tBalaDate = sCurrentDate;
		} else {
			tBalaDate = mBalaDate;
		}

		if ("".equals(mContNo))// 批量回滚
		{
			tSQLLCInsuAcc = "update lcinsureacc b"
					+ " set b.baladate=?mBalaDate?,"
					+ " b.insuaccbala=(select sum(money) from lcinsureacctrace c where c.polno = b.polno and c.paydate<'"
					+ "?tBalaDate?"
					+ "'),"
					+ " b.lastaccbala=(select sum(money) from lcinsureacctrace d where d.polno = b.polno and  d.moneytype='BF')"
					+ " where b.polno not in "
					+ " (select polno from lcinsureacctrace where moneytype = 'LQ')";
			sbv1.sql(tSQLLCInsuAcc);
			sbv1.put("tBalaDate", tBalaDate);
			sbv1.put("mBalaDate", mBalaDate);
			map.put(sbv1, "UPDATE");
			tSQLLCInsuAccClass = "update lcinsureaccclass b "
					+ " set baladate =?mBalaDate?,"
					+ " insuaccbala = (select sum(money) from lcinsureacctrace c where c.polno = b.polno and c.paydate<'"
					+ "?tBalaDate?"
					+ "'),"
					+ " lastaccbala = (select sum(money) from lcinsureacctrace d where d.polno = b.polno and d.moneytype='BF')"
					+ " where b.polno not in"
					+ " (select polno from lcinsureacctrace where moneytype = 'LQ')";
			sbv2.sql(tSQLLCInsuAccClass);
			sbv2.put("tBalaDate", tBalaDate);
			sbv2.put("mBalaDate", mBalaDate);
			map.put(sbv2, "UPDATE");
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSQLLCInsuAccTrace = "delete from lcinsureacctrace "
						+ " where moneytype not in ('BF', 'LQ', 'LF')"
						+ " and polno not in"
						+ " (select polno from lcinsureacctrace where moneytype = 'LQ') and paydate<'"
						+ "?tBalaDate?" + "'";
				sbv3.sql(tSQLLCInsuAccTrace);
				sbv3.put("tBalaDate", tBalaDate);
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSQLLCInsuAccTrace = "delete from lcinsureacctrace "
						+ " where moneytype not in ('BF', 'LQ', 'LF')"
						+ " and polno not in"
						+ " (select polno from  (select polno from lcinsureacctrace where moneytype = 'LQ')  a ) and paydate<'"
						+ "?tBalaDate?" + "'";
				sbv3.sql(tSQLLCInsuAccTrace);
				sbv3.put("tBalaDate", tBalaDate);
			}
			map.put(sbv3, "DELETE");
			tSQLLCInsuAccFee = "update lcinsureaccfee b"
					+ " set baladate = ?mBalaDate?,"
					+ " fee = (select fee from lcinsureaccfeetrace c where c.polno = b.polno and c.feecode='815601')  "
					+ " where b.polno not in"
					+ " (select polno from lcinsureacctrace where moneytype = 'LQ') and b.baladate<'"
					+ "?tBalaDate?" + "'";
			sbv4.sql(tSQLLCInsuAccFee);
			sbv4.put("tBalaDate", tBalaDate);
			sbv4.put("mBalaDate", mBalaDate);
			map.put(sbv4, "UPDATE");
			tSQLLCInsuAccFeeClass = "update lcinsureaccclassfee b"
					+ " set baladate = ?mBalaDate?,"
					+ " fee = (select fee from lcinsureaccfeetrace c where c.polno = b.polno and c.feecode='815601')  "
					+ " where b.polno not in"
					+ " (select polno from lcinsureacctrace where moneytype = 'LQ') and b.baladate<'"
					+ "?tBalaDate?" + "'";
			sbv5.sql(tSQLLCInsuAccFeeClass);
			sbv5.put("tBalaDate", tBalaDate);
			sbv5.put("mBalaDate", mBalaDate);
			map.put(sbv5, "UPDATE");
			tSQLLCInsuAccFeeClassDel = "delete from lcinsureaccclassfee where lcinsureaccclassfee.feecode='815602' and lcinsureaccclassfee.polno not in"
					+ " (select polno from lcinsureacctrace where moneytype = 'LQ') and lcinsureaccclassfee.baladate<'"
					+ "?tBalaDate?" + "'";
			sbv6.sql(tSQLLCInsuAccFeeClassDel);
			sbv6.put("tBalaDate", tBalaDate);
			map.put(sbv6, "DELETE");

			tSQLLCInsuAccFeeTrace = "delete from lcinsureaccfeetrace "
					+ "where feecode in ('815602')"
					+ "and polno not in "
					+ "(select polno from lcinsureacctrace where moneytype = 'LQ') and paydate<'"
					+ "?tBalaDate?" + "'";
			sbv7.sql(tSQLLCInsuAccFeeTrace);
			sbv7.put("tBalaDate", tBalaDate);


				
				
				
				
			
				
			map.put(sbv7, "DELETE");
			tSQLLOPrtManager = "delete from loprtmanager"
					+ " where code in ('BQ00','BQ01') and otherno not in"
					+ "(select contno from lcinsureacctrace where moneytype = 'LQ') and to_date(standbyflag1,'YYYY-MM-DD')<'"
					+ "?tBalaDate?" + "'";
			sbv8.sql(tSQLLOPrtManager);
			sbv8.put("tBalaDate", tBalaDate);
			map.put(sbv8, "DELETE");

		} else// 单个回滚
		{
			tSQLLCInsuAcc = "update lcinsureacc b"
					+ " set b.baladate=?mBalaDate?,"
					+ " b.insuaccbala=(select sum(money) from lcinsureacctrace c where c.polno = b.polno and c.paydate<='"
					+ "?tBalaDate?"
					+ "'),"
					+ " b.lastaccbala=(select sum(money) from lcinsureacctrace d where d.polno = b.polno and d.moneytype='BF')"
					+ " where b.polno not in "
					+ " (select polno from lcinsureacctrace where moneytype = 'LQ') and b.ContNo='"
					+ "?mContNo?" + "' and b.paydate<'" + "?tBalaDate?" + "'";
			sbv1.sql(tSQLLCInsuAcc);
			sbv1.put("tBalaDate", tBalaDate);
			sbv1.put("mContNo", mContNo);
			sbv1.put("mBalaDate", mBalaDate);
			map.put(sbv1, "UPDATE");
			tSQLLCInsuAccClass = "update lcinsureaccclass b "
					+ " set baladate = ?mBalaDate?,"
					+ " insuaccbala = (select sum(money) from lcinsureacctrace c where c.polno = b.polno and c.paydate<='"
					+ "?tBalaDate?"
					+ "'),"
					+ " lastaccbala = (select sum(money) from lcinsureacctrace d where d.polno = b.polno and d.moneytype='BF')"
					+ " where b.polno not in"
					+ " (select polno from lcinsureacctrace where moneytype = 'LQ') and b.ContNo='"
					+ "?mContNo?" + "' and b.paydate<'" + "?tBalaDate?" + "'";
			sbv2.sql(tSQLLCInsuAccClass);
			sbv2.put("tBalaDate", tBalaDate);
			sbv2.put("mContNo", mContNo);
			sbv2.put("mBalaDate", mBalaDate);
			map.put(sbv2, "UPDATE");
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSQLLCInsuAccTrace = "delete from lcinsureacctrace "
						+ " where moneytype not in ('BF', 'LQ', 'LF')"
						+ " and polno not in"
						+ " ( select polno from lcinsureacctrace where moneytype = 'LQ') and ContNo='"
						+ "?mContNo?" + "' and paydate >='" + "?tBalaDate?" + "'";
				sbv3.sql(tSQLLCInsuAccTrace);
				sbv3.put("tBalaDate", tBalaDate);
				sbv3.put("mContNo", mContNo);
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSQLLCInsuAccTrace = "delete from lcinsureacctrace "
						+ " where moneytype not in ('BF', 'LQ', 'LF')"
						+ " and polno not in"
						+ " (select polno from (select polno from lcinsureacctrace where moneytype = 'LQ') a ) and ContNo='"
						+ "?mContNo?" + "' and paydate >='" + "?tBalaDate?" + "'";
				sbv3.sql(tSQLLCInsuAccTrace);
				sbv3.put("tBalaDate", tBalaDate);
				sbv3.put("mContNo", mContNo);
			}
			map.put(sbv3, "DELETE");
			tSQLLCInsuAccFee = "update lcinsureaccfee b"
					+ " set baladate = ?mBalaDate?,"
					+ " fee = (select fee from lcinsureaccfeetrace c where c.polno = b.polno and c.feecode='815601')  "
					+ " where b.polno not in"
					+ " (select polno from lcinsureacctrace where moneytype = 'LQ') and b.ContNo='"
					+ "?mContNo?" + "' and b.baladate<'" + "?tBalaDate?" + "'";
			sbv4.sql(tSQLLCInsuAccFee);
			sbv4.put("tBalaDate", tBalaDate);
			sbv4.put("mContNo", mContNo);
			sbv4.put("mBalaDate", mBalaDate);
			map.put(sbv4, "UPDATE");
			tSQLLCInsuAccFeeClass = "update lcinsureaccclassfee b"
					+ " set baladate = ?mBalaDate?,"
					+ " fee = (select fee from lcinsureaccfeetrace c where c.polno = b.polno and c.feecode='815601')  "
					+ " where b.polno not in"
					+ " (select polno from lcinsureacctrace where moneytype = 'LQ') and b.ContNo='"
					+ "?mContNo?" + "' and b.baladate<'" + "?tBalaDate?" + "'";
			sbv5.sql(tSQLLCInsuAccFeeClass);
			sbv5.put("tBalaDate", tBalaDate);
			sbv5.put("mContNo", mContNo);
			sbv5.put("mBalaDate", mBalaDate);
			map.put(sbv5, "UPDATE");
			tSQLLCInsuAccFeeClassDel = "delete from lcinsureaccclassfee where lcinsureaccclassfee.feecode='815602' and lcinsureaccclassfee.polno not in"
					+ " (select polno from lcinsureacctrace where moneytype = 'LQ') and lcinsureaccclassfee.ContNo='"
					+ "?mContNo?" + "' and lcinsureaccclassfee.baladate<'" + "?tBalaDate?" + "'";
			sbv6.sql(tSQLLCInsuAccFeeClassDel);
			sbv6.put("tBalaDate", tBalaDate);
			sbv6.put("mContNo", mContNo);
			map.put(sbv6, "DELETE");

			tSQLLCInsuAccFeeTrace = "delete from lcinsureaccfeetrace "
					+ "where feecode in ('815602')"
					+ "and polno not in "
					+ "(select polno from lcinsureacctrace where moneytype = 'LQ') and ContNo='"
					+ "?mContNo?" + "' and paydate >='" + "?tBalaDate?" + "'";
			sbv7.sql(tSQLLCInsuAccFeeTrace);
			sbv7.put("tBalaDate", tBalaDate);
			sbv7.put("mContNo", mContNo);


				
				
				
				
				
				
				
			map.put(sbv7, "DELETE");
			tSQLLOPrtManager = "delete from loprtmanager"
					+ " where code in ('BQ00','BQ01') and otherno not in"
					+ "(select contno from lcinsureacctrace where moneytype = 'LQ') and otherno='"
					+ "?mContNo?" + "' and to_date(standbyflag1,'YYYY-MM-DD')>='" + "?tBalaDate?" + "'";
			sbv8.sql(tSQLLOPrtManager);
			sbv8.put("tBalaDate", tBalaDate);
			sbv8.put("mContNo", mContNo);
			map.put(sbv8, "DELETE");
		}

		return true;
	}

	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 错误构建方法
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "InsuAccBalaRollBack";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * getErrors
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
}
