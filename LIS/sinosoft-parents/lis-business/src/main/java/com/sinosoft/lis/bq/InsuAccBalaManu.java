package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 万能险保单账户手工触发结算
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
 * @CreateDate： 2007-11-22
 */
public class InsuAccBalaManu implements BusinessService {
	private static Logger logger = Logger.getLogger(InsuAccBalaManu.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();
	private GlobalInput tG = new GlobalInput();

	private String sManageCom = "";
	private String sBalaDate = "";
	private String sRiskCode = "";
	private String tContNo = "";

	/** 数据操作字符串 */
	private String mOperate;

	public String mContent;

	private int iSuccCount = 0;
	private int iFailCount = 0;

	private ExeSQL tExeSQL = new ExeSQL();
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("after getInputData....");

		String tComSQL = "";
		// 指定保单处理
		if (!"".equals(tContNo) && tContNo != null) {
			sManageCom = tContNo.substring(0, 4);
			if (!dealData()) {
				return false;
			}
		} else {
			// 分机构处理
			tComSQL = "select comcode from ldcom  where comcode='86' ";
            SQLwithBindVariables sbv=new SQLwithBindVariables();
            sbv.sql(tComSQL);
			SSRS tSSRS = tExeSQL.execSQL(sbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				CError.buildErr(this, "查询分支机构失败!");
				return false;
			} else {
				for (int k = 1; k <= tSSRS.MaxRow; k++) {
					// 数据操作业务处理
					sManageCom = tSSRS.GetText(k, 1);
					if (!dealData()) {
						return false;
					}
				}
			}
		}

		logger.debug("after dealData...");
		mResult.clear();
		mResult.add(this.mContent);

		return true;
	}

	private boolean getInputData() {
		// 全局变量
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		tContNo = (String) mTransferData.getValueByName("ContNo");
		sBalaDate = (String) mTransferData.getValueByName("BalaDate");
		sRiskCode = (String) mTransferData.getValueByName("RiskCode");
		// sInsuAccNo= (String) mTransferData.getValueByName("InsuAccNo");
		return true;
	}

	private boolean dealData() {
		logger.debug("Universal Insurance Start.....");

		try {

			String sCurrentDate = PubFun.getCurrentDate();
			// sBalaDate="2007-11-01";

			// 指定结算止期

			if ("".equals(sBalaDate) || sBalaDate == null) {
				ExeSQL tExeSQL = new ExeSQL();
				String tSql = "select max(baladate) from LMInsuAccRate where "
						+ "riskcode ='"
						+ "?sRiskCode?"
						+ "' and rateintv='Y' and ratestate='1' order by baladate desc";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("sRiskCode", sRiskCode);
				sBalaDate = tExeSQL.getOneValue(sqlbv); // 获取最新公布的利率
				if ("".equals(sBalaDate) || sBalaDate == null) {
					sBalaDate = sCurrentDate;
				}
				sBalaDate = sBalaDate.substring(0, 10);
			}
			tG.Operator = "001";
			tG.ManageCom = sManageCom;
			tG.ComCode = sManageCom;

			// add by jiaqiangli 2009-10-15 加锁按险种 两个并发进程处理放入set的保单存在危险
			// 另外也可以一上来先判断是否已经结算到sBalaDate了
			if (!mPubLock.lock(sRiskCode, "LI0001", tG.Operator)) {
				logger.debug("InsuAccBalaManu ConCurrency 出现双击"
						+ sRiskCode.hashCode() + "-" + PubFun.getCurrentDate()
						+ PubFun.getCurrentTime());
				CError tError = new CError("险种" + sRiskCode
						+ "正在进行万能险结算，不允许双击！");
				this.mErrors.addOneError(tError);
				return false;
			}
			// add by jiaqiangli 2009-10-15 加锁按险种

			// 查询出当前系统日期有公布结算利率的帐户,当查询出是所有有公布险种结算利率的保单，本月是否结算未知
			String sql = " select a.* from lcpol b, lcinsureacc a "
					+ " where a.riskcode in  ( select riskcode from LMInsuAccRate "
					+ " where "
					+
					// 为了万能测试暂时注释掉
					// "baladate <= date'"
					// + sBalaDate
					// + "'  and date'"
					// + sBalaDate
					// + "' <ADD_MONTHS(baladate,1) "
					// + " and " +
					"ratestate='1' and InsuAccNo=a.InsuAccNo and riskcode='"
					+ "?sRiskCode?"
					+ "') "
					+ " and a.baladate <to_date('"
					+ "?sBalaDate?"
					+ "','yyyy-mm-dd')"
					+ " and b.polno = a.polno and b.appflag = '1' "
					+ " and a.ManageCom like concat('"
					+ "?sManageCom?"
					+ "','%') "
					+ ReportPubFun.getWherePart("a.ContNo", ReportPubFun.getParameterStr(tContNo, "?tContNo?"))
					+ " and exists (select  'X' from lccontstate c where c.statetype='Available' and c.state='0' and c.enddate is null and c.polno=a.polno)"
					// test as usecase add by jiaqiangli 2009-10-15
					+ " and not exists (select  'X' from lcconthangupstate d where d.ContNo=a.ContNo)";
			logger.debug(sql);
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("sRiskCode", sRiskCode);
			sqlbv.put("sBalaDate", sBalaDate);
			sqlbv.put("sManageCom", sManageCom);
		    sqlbv.put("tContNo", tContNo);
			RSWrapper rsWrapper = new RSWrapper();
			LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
			if (!rsWrapper.prepareData(tLCInsureAccSet, sqlbv)) {
				this.mErrors.copyAllErrors(rsWrapper.mErrors);
				logger.debug(rsWrapper.mErrors.getFirstError());
				return false;
			}

			do {
				rsWrapper.getData();
				if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1) {
					break;
				}
				// String LockNo="";
				for (int i = 1; i <= tLCInsureAccSet.size(); i++) {

					// LockNo = "";
					// LockNo= tLCInsureAccSet.get(i).getContNo();
					// comment by jiaqiangli 2009-11-06 此处存在bug
					// 前面锁完险种后，锁第一单后，处理完解锁时除了将第一单解锁，还产生了副作用（解锁了险种）
					// 究其原因是使用同一个并发锁类先后加锁解锁两个不同号段 应该使用两个不同类
					// 这里为了效率上的考虑就使用一个锁即可（锁险种）
					// if (!mPubLock.lock(LockNo, "LI0001", tG.Operator))
					// {
					// CError tError = new
					// CError(mPubLock.mErrors.getLastError());
					// this.mErrors.addOneError(tError);
					// continue;
					// }

					if (!RecurCount(tLCInsureAccSet.get(i), sBalaDate,
							tLCInsureAccSet.get(i).getBalaDate())) {
						++iFailCount;
						continue;

					} else {
						++iSuccCount;
					}
					// mPubLock.unLock();
				}
			} while (tLCInsureAccSet != null && tLCInsureAccSet.size() > 0);
			rsWrapper.close();

			// add by jiaqiangli 2009-10-15 解锁按险种
			mPubLock.unLock();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			mPubLock.unLock();

		}
		this.mContent = "共计" + iSuccCount + "个保单结算成功，" + "共计" + iFailCount
				+ "个保单结算失败.";
		logger.debug("Universal Insurance End.....");
		return true;
	}

	/**
	 * 判断帐户是否是首月结算 @
	 * 
	 * @return String
	 */
	private String checkFirst(String sInsurAccNo, String sPolNo) {
		String sql = " select 1 from lcinsureacc a " + " where a.insuaccno = '"
				+ "?sInsurAccNo?" + "' and polno = '" + "?sPolNo?"
				+ "' and baladate = (select cvalidate from lcpol c "
				+ " where polno = (a.polno))";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sInsurAccNo", sInsurAccNo);
		sqlbv.put("sPolNo", sPolNo);
		String sFirst = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单生效日期查询失败!");
			return null;
		}
		if (sFirst != null && sFirst.equals("1")) {
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * <p>
	 * 递归结算
	 * </p>
	 * 
	 * @author: pst
	 * @version: 1.0
	 * @date: 2008-04-28
	 * @param: tLCInsureAccSchema
	 * @param:tCurBalaDate 本次需要结算的日期
	 * @param:tPreBalaDate 最迟一次结算的日期
	 * @return:boolean
	 * */
	private boolean RecurCount(LCInsureAccSchema tLCInsureAccSchema,
			String tCurBalaDate, String tLastBalaDate) {
		InsuAccBala tInsuAccBala = null;
		TransferData tTransferData = null;
		ExeSQL tExeSQL = new ExeSQL();
		String isFirst = checkFirst(tLCInsureAccSchema.getInsuAccNo(),
				tLCInsureAccSchema.getPolNo());
		if (isFirst == null) {
			return false;
		}
		if ("Y".equals(isFirst)) // 首次结算
		{

			// 获得本月结算日
			//
			String rBalaDate = tLastBalaDate.substring(0, 8) + "01";
			String cBalaDate = PubFun.calDate(rBalaDate, 1, "M", "");//

			String tAccTraceSQL = "select (case when polno is not null then polno else '' end) from LCInsureAccTrace where polno='"
					+ "?polno?"
					+ "' and  moneytype='LX' and paydate='"
					+ "?cBalaDate?"
					+ "' and money>=0";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tAccTraceSQL);
			sqlbv.put("polno", tLCInsureAccSchema.getPolNo());
			sqlbv.put("cBalaDate", cBalaDate);
			String tFlag = tExeSQL.getOneValue(sqlbv);
			if ("".equals(tFlag) || tFlag == null) {
				tInsuAccBala = new InsuAccBala();
				tTransferData = new TransferData();
				tTransferData.setNameAndValue("InsuAccNo",
						tLCInsureAccSchema.getInsuAccNo());
				tTransferData.setNameAndValue("PolNo",
						tLCInsureAccSchema.getPolNo());
				tTransferData.setNameAndValue("BalaDate", cBalaDate);
				VData tVData = new VData();
				tVData.add(tG);
				tVData.add(tTransferData);
				if (!tInsuAccBala.submitData(tVData, "Service")) {
					return false;

				}
				tInsuAccBala = null;
				tTransferData = null;
			}
			int tIntvD = PubFun.calInterval(cBalaDate, tCurBalaDate, "D");
			if (tIntvD > 0) // 说明单子漏结算过,需要递归
			{
				String tNextDate = PubFun.calDate(cBalaDate, 1, "M", "");
				RecurCount(tLCInsureAccSchema, tCurBalaDate, tNextDate);
			}
		} else {

			String tAccTraceSQL = "select (case when polno is not null then polno else '' end) from LCInsureAccTrace where polno='"
					+ "?polno?"
					+ "' and  moneytype='LX' and paydate='"
					+ "?tLastBalaDate?"
					+ "' and money>=0";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tAccTraceSQL);
			sqlbv.put("polno", tLCInsureAccSchema.getPolNo());
			sqlbv.put("tLastBalaDate", tLastBalaDate);
			String tFlag = tExeSQL.getOneValue(sqlbv);
			if ("".equals(tFlag) || tFlag == null) {
				tInsuAccBala = new InsuAccBala();
				tTransferData = new TransferData();
				tTransferData.setNameAndValue("InsuAccNo",
						tLCInsureAccSchema.getInsuAccNo());
				tTransferData.setNameAndValue("PolNo",
						tLCInsureAccSchema.getPolNo());
				tTransferData.setNameAndValue("BalaDate", tLastBalaDate);
				VData tVData = new VData();
				tVData.add(tG);
				tVData.add(tTransferData);
				if (!tInsuAccBala.submitData(tVData, "Service")) {
					return false;

				}
				tInsuAccBala = null;
				tTransferData = null;
			}
			int tIntvD = PubFun.calInterval(tLastBalaDate, tCurBalaDate, "D");
			if (tIntvD > 0) // 说明单子漏结算过,需要递归
			{
				String tNextDate = PubFun.calDate(tLastBalaDate, 1, "M", "");
				RecurCount(tLCInsureAccSchema, tCurBalaDate, tNextDate);
			}
		}
		return true;
	}

	public static void main(String arg[]) {

		String[] tAllPol = { "86320620090210030809", "86230020090210014942",
				"86430020090210012803", "86341320090210002559",
				"86320620090210032046", "86411520090210018769",
				"86341320090210002557", "86320620090210031171",
				"86430020090210012328", "86320620090210030265",
				"86320620090210030727", "86411520090210020601",
				"86230620090210002117", "86341320090210002011",
				"86411520090219000054" };

		for (int i = 0; i < tAllPol.length; i++) {
			String tBalaDate = "2009-10-01";
			String tRiskCode = "314302";
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("BalaDate", tBalaDate);
			tTransferData.setNameAndValue("RiskCode", tRiskCode);
			tTransferData.setNameAndValue("ContNo", tAllPol[i]);
			tTransferData.setNameAndValue("ManageCom", "86");

			VData tVData = new VData();
			tVData.addElement(tTransferData);
			InsuAccBalaManu tInsuAccBalaManu = new InsuAccBalaManu();
			tInsuAccBalaManu.submitData(tVData, "");
		}
		logger.debug("all done");
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}

}
